package expenstrak;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ExpensTrakApplication {
	//private static final Logger log = LoggerFactory.getLogger(Test04Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ExpensTrakApplication.class, args);	
//		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("user.xml")){
//			//UserService userService = context.getBean(UserService.class);
//			
//		}
	}
	
//	@Bean
//	public CommandLineRunner dummy(UserRepository userRepo) {
//		return (args)-> {
//			log.info("Adding dummy data");
//			userRepo.save(new User("Tom@usa.com", "Smith",  "Tom"));
//			userRepo.save(new User("Joe@usa.com", "Dick",  "Joe"));
//			
//			log.info("List all dummy data");
//			for(User user : userRepo.findAll()) {
//				log.info(user.toString());
//			}
//		};
//	}
}
