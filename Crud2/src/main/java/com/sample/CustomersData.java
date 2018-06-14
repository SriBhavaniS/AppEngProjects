package com.sample;


	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.discovery.model.JsonSchema.Variant.Map;
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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	@WebServlet(
	    name = "HelloAppEngine",
	    urlPatterns = {"/hello"}
	)

	public class CustomersData extends HttpServlet {
		
		
		private String username;
	    private String emailid;
	    private static UUID UUID;
	    private String uuid;
	    //private String Date;
	    private Customer customerObj;

	  @Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response) 
	      throws IOException {
		  
	      
	    response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    
	    /*String axn = "";

	    axn = request.getParameter("axn");
	    
	    
	    if(axn.equals(new String("add")))
	    {	    	   	
			add(request, response, customerObj );					
	    }
	    if(axn.equals(new String("listsingle")))
	    {
	    	
	    	listSingle(request, response);
	    }
	    if(axn.equals(new String("update")))
	    {
	    	
	    	update(request, response);
	    }
	    if(axn.equals(new String("delete")))
	    {
	    	
	    	delete(request, response);
	    }
	    if(axn.equals(new String("list")))
	    {
	    	
	    	list(request, response);
	    }	   */
	    
	    
	    list(request, response);
	    
	  }
	  
	  
	  
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	    
		    
		 		  		  
		  	//username = request.getParameter("username");   
  			//emailid = request.getParameter("emailid");
		  	//  System.out.println(username + "username");  This is not working if the form data is sent as JSON	 
		  
		 
		  BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
		    String json = "";
		    if(br != null){
		        json = br.readLine();
		    }
		    
		    System.out.println("JSON request in doPost " + json);
		    
		    
		    ObjectMapper mapper = new ObjectMapper();

		    //  received JSON to customer object
		    customerObj = mapper.readValue(json, Customer.class);
  		
		    
		    try{
		        add(request, response, customerObj);
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
	  
	  
	  
	  
	  public void add(HttpServletRequest request, HttpServletResponse response, Customer customerObj) throws IOException {

	    		
		  		
	    		
	    		//String json = "{ "name" ; "vineeth" } '
	    	        Key customerKey = KeyFactory.createKey("Customer", uuid);
	    	        
	    	        	//Date date = new Date();
	                    Entity customer = new Entity("Customer", customerKey);
	                   /* customer.setProperty("name", username);
	                    customer.setProperty("email", emailid);
	                    customer.setProperty("uuid",uuid);*/
	                    
	                    uuid = getUuid();
	                    
	                    
	                    customer.setProperty("name", customerObj.getUsername());
	                    customer.setProperty("email", customerObj.getEmailid());
	                    customer.setProperty("uuid",uuid);
	                  // customer.setProperty("date", Date);
	                    
	                 
	                    
	                    
	                    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	                    datastore.put(customer); 
	           
	                   //list(request, response);
	            
	    	}
	    		
	  
	 
	  
	  
	  
	  
	  
	  
	    	
	    	    	
	    	

	    	
	    	
	    	public void update(HttpServletRequest request, HttpServletResponse response, Customer customerObj) throws IOException {

	    		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    		 
	    		String name = customerObj.getUsername();
	    		String email = customerObj.getEmailid();
	    		String uuid =  customerObj.getUuid();
	    		
	    		System.out.println("Update values " + name + " " + email + " " + uuid);
	    		
	    		Query query = new Query("Customer");
	    		query.addFilter("uuid", FilterOperator.EQUAL, uuid);
	    		PreparedQuery pq = datastore.prepare(query);
	    		Entity customer = pq.asSingleEntity();
	    		
	    		customer.setProperty("name", name);
	    		customer.setProperty("email", email);
	    		
	    		datastore.put(customer);   
	    		
	    		//list(request, response);
	                 
	            
	    	}
	    		
	    	
	    	public void delete(HttpServletRequest request, HttpServletResponse response, Customer customerObj) throws IOException {

	                    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	                    
	                   
	            
	                    Query query = new Query("Customer");
	                    query.addFilter("uuid", FilterOperator.EQUAL, customerObj.getUuid());
	                    PreparedQuery pq = datastore.prepare(query);
	                    Entity customer = pq.asSingleEntity();
	    		
	                    datastore.delete(customer.getKey());  
	                    
	                   list(request, response);
	                   
	            
	    	}

	    	
	    	
	    	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    		
	    		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    		 Query q = new Query("Customer");
	    	      PreparedQuery pq = datastore.prepare(q);
	    	      PrintWriter out = response.getWriter();
	    	     // out.println("<form action = '/hello' method='GET'>");
	    	          	      
	    	      
	    	     
	    	      
	    	      List<Customer> Customers = new LinkedList<Customer>();
	    	     	    	      
	    	      for (Entity result : pq.asIterable())
	    	      {
	    	    	  username = result.getProperty("name").toString();
	    	    	  emailid = result.getProperty("email").toString();
	    	    	  uuid = result.getProperty("uuid").toString();
	    	    	 // Date = result.getProperty(Date).toString();
	    	    	  
	    	    	  System.out.println("While listing " + username + " " + emailid + " " + uuid);
	    	    	  
	    	    	  Customer customer =  getCustomer();
	    	    	  Customers.add(customer);
	    	      }
	    	      
	    	      ObjectMapper mapper = new ObjectMapper();
	    	      
	    	      response.setContentType("application/json");  
	    	      //mapper.writeValue(out, Customers);
	    	      String jsonStr = mapper.writeValueAsString(Customers);
	    	     out.print(jsonStr);      
	    	      
	    	      
	    	      

	    	}
	    	    
	    	
	    	
	    	 Customer getCustomer(){
	    	      Customer customer = new Customer();
	    	      customer.setUsername(username);
	    	      customer.setEmailid(emailid);  
	    	      customer.setUuid(uuid); 
	    	      //customer.setDate(Date);
	    	      return customer;
	    	
	    	}

	  }



