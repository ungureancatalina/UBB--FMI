use formula1_2
go

insert into ECHIPE( nume, an_aparitie)
values 
('Red Bull', 2005),
('Mercedes', 1954),
('Ferrari', 1939),
('McLaren', 1966),
('Aston Martin', 2021),
('Williams', 1977),
('Alfa Romeo', 1950),
('Haas', 2016),
('Alpine', 2021);

insert into SPONSORI(nume, anul, buget)
values 
('RedBull Racing', 2005, 50000000),
('Mercedes-Benz', 2010, 75000000),
('Rolex', 2015, 30000000),
('Shell', 2000, 40000000),
('Petronas', 2013, 65000000),
('Heineken', 2014, 35000000),
('Pirelli', 2013, 45000000),
('Emirates', 2012, 60000000),
('Honda', 2006, 55000000);

insert into ECHIPE_SPONSORI(id_echipe, id_sponsori, suma_contribuita)
values 
(1, 2, 45000000),
(2, 3, 70000000),
(3, 4, 25000000),
(4, 7, 30000000),
(5, 6, 50000000),
(6, 5, 25000000),
(7, 8, 45000000),
(8, 9, 40000000),
(9, 1, 50000000);

insert into ECHIPE_SPONSORI(id_echipe, id_sponsori, suma_contribuita)
values 
(1, 4, 45000000),
(4, 5, 70000000);

insert into SOFER(id_echipe, nume, prenume, varsta, pozitie_podium)
values 
(1, 'Verstappen', 'Max', 26, 1),
(2, 'Hamilton', 'Lewis', 39, 2),
(3, 'Leclerc', 'Charles', 26, 3),
(4, 'Norris', 'Lando', 24, 4),
(5, 'Alonso', 'Fernando', 42, 5),
(2, 'Russell', 'George', 25, 5),
(7, 'Bottas', 'Valtteri', 34, 6),
(1, 'Perez', 'Sergio', 33, 2),          
(3, 'Sainz', 'Carlos', 29, 3),       
(4, 'Vettel', 'Sebastian', 36, 1); 

insert into MECANICI(id_echipe, nume, experienta, piese)
values 
(1, 'Adrian Newey', 25, 'Aerodynamics'),
(2, 'James Allison', 20, 'Engine'),
(3, 'David Sanchez', 18, 'Chassis'),
(4, 'Andrea Stella', 15, 'Brakes'),
(4, 'Tom McCullough', 17, 'Suspension'),
(5, 'Jonathan Williams', 22, 'Gearbox'),
(6, 'Andrea Ferrari', 19, 'Chassis'),
(7, 'Pietro Ferrari', 15, 'Suspension'),
(8, 'Jorge Martinez', 20, 'Brakes');

insert into MECANICI(id_echipe, nume, experienta, piese)
values 
(2, 'Adam Brooke', 24, 'Brakes'),
(6, 'Jocey Stancey', 27, 'Engine');

insert into PUNCTE(id_echipe, numar, anul)
values 
(1, 950, 2023),    
(2, 850, 2023),    
(3, 550, 2023),   
(4, 450, 2023),   
(5, 320, 2023),   
(6, 800, 2022),    
(7, 765, 2022),   
(8, 505, 2022),    
(9, 375, 2022);

insert into CURSA(id_echipe, numar)
values 
(1, 57),
(2, 44),
(9, 70),
(3, 31),
(5, 21),
(6, 38),
(7, 47),
(4, 29),
(8, 42);

insert into CURSA(id_echipe, numar)
values (1,40), (2,50);

insert into ISTORIC(id_sofer, nume_echipa, perioada, puncte_aduse)
values 
(1, 'Scuderia Toro Rosso', 2015, 340),
(2, 'McLaren', 2007, 755),
(3, 'Alfa Romeo Sauber', 2018, 175),
(4, 'McLaren', 2019, 120),
(5, 'Ferrari', 2010, 1020),
(5, 'Alpine',2021,160);

insert into TARA(id_cursa, nume, continent)
values 
(1, 'Monaco', 'Europe'),
(2, 'United States', 'North America'),
(3, 'Brazil', 'South America'),
(4, 'United Kingdom', 'Europe'),
(5, 'Australia', 'Australia'),
(6, 'Canada', 'North America'),
(7, 'Japan', 'Asia'),
(1, 'Spain', 'Europe'),
(2, 'Mexico', 'North America');

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

insert into PARTICIPANTI(id_tara, numar, numar_recurent)
values 
(1, 50000, 45000),  
(2, 40000, 38000),  
(3, 60000, 55000),  
(4, 70000, 65000),  
(5, 80000, 75000), 
(6, 90000, 85000),
(7, 75000, 70000),
(8, 65000, 60000);

select * from ECHIPE
select * from SPONSORI
select * from SOFER
select * from MECANICI
select * from PUNCTE
select * from CURSA
select * from ISTORIC
select * from TARA
select * from ORAS
select * from PARTICIPANTI
select * from ECHIPE_SPONSORI