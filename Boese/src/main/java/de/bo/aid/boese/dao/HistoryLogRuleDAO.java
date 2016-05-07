package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.HistoryLogDeviceComponent;

public class HistoryLogRuleDAO implements StandardDAO<HistoryLogRuleDAO>{

	@Override
	public HistoryLogRuleDAO get(EntityManager em, int id) {
		HistoryLogRuleDAO entity = (HistoryLogRuleDAO) em.find(HistoryLogRuleDAO.class, id);
		return entity;
	}

	@Override
	public Set<HistoryLogRuleDAO> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT h FROM HistoryLogRuleDAO h");
		List<?> erg = q.getResultList();
		Set<HistoryLogRuleDAO> entities = new HashSet<HistoryLogRuleDAO>();
		for(Object o : erg){
			entities.add((HistoryLogRuleDAO)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(h) from HistoryLogRuleDAO h";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
