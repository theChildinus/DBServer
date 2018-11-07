/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ public class DataServiceConfig extends ConfigType
/*    */ {
/*    */   private static final long serialVersionUID = -5366770149371936672L;
/*    */   public static final String SECONDBJECTCLASS = "DataServiceConfig";
/*    */   private String brokerURI;
/*    */   private String customerURI;
/*    */   private String defaultReadCount;
/*    */   private String topics;
/*    */   private String createPullPointURI;
/*    */   private String jmsurl;
/*    */   private String remoteJdbcUrl;
/*    */   private String remoteJdbcUser;
/*    */   private String remoteJdbcPass;
/*    */ 
/*    */   public void setBrokerURI(String brokerURI)
/*    */   {
/* 22 */     this.brokerURI = brokerURI;
/*    */   }
/*    */   public String getBrokerURI() {
/* 25 */     return this.brokerURI;
/*    */   }
/*    */   public void setCustomerURI(String customerURI) {
/* 28 */     this.customerURI = customerURI;
/*    */   }
/*    */   public String getCustomerURI() {
/* 31 */     return this.customerURI;
/*    */   }
/*    */   public void setDefaultReadCount(String defaultReadCount) {
/* 34 */     this.defaultReadCount = defaultReadCount;
/*    */   }
/*    */   public String getDefaultReadCount() {
/* 37 */     return this.defaultReadCount;
/*    */   }
/*    */   public void setTopics(String topics) {
/* 40 */     this.topics = topics;
/*    */   }
/*    */   public String getTopics() {
/* 43 */     return this.topics;
/*    */   }
/*    */   public void setCreatePullPointURI(String createPullPointURI) {
/* 46 */     this.createPullPointURI = createPullPointURI;
/*    */   }
/*    */   public String getCreatePullPointURI() {
/* 49 */     return this.createPullPointURI;
/*    */   }
/*    */   public void setJmsurl(String jmsurl) {
/* 52 */     this.jmsurl = jmsurl;
/*    */   }
/*    */   public String getJmsurl() {
/* 55 */     return this.jmsurl;
/*    */   }
/*    */   public void setRemoteJdbcUrl(String remoteJdbcUrl) {
/* 58 */     this.remoteJdbcUrl = remoteJdbcUrl;
/*    */   }
/*    */   public String getRemoteJdbcUrl() {
/* 61 */     return this.remoteJdbcUrl;
/*    */   }
/*    */   public void setRemoteJdbcUser(String remoteJdbcUser) {
/* 64 */     this.remoteJdbcUser = remoteJdbcUser;
/*    */   }
/*    */   public String getRemoteJdbcUser() {
/* 67 */     return this.remoteJdbcUser;
/*    */   }
/*    */   public void setRemoteJdbcPass(String remoteJdbcPass) {
/* 70 */     this.remoteJdbcPass = remoteJdbcPass;
/*    */   }
/*    */   public String getRemoteJdbcPass() {
/* 73 */     return this.remoteJdbcPass;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig
 * JD-Core Version:    0.6.0
 */