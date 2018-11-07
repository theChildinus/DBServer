

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * (1)Amoeba 启动心跳传送
 * (2)Amoeba接收从元数据服务器传送过来的最新配置文件
 * @author Xiaojie Chen
 *
 */
public class AmoebaClient extends Thread{
	
	private HandleServer handler;
	private ReportClient client;
	
	public void run()
	{
			
			client = new ReportClient();
			client.start();
			
			handler = new HandleServer();
			handler.start();
		
		
	}
	/**
	 * 响应从元数据服务器传过来的更新文件请求，更新amoeba本地的rule.xml和dbServers.xml文件
	 * @author chenxiaojie
	 *
	 */
	private class HandleServer extends Thread
	{
		
		public void run()
		{
			String temp;
			PrintWriter  writer = null;
			
			while(true)
			{
				try {
					if(client.inReader.ready())
					{
						File ruleFile = new File(Commons.getInstance().getRunPath() + File.separator + "rule.xml");
						File dbServersFile = new File(Commons.getInstance().getRunPath() + File.separator + "dbServers.xml");
						
						String firstLine = client.inReader.readLine();
						System.out.println(ruleFile.getAbsolutePath());
						if(firstLine.equals("rule.xml"))
						{
							writer = new PrintWriter(new BufferedWriter(new FileWriter(ruleFile)));
							
						}
						else
						{
							writer = new PrintWriter(new BufferedWriter(new FileWriter(dbServersFile)));
						}
						
						while(!(temp = client.inReader.readLine()).equals("end"))
						{
							writer.println(temp);
						}
						
						writer.flush();
						writer.close();
					}
					
			
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 向元数据库服务器每隔一分钟发送心跳信息
	 * @author chenxiaojie
	 *
	 */
	private class ReportClient extends Thread
	{

		private String ip =PublicResource.getString("reportIP");
		private int port = Integer.parseInt(PublicResource.getString("reportPort"));
		private BufferedReader inReader;
		private PrintWriter outWriter;
		private Socket socket = null;

	
		public ReportClient()
		{
			try{
				socket = new Socket(ip, port);			
				inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outWriter=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));				
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	
		public void run()
		{
			while(true)
			{
				boolean flag=AmoebaHandler.getInstance().getRunning();
				if(flag)
				{
					try {
						outWriter.println(InetAddress.getLocalHost().getHostAddress()+":8066");
						outWriter.flush();
						Thread.sleep(60000);
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else
				{
					try {
						outWriter.println(InetAddress.getLocalHost().getHostAddress()+":8066"+"_exit");
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outWriter.flush();
				}			
			}
		}
	}
	public static void main(String[] args)
	{
		AmoebaClient dd = new AmoebaClient();
		dd.start();
	}
}

