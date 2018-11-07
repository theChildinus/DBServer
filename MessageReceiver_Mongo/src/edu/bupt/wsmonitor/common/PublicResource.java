package edu.bupt.wsmonitor.common;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

public class PublicResource {

	private static ResourceBundle bundle = null;
	private static File file=null;
	private static long old=-1;
	
	private PublicResource() {
	}

	/**
	 * initalize resource
	 *  
	 */
	public static void changeBundle(String respath) {
		bundle = ResourceBundle.getBundle(respath);
		file=new File(transPath(respath));
		old=file.lastModified();
	}
	private static String transPath(String str)
	{
		if(str==null||str.equals(""))
			return "";
		String tmp=str.replace('.','/');
		tmp=tmp+".properties";
		return tmp;
	}
    /**
     * 返回一个资源绑定实例
     * @return
     */
	private static ResourceBundle getBundle() {
		
		if (bundle == null) {
			bundle = ResourceBundle.getBundle("info", Locale.getDefault());
			file=new File("info.properties");
			old=file.lastModified();
		}else
		{
			if(fileChanged())
				changeBundle("info");
		}
		return bundle;
	}

	/**
	 * 判断资源文件是否改变
	 * @return
	 */
    protected static boolean fileChanged()
    {
    	if(old!=file.lastModified())
    	{
    		return true;
    	}else
    		return false;
    }
	/**
	 * get value by key in resource file
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return getBundle().getString(key);
		} catch (java.util.MissingResourceException e) {
			JOptionPane.showMessageDialog(null,"can't find resource,key=" + key,
					"error", 0);
			return "";
		}
	}
	

}
