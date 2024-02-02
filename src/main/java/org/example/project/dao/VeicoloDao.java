package org.example.project.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.project.entities.TesseraCliente;
import org.example.project.entities.Veicolo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class VeicoloDao {
    private final EntityManager entityManager;
    private final EntityManagerFactory emf;

    public VeicoloDao() {
        this.emf = Persistence.createEntityManagerFactory("biglietteria");
        entityManager =emf.createEntityManager();
    }

    public void saveVeicolo(Veicolo veicolo) throws Exception {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(veicolo);
        transaction.commit();
    }

    public void upDate(Veicolo v) throws Exception {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.merge(v);
        et.commit();
    }

    public Veicolo getVeicoloById(int id){
        return entityManager.find(Veicolo.class,id);
    }
    public void delete(int id){
        EntityTransaction et=entityManager.getTransaction();
        et.begin();
        entityManager.remove(getVeicoloById(id));
        et.commit();
    }

    public void closeEM(){
        entityManager.close();
        emf.close();
    }

    public List<Object[]> dataManutenzioniVeicolo(int idVeicolo) {
            return entityManager.createNamedQuery("Manutenzione.cercaPerVeicoloId", Object[].class)
                    .setParameter("idVeicolo", idVeicolo)
                    .getResultList();
    }
    public List<Object[]> periodiServizioVeicolo(int idVeicolo) {
        try {
            List<Object[]> periodiServizio = new ArrayList<>();
            List<Object[]> manutenzioni =dataManutenzioniVeicolo(idVeicolo);

            LocalDate dataInizioPrimoServizioVeicolo = entityManager.find(Veicolo.class, idVeicolo).getDataInizioServizio();
            LocalDate dataInizioServizio = dataInizioPrimoServizioVeicolo;

            for (Object[] manutenzione : manutenzioni) {
                LocalDate dataInizioManutenzione = (LocalDate) manutenzione[0];
                periodiServizio.add(new Object[]{dataInizioServizio, dataInizioManutenzione});
                dataInizioServizio = (LocalDate) manutenzione[1];
            }
            periodiServizio.add(new Object[]{dataInizioServizio, LocalDate.now()});

            return periodiServizio;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public int sommaGiorniServizio(int idVeicolo){

        int sommaGiorni = 0;

        List<Object[]> periodiServizio =periodiServizioVeicolo(idVeicolo);
        if (!periodiServizio.isEmpty()){
        for (Object[]periodo: periodiServizio){
            LocalDate dataInizio = (LocalDate)periodo[0];
            LocalDate dataFine = (LocalDate) periodo[1];
            long giorniServizio = ChronoUnit.DAYS.between(dataInizio, dataFine) + 1;
            sommaGiorni += (int) giorniServizio;
        }}
        return sommaGiorni;
    }
    public int sommaGiorniManutenzione(int idVeicolo){

        int sommaGiorni = 0;

        List<Object[]> periodiManutenzione =dataManutenzioniVeicolo(idVeicolo);
        if (!periodiManutenzione.isEmpty()){
            for (Object[]periodo: periodiManutenzione){
                LocalDate dataInizio = (LocalDate)periodo[0];
                LocalDate dataFine = (LocalDate) periodo[1];
                long giorniManutenzione = ChronoUnit.DAYS.between(dataInizio, dataFine) + 1;
                sommaGiorni += (int) giorniManutenzione;
            }}
        return sommaGiorni;
    }

    public LocalDate getUltimaDataManutenzione(int idVeicolo) {
        try {
            return entityManager.createNamedQuery("Manutenzione.ultimaDataPerVeicoloId", LocalDate.class)
                    .setParameter("idVeicolo", idVeicolo)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDate getInizioManutenzioneProgrammata(int idVeicolo) {
        try {
            return entityManager.createNamedQuery("Manutenzione.ultimaDataInizioPerVeicoloId", LocalDate.class)
                    .setParameter("idVeicolo", idVeicolo)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}

