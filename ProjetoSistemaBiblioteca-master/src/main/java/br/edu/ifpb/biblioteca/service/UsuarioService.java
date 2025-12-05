package br.edu.ifpb.biblioteca.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.biblioteca.entity.Usuario;
import br.edu.ifpb.biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // retorna um usuario com seus emprestimos associados
	public Usuario findByIdWithEmprestimos(Long id) {
		return repository.findByIdWithEmprestimos(id);
	}

    public List<Usuario> buscarUsuariosSemPerfil() {
        return repository.findByPerfilIsNull();
    }

    public Usuario findByIdWithPerfil(Long id) {
        return repository.findByIdWithPerfil(id);
    }

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return repository.findByLogin(username)
			.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}

    public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}
}
