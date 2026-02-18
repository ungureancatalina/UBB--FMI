package lab.model;

import java.io.Serializable;
import java.util.UUID;

// Implementeaza Serializable pentru a putea fi transformat in biti
// si trimis prin retea (prin Socket) de la Client la Server si invers.
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    // ID unic al tranzactiei
    public String id;

    // Legatura catre rezervarea pentru care s-a facut plata.
    public String resId;

    // Tipul operatiunii: "INCASARE" (bani veniti) sau "RAMBURS" (bani returnati).
    // Poate fi si "ANULARE_TIMEOUT" cu suma 0.
    public String type;

    // Suma tranzactionata (poate fi negativa la ramburs).
    public double amount;
    public long timestamp;

    public Transaction(String resId, String type, double amount) {
        this.id = UUID.randomUUID().toString();
        this.resId = resId;
        this.type = type;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }
}