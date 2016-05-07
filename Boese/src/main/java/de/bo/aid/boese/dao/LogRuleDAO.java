package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.LogRule;

public class LogRuleDAO implements StandardDAO<LogRule>{

	@Override
	public LogRule get(EntityManager em, int id) {
		LogRule entity = (LogRule) em.find(LogRule.class, id);
		return entity;
	}

	@Override
	public Set<LogRule> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT l FROM LogRule l");
		List<?> erg = q.getResultList();
		Set<LogRule> entities = new HashSet<LogRule>();
		for(Object o : erg){
			entities.add((LogRule)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(l) from LogRule l";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
