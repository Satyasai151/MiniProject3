package com.info.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.info.binding.ActivateAccount;
import com.info.binding.Login;
import com.info.binding.User;
import com.info.entity.UserMaster;
import com.info.repository.UserRepository;
import com.info.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean saveUser(User user) {
		UserMaster entity = new UserMaster();
		BeanUtils.copyProperties(user, entity);
		entity.setPassword(generateRandomPassword());
		entity.setAccStatus("IN-Active");
		UserMaster save = repo.save(entity);
		String subject = "Your Registration success";
		String fileName = "REG-EMAIL-BODY.txt";
		String body = readEmailBody(entity.getFullName(), entity.getPassword(), fileName);

		emailUtils.sendMail(user.getEmail(), subject, body);

		return save.getUserId() != null;
	}

	@Override
	public boolean activateUserAccount(ActivateAccount activate) {
		UserMaster entity = new UserMaster();
		entity.setEmail(activate.getEmail());
		entity.setPassword(activate.getTempPwd());
		Example<UserMaster> of = Example.of(entity);
		List<UserMaster> findAll = repo.findAll(of);
		if (findAll.isEmpty()) {
			return false;
		} else {
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(activate.getNewPwd());
			userMaster.setAccStatus("Activate");
			repo.save(userMaster);
			return true;

		}

	}

	@Override
	public List<User> getAllUsers() {
		List<UserMaster> findAll = repo.findAll();
		List<User> users = new ArrayList<>();
		for (UserMaster entity : findAll) {
			User user = new User();
			BeanUtils.copyProperties(entity, user);
		}
		return users;
	}

	@Override
	public User getUserById(Integer userId) {
		Optional<UserMaster> findById = repo.findById(userId);
		if (findById.isPresent()) {
			User user = new User();
			UserMaster userMaster = findById.get();
			BeanUtils.copyProperties(userMaster, user);
			return user;

		}

		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		try {
			repo.deleteById(userId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}

		return false;
	}

	@Override
	public boolean changeAccountStatus(Integer userId, String accStatus) {
		Optional<UserMaster> findById = repo.findById(userId);
		if (findById.isPresent()) {
			UserMaster userMaster = findById.get();
			userMaster.setAccStatus(accStatus);
			repo.save(userMaster);
			return true;

		}
		return false;
	}

	@Override
	public String login(Login login) {
		/*
		 * UserMaster entity = new UserMaster(); entity.setEmail(login.getEmail());
		 * entity.setPassword(login.getPassword()); Example<UserMaster> of =
		 * Example.of(entity); List<UserMaster> findAll = repo.findAll(of)
		 */;
		UserMaster entity = repo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		if (entity == null) {
			return "invalid credentials";
		} else {

			if (entity.getAccStatus().equals("Activate")) {
				return "Success";
			} else {
				return "Account not activated";
			}
		}

	}

	@Override
	public String forgotPassword(String email) {
		UserMaster findByEmail = repo.findByEmail(email);
		if (findByEmail == null) {
			return "Invalid Email";
		}
		String suject = "Forgot Password";
		String fileName = "RECOVER-EMAIL-BODY.txt";
		String body = readEmailBody(findByEmail.getFullName(), findByEmail.getPassword(), fileName);
		boolean sendMail = emailUtils.sendMail(email, suject, body);
		if (sendMail) {
			return "Password Sent Your Register Email";
		}

		return null;
	}

	private String generateRandomPassword() {
	    // create a string of uppercase and lowercase characters and numbers
	    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
	    String numbers = "0123456789";

	    // combine all strings
	    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

	    // create random string builder
	    StringBuilder sb = new StringBuilder();

	    // create an object of Random class
	    Random random = new Random();

	    // specify length of random string
	    int length =6;

	    for(int i = 0; i < length; i++) {

	      // generate random index number
	      int index = random.nextInt(alphaNumeric.length());

	      // get character specified by index
	      // from the string
	      char randomChar = alphaNumeric.charAt(index);

	      // append the character to string builder
	      sb.append(randomChar);
	    }

	    return sb.toString();
	    
	}

	private String readEmailBody(String fullName, String tempPwd, String fileName) {

		String url = "";
		String mailBody = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String readLine = br.readLine();
			while (readLine != null) {
				stringBuffer.append(readLine);
				readLine = br.readLine();
			}
			br.close();
			mailBody = stringBuffer.toString();
			mailBody.replace("{FULLNAME}", fullName);
			mailBody.replace("{TEMP-PWD}", tempPwd);
			mailBody.replace("{URL}", url);
			mailBody.replace("PASSWORD", tempPwd);
		}

		catch (Exception e) {
			e.printStackTrace();

		}
		return mailBody;

	}

}
