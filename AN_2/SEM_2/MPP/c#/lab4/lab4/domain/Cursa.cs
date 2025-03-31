namespace lab4.domain
{
    public class Cursa(int idCursa, string nume, int capacitateMinima, int capacitateMaxima)
        : IDentifiable<int>
    {
        private int _idCursa = idCursa;
        private string _nume = nume;
        private int _capacitateMinima = capacitateMinima;
        private int _capacitateMaxima = capacitateMaxima;

        public Cursa(string nume, int capacitateMinima, int capacitateMaxima) : this(0, nume, capacitateMinima, capacitateMaxima) {}

        public string GetNume() => _nume;
        public void SetNume(string nume) => this._nume = nume;

        public int GetCapacitateMinima() => _capacitateMinima;
        public void SetCapacitateMinima(int capacitateMinima) => this._capacitateMinima = capacitateMinima;

        public int GetCapacitateMaxima() => _capacitateMaxima;
        public void SetCapacitateMaxima(int capacitateMaxima) => this._capacitateMaxima = capacitateMaxima;

        public void SetId(int id) => this._idCursa = id;
        public int GetId() => _idCursa;

        public override bool Equals(object? obj)
        {
            if (this == obj) return true;
            if (obj == null || GetType() != obj.GetType()) return false;
            var cursa = (Cursa)obj;
            return _idCursa == cursa._idCursa;
        }

        public override int GetHashCode() => _idCursa.GetHashCode();

        public override string ToString()
        {
            return $"Cursa{{idCursa={_idCursa}, nume='{_nume}', capacitateMinima={_capacitateMinima}, capacitateMaxima={_capacitateMaxima}}}";
        }
    }
}