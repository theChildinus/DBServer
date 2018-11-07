package bupt.intt.wsmonitor.servicemonitor.domain.test;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao;
import bupt.intt.wsmonitor.servicemonitor.dao.DataServiceDao;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.DataServiceConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import junit.framework.Assert;
import junit.framework.TestCase;

public class DataServiceTest extends TestCase {
	DataServiceDao dataServiceDao;
	public DataServiceTest() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		dataServiceDao = (DataServiceDao)context.getBean("dbServiceDao");
	}
	
	public void testCreate() {
		
		DataServiceConfig config = new DataServiceConfig();
		config.setName("DbServer1");
		config.setDescription("数据服务器1");
		config.setBrokerURI("http://192.123.12.1/broker");
		config.setCustomerURI("http://123.123.123.123/service1");
		config.setDefaultReadCount("10");
		config.setTopics("MyTopic1,MyTopic2");
		
		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		pathNames.add(new PathName("ou","configs"));
		pathNames.add(new PathName("ou","DbServer1"));
		config.setDisNames(pathNames);
		
		dataServiceDao.create(config);
		
		DataServiceConfig typeToGet = dataServiceDao.getByDn(dataServiceDao.getDName(config));
		Assert.assertEquals(typeToGet.getName(),config.getName());
	}
}
