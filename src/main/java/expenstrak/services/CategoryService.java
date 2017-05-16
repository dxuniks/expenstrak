package expenstrak.services;

import java.util.List;

import org.springframework.stereotype.Component;

import expenstrak.domain.Category;

public interface CategoryService {
	public List<Category> getAllCategory();
	
	public Category addCategory(Category category);
	public void deleteCategory(Category category);
	public void deleteCategory(long categoryId);
	public Category updateCategory(long categoryId, Category category);
	public Category getCategory(long categoryId);	

}
