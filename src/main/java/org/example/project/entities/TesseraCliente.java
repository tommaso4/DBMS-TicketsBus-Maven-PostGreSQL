package org.example.project.entities;

import jakarta.persistence.*;
import org.example.project.dao.ProdottoDao;
import org.example.project.dao.TesseraDao;
import org.example.project.enums.CategoriaCliente;
import org.example.project.enums.Genere;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name="tessere_clienti")
@NamedQuery(name = "getListaTessereScadute", query = "SELECT t FROM TesseraCliente t WHERE t.dataScadenza < :currentDate")
public class TesseraCliente {
    @Id
    @Column(name = "tessera_cliente",nullable = false)
    private int tesseraCliente;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(name = "data_di_nascita",nullable = false)
    private LocalDate dataNascita;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genere genere;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_cliente",nullable = false)
    private CategoriaCliente categoriaCliente;
    @Column(name = "data_attivazione",nullable = false)
    private LocalDate dataAttivazione;
    @Column (name = "data_scadenza")
    private LocalDate dataScadenza;
    @Column(name = "stato_tessera",nullable = false)
    private boolean statoTessera = false;

    @Column(name = "data_rinnovo",nullable = false)
    private LocalDate dataRinnovo;

    @Transient
    private final List<Integer> listaTessere = new ArrayList<>();

    @OneToMany(mappedBy = "tesseraCliente")
    private List<Abbonamento> listaAbbonamenti;

    public TesseraCliente() {  }

    public TesseraCliente(String nome, String cognome, LocalDate dataNascita, Genere genere, CategoriaCliente categoriaCliente) throws Exception {
        this.tesseraCliente = setNumTessera();
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.genere = genere;
        this.categoriaCliente = categoriaCliente;
        this.dataAttivazione = LocalDate.now();
        rinnovoTessera();
    }
    public int setNumTessera() throws Exception {
        Random random = new Random();
        int possibleTessera;
        final long maxSpace = 1000L;

        if (listaTessere.size() < maxSpace) {
            do {
                possibleTessera = random.nextInt(1000) + 1;
            } while (listaTessere.contains(possibleTessera));

            listaTessere.add(possibleTessera);
            return possibleTessera;
        } else {
            throw new Exception("No more space in archive");
        }

    }
    public void rinnovoTessera(){
        if(!statoTessera){
        this.dataRinnovo = LocalDate.now();
        this.dataScadenza = LocalDate.now().plusYears(1);
        statoTessera=true;
        }else{
            System.out.println("La tessera non è scaduta");
        }
    }

    public int getTessera_cliente() {
        return tesseraCliente;
    }

    public LocalDate getDataAttivazione() {
        return dataAttivazione;
    }

    public LocalDate getDataRinnovo() {
        return dataRinnovo;
    }

    public List<Integer> getListaTessere() {
        return listaTessere;
    }

    public List<Abbonamento> getListaAbbonamenti() {
        return listaAbbonamenti;
    }

    public void caricaDatabase() {
        TesseraDao tesseraDao = new TesseraDao();
        try{tesseraDao.upDate(this);}
        catch (Exception e){
            System.out.println("Errore nel salvataggio");
            System.out.println(e.getMessage());
        }finally {
            tesseraDao.closeEM();
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        caricaDatabase();
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
        caricaDatabase();
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
        caricaDatabase();
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
        caricaDatabase();
    }

    public CategoriaCliente getCategoriaCliente() {
        return categoriaCliente;
    }

    public void setCategoriaCliente(CategoriaCliente categoriaCliente) {
        this.categoriaCliente = categoriaCliente;
        caricaDatabase();
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
        caricaDatabase();
    }

    public boolean isStatoTessera() {
        return statoTessera;
    }

    public void setStatoTessera(boolean statoTessera) {
        this.statoTessera = statoTessera;
        caricaDatabase();
    }

    @Override
    public String toString() {
        return  "tesseraCliente=" + tesseraCliente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", genere=" + genere +
                ", categoriaCliente=" + categoriaCliente +
                ", dataAttivazione=" + dataAttivazione +
                ", dataScadenza=" + dataScadenza +
                ", statoTessera=" + statoTessera +
                ", dataRinnovo=" + dataRinnovo;
    }
}
