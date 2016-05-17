package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.Group;
import de.bo.aid.boese.modelJPA.GroupUser;
import de.bo.aid.boese.modelJPA.User;

public class GroupUserDAO implements StandardDAO<GroupUser>{
	
	public GroupUser create(EntityManager em, Group group, User user, short position){
		GroupUser entity = new GroupUser(group, user, position);
		em.persist(entity);
		return entity;
	}

	@Override
	public GroupUser get(EntityManager em, int id) {
		GroupUser entity = (GroupUser) em.find(GroupUser.class, id);
		return entity;
	}

	@Override
	public Set<GroupUser> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT g FROM GroupUser g");
		List<?> erg = q.getResultList();
		Set<GroupUser> entities = new HashSet<GroupUser>();
		for(Object o : erg){
			entities.add((GroupUser)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(g) from GroupUser g";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}
	
}
