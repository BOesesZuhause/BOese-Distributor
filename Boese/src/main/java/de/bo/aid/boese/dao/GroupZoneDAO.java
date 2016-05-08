package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.Group;
import de.bo.aid.boese.model.GroupZone;
import de.bo.aid.boese.model.Zone;

public class GroupZoneDAO implements StandardDAO<GroupZone>{
	
	public GroupZone create(EntityManager em, Group group, Zone zone, short rights){
		GroupZone entity = new GroupZone(group, zone, rights);
		em.persist(entity);
		return entity;
	}

	@Override
	public GroupZone get(EntityManager em, int id) {
		GroupZone entity = (GroupZone) em.find(GroupZone.class, id);
		return entity;
	}

	@Override
	public Set<GroupZone> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT g FROM GroupZone g");
		List<?> erg = q.getResultList();
		Set<GroupZone> entities = new HashSet<GroupZone>();
		for(Object o : erg){
			entities.add((GroupZone)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(g) from GroupZone g";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
