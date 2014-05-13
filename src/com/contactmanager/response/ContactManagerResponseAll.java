package com.contactmanager.response;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.contactmanager.representation.ContactDetails;

public class ContactManagerResponseAll {
	
	
	int code;
	String message;
	@JsonProperty("contact_details")
	ArrayList<ContactDetails> contactDetails = new ArrayList<ContactDetails>();
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<ContactDetails> getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(ArrayList<ContactDetails> contactDetails) {
		this.contactDetails = contactDetails;
	}
	
	

}
