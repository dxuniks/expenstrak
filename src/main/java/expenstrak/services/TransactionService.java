package expenstrak.services;

import java.util.Date;
import java.util.List;

import expenstrak.domain.Account;
import expenstrak.domain.CategorySum;
import expenstrak.domain.CategoryTotalAmount;
import expenstrak.domain.Transaction;

public interface TransactionService {
	public Transaction addTransaction (Transaction transaction);
	public Transaction getTransaction (long transactionId);
	public Transaction updateTransaction(Transaction transaction);
	public void deleteTransaction(long transactionId);
	public List <Transaction> getAllTransacdtion ();
	public List <Transaction> getLatest10Transactions();
	public List <Transaction> getTransactionByAccount (Account account);	
	public List <Transaction> getSomeLatestTransactions(int number);
	public List <Transaction> searchTransactions(Date startDate, Date endDate, long categoryId, long accountId, long userId);
	public List <Transaction> getTransactionsByMonth(String yearMonth01Str);
	
	//public  void addTransaction (Transaction transaction);
	public List <CategoryTotalAmount> getCategoryTotalAmountByMonth(String yearMonth01Str);
}
