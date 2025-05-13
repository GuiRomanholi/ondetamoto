package br.com.fiap.ondetamoto.model;

import jakarta.persistence.*;
import org.apache.catalina.User;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usu;
    private String email;
    private String senha;
    @ManyToOne
    @JoinColumn(name = "id_estabelecimento")
    private Estabelecimento estabelecimento;

    public Usuario(){
    }

    public Usuario(Long id_usu, String email, String senha){
        this.id_usu = id_usu;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters

    public Long getId_usu() {
        return id_usu;
    }

    public void setId_usu(Long id_usu) {
        this.id_usu = id_usu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
