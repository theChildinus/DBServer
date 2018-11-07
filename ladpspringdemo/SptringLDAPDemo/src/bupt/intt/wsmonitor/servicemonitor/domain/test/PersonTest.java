package bupt.intt.wsmonitor.servicemonitor.domain.test;


import bupt.intt.wsmonitor.servicemonitor.dao.PersonDao;
import bupt.intt.wsmonitor.servicemonitor.domain.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import junit.framework.TestCase;


import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
public class PersonTest extends TestCase {
	private PersonDao personDaoImpl;
	
	public void setPersonDaoImpl(PersonDao personDaoImpl) {
		this.personDaoImpl = personDaoImpl;
	}

	public PersonDao getPersonDaoImpl() {
		return personDaoImpl;
	}
	
	protected void onSetUp(){
		//super.onSetUp();
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		personDaoImpl = (PersonDao)context.getBean("personDao");
	}
	
	protected void onTearDown() throws Exception {
		//super.onTearDown();		
	}
	
	protected String[] getConfigLocations() {
		return new String[] { "testContext.xml" };
	}
	
	public void testGetAllPeople() {
		onSetUp();
		List personList = personDaoImpl.getAll();
		for(Iterator iterator = personList.iterator();iterator.hasNext();) {
			Person p = (Person)iterator.next();
			System.out.println(p);
		}
		Assert.assertTrue(personList.size() > 0);
			
	}
	
}
