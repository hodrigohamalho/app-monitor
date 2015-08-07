package lab.hack.appmonitor.model;

import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ServerTest {

	@Before
	public void init() {
		server = new Server();
	}
	
	private Server server;
	
	@Test
	public void hasAttributes() {
		assertNull(server.getIp());
		assertNull(server.getDns());
		assertNull(server.getSO());
		assertNull(server.getDistro());
	}
	
	@Test
	public void hasManyApps(){
		List<Application> apps = server.getApps();
		assertNull(apps);
	}

	
}
