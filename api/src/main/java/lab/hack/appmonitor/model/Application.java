package lab.hack.appmonitor.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="appJacksonId")
public class Application extends SuperEntity{
	
	public Application() {}
	
	public Application(String context, String language) {
		this.context = context;
		this.language = language;
	}

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
