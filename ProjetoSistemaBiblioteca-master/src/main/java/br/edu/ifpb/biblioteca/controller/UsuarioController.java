package br.edu.ifpb.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.biblioteca.entity.Emprestimo;
import br.edu.ifpb.biblioteca.entity.Role;
import br.edu.ifpb.biblioteca.entity.Usuario;
import br.edu.ifpb.biblioteca.entity.Perfil;
import br.edu.ifpb.biblioteca.service.RoleService;
import br.edu.ifpb.biblioteca.service.UsuarioService;
import br.edu.ifpb.biblioteca.service.PerfilService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    protected UsuarioService usuarioService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected PerfilService perfilService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "listarUsuario";
    }

    // lista de roles para o formulario de usuario
    private void roles(Model model) {
		List<Role> roles = roleService.listar();
		model.addAttribute("roles", roles);
	}

    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        // lista de roles para o formulario de usuario
        roles(model);
        model.addAttribute("usuario", new Usuario());
        return "cadastrarUsuario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario, Model model) {
       if (usuarioService.existsByEmail(usuario.getEmail())) {
			model.addAttribute("mensagemErro", "Usuário com Email " + usuario.getEmail() + " já cadastrado.");
			return "cadastrarUsuario";
		} else {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuarioService.salvar(usuario);
		}
		model.addAttribute("mensagemSucesso", "Usuário com Email " + usuario.getEmail() + " cadastrado com sucesso!");
		return "cadastrarUsuario";
    }

    @GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable Long id, Model model) {
        roles(model);
		Usuario usuario = usuarioService.buscarPorId(id);
		model.addAttribute("usuario", usuario);
		return "editarUsuario";
	}

    @PostMapping("/editar")
	public String editarUsuarioPost(@ModelAttribute Usuario usuario, Model model) {
		if((!usuarioService.buscarPorId(usuario.getId()).getEmail().equals(usuario.getEmail())) && usuarioService.existsByEmail(usuario.getEmail())) {
			model.addAttribute("mensagemErro", "Usuário com Email " + usuario.getEmail() + " já existe.");
			return listar(model);
		}else {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioService.salvar(usuario);
		}

		model.addAttribute("mensagemSucesso", "Usuário com Email " + usuario.getEmail() + " atualizado com sucesso!");
		return listar(model);
	}


    @GetMapping("/deletar/{id}")
public String deletar(@PathVariable Long id, Model model) {
    // 1. Busca o usuário para verificar se existe
    Usuario usuario = usuarioService.buscarPorId(id);
    
    if (usuario == null) {
        model.addAttribute("mensagemErro", "Usuário não encontrado.");
        return listar(model);
    }

    // 2. Verifica vínculo com empréstimos (seu código original mantido)
    Usuario usuarioComEmprestimos = usuarioService.findByIdWithEmprestimos(id);
    List<Emprestimo> emprestimos = usuarioComEmprestimos != null ? usuarioComEmprestimos.getEmprestimos() : null;
    if (emprestimos != null && !emprestimos.isEmpty()) {
        model.addAttribute("mensagemErro", "Usuário não pode ser deletado, pois possui empréstimos.");
        model.addAttribute("emprestimos", emprestimos);
        return listar(model);
    }

    // 3. CORREÇÃO: Busca o perfil pelo ID do USUÁRIO diretamente no serviço de perfis
    // Isso garante que acharemos o perfil mesmo que o objeto 'usuario' esteja desatualizado
    Perfil perfilVinculado = perfilService.buscarPorUsuarioId(id);

    if (perfilVinculado != null) {
        // Desvincula o usuário do perfil (seta a FK na tabela Perfil para null)
        perfilVinculado.setUsuario(null);
        perfilService.salvar(perfilVinculado);
    }
    
    // Se o usuário tiver referência para o perfil, limpa também
    if (usuario.getPerfil() != null) {
        usuario.setPerfil(null);
        usuarioService.salvar(usuario);
    }

    // 4. Agora é seguro deletar
    usuarioService.deletar(id);
    model.addAttribute("mensagemSucesso", "Usuário removido com sucesso");
    return listar(model);
}
}
