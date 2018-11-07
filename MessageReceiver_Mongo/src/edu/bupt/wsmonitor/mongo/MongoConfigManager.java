package edu.bupt.wsmonitor.mongo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;

import edu.bupt.wsmonitor.common.Commons;
import edu.bupt.wsmonitor.common.PublicResource;


public class MongoConfigManager {

	private static int minConnection = 1;

	private static int maxConnection = 10;

	private static int _multiConnection=10;// number of connection in
										// connection-pool , >=1
	private List<Mongo> connections = new LinkedList<Mongo>();
	private static int iterator = 0;
	static Logger MongoConfigManager = Logger.getLogger(MongoConfigManager.class);

	private String ip= PublicResource.getString("metaDataServer");
	private int port = Integer.parseInt(PublicResource.getString("receiverPort"));
	private static String outFile = Commons.getInstance().getRunPath() + File.separator +"amoeba.xml";
	
    static MongoConfigManager instance = new MongoConfigManager();
	
	public static MongoConfigManager Instacen() {
		return instance;
	}   
	
	public void start() throws ClassNotFoundException, UnknownHostException, MongoException {

		String tempStr;
		Socket socket = null;
		try
		{
			/**
			 * 从元数据服务器下载正在运行的分布式代理列表
			 */
			socket = new Socket(ip, port);			
	
			BufferedReader inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outWriter=new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
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
		
		File file = new File(outFile);
        if (!file.exists())
            return;
        
        /**
         * 抽取正在运行的分布式代理列表ip地址和端口号，创建实例池
         */
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
				System.out.println(amoebainfo);
				String arr[]=amoebainfo.split(",");
				
				Mongo mongo = new Mongo(arr[0].trim(),Integer.parseInt(arr[1].trim()));  
				MongoOptions options = mongo.getMongoOptions();  
				options.autoConnectRetry = true;  
				options.connectionsPerHost = maxConnection; 
				connections.add(mongo);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_multiConnection = connections.size();
         System.out.println(connections.size());

	}


	public void setConnection(ArrayList<Mongo> _connectionList) {
		this.connections = _connectionList;
	}
	public  Mongo getConnection()//存取方法同步
	{
		try {
			if (_multiConnection == 1)
			{
				return this.connections.get(0);
			} 
			else
			{
				increment();
				MongoConfigManager.debug("iterator of connection pools:" + iterator);
				if (iterator == _multiConnection)
				{
					iterator = 0;
				}

				return connections.get(iterator);
			}
		} catch (NullPointerException ex) {
			if (_multiConnection == 1)
			{
				return this.connections.get(0);
			} 
			else 
			{
				increment();
				MongoConfigManager.debug("iterator: " + iterator);
				if (iterator == _multiConnection)
				{
					iterator = 0;
				}

				return connections.get(iterator);
			}
		}
	}

	private static void increment() {
		if (iterator < _multiConnection - 1) {
			iterator++;
		} else {
			iterator = 0;
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, MongoException, ClassNotFoundException
	{
		MongoConfigManager dd = new MongoConfigManager();
		dd.Instacen().start();
	}

}
