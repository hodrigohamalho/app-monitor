package lab.hack.appmonitor.model;

public class Server {

	private String ip;
	private String dns;
	private String SO; // Enum ? Other entity... TODO
	private String distro;
	
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
	
}
