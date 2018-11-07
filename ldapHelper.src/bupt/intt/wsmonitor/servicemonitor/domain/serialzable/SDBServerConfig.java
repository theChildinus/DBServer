/*     */ package bupt.intt.wsmonitor.servicemonitor.domain.serialzable;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
/*     */ 
/*     */ public class SDBServerConfig extends SConfigType
/*     */ {
/*     */   private static final long serialVersionUID = -5953164643550991749L;
/*     */   private String ip;
/*     */   private String port;
/*     */   private String hostname;
/*     */   private String mysqldir;
/*     */   private String mysqluser;
/*     */   private String mysqlpwd;
/*     */   private String mysqldb;
/*     */   private String memdbBackurl;
/*     */   private String memdbBackuser;
/*     */   private String memdbBackpwd;
/*     */   private String memdbBackScriptPath;
/*     */ 
/*     */   public String toString()
/*     */   {
/*  25 */     return "ip=" + this.ip + ",port=" + this.port + ",hostname=" + this.hostname + 
/*  26 */       ",mysqldir=" + this.mysqldir + ",mysqluser=" + this.mysqluser + 
/*  27 */       ",mysqlpwd=" + this.mysqlpwd + ",mysqldb=" + this.mysqldb + 
/*  28 */       ",memdbBackurl=" + this.memdbBackurl + ",memdbBackuser=" + 
/*  29 */       this.memdbBackuser + ",memdbBackpwd=" + this.memdbBackpwd + 
/*  30 */       ",memdbBackScriptPath=" + this.memdbBackScriptPath;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip) {
/*  34 */     this.ip = ip;
/*     */   }
/*     */   public String getIp() {
/*  37 */     return this.ip;
/*     */   }
/*     */   public void setPort(String port) {
/*  40 */     this.port = port;
/*     */   }
/*     */   public String getPort() {
/*  43 */     return this.port;
/*     */   }
/*     */   public void setHostname(String hostname) {
/*  46 */     this.hostname = hostname;
/*     */   }
/*     */   public String getHostname() {
/*  49 */     return this.hostname;
/*     */   }
/*     */   public void setMysqldir(String mysqldir) {
/*  52 */     this.mysqldir = mysqldir;
/*     */   }
/*     */   public String getMysqldir() {
/*  55 */     return this.mysqldir;
/*     */   }
/*     */   public void setMysqluser(String mysqluser) {
/*  58 */     this.mysqluser = mysqluser;
/*     */   }
/*     */   public String getMysqluser() {
/*  61 */     return this.mysqluser;
/*     */   }
/*     */   public void setMysqlpwd(String mysqlpwd) {
/*  64 */     this.mysqlpwd = mysqlpwd;
/*     */   }
/*     */   public String getMysqlpwd() {
/*  67 */     return this.mysqlpwd;
/*     */   }
/*     */   public void setMysqldb(String mysqldb) {
/*  70 */     this.mysqldb = mysqldb;
/*     */   }
/*     */   public String getMysqldb() {
/*  73 */     return this.mysqldb;
/*     */   }
/*     */   public void setMemdbBackurl(String memdbBackurl) {
/*  76 */     this.memdbBackurl = memdbBackurl;
/*     */   }
/*     */   public String getMemdbBackurl() {
/*  79 */     return this.memdbBackurl;
/*     */   }
/*     */   public void setMemdbBackuser(String memdbBackuser) {
/*  82 */     this.memdbBackuser = memdbBackuser;
/*     */   }
/*     */   public String getMemdbBackuser() {
/*  85 */     return this.memdbBackuser;
/*     */   }
/*     */   public void setMemdbBackpwd(String memdbBackpwd) {
/*  88 */     this.memdbBackpwd = memdbBackpwd;
/*     */   }
/*     */   public String getMemdbBackpwd() {
/*  91 */     return this.memdbBackpwd;
/*     */   }
/*     */   public void setMemdbBackScriptPath(String memdbBackScriptPath) {
/*  94 */     this.memdbBackScriptPath = memdbBackScriptPath;
/*     */   }
/*     */   public String getMemdbBackScriptPath() {
/*  97 */     return this.memdbBackScriptPath;
/*     */   }
/*     */ 
/*     */   public SDBServerConfig(DBServerConfig config) {
/* 101 */     setIp(config.getIp());
/* 102 */     setPort(config.getPort());
/* 103 */     setHostname(config.getHostname());
/* 104 */     setMysqldir(config.getMysqldir());
/* 105 */     setMysqluser(config.getMysqluser());
/* 106 */     setMysqlpwd(config.getMysqlpwd());
/* 107 */     setMysqldb(config.getMysqldb());
/* 108 */     setMemdbBackurl(config.getMemdbBackurl());
/* 109 */     setMemdbBackuser(config.getMemdbBackuser());
/* 110 */     setMemdbBackpwd(config.getMemdbBackpwd());
/* 111 */     setMemdbBackScriptPath(config.getMemdbBackScriptPath());
/*     */   }
/*     */ 
/*     */   public SDBServerConfig()
/*     */   {
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.serialzable.SDBServerConfig
 * JD-Core Version:    0.6.0
 */