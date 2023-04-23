package lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import junit.framework.TestCase;
import lojavirtual.controller.AcessoController;
import lojavirtual.model.Acesso;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class LojaVirtualApplicationTests extends TestCase {

	@Autowired
	private AcessoController acessoController;
	
	@Test
	public void testeCadastraAcesso() {
		Acesso acesso=new Acesso();
		
		acesso.setDescricao("ROLE_ADMIN");
		
		assertEquals(true, acesso.getId() == null);
		
		acesso=acessoController.salvarAcesso(acesso).getBody();
		
		assertEquals(true, acesso.getId() > 0); //expected, actual
		
		assertEquals("ROLE_ADMIN", acesso.getDescricao());
	}

}
