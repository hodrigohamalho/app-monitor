package lab.hack.appmonitor.persitence;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lab.hack.appmonitor.model.Application;

@Stateless
public class ApplicationDAO {

	@Inject
	protected EntityManager em;
	
	public void salvar(Application app) {
		em.persist(app);
	}
	
}
