package expenstrak.restcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import expenstrak.ApiResponse;
import expenstrak.domain.User;
import expenstrak.domain.UserDTO;
import expenstrak.exceptions.RecordNotFoundException;
import expenstrak.services.UserService;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {

	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
	

	private final UserService userService;
	
	@Autowired
	public UserRestController (@Qualifier("UserServiceDAOBean") UserService userService) {
		this.userService = userService;		
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ApiResponse getUser(@PathVariable("userId") long userId) {
		log.info("looking for userID = [" + userId + "]");
		User user = userService.getUser(userId);
		if (user == null) {
			log.info("User id [" + userId +"] does not exist.");
			throw new RecordNotFoundException ("User id [" + userId +"] does not exist.");
		} 
		ArrayList<UserDTO> userList = new ArrayList<UserDTO> ();
		userList.add(new UserDTO(user));
		return new ApiResponse("Ok", "found user", "User", userList) ;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResponse getAllUser() {
		log.info("looking for all users");
		return new ApiResponse("Ok", "All Users returned", "User", userService.getAllUser().stream().map(UserDTO::new).collect(Collectors.toList()) );
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse addUser(@RequestBody UserDTO user) {
		log.info("Adding a new user: [" + user.toString() + "]");
		User newUser = userService.addUser(user.convertToUser());
		ArrayList<UserDTO> userList = new ArrayList<UserDTO> ();
		userList.add(new UserDTO(newUser));
		return new ApiResponse  ("Ok", "Added", "User", userList);
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ApiResponse deleteUser(@PathVariable("userId") long userId) {
		log.info("deleting for userID = [" + userId + "]");
		userService.deleteUser(userId);
		return new ApiResponse("Ok", "Deleted", null, null);
	}	
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse updateUser(@PathVariable("userId") long userId, @RequestBody UserDTO user) {
		log.info("Updating a user: [" + user.toString() + "]");		
		User newUser = userService.updateUser(userId, user.convertToUser());
		ArrayList<UserDTO> userList = new ArrayList<UserDTO> ();
		userList.add(new UserDTO(newUser));
		return new ApiResponse("Ok", "Updated", "User", userList);
	}
}
