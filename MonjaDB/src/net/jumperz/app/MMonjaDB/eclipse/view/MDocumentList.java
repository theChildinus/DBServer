package net.jumperz.app.MMonjaDB.eclipse.view;

import org.bson.types.BSONTimestamp;
import org.bson.types.CodeWScope;
import org.bson.types.MaxKey;
import org.bson.types.MinKey;
import org.bson.types.ObjectId;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogMessageArea;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Composite;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.text.Collator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import net.jumperz.util.*;

import java.awt.Color;
import java.io.*;

import net.jumperz.gui.*;
import net.jumperz.mongo.MFindQuery;
import net.jumperz.mongo.MMongoUtil;

import net.jumperz.app.MMonjaDB.eclipse.MUtil;
import net.jumperz.app.MMonjaDB.eclipse.dialog.*;
import net.jumperz.app.MMonjaDBCore.MConstants;
import net.jumperz.app.MMonjaDBCore.MDataManager;
import net.jumperz.app.MMonjaDBCore.MOutputView;
import net.jumperz.app.MMonjaDBCore.action.*;
import net.jumperz.app.MMonjaDBCore.action.mj.MEditAction;
import net.jumperz.app.MMonjaDBCore.action.mj.MShowAllDbStatsAction;
import net.jumperz.app.MMonjaDBCore.event.*;
import net.jumperz.app.common.LoadConfig;
import net.jumperz.app.common.PublicResource;
import net.jumperz.app.common.RefreshThread;
import net.jumperz.app.common.SysConfig;
import net.jumperz.app.state.SystemState;

import java.util.*;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import java.util.regex.*;

import javax.swing.JOptionPane;
import javax.swing.text.TableView;

public class MDocumentList
extends MAbstractView
implements MOutputView
{
private Table table;
private TableViewer tableViewer;
private Image image;
private String dbName;
private String collName;

private Action copyAction;
private Action pasteAction;
private Action editDocumentAction;
private Action editFieldAction;
private Action copyFieldAction;
private Action copyAsJsonAction;
private Action copyAsStringAction;
private Action removeAction;
private Action insertBlankAction;
private Action insertJsonAction;
private Action reloadAction;
private Action forwardAction;
private Action backAction;
private Action prevItemsAction;
private Action nextItemsAction;
private Combo historyCombo;
private Combo grepCombo;
private Button refresh;
private Button prevItemsButton;
private Button nextItemsButton;
private Label naviLabel;
private int mouseUpX;
private int mouseUpY;
private Set grepStrSet = new LinkedHashSet();

private List<String> columnNameList=new ArrayList<String>();
private String optionalItems;
private Text text;
private static java.util.List tmpDocumentDataList = new ArrayList();
private boolean clear = true;

private java.util.List _columns;
private java.util.List _datas;
private static String preDbName=null;
private static String preCollName=null;
private static boolean first=true;
//--------------------------------------------------------------------------------
public MDocumentList()
{
MEventManager.getInstance().register2( this );
}
//--------------------------------------------------------------------------------
public void dispose()
{
eventManager.removeObserver2( this );
super.dispose();
}
//--------------------------------------------------------------------------------
private void showPrevItems()
{
executeAction( "mj prev items" );
}
//--------------------------------------------------------------------------------
private void showNextItems()
{
executeAction( "mj next items" );
}
//--------------------------------------------------------------------------------
private void copyField()
{
if( mouseUpX == -1 || mouseUpY == -1 )
	{
	return;
	}

Point point = new Point( mouseUpX, mouseUpY );
final TableItem item = tableViewer.getTable().getItem( point );
if( item == null )
	{
	return;
	}
int columnIndex = -1;
for( int i = 0; i < tableViewer.getTable().getColumnCount(); ++i )
	{
	if( item.getBounds( i ).contains( point ) )
		{
		columnIndex = i;
		break;
		}
	}
if( columnIndex == -1 ) //ObjectId
	{
	return;
	}

TableItem[] items = tableViewer.getTable().getSelection();
if( items != null )
	{
	StringBuffer buf = new StringBuffer();
	for( int i = 0; i < items.length; ++i )
		{
		if( i > 0 )
			{
			buf.append( "\n" );
			}
		buf.append( items[ i ].getText( columnIndex ) );
		}
	MSwtUtil.copyToClipboard( buf.toString() );
	}
}
//--------------------------------------------------------------------------------
private void copyAsJson()
{
TableItem[] items = tableViewer.getTable().getSelection();
if( items == null || items.length == 0 )
	{
	return;
	}
StringBuffer buf = new StringBuffer();
for( int i = 0; i < items.length; ++i )
	{
	BasicDBObject documentData = ( BasicDBObject )items[ i ].getData();
	if( i > 0 )
		{
		buf.append( ",\n" );
		}
	buf.append( MMongoUtil.toJson( dataManager.getDB(), documentData ) );
	}
MSwtUtil.copyToClipboard( buf.toString() );
}
//--------------------------------------------------------------------------------
private void copyAsString()
{
TableItem[] items = tableViewer.getTable().getSelection();
if( items == null || items.length == 0 )
	{
	return;
	}
StringBuffer buf = new StringBuffer();
for( int i = 0; i < items.length; ++i )
	{
	if( i > 0 )
		{
		buf.append( "\n" );
		}
	for( int k = 0; k < tableViewer.getTable().getColumnCount(); ++k )
		{
		if( k > 0 )
			{
			buf.append( " " );
			}
		String value = items[ i ].getText( k );
		buf.append( value );
		}
	}
MSwtUtil.copyToClipboard( buf.toString() );
}
//--------------------------------------------------------------------------------
private void editField()
{
if( mouseUpX > -1 && mouseUpY > -1 )
	{
	onTableDoubleClick( mouseUpX, mouseUpY );
	}
}
//--------------------------------------------------------------------------------
private void copy()
{
executeAction( "mj copy" );
}
//--------------------------------------------------------------------------------
private void onCopy()
{
shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//-----

TableItem[] items = tableViewer.getTable().getSelection();
if( items == null || items.length == 0 )
	{
	return;
	}

List copiedDocumentList = new ArrayList();
for( int i = 0; i < items.length; ++i )
	{
	copiedDocumentList.add( items[ i ].getData() );
	}
debug( copiedDocumentList );
dataManager.setCopiedDocumentList( copiedDocumentList );
dataManager.setCopiedCollName( collName );

}});//----
}
//--------------------------------------------------------------------------------
private void paste()
{
executeAction( "mj paste" );
}
//--------------------------------------------------------------------------------
private void remove()
{
MessageBox dialog = new MessageBox( shell, SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
dialog.setText(PublicResource.getString("Confirm Remove"));
dialog.setMessage(PublicResource.getString("Do you really want to remove?"));
int returnCode = dialog.open();
if( returnCode == SWT.OK )
	{
	executeAction( "mj remove" );

	}
}
//--------------------------------------------------------------------------------
private void onRemove()
{
shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//-----

TableItem[] items = tableViewer.getTable().getSelection();
if( items == null || items.length == 0 )
	{
	return;
	}
for( int i = 0; i < items.length; ++i )
	{
	BasicDBObject data = ( BasicDBObject )items[ i ].getData();
	tableViewer.remove(data);//添加
	BasicDBObject removeCond = new BasicDBObject();
	removeCond.put( "_id", data.get( "_id" ) );
	String objectStr = MMongoUtil.toJson( dataManager.getDB(), removeCond, true );
	executeAction( "db." + collName + ".remove(" + objectStr + ")" );
	}

//StructuredSelection selection = (StructuredSelection)tableViewer.getSelection();
//BasicDBObject item=(BasicDBObject)selection.getFirstElement();
//
//tableViewer.remove(item);

}});//----
}
//--------------------------------------------------------------------------------
private void onPaste()
{
List copiedDocumentList = dataManager.getCopiedDocumentList();
DBCollection coll = dataManager.getDB().getCollection( collName );

for( int i = 0; i < copiedDocumentList.size(); ++i )
	{
	BasicDBObject data = ( BasicDBObject )copiedDocumentList.get( i );
	if( dataManager.getCopiedCollName().equals( collName ) )
		{
		data.remove( "_id" );
		}
	coll.insert( data, WriteConcern.SAFE );
	}
	dataManager.reloadDocument();
}
//--------------------------------------------------------------------------------
private void insertJsonDocument()
{
Map dialogData = new HashMap();
MInsertJsonDialog dialog = new MInsertJsonDialog( shell, dialogData );
int result = dialog.open();

if( dialogData.containsKey( "json" ) )
	{
	String jsonStr = ( String )dialogData.get( "json" );
	BasicDBList list = MMongoUtil.parseJsonToArray( dataManager.getDB(), jsonStr );
	for( int i = 0; i < list.size(); ++i )
		{
		executeAction( "db." + collName + ".insert(" + MMongoUtil.toJson( dataManager.getDB(), list.get( i ), true ) + ")" );
		}
	reload();
	}
}
//--------------------------------------------------------------------------------
private void insertBlankDocument()//需要确定关键字显示
{
	executeAction( "db." + collName + ".insert({})" );
	reload();
}
class TableContentProvider implements IStructuredContentProvider{

	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return ((List)inputElement).toArray();
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}
}
class TableLableProvider implements ITableLabelProvider{

	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub

