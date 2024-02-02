package org.example.project.dao;

import jakarta.persistence.*;
import org.example.project.entities.*;

import java.util.ArrayList;
import java.util.List;

public class CorsaDao {
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public CorsaDao() {
        emf = Persistence.createEntityManagerFactory("biglietteria");
        em = emf.createEntityManager();
    }

    public List<Biglietto> getBigliettiTimbratiInCorsa(int idCorsa) {
        try {
            return em.createNamedQuery("Corsa.trovaBigliettiTimbrati", Biglietto.class)
                    .setParameter("idCorsa", idCorsa)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int numeroBigliettiTimbrati(int idCorsa) {
        List<Biglietto> bigliettiTimbrati = getBigliettiTimbratiInCorsa(idCorsa);
        if (bigliettiTimbrati != null) {
            return bigliettiTimbrati.size();
        } else {
            return 0;
        }
    }


    public void aggiungiCorsa(Corsa c) throws Exception {
        EntityTransaction et = em.getTransaction();
        et.begin();
        Veicolo veicolo = c.getVeicolo();
        if (!veicolo.isDisponibile(c)) {
            throw new Exception("Il veicolo non è disponibile per la corsa.");
        }
        em.persist(c);
        et.commit();
        em.refresh(c);
    }

    public void upDate(Corsa c) throws Exception {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(c);
        et.commit();
    }
    public Corsa cercaCorsaById(int id){
        return em.find(Corsa.class, id);
    }

    public void cancellaCorsaById(int id) throws Exception {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.remove(cercaCorsaById(id));
        et.commit();
    }

    public void closeEM() {
        emf.close();
        em.close();
    }

    public long associazioneVeicoloTratta(Veicolo v, Tratta t){
        Query q=em.createNamedQuery("associazioneVeicoloTratta");
        q.setParameter("veicoloId",v.getId());
        q.setParameter("trattaId",t.getId());
        return (long) q.getSingleResult();
    }
}
