package com.contactmanager.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.contactmanager.representation.ContactDetails;

public class ContactManagerServices {

	public ArrayList<ContactDetails> allcontactdetails(Connection connection) {

		ArrayList<ContactDetails> contacts = new ArrayList();

		ResultSet rs = null;
		try {
			int flag = 0;
			Statement statement = connection.createStatement();
			String squery = "select * from contactregister;";
			rs = statement.executeQuery(squery);
			while (rs.next()) {
				ContactDetails contactDetail = new ContactDetails();
				contactDetail.setContactId(rs.getInt(1));
				contactDetail.setFirstName(rs.getString(2));
				contactDetail.setLastName(rs.getString(3));
				contactDetail.setPhysicalAddress(rs.getString(4));
				contactDetail.setPhoneNumber(rs.getLong(5));
				contactDetail.setEmailId(rs.getString(6));
				contacts.add(contactDetail);
			}
		} catch (SQLException sqle) {

		} catch (Exception e) {

		}
		return contacts;

	}

	public ContactDetails getContact(ContactDetails contactDetail,
			Connection connection) {
		// TODO Auto-generated method stub

		ResultSet rs = null;
		try {
			int contactId = contactDetail.getContactId();
			Statement statement = connection.createStatement();
			String squery = "select * from contactregister where contactid = '"
					+ contactId + "';";
			rs = statement.executeQuery(squery);
			if (rs.next()) {
				/*
				 * return "<h2>Details of Employee # " + employeeId + " </h2><p><h3>Employee
				 * name: " + rs.getString(2) + "</h3>";
				 */
				contactDetail.setFirstName(rs.getString(2));
				contactDetail.setLastName(rs.getString(3));
				contactDetail.setPhysicalAddress(rs.getString(4));
				contactDetail.setPhoneNumber(rs.getLong(5));
				contactDetail.setEmailId(rs.getString(6));
			} else {
				contactDetail.setContactId(0);
			}

		} catch (SQLException sqle) {

		} catch (Exception e) {

		}
		return contactDetail;

	}

	public ContactDetails addContact(ContactDetails contactDetail,
			Connection connection) {
		// TODO Auto-generated method stub
		try {
			int flag = 0;
			String firstName = contactDetail.getFirstName();
			String lastName = contactDetail.getLastName();
			String physicalAddress = contactDetail.getPhysicalAddress();
			long phoneNumber = contactDetail.getPhoneNumber();
			String emailId = contactDetail.getEmailId();
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select phonenumber from contactregister");
			while (rs.next()) {
				if (phoneNumber == rs.getLong(1)) {
					flag = 1;
				}
			}
			if (flag == 0) {
				String squery = "insert into contactregister(firstname,lastname,physicaladdress,phonenumber,emailid) values('"
						+ firstName
						+ "', '"
						+ lastName
						+ "', '"
						+ physicalAddress
						+ "', '"
						+ phoneNumber
						+ "', '"
						+ emailId + "');";
				System.out.println(squery);
				int a = statement.executeUpdate(squery);
				ResultSet rs1 = statement
						.executeQuery("select contactid from contactregister where phonenumber = '"
								+ phoneNumber + "'");
				if (rs1.next()) {
					contactDetail.setContactId(rs1.getInt(1));
				}
				return contactDetail;
			} else {
				return contactDetail;
			}

		} catch (SQLException sqle) {

		} catch (Exception e) {

		}
		return contactDetail;
	}

	public ContactDetails updateContact(ContactDetails contactDetail,
			Connection connection) {
		// TODO Auto-generated method stub

		try {
			int contactId = contactDetail.getContactId();
			String firstName = contactDetail.getFirstName();
			String lastName = contactDetail.getLastName();
			String physicalAddress = contactDetail.getPhysicalAddress();
			long phoneNumber = contactDetail.getPhoneNumber();
			String emailId = contactDetail.getEmailId();
			Statement statement = connection.createStatement();
			String squery = "update contactregister(firstname,lastname,physicaladdress,phonenumber,emailid) set firstname = '"
					+ firstName
					+ "',lastname = '"
					+ lastName
					+ "',physicaladdress = '"
					+ physicalAddress
					+ "',phonenumber = '"
					+ phoneNumber
					+ "',emailid = '"
					+ emailId + "' where contactid ='" + contactId + "';";
			int a = statement.executeUpdate(squery);

			if (a == 0) {

				return contactDetail;
			} else {
				return contactDetail;
			}

		} catch (SQLException sqle) {

		} catch (Exception e) {

		}
		return contactDetail;

	}

	public ContactDetails deleteContact(ContactDetails contactDetail,
			Connection connection) {

		try {
			contactDetail = getContact(contactDetail, connection);
			int contactId = contactDetail.getContactId();
			Statement statement = connection.createStatement();
			String squery = "delete from contactregister where contactid ='"
					+ contactId + "';";
			int a = statement.executeUpdate(squery);

		} catch (SQLException sqle) {

		} catch (Exception e) {
			System.out.println("Exception occured in Employeeserivce class");

		}
		return contactDetail;

	}

}