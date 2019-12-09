package br.edu.ifpb.pweb2.projeto1.caderneta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
		
	Disciplina findByCodigo(String codigo);
	Disciplina findByNome(String nome);
	
	@Query(value = "SELECT * FROM Disciplina WHERE turma_id = :id", 
			  nativeQuery = true)
	List<Disciplina> findDisciplinaByTurma(@Param("id")int id);
	
	@Query(value = "SELECT * FROM Disciplina WHERE professor_id = :id", 
			  nativeQuery = true)
	List<Disciplina> findDisciplinaByProfessor(@Param("id")int id);
}
