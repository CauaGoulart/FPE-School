package com.fpe.school.controllers;

import com.fpe.school.entities.Aluno;
import com.fpe.school.services.AlunoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AlunoService alunoService;

    public HomeController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Aluno> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);
        return "index";
    }

    @GetMapping("/professores/cartoes")
    public String verCartoesProfessores() {
        return "professores-cartoes";
    }

    @GetMapping("/cadastro-aluno")
    public String paginaCadastroAluno() {
        return "cadastro-aluno";
    }

    @GetMapping("/cadastro-professor")
    public String paginaCadastroProfessor() {
        return "cadastro-professor";
    }

    @GetMapping("/alice")
    public String paginaAlice() {
        return "alice";
    }

    @GetMapping("/dougal")
    public String paginaDougal() {
        return "dougal";
    }

}
