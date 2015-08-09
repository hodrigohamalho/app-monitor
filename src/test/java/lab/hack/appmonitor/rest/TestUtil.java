package lab.hack.appmonitor.rest;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class TestUtil {

	public static String toJson(Object object) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
}
