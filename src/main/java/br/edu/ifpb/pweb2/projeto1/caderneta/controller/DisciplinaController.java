package br.edu.ifpb.pweb2.projeto1.caderneta.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Disciplina;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Professor;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.DisciplinaRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.ProfessorRepository;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;	
	
	@Autowired
	private ProfessorRepository professorRepository;	
	
	@Autowired
	HttpSession session;

    
    @GetMapping
    public String listarDisc(Model model) {
        model.addAttribute("disciplinas", this.disciplinaRepository.findAll());
        System.out.println(model);
        return "disciplina/index";
    }
    
    @PostMapping
    public String addDisc(@ModelAttribute Disciplina disc) {
        this.disciplinaRepository.save(disc);
        return "redirect:disciplinas/";
    }
    
    @GetMapping("/showDisc")
    public String inserirCondutorView(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplina/discform";
    }
    
	 @GetMapping("delete/{id}")
	    public String delete(@PathVariable("id") Integer id, Model model) {
		 	Optional<Disciplina> discOpt = disciplinaRepository.findById(id);
		 	Disciplina disc = discOpt.get();
	        disciplinaRepository.delete(disc);
		    model.addAttribute("disciplina", disciplinaRepository.findAll());  
	        return "redirect:/coordenador";
	  }
    
	 @RequestMapping("/adicionarprofessor/{id}")
		public String adcProfessor(@PathVariable Integer id, Model model) {
			Optional<Disciplina> d = disciplinaRepository.findById(id);
			Disciplina disc = null;
			if(d.isPresent())
				disc = d.get();
			Professor professor = new Professor();
			model.addAttribute(disc);
			model.addAttribute(professor);
			return "disciplina/FormAdicionarProfessor";
		}

		@PostMapping("/adicionarprofessor/{id}")
		public String adcprofPost(@PathVariable Integer id, Disciplina disc, String login, Model model) {
			Optional<Disciplina> d = disciplinaRepository.findById(disc.getId());
			Disciplina disciplina = d.get();
			Professor prof = professorRepository.findByLogin(login);
			prof.addDisciplina(disciplina);
			disciplina.setProfessor(prof);
			
			disciplinaRepository.saveAndFlush(disciplina);
			professorRepository.saveAndFlush(prof);
			return "redirect:/coordenador";
		}
    
}
