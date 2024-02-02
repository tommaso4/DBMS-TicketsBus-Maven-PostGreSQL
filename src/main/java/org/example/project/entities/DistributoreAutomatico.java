package org.example.project.entities;

import jakarta.persistence.*;
import org.example.project.enums.StatoDistributore;

@Entity
@Table(name="distributori_automatici")
public class DistributoreAutomatico extends Venditore{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoDistributore stato;

    public DistributoreAutomatico(){}

    public DistributoreAutomatico(String name, StatoDistributore stato) {
        super(name);
        this.stato = stato;
    }

    public StatoDistributore getStato() {
        return stato;
    }

    public void setStato(StatoDistributore stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return  super.toString()+
                ", stato=" + stato;
    }
}
