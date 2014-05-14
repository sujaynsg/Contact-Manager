package com.contactmanager.response;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BadRequestException extends WebApplicationException

{
	private static final long serialVersionUID = 1L;

	public BadRequestException(ContactManagerError error) {
		super(Response.status(Status.NOT_FOUND)
				.type(MediaType.APPLICATION_JSON).entity(
						new GenericEntity<ContactManagerError>(error) {
						}).build());
	}

}
