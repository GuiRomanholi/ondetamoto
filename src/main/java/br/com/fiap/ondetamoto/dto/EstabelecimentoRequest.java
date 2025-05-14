package br.com.fiap.ondetamoto.dto;

public class EstabelecimentoRequest {
    private Long id;
    private String endereco;

    public EstabelecimentoRequest() {
    }

    public EstabelecimentoRequest(Long id, String endereco) {
        this.id = id;
        this.endereco = endereco;
    }

    public EstabelecimentoRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
