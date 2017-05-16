package expenstrak.restcontrollers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import expenstrak.ApiResponse;
import expenstrak.domain.Account;
import expenstrak.domain.AccountDTO;
import expenstrak.domain.Transaction;
import expenstrak.domain.TransactionDTO;
import expenstrak.services.AccountService;
import expenstrak.services.CategoryService;
import expenstrak.services.TransactionService;
import expenstrak.services.UserService;

@RestController
@RequestMapping("/rest/transactions")
public class TransactionRestController {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionRestController.class);	
	
	private final AccountService accountService;
	private final TransactionService transactionService;
	private final CategoryService categoryService;
	private final UserService userService;
	
	@Autowired
	public TransactionRestController (@Qualifier("TransactionServiceDAOBean")TransactionService transactionService,
								@Qualifier("AccountServiceDAOBean")AccountService accountService,
								@Qualifier("CategoryServiceDAOBean")CategoryService categoryService,
								@Qualifier("UserServiceDAOBean")UserService userService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
		this.categoryService = categoryService;
		this.userService = userService;
	}
	
	////-----GET
	//get transactions from 
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ApiResponse getTransactionByAccount(@PathVariable("accountId") long accountId) {
		log.info("looking for all Transaction for account +[" + accountId + "]");
		return new ApiResponse( "OK", "All transactions for accountId +[" + accountId + "]", 
				"Account", transactionService.getTransactionByAccount(accountService.getAccount(accountId)).stream().map(TransactionDTO::new).collect(Collectors.toList()) );
	}
	
	////-----GET
	//get transactions from 
	@RequestMapping(method = RequestMethod.GET)
	public ApiResponse getRecentTransactions() {
		log.info("looking for all Transaction.");
//		return new ApiResponse( "OK", "All transactions", 
//				"Account", transactionService.getAllTransacdtion().stream().map(TransactionDTO::new).collect(Collectors.toList()) );
		ArrayList<Transaction> transactionList = (ArrayList<Transaction>) transactionService.getSomeLatestTransactions(1);
		//log.info("transactions found + [" + transactionList + "]");
		return new ApiResponse( "OK", "All transactions", 
				"Account", transactionList);
	}
	
	// -- GET Browse 
	@RequestMapping(value = "/browse/{year}/{month}", method = RequestMethod.GET)
	public ApiResponse getTransactionsByMonth (@PathVariable("year") String year,  @PathVariable("month") String month) {
		log.info("looking for all Transaction.");
		ArrayList<Transaction> transactionList = (ArrayList<Transaction>) transactionService.getTransactionsByMonth(year + "-" + month + "-01");
		return new ApiResponse( "OK", "browse transactions", 
				"Account", transactionList);
	}
	
	////-----POST
	//add a transaction
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ApiResponse addTransaction (@RequestBody TransactionDTO transaction){
		log.info("Add new Transaction [" + transaction + "]");
		Transaction newTransaction = transactionService.addTransaction(transaction.convertToTransaction(accountService, categoryService, userService));
		//log.info("new transaction is [" + newTransaction + "]");
		ArrayList<Transaction> transactionList = new ArrayList<Transaction> ();
		transactionList.add(newTransaction);
		log.info("transactions found + [" + transactionList + "]");
		return new ApiResponse ("OK", "Transaction posted.", "Transaction", transactionList );
	}
	
	// --- DELETE
	@RequestMapping(value = "/{transactionId}", method = RequestMethod.DELETE)
	public ApiResponse deleteUser(@PathVariable("transactionId") long transactionId) {
		log.info("deleting for transaction = [" + transactionId + "]");
		transactionService.deleteTransaction(transactionId);
		return new ApiResponse("Ok", "Deleted", null, null);
	}	
	
	// --- PUT
	/// update date an transaction
	@RequestMapping(value = "/{transactionId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse updateTransaction(@PathVariable("transactionId") long transactionId, @RequestBody TransactionDTO transaction) {
		log.info("Updating an Transaction: [" + transaction.toString() + "]");	
		Transaction newTransaction = transaction.convertToTransaction(accountService, categoryService, userService);
		newTransaction.setId(transactionId);
		newTransaction = transactionService.updateTransaction(newTransaction);
		ArrayList<Transaction> transactionList = new ArrayList<Transaction> ();
		transactionList.add(newTransaction);
		return new ApiResponse("Ok", "Updated", "Transaction", transactionList);
	}
	
	// -- POST search
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse searchTransactions(
			@RequestParam(value="startdate", required=true) String startDateStr,
			@RequestParam(value="enddate", required=true) String endDateStr,
			@RequestParam(value="category", required=false) Long category,
			@RequestParam(value="account", required=false) Long account,
			@RequestParam(value="user", required=false) Long user
	) {
		long categoryId = -999;
		long userId = -999;
		long accountId = -999;
		
		log.info("Search request: start date:[" + startDateStr + "] end date:[" + endDateStr + "] category [" + category + "] account:[" + account + "] user:[" + user + "]");
		if (category != null) {
			categoryId = category.longValue();
		}
		if (account != null) {
			accountId = account.longValue();
		}
		if (user != null) {
			userId = user.longValue();
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			startDate = format.parse(startDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new ApiResponse("Ok", "Search ", "Transaction", transactionService.searchTransactions(startDate, endDate, categoryId, accountId, userId));
	}
	
	

	

}
