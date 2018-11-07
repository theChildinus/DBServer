ProjectProposalHandler Sample
-----------------------------
Introduction
============
The idea of this sample is to demonstrate how to use media type handlers.  The sample application is to process project proposals, so we'll be using a component that intercepts Registry put() requests with a particular "porposal" media-type.  The Handler will read the proposal and mark it as valid or not.  In order to be a valid proposal there should be a number of fields - if all of them are there then the handler will mark the proposal as valid.  If one or more fields are missing then the proposal would be invalid.

Steps
-----
1. Build the distribution.

2. copy the 'org.wso2.carbon.registry.samples.handler-*.jar' into 
	GREG_HOME/repository/components/dropins.

3. Edit the registry.xml file which is in 'conf' folder with the following xml snippet.
	
	<handler class="org.wso2.carbon.registry.samples.handler.ProjectProposalMediaTypeHandler" methods="PUT">
        	<filter class="org.wso2.carbon.registry.core.jdbc.handlers.filters.MediaTypeMatcher">
           		 <property name="mediaType">pp</property>
       		</filter>
    	</handler>

4. Start the server.

5. Go to admin console.

6. Go to Add a new resource window.

7. Select the 'Upload content from file' from 'Method' dropdown.

8. Select your project proposal and type the media type as'pp'(you can use whatever the media type you prefer).

9. And 'Add' it.

10. Browse to the added project proposal. You could see Properties and the Tag has added to the resource.

Note:- Sample project proposals are available in "GREG_HOME/samples/handler/src/resources" folder.

