package bupt.intt.wsmonitor.servicemonitor.domain.test;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import junit.framework.Assert;
import junit.framework.TestCase;

public class DbServerDaoTest extends TestCase {
	DBServerDao dbServerDao;
	public DbServerDaoTest() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		dbServerDao = (DBServerDao)context.getBean("dbServerConfigDao");
	}
	
	public void testCreate123() {
		
		DBServerConfig config = new DBServerConfig();
		config.setName("DbServer1");
		config.setDescription("数据服务器1");
		config.setIp("192.168.1.1");
		config.setHostname("BUPT-007");
		config.setPort("8002");
		config.setMemdbBackurl("http://localhost/h2/mem:test");
		config.setMemdbBackuser("sa");
		config.setMemdbBackpwd(" ");
		config.setMemdbBackScriptPath("d:/initScript/");
		config.setMysqldb("test");
		config.setMysqldir("c:/program files/my sql 5.0/bin/");
		config.setMysqluser("root");
		config.setMysqlpwd("root");
		
		
		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		pathNames.add(new PathName("ou","configs"));
		pathNames.add(new PathName("ou","DbServer1"));
		config.setDisNames(pathNames);
		
		dbServerDao.create(config);
		
		DBServerConfig typeToGet = dbServerDao.getByDn(dbServerDao.getDName(config));
		Assert.assertEquals(typeToGet.getName(),config.getName());
	}
	
	public void testExist() {
		ArrayList<PathName> disNames = new ArrayList<PathName>();
		disNames.add(new PathName("ou","configs"));
		disNames.add(new PathName("ou","userConfigs"));
		disNames.add(new PathName("ou","235"));
		disNames.add(new PathName("ou","20100717094712_DbServer"));
		
		ConfigType t = new ConfigType();
		t.setDisNames(disNames);
		
		DBServerConfig typeToGet = dbServerDao.getByDn(dbServerDao.getDName(t));
		Assert.assertNotNull(typeToGet);
	}
}
