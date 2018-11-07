/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.test;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.dao.MemDBDao;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*    */ import java.util.ArrayList;
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.TestCase;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class MemDBDaoTest extends TestCase
/*    */ {
/* 17 */   MemDBDao memdbDao = null;
/*    */ 
/*    */   public MemDBDaoTest() {
/* 20 */     ApplicationContext context = new ClassPathXmlApplicationContext(
/* 21 */       "testContext.xml");
/* 22 */     this.memdbDao = ((MemDBDao)context.getBean("memDbDao"));
/*    */   }
/*    */ 
/*    */   public void testCreate() {
/* 26 */     MemDBConfig config = new MemDBConfig();
/* 27 */     config.setName("DbServer1");
/* 28 */     config.setDescription("数据服务器1");
/*    */ 
/* 30 */     config.setInitHostname("BUPT-FUCKU");
/* 31 */     config.setInitIP("192.168.1.1");
/* 32 */     config.setInitPort("8002");
/* 33 */     config.setInitScriptPath("d:/initScriptPath");
/* 34 */     config.setMaxConnection("10");
/* 35 */     config.setMinConnection("1");
/* 36 */     config.setMemDbUrl("http://localhost/h2/mem:test/");
/*    */ 
/* 38 */     ArrayList pathNames = new ArrayList();
/* 39 */     pathNames.add(new PathName("ou", "configs"));
/* 40 */     pathNames.add(new PathName("ou", "DbServer1"));
/* 41 */     config.setDisNames(pathNames);
/*    */ 
/* 43 */     this.memdbDao.create(config);
/*    */ 
/* 45 */     MemDBConfig typeToGet = this.memdbDao.getByDn(this.memdbDao
/* 46 */       .getDName(config));
/* 47 */     Assert.assertEquals(typeToGet.getName(), config.getName());
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.test.MemDBDaoTest
 * JD-Core Version:    0.6.0
 */