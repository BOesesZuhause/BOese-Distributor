package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DeviceComponentRuleDAO implements StandardDAO<DeviceComponentRuleDAO>{

	@Override
	public DeviceComponentRuleDAO get(EntityManager em, int id) {
		DeviceComponentRuleDAO entity = (DeviceComponentRuleDAO) em.find(DeviceComponentRuleDAO.class, id);
		return entity;
	}

	@Override
	public Set<DeviceComponentRuleDAO> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT d FROM DeviceComponentRule d");
		List<?> erg = q.getResultList();
		Set<DeviceComponentRuleDAO> entities = new HashSet<DeviceComponentRuleDAO>();
		for(Object o : erg){
			entities.add((DeviceComponentRuleDAO)o);
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
