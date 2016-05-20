package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.Device;
import de.bo.aid.boese.modelJPA.DeviceService;
import de.bo.aid.boese.modelJPA.Service;

public class DeviceServiceDAO implements StandardDAO<DeviceService>{
	
	public DeviceService create(EntityManager em, Device device, Service service){
		DeviceService entity = new DeviceService(device, service);
		em.persist(entity);
		return entity;
	}

	@Override
	public DeviceService get(EntityManager em, int id) {
		DeviceService entity = (DeviceService) em.find(DeviceService.class, id);
		return entity;
	}

	@Override
	public Set<DeviceService> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT d FROM DeviceGroup d");
		List<?> erg = q.getResultList();
		Set<DeviceService> entities = new HashSet<DeviceService>();
		for(Object o : erg){
			entities.add((DeviceService)o);
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
