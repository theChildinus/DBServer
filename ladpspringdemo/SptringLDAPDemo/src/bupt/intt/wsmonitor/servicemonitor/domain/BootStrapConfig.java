package bupt.intt.wsmonitor.servicemonitor.domain;

public class BootStrapConfig extends ConfigType {
	public static final String SECONDBJECTCLASS = "BootStrapConfig";
	private String displayName;
	private String runPath;
	private String iconUrl;
	private String halfOpcityImgUrl;
	private String imgUrl;
	private String processName;
	private String runArgument;
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setRunPath(String runPath) {
		this.runPath = runPath;
	}
	public String getRunPath() {
		return runPath;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setHalfOpcityImgUrl(String halfOpcityImgUrl) {
		this.halfOpcityImgUrl = halfOpcityImgUrl;
	}
	public String getHalfOpcityImgUrl() {
		return halfOpcityImgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessName() {
		return processName;
	}
	public void setRunArgument(String runArgument) {
		this.runArgument = runArgument;
	}
	public String getRunArgument() {
		return runArgument;
	}
	
			
}
