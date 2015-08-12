package lab.hack.appmonitor.util;

import java.util.logging.Logger;

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
	private ServerDAO serverDAO;
	@Inject
	private ApplicationDAO appDAO;
	@Inject
	private Logger log;
	
	@PostConstruct
	public void populateServer(){
		log.info("Loading some initial data on database...");
		Server server = new Server("172.168.0.1","UFC.com","Linux", "Red Hat 7.2");
		serverDAO.save(server);
		
		saveApp(new Application("/tv", "Python"), server);
		saveApp(new Application("/live", "Ruby"), server);
		
		server = new Server("172.168.0.2","netflix.com","Linux", "Debian 8.1");
		serverDAO.save(server);
		
		saveApp(new Application("/tv", "Python"), server);
		saveApp(new Application("/kids", "Python"), server);
		saveApp(new Application("/series", "Ruby"), server);
		saveApp(new Application("/api", "NodeJS"), server);
		
		server = new Server("172.168.0.3","google.com","Linux", "Suse 11");
		serverDAO.save(server);
		
		saveApp(new Application("/inbox", "Python"), server);
		saveApp(new Application("/calendar", "Java"), server);
		saveApp(new Application("/gplus", "Java"), server);
		
		log.info("Data loaded succesfully....");
	}

	private void saveApp(Application app, Server server) {
		app.setServer(server);
		appDAO.save(app);
	}
	
	
}
