package com.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

//  using Customer class to get the values from datastore and load the values to Customer object by set methods

public class Customer {
	
	
	private String emailid;
	private String username;
	private String uuid;
	private String createdDate ;
	private String lastUpdated;
	private ArrayList<String> mobiles;
	private ArrayList<String> landlines;
	private HashMap<String, Map<String, String>>phones;
	//private Boolean deleted;
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

public String getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(String createdDate) {
	
	this.createdDate = createdDate;
}

public String getLastUpdated() {
	return lastUpdated;
}
	
public void setLastUpdated(String lastUpdated) {
	this.lastUpdated = lastUpdated;
	
}
/*public List getMobiles() {
	return mobiles;
}
public void setMobiles(ArrayList<String> mobileNumbers) {
	this.mobiles = mobileNumbers;
}
public List getLandlines() { 
	return landlines;
	
}
public void setLandlines(ArrayList<String> landlineNumbers) {
	this.landlines =landlineNumbers;
}*/

public HashMap<String, Map<String, String>> getPhones()
{
	return phones;
}

public void setPhones(HashMap<String, Map<String, String>> phones)
{
	this.phones = phones;
}


}



