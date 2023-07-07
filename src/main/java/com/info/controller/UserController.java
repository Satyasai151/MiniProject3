package com.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.info.binding.ActivateAccount;
import com.info.binding.User;
import com.info.service.UserServiceImpl;

@RestController
public class UserController {
	@Autowired
	private UserServiceImpl service;

	@PostMapping("/save")
	public ResponseEntity<String> userRegistration(@RequestBody User user) {
		boolean saveUser = service.saveUser(user);
		if (saveUser) {
			return new ResponseEntity<>("Registration Success", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Registration Failed", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/activate")
	public ResponseEntity<String> activateAccount(@RequestBody ActivateAccount account) {
		boolean activateUserAccount = service.activateUserAccount(account);
		if (activateUserAccount) {
			return new ResponseEntity<>("Activate Account", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Invalid Temp Password", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
		User user = service.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable Integer userId) {
		boolean deleteUserById = service.deleteUserById(userId);
		if (deleteUserById) {
			return new ResponseEntity<>("Deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/status/{userId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer userId, @PathVariable String status) {
		boolean changeAccountStatus = service.changeAccountStatus(userId, status);
		if (changeAccountStatus) {
			return new ResponseEntity<>("Status Changed", HttpStatus.OK);
		}

		else {
			return new ResponseEntity<>("Failed To Changed", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
