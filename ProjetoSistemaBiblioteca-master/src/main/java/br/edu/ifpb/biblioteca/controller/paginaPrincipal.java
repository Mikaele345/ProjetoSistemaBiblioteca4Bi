package br.edu.ifpb.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paginaPrincipal")
public class paginaPrincipal {
	
	@GetMapping("")
	public String paginaPrincipal(Model model) {
		return "paginaPrincipal";
	}

}
