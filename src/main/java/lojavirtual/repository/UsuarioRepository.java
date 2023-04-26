package lojavirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lojavirtual.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	//o db so permite um login unico
	@Query(value = "select u from Usuario u where u.login=?1")
	Usuario findUserByLogin(String login);
	
}
