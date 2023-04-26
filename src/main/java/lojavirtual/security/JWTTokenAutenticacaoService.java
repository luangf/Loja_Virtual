package lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
		
		// Usado para ver no Postman para teste / Corpo da resposta
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
		
	}
	
}
