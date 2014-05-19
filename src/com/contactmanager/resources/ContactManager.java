package com.contactmanager.resources;

import java.awt.Image;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.contactmanager.representation.ContactPhoto;
import com.contactmanager.response.BadRequestException;
import com.contactmanager.response.ConflictRequestException;
import com.contactmanager.response.ContactManagerError;
import com.contactmanager.response.ContactManagerResponse;
import com.contactmanager.response.ContactManagerResponseAll;
import com.contactmanager.services.ContactManagerServices;
import com.sun.jersey.multipart.FormDataParam;

@Path("contacts")
public class ContactManager {

	Connection connection = ContactManagerDaoFactory.createconnection();

	ContactDetails contactDetail = new ContactDetails();
	ContactManagerResponse contactResponse = new ContactManagerResponse();
	ContactManagerResponseAll contactResponseAll = new ContactManagerResponseAll();
	ContactManagerServices contactManagerServices = new ContactManagerServices();
	ContactManagerError contactManagerError = new ContactManagerError();
	ContactPhoto contactPhoto = new ContactPhoto();

	ArrayList<ContactDetails> contactDetails = new ArrayList();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		contactDetails = contactManagerServices.allcontactdetails(connection);
		if (contactDetails.get(0).getContactId() != 0) {
			contactResponseAll.setCode(200);
			contactResponseAll.setMessage("Displaying All Contacts");
			contactResponseAll.setContactDetails(contactDetails);
			return Response.status(Status.OK).entity(
					new GenericEntity<ContactManagerResponseAll>(
							contactResponseAll) {
					}).build();
		} else {
			contactManagerError.setCode(404);
			contactManagerError.setMessage("No data avaliable!!!");
			throw new BadRequestException(contactManagerError);
		}
	}

	@Path("/{contactid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("contactid")
	int contactid) {
		contactDetail.setContactId(contactid);
		contactDetail = contactManagerServices.getContact(contactDetail,
				connection);
		if (contactDetail.getContactId() != 0) {
			contactResponse.setCode(200);
			contactResponse.setMessage("Dislaying Contact Details");
			contactResponse.setContactDetail(contactDetail);
			return Response.status(Status.OK).entity(
					new GenericEntity<ContactManagerResponse>(contactResponse) {
					}).build();
		} else {
			contactManagerError.setCode(404);
			contactManagerError.setMessage("No data avaliable for Contact Id:"
					+ contactid);
			throw new BadRequestException(contactManagerError);
		}

	}

	@Path("/{contactid}/photo")
	@GET
	@Produces("image/jpeg")
	// @Produces({"image/png", "image/jpeg", "image/gif"})
	public Response findphoto(@PathParam("contactid")
	int contactid) {
		contactDetail.setContactId(contactid);
		contactPhoto = contactManagerServices.getContactPhoto(contactDetail,
				contactPhoto, connection);
		if (contactPhoto.getUploadedPhoto() != null) {
			contactResponse.setCode(200);
			contactResponse.setMessage("Dislaying Contact Details");
			contactResponse.setContactDetail(contactDetail);
			return Response.status(Status.OK).entity(
					new GenericEntity<InputStream>(contactPhoto
							.getUploadedPhoto()) {
					}).build();
		} else {
			contactManagerError.setCode(404);
			contactManagerError.setMessage("No photo avaliable for Contact Id:"
					+ contactid);
			throw new BadRequestException(contactManagerError);
		}

	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	// @Produces("image/jpeg")
	public Response createnew(@FormDataParam("firstname")
	String firstName, @FormDataParam("lastname")
	String lastName, @FormDataParam("address")
	String physicalAddress, @FormDataParam("phonenumber")
	long phoneNumber, @FormDataParam("emailid")
	String emailId, @FormDataParam("photo")
	InputStream uploadedPhoto) {
		contactDetail.setFirstName(firstName);
		contactDetail.setLastName(lastName);
		contactDetail.setPhysicalAddress(physicalAddress);
		contactDetail.setPhoneNumber(phoneNumber);
		contactDetail.setEmailId(emailId);
		contactPhoto.setUploadedPhoto(uploadedPhoto);
		if (firstName.isEmpty()) {
			contactManagerError.setCode(404);
			contactManagerError
					.setMessage("You might missed  First Name which is a mandatory fields");
			throw new BadRequestException(contactManagerError);

		} else if (phoneNumber == 0) {
			contactManagerError.setCode(404);
			contactManagerError
					.setMessage("You might missed  Phone Number which is a mandatory fields");
			throw new BadRequestException(contactManagerError);

		}

		else {
			contactPhoto = contactManagerServices.addContact(contactDetail,
					contactPhoto, connection);
			int contactId = contactDetail.getContactId();
			if (contactId == -1) {
				contactManagerError.setCode(409);
				contactManagerError
						.setMessage("Your given phone number has been already registered. Please use different Phone Number");
				throw new ConflictRequestException(contactManagerError);
			} else if (contactId == 0) {
				contactManagerError.setCode(404);
				contactManagerError
						.setMessage("Issues in Database, Data has not been inserted properly!!!");
				throw new BadRequestException(contactManagerError);

			} else {
				contactResponse.setCode(201);
				contactResponse.setMessage("Contact Added Successfully");
				contactResponse.setContactDetail(contactDetail);
				return Response.status(Status.CREATED).entity(
						new GenericEntity<ContactManagerResponse>(
								contactResponse) {
						}).build();

			}
		}

	}

	@Path("/{contactid}")
	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("contactid")
	int contactid, @FormDataParam("firstname")
	String firstName, @FormDataParam("lastname")
	String lastName, @FormDataParam("address")
	String physicalAddress, @FormDataParam("phonenumber")
	long phoneNumber, @FormDataParam("emailid")
	String emailId, @FormDataParam("photo")
	InputStream uploadedPhoto) {
		contactDetail.setContactId(contactid);
		contactDetail.setFirstName(firstName);
		contactDetail.setLastName(lastName);
		contactDetail.setPhysicalAddress(physicalAddress);
		contactDetail.setPhoneNumber(phoneNumber);
		contactDetail.setEmailId(emailId);
		contactPhoto.setUploadedPhoto(uploadedPhoto);
		contactDetail = contactManagerServices.updateContact(contactDetail,
				contactPhoto, connection);
		int contactId = contactDetail.getContactId();
		if (contactId == -1) {
			contactManagerError.setCode(409);
			contactManagerError
					.setMessage("Your given phone number has been already registered. Please use different Phone Number");
			throw new ConflictRequestException(contactManagerError);
		} else if (contactId == 0) {
			contactManagerError.setCode(404);
			contactManagerError.setMessage("No data avaliable for Contact Id:"
					+ contactid);
			throw new BadRequestException(contactManagerError);

		} else if (contactId == -2) {
			contactManagerError.setCode(404);
			contactManagerError
					.setMessage("Issues in Database, Data has not been updated properly!!!");
			throw new BadRequestException(contactManagerError);
		} else {
			contactResponse.setCode(200);
			contactResponse.setMessage("Updated Contact Details");
			contactResponse.setContactDetail(contactDetail);
			return Response.status(Status.OK).entity(
					new GenericEntity<ContactManagerResponse>(contactResponse) {
					}).build();
		}
	}

	@Path("/{contactid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("contactid")
	int contactid) {
		contactDetail.setContactId(contactid);
		contactDetail = contactManagerServices.deleteContact(contactDetail,
				connection);
		int contactId = contactDetail.getContactId();
		if (contactId == 0) {
			contactManagerError.setCode(404);
			contactManagerError.setMessage("No data avaliable for Contact Id:"
					+ contactid);
			throw new BadRequestException(contactManagerError);

		} else if (contactId == -2) {
			contactManagerError.setCode(404);
			contactManagerError
					.setMessage("Issues in Database, Data has not been updated properly!!!");
			throw new BadRequestException(contactManagerError);
		} else {
			contactResponse.setCode(200);
			contactResponse.setMessage("Deleted Contact Details");
			contactResponse.setContactDetail(contactDetail);
			return Response.status(Status.OK).entity(
					new GenericEntity<ContactManagerResponse>(contactResponse) {
					}).build();
		}

	}

}
