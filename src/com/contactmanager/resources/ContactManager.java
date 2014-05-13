package com.contactmanager.resources;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.contactmanager.dao.ContactManagerDaoFactory;
import com.contactmanager.representation.ContactDetails;
import com.contactmanager.services.ContactManagerServices;

@Path("contacts")
public class ContactManager {

	Connection connection = ContactManagerDaoFactory.createconnection();

	ContactDetails contactDetail = new ContactDetails();
	ContactManagerServices contactManagerServices = new ContactManagerServices();

	ArrayList<ContactDetails> contacts = new ArrayList();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ContactDetails> list() {
		contacts = contactManagerServices.allcontactdetails(connection);
		return contacts;
	}

	@Path("/{contactid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ContactDetails find(@PathParam("contactid")
	int contactid) {
		contactDetail.setContactId(contactid);
		contactDetail = contactManagerServices.getContact(contactDetail,
				connection);

		return contactDetail;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ContactDetails createnew(@FormParam("firstname")
	String firstName, @FormParam("lastname")
	String lastName, @FormParam("address")
	String physicalAddress, @FormParam("phonenumber")
	long phoneNumber, @FormParam("emailid")
	String emailId) {

		contactDetail.setFirstName(firstName);
		contactDetail.setLastName(lastName);
		contactDetail.setPhysicalAddress(physicalAddress);
		contactDetail.setPhoneNumber(phoneNumber);
		contactDetail.setEmailId(emailId);
		contactDetail = contactManagerServices.addContact(contactDetail,
				connection);
		return contactDetail;
	}

	@Path("/{contactid}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public ContactDetails update(@PathParam("contactid")
	int contactid, @FormParam("firstname")
	String firstName, @FormParam("lastname")
	String lastName, @FormParam("address")
	String physicalAddress, @FormParam("phonenumber")
	long phoneNumber, @FormParam("emailid")
	String emailId) {
		contactDetail.setContactId(contactid);
		contactDetail.setFirstName(firstName);
		contactDetail.setLastName(lastName);
		contactDetail.setPhysicalAddress(physicalAddress);
		contactDetail.setPhoneNumber(phoneNumber);
		contactDetail.setEmailId(emailId);
		contactDetail = contactManagerServices.updateContact(contactDetail,
				connection);
		return contactDetail;

	}

	@Path("/{contactid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public ContactDetails delete(@PathParam("contactid")
	int contactId) {
		contactDetail.setContactId(contactId);
		contactDetail = contactManagerServices.deleteContact(contactDetail,
				connection);
		return contactDetail;

	}

}
