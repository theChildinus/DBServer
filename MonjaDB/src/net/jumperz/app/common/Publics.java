package net.jumperz.app.common;
import org.apache.log4j.LogManager;

import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
import bupt.intt.wsmonitor.servicemonitor.ldapClient.LdapConfigs;



public class Publics {
	private static Publics instance = new Publics();
	public static Publics Instance() {		
		return instance;
	}	
	private String BrokerURI;
	private String CreatePullPointURI;

	
	public Publics() {				
		LogManager.resetConfiguration();		

		String local = BoundlerHelper.getInfo("localhost");     

		LdapConfigs configs= LdapConfigs.Instance(local, Commons.getInstance().getRunPath() +"testContext.xml");
		DataServiceConfig dataServiceConfig = configs.getDataServiceConfig();
		if(dataServiceConfig!=null) {
			CreatePullPointURI = dataServiceConfig.getCreatePullPointURI();
			BrokerURI = dataServiceConfig.getBrokerURI();
		}
		
		
	}

	public String getBrokerURI() {
		return BrokerURI;
	}

	public void setCreatePullPointURI(String createPullPointURI) {
		CreatePullPointURI = createPullPointURI;
	}

	public String getCreatePullPointURI() {
		return CreatePullPointURI;
	}

}
