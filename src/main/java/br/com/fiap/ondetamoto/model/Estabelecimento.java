package br.com.fiap.ondetamoto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_est;
    private String endereco;
}
