package net.jumperz.app.receiver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import net.jumperz.app.common.Commons;
import net.jumperz.app.common.PublicResource;
/**
 * 客户端从元数据服务器下载配置文件
 *
 */
public class Receiver{

	private String ip = PublicResource.getString("serverip");
	private int port = Integer.parseInt(PublicResource.getString("configPort"));
	private BufferedReader inReader;
	private PrintWriter outWriter;
	private static String outFile = Commons.getInstance().getRunPath() + File.separator +"amoeba.xml";
	private Socket socket = null;

	
	public void load()
	{
		String tempStr;
		try{
			socket = new Socket(ip, port);			
			inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outWriter=new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					outFile),"GB2312")));
			
			while((tempStr = inReader.readLine()) != null)
			{
				System.out.println(tempStr);
				outWriter.println(tempStr);
			}
			inReader.close();
			outWriter.flush();
			outWriter.close();
						
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(socket != null){
				try{
					socket.close();
				}catch(IOException e){
					e.printStackTrace();
				}			
			}
		}
	}
	public static void main(String[] args)
	{
		Receiver dd = new Receiver();
		dd.load();
	}
}

