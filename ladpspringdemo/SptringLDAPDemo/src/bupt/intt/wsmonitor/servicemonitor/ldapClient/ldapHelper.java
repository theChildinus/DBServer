package bupt.intt.wsmonitor.servicemonitor.ldapClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.Name;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;

import bupt.intt.wsmonitor.servicemonitor.dao.BootAssignDao;
import bupt.intt.wsmonitor.servicemonitor.dao.BootStrapDao;
import bupt.intt.wsmonitor.servicemonitor.dao.ConfigTypeDao;
import bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao;
import bupt.intt.wsmonitor.servicemonitor.dao.DataServiceDao;
import bupt.intt.wsmonitor.servicemonitor.dao.MemDBDao;
import bupt.intt.wsmonitor.servicemonitor.dao.MessageServerDao;
import bupt.intt.wsmonitor.servicemonitor.domain.BootAssignConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;

public class ldapHelper {
	
	private LdapTemplate ldapTemplate;	
	private ConfigTypeDao configTypeDao;
	private DBServerDao dbServerDao;
	private MessageServerDao messageServerDao;
	private MemDBDao memDBDao;
	private DataServiceDao dataServiceDao;
	private BootStrapDao bootStrapDao;
	private BootAssignDao bootAssignDao;
	
	
	// private static final String Base_Dn="ou=configs";

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public ArrayList listConfigs(String id) {
		ArrayList configs = new ArrayList();
		
		DistinguishedName _userName = getDNameByUid(id);
		System.out.println("***************"+_userName);
		ConfigType type = configTypeDao.getByDn(_userName);
		String positionId = type.getCatetory();

		List _subItems = ldapTemplate.list(_userName);
		for (Iterator iterator = _subItems.iterator(); iterator.hasNext();) {			
			Object _item = iterator.next();
			String _itemName = _item.toString().substring(3);

			_userName = getDNameByUid(id);
			_userName.add("ou", _itemName);
			
			
			ArrayList<PathName> pathNames = new ArrayList<PathName>();			
			pathNames.add(new PathName("ou", "configs"));
			pathNames.add(new PathName("ou", "userConfigs"));
			pathNames.add(new PathName("ou", id));			

			if (_itemName.contains(TypeStringMapper.DATASERVER)) {
				
				/*DBServerDao dbServerDao = (DBServerDao) context
						.getBean("dbServerConfigDao");*/
				DBServerConfig config = getDbServerDao().getByDn(_userName);
			    pathNames.add(new PathName("ou",_itemName));
			    config.setDisNames(pathNames);
				configs.add(config);
				
			} else if (_itemName
					.contains(TypeStringMapper.MESSAGESERVER)) {
				
				/*MessageServerDao dbServerDao = (MessageServerDao) context
						.getBean("messageServerConfigDao");*/
				MessageServerConfig config = getMessageServerDao().getByDn(_userName);
				pathNames.add(new PathName("ou",_itemName));
			    config.setDisNames(pathNames);
				configs.add(config);

			} else if (_itemName.contains(TypeStringMapper.MEMDB)) {

				/*MemDBDao dbServerDao = (MemDBDao) context.getBean("memDbDao");*/
				MemDBConfig config = getMemDBDao().getByDn(_userName);
				pathNames.add(new PathName("ou",_itemName));
			    config.setDisNames(pathNames);
				configs.add(config);

			} else if (_itemName
					.contains(TypeStringMapper.MESSAGESERVICE)) {
				
				/*DataServiceDao dbServerDao = (DataServiceDao) context.getBean("dbServiceDao");*/
				DataServiceConfig config = getDataServiceDao().getByDn(_userName);
				config.setTopics(getTopicByPositionId(positionId));
				pathNames.add(new PathName("ou",_itemName));
			    config.setDisNames(pathNames);
				configs.add(config);

			} else if(_itemName.contains(TypeStringMapper.BOOTASSGIN)) {
				BootAssignConfig configType = this.getBootAssignDao().getByDn(_userName);
				pathNames.add(new PathName("ou",_itemName));
				configType.setDisNames(pathNames);
				configs.add(configType);
			}
		}
		return configs;
	}
	
