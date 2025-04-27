USE sgbd

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

create table ECHIPE_SPONSORI
(
	id_echipe INT FOREIGN KEY REFERENCES ECHIPE,
	id_sponsori INT FOREIGN KEY REFERENCES SPONSORI,
	suma_contribuita INT,
	CONSTRAINT echipesponsori PRIMARY KEY (id_echipe, id_sponsori)
)

INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('Red Bull', 2005);
INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('Mercedes', 1954);
INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('Ferrari', 1939);
INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('McLaren', 1966);
INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('Aston Martin', 2021);
INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('Williams', 1977);
INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('Alfa Romeo', 1950);
INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('Haas', 2016);
INSERT INTO ECHIPE (nume, an_aparitie) VALUES ('Alpine', 2021);

INSERT INTO SPONSORI (nume, anul, buget) VALUES ('RedBull Racing', 2005, 50000000);
INSERT INTO SPONSORI (nume, anul, buget) VALUES ('Mercedes-Benz', 2010, 75000000);
INSERT INTO SPONSORI (nume, anul, buget) VALUES ('Rolex', 2015, 30000000);
INSERT INTO SPONSORI (nume, anul, buget) VALUES ('Shell', 2000, 40000000);
INSERT INTO SPONSORI (nume, anul, buget) VALUES ('Petronas', 2005, 65000000);
INSERT INTO SPONSORI (nume, anul, buget) VALUES ('Heineken', 2013, 55000000);
INSERT INTO SPONSORI (nume, anul, buget) VALUES ('Pirelli', 2012, 45000000);
INSERT INTO SPONSORI (nume, anul, buget) VALUES ('Emirates', 2013, 60000000);
INSERT INTO SPONSORI (nume, anul, buget) VALUES ('Honda', 2006, 55000000);

INSERT INTO ECHIPE_SPONSORI VALUES (1, 2, 45000000);
INSERT INTO ECHIPE_SPONSORI VALUES (1, 4, 45000000);
INSERT INTO ECHIPE_SPONSORI VALUES (1, 5, 31371);
INSERT INTO ECHIPE_SPONSORI VALUES (1, 9, 9187);
INSERT INTO ECHIPE_SPONSORI VALUES (2, 3, 70000000);
INSERT INTO ECHIPE_SPONSORI VALUES (2, 6, 35911);
INSERT INTO ECHIPE_SPONSORI VALUES (3, 4, 25000000);
INSERT INTO ECHIPE_SPONSORI VALUES (4, 1, 70000000);
INSERT INTO ECHIPE_SPONSORI VALUES (4, 8, 24536);
INSERT INTO ECHIPE_SPONSORI VALUES (4, 6, 50000000);
INSERT INTO ECHIPE_SPONSORI VALUES (4, 7, 30000000);
INSERT INTO ECHIPE_SPONSORI VALUES (5, 1, 8000000);
INSERT INTO ECHIPE_SPONSORI VALUES (6, 5, 25000000);
INSERT INTO ECHIPE_SPONSORI VALUES (6, 7, 35693);
INSERT INTO ECHIPE_SPONSORI VALUES (7, 7, 19432);
INSERT INTO ECHIPE_SPONSORI VALUES (8, 7, 44904);
INSERT INTO ECHIPE_SPONSORI VALUES (9, 9, 40000000);
INSERT INTO ECHIPE_SPONSORI VALUES (9, 6, 11491);

select * from ECHIPE
select * from SPONSORI
select * from ECHIPE_SPONSORI

-- Bugetul trebuie să fie mai mare de 10.000
CREATE FUNCTION uf_ValidateBuget (@buget INT)
RETURNS INT
AS
BEGIN
    RETURN CASE WHEN @buget > 10000 THEN 1 ELSE 0 END;
END

-- Anul între 2000 și anul curent
CREATE FUNCTION uf_ValidateAn (@anul INT)
RETURNS INT
AS
BEGIN
    RETURN CASE WHEN @anul BETWEEN 2000 AND YEAR(GETDATE()) THEN 1 ELSE 0 END;
END

