package bupt.intt.wsmonitor.respository.remote;

import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wso2.carbon.registry.app.RemoteRegistry; 
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.Collection;
import org.wso2.carbon.registry.core.RegistryConstants;
import org.wso2.carbon.registry.core.Resource;

public class remoteResource {
    public static void main(String args[])
        throws RegistryException, MalformedURLException{
    	System.setProperty("javax.net.ssl.trustStore", "D:/Eclipse Project/ladpspringdemo/RemoteRegistry/resources/client-truststore.jks");
    	System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
    	System.setProperty("javax.net.ssl.trustStoreType","JKS");
    	
    	try {
    		query();
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	/*RemoteRegistry remote_registry = 
    		new RemoteRegistry(new URL(
    				"https://localhost:9443/registry"),"admin","admin");
    	
    	Resource _resource = remote_registry.newResource();
    	_resource.setMediaType("application/wsdl+xml");
    	
    	try
    	{    	
    		String _content = SjwGetBuffer("http://localhost:8080/axis/" +
    				"services/HelloWorldWSDD?wsdl");
    		_resource.setContent(_content);
    		remote_registry.put("wsdls", _resource); 
    			
    	}
    	catch (IOException ioException) {
    		System.out.println(ioException);
    		return;
    	}
    	//Collection collection = remote_registry.
        //System.out.println(collection.getChildCount());
    	
    	//Resource resource = remote_registry.searchContent("services");
    	
    	
    	//Resource resource = remote_registry.get("/governance/services/http/localhost:8080/axis/services/HelloWorldWSDD/HelloWorldWSDDService");
    	//String result = new String((byte[])resource.getContent());
    	//System.out.println(result);*/
    }
    
    public static String SjwGetBuffer(String url) throws IOException { 
    	ByteArrayOutputStream  bos  =  new ByteArrayOutputStream();
    	try{
	        URL sjwurl = null; 
	        HttpURLConnection httpUrl = null; 
	        BufferedInputStream bis = null; 
	        byte[] buf = new byte[1024]; 
	         
	        sjwurl = new URL(url); 
	        httpUrl = (HttpURLConnection) sjwurl.openConnection(); 
	        httpUrl.connect();
	        bis = new BufferedInputStream(httpUrl.getInputStream());
	        if(bis==null)return null;
	        while(true)  { 
	            int bytes_read = bis.read( buf ); 
	            if( bytes_read > 0 ) { 
	                bos.write( buf, 0, bytes_read ); 
	            }else { 
	                break; 
	            } 
	        } ; 
	        bis.close(); 
	        httpUrl.disconnect(); 
    	}catch   (Exception e) {   
    		  System.out.println("err!"); 
    		  return null;
    	}
        return new String(bos.toByteArray()); 
    } 

    public static void query() throws Exception {
    	RemoteRegistry remote_registry = new RemoteRegistry(new URL("https://localhost:9443/registry"),"admin","admin");
    	
    	if(!remote_registry.resourceExists("/queries/all-services-queries")) {    		
    		String sql1 = "SELECT REG_PATH_ID,REG_NAME FROM REG_RESOURCE WHERE REG_MEDIA_TYPE LIKE ? ORDER BY REG_PATH_ID,REG_NAME";
    		//	REG_PATH_ID,REG_NAME,REG_CREATOR    	
    	
    		Resource q1 = remote_registry.newResource();
    		q1.setContent(sql1);
    		q1.setMediaType(RegistryConstants.SQL_QUERY_MEDIA_TYPE);
    		q1.addProperty(RegistryConstants.RESULT_TYPE_PROPERTY_NAME,
                RegistryConstants.RESOURCES_RESULT_TYPE);
    		remote_registry.put("/queries/all-services-queries", q1);
    		}
    	
    	// then you should give the parameters and the query location you just put       
        Map parameters = new HashMap();

        parameters.put("1", "application/vnd.wso2-service+xml");
        Resource result = remote_registry.executeQuery("/queries/all-services-queries", parameters);        
      
        String[] paths = (String[])result.getContent();
        //int i = 0;
        for(String path:paths) { 
        	System.out.println(path);        	
        }
        
        Resource re = remote_registry.get(paths[0]);
        
        //OutputStreamWriter sw = new OutputStreamWriter();
        
        //System.out.println(new String((byte[]) re.getContent()));
        String content = new String((byte[]) re.getContent());
        System.out.println(content);
        //String pattenString = "<namespace>[a-zA-z]+://[^\\s]*<namespace>";
        String pattenString = "<namespace>(.+)</namespace>";
        Pattern p = Pattern.compile(pattenString);      
        Matcher m = p.matcher(content);
        if(m.find()){ 
        	System.out.println(m.group()); 
        	} 

        

    }
}
