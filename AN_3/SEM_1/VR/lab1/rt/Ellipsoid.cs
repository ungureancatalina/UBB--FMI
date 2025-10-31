using System;


namespace rt
{
    public class Ellipsoid : Geometry
    {
        private Vector Center { get; }
        private Vector SemiAxesLength { get; }
        private double Radius { get; }

        public Quaternion Rotation { get; set;  } = Quaternion.NONE;

        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Material material, Color color) : base(
            material, color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            Radius = radius;
        }

        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Color color) : base(color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            Radius = radius;
        }

        public Ellipsoid (Ellipsoid e) : this(new Vector(e.Center), new Vector(e.SemiAxesLength), e.Radius, new Material(e.Material), new Color(e.Color))
        {
        }
        
        public override Intersection GetIntersection(Line line, double minDist, double maxDist)
        {
            // Consideram elipsoidul ca o sfera scalata pe fiecare axa
            var axes = new Vector(SemiAxesLength.X * Radius, SemiAxesLength.Y * Radius, SemiAxesLength.Z * Radius);

            // Ray in sistemul local al elipsoidului (centrat)
            var xo = line.X0 - Center;
            var dx = line.Dx;

            // Coeficienti pentru ecuatia patraticii: a*t^2 + b*t + c = 0
            double a = (dx.X * dx.X) / (axes.X * axes.X)
                       + (dx.Y * dx.Y) / (axes.Y * axes.Y)
                       + (dx.Z * dx.Z) / (axes.Z * axes.Z);

            double b = 2.0 * ((xo.X * dx.X) / (axes.X * axes.X)
                              + (xo.Y * dx.Y) / (axes.Y * axes.Y)
                              + (xo.Z * dx.Z) / (axes.Z * axes.Z));

            double c = (xo.X * xo.X) / (axes.X * axes.X)
                + (xo.Y * xo.Y) / (axes.Y * axes.Y)
                + (xo.Z * xo.Z) / (axes.Z * axes.Z) - 1.0;

            double disc = b * b - 4.0 * a * c;
            if (disc < 0.0 || Math.Abs(a) < 1e-12)
                return Intersection.NONE;

            double sqrtD = Math.Sqrt(disc);
            double t1 = (-b - sqrtD) / (2.0 * a);
            double t2 = (-b + sqrtD) / (2.0 * a);

            double t = double.MaxValue;
            if (t1 >= minDist && t1 <= maxDist) t = t1;
            else if (t2 >= minDist && t2 <= maxDist) t = t2;
            else return Intersection.NONE;

            // Pozitia si normala la punctul de intersectie
            var position = line.CoordinateToPosition(t);
            var local = position - Center;
            var nx = local.X / (axes.X * axes.X);
            var ny = local.Y / (axes.Y * axes.Y);
            var nz = local.Z / (axes.Z * axes.Z);
            var normal = new Vector(nx, ny, nz).Normalize();

            return new Intersection(true, true, this, line, t, normal, this.Material, this.Color);
        }
    }
}
