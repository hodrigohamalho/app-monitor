package lab.hack.appmonitor.persitence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class ServerDAOTest {

	@Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "app-monitor-test.war")
                .addClasses(Resource.class, GenericDAO.class,Server.class, ServerDAO.class, 
                			Application.class, SuperEntity.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("app-monitor-test-ds.xml");
    }
	
	@Inject
	ServerDAO serverDAO;
	
	@Test
	public void save() {
		createServer();
	}

	@Test
	public void update(){
		Server serverSaved = createServer();
		Server server = serverDAO.findById(serverSaved.getId());
		
		String dns = "newDNS.com";
		
		server.setDns(dns);
		serverDAO.update(server);
		
		server = serverDAO.findById(serverSaved.getId());
		assertEquals(dns, server.getDns());
	}
	
	@Test
	public void findById(){
		Server serverSaved = createServer(); // TODO remove dependency of save method
		
		Server app = serverDAO.findById(serverSaved.getId());
		assertNotNull(app);
		assertEquals(new Long(serverSaved.getId()), app.getId());
		assertEquals(serverSaved.getDns(), app.getDns());
	}
	
	@Test
	public void findAll(){
		for (int i=0; i<5; i++){
			createServer();
		}
		
		List<Server> servers = serverDAO.findAll();
		assertNotNull(servers);
		assertEquals(5, servers.size());
	}
	
	private Server createServer() {
		Server server = new Server();
		server.setIp("172.168.0.1");
		server.setDns("haxor.com");
		server.setDistro("Fedora");
		server.setSO("Linux");
		
		serverDAO.save(server);
		assertNotNull(server.getId());
		
		return server;
	}

}
