USE master;
GO 

DROP DATABASE IF EXISTS bdPizzaHut;
CREATE DATABASE bdPizzaHut;
GO 

USE bdPizzaHut;
GO

DROP TABLE IF EXISTS UBIGEO;
CREATE TABLE UBIGEO
(
	CODUBI int identity(1,1) NOT NULL,
	DEPUBI varchar(50) NOT NULL,
	PROUBI varchar(50) NOT NULL,
	DISTUBI varchar(50) NOT NULL,
	CONSTRAINT Ubigeo_ok PRIMARY KEY(CODUBI)
);
GO

DROP TABLE IF EXISTS CLIENTE;
CREATE TABLE CLIENTE
(
	IDCLI int identity(1,1) NOT NULL,
	NOMCLI varchar(50) NOT NULL,
	APECLI varchar(50) NOT NULL,
	DNICLI char(8) NOT NULL,
	TELCLI char(9) NOT NULL,
	CORCLI varchar(50) NOT NULL,
	ESTCLI char(1) NOT NULL,
	CODUBI int NOT NULL,
	CONSTRAINT Cliente_pk PRIMARY KEY (IDCLI)
);
GO

DROP TABLE IF EXISTS USUARIO;
CREATE TABLE USUARIO
(
	IDUSU int identity(1,1) NOT NULL,
	NOMUSU varchar(50) NOT NULL,
	APEUSU varchar(50) NOT NULL,
	DNIUSU char(8) NOT NULL,
	TELUSU char(9) NOT NULL,
	NIVUSU	char(1) NOT NULL,
	ESTUSU char(1) NOT NULL,
	CODUBI int NOT NULL,
	CONSTRAINT Usuario_pk PRIMARY KEY (IDUSU)
);
GO


DROP TABLE IF EXISTS VENTA;
CREATE TABLE VENTA
(
	CODVEN int identity(1,1) NOT NULL,
	FECVEN date NOT NULL,
	IDCLI int NOT NULL,
	IDUSU int NOT NULL,
	CONSTRAINT Venta_pk PRIMARY KEY (CODVEN)
);
GO


DROP TABLE IF EXISTS PRODUCTO;
CREATE TABLE PRODUCTO
(
	CODPRO	int identity(1,1) NOT NULL,
	NOMPRO varchar(50) NOT NULL,
	PREPRO decimal(8,2) NOT NULL,
	CANPRO int NOT NULL,
	DESPRO varchar(50) NOT NULL,
	TIPPRO varchar(30) NOT NULL,
	ESTPRO char(1) NOT NULL,
	CONSTRAINT Producto_pk PRIMARY KEY (CODPRO)
);
GO


DROP TABLE IF EXISTS SUCURSAL;
CREATE TABLE SUCURSAL
(
	IDSUC int identity(1,1) NOT NULL,
	NOMSUC varchar(50) NOT NULL,
	CODUBI int NOT NULL,
	CONSTRAINT Sucursal_pk PRIMARY KEY (IDSUC)
);
GO

DROP TABLE IF EXISTS VENTADETALLE;
CREATE TABLE VENTADETALLE
(
	CODVENDET int identity(1,1) NOT NULL,
	CANVENDET int NOT NULL,
	CODVEN int NOT NULL,
	CODPRO int NOT NULL,
	CONSTRAINT Ventadetalle_pk PRIMARY KEY (CODVENDET)
);
GO


-- foreign keys
-- Reference: CLIENTE_UBIGEO (table: CLIENTE)
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);
GO

-- Reference: EMPLEADO_UBIGEO (table: USUARIO)
ALTER TABLE USUARIO ADD CONSTRAINT EMPLEADO_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);
GO

-- Reference: SUCURSAL_UBIGEO (table: SUCURSAL)
ALTER TABLE SUCURSAL ADD CONSTRAINT SUCURSAL_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);
GO

-- Reference: VENTADETALLE_PRODUCTO (table: VENTADETALLE)
ALTER TABLE VENTADETALLE ADD CONSTRAINT VENTADETALLE_PRODUCTO
    FOREIGN KEY (CODPRO)
    REFERENCES PRODUCTO (CODPRO);
GO
-- Reference: VENTADETALLE_VENTA (table: VENTADETALLE)
ALTER TABLE VENTADETALLE ADD CONSTRAINT VENTADETALLE_VENTA
    FOREIGN KEY (CODVEN)
    REFERENCES VENTA (CODVEN);
GO

-- Reference: VENTA_CLIENTE (table: VENTA)
ALTER TABLE VENTA ADD CONSTRAINT VENTA_CLIENTE
    FOREIGN KEY (IDCLI)
    REFERENCES CLIENTE (IDCLI);
GO

-- Reference: VENTA_USUARIO (table: VENTA)
ALTER TABLE VENTA ADD CONSTRAINT VENTA_USUARIO
    FOREIGN KEY (IDUSU)
    REFERENCES USUARIO (IDUSU);
GO




INSERT INTO UBIGEO
(DEPUBI,DISTUBI,PROUBI)    
VALUES
('LIMA','VENTANILLA','PROV. CONST. DEL CALLAO'),
('LIMA','PUENTE PIEDRA','LIMA'),
('LIMA','SAN JUAN DE LURIGANCHO','LIMA'),
('LIMA','LA PERLA',	'PROV. CONST. DEL CALLAO'),
('LIMA','VILLA MARIA DEL TRIUNFO','LIMA'),
('LIMA','INDEPENDENCIA','LIMA'),
('LIMA','CALLAO','PROV. CONST. DEL CALLAO'),
('LIMA','HUACHO','HUAURA'),
('LIMA','SAN MIGUEL','LIMA'),
('LIMA','COMAS','LIMA')
GO


