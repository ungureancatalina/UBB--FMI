namespace lab3.domain
{
    public class Echipa : Identifiable<int>
    {
        private int idEchipa;
        private string nume;

        public Echipa(string nume)
        {
            this.nume = nume;
        }

        public Echipa(int idEchipa, string nume)
        {
            this.idEchipa = idEchipa;
            this.nume = nume;
        }

        public void SetId(int id) => this.idEchipa = id;
        public int GetId() => idEchipa;

        public string GetNume() => nume;
        public void SetNume(string nume) => this.nume = nume;

        public override bool Equals(object? obj)
        {
            if (this == obj) return true;
            if (obj == null || GetType() != obj.GetType()) return false;
            var echipa = (Echipa)obj;
            return idEchipa == echipa.idEchipa;
        }

        public override int GetHashCode() => idEchipa.GetHashCode();

        public override string ToString()
        {
            return $"Echipa{{idEchipa={idEchipa}, nume='{nume}'}}";
        }
    }
}