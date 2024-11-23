package com.example.lab6fx.event;

import com.example.lab6fx.domain.Utilizator;

public class UtilizatorEvent extends Event {
    private TipEvent tip;
    private Utilizator utilizator, utilizator_vechi;

    public UtilizatorEvent(TipEvent tip, Utilizator utilizator) {
        this.tip = tip;
        this.utilizator = utilizator;
    }

    public UtilizatorEvent(TipEvent tip, Utilizator utilizator, Utilizator utilizator_vechi) {
        this.tip = tip;
        this.utilizator = utilizator;
        this.utilizator_vechi = utilizator_vechi;
    }

    public TipEvent getTip() {
        return tip;
    }

    public Utilizator getData() {
        return utilizator;
    }

    public Utilizator getData_vechi() {
        return utilizator_vechi;
    }
}
