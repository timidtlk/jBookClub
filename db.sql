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
    
    PRIMARY KEY(id)
);