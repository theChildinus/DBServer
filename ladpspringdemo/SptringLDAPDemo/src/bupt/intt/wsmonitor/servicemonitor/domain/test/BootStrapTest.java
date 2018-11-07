package bupt.intt.wsmonitor.servicemonitor.domain.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.intt.wsmonitor.servicemonitor.dao.BootStrapDao;
import bupt.intt.wsmonitor.servicemonitor.dao.DBServerDao;
import bupt.intt.wsmonitor.servicemonitor.domain.BootStrapConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.DBServerConfig;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import junit.framework.Assert;
import junit.framework.TestCase;

public class BootStrapTest extends TestCase {
	//bootStrapDao
	BootStrapDao bootStrapDao;
	public BootStrapTest() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		bootStrapDao = (BootStrapDao)context.getBean("bootStrapDao");
	}
	
	public void testCreate() {
		
		BootStrapConfig config = new BootStrapConfig();
		config.setName("DbServer1");
		config.setDescription("数据服务器1");
		config.setDisplayName("displayName");
		config.setRunPath("runpath");
		config.setIconUrl("iconUrl");
		config.setHalfOpcityImgUrl("halfOpcityImg");
		config.setImgUrl("imgUrl");
		config.setProcessName("processName");
		
		
		
		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		pathNames.add(new PathName("ou","configs"));
		pathNames.add(new PathName("ou","DbServer1"));
		config.setDisNames(pathNames);
		
		bootStrapDao.create(config);
		
		BootStrapConfig typeToGet = bootStrapDao.getByDn(bootStrapDao.getDName(config));
		Assert.assertEquals(typeToGet.getName(),config.getName());
	}
	
	public void testSearch() {
		List allBootStraps = bootStrapDao.searchAll();
		System.out.println(allBootStraps.size());
		Assert.assertTrue(allBootStraps.size() > 0);
	}
	
	public void testSerachById() {
		BootStrapConfig config = bootStrapDao.searchById("20100813151001_BootStrap");
		Assert.assertNotNull(config);
	}
	
	public void testSearchByCategory() {
		BootStrapConfig config = bootStrapDao.searchByCategory("图形监控程序");
		Assert.assertNotNull(config);
	}
}
