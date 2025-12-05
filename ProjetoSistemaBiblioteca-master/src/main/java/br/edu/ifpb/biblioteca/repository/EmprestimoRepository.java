package br.edu.ifpb.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.biblioteca.entity.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    
}
