package net.jumperz.app.MMonjaDB.eclipse.pref;

import net.jumperz.app.MMonjaDB.eclipse.*;
import net.jumperz.app.MMonjaDB.eclipse.view.MDocumentList;
import net.jumperz.app.MMonjaDBCore.MConstants;
import net.jumperz.app.MMonjaDBCore.MDataManager;
import net.jumperz.app.common.Commons;
import net.jumperz.app.common.PublicResource;
import net.jumperz.mongo.MMongoUtil;
import net.jumperz.util.MProperties;

import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
/**
 * 创建集合视图
 * 从资源文件读取本地数据库的数据集合的配置信息
 * 
 * @localhost @localPort
 * @datadb
 */

public class MCollectionPage extends Dialog implements MConstants
{
	private MProperties prop = MDataManager.getInstance().getProp();
	private  String tablesList[] = new String[2];
	private  Button[] buttons ;
	List documentDataList1 = new ArrayList();
	List columnNameList1 = new ArrayList();
	List documentDataList2 = new ArrayList();
	List columnNameList2 = new ArrayList();
	
	//--------------------------------------------------------------------------------
	public MCollectionPage( Shell parentShell )
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
		newShell.setText( "过滤集合项" );

		Image image = MUtil.getImage( newShell.getDisplay(), "server_lightning.png" );
		newShell.setImage( image );
	}
