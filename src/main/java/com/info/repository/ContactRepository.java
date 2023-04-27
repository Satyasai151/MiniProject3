package com.info.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.info.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Serializable> {

}