INSERT INTO CLIENTE
(NOMCLI,APECLI,TELCLI,CORCLI,DNICLI,ESTCLI,CODUBI)    
VALUES
('Juan Carlos','Zegarra Lopez','960849234','juan@gmail.com','73349428','A',1),
('Kevin Correa','Quispe Perez',	'932456123','Kevin@gmail.com','74474832','A',2),
('Melissa Andrea','Gonzales Mariano','908472934','Melissa@gmail.com','63874397','A',2),
('Irene Pallares','Orlando Hurtado','964347823','Irene@gmail.com','75947573','A',3),
('Jorge Arroyo','Judith Canela','749374937','Jorjito@gmail.com','74398273','A',2),
('Veronica Cespedes','Alarcon Lobato','994782789','Veronica@gmail.com','37589479','A',1),
('Montserrat Santa','Eugenia Matas','934647638','Montecito@gmail.com','75984789','A',3),
('Naiara Almagro','Marquez Diaz','945648658','Naira@gmail.com',	'53007389','A',5),
('Lidia Montesinos','Abellan Ramiro','974095689','Lidiaita@gmail.com','84798734','A',7),
('Maribel Mora','Morilla Lora','998748978','Mari@gmail.com','75893479',	'A',8)
GO

INSERT INTO USUARIO
(NOMUSU,APEUSU,DNIUSU,TELUSU,NIVUSU,ESTUSU,CODUBI)    
VALUES
('Juan Carlos','Zegarra Lopez','96084923','973349428','A','A',2),
('Kevin Correa','Quispe Perez',	'93245612','974474832','J','A',2),
('Melissa Andrea','Gonzales Mariano','90847293','963874397','V','A',5),
('Irene Pallares','Orlando Hurtado','96434782','975947573','V','A',4),
('Jorge Arroyo','Judith Canela','74937493','974398273','V','A',9),
('Veronica Cespedes','Alarcon Lobato','99478278','937589479','V','A',10),
('Montserrat Santa','Eugenia Matas','93464763','975984789','D','A',2),
('Naiara Almagro','Marquez Diaz','94564865','953007389','D','A',4),
('Lidia Montesinos','Abellan Ramiro','97409568','984798734','D','A',7),
('Maribel Mora','Morilla Lora','99874897','975893479','D','A',6)
GO


INSERT INTO SUCURSAL
(NOMSUC,CODUBI)    
VALUES
('PizzaHutEx',2),
('PizzaHutSmall',3),
('PizzaHutXXL',4),
('PizzaHutExpedisi�n',2),
('PizzaHutPower',1),
('PizzaHutExtra',8),
('PizzaHutDt',7),
('PizzaHutEspecial',6),
('PizzaHutMx',5),
('PizzaHutMax',3)
GO

INSERT INTO PRODUCTO
(NOMPRO,PREPRO,CANPRO,DESPRO,TIPPRO,ESTPRO)    
VALUES
('HAWAIANA','15.90',2,'Pescados, vegetales, frutas','Grande','A'),
('LA CHILI HUT','45.90',3,'Exquisita combinación de trozos','Familiar','A'),
('CHICKEN BBQ','45.90',1,'Deliciosa combinación de trozos de tocineta','Mediana','A'),
('XL CLASICA','59.90',2,'Extra de queso, extra de tocineta y peperoni','Pequeña','A'),
('SUPER SUPREMA','50.90',3,'Una perfecta mezcla de quesos y carnes','Grande','A'),
('MEAT LOVERS','50.90',4,'Un festín de carnes, vegetales y quesos','Familiar','A'),
('VEGETARIANA','50.90',5,'Exquisita combinación de quesos y embutidos','Mediana','A'),
('SUPREMA','50.90',3,'Nuestra famosa fusión de vegetales y quesos','Pequeña','A'),
('CONTINENTAL','40.90',2,'Peperoni, tocineta, extra de queso','Personal','A'),
('AMERICANA','40.90',1,'Piña, extra de queso.','Personal','A'),
('BARBACOA','35.90',3,'Con salsa BBQ y trozos de tocino','Grande','A'),
('MOZARELLA','40.00',2,'Combinaciones de quesos, americano, italiano.','Familiar','A'),
('ESPECIAL','30.00',3,'Rellena de queso en los border, pepenoni.','Familiar','A'),
('CALZONE','25.00',2,'Especial y rellena de albaca y queso','Mediana','A'),
('PROSCIUTTO','50.00',4,'Con trozos de Jamon Italiano.','Familiar','A'),
('CAPRICHOSA','30.00',1,'Alcachofas y champiñones','Pequeña','A'),
('MEXICANA','40.00',2,'Ingredientes típicos de México','Mediana','A'),
('LAHMACUM','70.00',2,'pan plano y fino untado con carne de cordero','Familiar','A')
GO


INSERT INTO VENTA (FECVEN,IDCLI,IDUSU)
VALUES
 ('12/05/2022',1,1),
 ('12/07/2022',2,2),
 ('12/04/2022',3,3),
 ('12/08/2022',4,4),
 ('12/02/2022',5,5),
 ('12/09/2022',6,6),
 ('12/10/2022',7,7),
 ('12/06/2022',8,8),
 ('11/07/2022',9,9)
GO


INSERT INTO VENTADETALLE(CANVENDET,CODVEN,CODPRO)
VALUES
 (1,1,1),
 (1,2,2),
 (2,3,3),
 (3,4,4),
 (4,5,5),
 (7,6,6),
 (5,7,7),
 (1,8,8),
 (2,9,9)
GO