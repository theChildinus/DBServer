package bupt.intt.wsmonitor.servicemonitor.domain.test;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.intt.wsmonitor.servicemonitor.dao.ConfigTypeDao;
import bupt.intt.wsmonitor.servicemonitor.dao.MessageServerDao;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.MessageServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import junit.framework.Assert;
import junit.framework.TestCase;

public class MessageServerConfigTest extends TestCase {
	MessageServerDao messageServerDao;
	
	public MessageServerConfigTest() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		messageServerDao = (MessageServerDao)context.getBean("messageServerConfigDao");
	}
	
	public void testCreate() {
		
		MessageServerConfig config = new MessageServerConfig();
		config.setName("MsgServer1");
		config.setDescription("消息服务器1");
		config.setBrokerURI("http://localhost:8192/brokeruri/");
		config.setCreatePullPointURI("http://localhost:8192/createpullpoint");
		config.setIp("192.168.1.1");
		
		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		pathNames.add(new PathName("ou","configs"));
		pathNames.add(new PathName("ou","MsgServer1"));
		config.setDisNames(pathNames);
		
		messageServerDao.create(config);
		
		MessageServerConfig typeToGet = messageServerDao.getByDn(messageServerDao.getDName(config));
		Assert.assertEquals(typeToGet.getName(),config.getName());
	}
}
