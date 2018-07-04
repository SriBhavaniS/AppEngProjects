package com.sample;


	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.api.services.discovery.model.JsonSchema.Variant.Map; //commented on 25th June
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
	import com.google.appengine.api.datastore.DatastoreServiceFactory;
	import com.google.appengine.api.datastore.Entity;
	import com.google.appengine.api.datastore.FetchOptions;
	import com.google.appengine.api.datastore.Key;
	import com.google.appengine.api.datastore.KeyFactory;
	import com.google.appengine.api.datastore.PreparedQuery;
	import com.google.appengine.api.datastore.Query;
	import com.google.appengine.api.datastore.Query.Filter;
	import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	@WebServlet(
	    name = "HelloAppEngine",
	    urlPatterns = {"/hello"}  //to find which servlet must be invoked for a given url by a client.
	)

	public class CustomersData extends HttpServlet {
		
		
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
	    
	   // static final int page_size = 10;

	  @Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response) 
	      throws IOException {
		  
	      
	    response.setContentType("text/html");// tells client what type of content is sent.
	    response.setCharacterEncoding("UTF-8");
	    
	    String axn = "";

	    axn = request.getParameter("axn");
	    uuid = request.getParameter("uuid");    
	    System.out.println(uuid);
	  
	    if(axn.equals(new String("list")))
	    {
	    	
	    	list(request, response);
	    }	   
	    
	    
	    if(axn.equals(new String("listSingle")))
	    {
	    	
	    	listSingle(request, response);
	    }	      
	    
 }
	  
	  
	  
	  private void listSingle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		  
		  PrintWriter out = response.getWriter();
          DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
          
          //landlines.clear();  // remove the values in the list which are loaded in post method 25-jun
          //mobiles.clear();
          
          Query query = new Query("Customer");
          query.addFilter("uuid", FilterOperator.EQUAL, uuid);
         // query.setFilter(new Query.FilterPredicate("uuid", FilterOperator.EQUAL,uuid));
          PreparedQuery pq = datastore.prepare(query);
          Entity customer = pq.asSingleEntity();    
          
          //System.out.println("Customer key " + customer.getKey().toString());
          
          username = customer.getProperty("name").toString();
    	  emailid = customer.getProperty("email").toString();
    	  uuid = customer.getProperty("uuid").toString();
    	  createdDate = customer.getProperty("createdDate").toString();  
    	  if(customer.getProperty("lastUpdated") != null)
  	  		{
    		  lastUpdated = customer.getProperty("lastUpdated").toString();
  	  		}
    	  else
    	  {
    		  lastUpdated = "";
  	  		}
          
    	  Query query1 = new Query("Phones");
          query1.addFilter("uuid", FilterOperator.EQUAL, uuid);
          PreparedQuery pq1 = datastore.prepare(query1);
         // Entity phone = q.asSingleEntity();        
	    	   
          phones = new HashMap<String, Map<String, String>>();

       
      /* List<Entity> results =
           datastore.prepare(phoneQuery).asList(FetchOptions.Builder.withDefaults());*/
          int resultCount = 0;
          String rstCntStr;
          
       		for (Entity result : pq1.asIterable()) {
       			
       			resultCount += 1;
       			
       			Map<String, String> phoneMap = new HashMap<String, String>();
       			phoneMap.put("type", result.getProperty("PhoneType").toString());
       			phoneMap.put("number", result.getProperty("PhoneNumber").toString());      			
       			
       			
       			rstCntStr = Integer.toString(resultCount);
       			phones.put(rstCntStr,phoneMap);
       				
   	      }
	    	 
	    	 
	    	  
	    	 //System.out.println("While listing single customer " + username + " " + emailid + " " + uuid + " " + createdDate + " " + lastUpdated +"" + landlines + " " + mobiles + " " );*/
	    	  
	    	   Customer customerSingle  =  getCustomer();
	    	  
	      
	     
	    	 
	      ObjectMapper mapper = new ObjectMapper();
	      
	      response.setContentType("application/json");	      
	      String jsonStr = mapper.writeValueAsString(customerSingle);
	      System.out.println("JSON from ListSingle " + jsonStr);
	      out.print(jsonStr);		  
		  
		
	}



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
		       listSingle(request, response);
		        
		    }catch(Exception e){
		        e.printStackTrace();
		    }
		    
		 
		    
		}
	  
	  
	  
	 
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
	  
	  
	  
	  
	  public void add (HttpServletRequest request, HttpServletResponse response, Customer customerObj) throws IOException {
 		
		  		    		  		
		  				uuid = getUuid();
		  				customerKey = KeyFactory.createKey("Customer", uuid);
	    	        
	    	        	
	                    Entity customer = new Entity("Customer", customerKey);                   
	                    
	                    uuid = getUuid();                    
	                   
	                    
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
	                    
	                    
	                   
	                    
	            
	    	
	    		
	  
	 
	  
	  
	  
	  
	  
	  
	    	
	    	    	
	    	

	    	
	    	
	    	public void update(HttpServletRequest request, HttpServletResponse response, Customer customerObj) throws IOException {

	    		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    		customerKey = KeyFactory.createKey("Customer", uuid);
	    		 
	    		String name = customerObj.getUsername();
	    		String email = customerObj.getEmailid();
	    		uuid =  customerObj.getUuid();
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

	    	
	    	
	    	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    		
	    		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	    		
	    		    
	    		 Query q = new Query("Customer").addSort("createdDate", Query.SortDirection.DESCENDING);
	    		 q.addFilter("deleted",FilterOperator.EQUAL,false);
	    	      PreparedQuery pq = datastore.prepare(q);
	    	      PrintWriter out = response.getWriter();
	    	   
	    	       phones = new HashMap<String, Map<String, String>>();
	    	       
	    	       List<Customer> Customers = new LinkedList<Customer>();    	      
	    	      
	    	       
	    	   
	    	     	    	      
	    	      for (Entity result : pq.asIterable())	    	    	  
	    	      {    	    	  
	    	    	  
	    	    	  
	    	    	  username = result.getProperty("name").toString();
	    	    	  emailid = result.getProperty("email").toString();
	    	    	  uuid = result.getProperty("uuid").toString();
	    	    	  createdDate = result.getProperty("createdDate").toString();  	 
	    	    	 
	    	    	 
	    	    	 if(result.getProperty("lastUpdated") != null)
	    	    	 {
	    	    		 lastUpdated = result.getProperty("lastUpdated").toString();
	    	    	 }
	    	    	 else
	    	    	 {
	    	    		 lastUpdated = "";
	    	    	 }
	    	    	  System.out.println("While listing " + username + " " + emailid + " " + uuid + " " + createdDate + " " + lastUpdated  );
	    	    	  
	    	    	 
	    	    	 	Query query1 = new Query("Phones");
	    	          query1.addFilter("uuid", FilterOperator.EQUAL, uuid);
	    	          PreparedQuery pq1 = datastore.prepare(query1);
	    	         // Entity phone = q.asSingleEntity();       
	    		    	   
	    	
	    	          int resultCount = 0;
	    	          String rstCntStr;
	    	          
	    	       		for (Entity result1 : pq1.asIterable()) {
	    	       			
	    	       			resultCount += 1;
	    	       			
	    	       			Map<String, String> phoneMap = new HashMap<String, String>();
	    	       			phoneMap.put("type", result1.getProperty("PhoneType").toString());
	    	       			phoneMap.put("number", result1.getProperty("PhoneNumber").toString());      			
	    	       			
	    	       			System.out.println("Record count " + resultCount);
	    	       			System.out.println("Phone Type" + result1.getProperty("PhoneType").toString());
	    	       			System.out.println("Phone Number" + result1.getProperty("PhoneNumber").toString());
	    	       			
	    	       			rstCntStr = Integer.toString(resultCount);
	    	       			phones.put(rstCntStr,phoneMap);
	    	       				
	    	   	      }
		    	      
		    	      
		    	         Customer customer =  getCustomer();
		   	    	     Customers.add(customer);
		   	    	     
		   	    	   
		    	      
	    	      }
	    	    	 
	    	      ObjectMapper mapper = new ObjectMapper();
	    	      
	    	      response.setContentType("application/json");  
	    	      //mapper.writeValue(out, Customers);
	    	      String jsonStr = mapper.writeValueAsString(Customers);
	    	      System.out.println("JSON from list " + jsonStr);
	    	      out.print(jsonStr); 	    	      
	    	     
	    	      

	    	}
	    	    
	    	
	    	
	    	 Customer getCustomer(){
	    	      Customer customer = new Customer();
	    	      customer.setUsername(username);
	    	      customer.setEmailid(emailid);  
	    	      customer.setUuid(uuid); 
	    	      customer.setCreatedDate(createdDate);
	    	      customer.setLastUpdated(lastUpdated);	    	   
	    	      customer.setPhones(phones);
	    	      
	    	      return customer;
	    	
	    	}

	  }



