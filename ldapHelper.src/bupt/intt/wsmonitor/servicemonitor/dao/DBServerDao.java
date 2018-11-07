/*     */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
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
/*     */ public class DBServerDao
/*     */   implements GenericDao<DBServerConfig>
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
/*     */   public void create(DBServerConfig t)
/*     */   {
/*  38 */     Name name = getDName(t);
/*  39 */     this.ldapTemplate.bind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public void delete(DBServerConfig t)
/*     */   {
/*  45 */     Name name = getDName(t);
/*  46 */     this.ldapTemplate.unbind(name);
/*     */   }
/*     */ 
/*     */   public List<DBServerConfig> getAll()
/*     */   {
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   public DBServerConfig getByDn(String dn)
/*     */   {
/*  58 */     return (DBServerConfig)this.ldapTemplate.lookup(dn, new DBServerConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public DBServerConfig getByDn(Name dn) {
/*  62 */     return (DBServerConfig)this.ldapTemplate.lookup(dn, new DBServerConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public void update(DBServerConfig t)
/*     */   {
/*  68 */     Name name = getDName(t);
/*  69 */     this.ldapTemplate.rebind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public Name getDName(ConfigType t)
/*     */   {
/*  76 */     DistinguishedName name = new DistinguishedName("");
/*     */ 
/*  79 */     ArrayList disNames = t.getDisNames();
/*  80 */     if (disNames == null)
/*  81 */       return null;
/*  82 */     for (int i = 0; i < disNames.size(); i++) {
/*  83 */       PathName disName = (PathName)disNames.get(i);
/*  84 */       name.add(disName.getPrefix(), disName.getValue());
/*     */     }
/*     */ 
/*  87 */     return name;
/*     */   }
/*     */ 
/*     */   private Attributes getAttributes(DBServerConfig t)
/*     */   {
/*  96 */     Attributes attrs = new BasicAttributes();
/*  97 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/*  98 */     ocattr.add("top");
/*  99 */     ocattr.add("organizationalUnit");
/* 100 */     ocattr.add("DbServerConfig");
/* 101 */     attrs.put(ocattr);
/*     */ 
/* 103 */     attrs.put("ou", t.getName());
/*     */ 
/* 105 */     if ((t.getDescription() != null) && (t.getDescription().length() > 0)) {
/* 106 */       attrs.put("description", t.getDescription());
/*     */     }
/*     */ 
/* 109 */     attrs.put("MemDbBackPwd", t.getMemdbBackpwd());
/* 110 */     attrs.put("MemDbBackurl", t.getMemdbBackurl());
/* 111 */     attrs.put("MemDbBackUser", t.getMemdbBackuser());
/* 112 */     attrs.put("MemDbScriptBackPath", t.getMemdbBackScriptPath());
/* 113 */     attrs.put("Mysqldbname", t.getMysqldb());
/* 114 */     attrs.put("MysqlDir", t.getMysqldir());
/* 115 */     attrs.put("Mysqlpwd", t.getMysqlpwd());
/* 116 */     attrs.put("Mysqluser", t.getMysqluser());
/*     */ 
/* 118 */     if ((t.getHostname() != null) && (t.getHostname().length() > 0)) {
/* 119 */       attrs.put("Hostname", t.getHostname());
/*     */     }
/* 121 */     if ((t.getIp() != null) && (t.getIp().length() > 0)) {
/* 122 */       attrs.put("IP", t.getIp());
/*     */     }
/* 124 */     if ((t.getPort() != null) && (t.getPort().length() > 0)) {
/* 125 */       attrs.put("Port", t.getPort());
/*     */     }
/*     */ 
/* 130 */     return attrs;
/*     */   }
/*     */ 
/*     */   class DBServerConfigAttributesMapper implements AttributesMapper
/*     */   {
/*     */     DBServerConfigAttributesMapper()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Object mapFromAttributes(Attributes attrs) throws NamingException
/*     */     {
/* 141 */       DBServerConfig t = new DBServerConfig();
/* 142 */       t.setName((String)attrs.get("ou").get());
/* 143 */       if (attrs.get("description") != null) {
/* 144 */         t.setDescription((String)attrs.get("description").get());
/*     */       }
/* 146 */       t.setMemdbBackurl((String)attrs.get("MemDbBackurl").get());
/* 147 */       t.setMemdbBackuser((String)attrs.get("MemDbBackUser").get());
/* 148 */       t.setMemdbBackpwd((String)attrs.get("MemDbBackPwd").get());
/* 149 */       t.setMemdbBackScriptPath((String)attrs.get("MemDbScriptBackPath").get());
/* 150 */       t.setMysqldb((String)attrs.get("Mysqldbname").get());
/* 151 */       t.setMysqluser((String)attrs.get("Mysqluser").get());
/* 152 */       t.setMysqlpwd((String)attrs.get("Mysqlpwd").get());
/* 153 */       t.setMysqldir((String)attrs.get("MysqlDir").get());
/*     */ 
/* 156 */       if (attrs.get("IP") != null)
/* 157 */         t.setIp((String)attrs.get("IP").get());
/* 158 */       if (attrs.get("Hostname") != null)
/* 159 */         t.setHostname((String)attrs.get("Hostname").get());
/* 160 */       if (attrs.get("Port") != null) {
/* 161 */         t.setPort((String)attrs.get("Port").get());
/*     */       }
/* 163 */       return t;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao
 * JD-Core Version:    0.6.0
 */