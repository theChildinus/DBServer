package bupt.intt.wsmonitor.servicemonitor.domain.serialzable;

import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;

public class SDBServerConfig extends SConfigType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5953164643550991749L;

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
	
	public String toString() {
		return "ip=" + ip + ",port=" + port + ",hostname=" + hostname
				+ ",mysqldir=" + mysqldir + ",mysqluser=" + mysqluser
				+ ",mysqlpwd=" + mysqlpwd + ",mysqldb=" + mysqldb
				+ ",memdbBackurl=" + memdbBackurl + ",memdbBackuser="
				+ memdbBackuser + ",memdbBackpwd=" + memdbBackpwd
				+ ",memdbBackScriptPath=" + memdbBackScriptPath;
	}
	
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
	
	public SDBServerConfig(DBServerConfig config) {
		this.setIp(config.getIp());
		this.setPort(config.getPort());
		this.setHostname(config.getHostname());
		this.setMysqldir(config.getMysqldir());
		this.setMysqluser(config.getMysqluser());
		this.setMysqlpwd(config.getMysqlpwd());
		this.setMysqldb(config.getMysqldb());
		this.setMemdbBackurl(config.getMemdbBackurl());
		this.setMemdbBackuser(config.getMemdbBackuser());
		this.setMemdbBackpwd(config.getMemdbBackpwd());
		this.setMemdbBackScriptPath(config.getMemdbBackScriptPath());
	}
	
	public SDBServerConfig() {
		
	}
}
