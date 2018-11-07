package test.console;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;
import bupt.intt.wsmonitor.servicemonitor.dao.WebServiceDao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Console1 {
	public static void main(String[] args) {
		
		//PersonDaoImpl personDaoImpl = (PersonDaoImpl)context.getBean("personDao");		
		//Delete();
		int a = 10;
		int b = 20;
		int c = 30;
		
		ArrayList<Integer> _list = new ArrayList<Integer>();
		_list.add(10);
		_list.add(20);
		_list.add(30);
		
		ArrayList<Integer> _list2 = (ArrayList<Integer>) _list.clone();
		b = 40;
		for(Iterator iterator = _list2.iterator();iterator.hasNext();) {
			System.out.println(iterator.next());
		}
		
		for(Iterator iterator = _list.iterator();iterator.hasNext();) {
			System.out.println(iterator.next());
		}
		
	}
	
	public static void addOperation()
	{
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
		
		WebServiceEntity service = new WebServiceEntity();
		service.setServiceName("add-operation");
		service.setReturnType("int");
		service.setServiceParameters("int,int");
		service.setServiceUrl("http://www.bupt.edu.cn/int/wsmonitor/services/opertaion/addopertaion");
		
		serviceDao.create(service);
	}
	
	public static void ListOperation() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
		List<WebServiceEntity> _list = serviceDao.getAll();
		for(int i = 0; i < _list.size(); i++) {
			System.out.println(_list.get(i).getServiceName());
			System.out.println(_list.get(i).getServiceParameters());
			System.out.println(_list.get(i).getReturnType());
			System.out.println(_list.get(i).getServiceUrl());
		}
			
	}
	
	public static void LookUp() {
		String _dn = "wn=addoperation,ou=services";
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
		WebServiceEntity service = serviceDao.getByDn(_dn);
		System.out.println(service.getServiceName());
		System.out.println(service.getServiceParameters());
		System.out.println(service.getReturnType());
		System.out.println(service.getServiceUrl());
	}
	
	public static void Delete() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
		WebServiceEntity service = new WebServiceEntity();
		service.setServiceName("add-operation");
		serviceDao.delete(service);
	}
	
	
}
