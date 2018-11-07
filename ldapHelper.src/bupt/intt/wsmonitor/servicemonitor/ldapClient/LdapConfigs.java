/*     */ package bupt.intt.wsmonitor.servicemonitor.ldapClient;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.dao.BootStrapDao;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.BootStrapConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.support.FileSystemXmlApplicationContext;
/*     */ 
/*     */ public class LdapConfigs
/*     */ {
/*     */   static LdapConfigs instance;
/*  20 */   private DBServerConfig dbServerCofig = null;
/*  21 */   private MessageServerConfig messageServerConfig = null;
/*  22 */   private MemDBConfig memDbConfig = null;
/*  23 */   private DataServiceConfig dataServiceConfig = null;
/*  24 */   private HashMap<String, String> topics = null;
/*     */   private ArrayList<BootStrapConfig> bootStrapList;
/*     */   ldapHelper clientHelper;
/*     */ 
/*     */   public static LdapConfigs Instance(String name)
/*     */   {
/*  34 */     return LdapConfigs.instance = new LdapConfigs(name);
/*     */   }
/*     */ 
/*     */   public static LdapConfigs Instance(String name, String iocFilename) {
/*  38 */     return LdapConfigs.instance = new LdapConfigs(name, iocFilename);
/*     */   }
/*     */ 
/*     */   public LdapConfigs(String name)
/*     */   {
/*  63 */     this(name, "testContext.xml");
/*     */   }
/*     */ 
/*     */   public LdapConfigs(String name, String iocFilename) {
/*  67 */     ApplicationContext context = new FileSystemXmlApplicationContext(
/*  68 */       iocFilename);
/*  69 */     this.clientHelper = ((ldapHelper)context.getBean("clientHelper"));
/*  70 */     ArrayList configList = this.clientHelper.listConfigs(name);
/*  71 */     for (int i = 0; i < configList.size(); i++) {
/*  72 */       Object _item = configList.get(i);
/*  73 */       if ((_item instanceof DBServerConfig)) {
/*  74 */         setDbServerCofig((DBServerConfig)_item);
/*  75 */       } else if ((_item instanceof MessageServerConfig)) {
/*  76 */         setMessageServerConfig((MessageServerConfig)_item);
/*  77 */       } else if ((_item instanceof MemDBConfig)) {
/*  78 */         setMemDbConfig((MemDBConfig)_item);
/*  79 */       } else if ((_item instanceof DataServiceConfig)) {
/*  80 */         setDataServiceConfig((DataServiceConfig)_item);
/*  81 */       } else if ((_item instanceof ConfigType)) {
/*  82 */         ConfigType type = (ConfigType)_item;
/*  83 */         setBootStrapList(getBootStrapConfigByName(this.clientHelper, type
/*  84 */           .getCatetory()));
/*     */       }
/*     */     }
/*     */ 
/*  88 */     this.topics = this.clientHelper.getAllTopics();
/*     */   }
/*     */ 
/*     */   public void setDbServerCofig(DBServerConfig dbServerCofig)
/*     */   {
/*  93 */     this.dbServerCofig = dbServerCofig;
/*     */   }
/*     */ 
/*     */   public DBServerConfig getDbServerCofig() {
/*  97 */     return this.dbServerCofig;
/*     */   }
/*     */ 
/*     */   public void setMessageServerConfig(MessageServerConfig messageServerConfig) {
/* 101 */     this.messageServerConfig = messageServerConfig;
/*     */   }
/*     */ 
/*     */   public MessageServerConfig getMessageServerConfig() {
/* 105 */     return this.messageServerConfig;
/*     */   }
/*     */ 
/*     */   public void setMemDbConfig(MemDBConfig memDbConfig) {
/* 109 */     this.memDbConfig = memDbConfig;
/*     */   }
/*     */ 
/*     */   public MemDBConfig getMemDbConfig() {
/* 113 */     return this.memDbConfig;
/*     */   }
/*     */ 
/*     */   public void setDataServiceConfig(DataServiceConfig dataServiceConfig) {
/* 117 */     this.dataServiceConfig = dataServiceConfig;
/*     */   }
/*     */ 
/*     */   public DataServiceConfig getDataServiceConfig() {
/* 121 */     return this.dataServiceConfig;
/*     */   }
/*     */ 
/*     */   public void setTopics(HashMap<String, String> topics) {
/* 125 */     this.topics = topics;
/*     */   }
/*     */ 
/*     */   public HashMap<String, String> getTopics() {
/* 129 */     return this.topics;
/*     */   }
/*     */ 
/*     */   public void setBootStrapList(ArrayList<BootStrapConfig> bootStrapList) {
/* 133 */     this.bootStrapList = bootStrapList;
/*     */   }
/*     */ 
/*     */   public ArrayList<BootStrapConfig> getBootStrapList() {
/* 137 */     return this.bootStrapList;
/*     */   }
/*     */ 
/*     */   public ArrayList<BootStrapConfig> getBootStrapConfigByName(ldapHelper helper, String name) {
/* 141 */     String[] bootStrapConfigName = name.split(",");
/* 142 */     ArrayList configList = new ArrayList();
/* 143 */     BootStrapDao bootStrapDao = helper.getBootStrapDao();
/* 144 */     for (String configName : bootStrapConfigName) {
/* 145 */       BootStrapConfig bootStrapConfig = bootStrapDao.searchByCategory(configName);
/* 146 */       configList.add(bootStrapConfig);
/*     */     }
/* 148 */     return configList;
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.ldapClient.LdapConfigs
 * JD-Core Version:    0.6.0
 */