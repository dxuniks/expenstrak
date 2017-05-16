package expenstrak.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import expenstrak.domain.Account;
import expenstrak.domain.Category;
import expenstrak.domain.User;
import expenstrak.services.AccountService;
import expenstrak.services.CategoryService;
import expenstrak.services.UserService;

@Controller
public class MetaDataController {
	
	private final AccountService accountService;
	private final UserService userService;
	private final CategoryService categoryService;
	
	private static final Logger log = LoggerFactory.getLogger(MetaDataController.class);
	
	@Autowired
	public MetaDataController(@Qualifier("AccountServiceDAOBean")AccountService accountService,
							  @Qualifier("UserServiceDAOBean")UserService userService,
							  @Qualifier("CategoryServiceDAOBean")CategoryService categoryService) {
		this.accountService = accountService;
		this.userService = userService;
		this.categoryService = categoryService;
	}
	
	@RequestMapping(value = "/metadata", method = RequestMethod.GET)
	public String metaDataform (Model model) {
		log.info("/metadata requested...");
		model.addAttribute("Accounts", accountService.getAllAccounts());
		model.addAttribute("Users", userService.getAllUser());
		model.addAttribute("Categories", categoryService.getAllCategory());
		
		log.info("metadata model returned...");
		return "metadata";
		
	}
	
	@RequestMapping(value = "/addcategory", method = RequestMethod.POST)
	public String addCatetory(@RequestParam("name") String name, @RequestParam("description") String description){
		Category newCategroy = new Category(name, description);
		categoryService.addCategory(newCategroy);	
		return "redirect:metadata";
	}
	
	@RequestMapping(value = "/addaccount", method = RequestMethod.POST)
	public String addAccount(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("user") long userId){
		Account account = new Account(name, description, 0, userService.getUser(userId));
		accountService.addAccount(account);	
		return "redirect:metadata";
	}
	
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String addAccount(@RequestParam("lastname") String lastName, @RequestParam("firstname") String firstName, @RequestParam("email") String email){
		User user = new User(email, lastName, firstName);
		userService.addUser(user);	
		return "redirect:metadata";
	}
}
