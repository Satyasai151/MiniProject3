package com.info.binding;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ContactForm {
	private Integer contactId;
	private String contactName;
	private String email;
	private Long contactNumber;
	private String activeSw;
	private LocalDate createdDate;
	private LocalDate updatedDate;

	}
