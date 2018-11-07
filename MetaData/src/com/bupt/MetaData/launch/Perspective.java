package com.bupt.MetaData.launch;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.bupt.MetaData.Server.AmoebaServer;
import com.bupt.MetaData.Server.ClientServer;
import com.bupt.MetaData.Server.HotDataServer;
import com.bupt.MetaData.Server.ReceiverServer;
import com.bupt.MetaData.view.AmoebaConfig;
import com.bupt.MetaData.view.AmoebaStatus;
import com.bupt.MetaData.view.DataCenter;
import com.bupt.MetaData.view.MongoList;


public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		
		ClientServer clientserver = new ClientServer();//Ϊ�ͻ��������ṩ����
		clientserver.start();
		
		HotDataServer hotdataserver = new HotDataServer();//�����ݷ�����
		hotdataserver.start();
		
		AmoebaServer amoebaserver = new AmoebaServer();//amoeba������
		amoebaserver.start();
		
		ReceiverServer receiverserver = new ReceiverServer();
		receiverserver.start();
		
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
//		IFolderLayout bottomFolder	= layout.createFolder( "bottom", IPageLayout.BOTTOM, 0.65f, editorArea );
//		
//		bottomFolder.addView( com.bupt.MetaData.view.MongoList.class.getName() );
		
		IFolderLayout folderLayout = layout.createFolder("topRight", IPageLayout.RIGHT,  0.7f,editorArea);
		folderLayout.addView(MongoList.ID);
        
        folderLayout.addPlaceholder(AmoebaConfig.ID);
        folderLayout.addPlaceholder(AmoebaStatus.ID);
        folderLayout.addPlaceholder(DataCenter.ID);


	}

}
