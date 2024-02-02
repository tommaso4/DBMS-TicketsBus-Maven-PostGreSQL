package org.example.project;

import jakarta.persistence.Query;
import org.example.project.dao.*;
import org.example.project.entities.*;
import org.example.project.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class UseApp {
    private static final CorsaDao corsaDao = new CorsaDao();
    private static final ManutenzioneDao manutenzioneDao = new ManutenzioneDao();
    private static final ProdottoDao prodottoDao = new ProdottoDao();
    private static final TesseraDao tesseraDao = new TesseraDao();
    private static final TrattaDao trattaDao = new TrattaDao();
    private static final VeicoloDao veicoloDao = new VeicoloDao();
    private static final VenditoreDao venditoreDao = new VenditoreDao();
    public static void main(String[] args) throws Exception {

        TesseraCliente t1 = tesseraDao.getById(159);//  Tessera Emilio
        TesseraCliente t2 = tesseraDao.getById(367);//  Tessera Tommaso
        TesseraCliente t3 = tesseraDao.getById(79);//  Tessera Calogero

        Venditore v1 = venditoreDao.getById(1);
        DistributoreAutomatico d1 = (DistributoreAutomatico) venditoreDao.getById(2);
        Venditore v2 = venditoreDao.getById(3);
        DistributoreAutomatico d2 = (DistributoreAutomatico) venditoreDao.getById(4);

        Tratta tratta1 = trattaDao.getTrattaById(1);
        Tratta tratta2 = trattaDao.getTrattaById(2);
        Tratta tratta3 = trattaDao.getTrattaById(3);
        Tratta tratta4 = trattaDao.getTrattaById(4);


        Abbonamento abbonamento1 = (Abbonamento) prodottoDao.getById(1);
        Abbonamento abbonamento2 = (Abbonamento) prodottoDao.getById(2);
        Abbonamento abbonamento3 = (Abbonamento) prodottoDao.getById(3);
        Abbonamento abbonamento4 = (Abbonamento) prodottoDao.getById(4);

        Veicolo veicolo1 = veicoloDao.getVeicoloById(1);
        Veicolo veicolo2 = veicoloDao.getVeicoloById(2);
        Veicolo veicolo3 = veicoloDao.getVeicoloById(3);
        Veicolo veicolo4 = veicoloDao.getVeicoloById(4);

        Corsa corsa3 = corsaDao.cercaCorsaById(9);
        Corsa corsa4 = corsaDao.cercaCorsaById(6);
        Corsa corsa1 = corsaDao.cercaCorsaById(7);
        Corsa corsa2 = corsaDao.cercaCorsaById(8);

        Biglietto biglietto1 = (Biglietto) prodottoDao.getById(5);
        Biglietto biglietto2 = (Biglietto) prodottoDao.getById(6);
        Biglietto biglietto3 = (Biglietto) prodottoDao.getById(7);
        Biglietto biglietto4 = (Biglietto) prodottoDao.getById(8);

        Manutenzione m1 = manutenzioneDao.getManutenzioneById(1);
        Manutenzione m2 = manutenzioneDao.getManutenzioneById(2);
        Manutenzione m3 = manutenzioneDao.getManutenzioneById(3);
        Manutenzione m4 = manutenzioneDao.getManutenzioneById(4);
                                        //RICHIESTE DELLA TRACCIA

        // ricerca di prodottiAcquistati per un determinato venditore

        Object vendutiDaV1 = prodottoDao.vendutiDaVenditore(v1);
        System.out.println("conteggio di prodotti venduti:" + vendutiDaV1);
        System.out.println("----------------------------------------------------------------");

//        //ricerca di prodottiAcquistati per range di date e venditore

        Object nrProdotti = prodottoDao.venditeEffettuateInData(LocalDate.of(2024, 1, 25),
                LocalDate.of(2024, 3, 31), v1.getId());
        System.out.println("conteggio di prodotti venduti in una data:" + nrProdotti);
        System.out.println("----------------------------------------------------------------");
//
//      //Verifica rapida di validità abbonamento per singola tessera

        Abbonamento ab =(Abbonamento) prodottoDao.getById(1);
        ab.setValiditaAbbonamento(false);
        prodottoDao.upDate(ab);
        List<Object[]> abbonamentiScaduti = prodottoDao.abbonamentiScadutiPerTessera(t1.getTessera_cliente());
        for (Object[] abb : abbonamentiScaduti) {
            System.out.println("abbId:" + abb[0]);
            System.out.println("nomeTessera:" + abb[1]);
            System.out.println("validita:" + abb[2]);
        }
        System.out.println("----------------------------------------------------------------");

//        //metodo per avere la lista di manutenzioni:

        stampaListaManutenziioni(veicolo2);
        System.out.println("----------------------------------------------------------------");
//
//        //metodo per avere la lista dei dati di servizio
        stampaPeriodiDiServizio(veicolo2);
        System.out.println("----------------------------------------------------------------");

        //timbratura di un biglietto
        System.out.println("timbratura di un biglietto");
        biglietto3.timbraBiglietto(corsa2);
        biglietto1.timbraBiglietto(corsa2);

        //biglietti timbrati in una corsa
        System.out.println("biglietti timbrati in una corsa");
        biglietto1.timbraBiglietto(corsa2);
        biglietto2.timbraBiglietto(corsa2);
        corsa2.getBiglietti().forEach(System.out::println);


        //biglietti timbrati in una corsa in un certo periodo di tempo
        System.out.println("biglietti timbrati in una corsa in un certo periodo di tempo");
        List<Biglietto> bigliettos = corsa2.cercaBigliettoPerData(LocalDate.of(2023,1,22),LocalDate.of(2024,2,4));
        bigliettos.forEach(System.out::println);


//        corsa2.setDataArrivo(LocalDateTime.of(2024,5,15,15,15,15));
        System.out.println("--------------------");
        corsa4.setDataArrivo(LocalDateTime.of(2024,1,28,15,0,0));
        corsa3.setDataArrivo(LocalDateTime.of(2024,1,28,15,0,0));

//        Corsa corsa5 = creaCorsa(veicolo2, tratta1, LocalDateTime.of(2023,3,12,12,0,0));


        corsaDao.closeEM();
        manutenzioneDao.closeEM();
        prodottoDao.closeEM();
        tesseraDao.closeEM();
        trattaDao.closeEM();
        veicoloDao.closeEM();
        venditoreDao.closeEM();
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

    public static void stampaPeriodiDiServizio(Veicolo veicolo){
        List<Object[]> periodiServizio = veicoloDao.periodiServizioVeicolo(veicolo.getId());

        for (Object[] periodo : periodiServizio) {
            LocalDate dataInizio = (LocalDate) periodo[0];
            LocalDate dataFine = (LocalDate) periodo[1];

            System.out.println("Periodo di servizio: " + dataInizio + " - " + dataFine);
        }
        System.out.println("Il veicolo con ID: " + veicolo.getId() + " ha effettuato " + veicoloDao.sommaGiorniServizio(veicolo.getId()) + " giorni di servizio");
    }

    public static void stampaListaManutenziioni(Veicolo veicolo) {
        List<Object[]> manutenzioni = veicoloDao.dataManutenzioniVeicolo(veicolo.getId());
        for (Object[] manutenzione : manutenzioni) {
            LocalDate dataInizio = (LocalDate) manutenzione[0];
            LocalDate dataFine = (LocalDate) manutenzione[1];
            System.out.println(" data inizio manutenzione:" + dataInizio + " data fine:" + dataInizio);
        }
        System.out.println("Il veicolo con ID: " + veicolo.getId() + " ha effettuato " + veicoloDao.sommaGiorniManutenzione(veicolo.getId()) + " giorni di manutenzione");
    }
}





