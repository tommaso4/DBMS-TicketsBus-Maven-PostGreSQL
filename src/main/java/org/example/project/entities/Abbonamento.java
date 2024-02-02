package org.example.project.entities;

import jakarta.persistence.*;
import org.example.project.enums.TipoAbbonamento;

import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
@NamedQuery(name = "checkValidita", query = "SELECT a FROM Abbonamento a WHERE a.id=:id")
@NamedQuery(name = "abbonamentiScaduti", query = "SELECT a FROM Abbonamento a WHERE a.fineValidita<CURRENT_DATE")
@NamedQuery(name = "abbonamentiScadutiPerUtente", query = "SELECT a.id, a.tesseraCliente.cognome, a.validitaAbbonamento" +
        " FROM Abbonamento a WHERE a.fineValidita<CURRENT_DATE or a.validitaAbbonamento = false " +
        "and a.tesseraCliente.tesseraCliente = :tesseraCliente")
@NamedQuery(name="checkExisting",query="SELECT a FROM Abbonamento a WHERE a.tratta.id=:trattaId AND a.tesseraCliente.id=:tesseraId")
public class Abbonamento extends ProdottoAcquistato {
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_abbonamento", nullable = false)
    private TipoAbbonamento tipoAbbonamento;

    @Column(name = "fine_validita", nullable = false)
    private LocalDate fineValidita;

    @ManyToOne
    @JoinColumn(name = "tessera_cliente_fk", nullable = false)
    private TesseraCliente tesseraCliente;

    @Column(name = "validita_abbonamento", nullable = false)
    private boolean validitaAbbonamento = true;



    public Abbonamento() {}
    public Abbonamento(Venditore venditore, Tratta tratta, TipoAbbonamento tipoAbbonamento, TesseraCliente tesseraCliente) {
        super(venditore, tratta);
        this.tipoAbbonamento = tipoAbbonamento;
        this.tesseraCliente = tesseraCliente;
        this.fineValidita = setFineValidita();
    }

    public LocalDate setFineValidita() {
        if (this.getDataAcquisto() != null) {
            if (this.tipoAbbonamento.equals(TipoAbbonamento.SETTIMANALE)) {
                this.fineValidita = this.getDataAcquisto().plusWeeks(1);
            } else if (this.tipoAbbonamento.equals(TipoAbbonamento.MENSILE)) {
                this.fineValidita = this.getDataAcquisto().plusDays(30);
            } else {
                this.fineValidita = this.getDataAcquisto().plusYears(1);
            }
        }
        return this.fineValidita;
    }
    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public TesseraCliente getTesseraCliente() {
        return tesseraCliente;
    }

    public LocalDate getFineValidita() {
        return fineValidita;
    }

    public void setFineValidita(LocalDate fineValidita) {
        this.fineValidita = fineValidita;
    }

    public boolean isValiditaAbbonamento() {
        return validitaAbbonamento;
    }

    public void setValiditaAbbonamento(boolean validitaAbbonamento) {
        this.validitaAbbonamento = validitaAbbonamento;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", fineValidita=" + fineValidita +
                ", tesseraCliente=" + tesseraCliente +
                ", validitaAbbonamento=" + validitaAbbonamento;
    }
}
