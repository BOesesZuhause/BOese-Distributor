package de.bo.aid.boese.dao;

import java.util.Set;

import javax.persistence.*;

public interface StandardDAO<model>{
	
	public default void create(EntityManager em, model entity){	
		em.persist(entity);
	}

	public model get(EntityManager em, int id);

	public Set<model> getAll(EntityManager em);
	
	public default model merge(EntityManager em, model entity){
		return em.merge(entity);
	}
	
	public default void delete(EntityManager em, model entity){
		em.remove(entity);
	}
	
	public long count(EntityManager em);
	
	public default long count(EntityManager em, String tabName){
		String query = "select count(a) from " + tabName + " a";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
