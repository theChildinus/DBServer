package net.jumperz.app.MMonjaDB.eclipse;

import net.jumperz.app.MMonjaDBCore.MAbstractLogAgent;
import net.jumperz.app.MMonjaDBCore.MDataManager;
import net.jumperz.app.MMonjaDBCore.action.MActionManager;

import net.jumperz.app.MMonjaDB.eclipse.view.*;
import net.jumperz.app.common.BackupConfigServer;
import net.jumperz.app.common.LoadConfig;
import net.jumperz.app.common.Publics;
import net.jumperz.app.receiver.Receiver;
import net.jumperz.util.MProperties;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.console.IConsoleConstants;

public class MPerspectiveFactory1
extends MAbstractLogAgent
implements IPerspectiveFactory
{
//--------------------------------------------------------------------------------
	public void createInitialLayout( IPageLayout layout )
	{
		//在绘制面板之前从元数据服务器下载所需要的配置文件
		Receiver configLoad = new Receiver();
		configLoad.load();
		
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible( false );
		
		IFolderLayout bottomFolder	= layout.createFolder( "bottom", IPageLayout.BOTTOM, 0.65f, editorArea );
		IFolderLayout leftFolder	= layout.createFolder( "left", IPageLayout.LEFT, 0.22f, editorArea );
		IFolderLayout centerFolder	= layout.createFolder( "center", IPageLayout.LEFT, 0.68f, editorArea );
		IFolderLayout rightFolder	= layout.createFolder( "right", IPageLayout.LEFT, 0.10f, editorArea );
		
		IFolderLayout bottomLeftFolder	= layout.createFolder( "bottomLeft", IPageLayout.LEFT, 0.35f, "bottom" );
		IFolderLayout bottomCenterFolder= layout.createFolder( "bottomCenter", IPageLayout.LEFT, 0.45f, "bottom" );
		
		//IFolderLayout rightBottomFolder = layout.createFolder( "rightBottom", IPageLayout.BOTTOM, 0.75f, "right" );
		
		
//		String URL;
//		String localMongo = null;
//		
//		if((localMongo = LoadConfig.getInstance().getLocalMongo()) !=null)
//		{
//			URL = localMongo;
//		}
//		else
//		{
//			URL=LoadConfig.getInstance().chooseAmoeba();
//		}
		
		leftFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MDBTree.class.getName() );
		centerFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MDocumentList.class.getName() );
		rightFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MDocumentEditor.class.getName() );
		
		bottomLeftFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MActionView.class.getName() );
		
		bottomCenterFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MSavedActionsView .class.getName() );
		
		bottomFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MJavaScriptView .class.getName() );
		bottomFolder.addView( IConsoleConstants.ID_CONSOLE_VIEW );
		
		rightFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MJsonView .class.getName() );
		
		centerFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MDBList.class.getName() );
		centerFolder.addView( net.jumperz.app.MMonjaDB.eclipse.view.MCollectionList.class.getName() );
	
		/**
		 * 从元数据服务器备份配置库，本地还原配置库
		 */
		BackupConfigServer.getInstance().BackupRemoteData();
		BackupConfigServer.getInstance().restoreRemoteData();
	}
	
	
//--------------------------------------------------------------------------------
}
