package lab.hack.appmonitor.persitence;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lab.hack.appmonitor.model.Application;

@Stateless
public class ApplicationDAO {

	@Inject
	protected EntityManager em;
	
	public void save(Application app) {
		em.persist(app);
	}
	
	public void update(Application app){
		em.merge(app);
	}
	
	public void remove(Application app){
		em.remove(app);
	}

	public List<Application> findAll() {
		String query = "FROM Application ORDER BY ID DESC";
		return em.createQuery(query).getResultList();
	}

	public Application findById(long id) {
		return em.find(Application.class, id);
	}	
	
}
