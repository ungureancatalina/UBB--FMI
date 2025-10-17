using System;

namespace rt
{
    class RayTracer(Geometry[] geometries, Light[] lights)
    {
        private double ImageToViewPlane(int n, int imgSize, double viewPlaneSize)
        {
            return -n * viewPlaneSize / imgSize + viewPlaneSize / 2;
        }

        private Intersection FindFirstIntersection(Line ray, double minDist, double maxDist)
        {
            var intersection = Intersection.NONE;

            foreach (var geometry in geometries)
            {
                var intr = geometry.GetIntersection(ray, minDist, maxDist);

                if (!intr.Valid || !intr.Visible) continue;

                if (!intersection.Valid || !intersection.Visible)
                {
                    intersection = intr;
                }
                else if (intr.T < intersection.T)
                {
                    intersection = intr;
                }
            }

            return intersection;
        }

        private bool IsLit(Vector point, Light light)
        {
            // Offset mic pentru a evita auto-intersectia
            const double eps = 1e-6;

            // Vector catre sursa de lumina
            var toLight = light.Position - point;
            var dist = toLight.Length();
            var dir = toLight / dist; // normalizam

            // Raza de umbra (shadow ray)
            var origin = point + dir * eps;
            var shadowRay = new Line(origin, origin + dir);

            // Verificam daca raza de umbra este blocata
            var intr = FindFirstIntersection(shadowRay, eps, dist - eps);

            // Daca ceva blocheaza lumina, punctul nu este luminat
            return !(intr.Valid && intr.Visible);
        }




        public void Render(Camera camera, int width, int height, string filename)
        {
            var background = new Color(0.2, 0.2, 0.2, 1.0);

            var image = new Image(width, height);

            for (var i = 0; i < width; i++)
            {
                for (var j = 0; j < height; j++)
                {
                    // Calculeaza coordonatele pixelului pe planul de vizualizare
                    double vx = ImageToViewPlane(i, width, camera.ViewPlaneWidth);
                    double vy = ImageToViewPlane(j, height, camera.ViewPlaneHeight);

                    // Pozitia pixelului pe planul de vizualizare
                    var center = camera.Position + camera.Direction.Normalize() * camera.ViewPlaneDistance;
                    var right = (camera.Direction ^ camera.Up).Normalize();
                    var up = camera.Up.Normalize();
                    var pixelPos = center - right * vx + up * vy; 
                    // Creeaza raza prin pixel
                    var ray = new Line(camera.Position, pixelPos);

                    // Gaseste prima intersectie cu geometria scenei
                    var intr = FindFirstIntersection(ray, camera.FrontPlaneDistance, camera.BackPlaneDistance);

                    if (!intr.Valid || !intr.Visible)
                    {
                        // Daca nu s-a gasit nicio intersectie, folosim culoarea de fundal
                        image.SetPixel(i, j, background);
                        continue;
                    }

                    // Calcul iluminare simpla (Phong)
                    var mat = intr.Material ?? Material.BLANK;
                    var surfColor = intr.Color;
                    var finalColor = new Color(0, 0, 0, 1);

                    foreach (var light in lights)
                    {
                        var L = (light.Position - intr.Position).Normalize();
                        var N = intr.Normal;
                        var V = (camera.Position - intr.Position).Normalize();
                        var R = (N * (2.0 * (N * L)) - L).Normalize();

                        double ndotl = Math.Max(0.0, N * L);
                        double rdotv = Math.Max(0.0, R * V);
                        double spec = rdotv > 0.0 ? Math.Pow(rdotv, Math.Max(1.0, mat.Shininess)) : 0.0;

                        // Adaugam ambientul oricum
                        finalColor += surfColor * mat.Ambient;

                        // Daca punctul nu e in umbra, adaugam difuz si specular
                        if (IsLit(intr.Position, light))
                        {
                            finalColor += surfColor * mat.Diffuse * ndotl + mat.Specular * spec;
                        }
                    }

                    // Seteaza pixelul cu culoarea calculata
                    image.SetPixel(i, j, finalColor);

                }
            }

            image.Store(filename);
        }
    }
}