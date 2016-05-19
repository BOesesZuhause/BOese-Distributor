package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.DeviceComponent;
import de.bo.aid.boese.modelJPA.DeviceComponentRule;
import de.bo.aid.boese.modelJPA.Rule;

public class DeviceComponentRuleDAO implements StandardDAO<DeviceComponentRule>{
	
	public DeviceComponentRule create(EntityManager em, DeviceComponent devicecomponent, Rule rule){
		DeviceComponentRule entity = new DeviceComponentRule(devicecomponent, rule);
		em.persist(entity);
		return entity;
	}

	@Override
	public DeviceComponentRule get(EntityManager em, int id) {
		DeviceComponentRule entity = (DeviceComponentRule) em.find(DeviceComponentRule.class, id);
		return entity;
	}

	@Override
	public Set<DeviceComponentRule> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT d FROM DeviceComponentRule d");
		List<?> erg = q.getResultList();
		Set<DeviceComponentRule> entities = new HashSet<DeviceComponentRule>();
		for(Object o : erg){
			entities.add((DeviceComponentRule)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(d) from DeviceComponentRule d";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
