package br.com.fiap.ondetamoto.service;

import br.com.fiap.ondetamoto.controller.EstabelecimentoController;
import br.com.fiap.ondetamoto.controller.UsuarioController;
import br.com.fiap.ondetamoto.dto.EstabelecimentoResponse;
import br.com.fiap.ondetamoto.dto.UsuarioRequest;
import br.com.fiap.ondetamoto.dto.UsuarioResponse;
import br.com.fiap.ondetamoto.model.Estabelecimento;
import br.com.fiap.ondetamoto.model.Usuario;
import br.com.fiap.ondetamoto.repository.EstabelecimentoRepository;
import br.com.fiap.ondetamoto.repository.UsuarioRepository;
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

    public UsuarioResponse usuarioToResponse(Usuario usuario, boolean self) {
        Link selfLink = self
                ? linkTo(methodOn(UsuarioController.class).readUsuario(usuario.getId())).withSelfRel()
                : linkTo(methodOn(UsuarioController.class).readUsuarios(0)).withRel("Lista de Usuarios");

        EstabelecimentoResponse estabelecimentoResponse = null;
        if (usuario.getEstabelecimento() != null) {
            Estabelecimento est = usuario.getEstabelecimento();
            Link estLink = linkTo(methodOn(EstabelecimentoController.class).readEstabelecimento(est.getId()))
                    .withSelfRel();
            estabelecimentoResponse = new EstabelecimentoResponse(est.getId(), est.getEndereco(), estLink);
        }

        return new UsuarioResponse(usuario.getId(), usuario.getEmail(), estabelecimentoResponse, selfLink);
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

    @Cacheable(value = "usuarios", key = "#id")
    public UsuarioResponse findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuarioToResponse(usuario, false);
    }

    @CacheEvict(value = "usuarios", key = "#id")
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @CachePut(value = "usuarios", key = "#id")
    public UsuarioResponse updateUsuario(Long id, UsuarioRequest request) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getEmail() != null) existente.setEmail(request.getEmail());
        if (request.getSenha() != null) existente.setSenha(request.getSenha());

        if (request.getIdEstabelecimento() != null) {
            estabelecimentoRepository.findById(request.getIdEstabelecimento())
                    .ifPresent(existente::setEstabelecimento);
        }

        Usuario atualizado = usuarioRepository.save(existente);
        return usuarioToResponse(atualizado, false);
    }
}