			BasicDBObject documentData = (BasicDBObject)element;
			
			if(columnIndex < _columns.size())
			{
				String columnName = ( String )_columns.get( columnIndex );
				Object value = documentData.get( columnName );
				if(value == null)
					return null;
				return value.toString();
			}		
		return null;
	}

	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

}
//--------------------------------------------------------------------------------
public void init2()
{
parent.setLayout( new FormLayout() );

tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
tableViewer.getTable().addMouseListener(new MouseAdapter() {
@Override
public void mouseDown(MouseEvent e) {
onMouseDown( e );
}
});
tableViewer.getTable().setHeaderVisible( true );
tableViewer.getTable().setLinesVisible( true );
FormData d1 = new FormData();
d1.top = new FormAttachment(0, 32);
d1.left = new FormAttachment( 0, 1 );
d1.right = new FormAttachment( 100, -1 );
d1.bottom = new FormAttachment( 100, -1 );
tableViewer.getTable().setLayoutData( d1 );
tableViewer.setContentProvider(new TableContentProvider());
tableViewer.setLabelProvider(new TableLableProvider());


menuManager = new MenuManager();
Menu contextMenu = menuManager.createContextMenu( tableViewer.getTable() );
tableViewer.getTable().setMenu( contextMenu );
historyCombo = new Combo(parent, SWT.NONE);
historyCombo.setToolTipText(PublicResource.getString("MDocumentHistoryCombo"));
historyCombo.addKeyListener(new KeyAdapter() {
	public void keyPressed(KeyEvent e) {
	//debug( e );
	if( e.keyCode == 13 )
		{
		String text = historyCombo.getText();
		if( text != null && text.length() > 0 )
			{
			executeAction( text );		
			}
		}
	}
});
FormData fd_combo = new FormData();
fd_combo.bottom = new FormAttachment(tableViewer.getTable(), -3);
fd_combo.left = new FormAttachment(0, 4);
fd_combo.right = new FormAttachment(30, 0);
historyCombo.setLayoutData(fd_combo);

	//listeners
tableViewer.getTable().addListener( SWT.MouseDoubleClick, this );
tableViewer.getTable().addListener( SWT.Selection, this );
tableViewer.getTable().addListener( SWT.KeyDown, this );

image = MUtil.getImage( parent.getShell().getDisplay(), "table.png" );

final MDocumentList documentList = this;

{	//editDocumentAction
editDocumentAction = new Action(){ public void run() { //--------
editDocument();
}};//-----
editDocumentAction.setText( PublicResource.getString("MDocumentEditDocument") );
setActionImage( editDocumentAction, "table_edit.png" );
menuManager.add( editDocumentAction );
}

{	//editFieldAction
editFieldAction = new Action(){ public void run() { //--------
editField();
}};//-----
editFieldAction.setText( PublicResource.getString("MDocumentEditField") );
setActionImage( editFieldAction, "page_edit.png" );
menuManager.add( editFieldAction );
}

menuManager.add( new Separator() );

{	//copyAction
copyAction = new Action(){ public void run() { //--------
copy();
}};//-----
copyAction.setText( PublicResource.getString("MDocumentCopy")+"\tCtrl+C" );
setActionImage( copyAction, "page_copy.png" );
menuManager.add( copyAction );
}

{	//pasteAction
pasteAction = new Action(){ public void run() { //--------
paste();
}};//-----
pasteAction.setText( PublicResource.getString("MDocumentPaste")+"\tCtrl+V" );
setActionImage( pasteAction, "page_white_paste_table.png" );
menuManager.add( pasteAction );
}

{	//insertBlankAction
insertBlankAction = new Action(){ public void run() { //--------
insertBlankDocument();
}};//-----
insertBlankAction.setText( PublicResource.getString("MDocumentInsertBlankDocument") );
setActionImage( insertBlankAction, "table_add.png" );
//menuManager.add( insertBlankAction );//删除此处
}

{	//insertJsonAction
insertJsonAction = new Action(){ public void run() { //--------
insertJsonDocument();
}};//-----
insertJsonAction.setText( PublicResource.getString("MDocumentInsertJSON") );
setActionImage( insertJsonAction, "table_add.png" );
menuManager.add( insertJsonAction );
}

menuManager.add( new Separator() );

{
copyFieldAction =  new Action(){ public void run() { //--------
copyField();
}};//-----
copyFieldAction.setText( PublicResource.getString("MDocumentCopyFields") );
menuManager.add( copyFieldAction );
}

{
copyAsJsonAction =  new Action(){ public void run() { //--------
copyAsJson();
}};//-----
copyAsJsonAction.setText( PublicResource.getString("MDocumentCopyDocumentAsJSON") );
menuManager.add( copyAsJsonAction );
}

{
copyAsStringAction =  new Action(){ public void run() { //--------
copyAsString();
}};//-----
copyAsStringAction.setText( PublicResource.getString("MDocumentCopyDocumentAsString") );
menuManager.add( copyAsStringAction );
}

menuManager.add( new Separator() );

{	//removeAction
removeAction = new Action(){ public void run() { //--------
remove();
}};//-----
removeAction.setText( PublicResource.getString("MDocumentDelete")+"\tDEL" );
setActionImage( removeAction, "table_delete.png" );
menuManager.add( removeAction );
}

menuManager.add( new Separator() );

{	//reloadAction
reloadAction = new Action(){ public void run() { //--------
reload();
}};//-----
reloadAction.setText(PublicResource.getString("MDocumentReloadDocument")+"\tF5/Ctrl+R");
reloadAction.setToolTipText(PublicResource.getString("MDocumentReloadDocument"));
initAction( reloadAction, "table_refresh.png", menuManager );
}

menuManager.add( new Separator() );

{	//backAction
backAction = new Action(){ public void run() { //--------
MHistory findHistory = dataManager.getFindHistory();
if( !findHistory.atBegin() )
	{
	findHistory.back();
	documentList.executeAction( findHistory.current() + "" );
	}
}};//-----
backAction.setText( PublicResource.getString("MDocumentPreviousQuery") );
backAction.setToolTipText( PublicResource.getString("MDocumentPreviousQuery") );
initAction( backAction, "bullet_left.png", menuManager );
}

{	//forwardAction
forwardAction = new Action(){ public void run() { //--------
MHistory findHistory = dataManager.getFindHistory();
if( !findHistory.atEnd() )
	{
	findHistory.forward();
	documentList.executeAction( findHistory.current() + "" );
	}
}};//-----
forwardAction.setText( PublicResource.getString("MDocumentNextQuery") );
forwardAction.setToolTipText( PublicResource.getString("MDocumentNextQuery") );
initAction( forwardAction, "bullet_right.png", menuManager );
}

menuManager.add( new Separator() );

{	//prevItemsAction
prevItemsAction = new Action(){ public void run() { //--------
showPrevItems();
}};//-----
prevItemsAction.setText( PublicResource.getString("MDocumentPageBack") );
prevItemsAction.setToolTipText( PublicResource.getString("MDocumentPageBack") );
setActionImage( prevItemsAction, "page_back.png" );
addActionToDropDownMenu( prevItemsAction );
menuManager.add( prevItemsAction );
}

{	//prevItemsButton
prevItemsButton = new Button( parent, SWT.FLAT);
prevItemsButton.addSelectionListener(new SelectionAdapter() {
	public void widgetSelected(SelectionEvent e) {
	showPrevItems();
	}
});
FormData fd_btnNewButton = new FormData();
fd_btnNewButton.right = new FormAttachment(tableViewer.getTable(), -40, SWT.RIGHT );
prevItemsButton.setLayoutData(fd_btnNewButton);
Image image = MUtil.getImage( parent.getShell().getDisplay(), "page_back.png" );
prevItemsButton.setImage( image );
}

{	//nextItemsAction
nextItemsAction = new Action(){ public void run() { //--------
showNextItems();
}};//-----
nextItemsAction.setText( PublicResource.getString("MDocumentPageForward") );
nextItemsAction.setToolTipText( PublicResource.getString("MDocumentPageForward") );
setActionImage( nextItemsAction, "page_forward.png" );
addActionToDropDownMenu( nextItemsAction );
menuManager.add( nextItemsAction );
}

{	//nextItemsButton
nextItemsButton = new Button( parent, SWT.FLAT );
nextItemsButton.addSelectionListener(new SelectionAdapter() {
	public void widgetSelected(SelectionEvent e) {
	showNextItems();
	}
});
FormData fd_btnNewButton = new FormData();
fd_btnNewButton.right = new FormAttachment(tableViewer.getTable(), -10, SWT.RIGHT );
nextItemsButton.setLayoutData(fd_btnNewButton);
Image image = MUtil.getImage( parent.getShell().getDisplay(), "page_forward.png" );
nextItemsButton.setImage( image );
}

naviLabel = new Label(parent, SWT.NONE);
FormData fd_naviLabel = new FormData();
fd_naviLabel.top = new FormAttachment(historyCombo, 6, SWT.TOP);
//fd_naviLabel.left = new FormAttachment(prevItemsButton, -66, SWT.LEFT);
fd_naviLabel.right = new FormAttachment(prevItemsButton, -6, SWT.LEFT);
naviLabel.setLayoutData(fd_naviLabel);
naviLabel.setText("");

grepCombo = new Combo(parent, SWT.NONE);
grepCombo.setToolTipText(PublicResource.getString("MDocumentGrep"));
FormData fd_grepCombo = new FormData();
fd_grepCombo.right = new FormAttachment(50);
fd_grepCombo.bottom = new FormAttachment(tableViewer.getTable(), -3);
fd_grepCombo.left = new FormAttachment(30, 6);
grepCombo.setLayoutData(fd_grepCombo);
grepCombo.addKeyListener(new KeyAdapter() {
	public void keyPressed(KeyEvent e) {
	if( e.keyCode == 13 )
		{
		grep();
		}
	}
});

refresh = new Button(parent,SWT.CHECK);
refresh.setToolTipText("每隔一分钟刷新数据库");
refresh.setSelection(true);
FormData fd_refresh = new FormData();
fd_refresh.right = new FormAttachment(60);
fd_refresh.bottom = new FormAttachment(tableViewer.getTable(), -3);
fd_refresh.left = new FormAttachment(50, 6);
refresh.setLayoutData(fd_refresh);
refresh.addMouseListener( new MouseAdapter() {
	public void mouseUp( final MouseEvent e) 
	{
		if(refresh.getSelection())
			SystemState.isRefresh=true;
		else
			SystemState.isRefresh = false;
	}
	});

initActionsAndButtons();

}
//--------------------------------------------------------------------------------
private void grep()
{
try
	{
	String grepStr = grepCombo.getText();
	Pattern grepPattern = Pattern.compile( grepStr, Pattern.CASE_INSENSITIVE );
	drawTable( dataManager.getDocumentDataList(), dataManager.getColumnNameList(), grepPattern );
	
	if( !grepStrSet.contains( grepStr ) )
		{
		grepStrSet.add( grepStr );
		grepCombo.removeAll();
		Iterator p = grepStrSet.iterator();
		while( p.hasNext() )
			{
			grepCombo.add( ( String )p.next() );
			}
		grepCombo.setText( grepStr );
		}
	}
catch( Exception e )
	{
		//ok
	}
}
//--------------------------------------------------------------------------------
private void reload()
{
dataManager.reloadDocument();
}
//--------------------------------------------------------------------------------
private void editDocument()
{
TableItem[] items = tableViewer.getTable().getSelection();
if( items.length == 1 )
	{
	try
		{
		MDocumentEditor documentEditor = null;
		IWorkbenchPage[] pages = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages();
		for( int i = 0; i < pages.length; ++i )
			{
			IViewReference[] views = pages[ i ].getViewReferences();
			for( int k = 0; k < views.length; ++k )
				{
				String viewId = views[ k ].getId();
				if( viewId.equals( MDocumentEditor.class.getName() ) )
					{
					documentEditor = ( MDocumentEditor )views[ k ].getView( true );
					pages[ i ].showView( viewId );
					break;
					}
				}
			}
		
		if( documentEditor == null )
			{
			documentEditor = ( MDocumentEditor )PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView( MDocumentEditor.class.getName() );		
			}
		
		int selectedIndex = tableViewer.getTable().getSelectionIndex();
		String objectIdStr = items[ 0 ].getText( 0 );
		executeAction( "mj edit " + objectIdStr );
		documentEditor.setFocus();
		documentEditor.activate();
		}
	catch( Exception e )
		{
		MEventManager.getInstance().fireErrorEvent( e );
		return;
		}
	}
}
//--------------------------------------------------------------------------------
public void setFocus()
{
}
//--------------------------------------------------------------------------------
private void onTableSelect()
{
if( !isActive() )
	{
	return;
	}

	//system.indexes is ignored
if( dataManager.getCollName().equals( "system.indexes" ) )
	{
	return;
	}

TableItem[] items = tableViewer.getTable().getSelection();
if( items.length == 1 )
	{
	int selectedIndex = tableViewer.getTable().getSelectionIndex();
	String objectIdStr = items[ 0 ].getText( 0 );
	executeAction( "mj edit " + objectIdStr );
	}
updateGui();
}
//--------------------------------------------------------------------------------
private void updateGui()
{
TableItem[] items = tableViewer.getTable().getSelection();
boolean oneItem = false;
boolean multiItem = false;
if( items.length == 1 )
	{
	oneItem = true;
	}
else if( items.length > 1 )
	{
	multiItem = true;
	}

editDocumentAction.setEnabled( oneItem );
copyAsJsonAction.setEnabled( multiItem || oneItem );
copyAsStringAction.setEnabled( multiItem || oneItem );
copyAction.setEnabled( multiItem || oneItem );
pasteAction.setEnabled( dataManager.getCopiedDocumentList().size() > 0 );
removeAction.setEnabled( multiItem || oneItem );

boolean collSelected = false;
if( dataManager.isConnected() && dataManager.getCollName() != null && dataManager.getCollName().length() > 0 )
	{
	collSelected = true;
	}
insertBlankAction.setEnabled( collSelected );
insertJsonAction.setEnabled( collSelected );
}
//--------------------------------------------------------------------------------
private void updateDocument( TableItem item, int columnIndex, Class clazz, String value )
{
//String objectIdStr = item.getText( 0 );
DBObject currentData = ( DBObject )item.getData();
//原版
String fieldName = tableViewer.getTable().getColumn( columnIndex ).getText();
//String fieldName = SysConfig.Instance().getColEngName(table.getColumn( columnIndex ).getText());

Object newValue = MMongoUtil.getValueByCurrentType( value, clazz );

Object id = currentData.get( "_id" );
dataManager.updateDocument( id, fieldName, newValue );
/*
BasicDBObject query = new BasicDBObject( "_id", new ObjectId( objectIdStr ) );
BasicDBObject update = new BasicDBObject( "$set", new BasicDBObject( fieldName, newValue ) );

String updateStr = null;
if( newValue.getClass() == Integer.class )
	{
	updateStr = "{ $set : { \"" + fieldName + "\" : Number} }";
	}
else
	{
	updateStr = MMongoUtil.toJson( dataManager.getDB(), update );
	}

actionManager.executeAction( "db." + collName + ".update(" +
	MMongoUtil.toJson( dataManager.getDB(), query ) + "," +
	updateStr +
	",false, false )" );
*/

}
//--------------------------------------------------------------------------------
private Class getCurrentClass( TableItem item, int columnIndex )
{
DBObject currentData = ( DBObject )item.getData();

String fieldName = tableViewer.getTable().getColumn( columnIndex ).getText();
Class clazz = null;
if( currentData.containsField( fieldName ) )
	{
	Object currentValue = currentData.get( fieldName );
	if( currentValue != null )
		{
		clazz = currentValue.getClass();	
		}
	}
return clazz;
}
//--------------------------------------------------------------------------------
private void onMouseDown( final MouseEvent event )
{
boolean fieldSelected = false;
Point point = new Point( event.x, event.y );
final TableItem item = tableViewer.getTable().getItem( point );
if( item == null )
	{
	}
else
	{
	int column = -1;
	for( int i = 0; i < tableViewer.getTable().getColumnCount(); ++i )
		{
		if( item.getBounds( i ).contains( point ) )
			{
			column = i;
			break;
			}
		}
	
	if( column == -1 )
		{
		mouseUpX = -1;
		mouseUpY = -1;
		}
	else
		{
		if( tableViewer.getTable().getSelection().length > 0 )
			{
			fieldSelected = true;
			}
		mouseUpX = event.x;
		mouseUpY = event.y;
		
		String columnName = tableViewer.getTable().getColumns()[ column ].getText();
		copyFieldAction.setText( "Copy Field ('" + columnName + "') Value" );
		}
	}

editFieldAction.setEnabled( fieldSelected );
copyFieldAction.setEnabled( fieldSelected );

updateGui();
}
//--------------------------------------------------------------------------------
private void onTableDoubleClick( final Event event )
{
onTableDoubleClick( event.x, event.y );
}
//--------------------------------------------------------------------------------
private void onTableDoubleClick( final int x, final int y )
{
	//system.indexes is ignored
if( dataManager.getCollName().equals( "system.indexes" ) )
	{
	return;
	}
final TableEditor editor = new TableEditor( tableViewer.getTable() );

shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//*****

Point point = new Point( x, y );
final TableItem item = tableViewer.getTable().getItem( point );
if( item == null )
	{
	return;
	}
int columnIndex = -1;
for( int i = 0; i < tableViewer.getTable().getColumnCount(); ++i )
	{
	if( item.getBounds( i ).contains( point ) )
		{
		columnIndex = i;
		break;
		}
	}
if( columnIndex == -1  ) //ObjectId
	{
	return;
	}

final Class clazz = getCurrentClass( item, columnIndex );
if( clazz == CodeWScope.class
 || clazz == BSONTimestamp.class
 || clazz == byte[].class
 || clazz == MinKey.class
 || clazz == MaxKey.class
  )
	{
	return;
	}
text =new Text( tableViewer.getTable(), SWT.NONE | SWT.BORDER );
String coll = SysConfig.Instance().getColEngName(tableViewer.getTable().getColumn(columnIndex).getText());
if(SysConfig.Instance().getColIsoptional(coll) == 1)
{
	optionalItems = SysConfig.Instance().getColOptionalItems(coll);
	shell.getDisplay().syncExec( new Runnable(){ public void run()	{//*****
		
		(new RadioDialog(null)).open();
		}});
}
else if(SysConfig.Instance().getColIsoptional(coll) == 2)
{
	optionalItems = SysConfig.Instance().getColOptionalItems(coll);
	shell.getDisplay().syncExec( new Runnable(){ public void run()	{//*****
		
		(new OptionalDialog(null)).open();
		}});
}
else
{
//原版
//final Text text = new Text( tableViewer.getTable(), SWT.NONE | SWT.BORDER );
text.setText( item.getText( columnIndex ) );
}
text.selectAll();

editor.horizontalAlignment = SWT.LEFT;
editor.grabHorizontal = true;
editor.setEditor( text, item, columnIndex );

final int selectedColumn = columnIndex;
Listener textListener = new Listener(){
public void handleEvent( final Event e )
{
switch( e.type )
	{
	case SWT.FocusOut :
		updateDocument( item, selectedColumn, clazz, text.getText() );
		text.dispose();
		break;
	case SWT.Traverse :
		switch( e.detail )
			{
			case SWT.TRAVERSE_RETURN :
				//item.setText( selectedColumn, text.getText() );
				updateDocument( item, selectedColumn, clazz, text.getText() );
				//break;
			case SWT.TRAVERSE_ESCAPE :
				text.dispose();
				e.doit = false;
				//break;
			}
		break;
	}
}
};

text.addListener( SWT.FocusOut, textListener );
text.addListener( SWT.Traverse, textListener );
//text.selectAll();
text.setFocus();

}});//*****
}
//--------------------------------------------------------------------------------
private void onTableKeyDown( Event e )
{
if( ( ( e.stateMask & SWT.CTRL ) == SWT.CTRL ) )
	{
	if( e.keyCode == 'c' )
		{
		copy();
		e.doit = false;
		}
	else if( e.keyCode == 'v' )
		{
		paste();
		e.doit = false;
		}
	else if( e.keyCode == 'r' )
		{
		reload();
		e.doit = false;
		}
	}
else
	{
	if( e.keyCode == 127 ) //delete
		{
		remove();
		e.doit = false;
		}
	else if( e.keyCode == SWT.F5 )
		{
		reload();
		e.doit = false;
		}
	}
}
// --------------------------------------------------------------------------------
protected void handleEvent2( Event event )
{
if( event.widget == tableViewer.getTable() )
	{
	switch( event.type )
		{
		case SWT.Selection:
			onTableSelect();
			break;
		case SWT.MouseDoubleClick:
			onTableDoubleClick( event );
			break;
		case SWT.KeyDown:
			onTableKeyDown( event );
			break;
		}
	}
else if( MSwtUtil.getTableColumns( tableViewer.getTable() ).contains( event.widget ) )
	{
	switch( event.type )
		{
		case SWT.Selection:
			onTableColumnSelect( ( TableColumn )event.widget );
			break;
		case SWT.Resize:
			onTableColumnResize();
			break;
		}
	}
}
//--------------------------------------------------------------------------------
private void onTableColumnSelect( TableColumn column )
{
	final String columnName = column.getText();
	first=false;
	executeAction( "mj sort by " + columnName + " " + dataManager.getSortOrder() );
/*
	//sort order
if( sortOrder == 1 )
	{
	sortOrder = -1;
	}
else
	{
	sortOrder = 1;
	}

final int _sortOrder = sortOrder;

drawTable( dataManager.getDocumentDataList(), dataManager.getColumnNameList() );
*/
}
// --------------------------------------------------------------------------------
private void onTableColumnResize()
{
MSwtUtil.setTableColumnWidthToProperties( getTablePrefix(), tableViewer.getTable(), prop );
}
//--------------------------------------------------------------------------------
private void clearTableSwt()
{
	//reset table
TableColumn[] columns = tableViewer.getTable().getColumns();
for( int i = 0; i < columns.length;  ++i )
	{
	columns[ i ].dispose();
	}
tableViewer.getTable().removeAll();
}
//--------------------------------------------------------------------------------
private String getTablePrefix()
{
return DOCUMENTLIST_TABLE + "_" + dbName + "_" + collName;
}
//--------------------------------------------------------------------------------
private void drawTable( final java.util.List _dataList, final java.util.List _columnNameList )
{
drawTable( _dataList, _columnNameList, null );
}
//--------------------------------------------------------------------------------
void clearTable()
{
	TableColumn[] columns = tableViewer.getTable().getColumns();
	for( int i = 0; i < columns.length;  ++i )
	{
		 columns[ i ].dispose();
	}
}
//--------------------------------------------------------------------------------
public void drawTable( final java.util.List _dataList, final java.util.List _columnNameList, final Pattern grepPattern )
{

final MDocumentList documentList = this;

this._columns=_columnNameList;
this._datas = _dataList;

shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//*****

	if(first)//第一次访问
	{
		clearTable();//清除历史界面控件
		for( int i = 0; i < _columnNameList.size(); ++i )
		{
			String columnName = ( String )_columnNameList.get( i );
			TableColumn column = new TableColumn( tableViewer.getTable(), SWT.NONE );
			//原版
//			column.setText(columnName );
			tableViewer.getTable().getColumn(i).pack();
			column.setText(SysConfig.Instance().getColChiName(columnName));
		}
		
		MSwtUtil.getTableColumnWidthFromProperties2( getTablePrefix() , tableViewer.getTable(), prop );
		MSwtUtil.addListenerToTableColumns2( tableViewer.getTable(), documentList );
		tableViewer.setInput(_dataList);
	}
	else
	{
		tableViewer.setInput(_dataList);
		tableViewer.refresh();
	}
	
}
});//*****
}
private void drawTable1( final java.util.List _dataList, final java.util.List _columnNameList, final Pattern grepPattern )
{
final MDocumentList documentList = this;
shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//*****

	if(clear)
	{
clearTableSwt();

if( _dataList.size() == 0 )
	{
	return;
	}

try
	{
		//set columns
	//java.util.List columnNameList = MSwtUtil.getNameListFromDataList( _dataList );
	//debug( columnNameList );

	{
	for( int i = 0; i < _columnNameList.size(); ++i )
		{
		String columnName = ( String )_columnNameList.get( i );
		TableColumn column = new TableColumn( table, SWT.NONE );
		//原版
		column.setText(columnName );
//		column.setText(SysConfig.Instance().getColChiName(columnName));
		}
	
	MSwtUtil.getTableColumnWidthFromProperties2( getTablePrefix() , table, prop );
	MSwtUtil.addListenerToTableColumns2( table, documentList );
	}
	
		//draw items
	{
	for( int i = 0; i < _dataList.size(); ++i )
		{
		BasicDBObject documentData = ( BasicDBObject )_dataList.get( i );
		
		if( grepPattern != null )
			{
				//grep
			boolean grepMatches = false;
			for( int k = 0; k < _columnNameList.size(); ++k )
				{
				String columnName = ( String )_columnNameList.get( k );
				Object value = documentData.get( columnName );
				if( value == null )
					{
					}
				else
					{
					if( grepPattern.matcher( value.toString() ).find() )
						{
						grepMatches = true;
						break;
						}
					}
				}
			if( !grepMatches )
				{
				continue;
				}
			}
		
			//draw item
		TableItem item = new TableItem( table, SWT.NONE );
		item.setImage( image );
		
		item.setData( documentData );
		for( int k = 0; k < _columnNameList.size(); ++k )
			{
			String columnName = ( String )_columnNameList.get( k );

//			if(SysConfig.Instance().getColIsreal(columnName) == 1)
//				item.setBackground(k,  shell.getDisplay().getSystemColor( SWT.COLOR_RED));
			
			Object value = documentData.get( columnName );
			if( value == null )
				{
				if( documentData.containsKey( columnName ) )
					{
					item.setText( k, "null" );
					}
				else
					{
					item.setText( k, "" );				
					}
				}
			else
				{
				/*
				if( value instanceof java.util.Date )
					{
					DateFormat df = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
					item.setText( k, df.format( ( Date  )value ) );
					}
				else
				*/
					{
					item.setText( k, value.toString() );				
					}
				}
			}
		}
	}
	
	}
catch( Exception e )
	{
	e.printStackTrace();
	MEventManager.getInstance().fireErrorEvent( e );
	}

}}
});//*****
}
//------------------------------------------------------------------------
public boolean compare(java.util.List a, java.util.List b)
{
	    if(a.size() != b.size())
	        return false;

	    for(int i=0;i<a.size();i++)
	    {
	        if(!a.get(i).equals(b.get(i)))
	            return false;
	    }
	    return true;
}

