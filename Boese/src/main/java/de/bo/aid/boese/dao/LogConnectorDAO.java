package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.LogConnector;

public class LogConnectorDAO implements StandardDAO<LogConnector>{

	@Override
	public LogConnector get(EntityManager em, int id) {
		LogConnector entity = (LogConnector) em.find(LogConnector.class, id);
		return entity;
	}

	@Override
	public Set<LogConnector> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT l FROM LogConnector l");
		List<?> erg = q.getResultList();
		Set<LogConnector> entities = new HashSet<LogConnector>();
		for(Object o : erg){
			entities.add((LogConnector)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(l) from LogConnector l";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
