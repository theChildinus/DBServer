/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.serialzable;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
/*    */ 
/*    */ public class SDataServiceConfig extends SConfigType
/*    */ {
/*    */   private static final long serialVersionUID = 6721311855415767655L;
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
/*    */   public String toString()
/*    */   {
/* 23 */     return "brokerURI=" + this.brokerURI + ",customerURI=" + this.customerURI + 
/* 24 */       ",defaultReadCount=" + this.defaultReadCount + ",topics=" + this.topics + 
/* 25 */       ",createPullPointURI=" + this.createPullPointURI + ",jmsurl=" + 
/* 26 */       this.jmsurl + ",remoteJdbcUrl=" + this.remoteJdbcUrl + 
/* 27 */       ",remoteJdbcUser=" + this.remoteJdbcUser + ",remoteJdbcPass=" + 
/* 28 */       this.remoteJdbcPass;
/*    */   }
/*    */ 
/*    */   public void setBrokerURI(String brokerURI) {
/* 32 */     this.brokerURI = brokerURI;
/*    */   }
/*    */   public String getBrokerURI() {
/* 35 */     return this.brokerURI;
/*    */   }
/*    */   public void setCustomerURI(String customerURI) {
/* 38 */     this.customerURI = customerURI;
/*    */   }
/*    */   public String getCustomerURI() {
/* 41 */     return this.customerURI;
/*    */   }
/*    */   public void setDefaultReadCount(String defaultReadCount) {
/* 44 */     this.defaultReadCount = defaultReadCount;
/*    */   }
/*    */   public String getDefaultReadCount() {
/* 47 */     return this.defaultReadCount;
/*    */   }
/*    */   public void setTopics(String topics) {
/* 50 */     this.topics = topics;
/*    */   }
/*    */   public String getTopics() {
/* 53 */     return this.topics;
/*    */   }
/*    */   public void setCreatePullPointURI(String createPullPointURI) {
/* 56 */     this.createPullPointURI = createPullPointURI;
/*    */   }
/*    */   public String getCreatePullPointURI() {
/* 59 */     return this.createPullPointURI;
/*    */   }
/*    */   public void setJmsurl(String jmsurl) {
/* 62 */     this.jmsurl = jmsurl;
/*    */   }
/*    */   public String getJmsurl() {
/* 65 */     return this.jmsurl;
/*    */   }
/*    */   public void setRemoteJdbcUrl(String remoteJdbcUrl) {
/* 68 */     this.remoteJdbcUrl = remoteJdbcUrl;
/*    */   }
/*    */   public String getRemoteJdbcUrl() {
/* 71 */     return this.remoteJdbcUrl;
/*    */   }
/*    */   public void setRemoteJdbcUser(String remoteJdbcUser) {
/* 74 */     this.remoteJdbcUser = remoteJdbcUser;
/*    */   }
/*    */   public String getRemoteJdbcUser() {
/* 77 */     return this.remoteJdbcUser;
/*    */   }
/*    */   public void setRemoteJdbcPass(String remoteJdbcPass) {
/* 80 */     this.remoteJdbcPass = remoteJdbcPass;
/*    */   }
/*    */   public String getRemoteJdbcPass() {
/* 83 */     return this.remoteJdbcPass;
/*    */   }
/*    */ 
/*    */   public SDataServiceConfig(DataServiceConfig serviceConfig) {
/* 87 */     setBrokerURI(serviceConfig.getBrokerURI());
/* 88 */     setCustomerURI(serviceConfig.getCustomerURI());
/* 89 */     setDefaultReadCount(serviceConfig.getDefaultReadCount());
/* 90 */     setTopics(serviceConfig.getTopics());
/* 91 */     setCreatePullPointURI(serviceConfig.getCreatePullPointURI());
/* 92 */     setJmsurl(serviceConfig.getJmsurl());
/* 93 */     setRemoteJdbcUrl(serviceConfig.getRemoteJdbcUrl());
/* 94 */     setRemoteJdbcUser(serviceConfig.getRemoteJdbcUser());
/* 95 */     setRemoteJdbcPass(serviceConfig.getRemoteJdbcPass());
/*    */   }
/*    */ 
/*    */   public SDataServiceConfig()
/*    */   {
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.serialzable.SDataServiceConfig
 * JD-Core Version:    0.6.0
 */