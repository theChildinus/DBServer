
/**
 * AuthenticationExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5-wso2v1  Built on : May 20, 2009 (09:53:23 IST)
 */

package org.wso2.carbon.core.services.authentication;

public class AuthenticationExceptionException extends java.lang.Exception{
    
    private org.wso2.carbon.core.services.authentication.AuthenticationExceptionE faultMessage;

    
        public AuthenticationExceptionException() {
            super("AuthenticationExceptionException");
        }

        public AuthenticationExceptionException(java.lang.String s) {
           super(s);
        }

        public AuthenticationExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public AuthenticationExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.wso2.carbon.core.services.authentication.AuthenticationExceptionE msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.core.services.authentication.AuthenticationExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    