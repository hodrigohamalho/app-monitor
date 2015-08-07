package lab.hack.appmonitor.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Application extends SuperEntity{

	private String context;
	private String language;
	@ManyToOne
	@JoinColumn(name="server_fk")
	private Server server;
	
	public String getContext() {
		return context;
	}
	public String getLanguage() {
		return language;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
		
}