	/**
	 * 得到用户级配置中的所有用户
	 * @return 所有用户
	 */
	public ArrayList<ConfigType> getAllUsers() {
		ArrayList<ConfigType> configs = new ArrayList<ConfigType>();
		DistinguishedName name = new DistinguishedName("");
		name.add("ou", "configs");
		name.add("ou", "userConfigs");

		List _subItems = ldapTemplate.list(name);
		for (Iterator iterator = _subItems.iterator(); iterator.hasNext();) {			
			Object _item = iterator.next();
			String _itemName = _item.toString().substring(3);

			DistinguishedName name2 = new DistinguishedName("");
			name2.add("ou", "configs");
			name2.add("ou", "userConfigs");
			name2.add("ou",_itemName);
			
			
			ArrayList<PathName> pathNames = new ArrayList<PathName>();			
			pathNames.add(new PathName("ou", "configs"));
			pathNames.add(new PathName("ou", "userConfigs"));
			pathNames.add(new PathName("ou", _itemName));
			
			ConfigType config = this.getConfigTypeDao().getByDn(name2);			
			config.setDisNames(pathNames);
			configs.add(config);
				
		
		}
		return configs;
	}

	public DistinguishedName getDNameByUid(String id) {
		DistinguishedName name = new DistinguishedName("");
		name.add("ou", "configs");
		name.add("ou", "userConfigs");
		name.add("ou", id);
		return name;
	}
	
	public String getTopicByPositionId(String id) {
		DistinguishedName name = new DistinguishedName("");
		name.add("ou", "configs");
		name.add("ou", "positionConfigs");
		name.add("ou",id);
		List _subItems = ldapTemplate.list(name);
		String topics = null;
		for(Object obj : _subItems) {
			if(obj.toString().contains(TypeStringMapper.MESSAGESERVICE)) {
				String ouName = obj.toString();
				name.add("ou",ouName.substring(ouName.indexOf("=") + 1));
				DataServiceConfig serviceConfig = dataServiceDao.getByDn(name);
				topics = serviceConfig.getTopics();
				break;
			}
		}
		return topics;
		
	}
	
	public HashMap<String,String> getAllTopics() {
		HashMap<String,String> _maps = new HashMap<String,String>();
		DistinguishedName name = new DistinguishedName("");
		name.add("ou", "configs");
		name.add("ou", "baseConfigs");
		List _subItems = ldapTemplate.list(name);
		
		for(Object obj : _subItems) {			
			
			String ouName = obj.toString();		
			
			DistinguishedName name2 = new DistinguishedName("");
			name2.add("ou","configs");
			name2.add("ou","baseConfigs");
			name2.add("ou",ouName.substring(ouName.indexOf("=") + 1));
			
			ConfigType type = configTypeDao.getByDn(name2);
			
			if(type.getDescription()!= null && type.getDescription().equals("订阅类型")) {
				ArrayList<PathName> _pathNames = new ArrayList<PathName>();
				_pathNames.add(new PathName("ou", "configs"));
				_pathNames.add(new PathName("ou", "baseConfigs"));
				_pathNames.add(new PathName("ou",ouName.substring(ouName.indexOf("=") + 1)));
				
				
				ConfigType subscription = new ConfigType();
				subscription.setDisNames(_pathNames);
				ArrayList<ConfigType> _children = configTypeDao.listAllChildren(subscription);
				for(ConfigType _child : _children) {
					_maps.put(_child.getDescription(), _child.getCatetory());
				}
				break;
			}
			
		}
		return _maps;
	}

	public void setDbServerDao(DBServerDao dbServerDao) {
		this.dbServerDao = dbServerDao;
	}

	public DBServerDao getDbServerDao() {
		return dbServerDao;
	}

	public void setMessageServerDao(MessageServerDao messageServerDao) {
		this.messageServerDao = messageServerDao;
	}

	public MessageServerDao getMessageServerDao() {
		return messageServerDao;
	}

