package com.sample;


	import java.io.File;
	import java.util.ArrayList;
	import java.util.List;
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.google.appengine.api.datastore.Entity;
	
	
	public class JsonObjectMapper {

		
		public static void writeJSON(String str) {
			
			String jsonstr;
			//Customer  customerobject = CustomersData.getCustomer();
	        
	        ObjectMapper mapper = new ObjectMapper();
	 
	     
	         
	        try {
	            jsonstr = mapper.writeValueAsString(str);
	            
	            
	            //map< object  = mapper.reader()
	        	//mapper.writeValue( new File("Customers.json"), customerobject);  //Plain JSON
	        	System.out.println(jsonstr);
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	    }
	     
	    
		
		

	}


