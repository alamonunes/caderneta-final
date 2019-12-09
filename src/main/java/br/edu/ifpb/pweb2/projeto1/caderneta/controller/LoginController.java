package br.edu.ifpb.pweb2.projeto1.caderneta.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifpb.pweb2.projeto1.caderneta.model.Aluno;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Professor;
import br.edu.ifpb.pweb2.projeto1.caderneta.model.Usuario;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.AlunoRepository;
import br.edu.ifpb.pweb2.projeto1.caderneta.repository.ProfessorRepository;

@Controller
@RequestMapping("/login")
public class LoginController {


	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@RequestMapping(method=RequestMethod.GET)
	public String loginForm(Model model, @CookieValue(value = "clogin", defaultValue = "") String clogin) {
		Usuario usuario = new Usuario();
		usuario.setLogin(clogin);
		model.addAttribute(usuario);	

		return "TelaInicial";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String valideLogin(Usuario usuario, HttpSession session, HttpServletRequest request) {
		String proxPagina = null;

		Aluno aluno = alunoRepository.findByLogin(usuario.getLogin());

		if(aluno == null) {
			Professor professor = professorRepository.findByLogin(usuario.getLogin());
			if(professor == null) {
				proxPagina = "redirect:/login";
			}
			else if(usuario.getLogin().equals(professor.getLogin()) && usuario.getSenha().equals(professor.getSenha()) && !professor.isCoordenador()) {
				session.setAttribute("professor", professor);
				proxPagina = "redirect:professor/";
			}
			else if(usuario.getLogin().equals(professor.getLogin()) && usuario.getSenha().equals(professor.getSenha()) && professor.isCoordenador()){
				session.setAttribute("coordenador", professor);
				proxPagina = "redirect:coordenador/";
			}	
		}
		else {
			if(usuario.getLogin().equals(aluno.getLogin()) && usuario.getSenha().equals(aluno.getSenha())) {

				session.setAttribute("aluno", aluno);
				proxPagina = "redirect:aluno/";

			}
		}
		return proxPagina;
	}


	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();
		return "redirect:/login";
	}


}
