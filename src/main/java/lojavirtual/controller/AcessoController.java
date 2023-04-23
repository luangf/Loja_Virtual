package lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lojavirtual.model.Acesso;
import lojavirtual.repository.AcessoRepository;
import lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	//Recebe dados de uma tela
	@ResponseBody //Corpo da resposta
	@PostMapping(value = "**/salvarAcesso") // mapeando url para receber json / ** qualquer lugar/url q vir
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) { // @ResponseBody -> recebe JSON e converte para objeto nos param ai
		Acesso acessoSalvo=acessoService.save(acesso);
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deletarAcesso")
	public ResponseEntity<?> deletarAcesso(@RequestBody Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso removido", HttpStatus.OK);
	}
	
}