-- Numele trebuie să înceapă cu majusculă și să aibă doar litere/spații
CREATE FUNCTION uf_ValidateName (@nume VARCHAR(30))
RETURNS INT
AS
BEGIN
    RETURN CASE 
        WHEN @nume LIKE '[A-Z]%' AND @nume NOT LIKE '%[^a-zA-Z ]%' AND LEN(@nume) > 1 THEN 1
        ELSE 0
    END;
END

-- Suma contribuției trebuie să fie între 1000 și 100.000.000
CREATE FUNCTION uf_ValidateSuma (@suma INT)
RETURNS INT
AS
BEGIN
    RETURN CASE WHEN @suma BETWEEN 1000 AND 100000000 THEN 1 ELSE 0 END;
END


-- Tabela de log
CREATE TABLE LogTable (
    Lid INT IDENTITY PRIMARY KEY,
    TypeOperation VARCHAR(50),
    TableOperation VARCHAR(50),
    ExecutionDate DATETIME DEFAULT GETDATE()
);

CREATE OR ALTER PROCEDURE AddSponsorEchipa_TotalRollback
    @nume_sponsor VARCHAR(30),
    @anul INT,
    @buget INT,
    @nume_echipa VARCHAR(30),
    @suma_contribuita INT
AS
BEGIN
    BEGIN TRAN;
    BEGIN TRY
        -- Validare sponsor
        IF dbo.uf_ValidateName(@nume_sponsor) = 0
            THROW 50010, 'Nume sponsor invalid!', 1;
        IF dbo.uf_ValidateBuget(@buget) = 0
            THROW 50011, 'Buget prea mic!', 1;
        IF dbo.uf_ValidateAn(@anul) = 0
            THROW 50012, 'Anul nu este valid!', 1;
        IF dbo.uf_ValidateName(@nume_echipa) = 0
            THROW 50013, 'Nume echipă invalid!', 1;
        IF dbo.uf_ValidateSuma(@suma_contribuita) = 0
            THROW 50014, 'Suma contribuției nu este validă!', 1;

        -- Inserare sponsor
        INSERT INTO SPONSORI(nume, anul, buget)
        VALUES (@nume_sponsor, @anul, @buget);
        DECLARE @id_sponsor INT = SCOPE_IDENTITY();

        -- Căutăm ID-ul echipei
        DECLARE @id_echipa INT;
        SELECT @id_echipa = id_echipe FROM ECHIPE WHERE nume = @nume_echipa;
        IF @id_echipa IS NULL
            THROW 50015, 'Echipa nu există!', 1;

        -- Inserare în relația m-n
        INSERT INTO ECHIPE_SPONSORI(id_echipe, id_sponsori, suma_contribuita)
        VALUES (@id_echipa, @id_sponsor, @suma_contribuita);

        -- Logare acțiune
        INSERT INTO LogTable(TypeOperation, TableOperation) 
        VALUES ('Insert', 'ECHIPE_SPONSORI');

        COMMIT;
        PRINT 'Inserare completă realizată cu succes.';
    END TRY
    BEGIN CATCH
        ROLLBACK;
        PRINT 'Eroare: ' + ERROR_MESSAGE();
    END CATCH
END

CREATE OR ALTER PROCEDURE AddSponsorEchipa_PartialRollback
    @nume_sponsor VARCHAR(30),
    @anul INT,
    @buget INT,
    @nume_echipa VARCHAR(30),
    @suma_contribuita INT
