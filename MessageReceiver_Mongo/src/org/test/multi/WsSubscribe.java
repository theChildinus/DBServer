package org.test.multi;

import java.util.ArrayList;
import java.util.List;

import wsn.wsnclient.command.SendWSNCommand;

public class WsSubscribe 
{
		private static String WEBSERVICE_ADDR = null;
		private static String WSN_ADDR = null;
		public String topic=null;
		
		public WsSubscribe(String webserviceAddr, String wsnAddr,String topic)
		{
			this.WEBSERVICE_ADDR = webserviceAddr;
			this.WSN_ADDR = wsnAddr;
			this.topic = topic;
		}
		
		/**
		 * @param args
		 * @throws Exception 
		 */
		public void subscribe() 
		{
			// TODO Auto-generated method stub
//			MessageSubscribe ms = new MessageSubscribe();
				
			SendWSNCommand temp = new SendWSNCommand(WEBSERVICE_ADDR, WSN_ADDR);
		
			//订阅		
			String str1 = null;
			String str2 = null;
			try 
			{
				str1 = temp.createPullPoint();
				if(str1 == "ok")
					System.out.println(topic + "  success in creatPullPoint~~");
				else
					System.out.println(topic + "  failed in creatPullPoint~~");
				
				str2 = temp.subscribe(topic);
				if(str2 == "ok")
					System.out.println(topic + "  success in subscribe~~");
				else
					System.out.println(topic + "  failed in subscribe~~");
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated c`atch block
				e.printStackTrace();	
			}
					
			
		}
}
