package br.com.fiap.ondetamoto.controller;

import br.com.fiap.ondetamoto.model.Usuario;
import br.com.fiap.ondetamoto.repository.UsuarioRepository;
import br.com.fiap.ondetamoto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    // Listar usuários
    @GetMapping("/listar")
    public String listarUsuarios(Model model, @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by("email").ascending());
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        model.addAttribute("usuarios", usuarios);
        return "usuario/listar_usuarios";
    }

    // Exibir formulário de criação
    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/form_usuario";
    }

    // Salvar novo usuário
    @PostMapping("/salvar")
    public String salvarUsuario(@Valid @ModelAttribute Usuario usuario, RedirectAttributes ra) {
        usuarioRepository.save(usuario);
        ra.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
        return "redirect:/usuarios/listar";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return "redirect:/usuarios/listar";
        }
        model.addAttribute("usuario", usuario.get());
        return "usuario/form_usuario";
    }

    // Excluir usuário
    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios/listar";
    }
}
