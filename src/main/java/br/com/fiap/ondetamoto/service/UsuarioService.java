package br.com.fiap.ondetamoto.service;

import br.com.fiap.ondetamoto.controller.UsuarioController;
import br.com.fiap.ondetamoto.dto.UsuarioRequest;
import br.com.fiap.ondetamoto.dto.UsuarioResponse;
import br.com.fiap.ondetamoto.model.Usuario;
import br.com.fiap.ondetamoto.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario requestToUsuario(UsuarioRequest usuarioRequest) {
        return new Usuario(null,
                usuarioRequest.getEmail(),
                usuarioRequest.getSenha());
    }

    public UsuarioResponse usuarioToResponse(Usuario usuario, boolean self){
        Link link;
        if (self){
            link = linkTo(
                    methodOn(
                            UsuarioController.class
                    ).readUsuario(usuario.getId_usu())
            ).withSelfRel();
        } else {
            link = linkTo(
                    methodOn(
                            UsuarioController.class
                    ).readUsuarios(0)
            ).withRel("Lista de Usuarios");
        }
        return new UsuarioResponse(usuario.getId_usu(), usuario.getEmail(), link);
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
