package org.example.project;

import org.example.project.dao.*;
import org.example.project.entities.*;
import org.example.project.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DBGenerator {
    private static final CorsaDao corsaDao = new CorsaDao();
    private static final ManutenzioneDao manutenzioneDao = new ManutenzioneDao();
    private static final ProdottoDao prodottoDao = new ProdottoDao();
    private static final TesseraDao tesseraDao = new TesseraDao();
    private static final TrattaDao trattaDao = new TrattaDao();
    private static final VeicoloDao veicoloDao = new VeicoloDao();
    private static final VenditoreDao venditoreDao = new VenditoreDao();
    public static void main(String[] args) {
        try{
            TesseraCliente t1=creaTessera("Emilio","Plances", LocalDate.of(1997,3,7), Genere.M, CategoriaCliente.STUDENTE);
            TesseraCliente t2= creaTessera("Tommaso","Cantarini",LocalDate.of(1991,6,20),Genere.M,CategoriaCliente.STUDENTE);
            TesseraCliente t3= creaTessera("Calogero","Teresi",LocalDate.of(1999,3,12),Genere.M,CategoriaCliente.STUDENTE);

            Venditore v1=creaVenditore("Da Mario");
            DistributoreAutomatico d1=creaDistributore("Shish", StatoDistributore.ATTIVO);
            Venditore v2=creaVenditore("Da Carlo");
            DistributoreAutomatico d2=creaDistributore("BellOchhio",StatoDistributore.ATTIVO);

            Tratta tratta1=creaTratta(TipoTratta.EXTRA_URBANA,"Palermo","Catania");
            Tratta tratta2=creaTratta(TipoTratta.URBANA,"Via piave","Via Isonzo");
            Tratta tratta3=creaTratta(TipoTratta.EXTRA_URBANA,"Cagliari","Oristano");
            Tratta tratta4=creaTratta(TipoTratta.EXTRA_URBANA,"Ancona","Osimo");

            Abbonamento a1=creaAbbonamento(v1,tratta1, TipoAbbonamento.MENSILE,t1);
            Abbonamento a2=creaAbbonamento(v2,tratta2,TipoAbbonamento.MENSILE,t1);
            Abbonamento a3=creaAbbonamento(d1,tratta1,TipoAbbonamento.MENSILE,t2);
            Abbonamento a4=creaAbbonamento(v1,tratta1,TipoAbbonamento.MENSILE,t3);

            Veicolo veicolo1=creaVeicolo(TipoVeicolo.AUTOBUS);
            Veicolo veicolo2=creaVeicolo(TipoVeicolo.AUTOBUS);
            Veicolo veicolo3=creaVeicolo(TipoVeicolo.TRAM);
            Veicolo veicolo4=creaVeicolo(TipoVeicolo.TRAM);

            Corsa c1 = creaCorsa(veicolo1,tratta1, LocalDateTime.of(2024,1,28,8,20));
            Corsa c2= creaCorsa(veicolo2,tratta1,LocalDateTime.of(2024,1,28,10,20));
            Corsa c3= creaCorsa(veicolo2,tratta2,LocalDateTime.of(2024,1,30,12,20));
            Corsa c4 = creaCorsa(veicolo1,tratta2,LocalDateTime.of(2024,1,30,14,20));

            Biglietto biglietto = creaBiglietto(v1,tratta1);
            Biglietto biglietto1 = creaBiglietto(v1,tratta1);
            Biglietto biglietto2 = creaBiglietto(v1,tratta2);
            Biglietto biglietto3 = creaBiglietto(v1,tratta2);

            Manutenzione manutenzione1 = creaManutenzione(veicolo1, LocalDate.of(2024,3,10),LocalDate.of(2024,3,20));
            Manutenzione manutenzione2 = creaManutenzione(veicolo2, LocalDate.of(2024,6,10),LocalDate.of(2024,6,20));
            Manutenzione manutenzione3 = creaManutenzione(veicolo2, LocalDate.of(2024,3,10),LocalDate.of(2024,3,20));
            Manutenzione manutenzione4 = creaManutenzione(veicolo1, LocalDate.of(2024,6,10),LocalDate.of(2024,6,20));



        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public static TesseraCliente creaTessera(String nome, String cognome, LocalDate dataNascita, Genere genere, CategoriaCliente categoriaCliente) throws Exception {
        TesseraCliente t = new TesseraCliente(nome, cognome, dataNascita, genere, categoriaCliente);
        tesseraDao.save(t);
        return t;
    }

    public static Venditore creaVenditore(String nome) throws Exception{
        Venditore v = new Venditore(nome);
        venditoreDao.save(v);
        return v;
    }

    public static DistributoreAutomatico creaDistributore(String nome, StatoDistributore statoDistributore)throws Exception {
        DistributoreAutomatico d = new DistributoreAutomatico(nome, statoDistributore);
        venditoreDao.save(d);
        return d;
    }

    public static Tratta creaTratta(TipoTratta tipoTratta, String partenza, String destinazione)throws Exception {
        Tratta t = new Tratta(tipoTratta, partenza, destinazione);
        trattaDao.save(t);
        return t;
    }

    public static Corsa creaCorsa(Veicolo veicolo, Tratta tratta, LocalDateTime dataPartenza)throws Exception {
        Corsa c = new Corsa(veicolo, tratta, dataPartenza);
        corsaDao.aggiungiCorsa(c);
        return c;
    }

    public static Abbonamento creaAbbonamento(Venditore venditore, Tratta tratta, TipoAbbonamento tipoAbbonamento, TesseraCliente tesseraCliente)throws Exception {
        Abbonamento a = new Abbonamento(venditore, tratta, tipoAbbonamento, tesseraCliente);
        prodottoDao.save(a);
        return a;
    }

    public static Biglietto creaBiglietto(Venditore venditore, Tratta tratta)throws Exception {
        Biglietto biglietto = new Biglietto(venditore, tratta);
        prodottoDao.save(biglietto);
        return biglietto;
    }

    public static Veicolo creaVeicolo(TipoVeicolo tipoveicolo)throws Exception {
        Veicolo v = new Veicolo(tipoveicolo);
        veicoloDao.saveVeicolo(v);
        return v;
    }

    public static Manutenzione creaManutenzione(Veicolo veicolo, LocalDate dataInizio, LocalDate dataFine) throws Exception{
        Manutenzione m = new Manutenzione(veicolo, dataInizio, dataFine);
        manutenzioneDao.saveManutenzione(m);
        return m;
    }
}
