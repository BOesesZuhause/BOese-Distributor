package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.Device;

public class DeviceDAO implements StandardDAO<Device> {

	@Override
	public Device get(EntityManager em, int id) {
		Device entity = (Device) em.find(Device.class, id);
		return entity;
	}

	@Override
	public Set<Device> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT d FROM Device d");
		List<?> erg = q.getResultList();
		Set<Device> entities = new HashSet<Device>();
		for(Object o : erg){
			entities.add((Device)o);
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
