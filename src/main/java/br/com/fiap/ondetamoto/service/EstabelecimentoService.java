package br.com.fiap.ondetamoto.service;

import br.com.fiap.ondetamoto.controller.EstabelecimentoController;
import br.com.fiap.ondetamoto.dto.EstabelecimentoRequest;
import br.com.fiap.ondetamoto.dto.EstabelecimentoResponse;
import br.com.fiap.ondetamoto.model.Estabelecimento;
import br.com.fiap.ondetamoto.repository.EstabelecimentoRepository;
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

    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository){
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    public Estabelecimento requestToEstabelecimento(EstabelecimentoRequest estabelecimentoRequest) {
        return new Estabelecimento(null,
                estabelecimentoRequest.getEndereco());
    }

    public EstabelecimentoResponse estabelecimentoToResponse(Estabelecimento estabelecimento, boolean self){
        Link link;
        if (self){
            link = linkTo(
                    methodOn(
                            EstabelecimentoController.class
                    ).readEstabelecimento(estabelecimento.getId_est())
            ).withSelfRel();
        } else {
            link = linkTo(
                    methodOn(
                            EstabelecimentoController.class
                    ).readEstabelecimentos(0)
            ).withRel("Lista de Estabelecimentos");
        }
        return new EstabelecimentoResponse(estabelecimento.getId_est(), estabelecimento.getEndereco(), link);
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
}

