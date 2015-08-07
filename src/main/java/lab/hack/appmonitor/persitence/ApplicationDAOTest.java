package lab.hack.appmonitor.persitence;

import static org.junit.Assert.*;

import javax.inject.Inject;

import lab.hack.appmonitor.model.Application;
import lab.hack.appmonitor.model.SuperEntity;
import lab.hack.appmonitor.util.Resource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ApplicationDAOTest {

	@Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "app-monitor-test.war")
                .addClasses(Resource.class, ApplicationDAO.class, Application.class, SuperEntity.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("app-monitor-test-ds.xml");
    }
	
	@Inject
	ApplicationDAO appDAO;
	
	@Test
	public void save() {
		Application app = new Application();
		app.setContext("/haxored");
		app.setLanguage("Java");
		
		appDAO.salvar(app);
		assertNotNull(app.getId());
	}

}
