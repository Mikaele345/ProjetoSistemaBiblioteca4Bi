package br.edu.ifpb.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.biblioteca.entity.Role;
import br.edu.ifpb.biblioteca.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    protected RoleService roleService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("roles", roleService.listar());
        return "listarRoles";
    }

    @GetMapping("/cadastrar")
     public String cadastrarForm(Model model) {
        model.addAttribute("roleObj", new Role());
        return "cadastrarRoles";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("roleObj") Role role, Model model) {	
        // normaliza o nome do papel para incluir o sufixo "ROLE"
        String normalized = withRoleSuffix(role.getRole());
        role.setRole(normalized);

        // Verifica se o papel já existe
        if (roleService.existsByRole(normalized)) {
            model.addAttribute("mensagemErro", "Papel " + normalized + " já cadastrado.");
            model.addAttribute("roleObj", role);
            return "cadastrarRoles";
        } else {
            roleService.salvar(role);
            model.addAttribute("mensagemSucesso", "Papel " + normalized + " cadastrado com sucesso!");
            return "redirect:/roles";
        }
    }

    @GetMapping("/editar/{id}")
    public String editRole(@PathVariable Long id, Model model) {
        Role role = roleService.buscarPorId(id);
        model.addAttribute("roleObj", role);
        return "editarRole";
    }

    @PostMapping("/editar")
    public String editRolePost(@ModelAttribute("roleObj") Role role, Model model) {
        // normaliza o novo nome
        String newName = withRoleSuffix(role.getRole());
        role.setRole(newName);

        // Verifica se o nome alterado já existe em outro papel, exceto ele mesmo
        String original = roleService.buscarPorId(role.getId()).getRole();
        if ((!original.equals(newName)) && roleService.existsByRole(newName)) {
            model.addAttribute("mensagemErro", "role " + newName + " já existe.");
            return listar(model);
        } else {
            roleService.salvar(role);
        }

        model.addAttribute("mensagemSucesso", "role " + newName + " atualizado com sucesso!");
        return listar(model);
    }


    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        roleService.deletar(id);
        return "redirect:/roles";
    }

    // helper para garantir o sufixo ROLE (em maiúsculas)
    private String withRoleSuffix(String name) {
        if (name == null) return null;
        name = name.trim().toUpperCase();
        if (!name.startsWith("ROLE_")) {
            name = "ROLE_" + name;
        }
        return name;
    }
}
