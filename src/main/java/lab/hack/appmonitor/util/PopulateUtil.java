package lab.hack.appmonitor.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import lab.hack.appmonitor.model.Server;
import lab.hack.appmonitor.persitence.ServerDAO;

@Startup
@Singleton
public class PopulateUtil {

	@Inject
	ServerDAO serverDAO;
	
	@PostConstruct
	public void populateServer(){
		System.out.println("Populating server....");
		for(int i=0; i<5; i++){
			Server server = new Server();
			server.setIp("172.168.0."+i);
			server.setDns("haxor.com"+i);
			server.setDistro("Fedora "+i);
			server.setSO("Linux "+i);
			
			serverDAO.save(server);
			System.out.println("Server "+server.getDns()+" saved!");
		}
		System.out.println("Finish....");
	}
	
	
}