	public void setMemDBDao(MemDBDao memDBDao) {
		this.memDBDao = memDBDao;
	}

	public MemDBDao getMemDBDao() {
		return memDBDao;
	}

	public void setDataServiceDao(DataServiceDao dataServiceDao) {
		this.dataServiceDao = dataServiceDao;
	}

	public DataServiceDao getDataServiceDao() {
		return dataServiceDao;
	}

	public void setConfigTypeDao(ConfigTypeDao configTypeDao) {
		this.configTypeDao = configTypeDao;
	}

	public ConfigTypeDao getConfigTypeDao() {
		return configTypeDao;
	}

	public void setBootStrapDao(BootStrapDao bootStrapDao) {
		this.bootStrapDao = bootStrapDao;
	}

	public BootStrapDao getBootStrapDao() {
		return bootStrapDao;
	}
	
	public void testConnection() {
		DistinguishedName name = new DistinguishedName("");
		name.add("ou", "configs");
		name.add("ou", "baseConfigs");
		List _subItems = ldapTemplate.list(name);
		if(_subItems.size() > 0) {
			
		}
	}
	
	/**
	 * 判断用户是否存在
	 * @param name
	 * @return
	 */
	public boolean testUserExist(String name) {
		try {
			DistinguishedName _userName = getDNameByUid(name);
			ConfigType type = configTypeDao.getByDn(_userName);
			if(type == null)
				return false;
			else 
				return true;
		} catch(Exception ex) {
			return false;
		}
		
	}
	
	/**
	 * 创建用户级别配置中的用户
	 * @param username 用户ip
	 */
	public void createUser(String username) {
		ConfigType type = new ConfigType();
		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		
		pathNames.add(new PathName("ou", "configs"));
		pathNames.add(new PathName("ou", "userConfigs"));
		pathNames.add(new PathName("ou", username));
		
		type.setName(username);
		type.setDescription(username);
		type.setDisNames(pathNames);
		InetAddress addr;
		
		try {
			addr = InetAddress.getLocalHost();
			String localHostname = addr.getHostName().toString();
			type.setComputerName(localHostname);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		configTypeDao.create(type);
	}
	
	/**
	 * 分配数据采集席位
	 * @param user 用户名
	 */
	public void assginCollectionPosition(String user) {
		assginPosition(user,"数据采集");
		
	}
	
	/**
	 * 分配数据管理席位
	 * @param user 用户名
	 */
	public void assginDbServerPosition(String user) {
		assginPosition(user,"数据管理");
	}
	
	/**
	 * 分配席位
	 * @param name 用户名称
	 * @param position 席位名称
	 */
	public void assginPosition(String name,String position) {
		DistinguishedName _userName = getDNameByUid(name);
		ConfigType type = configTypeDao.getByDn(_userName);
		if(type == null)
			return;
		
		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		pathNames.add(new PathName("ou","configs"));
		pathNames.add(new PathName("ou","positionConfigs"));

		ConfigTypeDao dao = this.getConfigTypeDao();
		ConfigType t = dao.getByDn(dao.getDName(pathNames));
		t.setDisNames(pathNames);

		ArrayList<ConfigType> _positions = dao.listSubType(t);
		for(ConfigType _position : _positions) {
			if(_position.getDescription().contains((position))){ //"数据采集"))) {
				String _id = _position.getName();
				String _description = _position.getDescription();
				type.setCatetory(_id);
				type.setDescription(type.getDescription() + "[" + _description + "]");
				
				ArrayList<PathName> pathNames2 = new ArrayList<PathName>();
				pathNames2.add(new PathName("ou","configs"));
				pathNames2.add(new PathName("ou","userConfigs"));
				pathNames2.add(new PathName("ou",type.getName()));				
				type.setDisNames(pathNames2);
				
				this.getConfigTypeDao().update(type);
				
			}
		}
	}

	public void setBootAssignDao(BootAssignDao bootAssignDao) {
		this.bootAssignDao = bootAssignDao;
	}

	public BootAssignDao getBootAssignDao() {
		return bootAssignDao;
	}	
}