//--------------------------------------------------------------------------------
private void onFind( MFindAction action )
{	
	dbName = action.getDB().getName();
	collName = action.getCollection().getName();
	
	if(dbName == preDbName && collName == preCollName)
		first=false;
	else
	{
		preDbName=dbName;
		preCollName=collName;
		first=true;//重新绘画界面
	}
	
	
	final java.util.List documentDataList = dataManager.getDocumentDataList();
	
	if(dbName.equals("config"))
	{
		columnNameList = dataManager.getColumnNameList();
	}
	else
	{
		if(MDataManager.getInstance().getFromFresh())
		{
			columnNameList = dataManager.getCloneColumnNameList();
		}
		else
		{
			columnNameList = dataManager.getColumnNameList();
			first=true;

			if(columnNameList.size() != 0 && !MDataManager.getInstance().getFromFresh())
			{
				//过滤列项
				shell.getDisplay().syncExec( new Runnable(){ public void run()	{//*****
					
					(new MFilterColumnDialog(null)).open();
					dataManager.setCloneColumnNameList(columnNameList);
					}});
			}
		}
	}
		
	drawTable( documentDataList, columnNameList);
	final MHistory history= dataManager.getFindHistory();
	final java.util.List historyList = history.getList();
	shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//-----
	historyCombo.removeAll();
	for( int i = 0; i < historyList.size(); ++i )
		{
		historyCombo.add( historyList.get( i ) + "" );
		}
	historyCombo.select( history.getPos() );
	
	boolean hasPrevItems = dataManager.hasPrevItems();
	prevItemsAction.setEnabled( hasPrevItems );
	prevItemsButton.setEnabled( hasPrevItems );
	
	boolean hasNextItems = dataManager.hasNextItems();
	nextItemsAction.setEnabled( hasNextItems );
	nextItemsButton.setEnabled( hasNextItems );
	
	}});//-----
	
	backAction.setEnabled( !history.atBegin() );
	reloadAction.setEnabled( true );
	forwardAction.setEnabled( !history.atEnd() );
		//naviLabel
	setNaviLabel();
	
	RefreshThread refresh = new RefreshThread();
	refresh.start();//线程start开始
}
//--------------------------------------------------------------------------------
private void setNaviLabel()
{
String labelString = "";
MFindAction findAction = dataManager.getLastFindAction();
int itemCount = dataManager.getDocumentDataList().size();
if( itemCount > 0 && findAction != null )
	{
	MFindQuery findQuery = findAction.getFindQuery();
	int start = 1;
	int end = 0;
	int skip = findQuery.getSkipArg();
	if( skip == -1 )
		{
		skip = 0;
		}
	start = skip + 1;
	int limit = findQuery.getLimitArg();
	if( itemCount < limit )
		{
		limit = itemCount;
		}
	if( limit >= 0 )
		{
		end = skip + limit;
		}
	else
		{
		end = skip + itemCount;
		}
	labelString = start + "-" + end;
	}

final String s = labelString;
shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//-----
naviLabel.setText( s );
naviLabel.getParent().layout();
//naviLabel.computeSize( SWT.DEFAULT, SWT.DEFAULT, true );
}});
}
//--------------------------------------------------------------------------------
private void initActionsAndButtons()
{
shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//-----

reloadAction.setEnabled( false );
backAction.setEnabled( false );
forwardAction.setEnabled( false );
prevItemsAction.setEnabled( false );
prevItemsButton.setEnabled( false );
nextItemsAction.setEnabled( false );
nextItemsButton.setEnabled( false );
editDocumentAction.setEnabled( false );
editFieldAction.setEnabled( false );
copyFieldAction.setEnabled( false );
copyAsJsonAction.setEnabled( false );
copyAsStringAction.setEnabled( false );
copyAction.setEnabled( false );
pasteAction.setEnabled( false );
removeAction.setEnabled( false );

}});//-----
}
//--------------------------------------------------------------------------------
private void onSort()
{
drawTable( dataManager.getDocumentDataList(), dataManager.getColumnNameList() );
}
//--------------------------------------------------------------------------------
private void onEdit( MEditAction action )
{
final String id = action.getIdAsString();

shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//*****

TableItem[] items = tableViewer.getTable().getItems();
for( int i = 0; i < items.length; ++i )
	{
	if( items[ i ].getText( 0 ).equals( id ) )
		{
		tableViewer.getTable().select( i );
		break;
		}
	}

}});//********
}
//--------------------------------------------------------------------------------
private void onUse()
{
shell.getDisplay().asyncExec( new Runnable(){ public void run()	{//-----
clearTableSwt();
}});//-----

initActionsAndButtons();
}
//--------------------------------------------------------------------------------
private void onDisconnect()
{
onUse();
}
//--------------------------------------------------------------------------------
public void update( final Object e, final Object source )
{
//threadPool.addCommand( new MCommand() {	public void execute(){ //-----------------

final MEvent event = ( MEvent )e;
final String eventName = event.getEventName();

if( event.getEventName().indexOf( event_find + "_end" ) == 0 )
	{
	MFindAction action = ( MFindAction )source;
	onFind( action );
	}
else if( event.getEventName().indexOf( event_mj_sort + "_end" ) == 0 )
	{
	onSort();
	}
else if( event.getEventName().indexOf( event_mj_edit + "_end" ) == 0 )
	{
	MEditAction action = ( MEditAction )source;
	onEdit( action );
	}
else if( event.getEventName().indexOf( event_use + "_end" ) == 0 )
	{
	onUse();
	}
else if( event.getEventName().indexOf( event_mj_copy + "_end" ) == 0 )
	{
	onCopy();
	}
else if( event.getEventName().indexOf( event_mj_paste + "_end" ) == 0 )
	{
	onPaste();
	}
else if( event.getEventName().indexOf( event_mj_remove + "_end" ) == 0 )
	{
	onRemove();
	}
else if( event.getEventName().indexOf( event_disconnect + "_end" ) == 0 )
	{
	onDisconnect();
	}
//	} public void breakCommand(){}	} ); //------------
}
//--------------------------------------------------------------------------------
private class MFilterColumnDialog extends Dialog
{
	private  Button[] buttons ;
	private HashMap<String,String> map = new HashMap<String,String>();
	
