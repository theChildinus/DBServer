
/**
 * ExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5-wso2v1  Built on : May 20, 2009 (09:53:23 IST)
 */

package org.wso2.carbon.core.services.authentication;

public class ExceptionException extends java.lang.Exception{
    
    private org.wso2.carbon.core.services.authentication.ExceptionE faultMessage;

    
        public ExceptionException() {
            super("ExceptionException");
        }

        public ExceptionException(java.lang.String s) {
           super(s);
        }

        public ExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.wso2.carbon.core.services.authentication.ExceptionE msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.core.services.authentication.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    