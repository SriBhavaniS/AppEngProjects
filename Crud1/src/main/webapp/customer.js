/**
 * 
 */



var formAct;
var username;
var emailid;
var uuid;
var createdDate;
var lastUpdated;
var phones;

var landBtnCnt = 1;
var mobileBtnCnt = 1;
formAct = "add";



$(document).ready(function(){
    
    $(document).on("click", "button" , function() {
    	
    	event.preventDefault();
     
    	var buttonId = $(this).attr("id");    	
    	var buttonName = $(this).attr("name");    	
    	//alert('Clicked button name  ' + buttonName);
    	//alert('Clicked button id  ' + buttonId);    
    	
    	if(buttonName === "submit")
    		{
    			formAxn();
    		}
    	if(buttonName == "edit")    		
    		{
    			usernametxt.value = "";
    			emailidtxt.value = ""; 
    			uuidhidden.value = "";
    			//landlinenum.value = "";
    			//mobilenum.value = "";
    			
    			formAct = "edit";
    			getDataForEdit(buttonId);
    		}
    	if(buttonName == "delete")
    		{
    			deleteData(buttonId);
    		}
    	
    	if(buttonName == "btn-add-phone")
    	{
    		//alert('Add Phone button');
    		
    		var index = $('.phone-input').length + 1;
			
			$('.phone-list').append(''+
					'<div class="input-group phone-input">'+	
							'<select id="changeType">'+
								'<option value="phone">Phone</option>'+
								'<option value="fax">Fax</option>'+
								'<option value="mobile">Mobile</option>'+
							'</select>'+
						'<input type="hidden" name="phones['+index+'][type]" class="type-input" value="phone" />'+
						'<input type="text" name="phones['+index+'][number]" class="form-control" placeholder="" />'+	
							'<button name="btn-remove-phone" type="button">-</button>'+
					'</div>'
			);
    	}
    	
    	if(buttonName == "btn-remove-phone")
    	{
    		$(this).closest('.phone-input').remove();
    	}
    	
  
    });
    
});
    	
    	/*if(buttonName == "landlineadd")
		{    		
    		landBtnCnt = landBtnCnt + 1;
    		$('#landlinediv').append('<br>');
    	    $('#landlinediv').append('<input type=text id=landlinenum'+landBtnCnt + ' name=landlines[]>');
    	    $('#landlinediv').append('<button id='+ landBtnCnt + ' name=landlineremove>-</button>');
		}
    	if(buttonName == "mobileadd")
		{    		
    		mobileBtnCnt = mobileBtnCnt + 1;
    		$('#mobilediv').append('<br>');
    		$('#mobilediv').append('<input type=text id=mobilenum'+mobileBtnCnt + ' name=mobiles[]>');
    		$('#mobilediv').append('<button id='+mobileBtnCnt + ' name=mobileremove>-</button>');
		}
    	if(buttonName == "landlineremove")		    	
    	{    	    
    		$(this).remove();
    		$('#landlinenum'+buttonId).remove();
		}
    	if(buttonName == "mobileremove")		    	
    	{    	    
    		$(this).remove();
    		$('#mobilenum'+buttonId).remove();
		}*/
    	
    	
    	
		$(document).on("change", "select" , function(){    		
			//alert($(this).val());
			$(this).closest('.phone-input').find('.type-input').val($(this).val());
			//alert($(this).closest('.type-input').val());
		});
		
		
		
		
		
    	
    	
  







