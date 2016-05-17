package de.bo.aid.boese.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.bo.aid.boese.modelJPA.Connector;

public class ConnectorDAO implements StandardDAO<Connector> {
	
	public Connector create(EntityManager em, String name, String password, boolean userConnector){
		Connector entity = new Connector(name, password, userConnector);
		em.persist(entity);
		return entity;
	}

	@Override
	public Connector get(EntityManager em, int id) {
		Connector entity = (Connector) em.find(Connector.class, id);
		return entity;
	}

	@Override
	public Set<Connector> getAll(EntityManager em) {
		Query q = em.createQuery("SELECT c FROM Connector c");
		List<?> erg = q.getResultList();
		Set<Connector> entities = new HashSet<Connector>();
		for(Object o : erg){
			entities.add((Connector)o);
		}
		return entities;
	}

	@Override
	public long count(EntityManager em) {
		String query = "select count(c) from Connector c";
		Object first = em.createQuery(query).getResultList().get(0);
		if (first instanceof Long) {
			return ((Long) first).longValue();
		}
		throw new RuntimeException("Unexpected result for count query: " + first);
	}

}
