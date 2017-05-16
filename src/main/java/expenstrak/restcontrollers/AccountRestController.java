package expenstrak.restcontrollers;

import java.util.ArrayList;
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
import expenstrak.domain.Account;
import expenstrak.domain.AccountDTO;
import expenstrak.domain.User;
import expenstrak.domain.UserDTO;
import expenstrak.services.AccountService;
import expenstrak.services.UserService;

@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {
	private static final Logger log = LoggerFactory.getLogger(AccountRestController.class);
	
	private final UserService userService;
	private final AccountService accountService;
	
	@Autowired
	public AccountRestController (@Qualifier("AccountServiceDAOBean")AccountService accountService, 
							@Qualifier("UserServiceDAOBean")UserService userService) {
		this.accountService = accountService;
		this.userService = userService;
	}

	////-----GET
	//get all the accounts
	@RequestMapping(method = RequestMethod.GET)
	public ApiResponse getAllAccounts() {
		log.info("looking for all Accounts");
		return new ApiResponse("OK", "All Accounts returned", "Account", accountService.getAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toList()) );
	}
	
	//get all the accounts associated with a user
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ApiResponse getAccountsByUser(@PathVariable("userId") long userId ) {
		log.info("looking for all Accounts for user +[" + userId + "]");
		return new ApiResponse("OK", "All Accounts for user [" + userId + "]", 
				"Account", accountService.getAccountByUser(userService.getUser(userId)).stream().map(AccountDTO::new).collect(Collectors.toList()) );
	}	
	
	////-----POST
	//add a account
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ApiResponse addAccount (@RequestBody AccountDTO account){
		log.info("Add new account [" + account.toString() + "]");
		Account newAccount = accountService.addAccount(account.convertToAccount());
		ArrayList<AccountDTO> accountList = new ArrayList<AccountDTO> ();
		accountList.add(new AccountDTO(newAccount));
		return new ApiResponse ("OK", "Account added.", "Account", accountList );
	}
	
	////-----DELETE
	//delete a account
	@RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
	public ApiResponse deleteAccount(@PathVariable("accountId") long accountId) {
		log.info("deleting account = [" + accountId + "]");
		accountService.deleteAccount(accountId);
		return new ApiResponse("Ok", "Deleted", null, null);
	}	
	
	//delete all accounts associated with the user
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public ApiResponse deleteAccountsByUser(@PathVariable("userId") long userId) {
		log.info("deleting account for user = [" + userId + "]");
		
		return new ApiResponse("Ok", "Followling Accounts are deleted.", "Account", 
				accountService.deleteAccountsByUser(userService.getUser(userId)).stream().map(AccountDTO :: new).collect(Collectors.toList()));
	}
	
	////-Update - PUT
	/// update  an Account
	@RequestMapping(value = "/{accountId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse updateUser(@PathVariable("accountId") long accountId, @RequestBody AccountDTO account) {
		log.info("Updating an Account: [" + account.toString() + "]");		
		Account newAccount = accountService.updateAccount(accountId, account.convertToAccount());
		ArrayList<AccountDTO> accountList = new ArrayList<AccountDTO> ();
		accountList.add(new AccountDTO(newAccount));
		return new ApiResponse("Ok", "Updated", "Account", accountList);
	}
	

}
