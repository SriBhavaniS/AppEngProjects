package com.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

/*@WebServlet(
		    name = "DeleteCustomer",
		    urlPatterns = {"/delete"}  //to find which servlet must be invoked for a given url by a client.
		) */

public class CustomersDataDelete extends HttpServlet {
	
	private String username;
    private String emailid;
    private static UUID UUID;
    private String uuid;	    
    private Customer customerObj;
    private String createdDate;
    private String lastUpdated;
    private boolean deleted;
    private ArrayList<String> landlines;
    private ArrayList<String> mobiles;
    private Key customerKey;
    private HashMap<String, Map<String, String>> phones;
    
	
	 protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	  
		  
		 
		  BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
		    String json = "";
		    if(br != null){
		        json = br.readLine();
		    }
		  
		  		    
		    System.out.println("JSON request in doDelete " + json);
		    
		    
		    ObjectMapper mapper = new ObjectMapper();

		    //  received JSON to customer object
		    customerObj = mapper.readValue(json, Customer.class);
		
		    
		    try{
		        delete(request, response, customerObj);
		        
		    }catch(Exception e){
		        e.printStackTrace();
		    }
		}
	  
	  
	  
	  public static String getUuid() {
	        
	        UUID = java.util.UUID.randomUUID();
	        String randomUUIDString = UUID.toString();
	        return randomUUIDString;      
	        
	        
	    }
	  
	  
	  
	public void delete(HttpServletRequest request, HttpServletResponse response, Customer customerObj) throws IOException {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
       uuid = customerObj.getUuid();

        Query query = new Query("Customer");
        query.addFilter("uuid", FilterOperator.EQUAL, uuid);
        
        PreparedQuery pq = datastore.prepare(query);
        Entity customer = pq.asSingleEntity();
        customer.setProperty("deleted",true);
        datastore.put(customer);
        
        
        Query query1 = new Query("Phones");    // Delete phone entries so new entries can be inserted
        query1.addFilter("uuid", FilterOperator.EQUAL, uuid);                
        PreparedQuery pq1 = datastore.prepare(query1);
        
        //datastore.delete(phoneDel.getKey());                     
        
		
        for (Entity result : pq1.asIterable())
	      {
	    	  result.setProperty("deleted", true);  	    	   
	    	 
	      }
        

       // datastore.delete(customer.getKey());  
        
        PrintWriter out = response.getWriter();
        out.print("Data deleted successfully");
    
       

}




}
