package net.jumperz.app.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 装载配置文件
 * 1、本地mongo服务匹配
 * 2、负载均衡---静态轮询选择amoeba 
 * @author 
 *
 */

public class LoadConfig 
{
	private  List<String> amoebaList = new ArrayList<String>();
	private  List<String> mongoInfoList = new ArrayList<String>();
	private  List<String> mongoNameList;
	private static LoadConfig Instance = new LoadConfig();
	
	public static LoadConfig getInstance()
	{
		return Instance;
	}
	
	public LoadConfig() 
	{	
		File file = new File(Commons.getInstance().getRunPath() + File.separator +"amoeba.xml");
		if (!file.exists())
            return;
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;
		Document doc = null;
		try {
			builder = dbf.newDocumentBuilder();
			doc = builder.parse(file); // 获取到xml信息
					
			NodeList amoebas = doc.getElementsByTagName("amoeba");
	
			for(int i = 0;i<amoebas.getLength();i++)
			{	
				String amoebainfo = doc.getElementsByTagName("amoeba").item(i).getTextContent().trim().replace("\n", ",");
				amoebaList.add(amoebainfo);
			}
			
			NodeList mongos = doc.getElementsByTagName("localMongo");
			
			for(int i = 0;i<mongos.getLength();i++)
			{	
				String localMongoinfo = doc.getElementsByTagName("localMongo").item(i).getTextContent().trim().replace("\n", ",");
				mongoInfoList.add(localMongoinfo);
			
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
	
	public String chooseAmoeba()
	{
		//随机数选取amobea
		return amoebaList.get((int)(Math.random()*(amoebaList.size())));
	}
	
	public String getLocalMongo()
	{
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String localIP = addr.getHostAddress().toString();
			
			for(int i=0;i<mongoInfoList.size();i++)
			{
				if(mongoInfoList.get(i).contains(localIP))
				{
					return mongoInfoList.get(i);
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "*,*,*,*";
		
	}
	
	public List<String> getNameList()
	{
		mongoNameList = new ArrayList<String>();
		
		for(int i=0;i<mongoInfoList.size();i++)
		{
			String temp =mongoInfoList.get(i);
			String[] arr = temp.split(",");
			mongoNameList.add(arr[0]);			
		}

		return mongoNameList;
		
	}
	
	public String locateNameInfo(String name)
	{
		String url;
		
		if(name == null)//首次连接没有筛选项的情况下，传进来的name为null
			return getLocalMongo();
		
		for(int i=0;i<mongoInfoList.size();i++)
		{
			if(mongoInfoList.get(i).contains(name))
			{
					return mongoInfoList.get(i);
			}
		}
		return null;
	}
	
	
	public static void main(String[] args)
	{
		
		System.out.println(LoadConfig.getInstance().getNameList());
	}

}
