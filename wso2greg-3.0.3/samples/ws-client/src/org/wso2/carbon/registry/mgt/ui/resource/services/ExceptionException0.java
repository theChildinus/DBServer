
/**
 * ExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1-wso2v2  Built on : Feb 17, 2009 (11:16:43 PST)
 */

package org.wso2.carbon.registry.mgt.ui.resource.services;

public class ExceptionException0 extends java.lang.Exception{
    
    private org.wso2.carbon.registry.mgt.ui.resource.services.ExceptionE faultMessage;
    
    public ExceptionException0() {
        super("ExceptionException0");
    }
           
    public ExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public ExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.wso2.carbon.registry.mgt.ui.resource.services.ExceptionE msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.registry.mgt.ui.resource.services.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    