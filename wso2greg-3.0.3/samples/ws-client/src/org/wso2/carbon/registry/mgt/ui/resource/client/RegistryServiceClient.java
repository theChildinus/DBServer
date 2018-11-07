/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.registry.mgt.ui.resource.client;

import org.wso2.carbon.registry.mgt.ui.resource.services.ResourceAdminService;
import org.wso2.carbon.registry.mgt.ui.resource.services.ResourceAdminServiceStub;
import org.wso2.carbon.core.services.authentication.AuthenticationAdminServiceStub;
import org.wso2.carbon.utils.NetworkUtils;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.context.ServiceContext;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.wso2.carbon.core.services.authentication.Login;
import org.wso2.carbon.core.services.authentication.LoginResponse;

public class RegistryServiceClient {		
    //public void testAddResource(){
      public static void main(String args[]) throws Exception{        
    	  
    	  String key_store;
    	  String filepath;
 
    	try {
    		InputStreamReader is = new InputStreamReader(System.in);
      		BufferedReader br = new BufferedReader(is);

      		System.out.println("Path to registry key_store file:");
      		key_store= br.readLine();
      		System.out.println("Path to your file system:");
      		filepath=br.readLine();
	
            // set the system properties to enable the https conection
            System.setProperty("javax.net.ssl.trustStore", key_store);
            System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
            System.setProperty("javax.net.ssl.trustStoreType", "JKS");

            // first authenticate the client. Change the host and port in the URL, appropriately.
            AuthenticationAdminServiceStub authenticationStub =
                    new AuthenticationAdminServiceStub("https://localhost:9443/services/AuthenticationAdminService");
            authenticationStub._getServiceClient().getOptions().setManageSession(true);
            boolean resp = authenticationStub.login("admin","admin",NetworkUtils.getLocalHostname());

            // get the cooke to use in the next service invoations. This lets registry service to authenticate
            // the second request
            ServiceContext serviceContext = authenticationStub._getServiceClient().getLastOperationContext().getServiceContext();
            String sessionCookie = (String) serviceContext.getProperty(HTTPConstants.COOKIE_STRING);
            // print the cookie
            System.out.println("session Cookie " + sessionCookie);

            // doing the registry update. Change the host and port in the URL, appropriately.
            ResourceAdminServiceStub stub = new ResourceAdminServiceStub("https://localhost:9443/services/ResourceAdminService");
            stub._getServiceClient().getOptions().setManageSession(true);
            stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(60000000);
            // create a path to add new resource
            String path = "/testpath";
            String mediaType = "application/zip";
            String description = "test service";
            // enable MTOM and set the previous cookie
            stub._getServiceClient().getOptions().setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
            stub._getServiceClient().getOptions().setProperty(HTTPConstants.COOKIE_STRING, sessionCookie);
            DataHandler dataHandler = new DataHandler(new FileDataSource(filepath));
            // adding the resource
            stub.addResource(path, mediaType, description, dataHandler,null);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    } 
}
