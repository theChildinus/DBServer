package bupt.intt.wsmonitor.servicemonitor.domain;

public class PathName {
	public PathName() {
	
	}
	
	public PathName(String prefix,String value) {
		this.prefix = prefix;
		this.value = value;
	}
	
	private String prefix;
	private String value;
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
