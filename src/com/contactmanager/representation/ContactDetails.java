package com.contactmanager.representation;

import org.codehaus.jackson.annotate.JsonProperty;

public class ContactDetails {

	@JsonProperty("contact_id")
	int contactId;
	@JsonProperty("first_name")
	String firstName;
	@JsonProperty("last_name")
	String lastName;
	@JsonProperty("physical_address")
	String physicalAddress;
	@JsonProperty("phone_number")
	long phoneNumber;
	@JsonProperty("email_id")
	String emailId;

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
