package lab.hack.appmonitor.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import lab.hack.appmonitor.model.Application;
import lab.hack.appmonitor.model.Server;
import lab.hack.appmonitor.persitence.ApplicationDAO;
import lab.hack.appmonitor.persitence.ServerDAO;

@Startup
@Singleton
public class PopulateUtil {

	@Inject
	ServerDAO serverDAO;
	@Inject
	ApplicationDAO appDAO;
	
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
			
			Application app = new Application("/hack", "Ruby");
			Application app2 = new Application("/globo", "Java");
			app.setServer(server);
			app2.setServer(server);
			appDAO.save(app);
			appDAO.save(app2);
		}
		System.out.println("Finish....");
	}
	
	
}
