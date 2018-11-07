/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.test;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
/*    */ import bupt.intt.wsmonitor.servicemonitor.ldapClient.LdapConfigs;
/*    */ import bupt.intt.wsmonitor.servicemonitor.ldapClient.ldapHelper;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.TestCase;
/*    */ 
/*    */ public class ClientTest extends TestCase
/*    */ {
/* 19 */   ldapHelper clientHelper = null;
/*    */ 
/*    */   public void testClient()
/*    */   {
/* 27 */     ArrayList configList = this.clientHelper.listConfigs("235");
/* 28 */     Assert.assertTrue(configList.size() == 4);
/*    */   }
/*    */ 
/*    */   public void testLdapConfigs()
/*    */   {
/* 33 */     LdapConfigs configs = LdapConfigs.Instance("cfds");
/* 34 */     Assert.assertEquals(configs.getDataServiceConfig().getTopics(), "{topics}");
/*    */   }
/*    */ 
/*    */   public void testTopics()
/*    */   {
/* 42 */     Date now = new Date();
/* 43 */     System.out.println(now.getTime());
/*    */ 
/* 45 */     LdapConfigs configs = LdapConfigs.Instance("235");
/* 46 */     HashMap topics = configs.getTopics();
/*    */ 
/* 48 */     now = new Date();
/*    */ 
/* 55 */     Set keys = topics.keySet();
/*    */ 
/* 57 */     int i = 0;
/* 58 */     System.out.println(now.getTime());
/* 59 */     while (i < 100) {
/* 60 */       Iterator iterator = keys.iterator();
/*    */ 
/* 62 */       while (iterator.hasNext()) {
/* 63 */         String key = iterator.next().toString();
/* 64 */         String s = key + ":" + (String)topics.get(key);
/* 65 */         System.out.println(s);
/* 66 */         i++;
/*    */       }
/* 68 */       System.out.println("-----------------");
/*    */     }
/*    */ 
/* 75 */     Date now2 = new Date();
/* 76 */     System.out.println(now.getTime());
/* 77 */     System.out.println(now2.getTime());
/*    */ 
/* 79 */     Assert.assertTrue(topics.size() > 0);
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.test.ClientTest
 * JD-Core Version:    0.6.0
 */