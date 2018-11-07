package com.bupt.MetaData.view;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
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
 * amoeba配置文件rule.xml,dataServers.xml面板
 *
 */

public class AmoebaConfig extends ViewPart
{
	public static final String ID = "com.bupt.MetaData.view.AmoebaConfig"; 

	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);

		final Group group = new Group(composite, SWT.NONE);
		group.setBounds(15, 15, 980, 600);
		
		final Group amobeaConfigGroup = new Group(group, SWT.NONE);
		amobeaConfigGroup.setText("数据服务器配置");
		amobeaConfigGroup.setBounds(15, 15, 450, 450);
		
		final Text dataServerText = new Text(amobeaConfigGroup,SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		dataServerText.setEditable(true);
		
		String s;
		StringBuilder dataStr=new StringBuilder();
		
		try {
			BufferedReader datain=new BufferedReader(new FileReader(Commons.getInstance().getRunPath()+File.separator+"dbServers.xml"));

			while((s=datain.readLine())!=null)
			{  
				dataStr.append(s+"\n");  
			}
			datain.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataServerText.setText(dataStr.toString());
		dataServerText.setBounds(20, 20, 400, 400);
		
		final Group amobeaRuleGroup = new Group(group, SWT.NONE);
		amobeaRuleGroup.setText("匹配规则配置");
		amobeaRuleGroup.setBounds(500, 15, 450, 450);
	
		final Text ruleText = new Text(amobeaRuleGroup,SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		ruleText.setEditable(true);

		StringBuilder ruleStr=new StringBuilder();
		
		try {
			BufferedReader rulein=new BufferedReader(new FileReader(Commons.getInstance().getRunPath()+File.separator+"rule.xml"));

			while((s=rulein.readLine())!=null)
			{  
				ruleStr.append(s+"\n");  
			}
			rulein.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ruleText.setText(ruleStr.toString());
		ruleText.setBounds(20, 20, 400, 400);

		final Label result = new Label(group, SWT.NONE);
		result.setForeground(SWTResourceManager.getColor(255, 0, 0));
		result.setText("        ");
		result.setBounds(900, 550, 60, 20);
		
		Button yesbtn = new Button(group, SWT.NONE);
		yesbtn.setText("确定");
		yesbtn.setBounds(900, 500, 50, 20);
		yesbtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				String content_data = dataServerText.getText();
				String content_rule = ruleText.getText();
				
				try {
					PrintWriter out_data=new PrintWriter(new BufferedWriter(new FileWriter(Commons.getInstance().getRunPath()+File.separator+"dbServers.xml")));
					out_data.write(content_data);
					out_data.flush();
					out_data.close();
					
					PrintWriter out_rule=new PrintWriter(new BufferedWriter(new FileWriter(Commons.getInstance().getRunPath()+File.separator+"rule.xml")));
					out_rule.write(content_rule);
					out_rule.flush();
					out_rule.close();
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
