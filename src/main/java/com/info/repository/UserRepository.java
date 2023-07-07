package com.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.info.entity.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster, Integer> {
	public UserMaster findByEmailAndPassword(String email,String pwd);
	public UserMaster findByEmail(String email);

}
