package org.example.project.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.project.entities.TesseraCliente;
import org.example.project.entities.Tratta;

public class TrattaDao {
    private final EntityManagerFactory emf;
    private final EntityManager em;
    public TrattaDao(){
        emf= Persistence.createEntityManagerFactory("biglietteria");
        em=emf.createEntityManager();
    }

    public void save(Tratta t) throws Exception{
        EntityTransaction et=em.getTransaction();
        et.begin();
        em.persist(t);
        et.commit();
        em.refresh(t);
    }
    public void upDate(Tratta t) throws Exception {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(t);
        et.commit();
    }
    public Tratta getTrattaById(int id){
        return em.find(Tratta.class,id);
    }

    public void deleteById(int id) throws Exception{
        EntityTransaction et=em.getTransaction();
        et.begin();
        em.remove(getTrattaById(id));
        et.commit();
    }

    public void closeEM(){
        emf.close();
        em.close();
    }
}
