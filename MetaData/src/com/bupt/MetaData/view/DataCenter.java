package com.bupt.MetaData.view;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.bupt.MetaData.common.Commons;
import com.bupt.MetaData.swtDesigner.SWTResourceManager;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class DataCenter extends ViewPart{
	public static final String ID = "com.bupt.MetaData.view.DataCenter"; 

	@Override
	public void createPartControl(Composite  parent) {
		// TODO Auto-generated method stub
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
	
		final Group dataCenterListGroup = new Group(composite, SWT.NONE);
		dataCenterListGroup.setText("磁盘空间已使用（已MB为单位）");
		dataCenterListGroup.setBounds(50, 50, 800, 500);
		
		final Text datacenterText = new Text(dataCenterListGroup,SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		datacenterText.setEditable(true);
		datacenterText.setBounds(30, 80, 750, 300);
		datacenterText.setText(getUsedStorage().toString());
		
		final Label result = new Label(dataCenterListGroup, SWT.NONE);
		result.setForeground(SWTResourceManager.getColor(255, 0, 0));
		result.setText("        ");
		result.setBounds(650, 450, 60, 20);
		
		Button yesbtn = new Button(dataCenterListGroup, SWT.NONE);
		yesbtn.setText("刷新");
		yesbtn.setBounds(650, 400, 50, 20);
		yesbtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				StringBuilder content=new StringBuilder();
				content =getUsedStorage();
				datacenterText.setText(content.toString());
				result.setText("刷新成功");
				
			}
		});	
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	
	public StringBuilder getUsedStorage()
	{		
		StringBuilder content = new StringBuilder();
	
		String filePath = Commons.getInstance().getRunPath() +"dbServers.xml";
		
		System.out.println(filePath.toString());
		SAXBuilder builder = new SAXBuilder();
		
		builder.setValidation(false);//不验证dtd文件	
		builder.setIgnoringElementContentWhitespace(true);
		File file = new File(filePath);
		
		if(file.exists())
		{
			try {
				Document document = builder.build(file);
				Element root = document.getRootElement();
				
				List<Element> dbs = root.getChildren();

				for(int i=1;i<dbs.size();i++)
				{
					Element item=dbs.get(i);
					Element factoryConfig =(Element)item.getChildren().get(0);
					List<Element> propertyList=factoryConfig.getChildren("property");
					
					String ip=propertyList.get(0).getValue();
					String port = propertyList.get(1).getValue();
				    
					content.append(ip + ":" + port+"\n");
					
					Mongo m = new Mongo(ip,Integer.parseInt(port));
					DB db;
					
					List<String>  list= m.getDatabaseNames();
					for(int n=0;n<list.size();n++)
					{
						db = m.getDB(list.get(n));
						int storage =Integer.valueOf(db.getStats().get("storageSize").toString()).intValue()/(1024*1024);
						content.append("     "+db.getName()+" :"+ storage+"\n");
					}
				}
				
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
//		Mongo m = null;
//		
//		try {
//			m = new Mongo("127.0.0.1",3333);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MongoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		DB db;
//		
//		List<String>  list= m.getDatabaseNames();
//		for(int i=0;i<list.size();i++)
//		{
//			db = m.getDB(list.get(i));
//			int storage =Integer.valueOf(db.getStats().get("storageSize").toString()).intValue()/(1024*1024);
//			content.append(db.getName()+" :"+ storage+"\n");
//		}
		return content;
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Mongo m = new Mongo("127.0.0.1",3333);
			DB db;
			
			List<String>  list= m.getDatabaseNames(); 
			for(int i=0;i<list.size();i++)
			{
				db = m.getDB(list.get(i));
				System.out.println(db.getName()+" :"+ db.getStats().get("storageSize"));
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String filePath = Commons.getInstance().getRunPath() +"rule.xml";
//		
//		System.out.println(filePath.toString());
//		SAXBuilder builder = new SAXBuilder();	
//		File file = new File(filePath);
//		
//		if(file.exists())
//		{
//			try {
//				Document document = builder.build(file);
//				Element root = document.getRootElement();
//				
//				Element tablerule = root.getChild("tableRule");
//				System.out.println(tablerule.getName());
//				
//			} catch (JDOMException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

//	}
	}

}
