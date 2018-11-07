/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ public class MessageServerConfig extends ConfigType
/*    */ {
/*    */   private String ip;
/*    */   private String createPullPointURI;
/*    */   private String brokerURI;
/*    */   public static final String SECONDBJECTCLASS = "MessageServerConfig";
/*    */ 
/*    */   public void setIp(String ip)
/*    */   {
/* 10 */     this.ip = ip;
/*    */   }
/*    */ 
/*    */   public String getIp() {
/* 14 */     return this.ip;
/*    */   }
/*    */ 
/*    */   public void setCreatePullPointURI(String createPullPointURI) {
/* 18 */     this.createPullPointURI = createPullPointURI;
/*    */   }
/*    */ 
/*    */   public String getCreatePullPointURI() {
/* 22 */     return this.createPullPointURI;
/*    */   }
/*    */ 
/*    */   public void setBrokerURI(String brokerURI) {
/* 26 */     this.brokerURI = brokerURI;
/*    */   }
/*    */ 
/*    */   public String getBrokerURI() {
/* 30 */     return this.brokerURI;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig
 * JD-Core Version:    0.6.0
 */