	protected MFilterColumnDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}	
	protected void configureShell( Shell newShell )
	{
		super.configureShell( newShell );
		newShell.setText( "筛选列" );
		
		Image image = MUtil.getImage( newShell.getDisplay(), "server_lightning.png" );
		newShell.setImage( image );
	}
	protected  Point getInitialSize() {   
	    return   new  Point( 300 ,  400 );   
	} 
	//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
	protected void okPressed()
	{
		columnNameList.clear();
		for (int i = 0; i < buttons.length; i++) 
		{
		    if (buttons[i].getSelection())
		     {
		    	columnNameList.add( map.get(buttons[i].getText()) );
		     }
		}	
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
	    

		buttons = new Button[columnNameList.size()];
	    for (int i = 0; i < columnNameList.size(); i++)
	    {
		    buttons[i] =new Button(c, SWT.CHECK);
		    
		    String name = columnNameList.get(i).toString();
		    map.put(SysConfig.Instance().getColChiName(name),name);
		    buttons[i].setText(SysConfig.Instance().getColChiName(name));
		    c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	    }
	    panel.setContent(c);
		
		return composite;
	}
	//--------------------------------------------------------------------------------
}
private class RadioDialog extends Dialog
{
	private  Button[] buttons ;
	private HashMap<String,String> map = new HashMap<String,String>();
	protected RadioDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}	
	protected void configureShell( Shell newShell )
	{
		super.configureShell( newShell );
		newShell.setText( "多选项" );
		
		Image image = MUtil.getImage( newShell.getDisplay(), "server_lightning.png" );
		newShell.setImage( image );
	}
	protected  Point getInitialSize() {   
	    return   new  Point( 300 ,  400 );   
	} 
	//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
	protected void okPressed()
	{
		for (int i = 0; i < buttons.length; i++) 
		{
		    if (buttons[i].getSelection())
		     {
			    text.setText(map.get(buttons[i].getText()));
		     }
		}	
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
		 

		String[] optionalList = optionalItems.split(",");//显示所替代的中文
		buttons = new Button[optionalList.length];
	    for (int i = 0; i < optionalList.length; i++)
	    {
	    	String item[]=optionalList[i].split(":");
	    	map.put(item[1], item[0]);
		    buttons[i] =new Button(c,SWT.RADIO);
		    buttons[i].setText(item[1]);
		    c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));		    
	    }
	    panel.setContent(c);
		
		return composite;
	}
	//--------------------------------------------------------------------------------
}
//------------------------------------------------------------------------------------
private class OptionalDialog extends Dialog
{
	private  Button[] buttons ;
	private HashMap<String,String> map = new HashMap<String,String>();
	String[] optionalList;
	protected OptionalDialog(Shell parentShell) {
		super(parentShell);
	    optionalList= optionalItems.split(",");
		// TODO Auto-generated constructor stub
	}	
	protected void configureShell( Shell newShell )
	{
		super.configureShell( newShell );
		newShell.setText( "多选项" );
		
		Image image = MUtil.getImage( newShell.getDisplay(), "server_lightning.png" );
		newShell.setImage( image );
	}
	protected  Point getInitialSize() {   
	    return   new  Point( 300 ,  400 );   
	} 
	//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
	protected void okPressed()
	{
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < buttons.length; i++) 
		{
		    if (buttons[i].getSelection())
		     {
		    	str.append(map.get(buttons[i].getText())+",");
		     }
		}
		System.out.println(str);
		text.setText(str.substring(0,str.length()-1).toString());
		setReturnCode(OK);
		close();
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
	       
//	    panel.setLayout(new  GridLayout( 1 ,  false )); 
	
	    final Composite c = new Composite(panel, SWT.NONE);
	    GridLayout layout = new GridLayout();
	    layout.numColumns=1;
	    c.setLayout(layout);
	    		
	    
		buttons = new Button[optionalList.length];
	    for (int i = 0; i < optionalList.length; i++)
	    {
	    	String[] item = optionalList[i].split(":");
	    	map.put(item[1], item[0]);
		    buttons[i] =new Button(c,SWT.CHECK);
		    buttons[i].setText(item[1]);
		    
	    }
	        
		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		panel.setContent(c);
	 		
		return composite;
	}
	//--------------------------------------------------------------------------------
}
}

