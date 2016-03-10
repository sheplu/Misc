package com.supinfo.suplink.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.supinfo.suplink.bean.Link;
import com.supinfo.suplink.bean.User;
import com.supinfo.suplink.dao.LinkDao;

public class JpaLinkDao implements LinkDao{
	private EntityManagerFactory emf;
	
	public JpaLinkDao(EntityManagerFactory emf){
        this.emf = emf;
    }
	
	@Override
    public Link add( Link link ) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        
        try {
            t.begin();
            
            em.persist( link );

            t.commit();
            
        } finally {
            if (t.isActive()) t.rollback();
            em.close();
        }
        
        return link;
    }	
	
	@Override
	public Link findLink(Link link) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery( "SELECT l FROM Link AS l WHERE l.shortened = :shortened AND l.state =  1" );
        query.setParameter("shortened", link.getShortened());
        try {
        	Link flink = (Link) query.getSingleResult();
        	em.close();
        	return flink;
        }
		catch (Exception e) {
			em.close();
			return null;
		}
	}
	
	@Override
	public List<Link> findAll(User user) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery( "SELECT l FROM Link AS l WHERE l.user = :user AND l.deleted = 0" );
		query.setParameter("user", user);
		List<Link> links = (List<Link>) query.getResultList();
        em.close();
        
        return links;
	}
	
	@Override
	public void update(Link link) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        
        try {
            t.begin();
            em.merge( link );
            t.commit();
            
        } finally {
            if (t.isActive()) t.rollback();
            em.close();
        }
	}
	
	@Override
	public Link findById(Long id) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery( "SELECT l FROM Link AS l WHERE l.id = :id" );
        query.setParameter("id", id);
        try {
        	Link flink = (Link) query.getSingleResult();
        	em.close();
        	return flink;
        }
		catch (Exception e) {
			em.close();
			return null;
		}
	}
	
	@Override
	public void remove(Link link) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        
        try {
            t.begin();
            em.merge( link );
            t.commit();
            
        } finally {
            if (t.isActive()) t.rollback();
            em.close();
        }
	}
	
}
