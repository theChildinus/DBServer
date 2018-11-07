package org.apache.servicemix.wsn.push;


import java.util.Date;

import javax.jws.WebService;


import org.apache.log4j.Logger;

import edu.bupt.wsmonitor.mongo.DbController;

@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
		serviceName="INotificationProcess")
public class NotificationProcessImpl implements INotificationProcess{

	static Logger logger = Logger.getLogger(NotificationProcessImpl.class);
	DbController memDbCon = null;
	public int wsnum; 
	
	public NotificationProcessImpl(int wsnum)
	{
		this.wsnum = wsnum;
	}
	
	public  void notificationProcess(String content) 
	{
			String Level = null;
			memDbCon = new DbController();
			int levelStart = content.indexOf("<Level>")+"<Level>".length();
			int levelEnd = content.indexOf("</Level>");
			
			if ( levelEnd == -1 || ( levelStart == -1 ) ) {
				return;
			}
			
			Level = content.substring(levelStart,levelEnd);

			if(Level != null && Level.length() > 0) 
			{
				int temp = content.indexOf("<Sql>");
				int sqlStart = 0;
				int sqlEnd  = 0;
				String sqlItem = null;
				
				if (Level.toLowerCase().equals("realtime"))
				{			
					while(temp !=-1)
					{		
						sqlStart = temp + "<Sql>".length();
						sqlEnd = content.indexOf("</Sql>", sqlStart);
						sqlItem = content.substring(sqlStart, sqlEnd);
						
						
						memDbCon.executeSql(sqlItem);
						
						temp = content.indexOf("<Sql>", sqlEnd);
					}
				} 
			
			}
	}
}

