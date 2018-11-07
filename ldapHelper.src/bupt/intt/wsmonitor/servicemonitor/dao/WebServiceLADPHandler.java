/*    */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;
/*    */ import javax.naming.Name;
/*    */ import javax.naming.directory.Attributes;
/*    */ import javax.naming.directory.BasicAttribute;
/*    */ import javax.naming.directory.BasicAttributes;
/*    */ import org.springframework.ldap.core.DistinguishedName;
/*    */ 
/*    */ class WebServiceLADPHandler
/*    */ {
/*    */   public static Name getDName(WebServiceEntity wService)
/*    */   {
/* 14 */     DistinguishedName name = new DistinguishedName("");
/* 15 */     name.add("ou", "services");
/* 16 */     name.add("wn", wService.getServiceName());
/*    */ 
/* 18 */     return name;
/*    */   }
/*    */ 
/*    */   public static Attributes BuildAttributes(WebServiceEntity wService) {
/* 22 */     Attributes attrs = new BasicAttributes();
/* 23 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/* 24 */     ocattr.add("top");
/* 25 */     ocattr.add("ws");
/* 26 */     attrs.put(ocattr);
/*    */ 
/* 28 */     attrs.put("wn", wService.getServiceName());
/* 29 */     attrs.put("wsop", wService.getServiceParameters());
/* 30 */     attrs.put("wsrtype", wService.getReturnType());
/* 31 */     attrs.put("wsurl", wService.getServiceUrl());
/*    */ 
/* 33 */     return attrs;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.WebServiceLADPHandler
 * JD-Core Version:    0.6.0
 */