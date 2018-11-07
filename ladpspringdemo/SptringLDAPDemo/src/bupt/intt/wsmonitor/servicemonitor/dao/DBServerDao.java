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

import bupt.intt.wsmonitor.servicemonitor.dao.MessageServerDao.MessageServerConfigAttributesMapper;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;

public class DBServerDao implements GenericDao<DBServerConfig> {
	private LdapTemplate ldapTemplate;
    //private static final String Base_Dn="ou=configs";		

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}
	
	@Override
	public void create(DBServerConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.bind(name, null, getAttributes(t));
	}

	@Override
	public void delete(DBServerConfig t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.unbind(name);	
	}

	@Override
	public List<DBServerConfig> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBServerConfig getByDn(String dn) {
		// TODO Auto-generated method stub
		return (DBServerConfig)ldapTemplate.lookup(dn,new DBServerConfigAttributesMapper());
	}
	
	public DBServerConfig getByDn(Name dn) {
		return (DBServerConfig)ldapTemplate.lookup(dn,new DBServerConfigAttributesMapper());
	}

	@Override
	public void update(DBServerConfig t) {
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
	private Attributes getAttributes(DBServerConfig t) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add(DBServerConfig.OBJECTCLASS);
		ocattr.add(DBServerConfig.SECONDBJECTCLASS);
		attrs.put(ocattr);
		
		attrs.put("ou",t.getName());
		
		if(t.getDescription()!=null && t.getDescription().length()>0) {
			attrs.put("description",t.getDescription());			
		}
		
		attrs.put("MemDbBackPwd",t.getMemdbBackpwd());
		attrs.put("MemDbBackurl",t.getMemdbBackurl());
		attrs.put("MemDbBackUser",t.getMemdbBackuser());
		attrs.put("MemDbScriptBackPath",t.getMemdbBackScriptPath());
		attrs.put("Mysqldbname",t.getMysqldb());
		attrs.put("MysqlDir",t.getMysqldir());
		attrs.put("Mysqlpwd",t.getMysqlpwd());
		attrs.put("Mysqluser",t.getMysqluser());
		
		if(t.getHostname()!=null && t.getHostname().length()>0) {
			attrs.put("Hostname",t.getHostname());			
		}
		if(t.getIp()!=null && t.getIp().length()>0) {
			attrs.put("IP",t.getIp());			
		}
		if(t.getPort()!=null && t.getPort().length()>0) {
			attrs.put("Port",t.getPort());			
		}
		
		
		
		return attrs;
	}
	
	/**
	 * 从Ldap获取数据是的属性映射类
	 * @author Administrator
	 *
	 */
	class DBServerConfigAttributesMapper implements AttributesMapper {
		public Object mapFromAttributes(Attributes attrs)
	      throws NamingException {
			DBServerConfig t = new DBServerConfig();			
			t.setName((String)attrs.get("ou").get());
			if(attrs.get("description") != null)
				t.setDescription((String)attrs.get("description").get());
			
			t.setMemdbBackurl((String)attrs.get("MemDbBackurl").get());
			t.setMemdbBackuser((String)attrs.get("MemDbBackUser").get());
			t.setMemdbBackpwd((String)attrs.get("MemDbBackPwd").get());
			t.setMemdbBackScriptPath((String)attrs.get("MemDbScriptBackPath").get());
			t.setMysqldb((String)attrs.get("Mysqldbname").get());
			t.setMysqluser((String)attrs.get("Mysqluser").get());
			t.setMysqlpwd((String)attrs.get("Mysqlpwd").get());
			t.setMysqldir((String)attrs.get("MysqlDir").get());
			
			
			if(attrs.get("IP") != null)
				t.setIp((String)attrs.get("IP").get());
			if(attrs.get("Hostname") != null)
				t.setHostname((String)attrs.get("Hostname").get());
			if(attrs.get("Port") != null)
				t.setPort((String)attrs.get("Port").get());
			
			return t;
		}
	}

}
