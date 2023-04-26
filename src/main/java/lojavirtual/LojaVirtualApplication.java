package lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EntityScan(basePackages = "lojavirtual.model")
@ComponentScan(basePackages = {"lojavirtual.*"})
@EnableJpaRepositories(basePackages = {"lojavirtual.repository"})
@EnableTransactionManagement
@EnableWebMvc
public class LojaVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualApplication.class, args);
	}

}
