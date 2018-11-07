/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.test;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.dao.DataServiceDao;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*    */ import java.util.ArrayList;
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.TestCase;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class DataServiceTest extends TestCase
/*    */ {
/*    */   DataServiceDao dataServiceDao;
/*    */ 
/*    */   public DataServiceTest()
/*    */   {
/* 19 */     ApplicationContext context = 
/* 20 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 21 */     this.dataServiceDao = ((DataServiceDao)context.getBean("dbServiceDao"));
/*    */   }
/*    */ 
/*    */   public void testCreate()
/*    */   {
/* 26 */     DataServiceConfig config = new DataServiceConfig();
/* 27 */     config.setName("DbServer1");
/* 28 */     config.setDescription("数据服务器1");
/* 29 */     config.setBrokerURI("http://192.123.12.1/broker");
/* 30 */     config.setCustomerURI("http://123.123.123.123/service1");
/* 31 */     config.setDefaultReadCount("10");
/* 32 */     config.setTopics("MyTopic1,MyTopic2");
/*    */ 
/* 34 */     ArrayList pathNames = new ArrayList();
/* 35 */     pathNames.add(new PathName("ou", "configs"));
/* 36 */     pathNames.add(new PathName("ou", "DbServer1"));
/* 37 */     config.setDisNames(pathNames);
/*    */ 
/* 39 */     this.dataServiceDao.create(config);
/*    */ 
/* 41 */     DataServiceConfig typeToGet = this.dataServiceDao.getByDn(this.dataServiceDao.getDName(config));
/* 42 */     Assert.assertEquals(typeToGet.getName(), config.getName());
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.test.DataServiceTest
 * JD-Core Version:    0.6.0
 */