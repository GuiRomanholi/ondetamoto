package br.com.fiap.ondetamoto.dto;

public class EstabelecimentoRequest {
    private String endereco;

    public EstabelecimentoRequest() {
    }

    public EstabelecimentoRequest(String endereco) {
        this.endereco = endereco;
    }


    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
