package expenstrak.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import expenstrak.services.AccountService;
import expenstrak.services.CategoryService;
import expenstrak.services.UserService;

public class TransactionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7664755724322879465L;

	private long id;
	private long accountId;
	private long userId;
	private long categoryId;
	private float amount;
	private String description;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT-5")
	private Date transactionDate;
	
	protected TransactionDTO () {}
	
	public TransactionDTO (Transaction transaction) {
		this.id = transaction.getId();
		this.accountId = transaction.getAccount().getId();
		this.amount = transaction.getAmount();
		this.description = transaction.getDescription();
		this.transactionDate = transaction.getTransactionDate();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
		
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public Transaction convertToTransaction(AccountService accountService, CategoryService categoryService, UserService userService) {
		return new Transaction(accountService.getAccount(this.accountId), categoryService.getCategory(this.categoryId), userService.getUser(this.userId), this.amount, this.description, this.transactionDate);
		
	}

	@Override
	public String toString() {
		return "TransactionDTO [id=" + id + ", accountId=" + accountId + ", userId=" + userId + ", categoryId="
				+ categoryId + ", amount=" + amount + ", description=" + description + ", transactionDate="
				+ transactionDate + "]";
	}
	
}
