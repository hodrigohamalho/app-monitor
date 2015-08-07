package lab.hack.appmonitor.persitence;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lab.hack.appmonitor.model.Server;

@Stateless
public class ServerDAO {
	
	@Inject
	protected EntityManager em;
	
	public void save(Server server) {
		em.persist(server);
	}
	
	public void update(Server server){
		em.merge(server);
	}
	
	public void remove(Server server){
		em.remove(server);
	}

	public List<Server> findAll() {
		String query = "FROM Server ORDER BY ID DESC";
		return em.createQuery(query).getResultList();
	}

	public Server findById(long id) {
		return em.find(Server.class, id);
	}	
}
