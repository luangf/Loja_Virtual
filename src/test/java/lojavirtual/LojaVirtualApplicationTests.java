package lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import junit.framework.TestCase;
import lojavirtual.controller.AcessoController;
import lojavirtual.model.Acesso;
import lojavirtual.repository.AcessoRepository;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class LojaVirtualApplicationTests extends TestCase {

	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
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
