package bupt.intt.wsmonitor.servicemonitor.domain;

public class WebServiceEntity {
	private String serviceName;
	private String serviceUrl;
	private String returnType;
	private String serviceParameters;
	private static final String PARAMETERSPLITER = "_"; 
	public static final String CATEGORYNAME="ou=services";
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public String getServiceUrl() {
		return serviceUrl;
	}
	
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public void setServiceParameters(String serviceParameters) {
		//this.serviceParameters = serviceParameters.split(PARAMETERSPLITER);
		this.serviceParameters = serviceParameters;
	}
	
	public String getServiceParameters() {
		/*StringBuilder _sb = new StringBuilder();
		for(int i=0; i < serviceParameters.length; i++)
		{
			if(i != 0)
				_sb.append(PARAMETERSPLITER);
			_sb.append(serviceParameters[i]);
		}
		return _sb.toString();*/
		return this.serviceParameters;
	}	
}
