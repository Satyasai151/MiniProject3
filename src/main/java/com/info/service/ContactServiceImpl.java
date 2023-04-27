package com.info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.binding.ContactForm;
import com.info.entity.Contact;
import com.info.repository.ContactRepository;

@Service
public class ContactServiceImpl implements IContactService {
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public String save(ContactForm contactform) {
		Contact entity = new Contact();
		
		BeanUtils.copyProperties(contactform, entity);
		entity.setActiveSw("Y");
		entity = contactRepository.save(entity);
		if (entity.getContactId() != null) {
			return "success";
		}

		return "contact failed to saved";
	}

	@Override
	public List<ContactForm> getAllContacts() {
		List<ContactForm> dataList = new ArrayList<>();
		List<Contact> findAll = contactRepository.findAll();
		for (Contact entity : findAll) {
			ContactForm form = new ContactForm();
			BeanUtils.copyProperties(entity, form);
			dataList.add(form);
		}
		return dataList;
	}

	@Override
	public ContactForm updateContactById(Integer contactId) {
		Optional<Contact> findById = contactRepository.findById(contactId);
		if (findById.isPresent()) {
			Contact entity = findById.get();
			ContactForm form = new ContactForm();
			BeanUtils.copyProperties(entity, form);
			return form;
		}
		return null;
	}

	@Override
	public List<ContactForm> delecteContactById(Integer contactId) {
		contactRepository.deleteById(contactId);
		List<ContactForm> dataList = new ArrayList<>();
		List<Contact> findAll = contactRepository.findAll();
		for (Contact entity : findAll) {
			ContactForm form = new ContactForm();
			BeanUtils.copyProperties(entity, form);
			dataList.add(form);
		}
			return dataList;
		}

	}

