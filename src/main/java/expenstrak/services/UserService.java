package expenstrak.services;

import java.util.List;

import expenstrak.domain.User;

public interface UserService {
	public User getUser(long id);
	public User addUser(User user);
	public User updateUser(long id, User user);
	public void deleteUser(long id);
	public List<User>  getAllUser();
}
