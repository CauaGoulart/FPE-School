package com.fpe.school.services;

import com.fpe.school.entities.Aluno;
import com.fpe.school.repositories.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional(readOnly = true)
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Aluno> findById(Long id) {
        return alunoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Aluno> findByName(String nome) {
        return alunoRepository.findByNameContainingIgnoreCase(nome);
    }

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno alunoAtualizado) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setNome(alunoAtualizado.getNome());
            aluno.setIdade(alunoAtualizado.getIdade());
            aluno.setGenero(alunoAtualizado.getGenero());
            aluno.setAltura(alunoAtualizado.getAltura());
            aluno.setEntidade(alunoAtualizado.getEntidade());
            aluno.setTalentos(alunoAtualizado.getTalentos());
            aluno.setHabilidades(alunoAtualizado.getHabilidades());
            return alunoRepository.save(aluno);
        }).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public void deletar(Long id) {
        alunoRepository.deleteById(id);
    }
}
