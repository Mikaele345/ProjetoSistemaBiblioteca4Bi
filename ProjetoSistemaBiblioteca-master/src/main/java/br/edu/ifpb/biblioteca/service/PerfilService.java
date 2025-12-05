package br.edu.ifpb.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifpb.biblioteca.entity.Perfil;
import br.edu.ifpb.biblioteca.repository.PerfilRepository;

@Service
public class PerfilService {
    private final PerfilRepository repository;

    public PerfilService(PerfilRepository repository) {
        this.repository = repository;
    }

    public List<Perfil> listar() {
        return repository.findAll();
    }

    public Perfil salvar(Perfil perfil) {
        return repository.save(perfil);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Perfil buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsByCpf(String cpf) {
		return repository.existsByCpf(cpf);
	}

        // Adicione este método na classe PerfilService
    public Perfil buscarPorUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
