package expenstrak.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "ACCOUNTS")
public class Account {

	@Id
	@GeneratedValue
	//@Column//(name="ACCOUNT_ID")
	private long id;
	
	@Column(name="NAME", nullable = false)
	private String name;
	
	@Column(name="DESCRIPTION", nullable = true)
	private String description;
	
	@Column(name="FLOW_BALANCE", nullable = false, columnDefinition="Decimal(10,2) default '0.00'")
	private float flowBalance;
	
	@ManyToOne//(cascade = CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Column(name="last_updated",
			updatable=false,
			columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	protected Account() {}
	
	public Account(Account account) {
		this.id = account.getId();
		this.name = account.getName();
		this.user = account.getUser();
		this.description = account.getDescription();
		this.flowBalance = account.getFlowBalance();			
	}
	
	public Account(String name, String description, float flowBalance, User user) {
		super();
		this.id = 0;
		this.name = name;
		this.description = description;
		this.flowBalance = flowBalance;
		this.user = user;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public long getId() {
		return id;
	}

	public void setId (long id) {
		this.id = id;
	}

	public float addTransaction(float amount) {
		this.flowBalance = this.flowBalance + amount;
		return this.flowBalance;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", description=" + description + ", flowBalance=" + flowBalance
				+ ", user=" + user + ", lastUpdate=" + lastUpdate + "]";
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

}
