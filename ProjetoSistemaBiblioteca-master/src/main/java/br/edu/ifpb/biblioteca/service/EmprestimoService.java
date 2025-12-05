package br.edu.ifpb.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifpb.biblioteca.entity.Emprestimo;
import br.edu.ifpb.biblioteca.repository.EmprestimoRepository;

@Service
public class EmprestimoService {
    private final EmprestimoRepository repository;

    public EmprestimoService(EmprestimoRepository repository) {
        this.repository = repository;
    }

    public List<Emprestimo> listar() {
        return repository.findAll();
    }

    public Emprestimo salvar(Emprestimo emprestimo) {
        return repository.save(emprestimo);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Emprestimo buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}
