create database formula1_2
go
use formula1_2
go

create table SPONSORI
(
	id_sponsori INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(30),
	anul INT,
	buget INT,
)

create table ECHIPE
(
	id_echipe INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(30),
	an_aparitie INT
)

create table SOFER
(
	id_sofer INT PRIMARY KEY IDENTITY(1,1),
	id_echipe INT FOREIGN KEY REFERENCES ECHIPE,
	nume VARCHAR(30),
	prenume VARCHAR(30),
	varsta INT,
	pozitie_podium INT
)

create table ISTORIC
(
	id_istoric INT PRIMARY KEY IDENTITY(1,1),
	id_sofer INT FOREIGN KEY REFERENCES SOFER,
	nume_echipa VARCHAR(30),
	perioada INT,
	puncte_aduse INT
)

create table MECANICI
(
	id_mecanici INT PRIMARY KEY IDENTITY(1,1),
	id_echipe INT FOREIGN KEY REFERENCES ECHIPE,
	nume VARCHAR(30),
	experienta INT,
	piese VARCHAR(30)
)

create table PUNCTE
(
	id_puncte INT PRIMARY KEY IDENTITY(1,1),
	id_echipe INT FOREIGN KEY REFERENCES  ECHIPE,
	numar INT,
	anul INT
)


create table ECHIPE_SPONSORI
(
	id_echipe INT FOREIGN KEY REFERENCES ECHIPE,
	id_sponsori INT FOREIGN KEY REFERENCES SPONSORI,
	suma_contribuita INT,
	CONSTRAINT echipesponsori PRIMARY KEY (id_echipe, id_sponsori)
)

create table CURSA
(
	id_cursa INT PRIMARY KEY IDENTITY(1,1),
	id_echipe INT FOREIGN KEY REFERENCES ECHIPE,
	numar INT
)

create table TARA
(
	id_tara INT PRIMARY KEY IDENTITY(1,1),
	id_cursa INT FOREIGN KEY REFERENCES CURSA,
	nume VARCHAR(30),
	continent VARCHAR(30)
)

create table ORAS
(
	id_oras INT PRIMARY KEY IDENTITY(1,1),
	id_tara INT FOREIGN KEY REFERENCES TARA,
	nume VARCHAR(30),
	sofer_provenienta_nume VARCHAR(30),
	nr_locuitori INT
)

create table PARTICIPANTI
(
	id_part INT PRIMARY KEY IDENTITY(1,1),
	id_tara INT FOREIGN KEY REFERENCES TARA,
	numar INT,
	numar_recurent INT
)
