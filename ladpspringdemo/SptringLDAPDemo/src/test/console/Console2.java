package test.console;

import java.util.Iterator;
import java.util.List;

import bupt.intt.wsmonitor.servicemonitor.dao.PersonDao;
import bupt.intt.wsmonitor.servicemonitor.domain.Person;
import bupt.intt.wsmonitor.servicemonitor.domain.WebServiceEntity;
import bupt.intt.wsmonitor.servicemonitor.dao.WebServiceDao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Console2 {    
	public static void main(String[] args) {	
		/*ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		//PersonDaoImpl personDaoImpl = (PersonDaoImpl)context.getBean("personDao");		
		WebServiceDao serviceDao = (WebServiceDao)context.getBean("webServiceDao1");
		
		WebServiceEntity service = new WebServiceEntity();
		service.setServiceName("ADD Opertaion");
		service.setReturnType("int");
		service.setServiceParameters("int,int");
		service.setServiceUrl("http://www.bupt.edu.cn/intt/services/AddService");
		
		serviceDao.create(service);*/
		
		String content = "abcdedfg,Ð»êÍ,¼úÈË,ºÃµÄ,";
		content = content.substring(0,content.length() - 1);
		System.out.println(content);
		
		
		
		
		/*
		List personList = personDaoImpl.GetAllPersons();
		for(Iterator iterator = personList.iterator();iterator.hasNext();) {
			Person p = (Person)iterator.next();
			System.out.println(p);
		}
		System.out.println(personList.size());*/
		
		//Person p = new Person();
		//p.setCn("Chen2");
		//p.setSn("Davied");
		//p.setDescription("Ncepuer");
		//p.setGivenname("davy");
		//p.setMail("cdw87@sina.com");
		//p.setManager("zy");
		//p.setUid("bupt-d3-1019");
		//p.setUserpassword("123456");
		
		//personDaoImpl.DeletePerson(p);
		
		//Person p = personDaoImpl.GetPersonByUId("uid=cbuckley");
		//System.out.println(p);
	}
}
