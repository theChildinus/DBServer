package bupt.intt.wsmonitor.servicemonitor.domain.test;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao;
import bupt.intt.wsmonitor.servicemonitor.dao.MemDBDao;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.MemDBConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import junit.framework.Assert;
import junit.framework.TestCase;

public class MemDBDaoTest extends TestCase {
	MemDBDao memdbDao = null;

	public MemDBDaoTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"testContext.xml");
		memdbDao = (MemDBDao) context.getBean("memDbDao");
	}

	public void testCreate() {
		MemDBConfig config = new MemDBConfig();
		config.setName("DbServer1");
		config.setDescription("数据服务器1");
		
		config.setInitHostname("BUPT-FUCKU");
		config.setInitIP("192.168.1.1");
		config.setInitPort("8002");
		config.setInitScriptPath("d:/initScriptPath");
		config.setMaxConnection("10");
		config.setMinConnection("1");
		config.setMemDbUrl("http://localhost/h2/mem:test/");		

		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		pathNames.add(new PathName("ou", "configs"));
		pathNames.add(new PathName("ou", "DbServer1"));
		config.setDisNames(pathNames);

		memdbDao.create(config);

		MemDBConfig typeToGet = memdbDao.getByDn(memdbDao
				.getDName(config));
		Assert.assertEquals(typeToGet.getName(), config.getName());
	}
}
