package expenstrak.domain;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import expenstrak.services.UserService;

public class AccountDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3671285087882338670L;
	
	private long id;
	private String name;
	private String description;
	private float flowBalance;
	private User user;
	
	protected AccountDTO () {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	//private UserDTO user;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getFlowBalance() {
		return flowBalance;
	}
	public void setFlowBalance(float flowBalance) {
		this.flowBalance = flowBalance;
	}
//	public UserDTO getUser() {
//		return user;
//	}
//	public void setUser(UserDTO user) {
//		this.user = user;
//	}		
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.name = account.getName();
		this.description = account.getDescription();
		this.flowBalance = account.getFlowBalance();
		this.user = account.getUser();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Account convertToAccount() { 
		return new Account(this.name, this.description, this.flowBalance, this.user);
	}

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", name=" + name + ", description=" + description + ", flowBalance="
				+ flowBalance + ", user=" + user + "]";
	}
	
}
