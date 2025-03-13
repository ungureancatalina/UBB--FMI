package ro.mpp2024.domain;

public class Echipa {
    private int idEchipa;
    private String nume;

    public Echipa(int idEchipa, String nume) {
        this.idEchipa = idEchipa;
        this.nume = nume;
    }

    public int getIdEchipa() {
        return idEchipa;
    }

    public void setIdEchipa(int idEchipa) {
        this.idEchipa = idEchipa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
