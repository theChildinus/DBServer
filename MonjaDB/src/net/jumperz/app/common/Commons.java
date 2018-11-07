package net.jumperz.app.common;


import java.io.UnsupportedEncodingException;
/**
 * 获取工程目录绝对路径
 * @author 
 *
 */

public class Commons {
	
	private static  Commons instance = new Commons();
	private String runPath;
	
	public static Commons getInstance()
	{
		return instance;
	}
	
	private Commons()
	{
		runPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		String fileLocationPrefix = "file:/";
		
		if(runPath.startsWith(fileLocationPrefix)) {
			runPath = runPath.substring(fileLocationPrefix.length());
		}
		
		try {
			runPath = java.net.URLDecoder.decode(runPath,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		runPath = runPath.substring(0,runPath.lastIndexOf("/")+1);
	}
	
	public  String getRunPath() 
	{
		return runPath;
	}
}
