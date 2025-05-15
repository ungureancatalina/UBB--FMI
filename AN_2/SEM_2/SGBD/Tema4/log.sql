use sgbd
go

create table TARA
(
	id_tara INT PRIMARY KEY IDENTITY(1,1),
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


insert into TARA(nume, continent)
values 
('Monaco', 'Europe'),
('United States', 'North America'),
('Brazil', 'South America'),
('United Kingdom', 'Europe'),
('Australia', 'Australia'),
('Canada', 'North America'),
('Japan', 'Asia'),
('Spain', 'Europe'),
('Mexico', 'North America');

insert into ORAS(id_tara, nume, sofer_provenienta_nume, nr_locuitori)
values 
(1, 'Monte Carlo', 'Charles Leclerc', 39000),
(2, 'Austin', 'Lance Stroll', 960000),
(3, 'São Paulo', 'Ayrton Senna', 12252000),
(4, 'London', 'Lewis Hamilton', 8900000),
(5, 'Miami', 'Logan Sargeant', 442000),
(6, 'Las Vegas', 'Alexander Rossi', 650000),
(6, 'Melbourne', 'Daniel Ricciardo', 5080000),
(7, 'Barcelona', 'Carlos Sainz', 1600000),
(8, 'Mexico City', 'Sergio Perez', 9200000);

select * from TARA
select * from ORAS

-- Creare tabel log
CREATE TABLE Transaction_Log (
    Log_ID INT PRIMARY KEY IDENTITY,
    Transaction_Name NVARCHAR(100),
    Action NVARCHAR(100),
    Timestamp DATETIME DEFAULT GETDATE(),
    Status NVARCHAR(50)
);

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;