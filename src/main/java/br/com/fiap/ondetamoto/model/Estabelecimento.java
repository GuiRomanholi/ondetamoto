package br.com.fiap.ondetamoto.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_est;
    private String endereco;

    @OneToMany(mappedBy = "estabelecimento")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "estabelecimento")
    private List<Setores> setores;

    public Estabelecimento(){}

    public Estabelecimento(Long id_est, String endereco){
        this.id_est = id_est;
        this.endereco = endereco;
    }

    // Getters e Setters

    public Long getId_est() {
        return id_est;
    }

    public void setId_est(Long id_est) {
        this.id_est = id_est;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Setores> getSetores() {
        return setores;
    }

    public void setSetores(List<Setores> setores) {
        this.setores = setores;
    }
}
