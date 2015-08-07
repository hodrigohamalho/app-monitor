package lab.hack.appmonitor.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ServerTest {

	@Before
	public void init() {
		server = new Server();
	}
	
	private Server server;
	
	@Test
	public void checkAttributesExistence() {
		assertNull(server.getIp());
		assertNull(server.getDns());
		assertNull(server.getSO());
		assertNull(server.getDistro());
	}
	
}
