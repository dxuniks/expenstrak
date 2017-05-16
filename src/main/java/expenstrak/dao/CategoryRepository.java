package expenstrak.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import expenstrak.domain.Account;
import expenstrak.domain.Category;
import expenstrak.domain.User;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	List <Category> findAllByOrderByName();
	
}
