/*     */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*     */ 
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
/*     */ public class ConfigTypeDao
/*     */   implements GenericDao<ConfigType>
/*     */ {
/*     */   private LdapTemplate ldapTemplate;
/*     */ 
/*     */   public void setLdapTemplate(LdapTemplate ldapTemplate)
/*     */   {
/*  30 */     this.ldapTemplate = ldapTemplate;
/*     */   }
/*     */ 
/*     */   public LdapTemplate getLdapTemplate() {
/*  34 */     return this.ldapTemplate;
/*     */   }
/*     */ 
/*     */   public void create(ConfigType t)
/*     */   {
/*  40 */     Name name = getDName(t);
/*  41 */     this.ldapTemplate.bind(name, null, getAttributes(t));
/*     */   }
/*     */ 
/*     */   public void delete(ConfigType t)
/*     */   {
/*  50 */     ArrayList _children = listSubType(t);
/*  51 */     for (ConfigType _type : _children) {
/*  52 */       delete(_type);
/*     */     }
/*  54 */     this.ldapTemplate.unbind(getDName(t));
/*     */   }
/*     */ 
/*     */   public void deleteChinldren(ConfigType t) {
/*  58 */     ArrayList _children = listSubType(t);
/*  59 */     for (ConfigType _type : _children)
/*  60 */       delete(_type);
/*     */   }
/*     */ 
/*     */   public List<ConfigType> getAll()
/*     */   {
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   public ConfigType getByDn(String dn)
/*     */   {
/*  78 */     return (ConfigType)this.ldapTemplate.lookup(dn, new ConfigTypeAttributesMapper());
/*     */   }
/*     */ 
/*     */   public ConfigType getByDn(Name dn) {
/*  82 */     return (ConfigType)this.ldapTemplate.lookup(dn, new ConfigTypeAttributesMapper());
/*     */   }
/*     */ 
/*     */   public void update(ConfigType t)
/*     */   {
/*  88 */     Name name = getDName(t);
/*  89 */     this.ldapTemplate.rebind(name, null, getAttributes(t));
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
/*     */   public Name getDName(ArrayList<PathName> lists) {
/* 124 */     DistinguishedName name = new DistinguishedName("");
/*     */ 
/* 128 */     if (lists == null)
/* 129 */       return null;
/* 130 */     for (int i = 0; i < lists.size(); i++) {
/* 131 */       PathName disName = (PathName)lists.get(i);
/* 132 */       name.add(disName.getPrefix(), disName.getValue());
/*     */     }
/* 134 */     return name;
/*     */   }
/*     */ 
/*     */   private Attributes getAttributes(ConfigType t) {
/* 138 */     Attributes attrs = new BasicAttributes();
/* 139 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/* 140 */     ocattr.add("top");
/* 141 */     ocattr.add("organizationalUnit");
/* 142 */     attrs.put(ocattr);
/*     */ 
/* 144 */     attrs.put("ou", t.getName());
/*     */ 
/* 146 */     if ((t.getDescription() != null) && (t.getDescription().length() > 0)) {
/* 147 */       attrs.put("description", t.getDescription());
/*     */     }
/* 149 */     if ((t.getCatetory() != null) && (t.getCatetory().length() > 0)) {
/* 150 */       attrs.put("businessCategory", t.getCatetory());
/*     */     }
/* 152 */     if ((t.getComputerName() != null) && (t.getComputerName().length() > 0)) {
/* 153 */       attrs.put("l", t.getComputerName());
/*     */     }
/*     */ 
/* 158 */     return attrs;
/*     */   }
/*     */ 
/*     */   public ArrayList<ConfigType> listSubType(ConfigType t)
/*     */   {
/* 163 */     ArrayList _subTypes = new ArrayList();
/* 164 */     List _subItems = this.ldapTemplate.list(getDName(t));
/* 165 */     for (Iterator iterator = _subItems.iterator(); iterator.hasNext(); ) {
/* 166 */       Object _item = iterator.next();
/* 167 */       if (_item.toString().startsWith("ou=")) {
/* 168 */         String _value = _item.toString().substring(3);
/*     */ 
/* 170 */         ArrayList _pathName = (ArrayList)t.getDisNames().clone();
/* 171 */         _pathName.add(new PathName("ou", _value));
/*     */ 
/* 173 */         ConfigType type = getByDn(getDName(_pathName));
/* 174 */         type.setDisNames(_pathName);
/*     */ 
/* 177 */         _subTypes.add(type);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 182 */     return _subTypes;
/*     */   }
/*     */ 
/*     */   public ArrayList<ConfigType> listAllChildren(ConfigType t)
/*     */   {
/* 187 */     ArrayList _children = new ArrayList();
/*     */ 
/* 189 */     ArrayList _nextLayer = listSubType(t);
/* 190 */     for (ConfigType _child : _nextLayer) {
/* 191 */       ArrayList _childrenOfNextLevel = listAllChildren(_child);
/* 192 */       for (ConfigType _childNextLayer : _childrenOfNextLevel) {
/* 193 */         _children.add(_childNextLayer);
/*     */       }
/* 195 */       _children.add(_child);
/*     */     }
/*     */ 
/* 198 */     return _children;
/*     */   }
/*     */ 
/*     */   class ConfigTypeAttributesMapper
/*     */     implements AttributesMapper
/*     */   {
/*     */     ConfigTypeAttributesMapper()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Object mapFromAttributes(Attributes attrs)
/*     */       throws NamingException
/*     */     {
/*  95 */       ConfigType t = new ConfigType();
/*  96 */       t.setName((String)attrs.get("ou").get());
/*  97 */       if (attrs.get("description") != null)
/*  98 */         t.setDescription((String)attrs.get("description").get());
/*  99 */       if (attrs.get("businessCategory") != null)
/* 100 */         t.setCatetory((String)attrs.get("businessCategory").get());
/* 101 */       if (attrs.get("l") != null) {
/* 102 */         t.setComputerName((String)attrs.get("l").get());
/*     */       }
/* 104 */       return t;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.ConfigTypeDao
 * JD-Core Version:    0.6.0
 */