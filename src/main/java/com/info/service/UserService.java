package com.info.service;

import java.util.List;

import com.info.binding.ActivateAccount;
import com.info.binding.Login;
import com.info.binding.User;

public interface UserService {
	public boolean saveUser(User user);

	public boolean activateUserAccount(ActivateAccount activate);

	public List<User> getAllUsers();

	public User getUserById(Integer userId);

	public User getUserByEmail(String email);

	public boolean deleteUserById(Integer userId);

	public boolean changeAccountStatus(Integer userId, String accStatus);

	public String login(Login login);

	public String forgotPassword(String email);

}
