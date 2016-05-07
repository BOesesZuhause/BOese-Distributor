package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.HistoryLogConnector;

public class HistoryLogConnectorDAO implements StandardDAO<HistoryLogConnector>{

	@Override
	public HistoryLogConnector get(EntityManager em, int id) {
		HistoryLogConnector entity = (HistoryLogConnector) em.find(HistoryLogConnector.class, id);
		return entity;
	}

	@Override
	public Set<HistoryLogConnector> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT h FROM HistoryLogConnector h");
		List<?> erg = q.getResultList();
		Set<HistoryLogConnector> entities = new HashSet<HistoryLogConnector>();
		for(Object o : erg){
			entities.add((HistoryLogConnector)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(h) from HistoryLogConnector h";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
