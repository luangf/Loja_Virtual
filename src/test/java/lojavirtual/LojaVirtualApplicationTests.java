package lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;
import lojavirtual.controller.AcessoController;
import lojavirtual.model.Acesso;
import lojavirtual.repository.AcessoRepository;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class LojaVirtualApplicationTests extends TestCase {

	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext wac;
	
	//teste do endpoint de salvar
	@Test
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder=MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc=builder.build();
		
		Acesso acesso=new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR");
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		ResultActions retornoApi=mockMvc.perform(MockMvcRequestBuilders.post("/salvarAcesso")
										.content(objectMapper.writeValueAsString(acesso))
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON));
		
//		System.out.println("Retorno da API: "+retornoApi.andReturn().getResponse().getContentAsString());
		
		//converter o retorno da api para um objeto de acesso
		Acesso objetoRetorno=objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
	}
	
	//teste do endpoint de deletar por objeto
	@Test
	public void testRestApiDeletaAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder=MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc=builder.build();
		
		Acesso acesso=new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE");
		
		acesso=acessoRepository.save(acesso);
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		ResultActions retornoApi=mockMvc.perform(MockMvcRequestBuilders.delete("/deletarAcesso")
										.content(objectMapper.writeValueAsString(acesso))
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API: "+retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de Retorno: "+retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso removido", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}
	
	// teste do endpoint de deletar por id
	@Test
	public void testRestApiDeletaAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE_POR_ID");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders
				.delete("/deletarAcessoPorId/" + acesso.getId()).content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de Retorno: " + retornoApi.andReturn().getResponse().getStatus());

		assertEquals("Acesso removido", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}
	
	// teste do endpoint de obter por id
	@Test
	public void testRestApiObterAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_OBTER_ACESSO_POR_ID");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders
				.get("/obterAcesso/" + acesso.getId()).content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de Retorno: " + retornoApi.andReturn().getResponse().getStatus());

		Acesso acessoRetorno=objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}
	
	@Test
	public void testRestApiObterAcessoDesc() throws JsonProcessingException, Exception {
	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    Acesso acesso = new Acesso();
	    acesso.setDescricao("ROLE_TESTE_OBTER_LIST");
	    
	    acesso = acessoRepository.save(acesso);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions retornoApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.get("/buscarPorDesc/OBTER_LIST")
	    						 .content(objectMapper.writeValueAsString(acesso))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	    
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	    
	    List<Acesso> retornoApiList = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(),
	    									                 new TypeReference<List<Acesso>>(){});
	    
	    assertEquals(1, retornoApiList.size());
	    assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());
	    
	    acessoRepository.deleteById(acesso.getId());
	}
	
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
