Distributed LifeCycle Management sample
-------------------------------------

Introduction
------------
ProjectLifecycle CheckList is a sample about how can a user able to use the "Lifecycle status" in his project development cycle.
First Architect designs 'Use case diagrams and other designs' and let the Developers to implement it.
After finishing the implementation task, Developers promote the resource to QAs to validate the resource/product. After passing all the tests, QAs
allow customers to deploy the product/project. It has six states.
-Initialize
-Designed
-Created
-Tested
-Deployed
-Deprecated


Running the sample
------------------

1. Copy the org.wso2.carbon.governance.samples.lsm-SNAPSHOT.jar to
	GREG_HOME/webapps/ROOT/WEB-INF/plugins/server folder

2. Start the server.

3. Go to management console and select Govern-->Lifecycles menu.

4. Where user can add his own lifecycle configuration using 'Add New Lifecycle' option.

5. After adding the configuration, go to Resource-->Browse menu and add a new collection.

6. Add a resource into that collection.

7. Navigate to new resource.

8. At the "Life cycle management' panel, select your lifecycle configuration, which you added in step-5.

9. Check all the options and 'Promote'

10. Go to "environment/design" folder, where resource is stored with new life cycle state.
   Select that resource and promote. It can be found in "environment/development' folder with next state. 

11.Your resource will be promoted to 
	-/environment/init
	-/environment/design
	-/environment/development
	-/environment/qa
	-/environment/prod
	-/environment/Deprecated

12. Your resource will be demoted to relevant folder.

Sample lifecycle configuration.
-------------------------------

<aspect name="ProjectChecklist" class="org.wso2.carbon.governance.samples.lsm.DistributedLSM">
		<!-- Checklist can either be provided as a resource(type=resource) or as xml content(type=literal, default) as provided below. -->
		<!-- <configuration type="resource">/workspace/checklist</configuration> -->
		<!-- OR -->
		<configuration type="literal">
			<lifecycle>
    			<state name="Initialize" location="/environment/init">
		        	<checkitem>Requirements Gathered</checkitem>
        			<checkitem>Architecture Finalized</checkitem>
			        <checkitem>High Level Design Completed</checkitem>
			    </state>
			    <state name="Designed" location="/environment/design">
			        <checkitem>Code Completed</checkitem>
			        <checkitem>WSDL, Schema Created</checkitem>
				    <checkitem>QoS Created</checkitem>
			        </state>	
			    <state name="Created" location="/environment/development">
			        <checkitem>Effective Inspection Completed</checkitem>
			        <checkitem>Test Cases Passed</checkitem>
			        <checkitem>Smoke Test Passed</checkitem>
			    </state>
			    <state name="Tested" location="/environment/qa">
					<checkitem>Service Configuration</checkitem>
			          </state>
			   <state name="Deployed" location="/environment/prod">
					<checkitem>Service Configuration</checkitem>
				</state>
				<state name="Deprecated" location="/environment/Deprecated"> 
				</state>
			</lifecycle>
		</configuration>
	</aspect>


