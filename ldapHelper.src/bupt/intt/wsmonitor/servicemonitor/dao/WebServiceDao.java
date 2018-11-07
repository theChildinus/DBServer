/*    */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;
/*    */ import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;
/*    */ import java.util.List;
/*    */ import javax.naming.Name;
/*    */ import org.springframework.ldap.core.LdapTemplate;
/*    */ 
/*    */ public class WebServiceDao
/*    */   implements GenericDao<WebServiceEntity>
/*    */ {
/*    */   private LdapTemplate ldapTemplate;
/*    */ 
/*    */   public List<WebServiceEntity> getAll()
/*    */   {
/* 15 */     String _filter = "(objectclass=ws)";
/* 16 */     return this.ldapTemplate.search("ou=services", _filter, new WebServiceAttributeMapper());
/*    */   }
/*    */ 
/*    */   public WebServiceEntity getByDn(String dn) {
/* 20 */     return (WebServiceEntity)this.ldapTemplate.lookup(dn, new WebServiceAttributeMapper());
/*    */   }
/*    */ 
/*    */   public void create(WebServiceEntity service) {
/* 24 */     Name serviceDn = WebServiceLADPHandler.getDName(service);
/* 25 */     this.ldapTemplate.bind(serviceDn, null, WebServiceLADPHandler.BuildAttributes(service));
/*    */   }
/*    */ 
/*    */   public void update(WebServiceEntity t)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void delete(WebServiceEntity service) {
/* 33 */     Name serviceDn = WebServiceLADPHandler.getDName(service);
/* 34 */     this.ldapTemplate.unbind(serviceDn);
/*    */   }
/*    */ 
/*    */   public void setLdapTemplate(LdapTemplate ldapTemplate) {
/* 38 */     this.ldapTemplate = ldapTemplate;
/*    */   }
/*    */ 
/*    */   public LdapTemplate getLdapTemplate() {
/* 42 */     return this.ldapTemplate;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.WebServiceDao
 * JD-Core Version:    0.6.0
 */