package com.bupt.MetaData.model;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.bupt.MetaData.view.AmoebaConfig;
import com.bupt.MetaData.view.DataCenter;

public class DataCenterPerspective implements IPerspectiveFactory{

	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
		String editorArea = layout.getEditorArea();
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);
		
		layout.addStandaloneView(DataCenter.ID,false, IPageLayout.LEFT, 1.0f, editorArea);
		layout.setFixed(true);
		layout.setEditorAreaVisible(false);
	}
	
	/**
	 * Add fast views to the perspective.
	 */
	private void addFastViews(IPageLayout layout) {
	}

	/**
	 * Add view shortcuts to the perspective.
	 */
	private void addViewShortcuts(IPageLayout layout) {
	}

	/**
	 * Add perspective shortcuts to the perspective.
	 */
	private void addPerspectiveShortcuts(IPageLayout layout) {
	}

}
