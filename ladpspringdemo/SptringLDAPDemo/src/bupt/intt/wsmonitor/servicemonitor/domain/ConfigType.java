package bupt.intt.wsmonitor.servicemonitor.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Entity of ConfigType read from LDAP
 * @author Administrator
 *
 */
public class ConfigType{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7743687609410105617L;
	private String name;
	private String description;
	private String catetory;
	private String computerName;
	public static final String OBJECTCLASS = "organizationalUnit";
	
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
	
	public void setDisNames(ArrayList<PathName> disNames) {
		this.disNames = disNames;
	}
	public ArrayList<PathName> getDisNames() {
		return disNames;
	}
	
	public void setDisNames(String nameString) {
		
	}

	public void setCatetory(String catetory) {
		this.catetory = catetory;
	}
	public String getCatetory() {
		return catetory;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public String getComputerName() {
		return computerName;
	}

	private ArrayList<PathName> disNames;
		
}
