package br.com.fiap.ondetamoto.dto;

import org.springframework.hateoas.Link;

public record MotoResponse(Long id_mot, String placa, String tag, Link link) {
}
