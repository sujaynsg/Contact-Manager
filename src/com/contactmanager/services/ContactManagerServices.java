package com.contactmanager.services;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.contactmanager.representation.ContactDetails;
import com.contactmanager.representation.ContactPhoto;

public class ContactManagerServices {

	public ArrayList<ContactDetails> allcontactdetails(Connection connection) {

		ArrayList<ContactDetails> contactDetails = new ArrayList();

		ResultSet rs = null;
		try {
			int flag = 0;
			Statement statement = connection.createStatement();
			String squery = "select * from contactregister;";
			rs = statement.executeQuery(squery);
			while (rs.next()) {
				flag = 1;
				ContactDetails contactDetail = new ContactDetails();
				contactDetail.setContactId(rs.getInt(1));
				contactDetail.setFirstName(rs.getString(2));
				contactDetail.setLastName(rs.getString(3));
				contactDetail.setPhysicalAddress(rs.getString(4));
				contactDetail.setPhoneNumber(rs.getLong(5));
				contactDetail.setEmailId(rs.getString(6));
				contactDetails.add(contactDetail);
			}
			if (flag == 0) {
				ContactDetails contactDetail = new ContactDetails();
				contactDetail.setContactId(0);
				contactDetails.add(contactDetail);

			}
		} catch (SQLException sqle) {

		} catch (Exception e) {

		}
		return contactDetails;

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

	public ContactPhoto getContactPhoto(ContactDetails contactDetail,
			ContactPhoto contactPhoto, Connection connection) {
		// TODO Auto-generated method stub

		ResultSet rs = null;
		try {
			int contactId = contactDetail.getContactId();
			String filePath = "E:/Photos/" + contactId + ".jpg";
			System.out.println(filePath);
			final int BUFFER_SIZE = 4096;
			String squery = "SELECT contactphoto FROM contactregister WHERE contactid = '"
					+ contactId + "';";
			PreparedStatement statement1 = connection.prepareStatement(squery);
			ResultSet result = statement1.executeQuery();
			if (result.next()) {
				Blob blob = result.getBlob("contactphoto");
				InputStream inputStream = blob.getBinaryStream();
				OutputStream outputStream = new FileOutputStream(filePath);

				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				System.out.println("File saved");
				contactPhoto.setUploadedPhoto(inputStream);

			} else {
				contactPhoto.setUploadedPhoto(null);
			}

		} catch (SQLException sqle) {

		} catch (Exception e) {

		}
		return contactPhoto;

	}

	public ContactPhoto addContact(ContactDetails contactDetail,
			ContactPhoto contactPhoto, Connection connection) {
		// TODO Auto-generated method stub
		try {
			int flag = 0;
			String firstName = contactDetail.getFirstName();
			String lastName = contactDetail.getLastName();
			String physicalAddress = contactDetail.getPhysicalAddress();
			long phoneNumber = contactDetail.getPhoneNumber();
			String emailId = contactDetail.getEmailId();
			InputStream uploadedPhoto = contactPhoto.getUploadedPhoto();
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select phonenumber from contactregister");
			while (rs.next()) {
				if (phoneNumber == rs.getLong(1)) {
					flag = 1;
				}
			}

			if (flag == 0) {

				String sql = "INSERT INTO contactregister (firstname,lastname,physicaladdress,phonenumber,emailid,contactphoto) values (?,?,?,?,?,?)";
				PreparedStatement statement1 = connection.prepareStatement(sql);

				statement1.setString(1, firstName);
				statement1.setString(2, lastName);
				statement1.setString(3, physicalAddress);
				statement1.setLong(4, phoneNumber);
				statement1.setString(5, emailId);
				statement1.setBlob(6, uploadedPhoto);
				System.out.println(sql);
				int row = statement1.executeUpdate();

				ResultSet rs1 = statement
						.executeQuery("select contactid from contactregister where phonenumber = '"
								+ phoneNumber + "'");
				if (rs1.next()) {

					contactDetail.setContactId(rs1.getInt(1));
				} else {
					contactDetail.setContactId(0);
				}

			} else {
				contactDetail.setContactId(-1);
			}

		} catch (SQLException sqle) {

		} catch (Exception e) {

		}
		return contactPhoto;
	}

	public ContactDetails updateContact(ContactDetails contactDetail,
			ContactPhoto contactPhoto, Connection connection) {
		// TODO Auto-generated method stub

		try {
			int flag = 0;
			int contactId = contactDetail.getContactId();
			ContactDetails contactDetailTemp = new ContactDetails();
			contactDetailTemp.setContactId(contactId);
			contactDetailTemp = getContact(contactDetailTemp, connection);
			if (contactDetailTemp.getContactId() == 0) {

				contactDetailTemp.setContactId(0);
				return contactDetailTemp;

			} else {
				String firstName = contactDetail.getFirstName();
				String lastName = contactDetail.getLastName();
				String physicalAddress = contactDetail.getPhysicalAddress();
				long phoneNumber = contactDetail.getPhoneNumber();
				String emailId = contactDetail.getEmailId();
				InputStream uploadedPhoto = contactPhoto.getUploadedPhoto();
				Statement statement = connection.createStatement();
				ResultSet rs = statement
						.executeQuery("select phonenumber from contactregister where contactid <> '"
								+ contactId + "'");
				while (rs.next()) {
					if (phoneNumber == rs.getLong(1)) {
						System.out.println(phoneNumber + "," + rs.getLong(1));
						flag = 1;
					}
				}
				System.out.println(flag);
				if (flag == 0) {
					String sql = "update contactregister set firstname = ?,lastname = ?, physicaladdress = ?, phonenumber =?, emailid = ?, contactphoto = ? where contactid = ?";
					PreparedStatement statement1 = connection
							.prepareStatement(sql);

					statement1.setString(1, firstName);
					statement1.setString(2, lastName);
					statement1.setString(3, physicalAddress);
					statement1.setLong(4, phoneNumber);
					statement1.setString(5, emailId);
					statement1.setBlob(6, uploadedPhoto);
					statement1.setInt(7, contactId);
					System.out.println(sql);
					int a = statement1.executeUpdate();

					System.out.println(a);
					if (a > 0) {
						contactDetail.setContactId(contactId);
					} else if (a == 0) {
						contactDetail.setContactId(-2);
					}
				} else {
					contactDetail.setContactId(-1);
				}
			}

		} catch (SQLException sqle) {

		} catch (Exception e) {

		}
		return contactDetail;

	}

	public ContactDetails deleteContact(ContactDetails contactDetail,
			Connection connection) {

		try {
			int contactId = contactDetail.getContactId();
			contactDetail = getContact(contactDetail, connection);
			if (contactDetail.getContactId() == 0) {

				contactDetail.setContactId(0);
				return contactDetail;

			} else {

				Statement statement = connection.createStatement();
				String squery = "delete from contactregister where contactid ='"
						+ contactId + "';";
				int a = statement.executeUpdate(squery);
				if (a == 0) {
					contactDetail.setContactId(-2);
				} else {
					contactDetail.setContactId(contactId);
				}
			}

		} catch (SQLException sqle) {

		} catch (Exception e) {
			System.out.println("Exception occured in Employeeserivce class");

		}
		return contactDetail;

	}

}
