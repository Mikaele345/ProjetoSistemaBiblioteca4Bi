package br.edu.ifpb.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifpb.biblioteca.entity.Role;
import br.edu.ifpb.biblioteca.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> listar() {
        return repository.findAll();
    }

    public Role salvar(Role role) {
        return repository.save(role);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Role buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsByRole(String role) {
		return repository.existsByRole(role);
	}
}
