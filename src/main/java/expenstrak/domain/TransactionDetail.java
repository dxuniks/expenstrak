package expenstrak.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity (name = "TransactionDetails")
public class TransactionDetail {
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne
	@JoinColumn (name = "TRANSACTION_ID")
	private Transaction transaction;
	
	@Column (length = 4096)
	private String notes;
	

}
