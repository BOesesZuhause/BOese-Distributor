package de.bo.aid.boese.dao;

import java.util.Set;

import javax.persistence.EntityManager;

public interface StandardDAO<model>{
	
	public default void create(EntityManager em, model entity){	
		em.persist(entity);
	}
	
	public model get(EntityManager em, int id);
	
	public Set<model> getAll(EntityManager em, int id);
	
	public default model merge(EntityManager em, model entity){
		return em.merge(entity);
	}
	
	public default void delete(EntityManager em, model entity){
		em.remove(entity);
	}
	
	public int count(EntityManager em);
	
	

}
