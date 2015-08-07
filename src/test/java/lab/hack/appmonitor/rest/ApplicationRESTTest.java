package lab.hack.appmonitor.rest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import lab.hack.appmonitor.model.Application;
import lab.hack.appmonitor.model.Server;
import lab.hack.appmonitor.model.SuperEntity;
import lab.hack.appmonitor.persitence.ApplicationDAO;
import lab.hack.appmonitor.persitence.ServerDAO;
import lab.hack.appmonitor.util.Resource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(Arquillian.class)
public class ApplicationRESTTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "app-monitor-test.war")
				.addClasses(Resource.class, Server.class, ServerDAO.class, 
						Application.class, SuperEntity.class, ApplicationDAO.class,
						ApplicationREST.class, RestActivator.class)
						.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
						.addAsWebInfResource("app-monitor-test-ds.xml")
						.addAsLibraries(
								Maven.resolver()
								.resolve("net.sourceforge.htmlunit:htmlunit:2.4", "com.google.code.gson:gson:2.3.1")
								.withTransitivity()
								.asFile());
	}

	@Inject
	ApplicationDAO dao;

	@Before
	public void init(){
		for (int i=0; i < 5; i++){
			Application app = new Application();
			app.setContext("/context");
			app.setLanguage("JAVA");

			dao.save(app);
		}
	}


	@Test
	public void testGetAll() throws IOException{
		URL url = new URL(getURL());
		WebRequestSettings request = new WebRequestSettings(url);
		WebClient client = new WebClient();
		WebResponse response = client.loadWebResponse(request);

		assertEquals(200, response.getStatusCode());
		Gson gson = new Gson();
		Type list = new TypeToken<List<Application>>(){}.getType();
		List<Application> apps = gson.fromJson(response.getContentAsString(), list);
		assertEquals(200, response.getStatusCode());
		assertTrue(apps.size() >= 5);
	}

	@Test
	public void testFindByID() throws IOException{
		URL url = new URL(getURL()+"/1");
		WebRequestSettings request = new WebRequestSettings(url);
		WebClient client = new WebClient();
		WebResponse response = client.loadWebResponse(request);

		Gson gson = new Gson();
		Application app = gson.fromJson(response.getContentAsString(), Application.class);
		assertEquals(200, response.getStatusCode());
		assertEquals(Long.valueOf(1), app.getId());
	}


	public String getURL(){
		return "http://127.0.0.1:8080/app-monitor-test/api/apps";
	}
}
