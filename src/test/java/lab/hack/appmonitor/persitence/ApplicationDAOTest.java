package lab.hack.appmonitor.persitence;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import lab.hack.appmonitor.model.Application;
import lab.hack.appmonitor.model.Server;
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
                .addClasses(Resource.class, ApplicationDAO.class, Application.class, 
                		    SuperEntity.class, Server.class, GenericDAO.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("app-monitor-test-ds.xml");
    }
	
	@Inject
	ApplicationDAO appDAO;
	
	@Test
	public void save() {
		createApp();
	}

	@Test
	public void update(){
		Application appSaved = createApp();
		Application app = appDAO.findById(appSaved.getId());
		
		String context = "/newContext";
		
		app.setContext(context);
		appDAO.update(app);
		
		app = appDAO.findById(appSaved.getId());
		assertEquals(context, app.getContext());
	}
	
	@Test
	public void findById(){
		Application appSaved = createApp(); // TODO remove dependency of save method
		
		Application app = appDAO.findById(appSaved.getId());
		assertNotNull(app);
		assertEquals(new Long(appSaved.getId()), app.getId());
		assertEquals(appSaved.getContext(), app.getContext());
	}
	
	@Test
	public void findAll(){
		for (int i=0; i<5; i++){
			createApp();
		}
		
		List<Application> apps = appDAO.findAll();
		assertNotNull(apps);
		assertTrue(apps.size() >= 5);
	}
	
//	@Test
//	public void automaticFillCreatedAtOnSave(){
//		Application app = createApp();
//		assertNotNull(app.getCreatedAt());
//	}
//	
//	@Test
//	public void automaticFillUpdatedAtOnUpdate(){
//		Application app = createApp();
//		
//		app.setLanguage("Ruby");
//		appDAO.update(app);
//		assertNotNull(app.getCreatedAt());
//		assertNotNull(app.getUpdatedAt());
//	}
	
	private Application createApp() {
		Application app = new Application();
		app.setContext("/haxored");
		app.setLanguage("Java");
		
		appDAO.save(app);
		assertNotNull(app.getId());
		
		return app;
	}

}
