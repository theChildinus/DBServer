/*     */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;
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
/*     */ public class MessageServerDao
/*     */   implements GenericDao<MessageServerConfig>
/*     */ {
/*     */   private LdapTemplate ldapTemplate;
/*     */ 
/*     */   public void setLdapTemplate(LdapTemplate ldapTemplate)
/*     */   {
/*  27 */     this.ldapTemplate = ldapTemplate;
/*     */   }
/*     */ 
/*     */   public LdapTemplate getLdapTemplate() {
/*  31 */     return this.ldapTemplate;
/*     */   }
/*     */ 
/*     */   public void create(MessageServerConfig t)
/*     */   {
/*  38 */     Name name = getDName(t);
/*  39 */     this.ldapTemplate.bind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public void delete(MessageServerConfig t)
/*     */   {
/*  45 */     Name name = getDName(t);
/*  46 */     this.ldapTemplate.unbind(name);
/*     */   }
/*     */ 
/*     */   public List<MessageServerConfig> getAll()
/*     */   {
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageServerConfig getByDn(String dn)
/*     */   {
/*  58 */     return (MessageServerConfig)this.ldapTemplate.lookup(dn, new MessageServerConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public MessageServerConfig getByDn(Name dn) {
/*  62 */     return (MessageServerConfig)this.ldapTemplate.lookup(dn, new MessageServerConfigAttributesMapper());
/*     */   }
/*     */ 
/*     */   public void update(MessageServerConfig t)
/*     */   {
/*  68 */     Name name = getDName(t);
/*  69 */     this.ldapTemplate.rebind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public Name getDName(ConfigType t) {
/*  73 */     DistinguishedName name = new DistinguishedName("");
/*     */ 
/*  76 */     ArrayList disNames = t.getDisNames();
/*  77 */     if (disNames == null)
/*  78 */       return null;
/*  79 */     for (int i = 0; i < disNames.size(); i++) {
/*  80 */       PathName disName = (PathName)disNames.get(i);
/*  81 */       name.add(disName.getPrefix(), disName.getValue());
/*     */     }
/*     */ 
/*  84 */     return name;
/*     */   }
/*     */ 
/*     */   private Attributes getAttributes(MessageServerConfig t) {
/*  88 */     Attributes attrs = new BasicAttributes();
/*  89 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/*  90 */     ocattr.add("top");
/*  91 */     ocattr.add("organizationalUnit");
/*  92 */     ocattr.add("MessageServerConfig");
/*  93 */     attrs.put(ocattr);
/*     */ 
/*  95 */     attrs.put("ou", t.getName());
/*     */ 
/*  97 */     if ((t.getDescription() != null) && (t.getDescription().length() > 0)) {
/*  98 */       attrs.put("description", t.getDescription());
/*     */     }
/*     */ 
/* 101 */     if (t.getBrokerURI() == null)
/* 102 */       throw new IllegalStateException("Broker地址不能为空");
/* 103 */     attrs.put("BrokerURI", t.getBrokerURI());
/*     */ 
/* 105 */     if (t.getCreatePullPointURI() == null)
/* 106 */       throw new IllegalStateException("Broker地址不能为空");
/* 107 */     attrs.put("CreatePullPointURI", t.getCreatePullPointURI());
/*     */ 
/* 109 */     if ((t.getIp() != null) && (t.getIp().length() > 0)) {
/* 110 */       attrs.put("IP", t.getIp());
/*     */     }
/*     */ 
/* 113 */     return attrs;
/*     */   }
/*     */   class MessageServerConfigAttributesMapper implements AttributesMapper {
/*     */     MessageServerConfigAttributesMapper() {
/*     */     }
/*     */     public Object mapFromAttributes(Attributes attrs) throws NamingException {
/* 119 */       MessageServerConfig t = new MessageServerConfig();
/* 120 */       t.setName((String)attrs.get("ou").get());
/* 121 */       if (attrs.get("description") != null)
/* 122 */         t.setDescription((String)attrs.get("description").get());
/* 123 */       t.setBrokerURI((String)attrs.get("BrokerURI").get());
/* 124 */       t.setCreatePullPointURI((String)attrs.get("CreatePullPointURI").get());
/* 125 */       if (attrs.get("IP") != null) {
/* 126 */         t.setIp((String)attrs.get("IP").get());
/*     */       }
/* 128 */       return t;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.MessageServerDao
 * JD-Core Version:    0.6.0
 */