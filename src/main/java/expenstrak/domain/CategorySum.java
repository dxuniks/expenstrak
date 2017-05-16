package expenstrak.domain;

import javax.persistence.Entity;


public class CategorySum {
	private String categoryName;
	private long transactionCount;
	private double total;
	private double budget;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	
	@Override
	public String toString() {
		return "CategorySum [categoryName=" + categoryName + ", transactionCount=" + transactionCount + ", total="
				+ total + ", budget=" + budget + "]";
	}
	
	protected CategorySum() {}
	
	public CategorySum(String categoryName, double total, double budget, long transactionCount) {
		super();
		this.categoryName = categoryName;
		this.total = total;
		this.budget = budget;
		this.transactionCount = transactionCount;
	}
	public long getTransactionCount() {
		return transactionCount;
	}
	public void setTransactionCount(long transactionCount) {
		this.transactionCount = transactionCount;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
}
