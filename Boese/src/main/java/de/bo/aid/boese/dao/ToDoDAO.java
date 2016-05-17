package de.bo.aid.boese.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.RepeatRule;
import de.bo.aid.boese.modelJPA.ToDo;

public class ToDoDAO implements StandardDAO<ToDo>{
	
	public ToDo create(EntityManager em, Date date){
		ToDo entity = new ToDo(date);
		em.persist(entity);
		return entity;
	}
	
	public ToDo create(EntityManager em, Date date, boolean active, RepeatRule rr){
		ToDo entity = new ToDo(date, active, rr);
		em.persist(entity);
		return entity;
	}

	@Override
	public ToDo get(EntityManager em, int id) {
		ToDo entity = (ToDo) em.find(ToDo.class, id);
		return entity;
	}

	@Override
	public Set<ToDo> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT t FROM ToDo t");
		List<?> erg = q.getResultList();
		Set<ToDo> entities = new HashSet<ToDo>();
		for(Object o : erg){
			entities.add((ToDo)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(t) from ToDo t";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
