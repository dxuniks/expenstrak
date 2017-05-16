package expenstrak.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import expenstrak.domain.*;
import expenstrak.services.AccountService;
import expenstrak.services.CategoryService;
import expenstrak.services.TransactionService;
import expenstrak.services.UserService;

@Controller
//@ComponentScan({"expenstrak.dao"})
public class TransactionController {
	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
	
	
	private final AccountService accountService;
	private final UserService userService;
	private final TransactionService transactionService;
	private final CategoryService categoryService;
	
	
	@Autowired
	public TransactionController (@Qualifier("TransactionServiceDAOBean")TransactionService transactionService,
								  @Qualifier("AccountServiceDAOBean")AccountService accountService,
								  @Qualifier("UserServiceDAOBean")UserService userService,
								  @Qualifier("CategoryServiceDAOBean")CategoryService categoryService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
		this.userService = userService;
		this.categoryService = categoryService;
	}
		
	@RequestMapping (value = "/transaction", method = RequestMethod.GET)
	public String getTransactions (Model model) {		
		model.addAttribute("Accounts", accountService.getAllAccounts());
		model.addAttribute("Users", userService.getAllUser());
		model.addAttribute("Categories", categoryService.getAllCategory());
		model.addAttribute("Transactions", transactionService.getLatest10Transactions());
		//log.info(accountService.getAllAccounts().toString());
		return "transaction";
	}
	
	@RequestMapping (value = "/addTransaction", method = RequestMethod.POST)
	public String addTransaction(@RequestParam("amount") float amount, @RequestParam("transactionDate") Date date, @RequestParam("description") String description, 
				@RequestParam("category") long categoryId, @RequestParam("account") long accountId, @RequestParam("user") long userId) {
		log.info(amount + ":" + date + ":" + description + ":" + categoryId + "|" + accountId + ":" + userId);
		Transaction transaction = new Transaction (accountService.getAccount(accountId), categoryService.getCategory(categoryId), userService.getUser(userId),amount, description,date );
		transactionService.addTransaction(transaction);
		
		return "redirect:transaction";
	}
	
	@RequestMapping (value = "/searchTransaction", method = RequestMethod.POST)
	public String searchTransaction(@RequestParam("action") String action, @RequestParam("category") long categoryId, @RequestParam("account") long accountId, @RequestParam("user") long userId,
				@RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, Model model) {
		log.info("[" + action + "]");
		if (action.equals("All")) {
			return "redirect:transaction";
		}
		log.info("search txn for: " + ":" + categoryId + ":" + accountId + ":" + userId + ":" + startDate + ":" + endDate);			
		model.addAttribute("Accounts", accountService.getAllAccounts());
		model.addAttribute("Users", userService.getAllUser());
		model.addAttribute("Categories", categoryService.getAllCategory());
		model.addAttribute("Transactions",transactionService.searchTransactions(startDate, endDate, categoryId, accountId, userId));	
		return "transaction";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/hi")
	public String test() {
		return "Hi" + System.currentTimeMillis();
	}
	
	public class Test {
		private String book;

		public String getBook() {
			return book;
		}

		public void setBook(String book) {
			this.book = book;
		}		
	}	
}
