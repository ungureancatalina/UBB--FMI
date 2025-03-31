namespace lab4.domain
{
    public class Echipa(int idEchipa, string nume) : IDentifiable<int>
    {
        private int _idEchipa = idEchipa;
        private string _nume = nume;

        public Echipa(string nume) : this(0, nume) {}

        public void SetId(int id) => this._idEchipa = id;
        public int GetId() => _idEchipa;

        public string GetNume() => _nume;
        public void SetNume(string nume) => this._nume = nume;

        public override bool Equals(object? obj)
        {
            if (this == obj) return true;
            if (obj == null || GetType() != obj.GetType()) return false;
            var echipa = (Echipa)obj;
            return _idEchipa == echipa._idEchipa;
        }

        public override int GetHashCode() => _idEchipa.GetHashCode();

        public override string ToString()
        {
            return $"Echipa{{idEchipa={_idEchipa}, nume='{_nume}'}}";
        }
    }
}