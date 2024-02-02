package org.example.project.entities;

import jakarta.persistence.*;
import org.example.project.dao.ProdottoDao;


import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQuery(
        name = "bigliettiVenduti",
        query = "SELECT COUNT(p) FROM ProdottoAcquistato p WHERE p.dataAcquisto BETWEEN :dataInizio AND :dataFine AND p.venditore.id = :idVenditore"
)
@NamedQuery(name="vendutiDaVenditore",query="SELECT COUNT(p) FROM ProdottoAcquistato p WHERE p.venditore=:Venditore")
public abstract class ProdottoAcquistato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sequenza_prodotto")
    @SequenceGenerator(name="sequenza_prodotto",initialValue = 1,allocationSize = 1)
    private Integer id;

    @Column(name="data_acquisto",nullable = false)
    private LocalDate dataAcquisto;

    @ManyToOne
    @JoinColumn(name="venditore_fk",nullable = false)
    private Venditore venditore;

    @ManyToOne
    @JoinColumn(name="tratta_fk",nullable = false)
    private Tratta tratta;

    public ProdottoAcquistato(){}

    public ProdottoAcquistato(Venditore venditore, Tratta tratta) {
        this.venditore = venditore;
        this.tratta = tratta;
        dataAcquisto=LocalDate.now();
    }

    public void caricaDatabase() {
        ProdottoDao prodottoDao = new ProdottoDao();
        try{prodottoDao.upDate(this);}
        catch (Exception e){
            System.out.println("Errore nel salvataggio");
            System.out.println(e.getMessage());
        }finally {
            prodottoDao.closeEM();
        }
    }


    public Integer getId() {
        return id;
    }

    public LocalDate getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(LocalDate dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
        caricaDatabase();
    }

    public Venditore getVenditore() {
        return venditore;
    }

    public void setVenditore(Venditore venditore) {
        this.venditore = venditore;
        caricaDatabase();
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", dataAcquisto=" + dataAcquisto +
                ", venditore=" + venditore;
    }

    public Tratta getTratta() {
        return tratta;
    }
}
