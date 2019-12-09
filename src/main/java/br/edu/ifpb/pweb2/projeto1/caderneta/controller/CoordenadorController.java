
package br.edu.ifpb.pweb2.projeto1.caderneta.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Aluno;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Disciplina;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Professor;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Turma;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.AlunoRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.DisciplinaRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.ProfessorRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.TurmaRepository;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	HttpSession session;

	@GetMapping
	public String exibirCoordenador(Model model) {
		Professor coordenador = (Professor) session.getAttribute("coordenador");
		model.addAttribute("coordenador", professorRepository.findById(coordenador.getId()));
		return "/coordenador/CoordenadorPrincipal";
	}

	@RequestMapping("/cadastrardisciplina")
	public String formDisciplina(Disciplina disciplina, Model model) {
		model.addAttribute(disciplina);
		return "disciplina/FormDisciplina";
	}

	@PostMapping("cadastrardisciplina")
	public String cadastrarDisciplina(Disciplina disciplina, String nome, String codigo, String curso, Integer carga){
		disciplina.setNome(nome);
		disciplina.setCodigo(codigo);
		disciplina.setCurso(curso);
		disciplina.setCarga(carga);
		disciplinaRepository.save(disciplina);

		return "redirect:/coordenador";
	}

	@RequestMapping("/cadastraraluno")
	public String formAluno(Aluno alu, Model model) {
		model.addAttribute(alu);
		return "aluno/FormAluno";
	}

	@PostMapping("cadastraraluno")
	public String cadastrarAluno(Aluno alu, String login, String matricula, String nome, String senha){
		alu.setNome(nome);
		alu.setLogin(login);
		alu.setMatricula(matricula);
		alu.setSenha(senha);
		alunoRepository.save(alu);

		return "redirect:/coordenador/listAlunos";
	}
	
	@RequestMapping("/cadastrarprofessor")
	public String formProfessor(Professor prof, Model model) {
		model.addAttribute(prof);
		return "professor/FormProfessor";
	}

	@PostMapping("cadastrarprofessor")
	public String cadastrarProfessor(Professor prof, String login, String senha, String nome, String email){
		prof.setCoordenador(false);
		prof.setEmail(email);
		prof.setLogin(login);
		prof.setNome(nome);
		prof.setSenha(senha);
		System.out.println(prof);
		professorRepository.saveAndFlush(prof);
		
		return "redirect:/coordenador";
	}
	
	@GetMapping("/listAlunos")
	public String listarAluno(Model model) {
		model.addAttribute("aluno", this.alunoRepository.findAll());
		return "coordenador/listAlunos";
	}
	
	@GetMapping("/listProfessor")
	public String listarProfessor(Model model) {
		model.addAttribute("professor", this.professorRepository.findAll());
		return "coordenador/listProfessor";
	}
	
	@GetMapping("/listDisciplina")
	public String listarDisciplinas(Model model) {
		model.addAttribute("disciplinas", this.disciplinaRepository.findAll());
		return "coordenador/listDisciplinas";
	}

	@RequestMapping("/cadastrarturma")
	public String formTurma(Turma turma, Model model) {
		model.addAttribute(turma);
		return "turma/FormTurma";
	}

	@PostMapping("cadastrarturma")
	public String adicionarTurma(Turma turma, Integer codigo){
		turma.setCodigo(codigo);
		turmaRepository.save(turma);

		return "redirect:/coordenador";
	}

}
