package com.fpe.school.controllers;

import com.fpe.school.entities.Aluno;
import com.fpe.school.services.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("alunos", alunoService.findAll());
        return "lista-alunos";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoService.findById(id);
        return aluno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam String nome, Model model) {
        model.addAttribute("alunos", alunoService.findByName(nome));
        return "lista-alunos";
    }

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro() {
        return "cadastro-aluno";
    }

    @PostMapping("/salvar")
    public String salvarAluno(
            @RequestParam String nome,
            @RequestParam Integer idade,
            @RequestParam String genero,
            @RequestParam Double altura,
            @RequestParam String entidade,
            @RequestParam(required = false) String talentos,
            @RequestParam(required = false) String habilidades) {

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setIdade(idade);
        aluno.setGenero(genero);
        aluno.setAltura(altura);
        aluno.setEntidade(entidade);

        if (talentos != null && !talentos.isEmpty()) {
            aluno.setTalentos(Arrays.asList(talentos.split(",")));
        }

        if (habilidades != null && !habilidades.isEmpty()) {
            aluno.setHabilidades(Arrays.asList(habilidades.split(",")));
        }

        alunoService.salvar(aluno);
        return "redirect:/alunos/cadastro";
    }

    @GetMapping("/editar/{id}")
    public String editarAluno(@PathVariable Long id, Model model) {
        Optional<Aluno> aluno = alunoService.findById(id);
        if (aluno.isEmpty()) {
            return "redirect:/alunos/listar?erro=not_found";
        }
        model.addAttribute("aluno", aluno.get());
        return "editar-aluno";
    }

    @PostMapping("/atualizar")
    public String atualizarAluno(@ModelAttribute Aluno aluno) {
        alunoService.atualizar(aluno.getId(), aluno);
        return "redirect:/alunos/listar?sucesso=atualizado";
    }

    @GetMapping("/listar")
    public String listarAlunos(Model model) {
        List<Aluno> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);
        return "listar-alunos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirAluno(@PathVariable Long id) {
        alunoService.deletar(id);
        return "redirect:/alunos/listar?sucesso=aluno_excluido";
    }
}
