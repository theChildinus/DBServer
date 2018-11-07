package net.jumperz.app.common;

import net.jumperz.app.MMonjaDBCore.MDataManager;
import net.jumperz.app.state.SystemState;

public class RefreshThread extends Thread{
	protected MDataManager dataManager = MDataManager.getInstance();
	
	public void run()
	{
		try {
			sleep(Integer.parseInt(PublicResource.getString("refreshInterval")));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(SystemState.isRefresh)
			dataManager.reloadDocument();
	}

}
