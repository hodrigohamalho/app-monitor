package lab.hack.appmonitor.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lab.hack.appmonitor.model.Application;
import lab.hack.appmonitor.model.Server;
import lab.hack.appmonitor.model.SuperEntity;
import lab.hack.appmonitor.persitence.ApplicationDAO;
import lab.hack.appmonitor.persitence.GenericDAO;
import lab.hack.appmonitor.persitence.ServerDAO;
import lab.hack.appmonitor.util.Resource;

import org.apache.commons.httpclient.HttpStatus;
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

import com.gargoylesoftware.htmlunit.HttpMethod;
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
						ApplicationREST.class, RestActivator.class, TestUtil.class, GenericDAO.class)
						.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
						.addAsWebInfResource("app-monitor-test-ds.xml")
						.addAsLibraries(
								Maven.resolver()
								.resolve("net.sourceforge.htmlunit:htmlunit:2.4", 
										 "com.google.code.gson:gson:2.3.1",
										 "org.jboss.resteasy:resteasy-jackson-provider:3.0.11.Final")
								.withTransitivity()
								.asFile());
	}
	
	List<Application> apps = new ArrayList<Application>();
	
	@Inject
	ApplicationDAO dao;

	@Before
	public void setup(){
		populate();
	}
	
	private void populate(){
		for (int i=0; i < 5; i++){
			Application app = new Application();
			app.setContext("/context"+i);
			app.setLanguage("JAVA");

			dao.save(app);
			apps.add(app);
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
	
	@Test
	public void testSave() throws IOException{
		int appsCount = apps.size();
		URL url = new URL(getURL());
		WebRequestSettings request = new WebRequestSettings(url);
		request.setHttpMethod(HttpMethod.POST);
		
		Application app = new Application("/newContext", "Ruby");
		TestUtil.createRequestBody(request, app);
		
		WebClient client = new WebClient();
		WebResponse response = client.loadWebResponse(request);

		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		int appsCountAfterSave = dao.findAll().size();
		assertEquals(appsCount+1,appsCountAfterSave);
	}


	@Test
	public void testRemove() throws IOException{
		Long id = new Long(1);
		
		URL url = new URL(getURL()+"/"+id);
		WebRequestSettings request = new WebRequestSettings(url);
		request.setHttpMethod(HttpMethod.DELETE);
		
		WebClient client = new WebClient();
		WebResponse response = client.loadWebResponse(request);
		
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		assertNull(dao.findById(id));
	}
	
	@Test
	public void testUpdate() throws IOException{
		Application app = apps.get(0);
		app.setContext("/newContext");
		
		URL url = new URL(getURL());
		WebRequestSettings request = new WebRequestSettings(url);
		request.setHttpMethod(HttpMethod.PUT);
		
		TestUtil.createRequestBody(request, app);
		
		WebClient client = new WebClient();
		WebResponse response = client.loadWebResponse(request);
		
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		assertEquals("/newContext", dao.findById(app.getId()).getContext());
	}


	public String getURL(){
		return "http://127.0.0.1:8080/app-monitor-test/api/apps";
	}
}
