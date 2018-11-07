/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.test;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.dao.BootStrapDao;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.BootStrapConfig;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.TestCase;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class BootStrapTest extends TestCase
/*    */ {
/*    */   BootStrapDao bootStrapDao;
/*    */ 
/*    */   public BootStrapTest()
/*    */   {
/* 21 */     ApplicationContext context = 
/* 22 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 23 */     this.bootStrapDao = ((BootStrapDao)context.getBean("bootStrapDao"));
/*    */   }
/*    */ 
/*    */   public void testCreate()
/*    */   {
/* 28 */     BootStrapConfig config = new BootStrapConfig();
/* 29 */     config.setName("DbServer1");
/* 30 */     config.setDescription("数据服务器1");
/* 31 */     config.setDisplayName("displayName");
/* 32 */     config.setRunPath("runpath");
/* 33 */     config.setIconUrl("iconUrl");
/* 34 */     config.setHalfOpcityImgUrl("halfOpcityImg");
/* 35 */     config.setImgUrl("imgUrl");
/* 36 */     config.setProcessName("processName");
/*    */ 
/* 40 */     ArrayList pathNames = new ArrayList();
/* 41 */     pathNames.add(new PathName("ou", "configs"));
/* 42 */     pathNames.add(new PathName("ou", "DbServer1"));
/* 43 */     config.setDisNames(pathNames);
/*    */ 
/* 45 */     this.bootStrapDao.create(config);
/*    */ 
/* 47 */     BootStrapConfig typeToGet = this.bootStrapDao.getByDn(this.bootStrapDao.getDName(config));
/* 48 */     Assert.assertEquals(typeToGet.getName(), config.getName());
/*    */   }
/*    */ 
/*    */   public void testSearch() {
/* 52 */     List allBootStraps = this.bootStrapDao.searchAll();
/* 53 */     System.out.println(allBootStraps.size());
/* 54 */     Assert.assertTrue(allBootStraps.size() > 0);
/*    */   }
/*    */ 
/*    */   public void testSerachById() {
/* 58 */     BootStrapConfig config = this.bootStrapDao.searchById("20100813151001_BootStrap");
/* 59 */     Assert.assertNotNull(config);
/*    */   }
/*    */ 
/*    */   public void testSearchByCategory() {
/* 63 */     BootStrapConfig config = this.bootStrapDao.searchByCategory("图形监控程序");
/* 64 */     Assert.assertNotNull(config);
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.test.BootStrapTest
 * JD-Core Version:    0.6.0
 */