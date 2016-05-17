package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

public interface StandardDAO<model>{
	
	public default void create(EntityManager em, model entity){	
		em.persist(entity);
	}

	public model get(EntityManager em, int id);

	public default model get(EntityManager em, int id, Class<model> c){
		model entity = (model) em.find(c, id);
		return entity;
	}

	public Set<model> getAll(EntityManager em);

	public default Set<model> getAll(EntityManager em, String tabName){
		Query q = em.createQuery("SELECT a FROM " + tabName + " a");
		List<?> erg = q.getResultList();
		Set<model> entities = new HashSet<model>();
		for(Object o : erg){
			entities.add((model)o);
		}
		return entities;
	}
	
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
