package com.bupt.MetaData.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.bupt.MetaData.common.PublicResource;
import com.bupt.MetaData.handler.HotDataHandler;

/**
 * 为客户端修改配置文件rulex.xml请求服务
 *
 */

public class HotDataServer extends Thread {
	
	private ServerSocket serverSocket;
	private int port = Integer.parseInt(PublicResource.getString("hotPort"));
	private Object object = new Object();
	
	public HotDataServer()
	{
			try {
				serverSocket = new ServerSocket(port);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public void run()
	{	
		while(true)
		{
			try{
				synchronized(object)
				{
					Socket socket = serverSocket.accept();
					
					BufferedReader inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					String str = inReader.readLine();
					System.out.println(str);
					HotDataHandler handler = new HotDataHandler(str);
					handler.handle();
					
					inReader.close();
					socket.close();
				}
				
				//socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}		
	}
	
	public static void main(String[] args){
		System.out.println("server running");
		HotDataServer temp = new HotDataServer();
		temp.start();
	}
}