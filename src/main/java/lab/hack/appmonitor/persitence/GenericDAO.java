package lab.hack.appmonitor.persitence;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lab.hack.appmonitor.model.SuperEntity;

@Stateless
public class GenericDAO<T extends SuperEntity> {

	@Inject
	protected EntityManager em;
	
	public void save(T obj) {
		em.persist(obj);
	}
	
	public void update(T obj){
		em.merge(obj);
	}
	
	public void remove(Long id){
		T obj = findById(id);
		em.remove(obj);
	}

	public List<T> findAll() {
		String query = "FROM " + getParameterizedClass().getName() + " ORDER BY ID DESC";
		return em.createQuery(query).getResultList();
	}

	public T findById(long id) {
		return em.find(getParameterizedClass(), id);
	}	
	
	private Class<T> getParameterizedClass() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
}
