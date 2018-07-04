/**
 * 
 */

function formValidate() { 
	
	
	var flag = true;
    var username = $('input[id=usernametxt]').val();    
    var emailid  = $('input[id=emailidtxt]').val();    
   
   
    if(username ==""){ 
    	alert("Please enter username");
    	$('input[id=usernametxt]').css('border-color','red');    	
        flag = false;
    }
    
 
    
    var atPosition=emailid.indexOf("@");
    var dotPosition=emailid.lastIndexOf(".");
    
    if (atPosition <1 || dotPosition < atPosition+2 || dotPosition+2 >= emailid.length)
      {
    	alert("Not a valid e-mail address");     
      
    	$('input[id=emailidtxt]').css('border-color','red');     	
        flag = false;
      }
    
   
    
    if(flag == false){
    	return false;
    }  	
    else    	
    return true;
}
    

