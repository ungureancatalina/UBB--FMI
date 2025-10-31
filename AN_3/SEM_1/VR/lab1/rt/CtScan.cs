using System;
using System.IO;
using System.Text.RegularExpressions;

namespace rt;

public class CtScan: Geometry
{
    private readonly Vector _position;
    private readonly double _scale;
    private readonly ColorMap _colorMap;
    private readonly byte[] _data;

    private readonly int[] _resolution = new int[3];
    private readonly double[] _thickness = new double[3];
    private readonly Vector _v0;
    private readonly Vector _v1;

    public CtScan(string datFile, string rawFile, Vector position, double scale, ColorMap colorMap) : base(Color.NONE)
    {
        _position = position;
        _scale = scale;
        _colorMap = colorMap;

        var lines = File.ReadLines(datFile);
        foreach (var line in lines)
        {
            var kv = Regex.Replace(line, "[:\\t ]+", ":").Split(":");
            if (kv[0] == "Resolution")
            {
                _resolution[0] = Convert.ToInt32(kv[1]);
                _resolution[1] = Convert.ToInt32(kv[2]);
                _resolution[2] = Convert.ToInt32(kv[3]);
            } else if (kv[0] == "SliceThickness")
            {
                _thickness[0] = Convert.ToDouble(kv[1]);
                _thickness[1] = Convert.ToDouble(kv[2]);
                _thickness[2] = Convert.ToDouble(kv[3]);
            }
        }

        _v0 = position;
        _v1 = position + new Vector(_resolution[0]*_thickness[0]*scale, _resolution[1]*_thickness[1]*scale, _resolution[2]*_thickness[2]*scale);

        var len = _resolution[0] * _resolution[1] * _resolution[2];
        _data = new byte[len];
        using FileStream f = new FileStream(rawFile, FileMode.Open, FileAccess.Read);
        if (f.Read(_data, 0, len) != len)
        {
            throw new InvalidDataException($"Failed to read the {len}-byte raw data");
        }
    }
    
    private ushort Value(int x, int y, int z)
    {
        if (x < 0 || y < 0 || z < 0 || x >= _resolution[0] || y >= _resolution[1] || z >= _resolution[2])
        {
            return 0;
        }

        return _data[z * _resolution[1] * _resolution[0] + y * _resolution[0] + x];
    }

    public override Intersection GetIntersection(Line line, double minDist, double maxDist)
    {
        // Intersectie raza - bounding box volum (metoda slaburilor)
        var invDx = 1.0 / (line.Dx.X == 0 ? 1e-12 : line.Dx.X);
        var invDy = 1.0 / (line.Dx.Y == 0 ? 1e-12 : line.Dx.Y);
        var invDz = 1.0 / (line.Dx.Z == 0 ? 1e-12 : line.Dx.Z);

        double tmin = (Math.Min(_v0.X, _v1.X) - line.X0.X) * invDx;
        double tmax = (Math.Max(_v0.X, _v1.X) - line.X0.X) * invDx;
        if (tmin > tmax) (tmin, tmax) = (tmax, tmin);

        double tymin = (Math.Min(_v0.Y, _v1.Y) - line.X0.Y) * invDy;
        double tymax = (Math.Max(_v0.Y, _v1.Y) - line.X0.Y) * invDy;
        if (tymin > tymax) (tymin, tymax) = (tymax, tymin);

        if ((tmin > tymax) || (tymin > tmax)) return Intersection.NONE;
        if (tymin > tmin) tmin = tymin;
        if (tymax < tmax) tmax = tymax;

        double tzmin = (Math.Min(_v0.Z, _v1.Z) - line.X0.Z) * invDz;
        double tzmax = (Math.Max(_v0.Z, _v1.Z) - line.X0.Z) * invDz;
        if (tzmin > tzmax) (tzmin, tzmax) = (tzmax, tzmin);

        if ((tmin > tzmax) || (tzmin > tmax)) return Intersection.NONE;
        if (tzmin > tmin) tmin = tzmin;
        if (tzmax < tmax) tmax = tzmax;

        // limite
        if (tmax < minDist || tmin > maxDist) return Intersection.NONE;
        double tStart = Math.Max(tmin, minDist);
        double tEnd = Math.Min(tmax, maxDist);
        if (tEnd < tStart) return Intersection.NONE;

        // Pas mic de sampling
        var step = Math.Min(Math.Min(_thickness[0], _thickness[1]), _thickness[2]) * _scale * 0.5;
        if (step <= 0) step = 0.5;

        // Compoziție volumetrică
        Color accumulated = new Color(0, 0, 0, 0);
        double T = 1.0;
        const double EPS = 1e-3;

        for (double t = tStart; t <= tEnd; t += step)
        {
            var pos = line.CoordinateToPosition(t);
            var col = GetColor(pos);
            if (col == null || col.Alpha <= 0.0) continue;

            // culoare contribuie în funcție de transparență
            accumulated += col * col.Alpha * T;
            T *= (1.0 - col.Alpha);

            if (T < EPS)
                break;
        }

        // dacă nu s-a acumulat nimic vizibil
        if (accumulated.Alpha == 0 && T > 0.999) return Intersection.NONE;

        // creăm o "intersecție" virtuală ca să coloreze pixelul
        var fakeNormal = new Vector(0, 0, 1);
        var fakeMat = Material.FromColor(accumulated);

        return new Intersection(true, true, this, line, tStart, fakeNormal, fakeMat, accumulated);
    }

    
    private int[] GetIndexes(Vector v)
    {
        return new []{
            (int)Math.Floor((v.X - _position.X) / _thickness[0] / _scale), 
            (int)Math.Floor((v.Y - _position.Y) / _thickness[1] / _scale),
            (int)Math.Floor((v.Z - _position.Z) / _thickness[2] / _scale)};
    }
    private Color GetColor(Vector v)
    {
        int[] idx = GetIndexes(v);

        ushort value = Value(idx[0], idx[1], idx[2]);
        return _colorMap.GetColor(value);
    }

    private Vector GetNormal(Vector v)
    {
        int[] idx = GetIndexes(v);
        double x0 = Value(idx[0] - 1, idx[1], idx[2]);
        double x1 = Value(idx[0] + 1, idx[1], idx[2]);
        double y0 = Value(idx[0], idx[1] - 1, idx[2]);
        double y1 = Value(idx[0], idx[1] + 1, idx[2]);
        double z0 = Value(idx[0], idx[1], idx[2] - 1);
        double z1 = Value(idx[0], idx[1], idx[2] + 1);

        return new Vector(x1 - x0, y1 - y0, z1 - z0).Normalize();
    }
}