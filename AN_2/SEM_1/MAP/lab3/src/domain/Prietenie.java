package domain;

import java.time.LocalDateTime;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Prietenie extends Entity<Long> {

    LocalDateTime date;
    Long idUser1;
    Long idUser2;

    public Prietenie(Long idUser1, Long idUser2)
    {
        this.idUser1 = min(idUser1,idUser2);
        this.idUser2 = max(idUser1,idUser2);
    }

    public Long getIdUser1()
    {
        return idUser1;
    }

    public Long getIdUser2()
    {
        return idUser2;
    }

    /**
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }
}
