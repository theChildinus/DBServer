
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5-wso2v1  Built on : May 20, 2009 (09:53:27 IST)
 */

            package org.wso2.carbon.registry.mgt.ui.resource.services.utils.xsd;
            /**
            *  ExtensionMapper class
            */
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "PermissionEntry".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.PermissionEntry.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "VersionsBean".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.VersionsBean.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://common.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "TagCount".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.common.xsd.TagCount.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://common.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "ResourceData".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.common.xsd.ResourceData.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "ContentDownloadBean".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.ContentDownloadBean.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "ContentBean".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.ContentBean.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "MetadataBean".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.MetadataBean.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "ResourceTreeEntryBean".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.ResourceTreeEntryBean.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "VersionPath".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.VersionPath.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "PermissionBean".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.PermissionBean.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://services.resource.ui.mgt.registry.carbon.wso2.org".equals(namespaceURI) &&
                  "Exception".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.services.Exception.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://common.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "WebResourcePath".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.common.xsd.WebResourcePath.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "CollectionContentBean".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.beans.xsd.CollectionContentBean.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://utils.services.resource.ui.mgt.registry.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "ResourceServiceException".equals(typeName)){
                   
                            return  org.wso2.carbon.registry.mgt.ui.resource.services.utils.xsd.ResourceServiceException.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    