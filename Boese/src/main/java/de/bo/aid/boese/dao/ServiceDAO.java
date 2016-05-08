package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.Service;

public class ServiceDAO implements StandardDAO<Service>{
	
	public Service create(EntityManager em, String description){
		Service entity = new Service(description);
		em.persist(entity);
		return entity;
	}

	@Override
	public Service get(EntityManager em, int id) {
		Service entity = (Service) em.find(Service.class, id);
		return entity;
	}

	@Override
	public Set<Service> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT s FROM Service s");
		List<?> erg = q.getResultList();
		Set<Service> entities = new HashSet<Service>();
		for(Object o : erg){
			entities.add((Service)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(s) from Service s";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
