/*
 * Name: Carson Lee
 * Course: CS-320 (Original), CS-499 (Enhanced)
 * Date: 11/25/2025
 * Description: Contact service, used to manage Contact objects
 */

package com.contactservice;

public class Contact {
	//initialize the required variables for storing required contact information
	private final String contactID;
	private String firstName;
	private String lastName;
	private String phoneNum;
	private String address;
	
	/*Constructor takes the required variables for a Contact object.
	 *Checks whether each one is valid and acts accordingly.
	 *If variable is empty or null, assigns "NULL" to the object's variable.
	 *If variable length is too long, accepts the maximum amount of allowed characters.
	 */
	public Contact(String contactID, String firstName, String lastName, String phoneNum, String address) {
		//Assigns contactID to object if valid, otherwise throws an exception. Does not check if contactID already exists.
		if (contactID != null && contactID.length() > 10) {
			throw new IllegalArgumentException("Invalid contact ID. The ID must not be greater than 10 characters long.");
		} else {
			this.contactID = contactID;
		}
		//Assigns firstName to object if valid, otherwise throws an exception.
		if (firstName == null || firstName.isEmpty() || firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name. First name must not be greater than 10 characters long and must not be null.");
		} else {
			this.firstName = firstName;
		}
		//Assigns lastName to object if valid, otherwise throws an exception.
		if (lastName == null || lastName.isEmpty() || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name. Last name must not be greater than 10 characters long and must not be null.");
		} else {
				this.lastName = lastName;
		}
		//Assigns phoneNum to object if valid, otherwise throws an exception.
		if (phoneNum == null || phoneNum.isEmpty() || phoneNum.length() != 10) {
			throw new IllegalArgumentException("Invalid phone number. Phone number must be exactly 10 digits long and not be null.");
		} else {
			this.phoneNum = phoneNum;
		}
		//If address is empty or null, assigns "NULL" and otherwise accepts the first 30 characters of the string
		if (address == null || address.isEmpty() || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address. Address must not be greater than 30 characters long and must not be null.");
		} else {
			this.address = address;
		}
	}
	//Getters for contact information
	public String getContactID() {
		return contactID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	
	public String getAddress() {
		return address;
	}
	//Setters for contact information other than contactId, using the same validation as the constructor
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isEmpty() || firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name. The first name must not be greater than 10 characters long.");
		} else {
			this.firstName = firstName;
		}
		
	}
	
	public void setLastName(String lastName) {
		if (lastName == null || lastName.isEmpty() || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name. last name must not be greater than 10 characters long and must not be null.");
		} else {
				this.lastName = lastName;
		}
	}
	
	public void setPhoneNum(String phoneNum) {
		if (phoneNum == null || phoneNum.isEmpty() || phoneNum.length() != 10) {
			throw new IllegalArgumentException("Invalid phone number. Phone number must be exactly 10 digits long and not be null.");
		} else {
			this.phoneNum = phoneNum;
		}
	}
	
	public void setAddress(String address) {
		if (address == null || address.isEmpty() || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address. Address must not be greater than 30 characters long and must not be null.");
		} else {
			this.address = address;
		}
	}
}
