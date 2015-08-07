package lab.hack.appmonitor.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resource {

	@Produces
    @PersistenceContext
    private EntityManager em;

}
