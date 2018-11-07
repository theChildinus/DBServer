/*   */ package bupt.intt.wsmonitor.servicemonitor.ldapClient;
/*   */ 
/*   */ public class TypeStringMapper
/*   */ {
/* 4 */   static TypeStringMapper mapper = new TypeStringMapper();
/*   */   public static final String CONFIG_TYPE = "ConfigType";
/*   */   public static final String MESSAGESERVER = "MessageServer";
/*   */   public static final String DATASERVER = "DbServer";
/*   */   public static final String MEMDB = "MemDb";
/*   */   public static final String MESSAGESERVICE = "MessageService";
/*   */   public static final String BOOTASSGIN = "BootAssign";
/*   */ 
/*   */   public static TypeStringMapper Instance()
/*   */   {
/* 6 */     return mapper;
/*   */   }
/*   */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.ldapClient.TypeStringMapper
 * JD-Core Version:    0.6.0
 */