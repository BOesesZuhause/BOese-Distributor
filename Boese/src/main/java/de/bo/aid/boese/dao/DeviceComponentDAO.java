package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.DeviceComponent;

public class DeviceComponentDAO implements StandardDAO<DeviceComponent>{
	
	public DeviceComponent create(EntityManager em, String description, double minValue, double maxValue, double logDifference, boolean loggen){
//		DeviceComponent entity = new DeviceComponent(description, minValue, maxValue, logDifference, loggen);
		DeviceComponent entity = new DeviceComponent(description, minValue, maxValue, 0.0, loggen);
		em.persist(entity);
		return entity;
	}

	@Override
	public DeviceComponent get(EntityManager em, int id) {
		DeviceComponent entity = (DeviceComponent) em.find(DeviceComponent.class, id);
		return entity;
	}

	@Override
	public Set<DeviceComponent> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT d FROM DeviceComponent d");
		List<?> erg = q.getResultList();
		Set<DeviceComponent> entities = new HashSet<DeviceComponent>();
		for(Object o : erg){
			entities.add((DeviceComponent)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(d) from DeviceComponent d";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
