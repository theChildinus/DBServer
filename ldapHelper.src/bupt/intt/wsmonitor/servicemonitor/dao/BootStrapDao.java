/*     */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.BootStrapConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
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
/*     */ public class BootStrapDao
/*     */   implements GenericDao<BootStrapConfig>
/*     */ {
/*     */   private LdapTemplate ldapTemplate;
/*     */ 
/*     */   public void setLdapTemplate(LdapTemplate ldapTemplate)
/*     */   {
/*  29 */     this.ldapTemplate = ldapTemplate;
/*     */   }
/*     */ 
/*     */   public LdapTemplate getLdapTemplate() {
/*  33 */     return this.ldapTemplate;
/*     */   }
/*     */ 
/*     */   public void create(BootStrapConfig t)
/*     */   {
/*  40 */     Name name = getDName(t);
/*  41 */     this.ldapTemplate.bind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public void delete(BootStrapConfig t)
/*     */   {
/*  47 */     Name name = getDName(t);
/*  48 */     this.ldapTemplate.unbind(name);
/*     */   }
/*     */ 
/*     */   public List<BootStrapConfig> getAll()
/*     */   {
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   public List searchAll() {
/*  58 */     return this.ldapTemplate.search("", "(objectclass=BootStrapConfig)", new BootStrapConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public BootStrapConfig searchById(String id) {
/*  62 */     List list = this.ldapTemplate.search("", "(&(objectclass=BootStrapConfig)(ou=" + id + "))", new BootStrapConfigAttributesMapper());
/*  63 */     if (list.size() > 0) {
/*  64 */       return (BootStrapConfig)list.get(0);
/*     */     }
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */   public BootStrapConfig searchByCategory(String category) {
/*  70 */     List list = this.ldapTemplate.search("", "(&(objectclass=BootStrapConfig)(description=" + category + "))", new BootStrapConfigAttributesMapper());
/*  71 */     if (list.size() > 0) {
/*  72 */       return (BootStrapConfig)list.get(0);
/*     */     }
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   public BootStrapConfig getByDn(String dn)
/*     */   {
/*  90 */     return (BootStrapConfig)this.ldapTemplate.lookup(dn, new BootStrapConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public BootStrapConfig getByDn(Name dn)
/*     */   {
/*  95 */     return (BootStrapConfig)this.ldapTemplate.lookup(dn, new BootStrapConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public void update(BootStrapConfig t)
/*     */   {
/* 101 */     Name name = getDName(t);
/* 102 */     this.ldapTemplate.rebind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public Name getDName(ConfigType t)
/*     */   {
/* 109 */     DistinguishedName name = new DistinguishedName("");
/*     */ 
/* 112 */     ArrayList disNames = t.getDisNames();
/* 113 */     if (disNames == null)
/* 114 */       return null;
/* 115 */     for (int i = 0; i < disNames.size(); i++) {
/* 116 */       PathName disName = (PathName)disNames.get(i);
/* 117 */       name.add(disName.getPrefix(), disName.getValue());
/*     */     }
/*     */ 
/* 120 */     return name;
/*     */   }
/*     */ 
/*     */   private Attributes getAttributes(BootStrapConfig t)
/*     */   {
/* 129 */     Attributes attrs = new BasicAttributes();
/* 130 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/* 131 */     ocattr.add("top");
/* 132 */     ocattr.add("organizationalUnit");
/* 133 */     ocattr.add("BootStrapConfig");
/* 134 */     attrs.put(ocattr);
/*     */ 
/* 136 */     attrs.put("ou", t.getName());
/*     */ 
/* 138 */     if ((t.getDescription() != null) && (t.getDescription().length() > 0)) {
/* 139 */       attrs.put("description", t.getDescription());
/*     */     }
/*     */ 
/* 142 */     attrs.put("ProcessDisplayName", t.getDisplayName());
/* 143 */     attrs.put("RunPath", t.getRunPath());
/* 144 */     attrs.put("IconUrl", t.getIconUrl());
/* 145 */     attrs.put("HalfOpcityImgUrl", t.getHalfOpcityImgUrl());
/* 146 */     attrs.put("ImgUrl", t.getImgUrl());
/* 147 */     attrs.put("ProcessName", t.getProcessName());
/* 148 */     attrs.put("RunArgument", t.getRunArgument());
/*     */ 
/* 151 */     return attrs;
/*     */   }
/*     */ 
/*     */   class BootStrapConfigAttributesMapper implements AttributesMapper
/*     */   {
/*     */     BootStrapConfigAttributesMapper()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Object mapFromAttributes(Attributes attrs) throws NamingException
/*     */     {
/* 162 */       BootStrapConfig t = new BootStrapConfig();
/* 163 */       t.setName((String)attrs.get("ou").get());
/* 164 */       if (attrs.get("description") != null) {
/* 165 */         t.setDescription((String)attrs.get("description").get());
/*     */       }
/* 167 */       t.setDisplayName((String)attrs.get("ProcessDisplayName").get());
/* 168 */       t.setRunPath((String)attrs.get("RunPath").get());
/* 169 */       t.setIconUrl((String)attrs.get("IconUrl").get());
/* 170 */       t.setHalfOpcityImgUrl((String)attrs.get("HalfOpcityImgUrl").get());
/* 171 */       t.setImgUrl((String)attrs.get("ImgUrl").get());
/* 172 */       t.setProcessName((String)attrs.get("ProcessName").get());
/* 173 */       t.setRunArgument((String)attrs.get("RunArgument").get());
/* 174 */       return t;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.BootStrapDao
 * JD-Core Version:    0.6.0
 */