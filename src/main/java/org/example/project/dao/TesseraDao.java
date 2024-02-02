package org.example.project.dao;

import jakarta.persistence.*;
import org.example.project.entities.ProdottoAcquistato;
import org.example.project.entities.TesseraCliente;

import java.time.LocalDate;
import java.util.List;


public class TesseraDao {
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public TesseraDao() {
        this.emf = Persistence.createEntityManagerFactory("biglietteria");
        this.em = emf.createEntityManager();
    }
    public void save (TesseraCliente t)throws Exception{
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(t);
        et.commit();
    }

    public void upDate(TesseraCliente t) throws Exception {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(t);
        et.commit();
    }
    public TesseraCliente getById(int id) {
        return em.find(TesseraCliente.class,id);
    }
    public void delete (TesseraCliente a){
        try{
            EntityTransaction et = em.getTransaction();
            et.begin();
            TesseraCliente art = getById(a.getTessera_cliente());
            em.remove(art);
            et.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeEM() {
        emf.close();
        em.close();
    }


    public boolean controllaTesseraById(int id){
        TesseraCliente t = getById(id);
        return t.isStatoTessera();
    }

    public void disattivaTessera(int id){
        TesseraCliente t = getById(id);
        boolean statoTessera = controllaTesseraById(id);

        try{
            if (statoTessera) {
                t.setStatoTessera(false);
                EntityTransaction et = em.getTransaction();
                et.begin();
                em.merge(t);
                et.commit();
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void attivaTessera(int id){
        TesseraCliente t = getById(id);
        boolean statoTessera = controllaTesseraById(id);

        try{
            if (statoTessera) {
                t.setStatoTessera(false);
                EntityTransaction et = em.getTransaction();
                et.begin();
                em.merge(t);
                et.commit();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void controllaOgniTessera(){
        try {
            LocalDate currentDate = LocalDate.now();
            Query q = em.createNamedQuery("getListaTessereScadute");
            q.setParameter("currentDate", currentDate);

            List<TesseraCliente> tessereScadute = q.getResultList();

            EntityTransaction et = em.getTransaction();
            et.begin();

            for (TesseraCliente tessera : tessereScadute) {
                tessera.setStatoTessera(false);
                em.merge(tessera);
            }

            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }









}
