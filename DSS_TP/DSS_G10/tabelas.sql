SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


CREATE SCHEMA IF NOT EXISTS `MC` DEFAULT CHARACTER SET utf8 ;
USE `MC` ;



CREATE TABLE IF NOT EXISTS `Utilizador` (
  `Pwd` VARCHAR(50) NULL,
  `Email` VARCHAR(50) NOT NULL,
   `Nome` VARCHAR(50) NULL,
   PRIMARY KEY (`Email`));
  
  
CREATE TABLE IF NOT EXISTS `Admins` (
  `Pwd` VARCHAR(50) NULL,
  `Email` VARCHAR(50) NOT NULL,
   `Nome` VARCHAR(50) NULL,
  PRIMARY KEY (`Email`));

CREATE TABLE IF NOT EXISTS `Conteudo` (
 `Filename` VARCHAR(200) NOT NULL, #o id do conteudo é o proprio nome do ficheiro
  `Nome` VARCHAR(50) NOT NULL,
  `Nome_Categoria` VARCHAR(50),
  `Tipo` INT NULL, # 0 musica, 1 video 
  PRIMARY KEY (`Nome`),
  FOREIGN KEY (`Nome_Categoria`) REFERENCES `Categoria`(`Nome`)); 
  
  
CREATE TABLE IF NOT EXISTS `Categoria` (
`Nome` VARCHAR(50) NOT NULL,
`Tipo` INT NULL,
PRIMARY KEY (`Nome`));

CREATE TABLE IF NOT EXISTS `User_Conteudo` (
`idUser_Conteudo` INT NOT NULL AUTO_INCREMENT,
`EmailUtilizador` VARCHAR(50) NOT NULL,
`Nome_Conteudo` VARCHAR(50) NOT NULL,
`Nome_Categoria` VARCHAR(50) NOT NULL,
PRIMARY KEY (`idUser_Conteudo`),
FOREIGN KEY (`EmailUtilizador`) REFERENCES `Utilizador`(`Email`), FOREIGN KEY (`Nome_Conteudo`) REFERENCES `Conteudo`(`Nome`),FOREIGN KEY (`Nome_Categoria`) REFERENCES `Categoria`(`Nome`));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
  
# Povoamento da tabela categoria, para media do tipo 0 (musicas)
INSERT INTO mc.categoria VALUES('Rock',0);
INSERT INTO mc.categoria VALUES('Pop',0);
INSERT INTO mc.categoria VALUES('Jazz',0);
INSERT INTO mc.categoria VALUES('Eletro',0);
INSERT INTO mc.categoria VALUES('HipHop',0);
INSERT INTO mc.categoria VALUES('Fado',0);
INSERT INTO mc.categoria VALUES('Kizomba',0);
INSERT INTO mc.categoria VALUES('Latino',0);

# Povoamento da tabela categoria, para media do tipo 1 (videos)
INSERT INTO mc.categoria VALUES('Comedia',1);
INSERT INTO mc.categoria VALUES('Acao',1);
INSERT INTO mc.categoria VALUES('Terror',1);
INSERT INTO mc.categoria VALUES('Romance',1);
INSERT INTO mc.categoria VALUES('Crime',1);
INSERT INTO mc.categoria VALUES('Documentario',1);
INSERT INTO mc.categoria VALUES('Aventura',1);
INSERT INTO mc.categoria VALUES('Desporto',1);

#Povoamento da tabela de admins
INSERT INTO mc.admins VALUES('admin','admin@','admin');

#Povoamento da tabela de utilizadores
INSERT INTO mc.utilizador VALUES('ze','ze@','ze');
INSERT INTO mc.utilizador VALUES('vila','vila@','vila');
INSERT INTO mc.utilizador VALUES('ramos','ramos@','ramos');
INSERT INTO mc.utilizador VALUES('ivo','ivo@','ivo');