AS
BEGIN
    DECLARE @id_sponsor INT = NULL, @id_echipa INT = NULL;

    -- Validare sponsor
    IF dbo.uf_ValidateName(@nume_sponsor) = 0
    BEGIN PRINT 'Nume sponsor invalid!'; RETURN; END
    IF dbo.uf_ValidateBuget(@buget) = 0
    BEGIN PRINT 'Buget invalid!'; RETURN; END
    IF dbo.uf_ValidateAn(@anul) = 0
    BEGIN PRINT 'An invalid!'; RETURN; END

    -- Inserare sponsor
    BEGIN TRY
        BEGIN TRAN;
        INSERT INTO SPONSORI(nume, anul, buget)
        VALUES (@nume_sponsor, @anul, @buget);
        SET @id_sponsor = SCOPE_IDENTITY();
        COMMIT;

        INSERT INTO LogTable(TypeOperation, TableOperation) VALUES ('Insert', 'SPONSORI');
    END TRY
    BEGIN CATCH
        ROLLBACK;
        PRINT 'Eroare la inserarea sponsorului: ' + ERROR_MESSAGE();
    END CATCH

    -- Validare echipă
    IF dbo.uf_ValidateName(@nume_echipa) = 0
    BEGIN PRINT 'Nume echipă invalid!'; RETURN; END
    IF dbo.uf_ValidateSuma(@suma_contribuita) = 0
    BEGIN PRINT 'Suma contribuției invalidă!'; RETURN; END

    -- Căutăm echipa
    SELECT @id_echipa = id_echipe FROM ECHIPE WHERE nume = @nume_echipa;

    -- Dacă avem ambele ID-uri, inserăm în relația m-n
    IF @id_sponsor IS NOT NULL AND @id_echipa IS NOT NULL
    BEGIN
        BEGIN TRY
            BEGIN TRAN;
            INSERT INTO ECHIPE_SPONSORI(id_echipe, id_sponsori, suma_contribuita)
            VALUES (@id_echipa, @id_sponsor, @suma_contribuita);
            COMMIT;

            INSERT INTO LogTable(TypeOperation, TableOperation) VALUES ('Insert', 'ECHIPE_SPONSORI');
        END TRY
        BEGIN CATCH
            ROLLBACK;
            PRINT 'Eroare la legătura echipă-sponsor: ' + ERROR_MESSAGE();
        END CATCH
    END
    ELSE
    BEGIN
        PRINT 'Legătura nu s-a făcut (date lipsă sau echipă inexistentă).';
    END
END



-- Inserare completă reușită (TOTAL ROLLBACK)
EXEC AddSponsorEchipa_TotalRollback 
    @nume_sponsor = 'Castrol', 
    @anul = 2023, 
    @buget = 20000000, 
    @nume_echipa = 'Ferrari', 
    @suma_contribuita = 7000000;

-- Buget sub 10000 (TOTAL ROLLBACK)
EXEC AddSponsorEchipa_TotalRollback 
    @nume_sponsor = 'TinySponsor', 
    @anul = 2023, 
    @buget = 8000, 
    @nume_echipa = 'Mercedes', 
    @suma_contribuita = 3000;
	
-- Echipă inexistentă (TOTAL ROLLBACK)
EXEC AddSponsorEchipa_TotalRollback 
    @nume_sponsor = 'Monster', 
    @anul = 2022, 
    @buget = 15000000, 
    @nume_echipa = 'EchipaFantoma', 
    @suma_contribuita = 5000000;

--  Echipă inexistentă (ROLLBACK PARȚIAL)
EXEC AddSponsorEchipa_PartialRollback 
    @nume_sponsor = 'Bridgestone', 
    @anul = 2019, 
    @buget = 18000000, 
    @nume_echipa = 'FakeTeam', 
    @suma_contribuita = 4000000;

-- Buget invalid (ROLLBACK PARȚIAL)
EXEC AddSponsorEchipa_PartialRollback 
    @nume_sponsor = 'CheapBrand', 
    @anul = 2020, 
    @buget = 5000, 
    @nume_echipa = 'McLaren', 
    @suma_contribuita = 100000;

-- Inserare completă reușită (ROLLBACK PARȚIAL)
EXEC AddSponsorEchipa_PartialRollback 
    @nume_sponsor = 'Dell', 
    @anul = 2022, 
    @buget = 30000000, 
    @nume_echipa = 'McLaren', 
    @suma_contribuita = 8000000;


-- Verifică sponsorii inserați
SELECT * 
FROM SPONSORI 
WHERE nume IN ('Dell', 'Bridgestone', 'Monster');

-- Verifică legăturile create în relația m-n
SELECT * 
FROM ECHIPE_SPONSORI;

-- Verifică logul operațiunilor
SELECT * 
FROM LogTable 
ORDER BY ExecutionDate DESC;
	



