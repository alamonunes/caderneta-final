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
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.AlunoRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.DisciplinaRepository;


@Controller
@RequestMapping( value = "/aluno")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	HttpSession session;

	@GetMapping
	public String exibirAluno(Model model) {
		Aluno aluno = (Aluno) session.getAttribute("aluno");
		model.addAttribute("aluno", alunoRepository.findById(aluno.getId()));
		try {
			List<Disciplina> disciplinas = disciplinaRepository.findDisciplinaByTurma(aluno.getTurma().getId());
			model.addAttribute("disciplinas", disciplinas);
			return "aluno/IndexAluno";
		}
		catch(NullPointerException e) {
			return "aluno/IndexAlunoVazio";
		}
	}

	@GetMapping("list/{id}")
	public String listId(@PathVariable("id") Integer id, Model model) {
		Optional<Aluno> usuario = alunoRepository.findById(id);     
		model.addAttribute("aluno",usuario);
		return "alunoHome";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, Model model) {
		Optional<Aluno> usuarioOpt = alunoRepository.findById(id);
		Aluno usuario = usuarioOpt.get();
		alunoRepository.delete(usuario);
		model.addAttribute("aluno", alunoRepository.findAll());  
		return "redirect:/coordenador/listAlunos";   

	}	
}
