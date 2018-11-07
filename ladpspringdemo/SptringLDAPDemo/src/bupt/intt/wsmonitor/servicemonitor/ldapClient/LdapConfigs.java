package bupt.intt.wsmonitor.servicemonitor.ldapClient;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bupt.intt.wsmonitor.servicemonitor.dao.BootStrapDao;
import bupt.intt.wsmonitor.servicemonitor.domain.BootStrapConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;

public class LdapConfigs {
	static LdapConfigs instance;
	private DBServerConfig dbServerCofig = null;
	private MessageServerConfig messageServerConfig = null;
	private MemDBConfig memDbConfig = null;
	private DataServiceConfig dataServiceConfig = null;
	private HashMap<String,String> topics = null;
	
	/**
	 * ∆Ù∂ØœÓ≈‰÷√
	 */
	private ArrayList<BootStrapConfig> bootStrapList;
	
	ldapHelper clientHelper;
	
	public static LdapConfigs Instance(String name) {
		return instance = new LdapConfigs(name);
	}
	
	public static LdapConfigs Instance(String name,String iocFilename) {
		return instance = new LdapConfigs(name,iocFilename);
	}

	public LdapConfigs(String name) {
		/*ApplicationContext context = new ClassPathXmlApplicationContext(
				"testContext.xml");
		clientHelper = (ldapHelper) context.getBean("clientHelper");
		ArrayList configList =clientHelper.listConfigs(name);
		for(int i = 0; i< configList.size(); i++) {
			Object _item = configList.get(i);
			if(_item instanceof DBServerConfig) {
				setDbServerCofig((DBServerConfig)_item);
			} else if(_item instanceof MessageServerConfig) {
				setMessageServerConfig((MessageServerConfig)_item);
			} else if(_item instanceof MemDBConfig) {
				setMemDbConfig((MemDBConfig)_item);
			} else if(_item instanceof DataServiceConfig) {
				setDataServiceConfig((DataServiceConfig)_item);
			} else if(_item instanceof ConfigType) {
				ConfigType type = (ConfigType)_item;
				setBootStrapList(getBootStrapConfigByName(clientHelper,type.getCatetory()));
			}
		}
		
		topics = clientHelper.getAllTopics();*/
		this(name, "testContext.xml");
	}
	
	public LdapConfigs(String name, String iocFilename) {
		ApplicationContext context = new FileSystemXmlApplicationContext(
				iocFilename);
		clientHelper = (ldapHelper) context.getBean("clientHelper");
		ArrayList configList = clientHelper.listConfigs(name);
		for (int i = 0; i < configList.size(); i++) {
			Object _item = configList.get(i);
			if (_item instanceof DBServerConfig) {
				setDbServerCofig((DBServerConfig) _item);
			} else if (_item instanceof MessageServerConfig) {
				setMessageServerConfig((MessageServerConfig) _item);
			} else if (_item instanceof MemDBConfig) {
				setMemDbConfig((MemDBConfig) _item);
			} else if (_item instanceof DataServiceConfig) {
				setDataServiceConfig((DataServiceConfig) _item);
			} else if (_item instanceof ConfigType) {
				ConfigType type = (ConfigType) _item;
				setBootStrapList(getBootStrapConfigByName(clientHelper, type
						.getCatetory()));
			}
		}

topics = clientHelper.getAllTopics();
	}
	
	
	public void setDbServerCofig(DBServerConfig dbServerCofig) {
		this.dbServerCofig = dbServerCofig;
	}

	public DBServerConfig getDbServerCofig() {
		return dbServerCofig;
	}

	public void setMessageServerConfig(MessageServerConfig messageServerConfig) {
		this.messageServerConfig = messageServerConfig;
	}

	public MessageServerConfig getMessageServerConfig() {
		return messageServerConfig;
	}

	public void setMemDbConfig(MemDBConfig memDbConfig) {
		this.memDbConfig = memDbConfig;
	}

	public MemDBConfig getMemDbConfig() {
		return memDbConfig;
	}

	public void setDataServiceConfig(DataServiceConfig dataServiceConfig) {
		this.dataServiceConfig = dataServiceConfig;
	}

	public DataServiceConfig getDataServiceConfig() {
		return dataServiceConfig;
	}

	public void setTopics(HashMap<String,String> topics) {
		this.topics = topics;
	}

	public HashMap<String,String> getTopics() {
		return topics;
	}

	public void setBootStrapList(ArrayList<BootStrapConfig> bootStrapList) {
		this.bootStrapList = bootStrapList;
	}

	public ArrayList<BootStrapConfig> getBootStrapList() {
		return bootStrapList;
	}
	
	public ArrayList<BootStrapConfig> getBootStrapConfigByName(ldapHelper helper,String name) {
		String[] bootStrapConfigName = name.split(",");
		ArrayList<BootStrapConfig> configList = new ArrayList<BootStrapConfig>();
		BootStrapDao bootStrapDao = helper.getBootStrapDao();
		for(String configName : bootStrapConfigName) {
			BootStrapConfig bootStrapConfig = bootStrapDao.searchByCategory(configName);
			configList.add(bootStrapConfig);			
		}
		return configList;
	}
	
	
	
}
