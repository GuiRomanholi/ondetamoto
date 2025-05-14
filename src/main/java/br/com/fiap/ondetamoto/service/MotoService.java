package br.com.fiap.ondetamoto.service;

import br.com.fiap.ondetamoto.controller.MotoController;
import br.com.fiap.ondetamoto.dto.MotoRequest;
import br.com.fiap.ondetamoto.dto.MotoResponse;
import br.com.fiap.ondetamoto.model.Moto;
import br.com.fiap.ondetamoto.repository.MotoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MotoService {
    private final MotoRepository motoRepository;

    public MotoService(MotoRepository motoRepository){
        this.motoRepository = motoRepository;
    }

    public Moto requestToMoto(MotoRequest motoRequest) {
        return new Moto(null,
                motoRequest.getMarca(),
                motoRequest.getPlaca(),
                motoRequest.getTag());
    }

    public MotoResponse motoToResponse(Moto moto, boolean self){
        Link link;
        if (self){
            link = linkTo(
                    methodOn(MotoController.class).readMoto(moto.getId())
            ).withSelfRel();
        } else {
            link = linkTo(
                    methodOn(MotoController.class).readMotos(0)
            ).withRel("Lista de Motos");
        }

        return new MotoResponse(moto.getId(), moto.getPlaca(), moto.getMarca(), moto.getTag(), link);
    }


    public List<MotoResponse> motosToResponse(List<Moto> motos) {
        List<MotoResponse> motosResponse = new ArrayList<>();
        for (Moto moto : motos) {
            motosResponse.add(motoToResponse(moto, true));
        }
        return motosResponse;
    }

    public Page<MotoResponse> findAll(Pageable pageable) {
        return motoRepository.findAll(pageable)
                .map(moto -> motoToResponse(moto, true));
    }

    public Moto updateMotoFromRequest(Moto motoExistente, MotoRequest motoRequest) {
        motoExistente.setMarca(motoRequest.getMarca());
        motoExistente.setPlaca(motoRequest.getPlaca());
        motoExistente.setTag(motoRequest.getTag());
        return motoExistente;
    }
}

