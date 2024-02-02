package org.example.project.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.project.entities.Manutenzione;
import org.example.project.entities.ProdottoAcquistato;
import org.example.project.entities.Veicolo;
import org.example.project.enums.StatoVeicolo;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

public class ManutenzioneDao {
    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final VeicoloDao veicoloDao;

    public ManutenzioneDao() {
        emf= Persistence.createEntityManagerFactory("biglietteria");
        em=emf.createEntityManager();
        veicoloDao = new VeicoloDao();
    }

    public void saveManutenzione(@NotNull Manutenzione manutenzione) throws Exception {
        EntityTransaction transaction = em.getTransaction();
        Veicolo veicolo = manutenzione.getVeicoloM();

        if (!veicolo.isInManutenzione()) {
            veicolo.setStatoVeicolo(StatoVeicolo.IN_MANUTENZIONE);

            // SERVE AGGIORNARE IL DB
            transaction.begin();
            em.persist(manutenzione);
            transaction.commit();
            em.refresh(manutenzione);
        }
    }

    public void upDate(Manutenzione m) throws Exception {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(m);
        et.commit();
    }
    public Manutenzione getManutenzioneById(int id) {
        return em.find(Manutenzione.class, id);
    }

    public void deleteManutenzione(int id){
        EntityTransaction et=em.getTransaction();
        et.begin();
        em.remove(getManutenzioneById(id));
        et.commit();
    }

    public void closeEM(){
        emf.close();
        em.close();
    }


}

