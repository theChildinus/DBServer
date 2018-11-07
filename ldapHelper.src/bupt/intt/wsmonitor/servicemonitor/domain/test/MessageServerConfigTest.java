/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.test;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.dao.MessageServerDao;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
/*    */ import java.util.ArrayList;
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.TestCase;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class MessageServerConfigTest extends TestCase
/*    */ {
/*    */   MessageServerDao messageServerDao;
/*    */ 
/*    */   public MessageServerConfigTest()
/*    */   {
/* 20 */     ApplicationContext context = 
/* 21 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 22 */     this.messageServerDao = ((MessageServerDao)context.getBean("messageServerConfigDao"));
/*    */   }
/*    */ 
/*    */   public void testCreate()
/*    */   {
/* 27 */     MessageServerConfig config = new MessageServerConfig();
/* 28 */     config.setName("MsgServer1");
/* 29 */     config.setDescription("消息服务器1");
/* 30 */     config.setBrokerURI("http://localhost:8192/brokeruri/");
/* 31 */     config.setCreatePullPointURI("http://localhost:8192/createpullpoint");
/* 32 */     config.setIp("192.168.1.1");
/*    */ 
/* 34 */     ArrayList pathNames = new ArrayList();
/* 35 */     pathNames.add(new PathName("ou", "configs"));
/* 36 */     pathNames.add(new PathName("ou", "MsgServer1"));
/* 37 */     config.setDisNames(pathNames);
/*    */ 
/* 39 */     this.messageServerDao.create(config);
/*    */ 
/* 41 */     MessageServerConfig typeToGet = this.messageServerDao.getByDn(this.messageServerDao.getDName(config));
/* 42 */     Assert.assertEquals(typeToGet.getName(), config.getName());
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.test.MessageServerConfigTest
 * JD-Core Version:    0.6.0
 */