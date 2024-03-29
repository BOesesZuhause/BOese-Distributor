package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.Rule;

public class RuleDAO implements StandardDAO<Rule>{
	
	public Rule create(EntityManager em, String permissions, String conditions, String actions){
		Rule entity = new Rule(permissions, conditions, actions);
		em.persist(entity);
		return entity;
	}

	@Override
	public Rule get(EntityManager em, int id) {
		Rule entity = (Rule) em.find(Rule.class, id);
		return entity;
	}

	@Override
	public Set<Rule> getAll(EntityManager em, int id) {
		Query q = em.createQuery("select r from Rule r");
		List<?> erg = q.getResultList();
		Set<Rule> entities = new HashSet<Rule>();
		for(Object o : erg){
			entities.add((Rule)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(r) from Rule r";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
