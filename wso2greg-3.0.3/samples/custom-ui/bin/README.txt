
This file explains the usage of the custom UI sample (Adding endpoint references as custom resources).
======================================================================================================

 Adding an endpoint reference as a resource
---------------------------------------------

1. Drop the following jars into GREG_HOME/webapps/ROOT/WEB-INF/plugins.

	-  Put the "org.wso2.carbon.registry.samples.mgt.ui.custom.topics-*.jar" into the "plugins/server" folder.
	-  Put the "org.wso2.carbon.registry.samples.ui.custom.topics-*.jar" into the "plugins/console" folder.

2. Add the following bundle info details that is in  the "bundles.info" file which is located at 
	"GREG_HOME/webapps/ROOT/WEB-INF/eclipse/configuration/org.eclipse.equinox.simpleconfigurator".

	-org.wso2.carbon.registry.samples.mgt.ui.custom.topics,BUNDLE-VERSION,file:plugins/org.wso2.carbon.registry.samples.mgt.ui.custom.topics_*.jar,10,true
	-org.wso2.carbon.registry.samples.ui.custom.topics,BUNDLE-VERSION,file:plugins/org.wso2.carbon.registry.samples.ui.custom.topics_*.jar,10,true

3. Start the  server. 

4. Log in  the admin console and go to  the Resources menu.

5. Click "Add Resource" link to add a new resource and choose "Create custom content" from Method combo box. 

6. A text box named "Media type" will be displayed, from which select "other" option from drop down button for media type.

7. Enter "epr" as the media type.

8. Click "Create Content". A form to fill endpoint details will be displayed.

9. Fill the form and click "Save'. The resource  will be added. 

 Browsing the resource
-----------------------

1. Browse the added resource from the Entries table and click on it.

2. The content of the resource will be shown in a custom view. 

3. The content can be edited by clicking "Edit end point".
