import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class HelloAppEngine extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
		  throws IOException {
		        
		      response.setContentType("text/plain");
		      
		  response.setCharacterEncoding("UTF-8");

		   
		      
		    
		    StringBuilder buffer = new StringBuilder();
		     
		   BufferedReader reader = request.getReader();
		     
		   String line;
		     
		   while ((line = reader.readLine()) != null) {
		         
		   buffer.append(line);
		     
		   }
		     
		   String data = buffer.toString();
		      
		  response.getWriter().print(data);
		      
		      

		    }


		  }