function getDataForEdit(buttonId) {	
	
	
	$.getJSON("/hello?axn=listSingle&uuid="+buttonId, function(data) {
							
		
		username = data.username;				
		emailid = data.emailid;
		uuid = data.uuid;		
		createdDate = data.createdDate;
		lastUpdated = data.lastUpdated;
		phones = data.phones;		
		
		usernametxt.value = username;
		emailidtxt.value = emailid;
		uuidhidden.value = uuid;
		
		$('.phone-list').empty();
		var index = 0;
		
		var HTMLStr = "";
		
		for (var i = 0, keys = Object.keys(phones), ii = keys.length; i < ii; i++) {		  
			  
			index = index + 1;
			
			  var phoneSet = phones[keys[i]];
			  	  
			 
				HTMLStr += '<div class="input-group phone-input">';
				
				
			 var valCnt = 1;
			  
			  for (var j = 0, keys1 = Object.keys(phoneSet), jj = keys1.length; j < jj; j++)
				  {
				  	//alert(phoneSet[keys1[j]]);
				  	if(valCnt == 1)
				  	{
				  		HTMLStr += '<input type=text name=phones[' +index + '][number] value=' + phoneSet[keys1[j]] + '>' ;
				  		HTMLStr += '<input type="hidden" name="phones['+ index +'][type]" class="type-input" value="phone" />';
				  	}	
				  	if(valCnt == 2)
				  	{
				  			HTMLStr += '<select id="changeType">';
				  			HTMLStr += '<option value=' + phoneSet[keys1[j]] + '>' + phoneSet[keys1[j]]  + '</option>';
				  			
				  			if(phoneSet[keys1[j]] == "phone")
				  			{
				  				HTMLStr += '<option value="mobile">Mobile</option>';
				  				HTMLStr += '<option value="fax">Fax</option>';
				  			}
				  			if(phoneSet[keys1[j]] == "mobile")
				  			{
				  				HTMLStr += '<option value="phone">Phone</option>';
				  				HTMLStr += '<option value="fax">Fax</option>';
				  			}
				  			if(phoneSet[keys1[j]] == "fax")
				  			{
				  				HTMLStr += '<option value="phone">Phone</option>';
				  				HTMLStr += '<option value="mobile">Mobile</option>';
				  			}
				  			HTMLStr +=	'</select>';
				  			
				  	}
				  	
				  	valCnt++;
				  }
			 
			  
			  
				
			  HTMLStr += '<button name="btn-remove-phone" type="button">-</button>'+
						'</div>';
		}
		
		$('.phone-list').append(HTMLStr);
		
	});
			  
}		  
	  
	
		
		
		
		
		
		
		
	/*	for (i = 1; i < data.landlines.length; i++) {
			
			$('#landlinediv').append('<br>');
    	    $('#landlinediv').append('Landline Number '+ i + ' <input type=text id=landlinenum'+i + ' name=landlines[] value='+ landlines[i] +' >');
    	    $('#landlinediv').append('<button id='+i + ' name=landlineremove>-</button>');
    	   		    
		}	
		
		$('#mobilediv').empty();
		
		for (i = 1; i < data.mobiles.length; i++) {
			
			$('#mobilediv').append('<br>');
    	    $('#mobilediv').append('Mobile Number '+ i + ' <input type=text id=mobilenum'+i + ' name=mobiles[] value='+ mobiles[i] +' >');
    	    $('#mobilediv').append('<button id='+i + ' name=mobileremove>-</button>');
    	   		    
		}	
		
		
		
	});	
		
	
} */


