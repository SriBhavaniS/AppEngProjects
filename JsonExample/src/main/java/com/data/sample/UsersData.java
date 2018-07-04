package com.data.sample;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class UsersData extends HttpServlet {

 
  static String username;
  
  static String emailid;
    
  	

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
  throws IOException {
  	  DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
  	       
        
             
       
   username = request.getParameter("username");
       
   emailid = request.getParameter("emailid");
        
  //age = Integer.parseInt(request.getParameter("age"));
        
        
       
   Entity user = new Entity("User");
        
  user.setProperty("Username", username);
      
   // user.setProperty("Age", age);
        
  user.setProperty("Emailid", emailid);
        
        
      
    //String uname = user.getProperty("Username").toString();
        
       
   ds.put(user);
        
       
   //user.getProperty(emailid);
     
    // user.getProperty(age);
        
       
   //com.google.appengine.api.datastore.Key key =KeyFactory.createKey("User",415);
     
    // System.out.println("the key value is :" + key);
        
      
   /* try {
        ds.get(key);
        }
  catch(EntityNotFoundException e5) {
      	 
   e5.printStackTrace();
      	  
        }*/
        
        
        
  Query q = new Query("User"); 
       
   PreparedQuery pq = ds.prepare(q);
      
    PrintWriter out = response.getWriter();
        
  out.println("<form action = '/hello' method='POST'>");
       
   out.println("<table border='1'>");
       
   for (Entity result : pq.asIterable())
        { 	
      	  		  
    
   out.println("<tr><td>" + result.getProperty("Username") + "</td><td>" + result.getProperty("Emailid") + "</td><td><input type='button' name='edit' value='edit'></td></tr>");  
      
    }
        
      
    out.println("</table></form>");
        
       
   JsonObjectMapper.writeJSON();
        
  //System.out.print(uname);
        
        
  //com.google.appengine.api.datastore.Key key1 = new KeyFactory.Builder("User","Greatest").addChild("User","Greater").addChild("User","Great").getKey();
       
    //Entity emp=new Entity("employee",user.getKey());
        
        
   
   }
    
    
    
  static User getUser(){
      
    User user = new User();
       
   user.setUsername(username);
       
   //user.setAge(age);
      
    user.setEmailid(emailid);  
       
   return user;

  }
    
    
    

        
      
