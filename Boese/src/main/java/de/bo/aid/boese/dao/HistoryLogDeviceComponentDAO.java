package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.GroupZone;
import de.bo.aid.boese.model.HistoryLogDeviceComponent;

public class HistoryLogDeviceComponentDAO implements StandardDAO<HistoryLogDeviceComponent> {

	@Override
	public HistoryLogDeviceComponent get(EntityManager em, int id) {
		HistoryLogDeviceComponent entity = (HistoryLogDeviceComponent) em.find(HistoryLogDeviceComponent.class, id);
		return entity;
	}

	@Override
	public Set<HistoryLogDeviceComponent> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT h FROM HistoryLogDeviceComponent h");
		List<?> erg = q.getResultList();
		Set<HistoryLogDeviceComponent> entities = new HashSet<HistoryLogDeviceComponent>();
		for(Object o : erg){
			entities.add((HistoryLogDeviceComponent)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(h) from HistoryLogDeviceComponent h";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
