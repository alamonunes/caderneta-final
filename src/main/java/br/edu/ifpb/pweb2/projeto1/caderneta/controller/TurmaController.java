package br.edu.ifpb.pweb2.projeto1.caderneta.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Aluno;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Disciplina;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Turma;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.AlunoRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.DisciplinaRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.TurmaRepository;

@Controller
@RequestMapping("/turmas")
public class TurmaController {
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired
	HttpSession session;
	
	@GetMapping
    public String listarTurmas(Model model) {
        model.addAttribute("turmas", this.turmaRepository.findAll());
        return "turma/IndexTurma";
    }
	
	@RequestMapping("/matricularaluno/{id}")
	public String matricular(@PathVariable Integer id, Model model) {
		Optional<Turma> t = turmaRepository.findById(id);
		Turma turma = null;
		if(t.isPresent())
			turma = t.get();
		Aluno aluno = new Aluno();
		model.addAttribute(aluno);
		model.addAttribute(turma);
		return "turma/FormMatricularAluno";
	}

	@PostMapping("/matricularaluno/{id}")
	public String matricularPost(@PathVariable Integer id, Turma turma, String nome, Model model) {
		Optional<Turma> t = turmaRepository.findById(turma.getId());
		Turma tur = t.get();
		Aluno a = alunoRepository.findByLogin(nome);
		tur.addAluno(a);
		a.setTurma(tur);
		turmaRepository.saveAndFlush(tur);
		alunoRepository.saveAndFlush(a);
		return "redirect:/coordenador";
	}
	
	@RequestMapping("/adicionardisciplina/{id}")
	public String adicionarDisciplina(@PathVariable Integer id, Model model) {
		Optional<Turma> t = turmaRepository.findById(id);
		Turma turma = null;
		if(t.isPresent())
			turma = t.get();
		Disciplina disciplina = new Disciplina();
		model.addAttribute(disciplina);
		model.addAttribute(turma);
		return "turma/FormAdicionarDisciplina";
	}

	@PostMapping("/adicionardisciplina/{id}")
	public String adicionarDisciplinaPost(@PathVariable Integer id, Turma turma, String nome, Model model) {
		Optional<Turma> t = turmaRepository.findById(turma.getId());
		Turma tur = t.get();
		Disciplina disciplina = disciplinaRepository.findByNome(nome);
		tur.addDisciplina(disciplina);
		disciplina.setTurma(tur);
		turmaRepository.saveAndFlush(tur);
		disciplinaRepository.saveAndFlush(disciplina);
		return "redirect:/coordenador";
	}
	
}
