package expenstrak.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import expenstrak.domain.User;

@Component
public interface UserRepository extends CrudRepository<User, Long> {
	List <User> findAllByOrderByLastName();

}
