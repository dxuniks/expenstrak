package expenstrak.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import expenstrak.domain.Account;
import expenstrak.domain.User;
import expenstrak.services.AccountService;
import expenstrak.services.UserService;

@Component("AccountServiceDAOBean")
public class AccountServiceDAO implements AccountService {
	private static final Logger log = LoggerFactory.getLogger(AccountServiceDAO.class);
	
	@Autowired
	AccountRepository accountRepo;
	
	@Override
	public Account getAccount(long id) {
		// TODO Auto-generated method stub
		return accountRepo.findOne(id);
	}

	@Override
	@Transactional
	public Account addAccount(Account account) {
		// TODO Auto-generated method stub
		log.info("Adding new account [" + account.toString() + "]");
		Account newAccount = accountRepo.save(account);
		log.info("ADDED ACCOUNT: " + newAccount.toString());
		return newAccount;
	}

	@Override
	public List<Account> getAccountByUser(User user) {
		// TODO Auto-generated method stub
		return (List<Account>) accountRepo.findAccountByUser(user);
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return (List<Account>) accountRepo.findAccountByOrderByName();
	
	}
	
	@Override
	public void deleteAccount(long accountId) {
		accountRepo.delete(accountId);
		log.info("DELETED ACCOUNT: " + accountId);		
	}
	
	@Override
	public List<Account> deleteAccountsByUser(User user) {
		return accountRepo.deleteAccountByUser(user);
	}

	protected void printAll() {
		for(Account account : accountRepo.findAll()) {
			log.info(account.toString());
		}
	}

	@Override
	public Account updateAccount(long accountId, Account account) {
		// TODO Auto-generated method stub
		log.info("UPDATE ACCOUNT: BEFORE: " + accountRepo.findOne(accountId).toString());
		Account oldAccount = new Account(account);
		oldAccount.setId(accountId);
		Account newAccount = accountRepo.save(oldAccount);
		log.info("UPDATE USER: AFTER: " + newAccount.toString());
		return newAccount;
	}

}
