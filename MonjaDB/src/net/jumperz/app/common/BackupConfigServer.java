package net.jumperz.app.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BackupConfigServer {
	private static String mongoBin = PublicResource.getString("mongobin");
	private static String databaseName = PublicResource.getString("configdb");
	private static String ip= PublicResource.getString("serverip");
	private static String port=PublicResource.getString("backupPort");
	private static String mongodbScriptName=PublicResource.getString("backupPath");
	
	public static BackupConfigServer instance = new BackupConfigServer();
	
	public static BackupConfigServer getInstance()
	{
		return instance;
	}
	
	private  BackupConfigServer()
	{		
	}
	
	public void BackupRemoteData()
	{
		String command = "cmd /C " + "\"" + mongoBin + "mongodump.exe\" -h " 
		+ ip+ " --port " +port+ " -d "+ databaseName +" -o " + mongodbScriptName;
		System.out.println("doing backup..." +" "+command);
		
		System.out.println(command);
		try{
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));   
	        String line = null;   
	        while((line = br.readLine()) != null){   
	            System.out.println(line);   
	        }   
	        br.close(); 
		    process.waitFor();
			
		}catch(IOException e){
			e.printStackTrace();
			return;
		}catch (InterruptedException e2){
			e2.printStackTrace();
			return;
		}finally{			
		}		
	}
	
	public void restoreRemoteData()
	{
		String command = "cmd /c " + "\"" + mongoBin + "mongorestore.exe\" " 
		+ " -d  config" +" --drop " + mongodbScriptName+"config";
		System.out.println("doing restore..." +" "+command);
		
		System.out.println(command);
		try{
			//进程在执行命令时，输出会有buffer，如果buffer满了，没有进程去读，会导致进程阻塞等待。
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));   
	        String line = null;   
	        while((line = br.readLine()) != null){   
	            System.out.println(line);   
	        }   
	        br.close();   


		process.waitFor();		
		}catch(IOException e){
			e.printStackTrace();
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{	
		}		
	}
	
	public static void main(String[] args)
	{
		BackupConfigServer.getInstance().BackupRemoteData();
		BackupConfigServer.getInstance().restoreRemoteData();
	}

}
