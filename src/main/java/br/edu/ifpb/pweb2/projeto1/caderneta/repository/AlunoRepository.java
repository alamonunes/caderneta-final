package br.edu.ifpb.pweb2.projeto1.caderneta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer>{
		Aluno findById(int id);
		Aluno findByLogin(String login);
		
}
