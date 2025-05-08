package br.com.fiap.ondetamoto.model;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "setores_id_sec")
    private Setores setores;

    // Getters e Setters

    public Long getId_mot() {
        return id_mot;
    }

    public void setId_mot(Long id_mot) {
        this.id_mot = id_mot;
    }

    public Long getTipo() {
        return tipo;
    }

    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

    public Long getPlaca() {
        return placa;
    }

    public void setPlaca(Long placa) {
        this.placa = placa;
    }

    public Long getTag() {
        return tag;
    }

    public void setTag(Long tag) {
        this.tag = tag;
    }

    public Setores getSetores() {
        return setores;
    }

    public void setSetores(Setores setores) {
        this.setores = setores;
    }
}
