package bupt.intt.wsmonitor.servicemonitor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;

import bupt.intt.wsmonitor.servicemonitor.dao.ConfigTypeDao.ConfigTypeAttributesMapper;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;

public class MessageServerDao implements GenericDao<MessageServerConfig> {
	private LdapTemplate ldapTemplate;
    //private static final String Base_Dn="ou=configs";		

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}
	
	
	@Override
	public void create(MessageServerConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.bind(name, null, getAttributes(t));
	}

	@Override
	public void delete(MessageServerConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.unbind(name);	
	}

	@Override
	public List<MessageServerConfig> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageServerConfig getByDn(String dn) {
		// TODO Auto-generated method stub
		return (MessageServerConfig)ldapTemplate.lookup(dn,new MessageServerConfigAttributesMapper());
	}
	
	public MessageServerConfig getByDn(Name dn) {
		return (MessageServerConfig)ldapTemplate.lookup(dn,new MessageServerConfigAttributesMapper());
	}

	@Override
	public void update(MessageServerConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.rebind(name, null, getAttributes(t));
	}
	
	public Name getDName(ConfigType t) {
		DistinguishedName name = new DistinguishedName("");
		/*name.add("ou","people");
		name.add("ou",t.getName());*/
		ArrayList<PathName> disNames = t.getDisNames();
		if(disNames == null)
			return null;
		for(int i = 0; i < disNames.size(); i++) {
			PathName disName = disNames.get(i);
			name.add(disName.getPrefix(),disName.getValue());			
		}
		
		return name;
	}
	
	private Attributes getAttributes(MessageServerConfig t) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add(ConfigType.OBJECTCLASS);
		ocattr.add(MessageServerConfig.SECONDBJECTCLASS);
		attrs.put(ocattr);
		
		attrs.put("ou",t.getName());
		
		if(t.getDescription()!=null && t.getDescription().length()>0) {
			attrs.put("description",t.getDescription());			
		}
		
		if(t.getBrokerURI() == null) 
			throw new IllegalStateException("Broker地址不能为空");
		attrs.put("BrokerURI",t.getBrokerURI());
		
		if(t.getCreatePullPointURI() == null) 
			throw new IllegalStateException("Broker地址不能为空");
		attrs.put("CreatePullPointURI",t.getCreatePullPointURI());
		
		if(t.getIp()!=null && t.getIp().length()>0) {
			attrs.put("IP",t.getIp());			
		}
		
		return attrs;
	}
	
	class MessageServerConfigAttributesMapper implements AttributesMapper {
		public Object mapFromAttributes(Attributes attrs)
	      throws NamingException {
			MessageServerConfig t = new MessageServerConfig();			
			t.setName((String)attrs.get("ou").get());
			if(attrs.get("description") != null)
				t.setDescription((String)attrs.get("description").get());
			t.setBrokerURI((String)attrs.get("BrokerURI").get());
			t.setCreatePullPointURI((String)attrs.get("CreatePullPointURI").get());
			if(attrs.get("IP") != null)
				t.setIp((String)attrs.get("IP").get());
			
			return t;
		}
	}

}
