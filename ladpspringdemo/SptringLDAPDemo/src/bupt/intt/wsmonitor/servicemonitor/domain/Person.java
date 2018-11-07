package bupt.intt.wsmonitor.servicemonitor.domain;

public class Person {
    private String cn;
    private String sn;
    private String description;
    private String givenname;
    private String mail;
    private String manager;
    private String uid;
    private String userpassword;
    public void setCn(String cn) {
		this.cn = cn;
	}
	public String getCn() {
		return cn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSn() {
		return sn;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}
	public String getGivenname() {
		return givenname;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMail() {
		return mail;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getManager() {
		return manager;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid() {
		return uid;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public String getUserpassword() {
		return userpassword;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Class " + this.getClass().getSimpleName());
		sb.append("-instance:");
		sb.append(this.hashCode() + ":");
		
		sb.append("cn-"+cn);
		sb.append(",sn-"+sn);
		sb.append(",description-"+description);
		sb.append(",givenname-"+givenname);
		sb.append(",description-"+description);
		sb.append(", mail-"+ mail);
		sb.append(",manager-"+manager);
		sb.append(",uid-"+uid);
		
		return sb.toString();
	}
}
