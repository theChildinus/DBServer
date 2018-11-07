package bupt.intt.wsmonitor.servicemonitor.domain;

import java.io.Serializable;

public class MemDBConfig extends ConfigType {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2479184826254859924L;
	public static final String SECONDBJECTCLASS = "MemdbConfig";
    private String initHostname;
    private String initIP;
    private String initPort;
    private String initScriptPath;
    private String maxConnection;
    private String memDbUrl;
    private String minConnection;
	public void setInitHostname(String initHostname) {
		this.initHostname = initHostname;
	}
	public String getInitHostname() {
		return initHostname;
	}
	public void setInitIP(String initIP) {
		this.initIP = initIP;
	}
	public String getInitIP() {
		return initIP;
	}
	public void setInitPort(String initPort) {
		this.initPort = initPort;
	}
	public String getInitPort() {
		return initPort;
	}
	public void setInitScriptPath(String initScriptPath) {
		this.initScriptPath = initScriptPath;
	}
	public String getInitScriptPath() {
		return initScriptPath;
	}
	/**
	 * @param maxConnection the maxConnection to set
	 */
	public void setMaxConnection(String maxConnection) {
		this.maxConnection = maxConnection;
	}
	/**
	 * @return the maxConnection
	 */
	public String getMaxConnection() {
		return maxConnection;
	}
	public void setMemDbUrl(String memDbUrl) {
		this.memDbUrl = memDbUrl;
	}
	public String getMemDbUrl() {
		return memDbUrl;
	}
	public void setMinConnection(String minConnection) {
		this.minConnection = minConnection;
	}
	public String getMinConnection() {
		return minConnection;
	}
}
