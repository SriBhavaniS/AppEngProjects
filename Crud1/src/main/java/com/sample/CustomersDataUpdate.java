package com.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

//@WebServlet(
		 //   name = "UpdateCustomer",
		  //  urlPatterns = {"/update"}  //to find which servlet must be invoked for a given url by a client.
		//)

public class CustomersDataUpdate extends HttpServlet {
	
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
    
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	  
		  
		 
		  BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
		    String json = "";
		    if(br != null){
		        json = br.readLine();
		    }
		    
		    System.out.println("JSON request in doPut " + json);
		    
		    
		    ObjectMapper mapper = new ObjectMapper();

		    //  received JSON to customer object
		    customerObj = mapper.readValue(json, Customer.class);
		
		    
		    try{
		        update(request, response, customerObj);
		        
		    }catch(Exception e){
		        e.printStackTrace();
		    }
		}
	  
	  




public void update(HttpServletRequest request, HttpServletResponse response, Customer customerObj) throws IOException {

	uuid =  customerObj.getUuid();
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	customerKey = KeyFactory.createKey("Customer", uuid);
	 
	String name = customerObj.getUsername();
	String email = customerObj.getEmailid();
	
	phones = customerObj.getPhones();
	//String lastUpdated = customerObj.getLastUpdated();
	
	
	
	//System.out.println("Update values " + name + " " + email + " " + uuid + landline + "" + mobile + "");
	
	Query query = new Query("Customer");
	query.addFilter("uuid", FilterOperator.EQUAL, uuid);
	PreparedQuery pq = datastore.prepare(query);
	Entity customer = pq.asSingleEntity();
	
	customer.setProperty("name", name);
	customer.setProperty("email", email);
	customer.setProperty("lastUpdated", Date1.getDate());
	datastore.put(customer);  
	
	
	
	Query query1 = new Query("Phones");    // Delete phone entries so new entries can be inserted
    query1.addFilter("uuid", FilterOperator.EQUAL, uuid);                
    PreparedQuery pq1 = datastore.prepare(query1);
    
    //datastore.delete(phoneDel.getKey());  
    
    
	
    for (Entity result : pq1.asIterable())
      {
    	  datastore.delete(result.getKey());    	    	   
    	 
      }
    
    
    
	/*
	//landlines = customerObj.getLandlines();
    //mobiles = customerObj.getMobiles();    
    
    int landlinesSize = landlines.size();
    int mobilesSize = mobiles.size();
    
    System.out.println("Landlines " + landlinesSize );
    System.out.println("Mobiles " + mobilesSize );
   
   int iter = Math.max(landlinesSize, mobilesSize);
   
   System.out.println("Iteration size " + iter);
   
   for (int i = 0; i < iter; i++)
   {              	   
	   Entity phones = new Entity("Phones", customerKey);
	   
	   phones.setProperty("uuid", uuid);
	   
	   if(i < landlinesSize)
	   	{
		   phones.setProperty("landlines", landlines.get(i));
	   	}
	   if(i < mobilesSize)
	   	{
		   phones.setProperty("mobiles", mobiles.get(i));	
	   	}
	   
	   datastore.put(phones);
	}     */
    
    
    Set<Map.Entry<String, Map<String, String>>> entries = phones.entrySet();
    
    for (Map.Entry<String, Map<String, String>> entry : entries) {
        
    	String phoneID = entry.getKey();	   
    	System.out.println("Phone set "+ phoneID);
    	
        Map<String, String> phoneDetails = entry.getValue();
         
        //Set<Map.Entry<String, String>> entries1 = phoneDetails.entrySet();
        
        Entity phonesEnt = new Entity("Phones", customerKey);
 	   	phonesEnt.setProperty("uuid", uuid);
 	   	phonesEnt.setProperty("deleted", false);
 	   	
 	   	String phoneType = "";
 	   	String phoneNumber = "";
 	   	
 	   	int valCount = 1;
        
        for (String value: phoneDetails.values())                         
        {	 
        	if(valCount == 1)                        	
        	{
        	 phoneNumber = value;
        	 phonesEnt.setProperty("PhoneNumber", phoneNumber);
        	}
        	
        	
        	if(valCount == 2)
        	{
        	 phoneType = value;
        	 phonesEnt.setProperty("PhoneType", phoneType);
        	}
        	
        	valCount++;
        	
        	System.out.println(phoneType);
        	System.out.println(phoneNumber);
        	
        	}
        	           	
  
        	datastore.put(phonesEnt);
        	
        		                        	
        }
	
	
	
	
	PrintWriter out = response.getWriter();
    out.print("Data updated successfully");
         
    
}
}


