CREATE TABLE estabelecimento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    endereco VARCHAR(255),
    usuario_id BIGINT,
    CONSTRAINT fk_estabelecimento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);