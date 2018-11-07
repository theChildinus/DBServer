package edu.bupt.wsmonitor.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;


import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
import bupt.intt.wsmonitor.servicemonitor.ldapClient.LdapConfigs;

public class Publics {
	private static Publics instance = new Publics();
	public static Publics Instance() {
		//DOMConfigurator.configure("c:/log4j.properties");		
		return instance;
	}

	//订阅配置
	private String customerURI;
	//�� �ͻ��˵�����
	private ArrayList<String> topics;
	//WSN Broker URI
	private String BrokerURI;
	//���� �ͻ��˶˿ڵ�ע����?
	private String CreatePullPointURI;
	
	private String JmsUrl;
	
	private int DefaultReadCount;
	
	private String RemoteJdbcUrl;
	
	private String RemoteUser;
	
	private String RemotePass; 
	/* 运行目录 */
	private String runPath;
	
	/* 主题--数据库表 映射 */
	private HashMap<String,String> topicMapper;
	
	/* 数据报警计算限�? */
	private double compareLimit;
	private double warnLimit;
	/** 
	 * 
	 */
	//private ArrayList<BootStrapConfig> bootStrapList;
	
	public Publics() {
		
		runPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		String fileLocationPrefix = "file:/";
		
		if(runPath.startsWith(fileLocationPrefix)) {
			runPath = runPath.substring(fileLocationPrefix.length());
		}
		runPath = runPath.substring(0,runPath.lastIndexOf("/") + 1);//File.separator));
		
		try {
			runPath = java.net.URLDecoder.decode(runPath,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//System.out.println(runPath);
		LogManager.resetConfiguration();		

		String machineName = PublicResource.getString("host");     

		String runPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		System.out.println(runPath);
		LdapConfigs configs= LdapConfigs.Instance(machineName, "testContext.xml");
		DataServiceConfig dataServiceConfig = configs.getDataServiceConfig();
		
				
		topicMapper = configs.getTopics();
		
		if(dataServiceConfig!=null) {
			customerURI = dataServiceConfig.getCustomerURI();
			String topicsString = dataServiceConfig.getTopics();
			String[] topicArray = topicsString.split(",");
			topics = new ArrayList<String>();
			for(String t : topicArray) {
				topics.add(t);
			}
			CreatePullPointURI = dataServiceConfig.getCreatePullPointURI();
			BrokerURI = dataServiceConfig.getBrokerURI();
			DefaultReadCount = Integer.valueOf(dataServiceConfig.getDefaultReadCount());
			JmsUrl = dataServiceConfig.getJmsurl();
			RemoteJdbcUrl = dataServiceConfig.getRemoteJdbcUrl();
			RemoteUser = dataServiceConfig.getRemoteJdbcUser();
			RemotePass = dataServiceConfig.getRemoteJdbcPass();
		}
		
		
	}

	public String getCustomerURI() {
		return customerURI;
	}

	public ArrayList<String> getTopics() {
		return topics;
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

	public void setDefaultReadCount(int defaultReadCount) {
		DefaultReadCount = defaultReadCount;
	}

	public int getDefaultReadCount() {
		return DefaultReadCount;
	}

	public void setJmsUrl(String jmsUrl) {
		JmsUrl = jmsUrl;
	}

	public String getJmsUrl() {
		return JmsUrl;
	}

	public void setRemoteJdbcUrl(String remoteJdbcUrl) {
		RemoteJdbcUrl = remoteJdbcUrl;
	}

	public String getRemoteJdbcUrl() {
		return RemoteJdbcUrl;
	}

	public void setRemoteUser(String remoteUser) {
		RemoteUser = remoteUser;
	}

	public String getRemoteUser() {
		return RemoteUser;
	}

	public void setRemotePass(String remotePass) {
		RemotePass = remotePass;
	}

	public String getRemotePass() {
		return RemotePass;
	}

	public HashMap<String,String> getTopicMapper() {
		return topicMapper;
	}

	public void setCompareLimit(double compareLimit) {
		this.compareLimit = compareLimit;
	}

	public double getCompareLimit() {
		return compareLimit;
	}

	public void setWarnLimit(double warnLimit) {
		this.warnLimit = warnLimit;
	}

	public double getWarnLimit() {
		return warnLimit;
	}

	

}
