package com.contactmanager.response;

import java.util.ArrayList;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.annotate.JsonProperty;

import com.contactmanager.representation.ContactDetails;

public class ContactManagerResponse {

	int code;
	String message;
	@JsonProperty("contact_detail")
	ContactDetails contactDetail = new ContactDetails();

	public ContactDetails getContactDetail() {
		return contactDetail;
	}

	public void setContactDetail(ContactDetails contactDetail) {
		this.contactDetail = contactDetail;
	}

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

}
