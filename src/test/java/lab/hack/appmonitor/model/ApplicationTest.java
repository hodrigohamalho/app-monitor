package lab.hack.appmonitor.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ApplicationTest {

	@Before
	public void init() {
		app = new Application();
	}
	
	private Application app;
	
	@Test
	public void checkAttributesExistence() {
		assertNull(app.getContext());
		assertNull(app.getLanguage());
	}

}
