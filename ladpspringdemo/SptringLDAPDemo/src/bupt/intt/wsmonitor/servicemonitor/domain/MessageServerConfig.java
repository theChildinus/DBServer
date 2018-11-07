package bupt.intt.wsmonitor.servicemonitor.domain;

public class MessageServerConfig extends ConfigType {
	private String ip;
	private String createPullPointURI;
	private String brokerURI;
	public static final String SECONDBJECTCLASS = "MessageServerConfig";

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;		
	}

	public void setCreatePullPointURI(String createPullPointURI) {
		this.createPullPointURI = createPullPointURI;
	}

	public String getCreatePullPointURI() {
		return createPullPointURI;
	}

	public void setBrokerURI(String brokerURI) {
		this.brokerURI = brokerURI;
	}

	public String getBrokerURI() {
		return brokerURI;
	}
	
	
	
}
