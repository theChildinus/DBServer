/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.test;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.dao.ConfigTypeDao;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.TestCase;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class ConfigTypeDaoTest extends TestCase
/*    */ {
/*    */   ConfigTypeDao typeDao;
/*    */ 
/*    */   public void testTypeCreate()
/*    */   {
/* 18 */     onSetUp();
/* 19 */     ConfigType type = new ConfigType();
/* 20 */     type.setName("北邮");
/* 21 */     type.setDescription("北邮");
/*    */ 
/* 23 */     ArrayList pathNames = new ArrayList();
/* 24 */     pathNames.add(new PathName("ou", "configs"));
/* 25 */     pathNames.add(new PathName("ou", "北邮"));
/* 26 */     type.setDisNames(pathNames);
/*    */ 
/* 28 */     this.typeDao.create(type);
/*    */ 
/* 30 */     ConfigType typeToGet = this.typeDao.getByDn(this.typeDao.getDName(type));
/* 31 */     Assert.assertEquals(typeToGet.getName(), type.getName());
/*    */   }
/*    */ 
/*    */   public void testList() {
/* 35 */     onSetUp();
/*    */ 
/* 37 */     ConfigType type = new ConfigType();
/* 38 */     type.setName("北邮");
/* 39 */     type.setDescription("北邮");
/*    */ 
/* 41 */     ArrayList pathNames = new ArrayList();
/* 42 */     pathNames.add(new PathName("ou", "configs"));
/*    */ 
/* 44 */     type.setDisNames(pathNames);
/*    */ 
/* 46 */     ArrayList _list = this.typeDao.listSubType(type);
/*    */ 
/* 48 */     System.out.println(_list.size());
/*    */ 
/* 50 */     Assert.assertNotNull(Boolean.valueOf(_list.size() > 0));
/*    */   }
/*    */ 
/*    */   protected void onSetUp()
/*    */   {
/* 55 */     ApplicationContext context = 
/* 56 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 57 */     this.typeDao = ((ConfigTypeDao)context.getBean("configTypeDao"));
/*    */   }
/*    */ 
/*    */   public void testDeleteChinldre() {
/* 61 */     ApplicationContext context = 
/* 62 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 63 */     this.typeDao = ((ConfigTypeDao)context.getBean("configTypeDao"));
/* 64 */     ArrayList _paths = new ArrayList();
/* 65 */     _paths.add(new PathName("ou", "configs"));
/* 66 */     _paths.add(new PathName("ou", "userConfigs"));
/* 67 */     _paths.add(new PathName("ou", "2532"));
/*    */ 
/* 69 */     ConfigType t = this.typeDao.getByDn(this.typeDao.getDName(_paths));
/* 70 */     t.setDisNames(_paths);
/* 71 */     this.typeDao.delete(t);
/* 72 */     ArrayList _types = this.typeDao.listSubType(t);
/* 73 */     Assert.assertTrue(_types.size() == 0);
/*    */   }
/*    */ 
/*    */   public void testModifyCategory() {
/* 77 */     ApplicationContext context = 
/* 78 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 79 */     this.typeDao = ((ConfigTypeDao)context.getBean("configTypeDao"));
/* 80 */     ArrayList _paths = new ArrayList();
/* 81 */     _paths.add(new PathName("ou", "configs"));
/* 82 */     _paths.add(new PathName("ou", "userConfigs"));
/* 83 */     _paths.add(new PathName("ou", "cfds"));
/*    */ 
/* 85 */     ConfigType t = this.typeDao.getByDn(this.typeDao.getDName(_paths));
/* 86 */     t.setCatetory("123131321312");
/* 87 */     t.setDisNames(_paths);
/* 88 */     this.typeDao.update(t);
/* 89 */     t = this.typeDao.getByDn(this.typeDao.getDName(_paths));
/*    */ 
/* 91 */     Assert.assertTrue(t.getCatetory().equals("123131321312"));
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.test.ConfigTypeDaoTest
 * JD-Core Version:    0.6.0
 */