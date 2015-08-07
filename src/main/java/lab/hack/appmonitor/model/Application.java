package lab.hack.appmonitor.model;

import javax.persistence.Entity;

@Entity
public class Application extends SuperEntity{

	private String context;
	private String language;
	
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
		
}
