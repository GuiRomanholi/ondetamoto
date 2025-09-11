package br.com.fiap.ondetamoto.controller;

import br.com.fiap.ondetamoto.model.Estabelecimento;
import br.com.fiap.ondetamoto.repository.EstabelecimentoRepository;
import br.com.fiap.ondetamoto.service.EstabelecimentoService;
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
@RequestMapping("/estabelecimentos")
public class EstabelecimentoWebController {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    EstabelecimentoService estabelecimentoService;

    // Listar estabelecimentos
    @GetMapping("/listar")
    public String listarEstabelecimentos(Model model, @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by("endereco").ascending());
        Page<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll(pageable);
        model.addAttribute("estabelecimentos", estabelecimentos);
        return "estabelecimento/listar_estabelecimentos";
    }

    // Exibir formulário de criação
    @GetMapping("/novo")
    public String novoEstabelecimento(Model model) {
        model.addAttribute("estabelecimento", new Estabelecimento());
        return "estabelecimento/form_estabelecimento";
    }

    // Salvar novo estabelecimento
    @PostMapping("/salvar")
    public String salvarEstabelecimento(@Valid @ModelAttribute Estabelecimento estabelecimento, RedirectAttributes ra) {
        estabelecimentoRepository.save(estabelecimento);
        ra.addFlashAttribute("mensagem", "Estabelecimento salvo com sucesso!");
        return "redirect:/estabelecimentos/listar";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarEstabelecimento(@PathVariable Long id, Model model) {
        Optional<Estabelecimento> estabelecimento = estabelecimentoRepository.findById(id);
        if (estabelecimento.isEmpty()) {
            return "redirect:/estabelecimentos/listar";
        }
        model.addAttribute("estabelecimento", estabelecimento.get());
        return "estabelecimento/form_estabelecimento";
    }

    // Excluir estabelecimento
    @GetMapping("/excluir/{id}")
    public String excluirEstabelecimento(@PathVariable Long id) {
        estabelecimentoRepository.deleteById(id);
        return "redirect:/estabelecimentos/listar";
    }
}
