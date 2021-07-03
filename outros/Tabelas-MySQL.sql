DROP TABLE IF EXISTS locacao_veiculos.clients;

CREATE TABLE locacao_veiculos.clients (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(127) NOT NULL,
  cpf VARCHAR(30) NOT NULL,
  rg VARCHAR(30),
  address VARCHAR(256),
  email VARCHAR(127),
  registered_at DATETIME DEFAULT now(),
  version INT DEFAULT 0,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8mb4;

INSERT INTO clients(NAME, CPF, EMAIL)
VALUES('João Victor Simonassi', '14902272709', 'jsimonassi@id.uff.br');

INSERT INTO clients(NAME, CPF, EMAIL)
VALUES('Fulano da Silva', '12345678911', 'fulano@id.uff.br');

select * from clients;