function deleteData(uuid) {						
		  
		        
		        // convert the formData to json format
				var formData =  "{\"uuid\"" + ": \"" + uuid + "\"}" ;			  
		        	
		        $.ajax({
		            type        : 'DELETE', 
		            url         : '/hello', 
		            data        :  formData,		            
		            contentType: 'application/json; charset=utf-8',     //this is for request type
		            encode      : true
		        })
		           
		            .done(function(data) {	
		            	
		            	//alert(data);
		            	window.location.reload();
		            	//$('#customers_table tr').remove();
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
				//alert(uuid);
				createdDate = value.createdDate;
				lastUpdated = value.lastUpdated;
				phones = value.phones;	
				
				//alert(phones);
				
					customer_data += '<tr>'	;			
					customer_data += '<td>' + username + '</td>';
					customer_data += '<td>' + emailid + '</td>';
					customer_data += '<td>' + createdDate + '</td>';
					customer_data += '<td>' + lastUpdated + '</td>';
					customer_data += '<td>';
					
				for (var i = 0, keys = Object.keys(phones), ii = keys.length; i < ii; i++) {					  
							  
							  var phoneSet = phones[keys[i]];
							  //alert(phoneVals);
							  
							  
							  for (var j = 0, keys1 = Object.keys(phoneSet), jj = keys1.length; j < jj; j++)
								  {
								   var phoneVals = phoneSet[keys1[j]];								   
								   customer_data += phoneVals;	
								   customer_data += ' ';  
								   //alert(phoneVals);
								  }
							  
							  customer_data += '<br>';
					  
					}
					
				 customer_data += '</td>';
				 customer_data += '<td>' + "<button id='" + uuid + "' name='edit'>Edit</button>"; 
				 customer_data += '<td>' + "<button id='" + uuid + "' name='delete'>Delete</button></td>";	
				 customer_data += '</tr>';			

			});
			$('#customers_table').append(customer_data);

		});
	}
		

		
		
	function postData() {
		
		
				if(formValidate() == true)   // If  validation is true then execute the following
		{
						
					var form = document.myform;
			        var jsonFormData = ConvertFormToJSON(form);		       
			        
			        var jsonFormDataStr = JSON.stringify(jsonFormData);
		        
		            alert(jsonFormDataStr);
		       		        		        	
		        $.ajax({
		            type        : 'POST', 
		            url         : '/hello', 
		            data        :  jsonFormDataStr,		            
		            contentType: 'application/json',     //this is for request type
		            encode      : true
		        }).done(function(data) {
		            
		        	/*$("input[type=text], textarea").val("");
		        		
		        	var unameRtn = data.username;
		        	var emailRtn = data.emailid;
		        	var createdDateRtn = data.createdDate;
		        	var uuidRtn = data.uuid;
		        	var landlinesRtn = data.landlines;
		        	var mobilesRtn = data.mobiles;
		
		        	
		        	var e = '<tr><td>' + unameRtn + '</td><td>' + emailRtn + '</td><td>' + createdDateRtn + '</td><td></td><td>' + landlinesRtn + '</td><td>' + mobilesRtn + '</td><td></td>';
		        	e += '<td>' + "<button id='" + uuidRtn + "' name='edit'>Edit</button>"; 
					e += '<td>' + "<button id='" + uuidRtn + "' name='delete'>Delete</button></td></tr>";	
		        	$('#customers_table').append(e);   */
		        	window.location.reload();
		  
		               
		            });
		        

		        }
				
			
				
				//event.preventDefault();	// prevent default action of the event - here Form submit
	}
	
	
	function ConvertFormToJSON(form){
	    /*var array = jQuery(form).serializeArray();
	    var json = {};
	    
	    jQuery.each(array, function() {
	        json[this.name] = this.value || '';
	    });*/
	    
		var json = $(form).serializeJSON();
	    return json;
	}
	
	
	
	function editData() {
		
		//alert('Inside editdata');
		
		if(formValidate() == true)   // If form validation is true then execute the following
{
					
			var form = document.myform;
	        var jsonFormData = ConvertFormToJSON(form);	                
	        var jsonFormDataStr = JSON.stringify(jsonFormData);		
		
	        alert("JSON in editData function " + jsonFormDataStr);
        	
        $.ajax({
            type        : 'PUT', 
            url         : '/hello', 
            data        :  jsonFormDataStr,            
            contentType: 'application/json; charset=utf-8',     //this is for request type
            encode      : true
        }).done(function(data) {
        	
        	/*usernametxt.value = "";
			emailidtxt.value = ""; 
			uuidhidden.value = "";
        		
        	var a = data.username;
        	var b = data.emailid;
        	var c = data.createdDate;
        	var d = data.uuid;        	
        	
        	var e = '<tr><td>' + a + '</td><td>' + b + '</td><td>' + c + '</td><td></td>';
        	e += '<td>' + "<button id='" + d + "' name='edit'>Edit</button>"; 
			e += '<td>' + "<button id='" + d + "' name='delete'>Delete</button></td></tr>";	
        	$('#customers_table').append(e);    */
        	
        	window.location.reload();
               
            });

        }
		
		//event.preventDefault();	// prevent default action of the event - here Form submit
}
	
	
	
	    


