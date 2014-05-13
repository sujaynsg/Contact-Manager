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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.contactmanager.dao.ContactManagerDaoFactory;
import com.contactmanager.representation.ContactDetails;
import com.contactmanager.response.ContactManagerResponse;
import com.contactmanager.response.ContactManagerResponseAll;
import com.contactmanager.services.ContactManagerServices;


@Path("contacts")
public class ContactManager {

	Connection connection = ContactManagerDaoFactory.createconnection();

	ContactDetails contactDetail = new ContactDetails();
	ContactManagerResponse contactResponse = new ContactManagerResponse();
	ContactManagerResponseAll contactResponseAll = new ContactManagerResponseAll();
	ContactManagerServices contactManagerServices = new ContactManagerServices();

	ArrayList<ContactDetails> contactDetails = new ArrayList();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		contactDetails = contactManagerServices.allcontactdetails(connection);
		if (contactDetails.get(0).getContactId() != 0) {
			contactResponseAll.setCode(1);
			contactResponseAll.setMessage("Displaying All Contacts");
			contactResponseAll.setContactDetails(contactDetails);
			
		}
		return Response.status(Status.OK).entity(new GenericEntity<ContactManagerResponseAll>(contactResponseAll){}).build();
	}

	@Path("/{contactid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("contactid")
	int contactid) {
		contactDetail.setContactId(contactid);
		contactDetail = contactManagerServices.getContact(contactDetail,
				connection);
		if(contactDetail.getContactId() != 0) {
			contactResponse.setCode(1);
			contactResponse.setMessage("Dislaying Contact Details");
			contactResponse.setContactDetail(contactDetail);
		}
		return Response.status(Status.OK).entity(new GenericEntity<ContactManagerResponse>(contactResponse){}).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createnew(@FormParam("firstname")
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
		int contactId = contactDetail.getContactId();
		if(contactId != 0 || contactId != -1) {
			contactResponse.setCode(1);
			contactResponse.setMessage("Contact Added Successfully");
			contactResponse.setContactDetail(contactDetail);
		}
		return Response.status(Status.CREATED).entity(new GenericEntity<ContactManagerResponse>(contactResponse){}).build();
	}

	@Path("/{contactid}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("contactid")
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
		if(contactDetail.getContactId() != 0) {
			contactResponse.setCode(1);
			contactResponse.setMessage("Updated Contact Details");
			contactResponse.setContactDetail(contactDetail);
		}
		return Response.status(Status.OK).entity(new GenericEntity<ContactManagerResponse>(contactResponse){}).build();


	}

	@Path("/{contactid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("contactid")
	int contactId) {
		contactDetail.setContactId(contactId);
		contactDetail = contactManagerServices.deleteContact(contactDetail,
				connection);
		if(contactDetail.getContactId() != 0) {
			contactResponse.setCode(1);
			contactResponse.setMessage("Deleted Contact Details");
			contactResponse.setContactDetail(contactDetail);
		}
		return Response.status(Status.OK).entity(new GenericEntity<ContactManagerResponse>(contactResponse){}).build();


	}

}
