package expenstrak.restcontrollers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import expenstrak.ApiResponse;
import expenstrak.domain.CategorySum;
import expenstrak.domain.CategoryTotalAmount;
import expenstrak.domain.Transaction;
import expenstrak.services.TransactionService;


@RestController
@RequestMapping("/rest/analysis")
public class AnalysisRestController {
	private static final Logger log = LoggerFactory.getLogger(AnalysisRestController.class);
	
	private final TransactionService transactionService;
	
	public AnalysisRestController (@Qualifier("TransactionServiceDAOBean") TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@RequestMapping(value="/categorytotalamount/{year}/{month}", method = RequestMethod.GET)
	public ApiResponse getCategorySumByMonth(@PathVariable("year") String year,  @PathVariable("month") String month) {
		log.info("Get CategorySumByMonth.");
		ArrayList<CategoryTotalAmount> categorySumList = (ArrayList<CategoryTotalAmount>) transactionService.getCategoryTotalAmountByMonth(year + "-" + month + "-01");
		return new ApiResponse( "OK", "browse transactions", 
				"Account", categorySumList);}	
	
}
