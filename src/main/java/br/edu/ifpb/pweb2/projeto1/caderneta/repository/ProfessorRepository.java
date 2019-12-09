package br.edu.ifpb.pweb2.projeto1.caderneta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
		
	Professor findById(int id);
	Professor findByLogin(String login);
	
}
