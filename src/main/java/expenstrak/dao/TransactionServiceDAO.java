package expenstrak.dao;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import expenstrak.domain.Account;
import expenstrak.domain.CategorySum;
import expenstrak.domain.CategoryTotalAmount;
import expenstrak.domain.Transaction;
import expenstrak.exceptions.RecordNotFoundException;
import expenstrak.services.TransactionService;

@Component("TransactionServiceDAOBean")
@Service
public class TransactionServiceDAO implements TransactionService {
	private static final Logger log = LoggerFactory.getLogger(TransactionServiceDAO.class);
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	AccountRepository accountRepo;
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	TransactionRepository transactionRepo;
	
	@Override
	//@Transactional
	public Transaction addTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		if (accountRepo.exists(transaction.getAccount().getId())) {
			log.info("Logging transaction [" + transaction.getAccount() + "]");
			Account account = accountRepo.findOne(transaction.getAccount().getId());
			account.addTransaction(transaction.getAmount());
			Transaction newTransaction =  transactionRepo.save(transaction);
			accountRepo.save(account);
			return newTransaction;
		}
		else 
			throw new RecordNotFoundException ("Account " + transaction.getAccount() + " does not exist");
		
	}

	@Override
	public List<Transaction> getAllTransacdtion() {
		// TODO Auto-generated method stub
		return (List<Transaction>) transactionRepo.findAll();
	}

	@Override
	public List<Transaction> getTransactionByAccount(Account account) {
		// TODO Auto-generated method stub
		return transactionRepo.findTransactionByAccountOrderByTransactionDateDesc(account);
		

	}
	
	@Override 
	public List<Transaction> getSomeLatestTransactions(int numbers) {
		
		return transactionRepo.findFirst20TransactionByOrderByTransactionDateDescIdDesc();
		//return (List<Transaction>) transactionRepo.findAll();
	}
	
	@Override
	public List <Transaction> searchTransactions(Date startDate, Date endDate, long categoryId, long accountId, long userId) {
		if (categoryId != -999 && accountId != -999 && userId != -999)  {
			return transactionRepo.findAllByCategoryAndAccountAndUserAndTransactionDateBetweenOrderByTransactionDateDesc(
					categoryRepo.findOne(categoryId), accountRepo.findOne(accountId), userRepo.findOne(userId), startDate, endDate);
		} else if (categoryId != -999 && accountId != -999 && userId == -999) {
			return transactionRepo.findAllByCategoryAndAccountAndTransactionDateBetweenOrderByTransactionDateDesc(
					categoryRepo.findOne(categoryId), accountRepo.findOne(accountId),  startDate, endDate);			
		} else if (categoryId != -999 && accountId == -999 && userId != -999) {
			return transactionRepo.findAllByCategoryAndUserAndTransactionDateBetweenOrderByTransactionDateDesc(
					categoryRepo.findOne(categoryId), userRepo.findOne(userId), startDate, endDate);			
		} else if (categoryId == -999 && accountId != -999 && userId != -999)  {
			return transactionRepo.findAllByAccountAndUserAndTransactionDateBetweenOrderByTransactionDateDesc(
					accountRepo.findOne(accountId), userRepo.findOne(userId), startDate, endDate);
		} else if (categoryId != -999 && accountId == -999 && userId == -999)  {
			return transactionRepo.findAllByCategoryAndTransactionDateBetweenOrderByTransactionDateDesc(
					categoryRepo.findOne(categoryId), startDate, endDate);
		} else if (categoryId == -999 && accountId != -999 && userId == -999)  {
			return transactionRepo.findAllByAccountAndTransactionDateBetweenOrderByTransactionDateDesc(
					accountRepo.findOne(accountId), startDate, endDate);
		} else if (categoryId == -999 && accountId == -999 && userId != -999)  {
			return transactionRepo.findAllByUserAndTransactionDateBetweenOrderByTransactionDateDesc(
					userRepo.findOne(userId), startDate, endDate);
		} else {
			return transactionRepo.findAllByTransactionDateBetweenOrderByTransactionDateDesc(startDate, endDate);
		}
	}

	@Override
	public List<Transaction> getLatest10Transactions() {
		// TODO Auto-generated method stub
		return transactionRepo.findFirst10TransactionByOrderByTransactionDateDescIdDesc();
	}

	@Override
	public Transaction getTransaction(long transactionId) {
		// TODO Auto-generated method stub
		return transactionRepo.findOne(transactionId);
	}

	@Override
	public void deleteTransaction(long transactionId) {
		// TODO Auto-generated method stub
		transactionRepo.delete(transactionId);
	}

	@Override
	public Transaction updateTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return transactionRepo.save(transaction);
	}

	@Override
	public List<Transaction> getTransactionsByMonth(String yearMonth01Str) {
		// TODO Auto-generated method stub
		log.info("Get transaction for the month of the year[" + yearMonth01Str + "]");
		return transactionRepo.queryByTransactionDate(yearMonth01Str);
	}

	@Override
	public List<CategoryTotalAmount> getCategoryTotalAmountByMonth(String yearMonth01Str) {
		// TODO Auto-generated method stub
		log.info("Get Statistic data for the month of the year[" + yearMonth01Str + "]");
		return transactionRepo.queryCategoryTotalAmountByMonth(yearMonth01Str);
	}
	
	
	 
	
}
