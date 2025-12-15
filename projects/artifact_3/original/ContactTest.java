/*
 * Name: Carson Lee
 * Course: CS-320
 * Date: 11/16/2024
 * Description: Contact service, used to manage Contact objects
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

class ContactTest {
	/*
	 * The following tests are to ensure the following requirements are met:
	 * - Contact information fields are not longer than their specified constraints
	 * - Contact information fields are not null
	 * - Note that phone number must be tested for being too long and too short
	 */
	@Test
	@DisplayName("Test that a valid contact can be created.")
	void testValidContact() {
		Contact contact = new Contact("1234567890", "Jane", "Doe", "1234567890", "1234 Test Drive");
		assertEquals("1234567890", contact.getContactID());
		assertEquals("Jane", contact.getFirstName());
		assertEquals("Doe", contact.getLastName());
		assertEquals("1234567890", contact.getPhoneNum());
		assertEquals("1234 Test Drive", contact.getAddress());
	}
	@Test
	@DisplayName("Test that an invalid contact ID throws an exception.")
	void testInvalidContactID() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123456789101112131415", "firstName", "LastName", "PhoneNum", "Address");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Contact(null, "FirstName", "LastName", "PhoneNum", "Address");
		});
	}
	@Test
	@DisplayName("Test that an invalid first name throws an exception.")
	void testInvalidFirstName() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Contact("12345", "ThisFirstNameIsTooLong", "LastName", "PhoneNum", "Address");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Contact("12345", null, "LastName", "PhoneNum", "Address");
		});
	}
	@Test
	@DisplayName("Test that an invalid last name throws an exception.")
	void testInvalidLastName() {
			assertThrows(IllegalArgumentException.class, () -> {
				new Contact("12345", "FirstName", "ThisLastNameIsTooLong", "PhoneNum", "Address");
			});
			assertThrows(IllegalArgumentException.class, () -> {
				new Contact("12345", "FirstName", null, "PhoneNum", "Address");
			});
	}
	@Test
	@DisplayName("Test that an invalid phone number throws an exception.")
	void testInvalidPhoneNum() {
			assertThrows(IllegalArgumentException.class, () -> {
				new Contact("12345", "FirstName", "LastName", "555555555555555", "Address");
			});
			assertThrows(IllegalArgumentException.class, () -> {
				new Contact("12345", "FirstName", "LastName", "555555", "Address");
			});
			assertThrows(IllegalArgumentException.class, () -> {
				new Contact("12345", "FirstName", "LastName", null, "Address");
			});
			assertThrows(IllegalArgumentException.class, () -> {
				new Contact("12345", "FirstName", "LastName", "NoNumberss", "Address");
			});
	}
	@Test
	@DisplayName("Test that an invalid address throws an exception.")
	void testInvalidAddress() {
			assertThrows(IllegalArgumentException.class, () -> {
				new Contact("12345", "FirstName", "LastName", "1234567890", "This Address has far too many characters to be valid and therefore should throw an exception.");
			});
			assertThrows(IllegalArgumentException.class, () -> {
				new Contact("12345", "FirstName", "LastName", "1234567890", null);
			});
			
	}
	@Test
	@DisplayName("Test that the setters for the Contact class update the contact correctly.")
	void testUpdateContact() {
		Contact contact = new Contact("1234567890", "Jane", "Doe", "1234567890", "1234 Test Drive");
		contact.setFirstName("Updated");
		contact.setLastName("Updated");
		contact.setPhoneNum("0987654321");
		contact.setAddress("1234 New Street");
		assertEquals(contact.getFirstName(),"Updated");
		assertEquals(contact.getLastName(), "Updated");
		assertEquals(contact.getPhoneNum(), "0987654321");
		assertEquals(contact.getAddress(), "1234 New Street");
	}
	
	@Test
	@DisplayName("Test that the setters for the Contact class correctly throw errors when given invalid parameters.")
	void testUpdateContactInvalid() {
		Contact contact = new Contact("1234567890", "Jane", "Doe", "1234567890", "1234 Test Drive");
		assertThrows(IllegalArgumentException.class, () -> {
			contact.setFirstName("ThisNameIsTooLong");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			contact.setLastName("ThisNameIsAlsoTooLong");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			contact.setPhoneNum("5555555555555555555");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			contact.setPhoneNum("NoNumberss");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			contact.setAddress("12345678 AddressIsWayTooLongToPossiblyBeARealOrValidAddress Drive");
		});
	}
}
