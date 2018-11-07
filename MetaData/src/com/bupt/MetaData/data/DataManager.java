package com.bupt.MetaData.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.bupt.MetaData.common.Commons;
/**
 * 正在运行的amoeba列表信息
 * @author chenxiaojie
 *
 */
public class DataManager {

	private static  DataManager instance = new DataManager();
	private List<String> amoebaList = new ArrayList<String>();
	
	public static DataManager getInstance()
	{
		return instance;
	}
	
	public List<String> getAmoebaList()
	{
		return amoebaList;
	}
	
	public void addAmoebaData(String item)
	{
		amoebaList.add(item);
		System.out.println(amoebaList.size());
		
		updateAmoebaFile();
	}
	
	public void removeAmoebaData(String item)
	{
		amoebaList.remove(item);
		updateAmoebaFile();
	}
	
	public void updateAmoebaFile()
	{
		String amoebaPath = Commons.getInstance().getRunPath() + File.separator +"amoebaList.xml";
		java.util.List<String> list = DataManager.getInstance().getAmoebaList();
		StringBuilder content=new StringBuilder();
		
		content.append("<amoebaList>\n");
		for(int i=0;i<list.size();i++)
		{
			String temp = list.get(i);
			String[] arr = temp.split(":");
			content.append("	<amoeba>\n");
			content.append("		<ip>"+arr[0] +"</ip>\n");
			content.append("		<port>"+arr[1] +"</port>\n");
			content.append("	</amoeba>\n");
		}
		content.append("</amoebaList>\n");
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(amoebaPath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.write(content.toString());
		out.flush();
		out.close();
	}
}
