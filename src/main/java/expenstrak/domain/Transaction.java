package expenstrak.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "TRANSACTIONS")
public class Transaction {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn (name = "ACCOUNT_ID", nullable = false)
	private Account account;
	
	@ManyToOne
	@JoinColumn (name = "USER_ID", nullable = true)
	private User user;
	
	@ManyToOne 
	@JoinColumn (name = "CATEGORY_ID", nullable = false, columnDefinition = "default '0'")
	private Category category;
	
	@Column (name = "DESCRIPTION", length = 512)
	private String description;
	
	@Column (name = "AMOUNT", nullable=false, columnDefinition="Decimal(10,2)")
	private float amount;
	
	@Column (name ="TRANSACTION_DATE", nullable = false, columnDefinition="DATE DEFAULT '0000-00-00'")
	@Temporal(value = TemporalType.DATE)
	private Date transactionDate;
	
	@Column(name="LAST_UPDATED", 
			updatable=false,
			columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdate;

	protected Transaction () {}
	
	public Transaction(Account account,  Category category, User user, float amount, String description, Date transactionDate) {
		super();
		this.account = account;
		this.description = description;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.category = category;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", account=" + account + ", user=" + user + ", category=" + category
				+ ", description=" + description + ", amount=" + amount + ", transactionDate=" + transactionDate
				+ ", lastUpdate=" + lastUpdate + "]";
	}
			
}
