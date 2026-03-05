package StronaSklepowa.Uczelnia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import StronaSklepowa.Uczelnia.Entities.Product;
import StronaSklepowa.Uczelnia.Repositories.ProductRepository;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UczelniaApplication {
    public static void main(String[] args) {
        SpringApplication.run(UczelniaApplication.class, args);
    }
	@Bean
	CommandLineRunner initDatabase(ProductRepository repository) {
		return args -> {
			Product p1 = new Product();
			p1.setName("Laptop Gamingowy");
			p1.setPrice(4500.0);
			p1.setDescription("Super szybki laptop");
			repository.save(p1);
		};
	}
}
