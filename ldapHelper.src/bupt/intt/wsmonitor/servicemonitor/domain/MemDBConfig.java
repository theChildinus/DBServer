/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ public class MemDBConfig extends ConfigType
/*    */ {
/*    */   private static final long serialVersionUID = -2479184826254859924L;
/*    */   public static final String SECONDBJECTCLASS = "MemdbConfig";
/*    */   private String initHostname;
/*    */   private String initIP;
/*    */   private String initPort;
/*    */   private String initScriptPath;
/*    */   private String maxConnection;
/*    */   private String memDbUrl;
/*    */   private String minConnection;
/*    */ 
/*    */   public void setInitHostname(String initHostname)
/*    */   {
/* 19 */     this.initHostname = initHostname;
/*    */   }
/*    */   public String getInitHostname() {
/* 22 */     return this.initHostname;
/*    */   }
/*    */   public void setInitIP(String initIP) {
/* 25 */     this.initIP = initIP;
/*    */   }
/*    */   public String getInitIP() {
/* 28 */     return this.initIP;
/*    */   }
/*    */   public void setInitPort(String initPort) {
/* 31 */     this.initPort = initPort;
/*    */   }
/*    */   public String getInitPort() {
/* 34 */     return this.initPort;
/*    */   }
/*    */   public void setInitScriptPath(String initScriptPath) {
/* 37 */     this.initScriptPath = initScriptPath;
/*    */   }
/*    */   public String getInitScriptPath() {
/* 40 */     return this.initScriptPath;
/*    */   }
/*    */ 
/*    */   public void setMaxConnection(String maxConnection)
/*    */   {
/* 46 */     this.maxConnection = maxConnection;
/*    */   }
/*    */ 
/*    */   public String getMaxConnection()
/*    */   {
/* 52 */     return this.maxConnection;
/*    */   }
/*    */   public void setMemDbUrl(String memDbUrl) {
/* 55 */     this.memDbUrl = memDbUrl;
/*    */   }
/*    */   public String getMemDbUrl() {
/* 58 */     return this.memDbUrl;
/*    */   }
/*    */   public void setMinConnection(String minConnection) {
/* 61 */     this.minConnection = minConnection;
/*    */   }
/*    */   public String getMinConnection() {
/* 64 */     return this.minConnection;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig
 * JD-Core Version:    0.6.0
 */