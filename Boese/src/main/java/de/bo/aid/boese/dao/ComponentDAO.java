package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.Component;

public class ComponentDAO implements StandardDAO<Component> {
	
	public Component create(EntityManager em, String name, boolean actor){
		Component entity = new Component(name, actor);
		em.persist(entity);
		return entity;
	}

	@Override
	public Component get(EntityManager em, int id) {
		Component entity = (Component) em.find(Component.class, id);
		return entity;
	}

	@Override
	public Set<Component> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT c FROM Component c");
		List<?> erg = q.getResultList();
		Set<Component> entities = new HashSet<Component>();
		for(Object o : erg){
			entities.add((Component)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(c) from Component c";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
