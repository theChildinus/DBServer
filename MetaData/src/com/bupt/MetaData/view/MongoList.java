package com.bupt.MetaData.view;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Composite;

import com.bupt.MetaData.common.Commons;
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
 * 本地mongo配置面板
 *
 */

public class MongoList extends ViewPart
{
	public static final String ID = "com.bupt.MetaData.view.MongoList"; 

	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);


		final Group mongoGroup = new Group(composite, SWT.NONE);
		mongoGroup.setText("本地Mongo配置");
		mongoGroup.setBounds(50, 50, 800, 500);


		final Label tip = new Label(mongoGroup, SWT.NONE);
		tip.setText("提示:");
		tip.setBounds(30, 30,30, 20);
		
		final Text localMongo = new Text(mongoGroup, SWT.NONE);
		localMongo.setText("name：小区名,key：小区关键字,ip：本地ip地址，port：本地端口号");
		localMongo.setBounds(70, 30,650, 20);

		final Text mongotext = new Text(mongoGroup,SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		mongotext.setEditable(true);
		
		BufferedReader in = null;
		String s;
		StringBuilder str=new StringBuilder();
		try {
			in = new BufferedReader(new FileReader(Commons.getInstance().getRunPath()+File.separator+"mongoList.xml"));
			while((s=in.readLine())!=null)
			{  
				str.append(s+"\n");  
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mongotext.setText(str.toString());
		mongotext.setBounds(30, 80, 750, 300);

		final Label result = new Label(mongoGroup, SWT.NONE);
		result.setForeground(SWTResourceManager.getColor(255, 0, 0));
		result.setText("        ");
		result.setBounds(650, 450, 60, 20);
		
		Button yesbtn = new Button(mongoGroup, SWT.NONE);
		yesbtn.setText("确定");
		yesbtn.setBounds(650, 400, 50, 20);
		yesbtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				String content = mongotext.getText();
				
				try {
					PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter(Commons.getInstance().getRunPath()+File.separator+"mongoList.xml")));
					out.write(content);
					out.flush();
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				result.setText("更新成功");
				
			}
		});	
		
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
	
}
