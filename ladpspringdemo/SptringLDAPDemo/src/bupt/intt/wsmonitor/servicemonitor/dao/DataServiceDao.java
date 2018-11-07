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

import bupt.intt.wsmonitor.servicemonitor.dao.MemDBDao.MemDBAttributesMapper;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;

public class DataServiceDao  implements GenericDao<DataServiceConfig>{
	private LdapTemplate ldapTemplate;
    //private static final String Base_Dn="ou=configs";		

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}
	
	
	@Override
	public void create(DataServiceConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.bind(name, null, getAttributes(t));
	}

	@Override
	public void delete(DataServiceConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.unbind(name);	
	}

	@Override
	public List<DataServiceConfig> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataServiceConfig getByDn(String dn) {
		// TODO Auto-generated method stub
		return (DataServiceConfig)ldapTemplate.lookup(dn,new DataServiceConfigAttributesMapper());
	}
	
	public DataServiceConfig getByDn(Name dn) {
		// TODO Auto-generated method stub
		return (DataServiceConfig)ldapTemplate.lookup(dn,new DataServiceConfigAttributesMapper());
	}

	@Override
	public void update(DataServiceConfig t) {
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
	
	private Attributes getAttributes(DataServiceConfig t) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add(DataServiceConfig.OBJECTCLASS);
		ocattr.add(DataServiceConfig.SECONDBJECTCLASS);
		attrs.put(ocattr);
		
		attrs.put("ou",t.getName());
		
		if(t.getDescription()!=null && t.getDescription().length()>0) {
			attrs.put("description",t.getDescription());			
		}
		
		attrs.put("BrokerURI",t.getBrokerURI());
		attrs.put("customerUri",t.getCustomerURI());
		attrs.put("DefaultReadCount",t.getDefaultReadCount());
		attrs.put("Topics",t.getTopics());
		attrs.put("CreatePullPointURI",t.getCreatePullPointURI());
		attrs.put("JMSurl",t.getJmsurl());
		attrs.put("RemoteJdbcUrl",t.getRemoteJdbcUrl());
		attrs.put("RemoteJdbcUser",t.getRemoteJdbcUser());
		attrs.put("RemoteJdbcPass",t.getRemoteJdbcPass());
		
		return attrs;
	}
	
	/**
	 * 从Ldap获取数据是的属性映射类
	 * @author Administrator
	 *
	 */
	class DataServiceConfigAttributesMapper implements AttributesMapper {
		public Object mapFromAttributes(Attributes attrs)
	      throws NamingException {
			DataServiceConfig t = new DataServiceConfig();			
			t.setName((String)attrs.get("ou").get());
			if(attrs.get("description") != null)
				t.setDescription((String)attrs.get("description").get());
			
			t.setBrokerURI((String)attrs.get("BrokerURI").get());
			t.setCustomerURI((String)attrs.get("customerUri").get());
			t.setDefaultReadCount((String)attrs.get("DefaultReadCount").get());
			t.setTopics((String)attrs.get("Topics").get());			
			t.setCreatePullPointURI((String)attrs.get("CreatePullPointURI").get());
			t.setJmsurl((String)attrs.get("JMSurl").get());
			t.setRemoteJdbcUrl((String)attrs.get("RemoteJdbcUrl").get());
			t.setRemoteJdbcUser((String)attrs.get("RemoteJdbcUser").get());
			t.setRemoteJdbcPass((String)attrs.get("RemoteJdbcPass").get());
			return t;
		}
	}

}
