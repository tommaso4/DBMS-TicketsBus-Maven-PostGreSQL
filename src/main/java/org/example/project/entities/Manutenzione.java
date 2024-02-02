package org.example.project.entities;

import jakarta.persistence.*;
import org.example.project.dao.CorsaDao;
import org.example.project.dao.ManutenzioneDao;

import java.time.LocalDate;

@Entity
@Table(name="manutenzioni")
@NamedQueries({
        @NamedQuery(name = "Manutenzione.cercaPerVeicoloId",
        query = "SELECT m.dataInizio, m.dataFine FROM Manutenzione m WHERE m.veicoloM.id = :idVeicolo"),
        @NamedQuery(name = "Manutenzione.ultimaDataPerVeicoloId",
                query = "SELECT MAX(m.dataFine) FROM Manutenzione m WHERE m.veicoloM.id = :idVeicolo"),
        @NamedQuery(name = "Manutenzione.ultimaDataInizioPerVeicoloId",
        query = "SELECT MAX(m.dataInizio) FROM Manutenzione m WHERE m.veicoloM.id = :idVeicolo")
})
public class Manutenzione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "veicolo_fk",nullable = false)
    private Veicolo veicoloM;

    @Column(name = "data_inizio",nullable = false)
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    public Manutenzione() {}
    public Manutenzione(Veicolo veicoloM, LocalDate dataInizio) {
        this.veicoloM = veicoloM;
        this.dataInizio = dataInizio;
    }
    public Manutenzione(Veicolo veicoloM, LocalDate dataInizio, LocalDate dataFine) {
        this.veicoloM = veicoloM;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public void caricaDatabase() {
        ManutenzioneDao manutenzioneDao = new ManutenzioneDao();
        try{manutenzioneDao.upDate(this);}
        catch (Exception e){
            System.out.println("Errore nel salvataggio");
            System.out.println(e.getMessage());
        }finally {
            manutenzioneDao.closeEM();
        }
    }

    public int getId() {
        return id;
    }

    public Veicolo getVeicoloM() {
        return veicoloM;
    }

    public void setVeicoloM(Veicolo veicoloM) {
        this.veicoloM = veicoloM;
        caricaDatabase();
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
        caricaDatabase();
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
        caricaDatabase();
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", veicoloM=" + veicoloM +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine;
    }
}