//--------------------------------------------------------------------------------
	protected void okPressed()
	{
		int j=0;
		for (int i = 0; i < buttons.length; i++) 
		{
		    if (buttons[i].getSelection())
		     {
		    	tablesList[j] = buttons[i].getText();
		    	j++;
		     }
		}		
		setReturnCode(OK);
		close();
		
		(new MColumnPage(null)).open();
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
	    c.setBounds(5, 5, 200, 100);
	    GridLayout layout = new GridLayout();
	    layout.numColumns=1;
	    c.setLayout(layout);

		
//		List<String> items = LoadConfig.getInstance().getNameList();
		  
	    Mongo mongo=null;
		try {
			mongo = new Mongo("localhost", Integer.parseInt(PublicResource.getString("localPort")));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	    DB db = mongo.getDB(PublicResource.getString("datadb"));
	    Set<String> items = db.getCollectionNames();
	    
		buttons = new Button[items.size()];
		
		Iterator<String> iter= items.iterator();
		int i=0;
		
		while (iter.hasNext()) 
	    {
		    buttons[i] =new Button(c, SWT.CHECK);
		    buttons[i].setText(iter.next());
		    buttons[i].setBounds(12, 13+40*i, 132, 40);		    
		    i++;
		    c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	    }
	    panel.setContent(c);
	    
		
		return composite;
	}
	//--------------------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		( new MCollectionPage( null ) ).open();
	}
	//--------------------------------------------------------------------------------
	/**
	 * 筛选列对话框
	 */
	private class MColumnPage extends Dialog implements MConstants
	{
		private Button buttonsOne[];
		private Button buttonsTwo[];
		private Text text;
		Mongo mongo=null;
		DB db;
		//--------------------------------------------------------------------------------
		public MColumnPage( Shell parentShell )
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
			newShell.setText( "过滤域项" );

			Image image = MUtil.getImage( newShell.getDisplay(), "server_lightning.png" );
			newShell.setImage( image );	
	
			try {
				mongo = new Mongo("localhost", Integer.parseInt(PublicResource.getString("localPort")));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MongoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		    db= mongo.getDB("test");
		}
	//--------------------------------------------------------------------------------
		protected void okPressed()
		{
			String conOne = null;
			String conTwo = null;

			if(text.getSelectionText() != "")
			{
				String con[] = text.getSelectionText().split(";");
				
				if(con.length ==2)
				{
					conOne = con[0].split(":")[1];
					conTwo = con[1].split(":")[1];
				}
				else if(con.length ==1)
				{
					if(con[0].split(":")[0].equals(tablesList[0]))
						conOne = con[0].split(":")[1];
					else
						conTwo = con[0].split(":")[1];;
				}
			}	

			BasicDBObject ob1 = new BasicDBObject();
			for(int i=0;i<buttonsOne.length;i++)
			{
				 if (buttonsOne[i].getSelection())
			     {
					
						ob1.put(buttonsOne[i].getText(),1);
			     }
			     
			}	
			
		    
			DBCollection collectionOne = db.getCollection(tablesList[0]);
			BasicDBObject condition1 = new BasicDBObject();
			if(conOne != null)
			{
				String cons[] = conOne.split(",");
				for(int i=0;i<cons.length;i++)
				{
					String item[] = cons[i].split(" ");
					System.out.println(item[0]+" "+item[1]+" "+item[2]);
					if(item[1].equals(">"))
						condition1.put(item[0], new BasicDBObject("$gt", Integer.parseInt(item[2])));
					else if(item[1].equals(">="))
						condition1.put(item[0], new BasicDBObject("$gte", Integer.parseInt(item[2])));
					else if(item[1].equals("<"))
						condition1.put(item[0], new BasicDBObject("$lt", Integer.parseInt(item[2])));
					else if(item[1].equals("<="))
						condition1.put(item[0], new BasicDBObject("$lte", Integer.parseInt(item[2])));				
					
				}
			}
			DBCursor cursor1 =  collectionOne.find(condition1,ob1);		
			
			BasicDBObject ob2 = new BasicDBObject();
			for(int i=0;i<buttonsTwo.length;i++)
			{
				 if (buttonsTwo[i].getSelection())
			     {
					 ob2.put(buttonsTwo[i].getText(),1);
			     }
			     
			}
			DBCollection collectionTwo = db.getCollection(tablesList[1]);
			BasicDBObject condition2 = new BasicDBObject();
			if(conTwo != null)
			{
				String cons[] = conTwo.split(",");
				for(int i=0;i<cons.length;i++)
				{
					String item[] = cons[i].split(" ");
					if(item[1].equals(">"))
						condition2.put(item[0], new BasicDBObject("$gt", Integer.parseInt(item[2])));
					else if(item[1].equals(">="))
						condition2.put(item[0], new BasicDBObject("$gte", Integer.parseInt(item[2])));
					else if(item[1].equals("<"))
						condition2.put(item[0], new BasicDBObject("$lt",Integer.parseInt(item[2])));
					else if(item[1].equals("<="))
						condition2.put(item[0], new BasicDBObject("$lte", Integer.parseInt(item[2])));				
					
				}
			}
			DBCursor cursor2 =  collectionTwo.find(condition2,ob2);
			
			while(cursor1.hasNext())
			{
				DBObject item1=cursor1.next();
				
				if(item1.get("id") != null)
				{			
				    String key = item1.get("id").toString();
				    DBCursor cursor2copy = cursor2.copy();//游标多次使用
					while(cursor2copy.hasNext())
					{
						DBObject item2=cursor2copy.next();
						if(item2.get("id") != null && item2.get("id").toString().equals(key))
						{
							documentDataList1.add(item1);
						    documentDataList2.add(item2);
						}
					}
				}
			}
			columnNameList1 = (ArrayList) MMongoUtil.getNameListFromDataList( documentDataList1 );
			columnNameList2 = (ArrayList) MMongoUtil.getNameListFromDataList( documentDataList2 );
			
			
//			try {
//				PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter(Commons.getInstance().getRunPath()+File.separator+"condition.txt")));
//				out.write(text.getText());
//				out.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
				
			
			setReturnCode(OK);
			close();
			
			if(documentDataList1.size() ==0 && documentDataList2.size()==0)
				JOptionPane.showMessageDialog(null, "合集为空！");

			else	
			{
				//(new MResultPage(null)).open();
				MDocumentList list = new MDocumentList();
				list.init2();
				documentDataList1.addAll(documentDataList2);
				columnNameList1.addAll(columnNameList2);
				list.drawTable(documentDataList1,columnNameList1 , null);
			}
		}
	//--------------------------------------------------------------------------------
		protected Control createDialogArea( Composite parent )
		{
			Composite composite = (Composite)super.createDialogArea( parent );
		    ScrolledComposite panel = new  ScrolledComposite(composite, SWT.BORDER | SWT.V_SCROLL);   
		    panel.setBounds(5, 5, 250, 1000);
		    panel.setLayoutData(new  GridData(GridData.FILL_VERTICAL));   
		    //强制显示滚动条    
		    panel.setAlwaysShowScrollBars(true );   
		    panel.setExpandHorizontal(true );   
		    panel.setExpandVertical(true );   
		    // 拖动滚动条里可以看到的Composite的最大高度    
		    panel.setMinHeight(800 );
		    panel.setMinWidth(220);
		       
//		    panel.setLayout(new  GridLayout( 1 ,  false )); 
		
		    final Composite c = new Composite(panel, SWT.NONE);
		    GridLayout layout = new GridLayout();
		    layout.numColumns=1;
		    c.setLayout(layout);
		    		
		    DBCollection collectionOne = db.getCollection(tablesList[0]);
		    String arrOne[]=collectionOne.findOne().toString().split(",");
		    
		    buttonsOne = new Button[arrOne.length-1];

		    Label labelOne = new Label(c,SWT.BUTTON1);
		    labelOne.setText(tablesList[0]+":");

		    for(int j=1;j<arrOne.length;j++)
		    {
			    String item[] = arrOne[j].split("\"");
			    
			    buttonsOne[j-1] =new Button(c, SWT.CHECK);
			    buttonsOne[j-1].setText(item[1]);			    
		    }
		    
		    Label seperate1 = new Label(c, SWT.SEPARATOR|SWT.HORIZONTAL);
		    
		    Label labelTwo = new Label(c,SWT.BUTTON1);
		    labelTwo.setText(tablesList[1]+":");
		    DBCollection collectionTwo = db.getCollection(tablesList[1]);

		    String arrTwo[]= collectionTwo.findOne().toString().split(",");
		    
		    buttonsTwo = new Button[arrTwo.length-1];

		    for(int j=1;j<arrTwo.length;j++)
		    {
			    String item[] = arrTwo[j].split("\"");
			    
			    buttonsTwo[j-1] =new Button(c, SWT.CHECK);
			    buttonsTwo[j-1].setText(item[1]);			    
		    }
		    
		    
		    Label seperate2 = new Label(c, SWT.SEPARATOR|SWT.HORIZONTAL);
		    
			c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			panel.setContent(c);
			
			Composite rowComposite=new Composite(composite,SWT.NONE);
			rowComposite.setBounds(5, 870, 250, 200);
			Label select = new Label(rowComposite,SWT.BUTTON1);
			select.setBounds(2, 2, 50, 20);
		    select.setText("筛选条件:");
	        text=new Text(rowComposite, SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
	        text.setBounds(2, 25, 240, 80);
	        
//	        try {
//	        	BufferedReader in = new BufferedReader(new FileReader(Commons.getInstance().getRunPath()+File.separator+"condition.txt"));
//	        	String s;
//	        	StringBuilder str=new StringBuilder();
//	        	while((s=in.readLine())!=null)
//				{  
//					str.append(s+"\n");  
//				}
//	        	in.close();
//				text.setText(str.toString());
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		 		
			return composite;
		}
	}
	/**
	 * 合集结果对话框
	 * @author Administrator
	 *
	 */
	private class MResultPage extends Dialog implements MConstants
	{
		//--------------------------------------------------------------------------------
		public MResultPage( Shell parentShell )
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
			newShell.setText( "合集" );

			Image image = MUtil.getImage( newShell.getDisplay(), "server_lightning.png" );
			newShell.setImage( image );
		}
	//--------------------------------------------------------------------------------
		protected void okPressed()
		{
			setReturnCode(OK);
			close();
		}
	//--------------------------------------------------------------------------------
		protected Control createDialogArea( Composite parent )
		{
		  Composite s = (Composite)super.createDialogArea( parent );
			
		  s.setSize(250,200); 
		  GridLayout g1=new GridLayout(); 
		  int num1 = columnNameList1.size();
		  int num2 = columnNameList2.size();
		  
		  g1.numColumns = num1 + num2-1; 
		  s.setLayout(g1); 
		  //create a table 
		  final Table t=new Table(s,SWT.BORDER); 
		  //create table header 
		  final GridData gd=new GridData(GridData.FILL_BOTH); 
		  gd.horizontalSpan = num1 + num2-1; 
		  t.setLayoutData(gd); 
		  
		  int i=0;
		  for(;i<num1;i++)
		  {
			  TableColumn tc=new TableColumn(t,SWT.CENTER); 
			  tc.setText(columnNameList1.get(i).toString()); 
			  tc.setWidth(75+5*i); 
		  }
		  for(int j=1;j<num2;j++)
		  {
			  TableColumn tc=new TableColumn(t,SWT.CENTER); 
			  tc.setText(columnNameList2.get(j).toString()); 
			  tc.setWidth(75+5*i+5+5*j); 
		  }
		  t.setHeaderVisible(true); 
		  t.setLinesVisible(true); 
		  
		  for( int m = 0; m < documentDataList1.size(); ++m )
			{
			  	BasicDBObject documentData1 = ( BasicDBObject )documentDataList1.get( m );
				BasicDBObject documentData2 = ( BasicDBObject )documentDataList2.get( m );
			
			  	String item[] = new String[columnNameList1.size()+columnNameList2.size()];
				
			  	int k=0;//类关键字显示
			  	int r=0;
			  	for(; k < columnNameList1.size(); ++k )
					{
						String columnName = ( String )columnNameList1.get( k );
						item[r++] = documentData1.get( columnName ).toString();
					}
			  	for( int n = 1;n < columnNameList2.size(); ++n )//类关键字不重复显示
				{
					String columnName = ( String )columnNameList2.get( n );
					item[r++] = documentData2.get( columnName ).toString();
				}
				 final TableItem item1=new TableItem(t,SWT.NONE); 
				 item1.setText(item); 
			}
			
		 return s;
		}
	}
}