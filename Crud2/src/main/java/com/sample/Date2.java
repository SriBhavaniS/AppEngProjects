package com.sample;


	import java.time.format.DateTimeFormatter;
	import java.time.LocalDateTime;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import com.google.protobuf.TextFormat.ParseException;

	public class Date2 {

		/*public static Date getDateTime() throws ParseException, java.text.ParseException {
			   String dateStr;		   
		       Date dNow = new Date( );	      
			   SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			   dateStr = sm.format(dNow);
			   dNow = sm.parse(dateStr);
			   return dNow;
		       
		}*/   
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			   LocalDateTime now = LocalDateTime.now();  
			   {
			   System.out.println(dtf.format(now));  
	}
	}
	


