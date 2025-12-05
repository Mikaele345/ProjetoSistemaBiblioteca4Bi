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

import br.edu.ifpb.biblioteca.entity.Emprestimo;
import br.edu.ifpb.biblioteca.entity.Usuario;
import br.edu.ifpb.biblioteca.service.EmprestimoService;
import br.edu.ifpb.biblioteca.service.UsuarioService;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    protected EmprestimoService service;

    @Autowired
    protected UsuarioService usuarioService;


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("emprestimos", service.listar());
        return "listarEmprestimo";
    }

    //a lista dos usuarios para o formulario de emprestimo
    private void usuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listar();
        model.addAttribute("usuarios", usuarios);
    }
    
    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        //usuarios para o formulario de emprestimo
        usuarios(model);
        model.addAttribute("emprestimo", new Emprestimo());
        return "cadastrarEmprestimo";
    }
 
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Emprestimo emprestimo) {
        service.salvar(emprestimo);
        return "redirect:/emprestimos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Emprestimo emprestimo = service.buscarPorId(id);
        if (emprestimo == null) {
            model.addAttribute("mensagemErro", "Empréstimo não encontrado.");
            return listar(model);
        }
        usuarios(model);
        model.addAttribute("emprestimo", emprestimo);
        return "editarEmprestimo";
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute Emprestimo emprestimo, Model model) {
        service.salvar(emprestimo);
        model.addAttribute("mensagemSucesso", "Empréstimo atualizado com sucesso!");
        return "redirect:/emprestimos";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/emprestimos";
    }
}
