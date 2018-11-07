package com.bupt.MetaData.Backup;

import java.io.File;
import java.io.IOException;

import com.bupt.MetaData.common.Commons;
import com.bupt.MetaData.common.PublicResource;

public class BackupFromDB {
	private String mongoDbBin = PublicResource.getString("mongodbBin");//"C:\\Program Files\\MySQL\\MySQL Server 5.1\\bin\\";
	private String host = PublicResource.getString("mongodbHost");
	private String dbName = PublicResource.getString("configDB");
	private String backupScript = Commons.getInstance().getRunPath();
	private String cliengip;
	
	public BackupFromDB(String clientip)
	{
		this.cliengip = clientip;
	}
	
	public void backupData()
	{
		String command1 = "cmd /C " + "\"" + mongoDbBin + "mongodump.exe\" -h " 
		+ host + " -d " + dbName  +" -o " + backupScript;
		System.out.println(command1);

		try {
			Process process = Runtime.getRuntime().exec(command1);
			process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String command2 = "cmd /C " + "\"" + mongoDbBin + "mongorestore.exe\" -h " 
		+ cliengip + " -d yyt"  +" --directoryperdb  " + backupScript+dbName;
		System.out.println(command2);
		
		try {
			Process process = Runtime.getRuntime().exec(command2);
			process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		BackupFromDB backup = new BackupFromDB("127.0.0.1");
		backup.backupData();
	}

}
