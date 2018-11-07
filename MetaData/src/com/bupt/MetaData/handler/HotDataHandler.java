package com.bupt.MetaData.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.bupt.MetaData.common.Commons;
/**
 * 根据用户热数据请求修改rule.xml文件
 *
 */

public class HotDataHandler 
{
	private String hotdata;
	
	public HotDataHandler(String hotdata)
	{
		this.hotdata = hotdata;
	}
	public void handle() 
	{	
		String[] arr = hotdata.split(";");
		String local = arr[0];
		String recenthot = arr[1];
		String minorhot = arr[2];
		
		recenthot = recenthot.toUpperCase();
		recenthot = recenthot.replace(":", "==");
		
		if(minorhot != null)
		{
			minorhot = minorhot.toUpperCase();
			minorhot = minorhot.replace(":", "==");
			System.out.println("recent:" +recenthot+ ";monor:" + minorhot);
		}
			
		String filePath = Commons.getInstance().getRunPath() + File.separator +"rule.xml";
		
		SAXBuilder builder = new SAXBuilder();
		File file = new File(filePath);
		
		if(file.exists())
		{
			try {
				Document document = builder.build(file);
				Element root = document.getRootElement();
				
				Element tablerule = root.getChild("tableRule");
				System.out.println(tablerule.getName());
				List<Element> rules = tablerule.getChildren();
				
				Iterator<Element> items = rules.iterator();
				
				while(items.hasNext())
				{
					Element ruleElement = items.next();
					
					Element expression = ruleElement.getChild("expression");
					Element writePools = ruleElement.getChild("writePools");
					
					if(expression.getText().contains(recenthot))
					{
						String tempwrite= writePools.getText();
						
						Element defaultPools = ruleElement.getChild("defaultPools");
						String tempdefault= defaultPools.getText();

						if(!tempwrite.contains(local))
						{
							writePools.setText(tempwrite + "," + local);
						}
						
						if(!tempdefault.contains(local))
						{
							defaultPools.setText(tempdefault + "," + local);
						}
						
						
					}
					
					if(expression.getText().contains(minorhot))
					{
						String tempwrite= writePools.getText();
						
						Element defaultPools = ruleElement.getChild("defaultPools");
						String tempdefault= defaultPools.getText();

						if(tempwrite.contains(local))
						{
							writePools.setText(tempwrite.replace(","+local, ""));
						}
						
						if(tempdefault.contains(local))
						{
							defaultPools.setText(tempdefault.replace(","+local, ""));
						}
						
						
					}
				}
				
				XMLOutputter out = new XMLOutputter();
			    out.output(document, new FileOutputStream(file));
				
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
       
		
	}
	
	
	public static void main(String[] args)
	{
		
		HotDataHandler handler = new HotDataHandler("WIN-G868SGOR4KJ;SCHOOLNUM==0;SCHOOLNUM==1");
		handler.handle();
	}

}
