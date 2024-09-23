CREATE TABLE usuario(
    id VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    cpf VARCHAR(255),
    telefone VARCHAR(255),

    PRIMARY KEY(id)
);

CREATE TABLE livro(
	id INT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255),
    autor VARCHAR(255),
    genero VARCHAR(255),
    editora VARCHAR(255),
    linguas VARCHAR(255),
    avaliacao VARCHAR(255),
    anoLancamento DATE,
    qtdPaginasTotal DOUBLE,
    qtdPaginasLidas DOUBLE,
    login_id VARCHAR(255) NOT NULL, 
    
    PRIMARY KEY(id),
    FOREIGN KEY(login_id) REFERENCES usuario(id) ON DELETE CASCADE
);