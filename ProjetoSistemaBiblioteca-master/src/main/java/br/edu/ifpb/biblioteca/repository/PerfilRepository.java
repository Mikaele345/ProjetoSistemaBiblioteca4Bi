package br.edu.ifpb.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.biblioteca.entity.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    boolean existsByCpf(String cpf);

  
    Perfil findByUsuarioId(Long usuarioId);
    
}
