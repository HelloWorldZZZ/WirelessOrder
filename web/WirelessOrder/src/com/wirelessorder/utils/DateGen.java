package com.wirelessorder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGen {
	
	public static String getTimeStr(){
		Date date = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		return time; 
	}

}
