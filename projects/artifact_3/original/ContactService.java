/*
 * Name: Carson Lee
 * Course: CS-320
 * Date: 11/16/2024
 * Description: Contact service, used to manage Contact objects
 */
import java.util.ArrayList;

public class ContactService {
	//use an ArrayList of Contact objects to create the contact list
	public ArrayList<Contact> contactList = new ArrayList<Contact>();
	
	//Create a new contact using the constructor, then add the contact to contactList.
	//Checks to ensure the specified contactID is not already in use, throws exception if it is.
	public void addContact(String contactID, String firstName, String lastName, String number, String address) {
		for (Contact contact : contactList) {
			if (contact.getContactID().equals(contactID)) {
				throw new IllegalArgumentException("That contact ID is already being used by another contact.");
			} 
		}
		Contact contact = new Contact(contactID, firstName, lastName, number, address);
		contactList.add(contact);
			
	}
	
	//Return a contact using the specified contactID, if one is not found return a null contact
	public Contact getContact(String contactID) {
		//iterate through contactList, searching for a contactID that matches the given one
		for (Contact contact : contactList) {
			if (contact.getContactID().equals(contactID)) {
				return contact;
			}
		}
		return null;
	}
	
	//Delete a contact using a specific contactID, return message if contact is not found
	public void deleteContact(String contactID) {
		for (int i = 0; i < contactList.size(); i++) {
			if (contactList.get(i).getContactID().contentEquals(contactID)) {
				contactList.remove(i);
				break;
			}
		}
		new IllegalArgumentException("No contact found with given ID.");
				
	}
	
	//Methods for finding a contact using a specified contactID to access the setter functions and update information
	//Update the first name of a contact with the specified ID, return a message if not found.
	public void updateFirstName(String contactID, String updatedName) {
		for (int  i= 0; i < contactList.size(); i++) {
			if (contactList.get(i).getContactID().contentEquals(contactID)) {
				contactList.get(i).setFirstName(updatedName);
				break;
			}
		}
		new IllegalArgumentException("No contact found with given ID.");
			
	}
	//Update the last name of a contact with the specified ID, return a message if not found.
	public void updateLastName(String contactID, String updatedName) {
		for (int  i= 0; i < contactList.size(); i++) {
			if (contactList.get(i).getContactID().contentEquals(contactID)) {
				contactList.get(i).setLastName(updatedName);
				break;
			}
		}
		new IllegalArgumentException("No contact found with given ID.");
			}
	
	//Update the phone number of a contact with a specified contactID, return message if not found.
	public void updatePhoneNum(String contactID, String updatedNum) {
		for (int  i= 0; i < contactList.size(); i++) {
			if (contactList.get(i).getContactID().contentEquals(contactID)) {
				contactList.get(i).setPhoneNum(updatedNum);
				break;
			}
		}
		new IllegalArgumentException("No contact found with given ID.");
			
	}
	//Update the address of a contact with a specified contactID, return a message if not found.
	public void updateAddress(String contactID, String updatedAddress) {
		for (int  i= 0; i < contactList.size(); i++) {
			if (contactList.get(i).getContactID().contentEquals(contactID)) {
				contactList.get(i).setAddress(updatedAddress);
				break;
			}
		}
		new IllegalArgumentException("No contact found with given ID.");
			
	}
	
	
	
}
