package bupt.intt.wsmonitor.servicemonitor.dao;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import bupt.intt.wsmonitor.servicemonitor.domain.Person;
import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;

public class PersonDao implements GenericDao<Person> {
    private LdapTemplate ldapTemplate;
    private static final String Base_Dn="ou=people";
		

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}
	
	
	class PeopleAttributesMapper implements AttributesMapper {
		public Object mapFromAttributes(Attributes attrs)
	      throws NamingException {
			Person p = new Person();
			p.setCn((String)attrs.get("cn").get());
			p.setSn((String)attrs.get("sn").get());
			p.setDescription((String)attrs.get("description").get());
			p.setGivenname((String)attrs.get("givenname").get());
			p.setMail((String)attrs.get("mail").get());
			//p.setManager((String)attrs.get("manager").get());
			p.setUid((String)attrs.get("uid").get());
			//p.setUserpassword((String)attrs.get("userpassword").get());
			return p;
		}
	}
	
	private Name getDName(Person p) {
		DistinguishedName name = new DistinguishedName("");
		name.add("ou","people");
		name.add("cn",p.getCn());	
		
		return name;
	}
    
	private Attributes getAttributes(Person p) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add("person");
		attrs.put(ocattr);
		attrs.put("cn",p.getCn());
		attrs.put("sn",p.getSn());
		if(p.getDescription()!=null && p.getDescription().length()>0) {
			attrs.put("description",p.getDescription());			
		}
		if(p.getGivenname()!=null && p.getGivenname().length()>0) {
			attrs.put("givenname",p.getGivenname());			
		}
		if(p.getGivenname()!=null && p.getGivenname().length()>0) {
			attrs.put("givenname",p.getGivenname());			
		}
		if(p.getMail()!=null && p.getMail().length()>0) {
			attrs.put("mail",p.getMail());			
		}
		if(p.getManager()!=null && p.getManager().length()>0) {
			attrs.put("manager",p.getManager());			
		}
		if(p.getUid()!=null && p.getUid().length()>0) {
			attrs.put("uid",p.getUid());			
		}
		if(p.getUserpassword()!=null && p.getUserpassword().length()>0) {
			attrs.put("userpassword",p.getUserpassword());			
		}
		return attrs;
	}

	@Override
	public void create(Person p) {
		// TODO Auto-generated method stub
		Name name = getDName(p);
		ldapTemplate.bind(name, null, getAttributes(p));
		
		
	}

	@Override
	public void delete(Person p) {
		// TODO Auto-generated method stub
		Name name = getDName(p);
		ldapTemplate.unbind(name);		
	}

	@Override
	public List<Person> getAll() {
		// TODO Auto-generated method stub
		String filter = "(objectclass=person)";
		return ldapTemplate.search(Base_Dn, filter, new PeopleAttributesMapper());
		
	}

	@Override
	public Person getByDn(String dn) {
		// TODO Auto-generated method stub
		return (Person)ldapTemplate.lookup("uid",new PeopleAttributesMapper());
	}

	@Override
	public void update(Person p) {
		// TODO Auto-generated method stub
		Name name = getDName(p);
		ldapTemplate.rebind(name, null, getAttributes(p));
		
	}
}
