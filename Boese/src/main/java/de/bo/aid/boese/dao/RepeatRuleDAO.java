package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.RepeatRule;

public class RepeatRuleDAO implements StandardDAO<RepeatRule>{

	@Override
	public RepeatRule get(EntityManager em, int id) {
		RepeatRule entity = (RepeatRule) em.find(RepeatRule.class, id);
		return entity;
	}

	@Override
	public Set<RepeatRule> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT r FROM RepeatRule r");
		List<?> erg = q.getResultList();
		Set<RepeatRule> entities = new HashSet<RepeatRule>();
		for(Object o : erg){
			entities.add((RepeatRule)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(r) from RepeatRule r";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
