package com.loan.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	public static String formatDateToString(Timestamp timestamp) {
		String str="";
		try {
		if(timestamp!=null) {
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		str = sdf.format(date);
		}
		}
		catch(Exception e) {
			
		}
		return str;
	}
	
	public static Date formatStringToDate(String dateStr) {
		Date dateObj = null;
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	         dateObj = sdf.parse(dateStr);
	        
		}

		catch(Exception e) {
			
		}
		return dateObj;
	
	}
	
	public static java.sql.Date formatStringToSqlDate(String dateStr) {
		Date utilDate = formatStringToDate(dateStr);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	
	}


}
