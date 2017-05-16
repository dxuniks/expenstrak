package expenstrak.dao;

import java.util.List;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import expenstrak.domain.Account;
import expenstrak.domain.User;

@Component
public interface AccountRepository extends CrudRepository<Account, Long> {
	List <Account> findAccountByUser(User user);
	List <Account> findAccountByOrderByName();
	
	@Transactional
	List <Account> deleteAccountByUser(User user);
}
