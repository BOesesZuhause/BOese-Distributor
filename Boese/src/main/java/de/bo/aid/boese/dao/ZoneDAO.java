package de.bo.aid.boese.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.Zone;

public class ZoneDAO implements StandardDAO<Zone>{
	
	public Zone create(EntityManager em, String name, Zone superZone){
		Zone entity = new Zone(name, superZone);
		em.persist(entity);
		return entity;
	}

	@Override
	public Zone get(EntityManager em, int id) {
		Zone entity = (Zone) em.find(Zone.class, id);
		return entity;
	}

	@Override
	public Set<Zone> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT z FROM Zone z");
		List<?> erg = q.getResultList();
		Set<Zone> entities = new HashSet<Zone>();
		for(Object o : erg){
			entities.add((Zone)o);
		}
		return entities;
	}
	
	public Map<Integer, Zone> getAllAsMap(EntityManager em) {
		Query q = em.createQuery("SELECT z FROM Zone z");
		List<?> erg = q.getResultList();
		Map<Integer, Zone> entities = new HashMap<Integer, Zone>();
		for(Object o : erg){
			Zone zone = (Zone)o; 
			entities.put(zone.getZoId(), zone);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(z) from Zone z";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
