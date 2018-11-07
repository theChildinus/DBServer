package net.jumperz.app.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * 热数据处理 
 *
 */
public class HotDataHandler {
	
	private String path = Commons.getInstance().getRunPath() + File.separator +"hotdata.txt";
	private static String hotData = "";
	private BufferedReader reader;
	private PrintWriter writer;
	private static HotDataHandler instance = new HotDataHandler();
	
	public static HotDataHandler getInstance()
	{
		return instance;
	}
	
	public HotDataHandler()
	{    
        try {
			 reader = new BufferedReader(new FileReader(new File(path)));			 
				
			 hotData = reader.readLine();
			 		 
			 reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void setHot(String data)
	{
		hotData = data;
	}
	
	public String getHot()
	{
		return hotData;
	}
	
	public void flushHotData()
	{
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(path),false)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.println(hotData);

		writer.close();
		
	}
	
	public static void main(String[] args)
	{
		HotDataHandler handler = new HotDataHandler();
		handler.flushHotData();
	}

}
