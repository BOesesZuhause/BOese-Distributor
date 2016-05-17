package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.Device;
import de.bo.aid.boese.modelJPA.DeviceGroup;
import de.bo.aid.boese.modelJPA.Group;

public class DeviceGroupDAO implements StandardDAO<DeviceGroup>{
	
	public DeviceGroup create(EntityManager em, Device device, Group group, short rights){
		DeviceGroup entity = new DeviceGroup(device, group, rights);
		em.persist(entity);
		return entity;
	}

	@Override
	public DeviceGroup get(EntityManager em, int id) {
		DeviceGroup entity = (DeviceGroup) em.find(DeviceGroup.class, id);
		return entity;
	}

	@Override
	public Set<DeviceGroup> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT d FROM DeviceGroup d");
		List<?> erg = q.getResultList();
		Set<DeviceGroup> entities = new HashSet<DeviceGroup>();
		for(Object o : erg){
			entities.add((DeviceGroup)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(d) from DeviceGroup d";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
