package com.info.service;

import java.util.List;

import com.info.binding.ContactForm;

public interface IContactService {
	public String save(ContactForm contactform);
	public List<ContactForm> getAllContacts();
	public ContactForm updateContactById(Integer contactId);
	public List<ContactForm> delecteContactById(Integer contactId);

}
