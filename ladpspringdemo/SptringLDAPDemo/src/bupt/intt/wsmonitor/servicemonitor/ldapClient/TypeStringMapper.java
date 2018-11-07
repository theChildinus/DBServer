package bupt.intt.wsmonitor.servicemonitor.ldapClient;

public class TypeStringMapper {
	static TypeStringMapper mapper = new TypeStringMapper();
	public static TypeStringMapper Instance() {
		return mapper;
	}
	
	public static final String CONFIG_TYPE = "ConfigType";
	public static final String MESSAGESERVER =  "MessageServer";
	public static final String DATASERVER =  "DbServer";
	public static final String MEMDB =  "MemDb";
	public static final String MESSAGESERVICE =  "MessageService";
	public static final String BOOTASSGIN = "BootAssign";
	
}
