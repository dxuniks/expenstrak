package expenstrak.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import expenstrak.domain.Category;
import expenstrak.services.CategoryService;

@Component("CategoryServiceDAOBean")
public class CategoryServiceDAO implements CategoryService {
	
	private final CategoryRepository categoryRepo;
	
	@Autowired
	public CategoryServiceDAO(CategoryRepository repo) {
		this.categoryRepo = repo;
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return (List<Category>) categoryRepo.findAllByOrderByName();
	}

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryRepo.save(category);
	}

	@Override
	public void deleteCategory(Category category) {
		// TODO Auto-generated method stub
		categoryRepo.delete(category);
	}

	@Override
	public void deleteCategory(long categoryId) {
		// TODO Auto-generated method stub
		categoryRepo.delete(categoryId);
	}

	@Override
	public Category updateCategory(long categoryId, Category category) {
		// TODO Auto-generated method stub
		category.setId(categoryId);
		return categoryRepo.save(category);
	}

	@Override
	public Category getCategory(long categoryId) {
		// TODO Auto-generated method stub
		return categoryRepo.findOne(categoryId);
	}

}
