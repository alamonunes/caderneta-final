package br.edu.ifpb.pweb2.projeto1.caderneta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Aluno;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Professor;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String>{
	
	Usuario findByLogin(String login);
	Usuario findById(int id);
	
	
	@Query("select u from Usuario u where u.login = ?1 and Type(u) = 'Professor'")
	Professor findProfessorByLogin(String login);
	
	@Query("select u from Usuario u where u.login = ?1 and Type(u) = 'Aluno'")
	Aluno findAlunoByLogin(String login);
	
	@org.springframework.data.jpa.repository.Query("select u from Usuario u where Typy(u)= 'Aluno'")
	List<Usuario> findByPerfilAluno();
	
}
