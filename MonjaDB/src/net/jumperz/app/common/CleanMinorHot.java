package net.jumperz.app.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.jumperz.app.MMonjaDBCore.MDataManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class CleanMinorHot extends Thread
{
	private String filter;
	
	public CleanMinorHot(String filter)
	{
		this.filter = filter;
	}
	public void run()
	{
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(filter != null)
		{
			//删除次热数据
			DB db = MDataManager.getInstance().getDB();
			Set<String> collections = db.getCollectionNames();
			
			System.out.println(db.getName() + "remote " + filter);
			String[] arr = filter.trim().split(":");
			
			Iterator iter = collections.iterator();   
			while(iter.hasNext())   
			{
				DBCollection coll = db.getCollection(iter.next().toString());
				
				BasicDBObject obj = new BasicDBObject();
				obj.put(arr[0],Integer.parseInt(arr[1]));
				
				coll.remove(obj);
			}
		}
	}

}
