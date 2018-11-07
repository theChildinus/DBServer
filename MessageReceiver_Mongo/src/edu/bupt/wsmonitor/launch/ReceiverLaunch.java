package edu.bupt.wsmonitor.launch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;


import org.apache.servicemix.wsn.push.NotificationProcessImpl;
import org.test.multi.WsSubscribe;

import com.mongodb.MongoException;

import edu.bupt.wsmonitor.common.Publics;
import edu.bupt.wsmonitor.mongo.MongoConfigManager;


public class ReceiverLaunch{

	private static String webserviceAddress = null;
	private static String wsnAddress = null;
	public static List<String> topics = new ArrayList<String>();

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		webserviceAddress = args[0];
		wsnAddress = args[1];
		
		try {
			MongoConfigManager.Instacen().start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		topics= Publics.Instance().getTopics();
		for(int i =0;i<topics.size();i++)
		{
			System.out.println("Starting Server");
			NotificationProcessImpl implementor = new NotificationProcessImpl(i);
			Endpoint.publish(webserviceAddress+i, implementor);
			System.out.println("Server start!");
			
			WsSubscribe ms = new WsSubscribe(webserviceAddress+i, wsnAddress,topics.get(i));
			ms.subscribe();	
		}
		
		System.out.println("初始化成功");
		
	}

}
