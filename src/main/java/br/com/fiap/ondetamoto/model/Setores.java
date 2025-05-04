package br.com.fiap.ondetamoto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Setores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sec;
    private String nome;
    private Tipo tipo;
    private String tamanho;
}
