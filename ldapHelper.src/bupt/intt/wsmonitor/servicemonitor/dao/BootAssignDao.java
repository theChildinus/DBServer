/*     */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.BootAssignConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*     */ import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class BootAssignDao
/*     */   implements GenericDao<BootAssignConfig>
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
/*     */   public void create(BootAssignConfig t)
/*     */   {
/*  38 */     Name name = getDName(t);
/*  39 */     this.ldapTemplate.bind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public void delete(BootAssignConfig t)
/*     */   {
/*  52 */     this.ldapTemplate.unbind(getDName(t));
/*     */   }
/*     */ 
/*     */   public List<BootAssignConfig> getAll()
/*     */   {
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   public BootAssignConfig getByDn(String dn)
/*     */   {
/*  76 */     return (BootAssignConfig)this.ldapTemplate.lookup(dn, new BootAssginAttributesMapper());
/*     */   }
/*     */ 
/*     */   public BootAssignConfig getByDn(Name dn) {
/*  80 */     return (BootAssignConfig)this.ldapTemplate.lookup(dn, new BootAssginAttributesMapper());
/*     */   }
/*     */ 
/*     */   public void update(BootAssignConfig t)
/*     */   {
/*  86 */     Name name = getDName(t);
/*  87 */     this.ldapTemplate.rebind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public Name getDName(ConfigType t)
/*     */   {
/* 105 */     DistinguishedName name = new DistinguishedName("");
/*     */ 
/* 108 */     ArrayList disNames = t.getDisNames();
/* 109 */     if (disNames == null)
/* 110 */       return null;
/* 111 */     for (int i = 0; i < disNames.size(); i++) {
/* 112 */       PathName disName = (PathName)disNames.get(i);
/* 113 */       name.add(disName.getPrefix(), disName.getValue());
/*     */     }
/*     */ 
/* 116 */     return name;
/*     */   }
/*     */ 
/*     */   public Name getDName(ArrayList<PathName> lists) {
/* 120 */     DistinguishedName name = new DistinguishedName("");
/*     */ 
/* 124 */     if (lists == null)
/* 125 */       return null;
/* 126 */     for (int i = 0; i < lists.size(); i++) {
/* 127 */       PathName disName = (PathName)lists.get(i);
/* 128 */       name.add(disName.getPrefix(), disName.getValue());
/*     */     }
/* 130 */     return name;
/*     */   }
/*     */ 
/*     */   private Attributes getAttributes(ConfigType t) {
/* 134 */     Attributes attrs = new BasicAttributes();
/* 135 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/* 136 */     ocattr.add("top");
/* 137 */     ocattr.add("organizationalUnit");
/* 138 */     attrs.put(ocattr);
/*     */ 
/* 140 */     attrs.put("ou", t.getName());
/*     */ 
/* 142 */     if ((t.getDescription() != null) && (t.getDescription().length() > 0)) {
/* 143 */       attrs.put("description", t.getDescription());
/*     */     }
/* 145 */     if ((t.getCatetory() != null) && (t.getCatetory().length() > 0)) {
/* 146 */       attrs.put("businessCategory", t.getCatetory());
/*     */     }
/*     */ 
/* 151 */     return attrs;
/*     */   }
/*     */ 
/*     */   public ArrayList<ConfigType> listSubType(ConfigType t)
/*     */   {
/* 156 */     ArrayList _subTypes = new ArrayList();
/* 157 */     List _subItems = this.ldapTemplate.list(getDName(t));
/* 158 */     for (Iterator iterator = _subItems.iterator(); iterator.hasNext(); ) {
/* 159 */       Object _item = iterator.next();
/* 160 */       if (_item.toString().startsWith("ou=")) {
/* 161 */         String _value = _item.toString().substring(3);
/*     */ 
/* 163 */         ArrayList _pathName = (ArrayList)t.getDisNames().clone();
/* 164 */         _pathName.add(new PathName("ou", _value));
/*     */ 
/* 166 */         ConfigType type = getByDn(getDName(_pathName));
/* 167 */         type.setDisNames(_pathName);
/*     */ 
/* 170 */         _subTypes.add(type);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 175 */     return _subTypes;
/*     */   }
/*     */ 
/*     */   public ArrayList<ConfigType> listAllChildren(ConfigType t)
/*     */   {
/* 180 */     ArrayList _children = new ArrayList();
/*     */ 
/* 182 */     ArrayList _nextLayer = listSubType(t);
/* 183 */     for (ConfigType _child : _nextLayer) {
/* 184 */       ArrayList _childrenOfNextLevel = listAllChildren(_child);
/* 185 */       for (ConfigType _childNextLayer : _childrenOfNextLevel) {
/* 186 */         _children.add(_childNextLayer);
/*     */       }
/* 188 */       _children.add(_child);
/*     */     }
/*     */ 
/* 191 */     return _children;
/*     */   }
/*     */ 
/*     */   class BootAssginAttributesMapper
/*     */     implements AttributesMapper
/*     */   {
/*     */     BootAssginAttributesMapper()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Object mapFromAttributes(Attributes attrs)
/*     */       throws NamingException
/*     */     {
/*  93 */       BootAssignConfig t = new BootAssignConfig();
/*  94 */       t.setName((String)attrs.get("ou").get());
/*  95 */       if (attrs.get("description") != null)
/*  96 */         t.setDescription((String)attrs.get("description").get());
/*  97 */       if (attrs.get("businessCategory") != null) {
/*  98 */         t.setCatetory((String)attrs.get("businessCategory").get());
/*     */       }
/* 100 */       return t;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.BootAssignDao
 * JD-Core Version:    0.6.0
 */