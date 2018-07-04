package com.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.*; 

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
/*@WebServlet(
		    name = "CreateCustomer",
		    urlPatterns = {"/create"}  //to find which servlet must be invoked for a given url by a client.
		) */

public class CustomersDataCreate extends HttpServlet{
	
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
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	    
	    
	 		  
		 
		  BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
		    String json = "";
		    if(br != null){
		        json = br.readLine();
		    }
		    
		   System.out.println("JSON request in doPost " + json);  
		   
		    
		    
		    
		    ObjectMapper mapper = new ObjectMapper();

		    //  received JSON to customer object
		    customerObj = mapper.readValue(json, Customer.class);
		
		    //System.out.println("Landlines in customer object " + customerObj.getLandlines().toString());
		    
		    try{
		       add(request, response, customerObj);
		     //  listSingle(request, response);
		        
		    }catch(Exception e){
		        e.printStackTrace();
		    }
		    
		 
		  }
	 public void add (HttpServletRequest request, HttpServletResponse response, Customer customerObj) throws IOException {
	 		
		 uuid = generateUuid();   
			
			customerKey = KeyFactory.createKey("Customer", uuid);
       Entity customer = new Entity("Customer", customerKey);                   
         
                          
        
         
         customer.setProperty("name", customerObj.getUsername());
         customer.setProperty("email", customerObj.getEmailid());
         customer.setProperty("uuid",uuid);
         customer.setProperty("createdDate", Date1.getDate());
         customer.setProperty("lastUpdated", null);   	      
         customer.setProperty("deleted",false);             
         
         
         DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
         datastore.put(customer);                     
        	                   
         
        /* landlines = (ArrayList<String>) customerObj.getLandlines();
         mobiles = (ArrayList<String>) customerObj.getMobiles();   
        
         
        System.out.println(landlines.size());
        System.out.println(mobiles.size());   
        
        int iter = Math.max(landlines.size(), mobiles.size());
        
        
        
        for (int i = 0; i < iter; i++)
        {              	   
     	   Entity phones = new Entity("Phones", customerKey);
     	   
     	   phones.setProperty("uuid", uuid);
     	   phones.setProperty("deleted", false);
     	   
     	   if(landlines.get(i) != null)
     	   	{
     		   phones.setProperty("landlines", landlines.get(i));
     	   	}
     	   if(mobiles.get(i) != null)
     	   	{
     		   phones.setProperty("mobiles", mobiles.get(i));	
     	   	}
     	   
     	   datastore.put(phones);
     	}                    commented on 25-June*/                       

         
         
         phones = customerObj.getPhones();
         
        // int mapSize = phones.size();
         
        
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
             	 phoneType = value;
             	 phonesEnt.setProperty("PhoneType", phoneType);
             	}
             	
             	
             	if(valCount == 2)
             	{
             	 phoneNumber = value;
             	 phonesEnt.setProperty("PhoneNumber", phoneNumber);
             	}
             	
             	valCount++;
             	
             	System.out.println(phoneType);
             	System.out.println(phoneNumber);
             	
          	}
             	           	
       
             	datastore.put(phonesEnt);
             	
             		                        	
             }
             
             
             
             
         }
	 
	 public static String generateUuid() {
	        
	        UUID = java.util.UUID.randomUUID();
	        String randomUUIDString = UUID.toString();
	        return randomUUIDString;      
	        
	        
	    }
	  
         
         
        
         
 }
