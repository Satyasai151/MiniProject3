package com.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.binding.ContactForm;
import com.info.service.ContactServiceImpl;

@RestController
@RequestMapping("/v1")
public class ContactController {
	@Autowired
	private ContactServiceImpl service;

	@PostMapping("/save")
	public String saveContact(@RequestBody ContactForm contactForm) {
		String save = service.save(contactForm);
		return save;

	}

	@GetMapping("/all")
	public List<ContactForm> getAllContact() {
		System.out.println("viwallcontact method");
		return service.getAllContacts();

	}

	@GetMapping("/update/{contactId}")
	public ContactForm updateContact(@PathVariable Integer contactId) {
		return service.updateContactById(contactId);

	}
	@DeleteMapping("/delete/{contactId}")
	public List<ContactForm> deleteContact(@PathVariable Integer contactId){
		return service.delecteContactById(contactId);
		
	}

}
