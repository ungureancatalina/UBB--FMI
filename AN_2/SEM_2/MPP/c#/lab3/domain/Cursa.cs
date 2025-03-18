namespace lab3.domain
{
    public class Cursa : Identifiable<int>
    {
        private int idCursa;
        private string nume;
        private int capacitateMinima;
        private int capacitateMaxima;

        public Cursa(string nume, int capacitateMinima, int capacitateMaxima)
        {
            this.nume = nume;
            this.capacitateMinima = capacitateMinima;
            this.capacitateMaxima = capacitateMaxima;
        }

        public Cursa(int idCursa, string nume, int capacitateMinima, int capacitateMaxima)
        {
            this.idCursa = idCursa;
            this.nume = nume;
            this.capacitateMinima = capacitateMinima;
            this.capacitateMaxima = capacitateMaxima;
        }

        public string GetNume() => nume;
        public void SetNume(string nume) => this.nume = nume;

        public int GetCapacitateMinima() => capacitateMinima;
        public void SetCapacitateMinima(int capacitateMinima) => this.capacitateMinima = capacitateMinima;

        public int GetCapacitateMaxima() => capacitateMaxima;
        public void SetCapacitateMaxima(int capacitateMaxima) => this.capacitateMaxima = capacitateMaxima;

        public void SetId(int id) => this.idCursa = id;
        public int GetId() => idCursa;

        public override bool Equals(object? obj)
        {
            if (this == obj) return true;
            if (obj == null || GetType() != obj.GetType()) return false;
            var cursa = (Cursa)obj;
            return idCursa == cursa.idCursa;
        }

        public override int GetHashCode() => idCursa.GetHashCode();

        public override string ToString()
        {
            return $"Cursa{{idCursa={idCursa}, nume='{nume}', capacitateMinima={capacitateMinima}, capacitateMaxima={capacitateMaxima}}}";
        }
    }
}