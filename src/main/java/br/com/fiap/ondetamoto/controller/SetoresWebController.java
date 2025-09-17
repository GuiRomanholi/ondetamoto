package br.com.fiap.ondetamoto.controller;

import br.com.fiap.ondetamoto.model.Setores;
import br.com.fiap.ondetamoto.repository.SetoresRepository;
import br.com.fiap.ondetamoto.model.Estabelecimento;
import br.com.fiap.ondetamoto.repository.EstabelecimentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/setores")
public class SetoresWebController {

    @Autowired
    private SetoresRepository setoresRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @GetMapping("/listar")
    public String listarSetores(Model model) {
        Page<Setores> setores = setoresRepository.findAll(PageRequest.of(0, 10));
        model.addAttribute("setores", setores);
        return "setores/listar_setores";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("setor", new Setores());
        model.addAttribute("estabelecimentos", estabelecimentoRepository.findAll());
        return "setores/form_setores";
    }

    @PostMapping("/salvar")
    public String salvarSetor(@Valid @ModelAttribute("setor") Setores setor, RedirectAttributes redirectAttributes) {
        try {
            // Se o setor tiver um ID, ele está sendo editado. Busque o estabelecimento existente.
            if (setor.getId() != null) {
                Optional<Setores> setorExistente = setoresRepository.findById(setor.getId());
                if (setorExistente.isPresent()) {
                    setor.setEstabelecimento(setorExistente.get().getEstabelecimento());
                }
            }

            Optional<Estabelecimento> estabelecimentoOptional = estabelecimentoRepository.findById(setor.getEstabelecimento().getId());
            if (estabelecimentoOptional.isPresent()) {
                setor.setEstabelecimento(estabelecimentoOptional.get());
                setoresRepository.save(setor);
                redirectAttributes.addFlashAttribute("mensagem", "Setor salvo com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Estabelecimento não encontrado.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Ocorreu um erro ao salvar o setor: " + e.getMessage());
        }
        return "redirect:/setores/listar";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        Optional<Setores> setorOptional = setoresRepository.findById(id);
        if (setorOptional.isPresent()) {
            model.addAttribute("setor", setorOptional.get());
            model.addAttribute("estabelecimentos", estabelecimentoRepository.findAll());
        } else {
            return "redirect:/setores/listar";
        }
        return "setores/form_setores";
    }

    @GetMapping("/excluir/{id}")
    public String excluirSetor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (setoresRepository.existsById(id)) {
                setoresRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("mensagem", "Setor excluído com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Setor não encontrado.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Ocorreu um erro ao excluir o setor: " + e.getMessage());
        }
        return "redirect:/setores/listar";
    }
}