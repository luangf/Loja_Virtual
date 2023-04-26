package lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lojavirtual.service.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {
	// Cliente faz o pedido de autenticação/autorização para o servidor e o server retorna o token/jwt(json web token) para o cliente
	// Ai o cliente dps de receber o JWT tem q mandar ele junto das requsições, ai o servidor diz ok, processei... tudo q fizer de requisição tem q mandar o token
	//Authorization Bearer dsflgnlçsadlk3204709832079432#@!@!#$%$.,,;.
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	//Método da parte de segurança do Spring
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		
		/* redireciona ou da um retorno para index quando desloga*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*mapeia o logout do sistema*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		/*Filtra as requisicoes para login de JWT*/
		.and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)
		
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	// Consultar user no banco com spring security
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	// Ignora algumas URLs: livres de autenticação
	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso")
//		              .antMatchers(HttpMethod.POST, "/salvarAcesso")
//					  .antMatchers(HttpMethod.DELETE, "/deletarAcesso");
	}
	
}
