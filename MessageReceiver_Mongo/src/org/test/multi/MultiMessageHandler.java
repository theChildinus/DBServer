package org.test.multi;

import edu.bupt.wsmonitor.mongo.DbController;

public class MultiMessageHandler extends Thread {

	public String message = null;

	public MultiMessageHandler() {
		setDaemon(true);
		start();
	}

	public MultiMessageHandler(String message) {
		this.message = message;
		setDaemon(true);
		start();
	}

	public void run(){
		while(true){
			try{
				System.out.println(message);
				int start = message.indexOf("<sql>");
				System.out.println(start);
				int end = message.indexOf("</sql>");
				System.out.println(end);
				String sql = "";
				if((start != -1) && (end != -1))
					sql = message.substring(start + 5, end);
				
				DbController memDbCon = new DbController();
				memDbCon.executeSql(sql);
				System.out.println(sql);
				
				sleep(1);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
