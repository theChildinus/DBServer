/*     */ package bupt.intt.wsmonitor.servicemonitor.dao;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.Person;
/*     */ import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;
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
/*     */ public class PersonDao
/*     */   implements GenericDao<Person>
/*     */ {
/*     */   private LdapTemplate ldapTemplate;
/*     */   private static final String Base_Dn = "ou=people";
/*     */ 
/*     */   public void setLdapTemplate(LdapTemplate ldapTemplate)
/*     */   {
/*  24 */     this.ldapTemplate = ldapTemplate;
/*     */   }
/*     */ 
/*     */   public LdapTemplate getLdapTemplate() {
/*  28 */     return this.ldapTemplate;
/*     */   }
/*     */ 
/*     */   private Name getDName(Person p)
/*     */   {
/*  49 */     DistinguishedName name = new DistinguishedName("");
/*  50 */     name.add("ou", "people");
/*  51 */     name.add("cn", p.getCn());
/*     */ 
/*  53 */     return name;
/*     */   }
/*     */ 
/*     */   private Attributes getAttributes(Person p) {
/*  57 */     Attributes attrs = new BasicAttributes();
/*  58 */     BasicAttribute ocattr = new BasicAttribute("objectclass");
/*  59 */     ocattr.add("top");
/*  60 */     ocattr.add("person");
/*  61 */     attrs.put(ocattr);
/*  62 */     attrs.put("cn", p.getCn());
/*  63 */     attrs.put("sn", p.getSn());
/*  64 */     if ((p.getDescription() != null) && (p.getDescription().length() > 0)) {
/*  65 */       attrs.put("description", p.getDescription());
/*     */     }
/*  67 */     if ((p.getGivenname() != null) && (p.getGivenname().length() > 0)) {
/*  68 */       attrs.put("givenname", p.getGivenname());
/*     */     }
/*  70 */     if ((p.getGivenname() != null) && (p.getGivenname().length() > 0)) {
/*  71 */       attrs.put("givenname", p.getGivenname());
/*     */     }
/*  73 */     if ((p.getMail() != null) && (p.getMail().length() > 0)) {
/*  74 */       attrs.put("mail", p.getMail());
/*     */     }
/*  76 */     if ((p.getManager() != null) && (p.getManager().length() > 0)) {
/*  77 */       attrs.put("manager", p.getManager());
/*     */     }
/*  79 */     if ((p.getUid() != null) && (p.getUid().length() > 0)) {
/*  80 */       attrs.put("uid", p.getUid());
/*     */     }
/*  82 */     if ((p.getUserpassword() != null) && (p.getUserpassword().length() > 0)) {
/*  83 */       attrs.put("userpassword", p.getUserpassword());
/*     */     }
/*  85 */     return attrs;
/*     */   }
/*     */ 
/*     */   public void create(Person p)
/*     */   {
/*  91 */     Name name = getDName(p);
/*  92 */     this.ldapTemplate.bind(name, null, getAttributes(p));
/*     */   }
/*     */ 
/*     */   public void delete(Person p)
/*     */   {
/* 100 */     Name name = getDName(p);
/* 101 */     this.ldapTemplate.unbind(name);
/*     */   }
/*     */ 
/*     */   public List<Person> getAll()
/*     */   {
/* 107 */     String filter = "(objectclass=person)";
/* 108 */     return this.ldapTemplate.search("ou=people", filter, new PeopleAttributesMapper());
/*     */   }
/*     */ 
/*     */   public Person getByDn(String dn)
/*     */   {
/* 115 */     return (Person)this.ldapTemplate.lookup("uid", new PeopleAttributesMapper());
/*     */   }
/*     */ 
/*     */   public void update(Person p)
/*     */   {
/* 121 */     Name name = getDName(p);
/* 122 */     this.ldapTemplate.rebind(name, null, getAttributes(p));
/*     */   }
/*     */ 
/*     */   class PeopleAttributesMapper
/*     */     implements AttributesMapper
/*     */   {
/*     */     PeopleAttributesMapper()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Object mapFromAttributes(Attributes attrs)
/*     */       throws NamingException
/*     */     {
/*  35 */       Person p = new Person();
/*  36 */       p.setCn((String)attrs.get("cn").get());
/*  37 */       p.setSn((String)attrs.get("sn").get());
/*  38 */       p.setDescription((String)attrs.get("description").get());
/*  39 */       p.setGivenname((String)attrs.get("givenname").get());
/*  40 */       p.setMail((String)attrs.get("mail").get());
/*     */ 
/*  42 */       p.setUid((String)attrs.get("uid").get());
/*     */ 
/*  44 */       return p;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.dao.PersonDao
 * JD-Core Version:    0.6.0
 */