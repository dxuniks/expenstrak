package expenstrak.domain;

public class CategoryTotalAmount {
	private String categoryName;
	private double total;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public CategoryTotalAmount(String categoryName, double total) {
		super();
		this.categoryName = categoryName;
		this.total = total;
	}
	@Override
	public String toString() {
		return "CategorySummaryOnAmount [categoryName=" + categoryName + ", total=" + total + "]";
	}
	
	
	

}
