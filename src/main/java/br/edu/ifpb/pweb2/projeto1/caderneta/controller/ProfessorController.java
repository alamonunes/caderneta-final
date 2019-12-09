package br.edu.ifpb.pweb2.projeto1.caderneta.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Aluno;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Disciplina;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Professor;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.AlunoRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.DisciplinaRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.ProfessorRepository;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	HttpSession session;

	@GetMapping
	public String exibirProfessor(Model model) {
		Professor professor = (Professor) session.getAttribute("professor");
		model.addAttribute("professor", professorRepository.findById(professor.getId()));
		try {
			List<Disciplina> disciplinas = disciplinaRepository.findDisciplinaByProfessor(professor.getId());
			model.addAttribute("disciplinas", disciplinas);
			return "professor/IndexProfessor";
		}
		catch(NullPointerException e) {
			return "professor/IndexProfessorVazio";
		}
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, Model model) {
		Optional<Professor> usuarioOpt = professorRepository.findById(id);
		Professor usuario = usuarioOpt.get();
		professorRepository.delete(usuario);
		model.addAttribute("professor", professorRepository.findAll());  
		return "redirect:/coordenador/listProfessor";   

	}	
	
	
}
