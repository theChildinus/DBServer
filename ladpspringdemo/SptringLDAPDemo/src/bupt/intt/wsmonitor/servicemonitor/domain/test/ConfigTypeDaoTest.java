package bupt.intt.wsmonitor.servicemonitor.domain.test;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.intt.wsmonitor.servicemonitor.dao.ConfigTypeDao;
import bupt.intt.wsmonitor.servicemonitor.domain.ConfigType;
import bupt.intt.wsmonitor.servicemonitor.domain.PathName;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ConfigTypeDaoTest extends TestCase {
	ConfigTypeDao typeDao;
	
	public void testTypeCreate() {
		onSetUp();
		ConfigType type = new ConfigType();
		type.setName("北邮");
		type.setDescription("北邮");
		
		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		pathNames.add(new PathName("ou","configs"));
		pathNames.add(new PathName("ou","北邮"));
		type.setDisNames(pathNames);
		
		typeDao.create(type);
		
		ConfigType typeToGet = typeDao.getByDn(typeDao.getDName(type));
		Assert.assertEquals(typeToGet.getName(),type.getName());
	}
	
	public void testList() {
		onSetUp();
		//typeDao.getByDn("");
		ConfigType type = new ConfigType();
		type.setName("北邮");
		type.setDescription("北邮");
		
		ArrayList<PathName> pathNames = new ArrayList<PathName>();
		pathNames.add(new PathName("ou","configs"));
		//pathNames.add(new PathName("ou","北邮"));
		type.setDisNames(pathNames);
		
		ArrayList<ConfigType> _list = typeDao.listSubType(type);
		
		System.out.println(_list.size());
		
		Assert.assertNotNull(_list.size() > 0);
	}
	
	protected void onSetUp(){
		//super.onSetUp();
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		typeDao = (ConfigTypeDao)context.getBean("configTypeDao");
	}
	
	public void testDeleteChinldre() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		typeDao = (ConfigTypeDao)context.getBean("configTypeDao");
		ArrayList<PathName> _paths = new ArrayList<PathName>();
		_paths.add(new PathName("ou","configs"));
		_paths.add(new PathName("ou","userConfigs"));
		_paths.add(new PathName("ou","2532"));
		
		ConfigType t = typeDao.getByDn(typeDao.getDName(_paths));
		t.setDisNames(_paths);
		typeDao.delete(t);
		ArrayList<ConfigType> _types = typeDao.listSubType(t);
		Assert.assertTrue(_types.size() == 0);
	}
	
	public void testModifyCategory() {
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("testContext.xml");
		typeDao = (ConfigTypeDao)context.getBean("configTypeDao");
		ArrayList<PathName> _paths = new ArrayList<PathName>();
		_paths.add(new PathName("ou","configs"));
		_paths.add(new PathName("ou","userConfigs"));
		_paths.add(new PathName("ou","cfds"));
		
		ConfigType t = typeDao.getByDn(typeDao.getDName(_paths));
		t.setCatetory("123131321312");
		t.setDisNames(_paths);
		typeDao.update(t);
		t = typeDao.getByDn(typeDao.getDName(_paths));
		
		Assert.assertTrue(t.getCatetory().equals("123131321312"));
	}
}
