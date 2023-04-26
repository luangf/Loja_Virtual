package lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@SpringBootApplication
@EntityScan(basePackages = "lojavirtual.model")
@ComponentScan(basePackages = {"lojavirtual.*"})
@EnableJpaRepositories(basePackages = {"lojavirtual.repository"})
@EnableTransactionManagement
@EnableWebMvc
public class LojaVirtualApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123"));
		
		SpringApplication.run(LojaVirtualApplication.class, args);
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
		
		viewResolver.setPrefix("classpath:template/");
		viewResolver.setSuffix(".html");
		
		return viewResolver;
	}
	
}
