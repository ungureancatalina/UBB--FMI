package lab.model;
import java.io.Serializable;
import java.util.UUID;

// Implementeaza Serializable pentru a putea fi transformat in biti
// si trimis prin retea (prin Socket) de la Client la Server si invers.
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    // Identificator unic
    public String id;
    public String clientName;
    public String cnp;

    // Datele care identifica ce a ales clientul (Centrul X, Atelierul Y).
    public int centerId;
    public int workshopId;
    public int hour;

    // Starea curenta: REZERVARE (initial), PLATITA, EXPIRATA sau ANULATA.
    public String status;

    // Momentul crearii (in milisecunde). Este important pentru a calcula
    // daca au trecut cele 15 secunde (T_plata) si daca trebuie expirata.
    public long timestamp;
    public double cost;

    public Reservation(String clientName, String cnp, int centerId, int workshopId, int hour, double cost) {
        this.id = UUID.randomUUID().toString();
        this.clientName = clientName;
        this.cnp = cnp;
        this.centerId = centerId;
        this.workshopId = workshopId;
        this.hour = hour;
        this.status = "REZERVARE";
        this.timestamp = System.currentTimeMillis();
        this.cost = cost;
    }
}