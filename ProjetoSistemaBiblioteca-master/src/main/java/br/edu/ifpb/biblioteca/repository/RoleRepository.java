package br.edu.ifpb.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.biblioteca.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRole(String role);
}
