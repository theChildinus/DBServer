/*     */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*     */ import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttribute;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import org.springframework.ldap.core.AttributesMapper;
/*     */ import org.springframework.ldap.core.DistinguishedName;
/*     */ import org.springframework.ldap.core.LdapTemplate;
/*     */ 
/*     */ public class DataServiceDao
/*     */   implements GenericDao<DataServiceConfig>
/*     */ {
/*     */   private LdapTemplate ldapTemplate;
/*     */ 
/*     */   public void setLdapTemplate(LdapTemplate ldapTemplate)
/*     */   {
/*  28 */     this.ldapTemplate = ldapTemplate;
/*     */   }
/*     */ 
/*     */   public LdapTemplate getLdapTemplate() {
/*  32 */     return this.ldapTemplate;
/*     */   }
/*     */ 
/*     */   public void create(DataServiceConfig t)
/*     */   {
/*  39 */     Name name = getDName(t);
/*  40 */     this.ldapTemplate.bind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public void delete(DataServiceConfig t)
/*     */   {
/*  46 */     Name name = getDName(t);
/*  47 */     this.ldapTemplate.unbind(name);
/*     */   }
/*     */ 
/*     */   public List<DataServiceConfig> getAll()
/*     */   {
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */   public DataServiceConfig getByDn(String dn)
/*     */   {
/*  59 */     return (DataServiceConfig)this.ldapTemplate.lookup(dn, new DataServiceConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public DataServiceConfig getByDn(Name dn)
/*     */   {
/*  64 */     return (DataServiceConfig)this.ldapTemplate.lookup(dn, new DataServiceConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public void update(DataServiceConfig t)
/*     */   {
/*  70 */     Name name = getDName(t);
/*  71 */     this.ldapTemplate.rebind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public Name getDName(ConfigType t) {
/*  75 */     DistinguishedName name = new DistinguishedName("");
/*     */ 
/*  78 */     ArrayList disNames = t.getDisNames();
/*  79 */     if (disNames == null)
/*  80 */       return null;
/*  81 */     for (int i = 0; i < disNames.size(); i++) {
/*  82 */       PathName disName = (PathName)disNames.get(i);
/*  83 */       name.add(disName.getPrefix(), disName.getValue());
/*     */     }
/*     */ 
/*  86 */     return name;
/*     */   }
/*     */ 
/*     */   private Attributes getAttributes(DataServiceConfig t) {
/*  90 */     Attributes attrs = new BasicAttributes();
/*  91 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/*  92 */     ocattr.add("top");
/*  93 */     ocattr.add("organizationalUnit");
/*  94 */     ocattr.add("DataServiceConfig");
/*  95 */     attrs.put(ocattr);
/*     */ 
/*  97 */     attrs.put("ou", t.getName());
/*     */ 
/*  99 */     if ((t.getDescription() != null) && (t.getDescription().length() > 0)) {
/* 100 */       attrs.put("description", t.getDescription());
/*     */     }
/*     */ 
/* 103 */     attrs.put("BrokerURI", t.getBrokerURI());
/* 104 */     attrs.put("customerUri", t.getCustomerURI());
/* 105 */     attrs.put("DefaultReadCount", t.getDefaultReadCount());
/* 106 */     attrs.put("Topics", t.getTopics());
/* 107 */     attrs.put("CreatePullPointURI", t.getCreatePullPointURI());
/* 108 */     attrs.put("JMSurl", t.getJmsurl());
/* 109 */     attrs.put("RemoteJdbcUrl", t.getRemoteJdbcUrl());
/* 110 */     attrs.put("RemoteJdbcUser", t.getRemoteJdbcUser());
/* 111 */     attrs.put("RemoteJdbcPass", t.getRemoteJdbcPass());
/*     */ 
/* 113 */     return attrs;
/*     */   }
/*     */ 
/*     */   class DataServiceConfigAttributesMapper implements AttributesMapper
/*     */   {
/*     */     DataServiceConfigAttributesMapper()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Object mapFromAttributes(Attributes attrs) throws NamingException
/*     */     {
/* 124 */       DataServiceConfig t = new DataServiceConfig();
/* 125 */       t.setName((String)attrs.get("ou").get());
/* 126 */       if (attrs.get("description") != null) {
/* 127 */         t.setDescription((String)attrs.get("description").get());
/*     */       }
/* 129 */       t.setBrokerURI((String)attrs.get("BrokerURI").get());
/* 130 */       t.setCustomerURI((String)attrs.get("customerUri").get());
/* 131 */       t.setDefaultReadCount((String)attrs.get("DefaultReadCount").get());
/* 132 */       t.setTopics((String)attrs.get("Topics").get());
/* 133 */       t.setCreatePullPointURI((String)attrs.get("CreatePullPointURI").get());
/* 134 */       t.setJmsurl((String)attrs.get("JMSurl").get());
/* 135 */       t.setRemoteJdbcUrl((String)attrs.get("RemoteJdbcUrl").get());
/* 136 */       t.setRemoteJdbcUser((String)attrs.get("RemoteJdbcUser").get());
/* 137 */       t.setRemoteJdbcPass((String)attrs.get("RemoteJdbcPass").get());
/* 138 */       return t;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.DataServiceDao
 * JD-Core Version:    0.6.0
 */