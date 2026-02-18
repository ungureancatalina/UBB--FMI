package lab.model;
import java.io.Serializable;

public class Workshop implements Serializable {
    public int id;
    public String name;
    public double price;
    public int duration;

    // Capacitatea maxima a atelierului. Serverul va verifica mereu
    // sa nu depasim acest numar de rezervari active la o anumita ora.
    public int capacity;

    public Workshop(int id, String name, double price, int duration, int capacity) {
        this.id = id; this.name = name; this.price = price; this.duration = duration; this.capacity = capacity;
    }
}