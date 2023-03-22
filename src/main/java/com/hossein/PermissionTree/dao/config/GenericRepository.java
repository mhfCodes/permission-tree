package com.hossein.PermissionTree.dao.config;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class GenericRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Session getSession() {
		return this.em.unwrap(Session.class);
	}
	
	public <T> List<T> getAll(String hql, Map<String, Object> params) {
		return this.getAll(hql, params, null);
	}
	
	public <T> T find(String hql, Map<String, Object> params) {
		return this.find(hql, params, null);
	}
	
	public <T> List<T> getAll(String hql, Map<String, Object> params, Class<T> transformerClass) {
		
		Session session = this.getSession();
		Query<T> query;
		
		if (transformerClass != null) {
			query = session.createQuery(hql).setResultListTransformer(Transformers.aliasToBean(transformerClass));
		} else {
			query = session.createQuery(hql);
		}
		
		params.keySet().forEach(k -> query.setParameter(k, params.get(k)));
		
		return query.list();
	}
	
	public <T> T find(String hql, Map<String, Object> params, Class<T> transformerClass) {

		Session session = this.getSession();
		Query<T> query;
		
		if (transformerClass != null) {
			query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(transformerClass));
		} else {
			query = session.createQuery(hql);
		}
		
		params.keySet().forEach(k -> query.setParameter(k, params.get(k)));
		
		return query.getSingleResult();
	}	

}
