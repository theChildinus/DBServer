
/**
 * AuthenticationExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1-wso2v2  Built on : Feb 17, 2009 (11:16:43 PST)
 */

package org.wso2.carbon.core.services.authentication;

public class AuthenticationExceptionException0 extends java.lang.Exception{
    
    private org.wso2.carbon.core.services.authentication.AuthenticationExceptionE faultMessage;
    
    public AuthenticationExceptionException0() {
        super("AuthenticationExceptionException0");
    }
           
    public AuthenticationExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public AuthenticationExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.wso2.carbon.core.services.authentication.AuthenticationExceptionE msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.core.services.authentication.AuthenticationExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    