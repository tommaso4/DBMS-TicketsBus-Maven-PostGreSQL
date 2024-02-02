package org.example.project.entities;
import jakarta.persistence.*;
import org.example.project.dao.ProdottoDao;

import java.time.LocalDateTime;

@Entity
@Table(name = "biglietti")
public class Biglietto extends ProdottoAcquistato {
    @Column(nullable = false)
    private boolean timbrato = false;

    @ManyToOne
    @JoinColumn(name="corsa_fk")
    private Corsa corsa;

    @Column(name = "data_timbro")
    private LocalDateTime dataTimbro = null;

    public Biglietto() {}
    public Biglietto(Venditore venditore, Tratta tratta) {
        super(venditore, tratta);
    }
    public boolean isTimbrato() {
        return timbrato;
    }

    public void setTimbrato(boolean timbrato) {
        this.timbrato = timbrato;
        caricaDatabase();
    }

    public Corsa getCorsa() {
        return corsa;
    }

    public void setCorsa(Corsa corsa) {
        this.corsa = corsa;
        caricaDatabase();
    }

    public LocalDateTime getDataTimbro() {
        return dataTimbro;
    }

    public void timbraBiglietto(Corsa corsa){
        if (!isTimbrato()) {
            this.corsa = corsa;
            this.timbrato = true;
            this.dataTimbro = LocalDateTime.now();
            caricaDatabase();
        }}

    @Override
    public String toString() {
        return  super.toString()+
                ", corsa="+corsa+
                ", timbrato=" + timbrato;
    }
}
