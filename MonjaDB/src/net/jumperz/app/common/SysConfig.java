package net.jumperz.app.common;

import java.math.BigDecimal;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class SysConfig {

	private static SysConfig instance = new SysConfig();
	private Mongo mg=null;
	private DB db = null;
	private DBCollection col=null;
	
	
	public static SysConfig Instance() {
		return instance;
	}
	public SysConfig() 
	{
		try {
			mg = new Mongo(PublicResource.getString("localip"), Integer.parseInt(PublicResource.getString("localPort")));
			db = mg.getDB(PublicResource.getString("configdb"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getColChiName(String colname)
	{
		col = db.getCollection("documentinfo");
		DBCursor cur = col.find();
		String chiname =null;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("engname").equals(colname))
			{
				chiname =  ob.get("chiname").toString();
				break;
			}
		}
		if(chiname == null)
			return colname;

		return chiname;
		
	}
	public String getColEngName(String colname)
	{
		col = db.getCollection("documentinfo");
		DBCursor cur = col.find();
		String engname=null;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("chiname").equals(colname))
			{
				engname =  ob.get("engname").toString();
				break;
			}
		}
		if(engname == null)
			return colname;

		return engname;
		
	}
	public int getColIsreal(String colname)
	{
		col = db.getCollection("documentinfo");
		DBCursor cur = col.find();
		int isreal=0;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("engname").equals(colname))
			{
				if(ob.get("isreal").toString() != null)
					isreal =  new BigDecimal(ob.get("isreal").toString()).intValue();
				break;
			}
		}
		return isreal;
		
	}
	public int getColIsoptional(String colname)
	{
		col = db.getCollection("documentinfo");
		DBCursor cur = col.find();
		int isoptional=0;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("engname").equals(colname))
			{
				if(ob.get("isoptional") != null)
					//Integer.parseInt(args),只适用于args是整数形式的情况
					isoptional =  (int)Float.parseFloat(ob.get("isoptional").toString());
				break;
			}
		}
		return isoptional;
		
	}
	public String getColOptionalItems(String colname)
	{
		col = db.getCollection("documentinfo");
		DBCursor cur = col.find();
		String optionalitems=null;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("engname").equals(colname))
			{
				if(ob.get("optionalItems").toString() != null)
					optionalitems =  ob.get("optionalItems").toString();
				break;
			}
		}
		return optionalitems;
		
	}
	public String getDBChiName(String engName)
	{
		col = db.getCollection("databaseinfo");
		DBCursor cur = col.find();
		String chiname=null;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("engname").equals(engName))
			{
				chiname =  ob.get("chiname").toString();
				break;
			}
		}
		if(chiname == null)
			return engName;

		return chiname;
		
	}
	public String getDBEngName(String chiName)
	{
		col = db.getCollection("databaseinfo");
		DBCursor cur = col.find();
		String engname=null;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("chiname").equals(chiName))
			{
				engname =  ob.get("engname").toString();
				break;
			}
		}
		if(engname == null)
			return chiName;

		return engname;
		
	}
	public String getTableChiName(String engName)
	{
		col = db.getCollection("tableinfo");
		DBCursor cur = col.find();
		String chiname=null;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("engname").equals(engName))
			{
				chiname =  ob.get("chiname").toString();
				break;
			}
		}
		if(chiname == null)
			return engName;

		return chiname;
		
	}
	public String getTableEngName(String chiName)
	{
		col = db.getCollection("tableinfo");
		DBCursor cur = col.find();
		String engname=null;
		
		while(cur.hasNext())
		{
			DBObject ob = cur.next();
			if(ob.get("chiname").equals(chiName))
			{
				engname =  ob.get("engname").toString();
				break;
			}
		}
		if(engname == null)
			return chiName;

		return engname;
		
	}
	
	public static void main(String[] args)
	{
		System.out.println(SysConfig.Instance().getColIsoptional("schoolNum"));
	}
}
