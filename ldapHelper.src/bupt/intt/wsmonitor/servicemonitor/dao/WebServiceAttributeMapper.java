/*    */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.directory.Attribute;
/*    */ import javax.naming.directory.Attributes;
/*    */ import org.springframework.ldap.core.AttributesMapper;
/*    */ 
/*    */ class WebServiceAttributeMapper
/*    */   implements AttributesMapper
/*    */ {
/*    */   public Object mapFromAttributes(Attributes attrs)
/*    */     throws NamingException
/*    */   {
/* 13 */     WebServiceEntity service = new WebServiceEntity();
/*    */ 
/* 15 */     service.setReturnType((String)attrs.get("wsrtype").get());
/* 16 */     service.setServiceName((String)attrs.get("wn").get());
/* 17 */     service.setServiceParameters((String)attrs.get("wsop").get());
/* 18 */     service.setServiceUrl((String)attrs.get("wsurl").get());
/*    */ 
/* 20 */     return service;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.WebServiceAttributeMapper
 * JD-Core Version:    0.6.0
 */