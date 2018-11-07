package edu.bupt.wsmonitor.mongo;


import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import edu.bupt.wsmonitor.common.MCoreUtil;
import edu.bupt.wsmonitor.common.PublicResource;

public class DbController {
	static Logger logger = Logger.getLogger(DbController.class);
	Mongo conn;
	String dbName = PublicResource.getString("dbName");

	public DbController()
	{
		try {
//			MongoConfigManager.Instacen().start();
			conn = MongoConfigManager.Instacen().getConnection();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void executeSql(String sql) {
		logger.info("执行SQL: "+sql);//sql以分号；分割，包括三部分（1）集合名（2）修改域的筛选项，已逗号分隔（3）想要修改的域值		
		String arr[]=sql.split(";");
		DB db = conn.getDB(dbName);
		DBCollection coll = db.getCollection( arr[0] );	
						
		DBObject queryCondition=new BasicDBObject();  
		
		String condition[]=arr[1].split(",");
		for(int i=0;i<condition.length;i++)
		{
			if(condition[i] != null)
			{
				String item[]=condition[i].split("->");
				queryCondition.put(item[0], MCoreUtil.getObjectIdFromString( item[1] ));//查询类关键字
			}
		}
		
		DBObject setValue=new BasicDBObject(); 
		String values[]=arr[2].split(",");
		for(int i=0;i<values.length;i++)
		{
			if(values[i] != null)
			{
				System.out.println(values[i]);
				String item[]=values[i].split("->");
				setValue.put(item[0], item[1]); 
			}
		}
			 
			  
		DBObject upsertValue=new BasicDBObject("$set",setValue);  

		System.out.println(queryCondition.toString()+" "+upsertValue.toString());
		coll.update(queryCondition, upsertValue, false, true);
	}
	
	public static void main(String[] args)
	{
		DbController mem = new DbController();
		mem.executeSql("student;id:1,schoolNum:0;name:student1");
	}

}
