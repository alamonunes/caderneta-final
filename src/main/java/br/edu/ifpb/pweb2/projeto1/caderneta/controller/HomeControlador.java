package br.edu.ifpb.pweb2.projeto1.caderneta.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControlador {

    @GetMapping("/home")
    public String exibirHome() {
        return "home";
    }
    
    @GetMapping("/")
    public String exibirHome2() {
        return "redirect:login";
    }
}
