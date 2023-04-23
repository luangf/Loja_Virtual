package lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@ResponseBody
	@PostMapping(value = "**/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {
		return new ResponseEntity<Acesso>(acessoService.save(acesso), HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deletarAcesso")
	public ResponseEntity<?> deletarAcesso(@RequestBody Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso removido", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deletarAcessoPorId/{id}")
	public ResponseEntity<?> deletarAcesso(@PathVariable("id") Long id) {
		acessoRepository.deleteById(id);
		return new ResponseEntity("Acesso removido", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value="**/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id){
		return new ResponseEntity<Acesso>(acessoRepository.findById(id).get(), HttpStatus.OK);
	}
	
}
