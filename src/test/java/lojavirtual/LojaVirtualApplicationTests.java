package lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lojavirtual.model.Acesso;
import lojavirtual.repository.AcessoRepository;
import lojavirtual.service.AcessoService;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests {

	@Autowired
	private AcessoService acessoService;
	
	//@Autowired
	//private AcessoRepository acessoRepository;
	
	@Test
	public void testeCadastraAcesso() {
		
		Acesso acesso=new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		acessoService.save(acesso);
		
	}

}
