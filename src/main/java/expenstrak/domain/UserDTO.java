package expenstrak.domain;

import java.io.Serializable;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 2692392091049384L;
	
	private long id;
	private String lastName;
	private String firstName;
	private String emailAddress;
	
	protected UserDTO () {}
	
	public UserDTO (User user) {
		this.id = user.getId();
		this.lastName = user.getLastName();
		this.firstName = user.getFirstName();
		this.emailAddress = user.getEmailAddress();
	}
	
	public String getLastName(){
		return this.lastName;
	}

	public String getfirstName(){
		return this.firstName;
	}

	public String getEmailAddress(){
		return this.emailAddress;
	}
	
	public User convertToUser() {
		if (this.id == -1) {
			this.id = 0;
		}
		return new User(this.emailAddress, this.lastName, this.firstName);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
}
