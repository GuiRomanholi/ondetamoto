package br.com.fiap.ondetamoto.dto;

import br.com.fiap.ondetamoto.model.Estabelecimento;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank(message = "O Email é Obrigatório")
    @Email(message = "Escreva um email Válido")
    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,25}$",
    message = "A senha deve ter entre 8 e 25 caracteres, conter ao menos uma letra maiúscula e um número, e não pode ter caracteres especiais.")
    @NotBlank(message = "A senha é Obrigatória")
    private String senha;

    private Estabelecimento estabelecimento;

    public UsuarioRequest(){
    }

    public UsuarioRequest(String email, String senha, Estabelecimento estabelecimento){
        this.email = email;
        this.senha = senha;
        this.estabelecimento = estabelecimento;
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
