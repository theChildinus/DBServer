/*     */ package bupt.intt.wsmonitor.servicemonitor.ldapClient;
/*     */ 
/*     */ import bupt.intt.wsmonitor.servicemonitor.dao.BootAssignDao;
/*     */ import bupt.intt.wsmonitor.servicemonitor.dao.BootStrapDao;
/*     */ import bupt.intt.wsmonitor.servicemonitor.dao.ConfigTypeDao;
/*     */ import bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao;
/*     */ import bupt.intt.wsmonitor.servicemonitor.dao.DataServiceDao;
/*     */ import bupt.intt.wsmonitor.servicemonitor.dao.MemDBDao;
/*     */ import bupt.intt.wsmonitor.servicemonitor.dao.MessageServerDao;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.BootAssignConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;
/*     */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.springframework.ldap.core.DistinguishedName;
/*     */ import org.springframework.ldap.core.LdapTemplate;
/*     */ 
/*     */ public class ldapHelper
/*     */ {
/*     */   private LdapTemplate ldapTemplate;
/*     */   private ConfigTypeDao configTypeDao;
/*     */   private DBServerDao dbServerDao;
/*     */   private MessageServerDao messageServerDao;
/*     */   private MemDBDao memDBDao;
/*     */   private DataServiceDao dataServiceDao;
/*     */   private BootStrapDao bootStrapDao;
/*     */   private BootAssignDao bootAssignDao;
/*     */ 
/*     */   public void setLdapTemplate(LdapTemplate ldapTemplate)
/*     */   {
/*  48 */     this.ldapTemplate = ldapTemplate;
/*     */   }
/*     */ 
/*     */   public LdapTemplate getLdapTemplate() {
/*  52 */     return this.ldapTemplate;
/*     */   }
/*     */ 
/*     */   public ArrayList listConfigs(String id) {
/*  56 */     ArrayList configs = new ArrayList();
/*  57 */     DistinguishedName _userName = getDNameByUid(id);
/*  58 */     ConfigType type = this.configTypeDao.getByDn(_userName);
/*  59 */     String positionId = type.getCatetory();
/*     */ 
/*  61 */     List _subItems = this.ldapTemplate.list(_userName);
/*  62 */     for (Iterator iterator = _subItems.iterator(); iterator.hasNext(); ) {
/*  63 */       Object _item = iterator.next();
/*  64 */       String _itemName = _item.toString().substring(3);
/*     */ 
/*  66 */       _userName = getDNameByUid(id);
/*  67 */       _userName.add("ou", _itemName);
/*     */ 
/*  70 */       ArrayList pathNames = new ArrayList();
/*  71 */       pathNames.add(new PathName("ou", "configs"));
/*  72 */       pathNames.add(new PathName("ou", "userConfigs"));
/*  73 */       pathNames.add(new PathName("ou", id));
/*     */ 
/*  75 */       if (_itemName.contains("DbServer"))
/*     */       {
/*  79 */         DBServerConfig config = getDbServerDao().getByDn(_userName);
/*  80 */         pathNames.add(new PathName("ou", _itemName));
/*  81 */         config.setDisNames(pathNames);
/*  82 */         configs.add(config);
/*     */       }
/*  85 */       else if (_itemName
/*  85 */         .contains("MessageServer"))
/*     */       {
/*  89 */         MessageServerConfig config = getMessageServerDao().getByDn(_userName);
/*  90 */         pathNames.add(new PathName("ou", _itemName));
/*  91 */         config.setDisNames(pathNames);
/*  92 */         configs.add(config);
/*     */       }
/*  94 */       else if (_itemName.contains("MemDb"))
/*     */       {
/*  97 */         MemDBConfig config = getMemDBDao().getByDn(_userName);
/*  98 */         pathNames.add(new PathName("ou", _itemName));
/*  99 */         config.setDisNames(pathNames);
/* 100 */         configs.add(config);
/*     */       }
/* 103 */       else if (_itemName
/* 103 */         .contains("MessageService"))
/*     */       {
/* 106 */         DataServiceConfig config = getDataServiceDao().getByDn(_userName);
/* 107 */         config.setTopics(getTopicByPositionId(positionId));
/* 108 */         pathNames.add(new PathName("ou", _itemName));
/* 109 */         config.setDisNames(pathNames);
/* 110 */         configs.add(config);
/*     */       }
/* 112 */       else if (_itemName.contains("BootAssign")) {
/* 113 */         BootAssignConfig configType = getBootAssignDao().getByDn(_userName);
/* 114 */         pathNames.add(new PathName("ou", _itemName));
/* 115 */         configType.setDisNames(pathNames);
/* 116 */         configs.add(configType);
/*     */       }
/*     */     }
/* 119 */     return configs;
/*     */   }
/*     */ 
/*     */   public ArrayList<ConfigType> getAllUsers()
/*     */   {
/* 127 */     ArrayList configs = new ArrayList();
/* 128 */     DistinguishedName name = new DistinguishedName("");
/* 129 */     name.add("ou", "configs");
/* 130 */     name.add("ou", "userConfigs");
/*     */ 
/* 132 */     List _subItems = this.ldapTemplate.list(name);
/* 133 */     for (Iterator iterator = _subItems.iterator(); iterator.hasNext(); ) {
/* 134 */       Object _item = iterator.next();
/* 135 */       String _itemName = _item.toString().substring(3);
/*     */ 
/* 137 */       DistinguishedName name2 = new DistinguishedName("");
/* 138 */       name2.add("ou", "configs");
/* 139 */       name2.add("ou", "userConfigs");
/* 140 */       name2.add("ou", _itemName);
/*     */ 
/* 143 */       ArrayList pathNames = new ArrayList();
/* 144 */       pathNames.add(new PathName("ou", "configs"));
/* 145 */       pathNames.add(new PathName("ou", "userConfigs"));
/* 146 */       pathNames.add(new PathName("ou", _itemName));
/*     */ 
/* 148 */       ConfigType config = getConfigTypeDao().getByDn(name2);
/* 149 */       config.setDisNames(pathNames);
/* 150 */       configs.add(config);
/*     */     }
/*     */ 
/* 154 */     return configs;
/*     */   }
/*     */ 
/*     */   public DistinguishedName getDNameByUid(String id) {
/* 158 */     DistinguishedName name = new DistinguishedName("");
/* 159 */     name.add("ou", "configs");
/* 160 */     name.add("ou", "userConfigs");
/* 161 */     name.add("ou", id);
/* 162 */     return name;
/*     */   }
/*     */ 
/*     */   public String getTopicByPositionId(String id) {
/* 166 */     DistinguishedName name = new DistinguishedName("");
/* 167 */     name.add("ou", "configs");
/* 168 */     name.add("ou", "positionConfigs");
/* 169 */     name.add("ou", id);
/* 170 */     List _subItems = this.ldapTemplate.list(name);
/* 171 */     String topics = null;
/* 172 */     for (Iterator localIterator = _subItems.iterator(); localIterator.hasNext(); ) { Object obj = localIterator.next();
/* 173 */       if (obj.toString().contains("MessageService")) {
/* 174 */         String ouName = obj.toString();
/* 175 */         name.add("ou", ouName.substring(ouName.indexOf("=") + 1));
/* 176 */         DataServiceConfig serviceConfig = this.dataServiceDao.getByDn(name);
/* 177 */         topics = serviceConfig.getTopics();
/* 178 */         break;
/*     */       }
/*     */     }
/* 181 */     return topics;
/*     */   }
/*     */ 
/*     */   public HashMap<String, String> getAllTopics()
/*     */   {
/* 186 */     HashMap _maps = new HashMap();
/* 187 */     DistinguishedName name = new DistinguishedName("");
/* 188 */     name.add("ou", "configs");
/* 189 */     name.add("ou", "baseConfigs");
/* 190 */     List _subItems = this.ldapTemplate.list(name);
/*     */ 
/* 192 */     for (Iterator localIterator1 = _subItems.iterator(); localIterator1.hasNext(); ) { Object obj = localIterator1.next();
/*     */ 
/* 194 */       String ouName = obj.toString();
/*     */ 
/* 196 */       DistinguishedName name2 = new DistinguishedName("");
/* 197 */       name2.add("ou", "configs");
/* 198 */       name2.add("ou", "baseConfigs");
/* 199 */       name2.add("ou", ouName.substring(ouName.indexOf("=") + 1));
/*     */ 
/* 201 */       ConfigType type = this.configTypeDao.getByDn(name2);
/*     */ 
/* 203 */       if ((type.getDescription() != null) && (type.getDescription().equals("订阅类型"))) {
/* 204 */         ArrayList _pathNames = new ArrayList();
/* 205 */         _pathNames.add(new PathName("ou", "configs"));
/* 206 */         _pathNames.add(new PathName("ou", "baseConfigs"));
/* 207 */         _pathNames.add(new PathName("ou", ouName.substring(ouName.indexOf("=") + 1)));
/*     */ 
/* 210 */         ConfigType subscription = new ConfigType();
/* 211 */         subscription.setDisNames(_pathNames);
/* 212 */         ArrayList _children = this.configTypeDao.listAllChildren(subscription);
/* 213 */         for (ConfigType _child : _children) {
/* 214 */           _maps.put(_child.getDescription(), _child.getCatetory());
/*     */         }
/* 216 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 220 */     return _maps;
/*     */   }
/*     */ 
/*     */   public void setDbServerDao(DBServerDao dbServerDao) {
/* 224 */     this.dbServerDao = dbServerDao;
/*     */   }
/*     */ 
/*     */   public DBServerDao getDbServerDao() {
/* 228 */     return this.dbServerDao;
/*     */   }
/*     */ 
/*     */   public void setMessageServerDao(MessageServerDao messageServerDao) {
/* 232 */     this.messageServerDao = messageServerDao;
/*     */   }
/*     */ 
/*     */   public MessageServerDao getMessageServerDao() {
/* 236 */     return this.messageServerDao;
/*     */   }
/*     */ 
/*     */   public void setMemDBDao(MemDBDao memDBDao) {
/* 240 */     this.memDBDao = memDBDao;
/*     */   }
/*     */ 
/*     */   public MemDBDao getMemDBDao() {
/* 244 */     return this.memDBDao;
/*     */   }
/*     */ 
/*     */   public void setDataServiceDao(DataServiceDao dataServiceDao) {
/* 248 */     this.dataServiceDao = dataServiceDao;
/*     */   }
/*     */ 
/*     */   public DataServiceDao getDataServiceDao() {
/* 252 */     return this.dataServiceDao;
/*     */   }
/*     */ 
/*     */   public void setConfigTypeDao(ConfigTypeDao configTypeDao) {
/* 256 */     this.configTypeDao = configTypeDao;
/*     */   }
/*     */ 
/*     */   public ConfigTypeDao getConfigTypeDao() {
/* 260 */     return this.configTypeDao;
/*     */   }
/*     */ 
/*     */   public void setBootStrapDao(BootStrapDao bootStrapDao) {
/* 264 */     this.bootStrapDao = bootStrapDao;
/*     */   }
/*     */ 
/*     */   public BootStrapDao getBootStrapDao() {
/* 268 */     return this.bootStrapDao;
/*     */   }
/*     */ 
/*     */   public void testConnection() {
/* 272 */     DistinguishedName name = new DistinguishedName("");
/* 273 */     name.add("ou", "configs");
/* 274 */     name.add("ou", "baseConfigs");
/* 275 */     List _subItems = this.ldapTemplate.list(name);
/* 276 */     _subItems.size();
/*     */   }
/*     */ 
/*     */   public boolean testUserExist(String name)
/*     */   {
/*     */     try
/*     */     {
/* 288 */       DistinguishedName _userName = getDNameByUid(name);
/* 289 */       ConfigType type = this.configTypeDao.getByDn(_userName);
/*     */ 
/* 291 */       return type != null;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */     }
/* 295 */     return false;
/*     */   }
/*     */ 
/*     */   public void createUser(String username)
/*     */   {
/* 305 */     ConfigType type = new ConfigType();
/* 306 */     ArrayList pathNames = new ArrayList();
/*     */ 
/* 308 */     pathNames.add(new PathName("ou", "configs"));
/* 309 */     pathNames.add(new PathName("ou", "userConfigs"));
/* 310 */     pathNames.add(new PathName("ou", username));
/*     */ 
/* 312 */     type.setName(username);
/* 313 */     type.setDescription(username);
/* 314 */     type.setDisNames(pathNames);
/*     */     try
/*     */     {
/* 318 */       InetAddress addr = InetAddress.getLocalHost();
/* 319 */       String localHostname = addr.getHostName().toString();
/* 320 */       type.setComputerName(localHostname);
/*     */     }
/*     */     catch (UnknownHostException e)
/*     */     {
/* 324 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 327 */     this.configTypeDao.create(type);
/*     */   }
/*     */ 
/*     */   public void assginCollectionPosition(String user)
/*     */   {
/* 335 */     assginPosition(user, "数据采集");
/*     */   }
/*     */ 
/*     */   public void assginDbServerPosition(String user)
/*     */   {
/* 344 */     assginPosition(user, "数据管理");
/*     */   }
/*     */ 
/*     */   public void assginPosition(String name, String position)
/*     */   {
/* 353 */     DistinguishedName _userName = getDNameByUid(name);
/* 354 */     ConfigType type = this.configTypeDao.getByDn(_userName);
/* 355 */     if (type == null) {
/* 356 */       return;
/*     */     }
/* 358 */     ArrayList pathNames = new ArrayList();
/* 359 */     pathNames.add(new PathName("ou", "configs"));
/* 360 */     pathNames.add(new PathName("ou", "positionConfigs"));
/*     */ 
/* 362 */     ConfigTypeDao dao = getConfigTypeDao();
/* 363 */     ConfigType t = dao.getByDn(dao.getDName(pathNames));
/* 364 */     t.setDisNames(pathNames);
/*     */ 
/* 366 */     ArrayList _positions = dao.listSubType(t);
/* 367 */     for (ConfigType _position : _positions)
/* 368 */       if (_position.getDescription().contains(position)) {
/* 369 */         String _id = _position.getName();
/* 370 */         String _description = _position.getDescription();
/* 371 */         type.setCatetory(_id);
/* 372 */         type.setDescription(type.getDescription() + "[" + _description + "]");
/*     */ 
/* 374 */         ArrayList pathNames2 = new ArrayList();
/* 375 */         pathNames2.add(new PathName("ou", "configs"));
/* 376 */         pathNames2.add(new PathName("ou", "userConfigs"));
/* 377 */         pathNames2.add(new PathName("ou", type.getName()));
/* 378 */         type.setDisNames(pathNames2);
/*     */ 
/* 380 */         getConfigTypeDao().update(type);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setBootAssignDao(BootAssignDao bootAssignDao)
/*     */   {
/* 387 */     this.bootAssignDao = bootAssignDao;
/*     */   }
/*     */ 
/*     */   public BootAssignDao getBootAssignDao() {
/* 391 */     return this.bootAssignDao;
/*     */   }
/*     */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.ldapClient.ldapHelper
 * JD-Core Version:    0.6.0
 */