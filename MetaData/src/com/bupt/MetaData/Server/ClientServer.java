package com.bupt.MetaData.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.bupt.MetaData.Backup.BackupFromDB;
import com.bupt.MetaData.common.Commons;
import com.bupt.MetaData.common.PublicResource;
/**
 * 组合配置文件供客户端下载
 * localMongoList & Amoebalist
 *
 */

public class ClientServer extends Thread {
	
	private ServerSocket serverSocket;
	private int port = Integer.parseInt(PublicResource.getString("configPort"));
	
	public ClientServer()
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
				Socket socket = serverSocket.accept();
				String clientip = socket.getInetAddress().getHostAddress();
				System.out.println("新连接");
				HandleClient thread = new HandleClient(socket,clientip);
				thread.start();
				//socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}		
	}

	private class HandleClient extends Thread{
		
		private Socket socket;	
		private String clientip;
		private PrintWriter outWriter;
		private String mongoPath = Commons.getInstance().getRunPath() + File.separator +"mongoList.xml";
		private String amoebaPath = Commons.getInstance().getRunPath() + File.separator +"amoebaList.xml";
		
		public HandleClient(Socket socket,String clientip)
		{
			this.socket = socket;	
			this.clientip = clientip;
		}
		
		public void run()
		{
			
			String str;
			File mongoFile = new File(mongoPath);
			File amoebaFile = new File(amoebaPath);
			
			try
			{
				BufferedReader inMongo = new BufferedReader(new InputStreamReader(
						new FileInputStream(mongoFile),
						"GB2312"));
				
				BufferedReader inAmoeba = new BufferedReader(new InputStreamReader(
						new FileInputStream(amoebaFile),
						"GB2312"));
				
				outWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"GB2312"));	
			
				
				
				outWriter.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
				outWriter.println("<config>");
				
				while((str = inMongo.readLine()) != null)
				{
					outWriter.println(str);
				}
				
				while((str = inAmoeba.readLine()) != null)
				{
					outWriter.println(str);
				}
				
				outWriter.println("</config>");
						
				inMongo.close();
				inAmoeba.close();
				outWriter.flush();
				outWriter.close();				
			}catch(IOException e)
			{
				e.printStackTrace();
			}
			finally{
					try {
						if(socket!=null)
							socket.close();
						
						BackupFromDB backup = new BackupFromDB(clientip);
						backup.backupData();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
		}
		
	}
	
	public static void main(String[] args){
		System.out.println("server running");
		ClientServer temp = new ClientServer();
		temp.start();
	}
}