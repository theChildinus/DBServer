
/**
 * ResourceServiceExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5-wso2v1  Built on : May 20, 2009 (09:53:23 IST)
 */

package org.wso2.carbon.registry.mgt.ui.resource.services;

public class ResourceServiceExceptionException extends java.lang.Exception{
    
    private org.wso2.carbon.registry.mgt.ui.resource.services.ResourceServiceExceptionE faultMessage;

    
        public ResourceServiceExceptionException() {
            super("ResourceServiceExceptionException");
        }

        public ResourceServiceExceptionException(java.lang.String s) {
           super(s);
        }

        public ResourceServiceExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ResourceServiceExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.wso2.carbon.registry.mgt.ui.resource.services.ResourceServiceExceptionE msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.registry.mgt.ui.resource.services.ResourceServiceExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    