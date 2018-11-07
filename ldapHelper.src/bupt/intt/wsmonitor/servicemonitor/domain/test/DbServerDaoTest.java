/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.test;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*    */ import java.util.ArrayList;
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.TestCase;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class DbServerDaoTest extends TestCase
/*    */ {
/*    */   DBServerDao dbServerDao;
/*    */ 
/*    */   public DbServerDaoTest()
/*    */   {
/* 18 */     ApplicationContext context = 
/* 19 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 20 */     this.dbServerDao = ((DBServerDao)context.getBean("dbServerConfigDao"));
/*    */   }
/*    */ 
/*    */   public void testCreate123()
/*    */   {
/* 25 */     DBServerConfig config = new DBServerConfig();
/* 26 */     config.setName("DbServer1");
/* 27 */     config.setDescription("数据服务器1");
/* 28 */     config.setIp("192.168.1.1");
/* 29 */     config.setHostname("BUPT-007");
/* 30 */     config.setPort("8002");
/* 31 */     config.setMemdbBackurl("http://localhost/h2/mem:test");
/* 32 */     config.setMemdbBackuser("sa");
/* 33 */     config.setMemdbBackpwd(" ");
/* 34 */     config.setMemdbBackScriptPath("d:/initScript/");
/* 35 */     config.setMysqldb("test");
/* 36 */     config.setMysqldir("c:/program files/my sql 5.0/bin/");
/* 37 */     config.setMysqluser("root");
/* 38 */     config.setMysqlpwd("root");
/*    */ 
/* 41 */     ArrayList pathNames = new ArrayList();
/* 42 */     pathNames.add(new PathName("ou", "configs"));
/* 43 */     pathNames.add(new PathName("ou", "DbServer1"));
/* 44 */     config.setDisNames(pathNames);
/*    */ 
/* 46 */     this.dbServerDao.create(config);
/*    */ 
/* 48 */     DBServerConfig typeToGet = this.dbServerDao.getByDn(this.dbServerDao.getDName(config));
/* 49 */     Assert.assertEquals(typeToGet.getName(), config.getName());
/*    */   }
/*    */ 
/*    */   public void testExist() {
/* 53 */     ArrayList disNames = new ArrayList();
/* 54 */     disNames.add(new PathName("ou", "configs"));
/* 55 */     disNames.add(new PathName("ou", "userConfigs"));
/* 56 */     disNames.add(new PathName("ou", "235"));
/* 57 */     disNames.add(new PathName("ou", "20100717094712_DbServer"));
/*    */ 
/* 59 */     ConfigType t = new ConfigType();
/* 60 */     t.setDisNames(disNames);
/*    */ 
/* 62 */     DBServerConfig typeToGet = this.dbServerDao.getByDn(this.dbServerDao.getDName(t));
/* 63 */     Assert.assertNotNull(typeToGet);
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.test.DbServerDaoTest
 * JD-Core Version:    0.6.0
 */