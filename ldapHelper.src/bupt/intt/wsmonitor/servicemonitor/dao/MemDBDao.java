/*     */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
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
/*     */ public class MemDBDao
/*     */   implements GenericDao<MemDBConfig>
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
/*     */   public void create(MemDBConfig t)
/*     */   {
/*  39 */     Name name = getDName(t);
/*  40 */     this.ldapTemplate.bind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public void delete(MemDBConfig t)
/*     */   {
/*  46 */     Name name = getDName(t);
/*  47 */     this.ldapTemplate.unbind(name);
/*     */   }
/*     */ 
/*     */   public List<MemDBConfig> getAll()
/*     */   {
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */   public MemDBConfig getByDn(String dn)
/*     */   {
/*  59 */     return (MemDBConfig)this.ldapTemplate.lookup(dn, new MemDBAttributesMapper());
/*     */   }
/*     */ 
/*     */   public MemDBConfig getByDn(Name dn)
/*     */   {
/*  64 */     return (MemDBConfig)this.ldapTemplate.lookup(dn, new MemDBAttributesMapper());
/*     */   }
/*     */ 
/*     */   public void update(MemDBConfig t)
/*     */   {
/*  70 */     Name name = getDName(t);
/*  71 */     this.ldapTemplate.rebind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public Name getDName(ConfigType t)
/*     */   {
/*  78 */     DistinguishedName name = new DistinguishedName("");
/*     */ 
/*  81 */     ArrayList disNames = t.getDisNames();
/*  82 */     if (disNames == null)
/*  83 */       return null;
/*  84 */     for (int i = 0; i < disNames.size(); i++) {
/*  85 */       PathName disName = (PathName)disNames.get(i);
/*  86 */       name.add(disName.getPrefix(), disName.getValue());
/*     */     }
/*     */ 
/*  89 */     return name;
/*     */   }
/*     */ 
/*     */   private Attributes getAttributes(MemDBConfig t)
/*     */   {
/*  98 */     Attributes attrs = new BasicAttributes();
/*  99 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/* 100 */     ocattr.add("top");
/* 101 */     ocattr.add("organizationalUnit");
/* 102 */     ocattr.add("MemdbConfig");
/* 103 */     attrs.put(ocattr);
/*     */ 
/* 105 */     attrs.put("ou", t.getName());
/*     */ 
/* 107 */     if ((t.getDescription() != null) && (t.getDescription().length() > 0)) {
/* 108 */       attrs.put("description", t.getDescription());
/*     */     }
/*     */ 
/* 111 */     attrs.put("InitHostname", t.getInitHostname());
/* 112 */     attrs.put("InitIP", t.getInitIP());
/* 113 */     attrs.put("InitPort", t.getInitPort());
/* 114 */     attrs.put("InitScriptPath", t.getInitScriptPath());
/* 115 */     attrs.put("maxConnection", t.getMaxConnection());
/* 116 */     attrs.put("memDbUrl", t.getMemDbUrl());
/* 117 */     attrs.put("minConnection", t.getMinConnection());
/*     */ 
/* 120 */     return attrs;
/*     */   }
/*     */ 
/*     */   class MemDBAttributesMapper implements AttributesMapper
/*     */   {
/*     */     MemDBAttributesMapper()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Object mapFromAttributes(Attributes attrs) throws NamingException
/*     */     {
/* 131 */       MemDBConfig t = new MemDBConfig();
/* 132 */       t.setName((String)attrs.get("ou").get());
/* 133 */       if (attrs.get("description") != null) {
/* 134 */         t.setDescription((String)attrs.get("description").get());
/*     */       }
/* 136 */       t.setInitHostname((String)attrs.get("InitHostname").get());
/* 137 */       t.setInitIP((String)attrs.get("InitIP").get());
/* 138 */       t.setInitPort((String)attrs.get("InitPort").get());
/* 139 */       t.setInitScriptPath((String)attrs.get("InitScriptPath").get());
/* 140 */       t.setMaxConnection((String)attrs.get("maxConnection").get());
/* 141 */       t.setMemDbUrl((String)attrs.get("memDbUrl").get());
/* 142 */       t.setMinConnection((String)attrs.get("minConnection").get());
/*     */ 
/* 144 */       return t;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.MemDBDao
 * JD-Core Version:    0.6.0
 */