/*
 * Name: Carson Lee
 * Course: CS-320
 * Date: 11/16/2024
 * Description: Contact service, used to manage Contact objects
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

class ContactServiceTest {
		ContactService contactService = new ContactService();

	
	@Test
	@DisplayName("Test that a valid contact can be added successfully.")
	void testAddValidContact() {
		//Add a test contact.
		contactService.addContact("12345", "Jane", "Doe", "1234567890", "1234 Test Street");
		
		//Test each field to make sure the value was assigned correctly.
		assertEquals(1, contactService.contactList.size());
		assertEquals("12345", contactService.contactList.get(0).getContactID());
		assertEquals("Jane", contactService.contactList.get(0).getFirstName());
		assertEquals("Doe", contactService.contactList.get(0).getLastName());
		assertEquals("1234567890", contactService.contactList.get(0).getPhoneNum());
		assertEquals("1234 Test Street", contactService.contactList.get(0).getAddress());
		
		//Remove the test contact from the contactList.
		contactService.contactList.remove(0);
	}
	
	@Test
	@DisplayName("Test that a duplicate contactID throws an error.")
	void testDuplicateContactID() {
		//Add a contact, then check that adding it again throws an exception.
		contactService.addContact("12345", "Jane", "Doe", "1234567890", "1234 Test Street");
		assertThrows(IllegalArgumentException.class, () -> {
			contactService.addContact("12345", "Jane", "Doe", "1234567890", "1234 Test Street");
		});
		//Remove the test contact
		contactService.contactList.remove(0);
	}
	
	@Test
	@DisplayName("Test that a contact can be returned using its ID.")
	void testGetContactWithID() {
		//Created a new contact and assign it to variable "contact".
		contactService.addContact("12345", "Jane", "Doe", "1234567890", "1234 Test Street");
		Contact contact = contactService.getContact("12345");
		//Assert that a contact was returned and that the correct contact was returned.
		assertNotNull(contact);
		assertEquals("Jane",contact.getFirstName());
		//Remove the test contact
		contactService.contactList.remove(0);
	}
	
	@Test
	@DisplayName("Test that a contact that is not found returns as null.")
	void testIDNotFound() {
		Contact contact = contactService.getContact("12345");
		assertNull(contact);
	}
	
	@Test
	@DisplayName("Test that a contact can be deleted using its Contact ID")
	public void testDeleteContact() {
		contactService.addContact("12345", "Jane", "Doe", "1234567890", "1234 Test Street");
		contactService.deleteContact("12345");
		assertEquals(0, contactService.contactList.size());
	}
	@Test
	@DisplayName("Test that a contact can be updated using its ID.")
	public void testUpdateContact() {
		//Add a test contact.
		contactService.addContact("12345", "Jane", "Doe", "1234567890", "1234 Test Street");
		
		//Update the contact.
		contactService.updateFirstName("12345", "Enaj");
		contactService.updateLastName("12345", "Eod");
		contactService.updatePhoneNum("12345", "0987654321");
		contactService.updateAddress("12345", "4321 Test Street");
		
		//Test each field to make sure each value was assigned correctly.
		assertEquals("Enaj", contactService.contactList.get(0).getFirstName());
		assertEquals("Eod", contactService.contactList.get(0).getLastName());
		assertEquals("0987654321", contactService.contactList.get(0).getPhoneNum());
		assertEquals("4321 Test Street", contactService.contactList.get(0).getAddress());
		
	}
	
	@Test
	@DisplayName("Test that deleting updating a contact that does not exist throws an error.")
	public void testNoContactFound() {
		contactService.deleteContact("1234567890");
		contactService.updateFirstName("1234567890", "updatedName");
		contactService.updateLastName("1234567890", "updatedName");
		contactService.updatePhoneNum("1234567890", "1234567890");
		contactService.updateAddress("1234567890", "1234 New Street");
	}

}
