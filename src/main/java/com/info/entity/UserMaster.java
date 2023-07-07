package com.info.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_master")
public class UserMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String fullName;
	private String email;
	private Long mobile;
	private String gender;
	private LocalDate dob;
	private Long ssn;
	private String password;
	private String accStatus;
	private LocalDate createDate;
	private LocalDate updateDate;
	private String createdBy;
	private String updateBy;

}
