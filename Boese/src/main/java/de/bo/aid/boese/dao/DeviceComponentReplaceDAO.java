package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.DeviceComponent;

public class DeviceComponentReplaceDAO implements StandardDAO<DeviceComponentReplaceDAO> {

	@Override
	public DeviceComponentReplaceDAO get(EntityManager em, int id) {
		DeviceComponentReplaceDAO entity = (DeviceComponentReplaceDAO) em.find(DeviceComponentReplaceDAO.class, id);
		return entity;
	}

	@Override
	public Set<DeviceComponentReplaceDAO> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT d FROM DeviceComponentReplace d");
		List<?> erg = q.getResultList();
		Set<DeviceComponentReplaceDAO> entities = new HashSet<DeviceComponentReplaceDAO>();
		for(Object o : erg){
			entities.add((DeviceComponentReplaceDAO)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(d) from DeviceComponentReplace d";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
