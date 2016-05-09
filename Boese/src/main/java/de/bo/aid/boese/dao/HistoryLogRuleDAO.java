package de.bo.aid.boese.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.HistoryLogRule;
import de.bo.aid.boese.modelJPA.Rule;

public class HistoryLogRuleDAO implements StandardDAO<HistoryLogRule>{
	
	public HistoryLogRule create(EntityManager em, Rule rule, Date timestamp){
		HistoryLogRule entity = new HistoryLogRule(rule, timestamp);
		em.persist(entity);
		return entity;
	}

	@Override
	public HistoryLogRule get(EntityManager em, int id) {
		HistoryLogRule entity = (HistoryLogRule) em.find(HistoryLogRule.class, id);
		return entity;
	}

	@Override
	public Set<HistoryLogRule> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT h FROM HistoryLogRuleDAO h");
		List<?> erg = q.getResultList();
		Set<HistoryLogRule> entities = new HashSet<HistoryLogRule>();
		for(Object o : erg){
			entities.add((HistoryLogRule)o);
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
