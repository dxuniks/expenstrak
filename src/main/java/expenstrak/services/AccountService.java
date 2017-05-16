package expenstrak.services;

import java.util.List;

import expenstrak.domain.Account;
import expenstrak.domain.User;

public interface AccountService {
	public Account addAccount(Account account);
	public Account getAccount(long accountId);
	public Account updateAccount(long accountId, Account account);
	public void deleteAccount(long accountId);
	public List<Account> deleteAccountsByUser(User user);
	public List<Account> getAllAccounts();
	public List<Account> getAccountByUser(User user);	
}
