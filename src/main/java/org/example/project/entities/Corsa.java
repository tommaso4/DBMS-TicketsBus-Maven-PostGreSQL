package org.example.project.entities;

import jakarta.persistence.*;
import org.example.project.dao.CorsaDao;

import java.time.Duration;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "corse")
@NamedQuery(name = "associazioneVeicoloTratta",query ="SELECT COUNT(c) FROM Corsa c WHERE c.tratta.id=:trattaId AND c.veicolo.id=:veicoloId" )
public class Corsa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenza_venditore")
    @SequenceGenerator(name = "sequenza_venditore", initialValue = 1, allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "veicolo_fk", nullable = false)
    private Veicolo veicolo;

    @ManyToOne
    @JoinColumn(name = "tratta_fk", nullable = false)
    private Tratta tratta;

    @OneToMany(mappedBy = "corsa", cascade = CascadeType.ALL)
    private final List<Biglietto> biglietti=new ArrayList<>();

    @Column(name = "data_partenza", nullable = false)
    private LocalDateTime dataPartenza;
    @Column(name = "data_arrivo")
    private LocalDateTime dataArrivo;
    private int durata;

    public Corsa() {}

    public Corsa(Veicolo veicolo, Tratta tratta, LocalDateTime dataPartenza) {
        this.veicolo = veicolo;
        this.tratta = tratta;
        this.dataPartenza = dataPartenza;
    }

    public void setDataArrivo(LocalDateTime dataArrivo) {
        this.dataArrivo=dataArrivo;
        if (dataArrivo.isAfter(dataPartenza)){
            this.durata = (int) Duration.between(this.dataPartenza, this.dataArrivo).toMinutes();
            tratta.setmediaDurata();
        }
        caricaDatabase();
    }
    public void timbraBiglietto(Biglietto biglietto) {
        biglietto.setTimbrato(true);
    }

    public void caricaDatabase() {
        CorsaDao corsaDao = new CorsaDao();
        try{corsaDao.upDate(this);}
        catch (Exception e){
            System.out.println("Errore nel salvataggio");
            System.out.println(e.getMessage());
        }finally {
            corsaDao.closeEM();
        }
    }
    public List<Biglietto> cercaBigliettoPerData (LocalDate primaData, LocalDate secondaData){
        List<Biglietto> bigliettiPerData = new ArrayList<Biglietto>();
        for (Biglietto b : biglietti){
            if (primaData.isBefore(b.getDataTimbro().toLocalDate()) && secondaData.isAfter(b.getDataTimbro().toLocalDate())){
                bigliettiPerData.add(b);
            }
        }
        return  bigliettiPerData;
    }

    public int getId() {
        return id;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
        caricaDatabase();
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
        caricaDatabase();
    }

    public LocalDateTime getDataPartenza() {
        return dataPartenza;
    }

    public void setDataPartenza(LocalDateTime dataPartenza) {
        this.dataPartenza = dataPartenza;
        caricaDatabase();
    }

    public LocalDateTime getDataArrivo() {
        return dataArrivo;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
        caricaDatabase();
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", veicolo=" + veicolo +
                ", tratta=" + tratta +
                ", dataPartenza=" + dataPartenza +
                ", dataArrivo=" + dataArrivo +
                ", durata=" + durata;
    }

    public void stampaListaBiglietti() {
        System.out.println("Lista dei biglietti per la corsa con ID " + id + ":");
        if(biglietti != null){
        for (Biglietto biglietto : biglietti) {
            System.out.println("ID Biglietto: " + biglietto.getId());
            System.out.println("Timbrato: " + biglietto.isTimbrato());
        }}}
}
