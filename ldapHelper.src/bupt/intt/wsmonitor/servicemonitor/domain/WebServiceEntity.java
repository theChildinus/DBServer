/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ public class WebServiceEntity
/*    */ {
/*    */   private String serviceName;
/*    */   private String serviceUrl;
/*    */   private String returnType;
/*    */   private String serviceParameters;
/*    */   private static final String PARAMETERSPLITER = "_";
/*    */   public static final String CATEGORYNAME = "ou=services";
/*    */ 
/*    */   public void setServiceName(String serviceName)
/*    */   {
/* 11 */     this.serviceName = serviceName;
/*    */   }
/*    */ 
/*    */   public String getServiceName() {
/* 15 */     return this.serviceName;
/*    */   }
/*    */ 
/*    */   public void setServiceUrl(String serviceUrl) {
/* 19 */     this.serviceUrl = serviceUrl;
/*    */   }
/*    */ 
/*    */   public String getServiceUrl() {
/* 23 */     return this.serviceUrl;
/*    */   }
/*    */ 
/*    */   public void setReturnType(String returnType) {
/* 27 */     this.returnType = returnType;
/*    */   }
/*    */ 
/*    */   public String getReturnType() {
/* 31 */     return this.returnType;
/*    */   }
/*    */ 
/*    */   public void setServiceParameters(String serviceParameters)
/*    */   {
/* 36 */     this.serviceParameters = serviceParameters;
/*    */   }
/*    */ 
/*    */   public String getServiceParameters()
/*    */   {
/* 48 */     return this.serviceParameters;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity
 * JD-Core Version:    0.6.0
 */