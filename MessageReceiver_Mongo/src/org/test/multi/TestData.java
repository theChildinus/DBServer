package org.test.multi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestData {

	public long timeDiff(String message, String ReceiveDate){
		long diff = 0;
		int start = message.indexOf("<timestamp>");
		int end = message.indexOf("</timestamp>");
		String notifyDate = "";
		if((start != -1) && (end != -1))
			notifyDate = message.substring(start + 11, end);
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			Date notify = sdf.parse(notifyDate);
			Date receive = sdf.parse(ReceiveDate);
			diff = receive.getTime() - notify.getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
	}
}
