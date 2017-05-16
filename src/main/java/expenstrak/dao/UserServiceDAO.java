package expenstrak.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import expenstrak.domain.User;
import expenstrak.services.UserService;

@Component("UserServiceDAOBean")
public class UserServiceDAO implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceDAO.class);
	
	@Autowired
	UserRepository userRepo;
	
	//@Override
	public User addNewUser(String lastName, String firstName, String emailAddress) {
		// TODO Auto-generated method stub
		User user = new User(lastName, firstName, emailAddress);
		log.info("Created the new user [" + user.toString() + "]");
		return userRepo.save(user);
	}

	//@Override
	public User getUser(String emailAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(long id) {
		// TODO Auto-generated method stub
		if (userRepo.exists(id)) {
			User user = userRepo.findOne(id);
			return user;
		} else {
			return null;
		}
			
	}

	//@Override
	public void deleteUser(String emailAddress) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return (List<User>) userRepo.findAllByOrderByLastName();
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		User newUser = userRepo.save(user);
		log.info("ADDED USER: " + newUser.toString());
		return newUser;
	}

	@Override
	public User updateUser(long id, User user) {
		// TODO Auto-generated method stub
		User oldUser = userRepo.findOne(id);
		log.info("UPDATE USER: BEFORE: " + oldUser.toString());
		oldUser.setEmailAddress(user.getEmailAddress());
		oldUser.setLastName(user.getLastName());
		oldUser.setFirstName(user.getFirstName());
		User newUser = userRepo.save(oldUser);
		log.info("UPDATE USER: AFTER: " + newUser.toString());
		return newUser;
	}

	@Override
	public void deleteUser(long id) {
		// TODO Auto-generated method stub
		userRepo.delete(id);
		printAll();	
	}
	
	
	protected void printAll() {
		for(User user : userRepo.findAll()) {
			log.info(user.toString());
		}
	}

}
