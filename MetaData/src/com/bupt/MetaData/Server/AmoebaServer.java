package com.bupt.MetaData.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.bupt.MetaData.common.Commons;
import com.bupt.MetaData.common.PublicResource;
import com.bupt.MetaData.data.DataManager;
/**
 * 推送最新的配置文件到amoeba
 * 接收amoeba的心跳信息
 *
 */

public class AmoebaServer extends Thread {
	
	private ServerSocket serverSocket;
	private List<HandleClient> threadArray = new ArrayList<HandleClient>();//线程数组，用于存放连接线程
	private int port = Integer.parseInt(PublicResource.getString("reportPort"));
	
	public AmoebaServer()
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
		ScanThread scan = new ScanThread();
		scan.start();
		
		while(true)
		{
			try{
				Socket socket = serverSocket.accept();
				System.out.println("建立新连接");
				HandleClient thread = new HandleClient(socket);
				thread.start();
				threadArray.add(thread);
				//socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}		
	}
	/**
	 * 推送最新更新文件线程
	 *
	 */
	private class ScanThread extends Thread
	{
		long oldModified_rule;
		long oldModified_dbsever;
		long newModified_rule;
		long newModified_dbsever;
		
		File ruleFile = new File(Commons.getInstance().getRunPath() + File.separator + "rule.xml");
		File dbServerFile = new File(Commons.getInstance().getRunPath() + File.separator + "dbServers.xml");
		
		public ScanThread()
		{
			oldModified_rule = ruleFile.lastModified(); 
			oldModified_dbsever = dbServerFile.lastModified();
		}
		
		public void run()
		{
			//根据文件是否修改判断当前文件是否是最新的配置文件，推送到amoeba客户端：rule.xml,dbServers.xml
			while(true)
			{
				newModified_rule = ruleFile.lastModified();
				newModified_dbsever = dbServerFile.lastModified();
				
				if(newModified_rule > oldModified_rule)
				{
					oldModified_rule = newModified_rule;
					
					try {
						BufferedReader in=new BufferedReader(new FileReader(ruleFile));
							
						String s;
						
						for(HandleClient client:threadArray)
						{
							client.out.println("rule.xml");	
						}	
						
						while((s=in.readLine())!=null)
						{
							for(HandleClient client:threadArray)
							{
								client.out.println(s);		
							}
						}
						
						for(HandleClient client:threadArray)
						{
							client.out.println("end");
							client.out.flush();	
						}	
						
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				if(newModified_dbsever > oldModified_dbsever)
				{
					oldModified_dbsever = newModified_dbsever;
					
					try {
						BufferedReader in=new BufferedReader(new FileReader(dbServerFile));
							
						String s;
						
						for(HandleClient client:threadArray)
						{
							client.out.println("dbServers.xml");	
						}	
						
						while((s=in.readLine())!=null)
						{
							for(HandleClient client:threadArray)
							{
								client.out.println(s);		
							}
						}
						
						for(HandleClient client:threadArray)
						{
							client.out.println("end");
							client.out.flush();	
						}	
						
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				//休眠一分钟在轮询配置文件
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class HandleClient extends Thread
	{
		private Socket socket;	
		private BufferedReader in ;
		private PrintWriter out ;
		
		public HandleClient(Socket socket)
		{
			this.socket = socket;			
		}
		
		public void run()
		{
			//接收从amoeba客户端发来的心跳信息，判断amoeba是否正常运行
			try {
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				
					while(true)
					{
						
						String s = in.readLine();
						if(s != null && !s.contains("exit"))//amoeba正常运行
						{
							System.out.println(s + " running...");
							if(!DataManager.getInstance().getAmoebaList().contains(s))
							{
								DataManager.getInstance().addAmoebaData(s);
							}
						}
						else if(s.contains("exit")) //amoeba故障退出
						{
							String[] arr = s.split("_");
							DataManager.getInstance().removeAmoebaData(arr[0]);
							break;
						}
					}

					threadArray.remove(this);
					
					System.out.println(this.toString() + "exit...");
					socket.close();
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}
	
	public static void main(String[] args){
		System.out.println("server running");
		AmoebaServer temp = new AmoebaServer();
		temp.start();
	}
}