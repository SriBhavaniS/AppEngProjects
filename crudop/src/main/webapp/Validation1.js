/**
 * 
 */
function formValidate() { 
	
	
	var flag = true;
    var username = $('input[id=username]').val();
    
    var emailid  = $('input[id=emailid]').val();
    
    
   
    //alert('Hi ' + username + emailid);
   
    if(username ==""){ 
    	alert("Please enter username");
    	$('input[id=username]').css('border-color','red');    	
        flag = false;
    }
    
 
    
    if(emailid==""){ 
    	alert("Please enter email address");
    	$('input[id=emailid]').css('border-color','red');     	
        flag = false;
    } 
    
   
    
    if(flag == false){
    	return false;
    }  	
    else    	
    return true;
}
    

