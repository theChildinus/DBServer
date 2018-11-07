package bupt.intt.wsmonitor.servicemonitor.domain.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.intt.wsmonitor.servicemonitor.dao.MemDBDao;
import bupt.intt.wsmonitor.servicemonitor.ldapClient.LdapConfigs;
import bupt.intt.wsmonitor.servicemonitor.ldapClient.ldapHelper;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ClientTest extends TestCase {
    ldapHelper clientHelper = null;
	public ClientTest() {
		/*ApplicationContext context = new ClassPathXmlApplicationContext(
				"testContext.xml");
		clientHelper = (ldapHelper) context.getBean("clientHelper");*/
	}

	public void testClient() {
		ArrayList configList =clientHelper.listConfigs("235");
		Assert.assertTrue(configList.size() == 4);

	}
	
	public void testLdapConfigs() {
		LdapConfigs configs= LdapConfigs.Instance("cfds");
		Assert.assertEquals(configs.getDataServiceConfig().getTopics(),"{topics}");
		//Assert.assertNotNull(configs.getMemDbConfig());
		//Assert.assertNotNull(configs.getDbServerCofig());
		//Assert.assertNotNull(configs.getMessageServerConfig());
		
	}
	
	public void testTopics() {
		Date now = new Date();
		System.out.println(now.getTime());
		
		LdapConfigs configs = LdapConfigs.Instance("235");
		HashMap<String,String> topics = configs.getTopics();
		
		now = new Date();
		
		
		
		/*for(String key : topics.keySet()) {
			System.out.println("key : " + key +" ,  " + topics.get(key));
		}*/
		Set<String> keys = topics.keySet();
		
		int i = 0;
		System.out.println(now.getTime());
		while(i < 100) {
			Iterator iterator = keys.iterator();
			
			while(iterator.hasNext()) {
				String key = iterator.next().toString();
				String s = key + ":" + topics.get(key);
				System.out.println(s);
				i ++;				
			}		
			System.out.println("-----------------");
			
		}
		
			
		
		
		Date now2  = new Date();
		System.out.println(now.getTime());
		System.out.println(now2.getTime());
		
		Assert.assertTrue(topics.size() > 0);
	}
}
