/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ public class DBServerConfig extends ConfigType
/*    */ {
/*    */   private static final long serialVersionUID = 68720509759313562L;
/*    */   public static final String SECONDBJECTCLASS = "DbServerConfig";
/*    */   private String ip;
/*    */   private String port;
/*    */   private String hostname;
/*    */   private String mysqldir;
/*    */   private String mysqluser;
/*    */   private String mysqlpwd;
/*    */   private String mysqldb;
/*    */   private String memdbBackurl;
/*    */   private String memdbBackuser;
/*    */   private String memdbBackpwd;
/*    */   private String memdbBackScriptPath;
/*    */ 
/*    */   public void setIp(String ip)
/*    */   {
/* 24 */     this.ip = ip;
/*    */   }
/*    */   public String getIp() {
/* 27 */     return this.ip;
/*    */   }
/*    */   public void setPort(String port) {
/* 30 */     this.port = port;
/*    */   }
/*    */   public String getPort() {
/* 33 */     return this.port;
/*    */   }
/*    */   public void setHostname(String hostname) {
/* 36 */     this.hostname = hostname;
/*    */   }
/*    */   public String getHostname() {
/* 39 */     return this.hostname;
/*    */   }
/*    */   public void setMysqldir(String mysqldir) {
/* 42 */     this.mysqldir = mysqldir;
/*    */   }
/*    */   public String getMysqldir() {
/* 45 */     return this.mysqldir;
/*    */   }
/*    */   public void setMysqluser(String mysqluser) {
/* 48 */     this.mysqluser = mysqluser;
/*    */   }
/*    */   public String getMysqluser() {
/* 51 */     return this.mysqluser;
/*    */   }
/*    */   public void setMysqlpwd(String mysqlpwd) {
/* 54 */     this.mysqlpwd = mysqlpwd;
/*    */   }
/*    */   public String getMysqlpwd() {
/* 57 */     return this.mysqlpwd;
/*    */   }
/*    */   public void setMysqldb(String mysqldb) {
/* 60 */     this.mysqldb = mysqldb;
/*    */   }
/*    */   public String getMysqldb() {
/* 63 */     return this.mysqldb;
/*    */   }
/*    */   public void setMemdbBackurl(String memdbBackurl) {
/* 66 */     this.memdbBackurl = memdbBackurl;
/*    */   }
/*    */   public String getMemdbBackurl() {
/* 69 */     return this.memdbBackurl;
/*    */   }
/*    */   public void setMemdbBackuser(String memdbBackuser) {
/* 72 */     this.memdbBackuser = memdbBackuser;
/*    */   }
/*    */   public String getMemdbBackuser() {
/* 75 */     return this.memdbBackuser;
/*    */   }
/*    */   public void setMemdbBackpwd(String memdbBackpwd) {
/* 78 */     this.memdbBackpwd = memdbBackpwd;
/*    */   }
/*    */   public String getMemdbBackpwd() {
/* 81 */     return this.memdbBackpwd;
/*    */   }
/*    */   public void setMemdbBackScriptPath(String memdbBackScriptPath) {
/* 84 */     this.memdbBackScriptPath = memdbBackScriptPath;
/*    */   }
/*    */   public String getMemdbBackScriptPath() {
/* 87 */     return this.memdbBackScriptPath;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig
 * JD-Core Version:    0.6.0
 */