package br.com.fiap.ondetamoto.service;

import br.com.fiap.ondetamoto.controller.UsuarioController;
import br.com.fiap.ondetamoto.dto.UsuarioRequest;
import br.com.fiap.ondetamoto.dto.UsuarioResponse;
import br.com.fiap.ondetamoto.model.Estabelecimento;
import br.com.fiap.ondetamoto.model.Usuario;
import br.com.fiap.ondetamoto.repository.EstabelecimentoRepository;
import br.com.fiap.ondetamoto.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, EstabelecimentoRepository estabelecimentoRepository){
        this.usuarioRepository = usuarioRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    public Usuario requestToUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario(null,
                usuarioRequest.getEmail(),
                usuarioRequest.getSenha());

        if (usuarioRequest.getIdEstabelecimento() != null) {
            estabelecimentoRepository.findById(usuarioRequest.getIdEstabelecimento())
                    .ifPresent(usuario::setEstabelecimento);
        }

        return usuario;
    }

    public UsuarioResponse usuarioToResponse(Usuario usuario, boolean self){
        Link link;
        if (self){
            link = linkTo(
                    methodOn(UsuarioController.class).readUsuario(usuario.getId())
            ).withSelfRel();
        } else {
            link = linkTo(
                    methodOn(UsuarioController.class).readUsuarios(0)
            ).withRel("Lista de Usuarios");
        }

        return new UsuarioResponse(usuario.getId(), usuario.getEmail(), usuario.getEstabelecimento(), link);
    }

    public List<UsuarioResponse> usuariosToResponse(List<Usuario> usuarios) {
        List<UsuarioResponse> usuariosResponse = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuariosResponse.add(usuarioToResponse(usuario, true));
        }
        return usuariosResponse;
    }

    public Page<UsuarioResponse> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuario -> usuarioToResponse(usuario, true));
    }
}
