package br.edu.ifpb.pweb2.projeto1.caderneta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer>{

}
