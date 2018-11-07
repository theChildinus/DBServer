package bupt.intt.wsmonitor.servicemonitor.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.NameClassPairCallbackHandler;

import bupt.intt.wsmonitor.servicemonitor.dao.PersonDao.PeopleAttributesMapper;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import bupt.intt.wsmonitor.servicemonitor.domain.Person;
import bupt.intt.wsmonitor.servicemonitor.genericDao.GenericDao;

public class ConfigTypeDao implements GenericDao<ConfigType> {
	private LdapTemplate ldapTemplate;
    //private static final String Base_Dn="ou=configs";		

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	@Override
	public void create(ConfigType t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.bind(name, null, getAttributes(t));
		
	}

	@Override
	public void delete(ConfigType t) {
		// TODO Auto-generated method stub
		/*Name name = getDName(t);
		ldapTemplate.unbind(name);*/
		ArrayList<ConfigType> _children = listSubType(t);
		for(ConfigType _type : _children) {
			this.delete(_type);
		}
		ldapTemplate.unbind(getDName(t));
	}
	
	public void deleteChinldren(ConfigType t) {
		ArrayList<ConfigType> _children = listSubType(t);
		for(ConfigType _type : _children) {
			this.delete(_type);
		}
	}

	@Override
	public List<ConfigType> getAll() {
		// TODO Auto-generated method stub
		//String filter = "(objectclass=" + + ")";
		//return ldapTemplate.search(Base_Dn, filter, new ConfigTypeAttributesMapper());
		return null;
	}

	@Override
	/*
	 * @ parameter dn : DistinguishedName倒写：从小到大 
	 */
	public ConfigType getByDn(String dn) {
		// TODO Auto-generated method stub
		return (ConfigType)ldapTemplate.lookup(dn,new ConfigTypeAttributesMapper());			
	}
	
	public ConfigType getByDn(Name dn) {
		return (ConfigType)ldapTemplate.lookup(dn,new ConfigTypeAttributesMapper());
	}

	@Override
	public void update(ConfigType t) {
		// TODO Auto-generated method stub
		Name name = getDName(t);
		ldapTemplate.rebind(name, null, getAttributes(t));
	}
	
	class ConfigTypeAttributesMapper implements AttributesMapper {
		public Object mapFromAttributes(Attributes attrs)
	      throws NamingException {
			ConfigType t = new ConfigType();			
			t.setName((String)attrs.get("ou").get());
			if(attrs.get("description") != null)
				t.setDescription((String)attrs.get("description").get());
			if(attrs.get("businessCategory") != null)
				t.setCatetory((String)attrs.get("businessCategory").get());
			if(attrs.get("l") != null)
				t.setComputerName((String)attrs.get("l").get());
			
			return t;
		}
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
	
	public Name getDName(ArrayList<PathName> lists) {
		DistinguishedName name = new DistinguishedName("");
		/*name.add("ou","people");
		name.add("ou",t.getName());*/
		//ArrayList<PathName> disNames = t.getDisNames();
		if(lists == null)
			return null;
		for(int i = 0; i < lists.size(); i++) {
			PathName disName = lists.get(i);
			name.add(disName.getPrefix(),disName.getValue());			
		}
		return name;
	}
	
	private Attributes getAttributes(ConfigType t) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add(ConfigType.OBJECTCLASS);
		attrs.put(ocattr);
		
		attrs.put("ou",t.getName());
		
		if(t.getDescription()!=null && t.getDescription().length()>0) {
			attrs.put("description",t.getDescription());			
		}		
		if(t.getCatetory()!=null && t.getCatetory().length()>0) {
			attrs.put("businessCategory",t.getCatetory());			
		}
		if(t.getComputerName()!=null && t.getComputerName().length()>0) {
			attrs.put("l",t.getComputerName());			
		}
		
		
		
		return attrs;
	}

	
	public ArrayList<ConfigType> listSubType(ConfigType t) {
		ArrayList<ConfigType> _subTypes = new ArrayList<ConfigType>();
		List _subItems = ldapTemplate.list(getDName(t));
		for(Iterator iterator = _subItems.iterator();iterator.hasNext();) {
			Object _item = iterator.next();
			if(_item.toString().startsWith("ou=")) {
				String _value = _item.toString().substring(3);
				
				ArrayList<PathName> _pathName = (ArrayList<PathName>) t.getDisNames().clone();
				_pathName.add(new PathName("ou",_value));
				
				ConfigType type = this.getByDn(this.getDName(_pathName));
				type.setDisNames(_pathName);
				
				
				_subTypes.add(type);
			}
		}
		
		
		return _subTypes;
		
	}
	
	public ArrayList<ConfigType> listAllChildren(ConfigType t) {
		ArrayList<ConfigType> _children = new ArrayList<ConfigType>();
		
		ArrayList<ConfigType> _nextLayer = listSubType(t);
		for(ConfigType _child : _nextLayer) {
			ArrayList<ConfigType> _childrenOfNextLevel = listAllChildren(_child);
			for(ConfigType _childNextLayer : _childrenOfNextLevel) {
				_children.add(_childNextLayer);
			}
			_children.add(_child);
		}
		
		return _children;
	}

}
