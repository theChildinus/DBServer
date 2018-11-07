package org.test.multi;

import java.util.ArrayList;
import java.util.List;

import wsn.wsnclient.command.SendWSNCommand;

import edu.bupt.wsmonitor.common.Publics;

public class MessageSubscribe {
	
	private static String WEBSERVICE_ADDR = null;
	private static String WSN_ADDR = null;
	
	public List<SendWSNCommand> sendWSNCommand = new ArrayList<SendWSNCommand>();
	public List<String> topic = new ArrayList<String>();
	
	public MessageSubscribe(String webserviceAddr, String wsnAddr){
		this.WEBSERVICE_ADDR = webserviceAddr;
		this.WSN_ADDR = wsnAddr;
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public void subscribe() {
		// TODO Auto-generated method stub
//		MessageSubscribe ms = new MessageSubscribe();
		
		topic= Publics.Instance().getTopics();
	
		System.out.println(topic.size());
		for(int i=0;i<topic.size();i++){
			SendWSNCommand temp = new SendWSNCommand(WEBSERVICE_ADDR, WSN_ADDR);
			sendWSNCommand.add(temp);
		}

		
		//订阅
		for(int i=0;i<topic.size();i++){
			String str1 = null;
			String str2 = null;
			try {
				str1 = sendWSNCommand.get(i).createPullPoint();
				if(str1 == "ok")
					System.out.println(topic.get(i) + "  success in creatPullPoint~~");
				else
					System.out.println(topic.get(i) + "  failed in creatPullPoint~~");
				
				str2 = sendWSNCommand.get(i).subscribe(topic.get(i));
				if(str2 == "ok")
					System.out.println(topic.get(i) + "  success in subscribe~~");
				else
					System.out.println(topic.get(i) + "  failed in subscribe~~");
			} catch (Exception e) {
				// TODO Auto-generated c`atch block
				e.printStackTrace();	
			}
		}
		
		
		
	}
}
