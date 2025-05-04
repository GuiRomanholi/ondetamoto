package br.com.fiap.ondetamoto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_mot;
    private Long tipo;
    @NotBlank(message = "A placa é obrigatória")
    private Long placa;
    private Long tag;
}
