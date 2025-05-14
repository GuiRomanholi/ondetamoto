package br.com.fiap.ondetamoto.dto;

import br.com.fiap.ondetamoto.model.Estabelecimento;
import org.springframework.hateoas.Link;

public record UsuarioResponse(Long id, String email, Estabelecimento estabelecimento, Link link) {
}
