package br.com.fiap.ondetamoto.dto;

import org.springframework.hateoas.Link;

public record EstabelecimentoResponse(Long id_est, String endereco, Link link) {
}
