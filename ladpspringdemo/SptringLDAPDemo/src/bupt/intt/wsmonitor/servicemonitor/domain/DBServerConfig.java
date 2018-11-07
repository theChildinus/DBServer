package bupt.intt.wsmonitor.servicemonitor.domain;

import java.io.Serializable;

public class DBServerConfig extends ConfigType {
	/**
	 * 
	 */
	private static final long serialVersionUID = 68720509759313562L;
	public static final String SECONDBJECTCLASS = "DbServerConfig";
	private String ip;
	private String port;
	private String hostname;
	private String mysqldir;
	private String mysqluser;
	private String mysqlpwd;
	private String mysqldb;
	private String memdbBackurl;
	private String memdbBackuser;
	private String memdbBackpwd;
	private String memdbBackScriptPath;
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIp() {
		return ip;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPort() {
		return port;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getHostname() {
		return hostname;
	}
	public void setMysqldir(String mysqldir) {
		this.mysqldir = mysqldir;
	}
	public String getMysqldir() {
		return mysqldir;
	}
	public void setMysqluser(String mysqluser) {
		this.mysqluser = mysqluser;
	}
	public String getMysqluser() {
		return mysqluser;
	}
	public void setMysqlpwd(String mysqlpwd) {
		this.mysqlpwd = mysqlpwd;
	}
	public String getMysqlpwd() {
		return mysqlpwd;
	}
	public void setMysqldb(String mysqldb) {
		this.mysqldb = mysqldb;
	}
	public String getMysqldb() {
		return mysqldb;
	}
	public void setMemdbBackurl(String memdbBackurl) {
		this.memdbBackurl = memdbBackurl;
	}
	public String getMemdbBackurl() {
		return memdbBackurl;
	}
	public void setMemdbBackuser(String memdbBackuser) {
		this.memdbBackuser = memdbBackuser;
	}
	public String getMemdbBackuser() {
		return memdbBackuser;
	}
	public void setMemdbBackpwd(String memdbBackpwd) {
		this.memdbBackpwd = memdbBackpwd;
	}
	public String getMemdbBackpwd() {
		return memdbBackpwd;
	}
	public void setMemdbBackScriptPath(String memdbBackScriptPath) {
		this.memdbBackScriptPath = memdbBackScriptPath;
	}
	public String getMemdbBackScriptPath() {
		return memdbBackScriptPath;
	}
	
}
