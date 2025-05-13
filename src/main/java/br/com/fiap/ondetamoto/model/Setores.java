package br.com.fiap.ondetamoto.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Setores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sec;
    private String nome;
    private Tipo tipo;
    private String tamanho;

    @ManyToOne
    @JoinColumn(name = "id_estabelecimento")
    private Estabelecimento estabelecimento;

    @OneToMany(mappedBy = "setores")
    private List<Moto> motos;

    // Getters e Setters

    public Long getId_sec() {
        return id_sec;
    }

    public void setId_sec(Long id_sec) {
        this.id_sec = id_sec;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public List<Moto> getMotos() {
        return motos;
    }

    public void setMotos(List<Moto> motos) {
        this.motos = motos;
    }
}
