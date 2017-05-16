package expenstrak.restcontrollers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import expenstrak.ApiResponse;
import expenstrak.domain.Category;
import expenstrak.domain.CategoryDTO;
import expenstrak.domain.User;
import expenstrak.domain.UserDTO;
import expenstrak.services.CategoryService;
import expenstrak.services.UserService;

@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
	private static final Logger log = LoggerFactory.getLogger(CategoryRestController.class);
	
	private final CategoryService categoryService;
	
	@Autowired
	public CategoryRestController (@Qualifier("CategoryServiceDAOBean") CategoryService categoryService) {
		this.categoryService = categoryService;		
	}	
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResponse getAllCategory() {
		log.info("looking for all categories");
		return new ApiResponse("Ok", "All categories returned", "Category", categoryService.getAllCategory().stream().map(CategoryDTO::new).collect(Collectors.toList()) );
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse addCategory(@RequestBody CategoryDTO category) {
		log.info("Adding a new category: [" + category.toString() + "]");
		Category newCategory = categoryService.addCategory(category.convertToCategory());
		ArrayList<CategoryDTO> categoryList = new ArrayList<CategoryDTO> ();
		categoryList.add(new CategoryDTO(newCategory));
		return new ApiResponse  ("Ok", "Added", "Category", categoryList);
	}
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
	public ApiResponse deletecategory(@PathVariable("categoryId") long categoryId) {
		log.info("deleting for categoryID = [" + categoryId + "]");
		categoryService.deleteCategory(categoryId);
		return new ApiResponse("Ok", "Deleted", null, null);
	}	
	
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse updatecategory(@PathVariable("categoryId") long categoryId, @RequestBody CategoryDTO category) {
		log.info("Updating a category: [" + category.toString() + "]");		
		Category newcategory = categoryService.updateCategory(categoryId, category.convertToCategory());
		ArrayList<CategoryDTO> categoryList = new ArrayList<CategoryDTO> ();
		categoryList.add(new CategoryDTO(newcategory));
		return new ApiResponse("Ok", "Updated", "category", categoryList);
	}
	
}
