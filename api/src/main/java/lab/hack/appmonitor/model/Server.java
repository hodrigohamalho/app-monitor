package lab.hack.appmonitor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="serverJacksonId")
public class Server extends SuperEntity{

	public Server() {}
	
	public Server(String ip, String dns, String sO, String distro) {
		this.ip = ip;
		this.dns = dns;
		this.SO = sO;
		this.distro = distro;
	}
	
	private String ip;
	private String dns;
	private String SO; // Enum ? Other entity... TODO
	private String distro;
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="server", fetch=FetchType.EAGER)
	private List<Application> apps;
	
	public String getIp() {
		return ip;
	}
	public String getDns() {
		return dns;
	}
	public String getSO() {
		return SO;
	}
	public String getDistro() {
		return distro;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public void setSO(String sO) {
		SO = sO;
	}
	public void setDistro(String distro) {
		this.distro = distro;
	}
	
	public List<Application> getApps() {
		return apps;
	}
	public void setApps(List<Application> apps) {
		this.apps = apps;
	}
}
