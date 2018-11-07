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
 * 提供给messageReceiver的下载amoeba可运行列表
 * Amoebalist
 *
 */

public class ReceiverServer extends Thread {
	
	private ServerSocket serverSocket;
	private int port = Integer.parseInt(PublicResource.getString("receiverPort"));
	
	public ReceiverServer()
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
		private String amoebaPath = Commons.getInstance().getRunPath() + File.separator +"amoebaList.xml";
		
		public HandleClient(Socket socket,String clientip)
		{
			this.socket = socket;	
			this.clientip = clientip;
		}
		
		public void run()
		{
			
			String str;
			File amoebaFile = new File(amoebaPath);
			
			try
			{			
				BufferedReader inAmoeba = new BufferedReader(new InputStreamReader(
						new FileInputStream(amoebaFile),
						"GB2312"));
				
				outWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"GB2312"));	
			
				
				
				outWriter.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
				outWriter.println("<config>");
				
				while((str = inAmoeba.readLine()) != null)
				{
					outWriter.println(str);
				}
				
				outWriter.println("</config>");
						
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
		ReceiverServer temp = new ReceiverServer();
		temp.start();
	}
}