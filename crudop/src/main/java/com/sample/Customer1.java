package com.sample;



	import java.util.Date;
	import java.util.UUID;

	//  using Customer class to get the values from datastore and load the values to Customer object by set methods

	public class Customer1 {
		
		
		private String emailid;
		private String username;
		private String uuid;
		private Date createddate ;
	//private String  uuid = UUID.randomUUID().toString();
				
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid= emailid;
	}

	public String getUsername(){
		return username;

	}
	public void setUsername(String username){
		this.username=username;
	}

	public String getUuid(){
		return uuid;

	}

	public void setUuid(String uuid){
		this.uuid = uuid;

	}
	public Date getdate() {
		return createddate;
}
	public void setdate(Date createddate) {
		this.createddate = createddate;
	}


		
	}



