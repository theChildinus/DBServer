/*    */ package bupt.intt.wsmonitor.servicemonitor.domain.test;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.dao.PersonDao;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.Person;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.TestCase;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class PersonTest extends TestCase
/*    */ {
/*    */   private PersonDao personDaoImpl;
/*    */ 
/*    */   public void setPersonDaoImpl(PersonDao personDaoImpl)
/*    */   {
/* 19 */     this.personDaoImpl = personDaoImpl;
/*    */   }
/*    */ 
/*    */   public PersonDao getPersonDaoImpl() {
/* 23 */     return this.personDaoImpl;
/*    */   }
/*    */ 
/*    */   protected void onSetUp()
/*    */   {
/* 28 */     ApplicationContext context = 
/* 29 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 30 */     this.personDaoImpl = ((PersonDao)context.getBean("personDao"));
/*    */   }
/*    */ 
/*    */   protected void onTearDown() throws Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   protected String[] getConfigLocations() {
/* 38 */     return new String[] { "testContext.xml" };
/*    */   }
/*    */ 
/*    */   public void testGetAllPeople() {
/* 42 */     onSetUp();
/* 43 */     List personList = this.personDaoImpl.getAll();
/* 44 */     for (Iterator iterator = personList.iterator(); iterator.hasNext(); ) {
/* 45 */       Person p = (Person)iterator.next();
/* 46 */       System.out.println(p);
/*    */     }
/* 48 */     Assert.assertTrue(personList.size() > 0);
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.test.PersonTest
 * JD-Core Version:    0.6.0
 */