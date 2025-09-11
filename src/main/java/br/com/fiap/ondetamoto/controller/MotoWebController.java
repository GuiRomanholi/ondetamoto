package br.com.fiap.ondetamoto.controller;

import br.com.fiap.ondetamoto.model.Moto;
import br.com.fiap.ondetamoto.repository.MotoRepository;
import br.com.fiap.ondetamoto.service.MotoService;
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
@RequestMapping("/motos")
public class MotoWebController {

    @Autowired
    MotoRepository motoRepository;

    @Autowired
    MotoService motoService;

    // Listar
    @GetMapping("/listar")
    public String listarMotos(Model model, @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by("placa").ascending());
        Page<Moto> motos = motoRepository.findAll(pageable);
        model.addAttribute("motos", motos);
        return "moto/listar_motos";
    }

    // Exibir formulário de criação
    @GetMapping("/novo")
    public String novoMoto(Model model) {
        model.addAttribute("moto", new Moto());
        return "moto/form_moto";
    }

    // Salvar nova moto
    @PostMapping("/salvar")
    public String salvarMoto(@Valid @ModelAttribute Moto moto, RedirectAttributes ra) {
        motoRepository.save(moto);
        ra.addFlashAttribute("mensagem", "Moto salva com sucesso!");
        return "redirect:/motos/listar";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarMoto(@PathVariable Long id, Model model) {
        Optional<Moto> moto = motoRepository.findById(id);
        if (moto.isEmpty()) {
            return "redirect:/motos/listar"; // Redireciona se não encontrar a moto
        }
        model.addAttribute("moto", moto.get());
        return "moto/form_moto";
    }

    // Excluir moto
    @GetMapping("/excluir/{id}")
    public String excluirMoto(@PathVariable Long id) {
        motoRepository.deleteById(id);
        return "redirect:/motos/listar";
    }
}
