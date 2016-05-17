package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.Group;

public class GroupDAO implements StandardDAO<Group>{
	
	public Group create(EntityManager em, String name){
		Group entity = new Group(name);
		em.persist(entity);
		return entity;
	}

	@Override
	public Group get(EntityManager em, int id) {
		Group entity = (Group) em.find(Group.class, id);
		return entity;
	}

	@Override
	public Set<Group> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT g FROM Group g");
		List<?> erg = q.getResultList();
		Set<Group> entities = new HashSet<Group>();
		for(Object o : erg){
			entities.add((Group)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(g) from Group g";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
