package lojavirtual.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lojavirtual.ApplicationContextLoad;
import lojavirtual.model.Usuario;
import lojavirtual.repository.UsuarioRepository;

//Cria a autenticação e retona tbm a autenticação JWT
@Service
@Component
public class JWTTokenAutenticacaoService {

	// Token de validade de 11 dias
	private static final long EXPIRATION_TIME = 950400000;
	
	// Chave de senha para juntar com o JWT, senha boa/mt boa, pode ser exemplo: gerada..base 64/md5
	private static final String SECRET = "fsdnnfsdau893u823@!0-=0-sfdnnlkfsda";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	// Gera o token e da a responsta para o cliente o com JWT
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		
		//... Montagem do Token ...//
		
		String JWT = Jwts.builder() // Chama o gerador de token
				.setSubject(username) // Adiciona o user
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //Tempo de expiração
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		// Separado em 3 partes, 2 pontos; usuario, senha, data de expiração
		// Ex: Bearer fdfgidsu849kjod0.dgf87gsdfg78fsd780dfdfs.gsdf78g68gfdshu08husd
		String token = TOKEN_PREFIX + " " + JWT;
		
		// Dá a resposta pra tela/cabeçalho e para o cliente, outra API, navegador, aplicativo, javascript, outra chamada java...
		response.addHeader(HEADER_STRING, token);
		
		liberacaoCors(response);
		
		// Usado para ver no Postman para teste / Corpo da resposta
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
		
	}
	
	// Retorna o usuário validado com token ou caso nao seja valido retona null
	public Authentication getAuthetication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_STRING); //pega token; bearer fsa.dafd.sa....
		
		try {
			if (token != null) {
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim(); //sem o Bearer e sem espaço; fsa.dafd.sa....
				
				// Faz a validacao do token do usuário na requisicao e obtem o user
				String user = Jwts.parser() //Conversão
						.setSigningKey(SECRET) //Assinatura
						.parseClaimsJws(tokenLimpo)
						.getBody().getSubject(); //pega o user
				
				if (user != null) {
					Usuario usuario = ApplicationContextLoad
							.getApplicationContext()
							.getBean(UsuarioRepository.class).findUserByLogin(user);
					if (usuario != null) {
						return new UsernamePasswordAuthenticationToken(
								usuario.getLogin(),
								usuario.getSenha(), 
								usuario.getAuthorities());
					}
					
				}
			}
		}catch (SignatureException e) {
			response.getWriter().write("Token está inválido.");
		}catch (ExpiredJwtException e) {
			response.getWriter().write("Token está expirado, efetue o login novamente.");
		}finally {
			liberacaoCors(response);
		}
		
		return null;
	}
	
	// Fazendo liberação contra erro de Cors no navegador
	public void liberacaoCors(HttpServletResponse response) {
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}
	
}
