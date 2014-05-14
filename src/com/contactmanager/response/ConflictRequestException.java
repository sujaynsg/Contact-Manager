package com.contactmanager.response;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ConflictRequestException extends WebApplicationException

{
	private static final long serialVersionUID = 1L;

	public ConflictRequestException(ContactManagerError contactManagerError) {
		super(Response.status(Status.CONFLICT).type(MediaType.APPLICATION_JSON)
				.entity(
						new GenericEntity<ContactManagerError>(
								contactManagerError) {
						}).build());
	}

}