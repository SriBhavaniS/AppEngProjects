/**
 * 
 */
var formAct;
var username;
var emailid;
var uuid;
formAct = "add";


function editCustomer(username, emailid, uuid) {	
	
	//alert(' Edited values ' + username + emailid + uuid);
	myform.username.value = username;
	myform.emailid.value = emailid;
	formAct = "edit";
	
	
}


function deleteCustomer(username, emailid, uuid) {	
	
	//alert(' Delete values ' + username + emailid + uuid);							
		  
		        
		        //below we convert the formData to json format
			//var formData =  "{\"uuid\"" + ": \"" + uuid + "\"}" ;
			   var formData =  "{\"username\"" + ": \"" + username + "\", \"emailid\"" + ": \"" + emailid + "\", \"uuid\"" + ": \"" + uuid + "\"}";
			                     
			                     
			   //var formData =  "{\"username\"" + ": \"" + $('input[name=username]').val() + "\", \"emailid\"" + ": \"" + $('input[name=emailid]').val() + "\"}" ;
	        
		        //var formDataString = JSON.Stringify(formData); // This is not woking. External json file may be needed
		        //var formDataString = formData.toString();
		       //alert(formData + ' Hi');
		        	
		        $.ajax({
		            type        : 'DELETE', 
		            url         : '/hello', 
		            data        :  formData,
		            dataType    : 'JSON',      // this is for response type
		            contentType: 'application/json; charset=utf-8',     //this is for request type
		            encode      : true
		        })
		           
		            .done(function(data) {

		                //alert('Done');
		                //console.log(data); 
						//getList();
		               
		            });

		  }
				
				
	
	



function formAxn()
{
	if(formAct == "add")
	{
		postData();
	}
	
	if(formAct == "edit")		
	{
		//alert('Calling function for PUT');
		editData();
	}	

}

	function getList() {	
				
		$.getJSON("/hello?axn=list", function(data) {
			var customer_data = "";
			$.each(data, function(key, value) {
				
				
				
				username = value.username;
				emailid = value.emailid;
				uuid = value.uuid;
				
								
				customer_data += '<tr>'				
				customer_data += '<td>' + username + '</td>'
				customer_data += '<td>' + emailid + '</td>'		
				//customer_data += '<td>' + uuid + '</td>'	
				
				customer_data += '<td>' + '<input type=button id=' +  uuid + ' value=Edit  onclick=editCustomer('
				customer_data += "'" + username + "','" + emailid + "','" + uuid + "');" 
				customer_data +=  '></td>'
				
					customer_data += '<td>' + '<input type=button id=' +  uuid + ' value=Delete  onclick=deleteCustomer('
					customer_data += "'" + username + "','" + emailid + "','" + uuid + "');" 
					customer_data +=  '></td>'			
				
				
				customer_data += '<input type=hidden id=' + uuid + ' name=uuid' + ' value=' + uuid + '>'
				customer_data += '</tr>'
				//alert(customer_data);

			});
			$('#customers_table').append(customer_data);

		});
	}
		

		
		
	function postData() {
		
		
				if(formValidate() == true)   // If form validation is true then execute the following
		{
								
		        
		        //below we convert the formData to json format
		   var formData =  "{\"username\"" + ": \"" + $('input[name=username]').val() + "\", \"emailid\"" + ": \"" + $('input[name=emailid]').val() + "\"}" ;

		        
		        //var formDataString = JSON.Stringify(formData); // This is not woking. External json file may be needed
		        
		        	
		        $.ajax({
		            type        : 'POST', 
		            url         : '/hello', 
		            data        :  formData,
		            dataType    : 'JSON',      // this is for response type
		            contentType: 'application/json; charset=utf-8',     //this is for request type
		            encode      : true
		        })
		           
		            .done(function(data) {

		                //alert('Done');
		                //console.log(data); 
						getList();   //Again display the list
		               
		            });

		        }
				
				event.preventDefault();	// prevent default action of the event - here Form submit
	}
	
	
	
	
	function editData() {
		
		//alert('Inside editdata');
		
		if(formValidate() == true)   // If form validation is true then execute the following
{
						
        //var formData =  {'username' : $('input[name=username]').val(), 'emailid' : $('input[name=emailid]').val() };
        
        //below we convert the formData to json format
   		var formData =  "{\"username\"" + ": \"" + $('input[name=username]').val() + "\", \"emailid\"" + ": \"" + $('input[name=emailid]').val() + "\", \"uuid\"" + ": \"" + $('input[name=uuid]').val() + "\"}" ;

        
        //var formDataString = JSON.Stringify(formData); // This is not woking. External json file may be needed
        //var formDataString = formData.toString();
       //alert(formData + ' Hi');
        	
        $.ajax({
            type        : 'PUT', 
            url         : '/hello', 
            data        :  formData,
            dataType    : 'JSON',      // this is for response type
            contentType: 'application/json; charset=utf-8',     //this is for request type
            encode      : true
        })
           
            .done(function(data) {

                //alert('Done');
                //console.log(data); 
				//getList();
               
            });

        }
		
		event.preventDefault();	// prevent default action of the event - here Form submit
}
	
	
	


