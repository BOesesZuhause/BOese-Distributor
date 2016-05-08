package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.Unit;

public class UnitDAO implements StandardDAO<Unit>{
	
	public Unit create(EntityManager em, String name, String symbol){
		Unit entity = new Unit(name, symbol);
		em.persist(entity);
		return entity;
	}

	@Override
	public Unit get(EntityManager em, int id) {
		Unit entity = (Unit) em.find(Unit.class, id);
		return entity;
	}

	@Override
	public Set<Unit> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT u FROM  Unit u");
		List<?> erg = q.getResultList();
		Set<Unit> entities = new HashSet<Unit>();
		for(Object o : erg){
			entities.add((Unit)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(u) from Unit u";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
