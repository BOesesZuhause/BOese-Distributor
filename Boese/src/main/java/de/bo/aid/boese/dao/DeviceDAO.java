package de.bo.aid.boese.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.Device;

public class DeviceDAO implements StandardDAO<Device> {
	
	public Device create(EntityManager em, String alias, String serialNumber){
		Device entity = new Device(alias, serialNumber);
		em.persist(entity);
		return entity;
	}

	@Override
	public Device get(EntityManager em, int id) {
		Device entity = (Device) em.find(Device.class, id);
		return entity;
	}

	@Override
	public Set<Device> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT d FROM Device d");
		List<?> erg = q.getResultList();
		Set<Device> entities = new HashSet<Device>();
		for(Object o : erg){
			entities.add((Device)o);
		}
		return entities;
	}

	public Map<Integer, Device> getAllAsMap(EntityManager em) {
		Query q = em.createQuery("SELECT d FROM Device d");
		List<?> erg = q.getResultList();
		Map<Integer, Device> entities = new HashMap<Integer, Device>();
		for(Object o : erg){
			Device d = (Device)o;
			entities.put(d.getDeId(), d);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(d) from Device d";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
