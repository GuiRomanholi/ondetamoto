package br.com.fiap.ondetamoto.dto;

import org.springframework.hateoas.Link;

public record UsuarioResponse(Long id_usu, String email, Link link) {
}
