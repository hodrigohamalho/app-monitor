package lab.hack.appmonitor.rest;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.gargoylesoftware.htmlunit.WebRequestSettings;

public class TestUtil {

	public static String toJson(Object object) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
	public static void createRequestBody(WebRequestSettings request, Object obj) throws IOException {
		request.setCharset("UTF-8");
		request.addAdditionalHeader("content-type", "application/json;charset=UTF-8");
		
		request.setRequestBody(TestUtil.toJson(obj));
	}
	
}
