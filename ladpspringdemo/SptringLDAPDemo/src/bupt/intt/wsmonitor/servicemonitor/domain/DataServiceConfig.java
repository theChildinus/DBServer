package bupt.intt.wsmonitor.servicemonitor.domain;

import java.io.Serializable;

public class DataServiceConfig extends ConfigType {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5366770149371936672L;
	public static final String SECONDBJECTCLASS = "DataServiceConfig";
	private String brokerURI;
	private String customerURI;
	private String defaultReadCount;
	private String topics;
	private String createPullPointURI;
	private String jmsurl;
	private String remoteJdbcUrl;
	private String remoteJdbcUser;
	private String remoteJdbcPass;
	
	public void setBrokerURI(String brokerURI) {
		this.brokerURI = brokerURI;
	}
	public String getBrokerURI() {
		return brokerURI;
	}
	public void setCustomerURI(String customerURI) {
		this.customerURI = customerURI;
	}
	public String getCustomerURI() {
		return customerURI;
	}
	public void setDefaultReadCount(String defaultReadCount) {
		this.defaultReadCount = defaultReadCount;
	}
	public String getDefaultReadCount() {
		return defaultReadCount;
	}
	public void setTopics(String topics) {
		this.topics = topics;
	}
	public String getTopics() {
		return topics;
	}
	public void setCreatePullPointURI(String createPullPointURI) {
		this.createPullPointURI = createPullPointURI;
	}
	public String getCreatePullPointURI() {
		return createPullPointURI;
	}
	public void setJmsurl(String jmsurl) {
		this.jmsurl = jmsurl;
	}
	public String getJmsurl() {
		return jmsurl;
	}
	public void setRemoteJdbcUrl(String remoteJdbcUrl) {
		this.remoteJdbcUrl = remoteJdbcUrl;
	}
	public String getRemoteJdbcUrl() {
		return remoteJdbcUrl;
	}
	public void setRemoteJdbcUser(String remoteJdbcUser) {
		this.remoteJdbcUser = remoteJdbcUser;
	}
	public String getRemoteJdbcUser() {
		return remoteJdbcUser;
	}
	public void setRemoteJdbcPass(String remoteJdbcPass) {
		this.remoteJdbcPass = remoteJdbcPass;
	}
	public String getRemoteJdbcPass() {
		return remoteJdbcPass;
	}
}
