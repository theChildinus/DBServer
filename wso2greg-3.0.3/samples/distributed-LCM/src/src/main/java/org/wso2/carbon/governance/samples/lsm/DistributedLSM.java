/*
 * Copyright (c) 2006, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.governance.samples.lsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.llom.util.AXIOMUtil;
import org.apache.commons.logging.Log;
import org.wso2.carbon.governance.mgt.ui.custom.lifecycles.checklist.util.LifecycleBeanPopulator;
import org.wso2.carbon.registry.core.Aspect;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.ResourcePath;
import org.wso2.carbon.registry.core.utils.RegistryUtils;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.jdbc.handlers.RequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class DistributedLSM extends Aspect {
	 private static final Log log = LogFactory.getLog(DistributedLSM.class);

	public static final String PROMOTE = "promote";
	public static final String DEMOTE = "demote";

	public enum ConditionEnum {
		isNull, equals, contains, lessThan, greaterThan
	}

	class Condition {
		public String property;
		public ConditionEnum condition;
		public String value;

		Condition(String property, String condition, String value) {
			this.property = property;
			this.condition = ConditionEnum.valueOf(condition);
			this.value = value;
		}

		public boolean isTrue(Resource resource) {
			String propVal = resource.getProperty(property);
			if (propVal == null) {
				return condition == ConditionEnum.isNull;
			}

			switch (condition) {
			case equals:
				return propVal.equals(value);
			case contains:
				return propVal.indexOf(value) > -1;
			case lessThan:
				return Integer.parseInt(propVal) < Integer.parseInt(value);
			case greaterThan:
				return Integer.parseInt(propVal) > Integer.parseInt(value);
			default:
				return false;
			}
		}

		public String getDescription() {
			StringBuffer ret = new StringBuffer();
			ret.append("Property '");
			ret.append(property);
			ret.append("' ");
			switch (condition) {
			case isNull:
				ret.append("must be null");
				break;
			case equals:
				ret.append("must equal '");
				ret.append(value);
				ret.append("'");
				break;
			case contains:
				ret.append("must contain '");
				ret.append(value);
				ret.append("'");
				break;
			case lessThan:
				ret.append("must be less than ");
				ret.append(value);
				break;
			case greaterThan:
				ret.append("must be greater than ");
				ret.append(value);
				break;
			}
			return ret.toString();
		}
	}

	private List<String> states = new ArrayList<String>();
	private List<String> distributedLocations = new ArrayList<String>();

	private Map<String, List<Condition>> transitions = new HashMap<String, List<Condition>>();
	private String stateProperty = "registry.lifecycle.ProjectChecklist.state";
    private String lifecycleProperty = "registry.LC.name";
    boolean isConfigurationFromResource = false;
	boolean configurationFromResourceExtracted = false;
	String configurationResourcePath = "";
	OMElement configurationElement = null;
	String aspectName = "ProjectChecklist";

	public DistributedLSM() {
		// Lifecycle with no configuration gets the default set of states, with
		// no conditions.
		states.add("Created");
		states.add("Designed");
		states.add("Implemented");
		states.add("Passed Tests");
		states.add("Deployed");
	}

	public DistributedLSM(OMElement config) throws RegistryException {
		String myName = config.getAttributeValue(new QName("name"));
		aspectName = myName;
		myName = myName.replaceAll("\\s", "");
		stateProperty = "registry.lifecycle." + myName + ".state";

		Iterator stateElements = config.getChildElements();
		while (stateElements.hasNext()) {
			OMElement stateEl = (OMElement) stateElements.next();
			/*
			 * expected format @ registry.xml <aspect name="ProjectChecklist"
			 * class="org.wso2.carbon.governance.samples.lsm.DistributedLSM">
			 * <configuration
			 * type="resource">/checklists/products</configuration> </aspect>
			 */
			if (stateEl.getAttribute(new QName("type")) != null) {
				String type = stateEl.getAttributeValue(new QName("type"));
				if (type.equalsIgnoreCase("resource")) {
					isConfigurationFromResource = true;
					configurationResourcePath = RegistryUtils.getAbsolutePath(stateEl.getText());
					states.clear();
					distributedLocations.clear();

					transitions.clear();
					break;
				} else if (type.equalsIgnoreCase("literal")) {
					isConfigurationFromResource = false;
					configurationElement = stateEl.getFirstElement();
					states.clear();

					transitions.clear();
					distributedLocations.clear();
					break;
				}
			}
			String name = stateEl.getAttributeValue(new QName("name"));
			if (name == null) {
				throw new IllegalArgumentException(
						"Must have a name attribute for each state");
			}
			states.add(name);
			List<Condition> conditions = null;
			Iterator conditionIterator = stateEl.getChildElements();
			while (conditionIterator.hasNext()) {
				OMElement conditionEl = (OMElement) conditionIterator.next();
				if (conditionEl.getQName().equals(new QName("condition"))) {
					String property = conditionEl.getAttributeValue(new QName(
							"property"));
					String condition = conditionEl.getAttributeValue(new QName(
							"condition"));
					String value = conditionEl.getAttributeValue(new QName(
							"value"));
					Condition c = new Condition(property, condition, value);
					if (conditions == null)
						conditions = new ArrayList<Condition>();
					conditions.add(c);
				}
			}
			if (conditions != null) {
				transitions.put(name, conditions);
			}
		}
	}

	@Override
	public void associate(Resource resource, Registry registry)
			throws RegistryException {

		try {
			String xmlContent = "";
			if (isConfigurationFromResource) {
				Resource configurationResource = registry
						.get(configurationResourcePath);
				xmlContent = new String((byte[]) configurationResource
						.getContent());
				configurationElement = AXIOMUtil.stringToOM(xmlContent);
			}

			Iterator stateElements = configurationElement.getChildElements();
			int propertyOrder = 0;
			while (stateElements.hasNext()) {
				OMElement stateEl = (OMElement) stateElements.next();
				String name = stateEl.getAttributeValue(new QName("name"));
				String location = stateEl.getAttributeValue(new QName(
						"location"));

				if (name == null) {
					throw new IllegalArgumentException(
							"Must have a name attribute for each state");
				}

				states.add(name);

				if (location != null) {
					if (!isValidRegistryCollection(location))
						throw new IllegalArgumentException("The location "
								+ location + ", is not a valid location.");
					if (!location.endsWith("/"))
						location += "/";
					distributedLocations.add(RegistryUtils.getAbsolutePath(location));
				} else {
					distributedLocations.add("#");
				}

				Iterator checkListIterator = stateEl.getChildElements();
				int checklistItemOrder = 0;
				while (checkListIterator.hasNext()) {
					OMElement itemEl = (OMElement) checkListIterator.next();
					if (itemEl.getQName().equals(new QName("checkitem"))) {
						List<String> items = new ArrayList<String>();
						String itemName = itemEl.getText();
						if (itemName == null)
							throw new RegistryException(
									"Checklist items should have a name!");
						items.add("status:" + name);
						items.add("name:" + itemName);
						items.add("value:false");

						if (itemEl.getAttribute(new QName("order")) != null) {
							items.add("order:"
									+ itemEl.getAttributeValue(new QName(
											"order")));
						} else {
							items.add("order:" + checklistItemOrder);
						}

						String resourcePropertyNameForItem = "registry.custom_lifecycle.checklist.option"
								+ propertyOrder + ".item";

						resource
								.setProperty(resourcePropertyNameForItem, items);
						checklistItemOrder++;
						propertyOrder++;
					}
				}
			}

		} catch (XMLStreamException e) {
			throw new RegistryException(
					"Resource does not contain valid XML configuration: "
							+ e.toString());
		}

		resource.setProperty(stateProperty, states.get(0));
        resource.setProperty(lifecycleProperty, aspectName);
    }

	@Override
	public String[] getAvailableActions(RequestContext context) {
        try {
            if (states.size() == 0) {
                distributedLocations.clear();
                Registry registry = context.getRegistry();
                String xmlContent = "";
                if (isConfigurationFromResource) {
                        Resource configurationResource = registry.get(configurationResourcePath);
                        xmlContent = new String((byte[])configurationResource.getContent());
                    configurationElement = AXIOMUtil.stringToOM(xmlContent);
                }

                Iterator stateElements = configurationElement.getChildElements();
                while (stateElements.hasNext()) {
                    OMElement stateEl = (OMElement) stateElements.next();
                    String name = stateEl.getAttributeValue(new QName("name"));
                    String location = stateEl.getAttributeValue(new QName("location"));
                    if (name == null) {
                        throw new IllegalArgumentException("Must have a name attribute for each state");
                    }

                    states.add(name);

                    if (location != null) {
                        if (!isValidRegistryCollection(location))
                            throw new IllegalArgumentException("The location "
                                    + location + ", is not a valid location.");
                        if (!location.endsWith("/"))
                            location += "/";
                        distributedLocations.add(RegistryUtils.getAbsolutePath(location));
                    } else {
                        distributedLocations.add("#");
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Resource does not contain valid XML configuration: " + e.toString()); 
        }

		ArrayList<String> actions = new ArrayList<String>();
		Resource resource = context.getResource();
		String currentState = resource.getProperty(stateProperty);

		Properties props = resource.getProperties();
		Iterator iKeys = props.keySet().iterator();
		boolean allItemsAreChecked = true;
		while (iKeys.hasNext()) {
			String propKey = (String) iKeys.next();
			if (propKey.startsWith("registry.custom_lifecycle.checklist.")) {
				List<String> propValues = (List<String>) props.get(propKey);
				String[] propertyValues = propValues
						.toArray(new String[propValues.size()]);
				String itemLifeCycleState = null;
				String itemValue = null;

				if (propertyValues != null) {
					for (int index = 0; index < propertyValues.length; index++) {
						String item = propertyValues[index];
						if ((itemLifeCycleState == null)
								&& (item.startsWith("status:"))) {
							itemLifeCycleState = item.substring(7);
						}
						if ((itemValue == null) && (item.startsWith("value:"))) {
							itemValue = item.substring(6);
						}
					}
				}

				if ((itemLifeCycleState != null) && (itemValue != null)) {
					if (itemLifeCycleState.equalsIgnoreCase(currentState)) {
						if (itemValue.equalsIgnoreCase("false")) {
							allItemsAreChecked = false;
							break;
						}
					}
				}
			}
		}

		int stateIndex = states.indexOf(currentState);
		if (stateIndex > -1 && stateIndex < states.size() - 1) {
			if (allItemsAreChecked)
				actions.add(PROMOTE);
		}
		if (stateIndex > 0) {
			actions.add(DEMOTE);
		}
		return actions.toArray(new String[actions.size()]);
	}

	@Override
	public void invoke(RequestContext requestContext, String action)
			throws RegistryException {

		final String FILE_SEPERATOR = "/";
		Resource resource = requestContext.getResource();
		String currentState = resource.getProperty(stateProperty);
		String resourcePath = requestContext.getResourcePath().getPath();
		String newresourcePath = "";

		String path = resource.getPath();
		String resourceName = path.substring(path.lastIndexOf("/") + 1);
		String nextLocation = "";

		int stateIndex = states.indexOf(currentState);

		if (stateIndex == -1) {
			throw new RegistryException("State '" + currentState
					+ "' is not valid!");
		}

		String newState;
		if (PROMOTE.equals(action)) {
			if (stateIndex == states.size() - 1) {
				throw new RegistryException(
						"Can't promote beyond end of configured lifecycle!");
			}

			// Make sure all conditions are met
			List<Condition> conditions = transitions.get(currentState);
			if (conditions != null) {
				for (Condition condition : conditions) {
					if (!condition.isTrue(resource)) {
						throw new RegistryException("Condition failed - "
								+ condition.getDescription());
					}
				}
			}

			if (stateIndex >= distributedLocations.size() - 1)
				nextLocation = distributedLocations.get(0);
			else
				nextLocation = distributedLocations.get(stateIndex + 1);

			if (!nextLocation.equals("#")) {
				newresourcePath = nextLocation + resourceName;
			}

			newState = states.get(stateIndex + 1);

		}

		else if (DEMOTE.equals(action)) {

			if (stateIndex == 0) {
				throw new RegistryException(
						"Can't demote beyond start of configured lifecycle!");
			}
			if (stateIndex <= 0)
				nextLocation = distributedLocations.get(0);
			else
				nextLocation = distributedLocations.get(stateIndex - 1);

			if (!nextLocation.equals("#")) {
				newresourcePath = nextLocation + resourceName;
			}

			newState = states.get(stateIndex - 1);
		}
		else {
			throw new RegistryException("Invalid action '" + action + "'");
		}

		resource.setProperty(stateProperty, newState);

		if (!nextLocation.equals("#")) {

			resource.setProperty(stateProperty, newState);
			requestContext.getRegistry().put(resourcePath, resource);
            try{
			    requestContext.getRegistry().move(resourcePath, newresourcePath);
                requestContext.setResourcePath(new ResourcePath(newresourcePath));
			}
			catch(RegistryException e)
			{
			String msg = "Failed to move resource" +
            resourcePath + ". " + e.getMessage();
			log.error(msg, e);
            
			}
		}
	}

	public String getCurrentState(Resource resource) {
		return resource.getProperty(stateProperty);
	}

	@Override
	public void dissociate(RequestContext requestContext) {

		Resource resource = requestContext.getResource();

		if (resource != null) {
			resource.removeProperty(stateProperty);
            resource.removeProperty(lifecycleProperty);
        }
	}

	public boolean isValidRegistryCollection(String path) {
		if (!path.startsWith("/"))
			return false;
		if (path.contains("~!@#$%^*()+={}[]|\\<>"))
			return false;
		return true;
	}
}
