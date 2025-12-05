package br.edu.ifpb.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.biblioteca.entity.Perfil;
import br.edu.ifpb.biblioteca.entity.Usuario;
import br.edu.ifpb.biblioteca.service.PerfilService;
import br.edu.ifpb.biblioteca.service.UsuarioService;

@Controller
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    protected PerfilService service;

    @Autowired
    protected UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("perfis", service.listar());
        return "listarPerfil";
    }

    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("perfil", new Perfil());
        model.addAttribute("usuarios", usuarioService.buscarUsuariosSemPerfil());
        return "cadastrarPerfil";
    }

    @PostMapping("/salvar")
    public String salvarPerfil(@ModelAttribute Perfil perfil, Model model) {
        if (service.existsByCpf(perfil.getCpf())) {
            model.addAttribute("mensagemErro", "Perfil com CPF " + perfil.getCpf() + " já existe.");
            model.addAttribute("perfil", perfil);
            model.addAttribute("usuarios", usuarioService.buscarUsuariosSemPerfil());
            return "cadastrarPerfil";
        }
        service.salvar(perfil);
        model.addAttribute("mensagemSucesso", "Perfil cadastrado com sucesso!");
        return "redirect:/perfis";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Perfil perfil = service.buscarPorId(id);
        model.addAttribute("perfil", perfil);
        // busca usuário já associado + usuários sem perfil
        List<Usuario> usuarios = usuarioService.buscarUsuariosSemPerfil();
        if (perfil.getUsuario() != null) {
            usuarios.add(perfil.getUsuario()); // adiciona usuário atual à lista
        }
        model.addAttribute("usuarios", usuarios);
        return "editarPerfil";
    }

    @PostMapping("/editar")
    public String editarPerfil(@ModelAttribute Perfil perfil, Model model) {
        Perfil perfilExistente = service.buscarPorId(perfil.getId());
        
        if ((!perfilExistente.getCpf().equals(perfil.getCpf())) && service.existsByCpf(perfil.getCpf())) {
            model.addAttribute("mensagemErro", "Perfil com CPF " + perfil.getCpf() + " já existe.");
            model.addAttribute("perfil", perfil);
            List<Usuario> usuarios = usuarioService.buscarUsuariosSemPerfil();
            if (perfilExistente.getUsuario() != null) {
                usuarios.add(perfilExistente.getUsuario());
            }
            model.addAttribute("usuarios", usuarios);
            return "editarPerfil";
        }
        
        service.salvar(perfil);
        model.addAttribute("mensagemSucesso", "Perfil atualizado com sucesso!");
        return "redirect:/perfis";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/perfis";
    }
}
