CREATE TABLE categoria(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (nome) values ('Investimentos');
INSERT INTO categoria (nome) values ('Moradia');
INSERT INTO categoria (nome) values ('Alimentação');
INSERT INTO categoria (nome) values ('Estudos');
INSERT INTO categoria (nome) values ('Lazer');


