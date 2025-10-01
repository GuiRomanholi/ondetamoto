package br.com.fiap.ondetamoto.service;

import br.com.fiap.ondetamoto.controller.EstabelecimentoController;
import br.com.fiap.ondetamoto.dto.EstabelecimentoRequest;
import br.com.fiap.ondetamoto.dto.EstabelecimentoResponse;
import br.com.fiap.ondetamoto.model.Estabelecimento;
import br.com.fiap.ondetamoto.model.Usuario; // <-- 1. IMPORTE A CLASSE USUARIO
import br.com.fiap.ondetamoto.repository.EstabelecimentoRepository;
import br.com.fiap.ondetamoto.repository.UsuarioRepository; // <-- 2. IMPORTE O REPOSITÓRIO DE USUARIO
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EstabelecimentoService {
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final UsuarioRepository usuarioRepository;

    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository, UsuarioRepository usuarioRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Estabelecimento requestToEstabelecimento(EstabelecimentoRequest estabelecimentoRequest) {
        Usuario usuario = usuarioRepository.findById(estabelecimentoRequest.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + estabelecimentoRequest.getUsuarioId()));

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setEndereco(estabelecimentoRequest.getEndereco());
        estabelecimento.setUsuario(usuario);

        return estabelecimento;
    }

    public EstabelecimentoResponse estabelecimentoToResponse(Estabelecimento estabelecimento, boolean self) {
        Link link;
        if (self) {
            link = linkTo(methodOn(EstabelecimentoController.class).readEstabelecimento(estabelecimento.getId())).withSelfRel();
        } else {
            link = linkTo(methodOn(EstabelecimentoController.class).readEstabelecimentos(0)).withRel("Lista de Estabelecimentos");
        }

        String usuarioEmail = (estabelecimento.getUsuario() != null) ? estabelecimento.getUsuario().getEmail() : null;

        return new EstabelecimentoResponse(estabelecimento.getId(), estabelecimento.getEndereco(), usuarioEmail, link);
    }

    public List<EstabelecimentoResponse> estabelecimentosToResponse(List<Estabelecimento> estabelecimentos) {
        List<EstabelecimentoResponse> estabelecimentosResponse = new ArrayList<>();
        for (Estabelecimento estabelecimento : estabelecimentos) {
            estabelecimentosResponse.add(estabelecimentoToResponse(estabelecimento, true));
        }
        return estabelecimentosResponse;
    }

    public Page<EstabelecimentoResponse> findAll(Pageable pageable) {
        return estabelecimentoRepository.findAll(pageable)
                .map(estabelecimento -> estabelecimentoToResponse(estabelecimento, true));
    }

    @Cacheable(value = "estabelecimentos", key = "#id")
    public EstabelecimentoResponse findById(Long id) {
        Estabelecimento est = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));
        return estabelecimentoToResponse(est, false);
    }

    @CacheEvict(value = "estabelecimentos", key = "#id")
    public void deleteEstabelecimento(Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    @CachePut(value = "estabelecimentos", key = "#id")
    public EstabelecimentoResponse updateEstabelecimento(Long id, EstabelecimentoRequest request) {
        Estabelecimento estExistente = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + request.getUsuarioId()));

        estExistente.setEndereco(request.getEndereco());
        estExistente.setUsuario(usuario);

        Estabelecimento salvo = estabelecimentoRepository.save(estExistente);
        return estabelecimentoToResponse(salvo, false);
    }
}