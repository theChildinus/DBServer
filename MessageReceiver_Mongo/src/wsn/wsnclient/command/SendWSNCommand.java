package wsn.wsnclient.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.StringReader;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.w3c.dom.Element;

public class SendWSNCommand {
	
	private String wsnAddress;
	private String endpointAddr;
	private String localServiceAddr;
	private String subscriptionAddr;
	
	public SendWSNCommand(String localServiceAddr,String wsnAddress){
		this.wsnAddress = wsnAddress;
		this.localServiceAddr = localServiceAddr;
		this.endpointAddr = null;
	}
	
	/**
	 * @param url ���ʵĵ�ַ
	 * @param params http�������
	 * @param charset �������
	 * @param pretty �Ƿ��Զ�����
	 * @param content ���͵�����
	 */
    @SuppressWarnings("deprecation")
	protected String[] send(String url, Map<String, String> params, String charset, boolean pretty,String content) {  
        StringBuffer response = new StringBuffer();  
        HttpClient client = new HttpClient();  
   		PostMethod method = new PostMethod(url); 
        try {
        	
			content=new String(content.getBytes("UTF-8"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		method.setRequestBody(content);
   		  
     if (params != null) {  
          HttpMethodParams p = new HttpMethodParams();  
          
           for (Map.Entry<String, String> entry : params.entrySet()) {  
                   p.setParameter(entry.getKey(), entry.getValue());  
          }  
          method.setParams(p);  
     }  
    
     try {  
    	 client.executeMethod(method); 
    	 BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));  
    	 String line;  
         while ((line = reader.readLine()) != null) { 
        	 if (pretty)  
        		 response.append(line).append(System.getProperty("line.separator"));  
        	 else  
        		 response.append(line);
        	 }  
         reader.close();
         method.releaseConnection();  
      } catch (IOException e) {  
      } finally {  
      
      }  
      return new String[] {String.valueOf(method.getStatusCode()),response.toString()};
    } 
	
	protected String getBody(String soapMessage){
		int index1 = soapMessage.indexOf("<env:Body>");
		int indexEnd = soapMessage.indexOf("</env:Body>");
		int indexStart = index1 + "<env:Body>".length();
		String bodyMessage = soapMessage.substring(indexStart, indexEnd);
		bodyMessage = bodyMessage.replaceAll("ns5:", "");
		return bodyMessage;
	}
	
	protected String getAddress(String endpointRef){
		int index1 = endpointRef.indexOf("<Address>");
		int indexEnd = endpointRef.indexOf("</Address>");
		int indexStart = index1 + "<Address>".length();
		String endpointAddr = endpointRef.substring(indexStart, indexEnd);
		return endpointAddr;
	}
    
    public String createPullPoint() throws Exception{
		
		String content = "";
		String returnValue = "";
		
		content+="<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		content+="<env:Body>";
		content+="<wsnt:CreatePullPoint xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\">";
		content+="</wsnt:CreatePullPoint>";
		content+="</env:Body>";
		content+="</env:Envelope>";
		
		String[] responseValue = send(wsnAddress+"/CreatePullPoint/",new HashMap<String,String>(), "utf-8", true, content.trim());
		
		
		if(responseValue[0].equals("200")) {
			returnValue="ok";
			CreatePullPointResponse response = new CreatePullPointResponse();
			JAXBContext jaxbContext = JAXBContext.newInstance(CreatePullPointResponse.class);
	    	StringReader reader = new StringReader(getBody(responseValue[1]));
	    	response = (CreatePullPointResponse) jaxbContext.createUnmarshaller().unmarshal(reader);
	    	String endpoint = response.getPullPoint().toString();
	    	this.endpointAddr = getAddress(endpoint);
		} else {
			returnValue="error";
		}
		return returnValue;
	}
	
	public String subscribe(String topic) throws Exception{
		String content = "";
		String returnValue = "";
		
		content+="<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		content+="<env:Body>";
		content+="<wsnt:Subscribe xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">";
		content+="<wsnt:ConsumerReference>";
		content+=" <wsa:Address>";
		content+="endpoint:"+this.endpointAddr;
		content+="</wsa:Address>";
		content+="</wsnt:ConsumerReference>";
		content+="<wsnt:Filter>";
		content+="<wsnt:TopicExpression Dialect=\"http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple\">";
		content+=topic;
		content+="</wsnt:TopicExpression>";
		content+="</wsnt:Filter>";
		content+="<wsnt:SubscriberAddress>";
		content+=this.localServiceAddr;
		content+="</wsnt:SubscriberAddress>";
		content+="</wsnt:Subscribe>";
		content+="</env:Body>";
		content+="</env:Envelope>";		

		String[] responseValue = send(wsnAddress+"/Broker/",new HashMap<String,String>(), "utf-8", true, content.trim());
		
		if(responseValue[0].equals("200")) {
			returnValue="ok";
			SubscribeResponse response = new SubscribeResponse();
			JAXBContext jaxbContext = JAXBContext.newInstance(SubscribeResponse.class);
	    	StringReader reader = new StringReader(getBody(responseValue[1]));
	    	response = (SubscribeResponse) jaxbContext.createUnmarshaller().unmarshal(reader);
	    	String subscriptionRef = response.getSubscriptionReference().toString();
	    	this.subscriptionAddr=getAddress(subscriptionRef.replaceAll("ns2:", ""));
		} else {
			returnValue="error";
		}
		return returnValue;		
	}
	
	public void unSubscribe(){
		String content = "";
		
		content+="<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">";
		content+="<env:Header>";
		content+="<wsa:Action>";
		content+="http://docs.oasis-open.org/wsn/bw-2/SubscriptionManager/UnsubscribeRequest";
		content+="</wsa:Action>";
		content+="<wsa:To>";
		content+=this.subscriptionAddr;
		content+="</wsa:To>";
		content+="</env:Header>";
		content+="<env:Body>";
		content+="</wsnt:Unsubscribe/>";
		content+="</env:Body>";
		content+="</env:Envelope>";
		
		String[] responseValue = send(wsnAddress+"/Broker/",new HashMap<String,String>(), "utf-8", true, content.trim());

	}
	
	public void notify(String topicName,String notification){
		String content = "";
		
		content+="<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		content+="<env:Body>";
		content+=" <wsnt:Notify xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\">";
		content+="<wsnt:NotificationMessage>";
		content+="<wsnt:Topic Dialect=\"http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple\">";
		content+=topicName;
		content+="</wsnt:Topic>";
		content+="<wsnt:Message>";
		content+=notification;
		content+="</wsnt:Message>";
		content+="</wsnt:NotificationMessage>";
		content+="</wsnt:Notify>";
		content+="</env:Body>";
		content+="</env:Envelope>";
		
		String[] responseValue = send(wsnAddress+"/Broker/",new HashMap<String,String>(), "utf-8", true, content.trim());
	}

	public String getMessage(String num) throws JAXBException{
		String content = "";
		String returnValue = "";
		
		content+="<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"	xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">";
		content+="<env:Header>";
		content+="<wsa:To>";
		content+=this.endpointAddr;
		content+="</wsa:To>";
		content+="</env:Header>";
		content+="<env:Body>";
		content+="<wsnt:GetMessages>";
		content+="<wsnt:MaximumNumber>";
		content+=num;
		content+="</wsnt:MaximumNumber>";
		content+="</wsnt:GetMessages>";
		content+="</env:Body>";
		content+="</env:Envelope>";
		
		String[] responseValue = send(wsnAddress="/Broker/",new HashMap<String,String>(), "utf-8", true, content.trim());
		
		if(responseValue[0].equals("202")) {
			GetMessagesResponse response = new GetMessagesResponse();
			JAXBContext jaxbContext = JAXBContext.newInstance(GetMessagesResponse.class);
	    	StringReader reader = new StringReader(responseValue[1]);
	    	response = (GetMessagesResponse) jaxbContext.createUnmarshaller().unmarshal(reader);
	    	for(NotificationMessageHolderType messageHolder : response.getNotificationMessage())
	    		returnValue+=messageHolder.getMessage().toString();
	    	return returnValue;
		} else {
			returnValue="error";
		}
		return returnValue;
	}
}

	