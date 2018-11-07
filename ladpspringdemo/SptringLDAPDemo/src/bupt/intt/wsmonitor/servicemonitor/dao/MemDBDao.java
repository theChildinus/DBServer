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

import bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao.DBServerConfigAttributesMapper;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;

public class MemDBDao implements GenericDao<MemDBConfig> {
	private LdapTemplate ldapTemplate;
    //private static final String Base_Dn="ou=configs";		

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}
	
	
	@Override
	public void create(MemDBConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.bind(name, null, getAttributes(t));
	}

	@Override
	public void delete(MemDBConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.unbind(name);	
	}

	@Override
	public List<MemDBConfig> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemDBConfig getByDn(String dn) {
		// TODO Auto-generated method stub
		return (MemDBConfig)ldapTemplate.lookup(dn,new MemDBAttributesMapper());
	}
	
	public MemDBConfig getByDn(Name dn) {
		// TODO Auto-generated method stub
		return (MemDBConfig)ldapTemplate.lookup(dn,new MemDBAttributesMapper());
	}

	@Override
	public void update(MemDBConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.rebind(name, null, getAttributes(t));
	}
	
	/**
	 * @see ConfigTypeDao.GetName(ConfigType t)
	 */
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
	
	/**
	 * 构造要新建的LDAP 属性集合：对应不同的属性内容，每个不同的配置是不同的。
	 * @param t 数据服务器配置
	 * @return  属性集合
	 */
	private Attributes getAttributes(MemDBConfig t) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add(MemDBConfig.OBJECTCLASS);
		ocattr.add(MemDBConfig.SECONDBJECTCLASS);
		attrs.put(ocattr);
		
		attrs.put("ou",t.getName());
		
		if(t.getDescription()!=null && t.getDescription().length()>0) {
			attrs.put("description",t.getDescription());			
		}
		
		attrs.put("InitHostname",t.getInitHostname());
		attrs.put("InitIP",t.getInitIP());
		attrs.put("InitPort",t.getInitPort());
		attrs.put("InitScriptPath",t.getInitScriptPath());
		attrs.put("maxConnection",t.getMaxConnection());
		attrs.put("memDbUrl",t.getMemDbUrl());
		attrs.put("minConnection",t.getMinConnection());		
		
		
		return attrs;
	}
	
	/**
	 * 从Ldap获取数据是的属性映射类
	 * @author Administrator
	 *
	 */
	class MemDBAttributesMapper implements AttributesMapper {
		public Object mapFromAttributes(Attributes attrs)
	      throws NamingException {
			MemDBConfig t = new MemDBConfig();			
			t.setName((String)attrs.get("ou").get());
			if(attrs.get("description") != null)
				t.setDescription((String)attrs.get("description").get());
			
			t.setInitHostname((String)attrs.get("InitHostname").get());
			t.setInitIP((String)attrs.get("InitIP").get());
			t.setInitPort((String)attrs.get("InitPort").get());
			t.setInitScriptPath((String)attrs.get("InitScriptPath").get());
			t.setMaxConnection((String)attrs.get("maxConnection").get());
			t.setMemDbUrl((String)attrs.get("memDbUrl").get());
			t.setMinConnection((String)attrs.get("minConnection").get());
			
			return t;
		}
	}


}
