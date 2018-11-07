package com.bupt.MetaData.view;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Composite;

import com.bupt.MetaData.common.Commons;
import com.bupt.MetaData.data.DataManager;
import com.bupt.MetaData.swtDesigner.SWTResourceManager;


import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * amoeba运行状态面板
 *
 */

public class AmoebaStatus extends ViewPart
{
	public static final String ID = "com.bupt.MetaData.view.AmoebaStatus"; 

	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);


		final Group amoebaListGroup = new Group(composite, SWT.NONE);
		amoebaListGroup.setText("正在运行的amoeba列表");
		amoebaListGroup.setBounds(50, 50, 800, 500);
		
		final Text amoebaText = new Text(amoebaListGroup,SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		amoebaText.setEditable(true);
		amoebaText.setBounds(30, 80, 750, 300);

//		BufferedReader in = null;
//		String s;
//		StringBuilder str=new StringBuilder();
//		try {
//			in = new BufferedReader(new FileReader(Commons.getInstance().getRunPath()+File.separator+"amoebaList.xml"));
//			while((s=in.readLine())!=null)
//			{  
//				str.append(s+"\n");  
//			}
//			in.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		amoebaText.setText(str.toString());
		
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
		amoebaText.setText(content.toString());
		final Label result = new Label(amoebaListGroup, SWT.NONE);
		result.setForeground(SWTResourceManager.getColor(255, 0, 0));
		result.setText("        ");
		result.setBounds(650, 450, 60, 20);
		
		Button yesbtn = new Button(amoebaListGroup, SWT.NONE);
		yesbtn.setText("刷新");
		yesbtn.setBounds(650, 400, 50, 20);
		yesbtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
//				String content = amoebaText.getText();
//				
//				try {
//					PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter(Commons.getInstance().getRunPath()+File.separator+"config.xml")));
//					out.write(content);
//					out.flush();
//					out.close();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				result.setText("更新成功");
				
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
				amoebaText.setText(content.toString());
				
			}
		});	
		
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
	
}
