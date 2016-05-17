package de.bo.aid.boese.dao;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.DeviceComponent;
import de.bo.aid.boese.modelJPA.LogDeviceComponent;

public class LogDeviceComponentDAO implements StandardDAO<LogDeviceComponent>{
	
	public LogDeviceComponent create(EntityManager em, DeviceComponent deco, Date timestamp, BigDecimal value){
		LogDeviceComponent entity = new LogDeviceComponent(deco, value, timestamp);
		em.persist(entity);
		return entity;
	}

	@Override
	public LogDeviceComponent get(EntityManager em, int id) {
		LogDeviceComponent entity = (LogDeviceComponent) em.find(LogDeviceComponent.class, id);
		return entity;
	}

	@Override
	public Set<LogDeviceComponent> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT l FROM LogDeviceComponent l");
		List<?> erg = q.getResultList();
		Set<LogDeviceComponent> entities = new HashSet<LogDeviceComponent>();
		for(Object o : erg){
			entities.add((LogDeviceComponent)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(l) from LogDeviceComponent l";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
