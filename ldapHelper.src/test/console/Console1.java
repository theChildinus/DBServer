/*    */ package test.console;
/*    */ 
/*    */ import bupt.intt.wsmonitor.servicemonitor.dao.WebServiceDao;
/*    */ import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class Console1
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 18 */     int a = 10;
/* 19 */     int b = 20;
/* 20 */     int c = 30;
/*    */ 
/* 22 */     ArrayList _list = new ArrayList();
/* 23 */     _list.add(Integer.valueOf(10));
/* 24 */     _list.add(Integer.valueOf(20));
/* 25 */     _list.add(Integer.valueOf(30));
/*    */ 
/* 27 */     ArrayList _list2 = (ArrayList)_list.clone();
/* 28 */     b = 40;
/* 29 */     for (Iterator iterator = _list2.iterator(); iterator.hasNext(); ) {
/* 30 */       System.out.println(iterator.next());
/*    */     }
/*    */ 
/* 33 */     for (Iterator iterator = _list.iterator(); iterator.hasNext(); )
/* 34 */       System.out.println(iterator.next());
/*    */   }
/*    */ 
/*    */   public static void addOperation()
/*    */   {
/* 41 */     ApplicationContext context = 
/* 42 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 43 */     WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
/*    */ 
/* 45 */     WebServiceEntity service = new WebServiceEntity();
/* 46 */     service.setServiceName("add-operation");
/* 47 */     service.setReturnType("int");
/* 48 */     service.setServiceParameters("int,int");
/* 49 */     service.setServiceUrl("http://www.bupt.edu.cn/int/wsmonitor/services/opertaion/addopertaion");
/*    */ 
/* 51 */     serviceDao.create(service);
/*    */   }
/*    */ 
/*    */   public static void ListOperation() {
/* 55 */     ApplicationContext context = 
/* 56 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 57 */     WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
/* 58 */     List _list = serviceDao.getAll();
/* 59 */     for (int i = 0; i < _list.size(); i++) {
/* 60 */       System.out.println(((WebServiceEntity)_list.get(i)).getServiceName());
/* 61 */       System.out.println(((WebServiceEntity)_list.get(i)).getServiceParameters());
/* 62 */       System.out.println(((WebServiceEntity)_list.get(i)).getReturnType());
/* 63 */       System.out.println(((WebServiceEntity)_list.get(i)).getServiceUrl());
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void LookUp()
/*    */   {
/* 69 */     String _dn = "wn=addoperation,ou=services";
/* 70 */     ApplicationContext context = 
/* 71 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 72 */     WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
/* 73 */     WebServiceEntity service = serviceDao.getByDn(_dn);
/* 74 */     System.out.println(service.getServiceName());
/* 75 */     System.out.println(service.getServiceParameters());
/* 76 */     System.out.println(service.getReturnType());
/* 77 */     System.out.println(service.getServiceUrl());
/*    */   }
/*    */ 
/*    */   public static void Delete() {
/* 81 */     ApplicationContext context = 
/* 82 */       new ClassPathXmlApplicationContext("testContext.xml");
/* 83 */     WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
/* 84 */     WebServiceEntity service = new WebServiceEntity();
/* 85 */     service.setServiceName("add-operation");
/* 86 */     serviceDao.delete(service);
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     test.console.Console1
 * JD-Core Version:    0.6.0
 */