package com.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
		   name = "ReadCustomer",
		   urlPatterns = {"/read"}  //to find which servlet must be invoked for a given url by a client.
		) */

public class CustomersDataRead  extends HttpServlet{
	
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
	      
	      
      			Customer customer = getCustomer();
	         
	    	     Customers.add(customer);
	    	     
	    	   
	      
     }
   	 
     ObjectMapper mapper = new ObjectMapper();
     
     response.setContentType("application/json");  
     //mapper.writeValue(out, Customers);
     String jsonStr = mapper.writeValueAsString(Customers);
     System.out.println("JSON from list " + jsonStr);
     out.print(jsonStr); 	    	      
    
     }


public  Customer  getCustomer(){
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
   
