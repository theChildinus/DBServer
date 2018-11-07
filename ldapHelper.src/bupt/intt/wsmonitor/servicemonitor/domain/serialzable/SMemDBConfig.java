/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.serialzable;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
/*    */ 
/*    */ public class SMemDBConfig extends SConfigType
/*    */ {
/*    */   private static final long serialVersionUID = -5544227068297046499L;
/*    */   private String initHostname;
/*    */   private String initIP;
/*    */   private String initPort;
/*    */   private String initScriptPath;
/*    */   private String maxConnection;
/*    */   private String memDbUrl;
/*    */   private String minConnection;
/*    */ 
/*    */   public String toString()
/*    */   {
/* 21 */     return "inithostname=" + this.initHostname + ",initIP=" + this.initIP + 
/* 22 */       ",initPort=" + this.initPort + ",initScriptPath=" + this.initScriptPath + 
/* 23 */       ",maxConnection=" + this.maxConnection + ", memDbUrl=" + this.memDbUrl + 
/* 24 */       ", minConnection=" + this.minConnection;
/*    */   }
/*    */ 
/*    */   public void setInitHostname(String initHostname) {
/* 28 */     this.initHostname = initHostname;
/*    */   }
/*    */   public String getInitHostname() {
/* 31 */     return this.initHostname;
/*    */   }
/*    */   public void setInitIP(String initIP) {
/* 34 */     this.initIP = initIP;
/*    */   }
/*    */   public String getInitIP() {
/* 37 */     return this.initIP;
/*    */   }
/*    */   public void setInitPort(String initPort) {
/* 40 */     this.initPort = initPort;
/*    */   }
/*    */   public String getInitPort() {
/* 43 */     return this.initPort;
/*    */   }
/*    */   public void setInitScriptPath(String initScriptPath) {
/* 46 */     this.initScriptPath = initScriptPath;
/*    */   }
/*    */   public String getInitScriptPath() {
/* 49 */     return this.initScriptPath;
/*    */   }
/*    */ 
/*    */   public void setMaxConnection(String maxConnection)
/*    */   {
/* 55 */     this.maxConnection = maxConnection;
/*    */   }
/*    */ 
/*    */   public String getMaxConnection()
/*    */   {
/* 61 */     return this.maxConnection;
/*    */   }
/*    */   public void setMemDbUrl(String memDbUrl) {
/* 64 */     this.memDbUrl = memDbUrl;
/*    */   }
/*    */   public String getMemDbUrl() {
/* 67 */     return this.memDbUrl;
/*    */   }
/*    */   public void setMinConnection(String minConnection) {
/* 70 */     this.minConnection = minConnection;
/*    */   }
/*    */   public String getMinConnection() {
/* 73 */     return this.minConnection;
/*    */   }
/*    */ 
/*    */   public SMemDBConfig(MemDBConfig config) {
/* 77 */     setInitHostname(config.getInitHostname());
/* 78 */     setInitIP(config.getInitIP());
/* 79 */     setInitPort(config.getInitPort());
/* 80 */     setInitScriptPath(config.getInitScriptPath());
/* 81 */     setMaxConnection(config.getMaxConnection());
/* 82 */     setMemDbUrl(config.getMemDbUrl());
/* 83 */     setMinConnection(config.getMinConnection());
/*    */   }
/*    */ 
/*    */   public SMemDBConfig()
/*    */   {
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.serialzable.SMemDBConfig
 * JD-Core Version:    0.6.0
 */