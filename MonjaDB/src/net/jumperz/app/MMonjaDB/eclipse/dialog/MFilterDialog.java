package net.jumperz.app.MMonjaDB.eclipse.dialog;

import net.jumperz.app.MMonjaDB.eclipse.*;
import net.jumperz.app.MMonjaDBCore.MConstants;
import net.jumperz.app.MMonjaDBCore.MDataManager;
import net.jumperz.app.MMonjaDBCore.action.MActionManager;
import net.jumperz.app.common.CleanMinorHot;
import net.jumperz.app.common.HotDataHandler;
import net.jumperz.app.common.LoadConfig;
import net.jumperz.app.receiver.HotDataClient;
import net.jumperz.util.MProperties;

import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
/**
 * 依据配置文件显示过滤选项
 *
 */

public class MFilterDialog extends Dialog implements MConstants
{
	private MProperties prop = MDataManager.getInstance().getProp();
	private static Map<String,Integer> localMongoList = new HashMap<String,Integer>();
	private  Button[] buttons ;
	//--------------------------------------------------------------------------------
	public MFilterDialog( Shell parentShell )
	{
		super( parentShell );
		
	}
	protected  Point getInitialSize() {   
	    return   new  Point( 300 ,  400 );   
	} 
	//--------------------------------------------------------------------------------
	protected void configureShell( Shell newShell )
	{
		super.configureShell( newShell );
		newShell.setText( "过滤选项" );

		Image image = MUtil.getImage( newShell.getDisplay(), "server_lightning.png" );
		newShell.setImage( image );
	}
//--------------------------------------------------------------------------------
	protected void okPressed()
	{
		String currentname = null;
		String[] localArr ;
		String[] currentArr;
		String filterKey ="";
		String host ;
		String port ;
		
		for (int i = 0; i < buttons.length; i++) 
		{
		    if (buttons[i].getSelection())
		     {
		    	currentname = buttons[i].getText();
		    	MDataManager.getInstance().setCurrentVisit(currentname);

		    	localMongoList.put(currentname, localMongoList.get(currentname) + 1);
		     }
		}		
	
		String currentInfo = LoadConfig.getInstance().locateNameInfo(currentname);
		String localInfo = LoadConfig.getInstance().getLocalMongo();
		
		localArr = localInfo.split(",");
		String localIP = localArr[2];
		String localPort = localArr[3].trim();
		String localFilter = localArr[1];
		
		currentArr = currentInfo.split(",");
		String currentFilter = currentArr[1];
//		arr = nameInfo.split(",");
		
		String minorhot = HotDataHandler.getInstance().getHot();
		
		if(currentInfo.equals(localInfo))//本地启动
		{
			host = localIP;
			port = localPort;
			MDataManager.getInstance().setFilterKey(localFilter);
		}
		else if(currentname.equals(minorhot))//远程数据已在本地备份，可以本地访问
		{
			host =  "127.0.0.1";
			port = "27017";
			
			MDataManager.getInstance().setFilterKey(currentFilter);
		}
		else//远程amoeba访问数据中心
		{   
        	String minorfilter = null;
        	int num=2;
        	
        	//如果当前数据的访问次数大于本地缓存热数据的访问次数，并且当前当问的数据不是本地缓存的热数据，则更新本地热数据;最后一个条件是针对初次设定热数据
        	if(!currentname.equals(minorhot) && 
        			localMongoList.get(currentname)>localMongoList.get(minorhot) && 
        			localMongoList.get(currentname)>num)
        	{
    			System.out.println("here");
        		if(minorhot != "")
        		{
        			String minorinfo = LoadConfig.getInstance().locateNameInfo(minorhot);

        			if(minorinfo != null)
	       			{
	        			String[] minorarr = minorinfo.split(",");
		       			minorfilter = minorarr[1].trim();//次热数据切分关键字
	       			}
        		}
        		
        		//发送热数据请求到元数据服务器修改配置文件 	        	
	       		 try {
	       			String local = "local_"+InetAddress.getLocalHost().getHostName();
	       			
	       			HotDataClient handler = new HotDataClient(local + ";" + currentFilter.trim() 
	       					+ ";" + minorfilter);
	       			handler.start();
	       		} catch (UnknownHostException e) {
	       			// TODO Auto-generated catch block
	       			e.printStackTrace();
	       		}
	       		
	       		HotDataHandler.getInstance().setHot(currentname);
	       		
	       		//清除次热数据，只保留最新的热数据
	       		CleanMinorHot clear = new CleanMinorHot(minorfilter);
	       		clear.start();
	       		
	       		localMongoList.put(minorhot, 0);//重置0
        		  		
        	}
        	
//	        while(iterator.hasNext())   
//	        {        	
//	        	Map.Entry mapentry = (Map.Entry)iterator.next(); 
//	        	if(Integer.parseInt(mapentry.getValue().toString()) > 1 &&
//	        			!mapentry.getKey().toString().equals(minorhot))
//	        	{
//	        		
//	        		if(minorhot != "")
//	        		{
//	        			String minorinfo = LoadConfig.getInstance().locateNameInfo(minorhot);
//	        			System.out.println("minorinfo " + minorinfo);
//	        			if(minorinfo != null)
//		       			{
//		        			String[] minorarr = minorinfo.split(",");
//			       			System.out.println(remoteFilter.trim()+ " " +minorarr[1].trim());
//			       			minorfilter = minorarr[1].trim();
//		       			}
//	        		}
//	        		
//		        		//发送热数据请求到元数据服务器修改配置文件 	        	
//		       		 try {
//		       			String local = "local_"+InetAddress.getLocalHost().getHostName();
//		       			
//		       			HotDataClient handler = new HotDataClient(local + ";" + remoteFilter.trim() 
//		       					+ ";" + minorfilter);
//		       			handler.start();
//		       		} catch (UnknownHostException e) {
//		       			// TODO Auto-generated catch block
//		       			e.printStackTrace();
//		       		}
//	        		 
//		       		
//		       		
//		       		
//	        	}
//	        } 
	        
	        
			String URL = LoadConfig.getInstance().chooseAmoeba();
			
			System.out.println(URL+"*");
			String[] amoebaarr = URL.split(",");
			host = amoebaarr[0];
			port = amoebaarr[1].trim();
			
			MDataManager.getInstance().setFilterKey(currentFilter);
		}
		String dbName="test";
	
		System.out.println("connect " + host + ":" + port + "/" + dbName );
		MActionManager.getInstance().executeAction( "connect " + host + ":" + port + "/" + dbName );
		prop.setProperty( CONNECT_DIALOG_NORMAL_CONNECTION, true );
		
	
		//save config
		prop.setProperty( CONNECT_DIALOG_HOST, host );
		prop.setProperty( CONNECT_DIALOG_PORT, port + "" );
		prop.setProperty( CONNECT_DIALOG_DB, dbName );
	
		setReturnCode(OK);
		close();
	}
//--------------------------------------------------------------------------------
	protected Control createDialogArea( Composite parent )
	{
		Composite composite = (Composite)super.createDialogArea( parent );
	    ScrolledComposite panel = new  ScrolledComposite(composite, SWT.BORDER | SWT.V_SCROLL);   
	    panel.setLayoutData(new  GridData(GridData.FILL_VERTICAL));   
	    //强制显示滚动条    
	    panel.setAlwaysShowScrollBars(true );   
	    panel.setExpandHorizontal(true );   
	    panel.setExpandVertical(true );   
	    // 拖动滚动条里可以看到的Composite的最大高度    
	    panel.setMinHeight(800 );
	    panel.setMinWidth(220);
	       
	    panel.setLayout(new  GridLayout( 1 ,  false ));   
	    
	    final Composite c = new Composite(panel, SWT.NONE);
	    GridLayout layout = new GridLayout();
	    layout.numColumns=1;
	    c.setLayout(layout);

		
		List<String> items = LoadConfig.getInstance().getNameList();
		  
		buttons = new Button[items.size()];
	    for (int i = 0; i < items.size(); i++)
	    {
		    buttons[i] =new Button(c, SWT.RADIO);
		    buttons[i].setText(items.get(i));
		    buttons[i].setBounds(12, 13+40*i, 132, 40);
		    
		    if(!localMongoList.containsKey(items.get(i)))
		    {
		    	localMongoList.put(items.get(i), 0);
		    }
		    c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	    }
	    panel.setContent(c);
		
		return composite;
	}
	//--------------------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		( new MFilterDialog( null ) ).open();
	}
}
