package expenstrak.domain;

public class CategoryDTO {

	private static final long serialVersionUID = 3671285087882338675L;
	
	private long id;
	private String name;
	private String description;
	 
	protected CategoryDTO () {}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public CategoryDTO (Category category) {
		this.id = category.getId();
		this.name =category.getName();
		this.description = category.getDescription();
	}
	
	public Category convertToCategory() {
		return new Category(this.name, this.description);
	}
	
	
}
