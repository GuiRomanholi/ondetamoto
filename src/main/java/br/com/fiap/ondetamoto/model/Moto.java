package br.com.fiap.ondetamoto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_mot;
    private String marca;
    @NotBlank(message = "A placa é obrigatória")
    private String placa;
    private String tag;

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Setores getSetores() {
        return setores;
    }

    public void setSetores(Setores setores) {
        this.setores = setores;
    }
}
