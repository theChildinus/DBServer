package org.test.multi;

import java.io.FileNotFoundException;
import javax.xml.ws.Endpoint;
import org.apache.servicemix.wsn.push.NotificationProcessImpl;

public class WebServiceApp{
	public static void main(String[] args) throws FileNotFoundException{
//		System.out.println("Starting Server");
//		NotificationProcessImpl implementor = new NotificationProcessImpl();		
//		String address = "http://10.108.166.66:9000/INotificationProcess";
////		String wsnAddress = "http://10.108.166.191:8192";
//		Endpoint.publish(address, implementor);
////		NotificationProcessImpl1 implementor1 = new NotificationProcessImpl1();
////		String address1 = "http://10.108.166.237:9000/INotificationProcess";
////		Endpoint.publish(address1, implementor1);
////		Endpoint.publish(address, implementor);
//		System.out.println("Server start!");
		
//		SendWSNCommand sendCommand = new SendWSNCommand(address, wsnAddress);
//		try {
//			
//			String response1 = sendCommand.createPullPoint();
//			if(response1 == "ok")
//				System.out.println("success in createPullPoint!");
//			else{
//				System.out.println("failed in createPullPoint!");
//				System.exit(0);
//			}		
//			
//			String response2 = sendCommand.subscribe("myTopic");
//			if(response2 == "ok")
//				System.out.println("success in subscrbing!");
//			else{
//				System.out.println("failed in subscribing!");
//				System.exit(0);
//			}
//			
//			sendCommand.notify("myTopic", 	"<alarm:Alarm xmlns:alarm=\"http://alarms.some-host\">"+
//											"<Name>Kettle Overfill</Name>"+
//											"<Desc>Kettle Overfill Alarm</Desc>"+
//											"<Date>2007-09-22-12:00:30:100</Date>"+
//											"<Severity>4</Severity>"+
//											"<Value>110.2</Value>"+
//											"<Ack>false</Ack>"+
//											"</alarm:Alarm>");
//			System.out.println("################");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}