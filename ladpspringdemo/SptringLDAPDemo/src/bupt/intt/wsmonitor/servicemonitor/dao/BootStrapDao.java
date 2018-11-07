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
import bupt.intt.wsmonitor.servicemonitor.domain.BootStrapConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;

public class BootStrapDao implements GenericDao<BootStrapConfig> {
	private LdapTemplate ldapTemplate;
    //private static final String Base_Dn="ou=configs";		

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}
	
	
	@Override
	public void create(BootStrapConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.bind(name, null, getAttributes(t));
	}

	@Override
	public void delete(BootStrapConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.unbind(name);	
	}

	@Override
	public List<BootStrapConfig> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List searchAll() {
		return ldapTemplate.search("", "(objectclass=BootStrapConfig)", new BootStrapConfigAttributesMapper());
	}
	
	public BootStrapConfig searchById(String id) {
		List list = ldapTemplate.search("", "(&(objectclass=BootStrapConfig)(ou=" + id + "))", new BootStrapConfigAttributesMapper());
		if(list.size() > 0)
			return (BootStrapConfig) list.get(0);
		else
			return null;
	}
	
	public BootStrapConfig searchByCategory(String category) {
		List list = ldapTemplate.search("", "(&(objectclass=BootStrapConfig)(description=" + category + "))", new BootStrapConfigAttributesMapper());
		if(list.size() > 0)
			return (BootStrapConfig) list.get(0);
		else
			return null;
	}
	
	/**
	 * 获取的预定义的所有启动配置项目
	 * @return 配置项列表
	 */
	/*public ArrayList<BootStrapConfig> getAllDefaultBootStrapConfigs() {
		ArrayList<BootStrapConfig> _bootStrapList = new ArrayList<BootStrapConfig>();
		DistinguishedName _userName = getDNameByUid(id);
		
	}*/

	@Override
	public BootStrapConfig getByDn(String dn) {
		// TODO Auto-generated method stub
		return (BootStrapConfig)ldapTemplate.lookup(dn,new BootStrapConfigAttributesMapper());
	}
	
	public BootStrapConfig getByDn(Name dn) {
		// TODO Auto-generated method stub
		return (BootStrapConfig)ldapTemplate.lookup(dn,new BootStrapConfigAttributesMapper());
	}

	@Override
	public void update(BootStrapConfig t) {
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
	private Attributes getAttributes(BootStrapConfig t) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add(BootStrapConfig.OBJECTCLASS);
		ocattr.add(BootStrapConfig.SECONDBJECTCLASS);
		attrs.put(ocattr);
		
		attrs.put("ou",t.getName());
		
		if(t.getDescription()!=null && t.getDescription().length()>0) {
			attrs.put("description",t.getDescription());			
		}
		
		attrs.put("ProcessDisplayName",t.getDisplayName());
		attrs.put("RunPath",t.getRunPath());
		attrs.put("IconUrl",t.getIconUrl());
		attrs.put("HalfOpcityImgUrl",t.getHalfOpcityImgUrl());
		attrs.put("ImgUrl",t.getImgUrl());
		attrs.put("ProcessName",t.getProcessName());				
		attrs.put("RunArgument",t.getRunArgument());
		
		
		return attrs;
	}
	
	/**
	 * 从Ldap获取数据是的属性映射类
	 * @author Administrator
	 *
	 */
	class BootStrapConfigAttributesMapper implements AttributesMapper {
		public Object mapFromAttributes(Attributes attrs)
	      throws NamingException {
			BootStrapConfig t = new BootStrapConfig();			
			t.setName((String)attrs.get("ou").get());
			if(attrs.get("description") != null)
				t.setDescription((String)attrs.get("description").get());
			
			t.setDisplayName((String)attrs.get("ProcessDisplayName").get());
			t.setRunPath((String)attrs.get("RunPath").get());
			t.setIconUrl((String)attrs.get("IconUrl").get());
			t.setHalfOpcityImgUrl((String)attrs.get("HalfOpcityImgUrl").get());
			t.setImgUrl((String)attrs.get("ImgUrl").get());
			t.setProcessName((String)attrs.get("ProcessName").get());			
			t.setRunArgument((String)attrs.get("RunArgument").get());
			return t;
		}
	}

}
