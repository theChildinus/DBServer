/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ public class BootStrapConfig extends ConfigType
/*    */ {
/*    */   public static final String SECONDBJECTCLASS = "BootStrapConfig";
/*    */   private String displayName;
/*    */   private String runPath;
/*    */   private String iconUrl;
/*    */   private String halfOpcityImgUrl;
/*    */   private String imgUrl;
/*    */   private String processName;
/*    */   private String runArgument;
/*    */ 
/*    */   public void setDisplayName(String displayName)
/*    */   {
/* 13 */     this.displayName = displayName;
/*    */   }
/*    */   public String getDisplayName() {
/* 16 */     return this.displayName;
/*    */   }
/*    */   public void setRunPath(String runPath) {
/* 19 */     this.runPath = runPath;
/*    */   }
/*    */   public String getRunPath() {
/* 22 */     return this.runPath;
/*    */   }
/*    */   public void setIconUrl(String iconUrl) {
/* 25 */     this.iconUrl = iconUrl;
/*    */   }
/*    */   public String getIconUrl() {
/* 28 */     return this.iconUrl;
/*    */   }
/*    */   public void setHalfOpcityImgUrl(String halfOpcityImgUrl) {
/* 31 */     this.halfOpcityImgUrl = halfOpcityImgUrl;
/*    */   }
/*    */   public String getHalfOpcityImgUrl() {
/* 34 */     return this.halfOpcityImgUrl;
/*    */   }
/*    */   public void setImgUrl(String imgUrl) {
/* 37 */     this.imgUrl = imgUrl;
/*    */   }
/*    */   public String getImgUrl() {
/* 40 */     return this.imgUrl;
/*    */   }
/*    */   public void setProcessName(String processName) {
/* 43 */     this.processName = processName;
/*    */   }
/*    */   public String getProcessName() {
/* 46 */     return this.processName;
/*    */   }
/*    */   public void setRunArgument(String runArgument) {
/* 49 */     this.runArgument = runArgument;
/*    */   }
/*    */   public String getRunArgument() {
/* 52 */     return this.runArgument;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.BootStrapConfig
 * JD-Core Version:    0.6.0
 */