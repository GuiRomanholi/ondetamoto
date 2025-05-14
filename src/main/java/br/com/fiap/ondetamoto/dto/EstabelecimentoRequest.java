package br.com.fiap.ondetamoto.dto;

public class EstabelecimentoRequest {
    private Long id_est;
    private String endereco;

    public EstabelecimentoRequest() {
    }

    public EstabelecimentoRequest(Long id_est, String endereco) {
        this.id_est = id_est;
        this.endereco = endereco;
    }

    public EstabelecimentoRequest(Long id_est) {
        this.id_est = id_est;
    }

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
}
