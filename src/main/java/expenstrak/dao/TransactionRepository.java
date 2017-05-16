package expenstrak.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import expenstrak.domain.Account;
import expenstrak.domain.Category;
import expenstrak.domain.CategorySum;
import expenstrak.domain.CategoryTotalAmount;
import expenstrak.domain.Transaction;
import expenstrak.domain.User;


public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	List <Transaction> findTransactionByAccountOrderByTransactionDateDesc(Account Account);
	List <Transaction> findFirst10TransactionByUserOrderByTransactionDateDesc(User user);
	List <Transaction> findFirst10TransactionByOrderByTransactionDateDescIdDesc();
	List <Transaction> findFirst3TransactionByOrderByTransactionDateDescIdDesc();
	List <Transaction> findFirst20TransactionByOrderByTransactionDateDescIdDesc();
	List <Transaction> findAllByCategoryAndAccountAndUserAndTransactionDateBetweenOrderByTransactionDateDesc(Category category, Account acount, User user, Date startDate, Date endDate);
	List <Transaction> findAllByCategoryAndTransactionDateBetweenOrderByTransactionDateDesc(Category category, Date startDate, Date endDate);
	List <Transaction> findAllByAccountAndTransactionDateBetweenOrderByTransactionDateDesc(Account acount, Date startDate, Date endDate);
	List <Transaction> findAllByUserAndTransactionDateBetweenOrderByTransactionDateDesc(User user, Date startDate, Date endDate);
	List <Transaction> findAllByCategoryAndAccountAndTransactionDateBetweenOrderByTransactionDateDesc(Category category, Account acount, Date startDate, Date endDate);
	List <Transaction> findAllByCategoryAndUserAndTransactionDateBetweenOrderByTransactionDateDesc(Category category,User user, Date startDate, Date endDate);
	List <Transaction> findAllByAccountAndUserAndTransactionDateBetweenOrderByTransactionDateDesc(Account acount, User user, Date startDate, Date endDate);
	List <Transaction> findAllByTransactionDateBetweenOrderByTransactionDateDesc(Date startDate, Date endDate);
	
	@Query("SELECT t FROM TRANSACTIONS t WHERE MONTH(transaction_date) = MONTH(?1) AND YEAR(transaction_date) = YEAR(?1) ORDER BY transaction_date, ID")
	//@Query("SELECT t FROM TRANSACTIONS t order by transaction_date, id")
	List <Transaction> queryByTransactionDate(String dateStr);  //dateStr to be 'YYYY-MM-DD'
	
	//@Query(value = "select new learn.test04.domain.CategorySum(c.name as categoryName, sum(t.amount*1.0) as total) from TRANSACTIONS t , CATEGORIES  c WHERE MONTH(transaction_date) = MONTH(?1) AND YEAR(transaction_date) = YEAR(?1) and c.id = t.category.id group by c.name")
	@Query(value = "select new expenstrak.domain.CategorySum(c.name as categoryName, sum(t.amount*1.0) as total, sum(t.amount*1.0) as budget, count(t) as transactionCount) "
			+ "from TRANSACTIONS t left join t.category c "
			+ "WHERE MONTH(t.transactionDate) = MONTH(?1) AND YEAR(t.transactionDate) = YEAR(?1) " //and (c.id = t.category.id or c.id is null)"
			+ "group by c.name "
			+ "order by c.name")
	//@Query(value = "select (name as categoryName, sum(amount*1.0) as total, sum(amount*1.0)) as budget from TRANSACTIONS, CATEGORIES WHERE MONTH(transaction_date) = MONTH(?1) AND YEAR(transaction_date) = YEAR(?1) and CATEGORIES.id = TRANSACTIONS.CATEGORY_ID group by categoryname",
	//nativeQuery = true)
	List <CategorySum> queryCategorySumDetailByMonth(String dateStr); //dateStr to be 'YYYY-MM-DD'
	@Query(value = "select new expenstrak.domain.CategoryTotalAmount(c.name as categoryName, sum(t.amount*1.0) as total) "
			+ "from TRANSACTIONS t left join t.category c "
			+ "WHERE MONTH(t.transactionDate) = MONTH(?1) AND YEAR(t.transactionDate) = YEAR(?1) " //and (c.id = t.category.id or c.id is null)"
			+ "group by c.name "
			+ "order by c.name")
	//@Query(value = "select (name as categoryName, sum(amount*1.0) as total, sum(amount*1.0)) as budget from TRANSACTIONS, CATEGORIES WHERE MONTH(transaction_date) = MONTH(?1) AND YEAR(transaction_date) = YEAR(?1) and CATEGORIES.id = TRANSACTIONS.CATEGORY_ID group by categoryname",
	//nativeQuery = true)
	List <CategoryTotalAmount> queryCategoryTotalAmountByMonth(String dateStr); //dateStr to be 'YYYY-MM-DD'
}
