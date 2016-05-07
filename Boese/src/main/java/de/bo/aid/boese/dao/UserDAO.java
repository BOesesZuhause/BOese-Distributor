package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.model.User;

public class UserDAO implements StandardDAO<User>{

	@Override
	public User get(EntityManager em, int id) {
		User entity = (User) em.find(User.class, id);
		return entity;
	}

	@Override
	public Set<User> getAll(EntityManager em, int id) {
		Query q = em.createQuery("SELECT u FROM User u");
		List<?> erg = q.getResultList();
		Set<User> entities = new HashSet<User>();
		for(Object o : erg){
			entities.add((User)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(u) from User u";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
