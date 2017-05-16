package expenstrak.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity (name = "users")
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToMany(mappedBy="user" ,cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Account> accounts;
	
	@Column
	private String firstName;

	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false, unique = true)
	private String emailAddress;
	
//	@Column (name = "password")
//	private String password;
	
	@Column(name="last_updated", 
			updatable=false,
			columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}
	
	public long getId () {
		return this.id;
	}
	
	public void setId (long id) {
		this.id = id;
	}

	protected User() {}
	
	public User (String emailAddress, String lastName, String firstName) {
		this.id = 0;
		this.lastName = lastName;
		this.firstName = firstName;
		this.emailAddress = emailAddress;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString () {
		return "ID=[" + this.id + "] lastname=[" + this.lastName + "] firstname= [" + this.firstName + "] email = [" + this.emailAddress + "]";
	}


}

