package br.edu.ifpb.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifpb.biblioteca.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // retorna um usuario com seus emprestimos associadas
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.emprestimos WHERE u.id = :id")
    Usuario findByIdWithEmprestimos(@Param("id") Long id);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.perfil WHERE u.id = :id")
    Usuario findByIdWithPerfil(@Param("id") Long id);

    Optional<Usuario> findByLogin(String login);

    //seleciona todos os usuarios que nao possuem perfil
    List<Usuario> findByPerfilIsNull();

    boolean existsByEmail(String email);
}

