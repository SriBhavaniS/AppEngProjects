package com.sample;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.protobuf.TextFormat.ParseException;

import java.util.Calendar;
import java.util.Date;

public class Date1 {

	public static Date getDate() {
	    Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

	    String strDate = sdf.format(date);
	    System.out.println("formatted date in dd/mm/yy : " + strDate);

	    sdf = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
	    strDate = sdf.format(date);
	    //System.out.println("formatted date in dd-MM-yyyy kk:mm:ss : " + strDate);
		return date;

    }
}