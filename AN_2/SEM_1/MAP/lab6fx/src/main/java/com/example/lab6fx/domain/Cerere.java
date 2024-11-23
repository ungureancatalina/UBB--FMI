package com.example.lab6fx.domain;

import java.time.LocalDateTime;

public class Cerere extends Entity<Long>{
    private Long id1;
    private Long id2;
    private LocalDateTime data_cererii;
    private String status;

    public Cerere(Long id1, Long id2, LocalDateTime data_cererii, String status) {
        this.id1 = id1;
        this.id2 = id2;
        this.data_cererii = data_cererii;
        this.status = status;
    }

    public Long getId1() {
        return id1;
    }
    public Long getId2() {
        return id2;
    }

    public LocalDateTime getData_cererii() {
        return data_cererii;
    }
    public String getStatus() {
        return status;
    }
}
