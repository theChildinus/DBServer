package net.jumperz.app.receiver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import net.jumperz.app.common.PublicResource;
/**
 * 客户端请求修改配置文件
 *
 */
public class HotDataClient extends Thread{

	private String ip = PublicResource.getString("serverip");
	private int port = Integer.parseInt(PublicResource.getString("hotPort"));
	private String hotData;
	private PrintWriter outWriter;
	private Socket socket = null;
	
	public HotDataClient(String hotData)
	{
		this.hotData = hotData;
		try{
			socket = new Socket(ip, port);			
			outWriter=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));				
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		outWriter.println(hotData);
		
		outWriter.flush();
		outWriter.close();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	public static void main(String[] args)
	{
		HotDataClient dd = new HotDataClient("teeest");
		dd.start();
	}
}

