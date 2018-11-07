package net.jumperz.app.common;

import java.util.Locale;
import java.util.ResourceBundle;

public class BoundlerHelper {
	static ResourceBundle bundle;
	
	public static  String getInfo(String key){
	//workspace\CoolSQL\CoolSql\info.properties
		if (bundle == null) {
			bundle = ResourceBundle.getBundle("info", Locale.getDefault());//com.coolsql.resource.info
		}
		
		// get value by key in resource file
		try {
			return bundle.getString(key);
		} catch (java.util.MissingResourceException e) {
			/*JOptionPane.showMessageDialog(null, "can't find resource,key=" + key,
					"error", 0);*/
			return "";
		}
	}
}
