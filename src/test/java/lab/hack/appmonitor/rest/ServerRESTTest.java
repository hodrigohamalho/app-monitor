package lab.hack.appmonitor.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class ServerRESTTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "app-monitor-test.war")
				.addClasses(Resource.class, Server.class, ServerDAO.class, 
						Application.class, SuperEntity.class, ApplicationDAO.class,
						ServerREST.class, GenericDAO.class ,RestActivator.class, TestUtil.class)
						.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
						.addAsWebInfResource("app-monitor-test-ds.xml")
						.addAsLibraries(
								Maven.resolver()
								.resolve("net.sourceforge.htmlunit:htmlunit:2.4", 
										 "com.google.code.gson:gson:2.3.1",
										 "com.fasterxml.jackson.datatype:jackson-datatype-hibernate4:2.6.0",
										 "org.jboss.resteasy:resteasy-jackson-provider:3.0.11.Final")
								.withTransitivity()
								.asFile());
		/**
		 * <dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId></artifactId>
			<version>2.6.0</version>
		</dependency>
		 */
	}
	
	@Inject
	ServerDAO dao;
	List<Server> servers = new ArrayList<Server>();
	
	@Before
	public void setup(){
		populate();
	}

	public void populate(){
		for (int i=0; i < 5; i++){
			Server server = new Server();
			server.setDns("app-monitor.com");
			server.setIp("172.168.0.16");
			server.setDistro("Fedora");
			server.setSO("Linux");
			
			dao.save(server);
			servers.add(server);
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
		Type list = new TypeToken<List<Server>>(){}.getType();
		List<Server> svs = gson.fromJson(response.getContentAsString(), list);
		assertEquals(200, response.getStatusCode());
		assertEquals(servers.size(), svs.size());
	}

	@Test
	public void testFindByID() throws IOException{
		URL url = new URL(getURL()+"/1");
		WebRequestSettings request = new WebRequestSettings(url);
		WebClient client = new WebClient();
		WebResponse response = client.loadWebResponse(request);

		System.out.println(response.getContentAsString());
		Gson gson = new Gson();
		Server server = gson.fromJson(response.getContentAsString(), Server.class);
		assertEquals(200, response.getStatusCode());
		assertEquals(Long.valueOf(1), server.getId());
	}
	
	@Test
	public void testSave() throws IOException{
		URL url = new URL(getURL());
		WebRequestSettings request = new WebRequestSettings(url);
		request.setHttpMethod(HttpMethod.POST);
		
		Server server = new Server("172.29.0.254", "rramalho.com", "Linux", "Ubuntu");
		TestUtil.createRequestBody(request, server);
		
		WebClient client = new WebClient();
		WebResponse response = client.loadWebResponse(request);

		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
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
		Server server = servers.get(0);
		server.setDns("newdns.com");
		
		URL url = new URL(getURL());
		WebRequestSettings request = new WebRequestSettings(url);
		request.setHttpMethod(HttpMethod.PUT);
		
		TestUtil.createRequestBody(request, server);
		
		WebClient client = new WebClient();
		WebResponse response = client.loadWebResponse(request);
		
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		assertEquals("newdns.com", dao.findById(server.getId()).getDns());
	}
	
	public String getURL(){
		return "http://127.0.0.1:8080/app-monitor-test/api/servers";
	}
}
