package org.example.project.entities;

import jakarta.persistence.*;
import org.example.project.dao.TesseraDao;
import org.example.project.dao.TrattaDao;
import org.example.project.enums.TipoTratta;

import java.util.List;

@Entity
@Table(name = "tratte")
public class Tratta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_tratta")
    @SequenceGenerator(name = "sequenza_tratta", initialValue = 1, allocationSize = 1)
    private int id;
    @Column(name = "media_durata")
    private Double mediaDurata;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tratta", nullable = false)
    private TipoTratta tipoTratta;
    @Column(nullable = false)
    private String partenza;
    @Column(nullable = false)
    private String destinazione;
    @OneToMany(mappedBy = "tratta")
    private List<Abbonamento> listaAbbonamenti;
    @OneToMany(mappedBy = "tratta")
    private List<Corsa> listaCorse;

    public Tratta(){}

    public Tratta(TipoTratta tipoTratta, String partenza, String destinazione) {
        this.tipoTratta = tipoTratta;
        this.partenza = partenza;
        this.destinazione = destinazione;
    }

    public void caricaDatabase() {
        TrattaDao trattaDao = new TrattaDao();
        try{trattaDao.upDate(this);}
        catch (Exception e){
            System.out.println("Errore nel salvataggio");
            System.out.println(e.getMessage());
        }finally {
            trattaDao.closeEM();
        }
    }

    public void setmediaDurata() {
        int somma = 0;
        int count=0;
        if (listaCorse != null && !listaCorse.isEmpty()){
            for (Corsa c : listaCorse) {
                if(c.getDurata()!=0) count++;
                somma += c.getDurata();
            }
            mediaDurata=(double) somma / count;
            caricaDatabase();
        }
    }

    public int getId() {
        return id;
    }

    public double getMediaDurata() {
        return mediaDurata;
    }

    public void setMediaDurata(double mediaDurata) {
        this.mediaDurata = mediaDurata;
        caricaDatabase();
    }

    public TipoTratta getTipoTratta() {
        return tipoTratta;
    }

    public void setTipoTratta(TipoTratta tipoTratta) {
        this.tipoTratta = tipoTratta;
        caricaDatabase();
    }

    public String getPartenza() {
        return partenza;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
        caricaDatabase();
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
        caricaDatabase();
    }
    @Override
    public String toString() {
        return "id=" + id +
                ", mediaDurata=" + mediaDurata +
                ", tipoTratta=" + tipoTratta +
                ", partenza='" + partenza + '\'' +
                ", destinazione='" + destinazione + '\'';
    }
}
