use formula1_2
go

--functie de random
CREATE OR ALTER FUNCTION RandomInt2(@lower INT, @upper INT)
    RETURNS INT
AS
BEGIN
    RETURN FLOOR((SELECT Value FROM RandomView) * (@upper - @lower) + @lower)
END
GO


--tabelele
CREATE TABLE Telespectator
(
    TelespectatorID INT PRIMARY KEY IDENTITY,
    Nume VARCHAR(50) NOT NULL,
    Telefon INT NOT NULL CHECK (Telefon >= 1000 AND Telefon <= 99999),
    Varsta INT CHECK (Varsta >= 1 AND Varsta <= 80),
    Gen VARCHAR(100)
);

CREATE TABLE Job
(
    JobID INT PRIMARY KEY IDENTITY,
    NumeJob VARCHAR(50) NOT NULL,
    Departament VARCHAR(50)
);

CREATE TABLE Job_Telespectator
(
    JobID INT REFERENCES Job(JobID),
    TelespectatorID INT REFERENCES Telespectator(TelespectatorID),
    PRIMARY KEY (JobID, TelespectatorID)
);

CREATE TABLE Studii
(
    StudiiID INT PRIMARY KEY IDENTITY,
    Nivel VARCHAR(50) NOT NULL,
    Institutie VARCHAR(100)
);

CREATE TABLE Studii_Telespectator
(
    StudiiID INT REFERENCES Studii(StudiiID),
    TelespectatorID INT REFERENCES Telespectator(TelespectatorID),
    PRIMARY KEY (StudiiID, TelespectatorID)
);


--functii de validare
CREATE OR ALTER FUNCTION ValidateNotNull (
    @Value NVARCHAR(MAX)
)
RETURNS BIT
AS
BEGIN
    RETURN CASE WHEN @Value IS NOT NULL AND LTRIM(RTRIM(@Value)) <> '' THEN 1 ELSE 0 END;
END;
GO

CREATE OR ALTER FUNCTION ValidateGen(
    @Gen NVARCHAR(50)
)
RETURNS BIT
AS
BEGIN
    RETURN CASE 
        WHEN @Gen IN ('Masculin', 'Feminin') THEN 1 
        ELSE 0 
    END;
END;
GO

CREATE OR ALTER FUNCTION ValidateDepartament(
    @Departament NVARCHAR(50)
)
RETURNS BIT
AS
BEGIN
    RETURN CASE 
        WHEN @Departament IN ('IT', 'HR', 'Sales') THEN 1 
        ELSE 0 
    END;
END;
GO

CREATE OR ALTER FUNCTION ValidateNivel(
    @Nivel NVARCHAR(50)
)
RETURNS BIT
AS
BEGIN
    RETURN CASE 
        WHEN @Nivel IN ('Licenta', 'Master', 'Doctorat') THEN 1 
        ELSE 0 
    END;
END;
GO


--CRUD Telespectator
CREATE OR ALTER PROCEDURE InsertTelespectator @seed INT, @NewTelespectatorID INT OUTPUT
AS
BEGIN
    DECLARE @Telefon INT = dbo.RandomInt2(1000, 99999);
    DECLARE @Varsta INT = dbo.RandomInt2(1, 80);
    DECLARE @Nume NVARCHAR(50) = 'Telespectator ' + CONVERT(VARCHAR(50), @seed);
    DECLARE @Gen NVARCHAR(100) = CASE dbo.RandomInt2(1, 2)
        WHEN 1 THEN 'Feminin'
        WHEN 2 THEN 'Masculin'
    END;

    IF dbo.ValidateNotNull(@Nume) = 0
    BEGIN
        THROW 50000, 'Validarea a esuat pentru InsertTelespectator.', 1;
    END;

	IF dbo.ValidateGen(@Gen) = 0
    BEGIN
        THROW 50001, 'Validarea a esuat pentru Gen in InsertTelespectator.', 1;
    END;

    INSERT INTO Telespectator (Nume, Telefon, Varsta, Gen)
    VALUES (@Nume, @Telefon, @Varsta, @Gen);

	SET @NewTelespectatorID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE UpdateTelespectator
    @TelespectatorID INT,
    @Nume VARCHAR(50),
    @Telefon INT,
    @Varsta INT,
    @Gen VARCHAR(100)
AS
BEGIN
    IF dbo.ValidateNotNull(@Nume) = 0
    BEGIN
        THROW 50000, 'Validarea a esuat pentru UpdateTelespectator.', 1;
    END;

	IF dbo.ValidateGen(@Gen) = 0
    BEGIN
        THROW 50001, 'Validarea a esuat pentru Gen in InsertTelespectator.', 1;
    END;

    UPDATE Telespectator
    SET Nume = @Nume, Telefon = @Telefon, Varsta = @Varsta, Gen = @Gen
    WHERE TelespectatorID = @TelespectatorID;
END;
GO

CREATE OR ALTER PROCEDURE GetTelespectatorById
    @TelespectatorID INT
AS
BEGIN
    SELECT * FROM Telespectator WHERE TelespectatorID = @TelespectatorID;
END;
GO

CREATE OR ALTER PROCEDURE DeleteTelespectator
    @TelespectatorID INT
AS
BEGIN
    DELETE FROM Telespectator WHERE TelespectatorID = @TelespectatorID;
END;
GO


--CRUD Job
CREATE OR ALTER PROCEDURE InsertJob @seed INT
AS
BEGIN
    DECLARE @NumeJob NVARCHAR(50) = 'Job ' + CONVERT(VARCHAR(50), @seed);
    DECLARE @Departament NVARCHAR(50) = CASE dbo.RandomInt2(1, 3)
        WHEN 1 THEN 'IT'
        WHEN 2 THEN 'HR'
        WHEN 3 THEN 'Sales'
    END;

    IF dbo.ValidateNotNull(@NumeJob) = 0 OR dbo.ValidateNotNull(@Departament) = 0
    BEGIN
        THROW 50000, 'Validarea a esuat pentru InsertJob.', 1;
    END;

	IF dbo.ValidateDepartament(@Departament) = 0
    BEGIN
        THROW 50002, 'Validarea a esuat pentru Departament in InsertJob.', 1;
    END;

    INSERT INTO Job (NumeJob, Departament)
    VALUES (@NumeJob, @Departament);
END;
GO

CREATE OR ALTER PROCEDURE UpdateJob
    @JobID INT,
    @NumeJob VARCHAR(50),
    @Departament VARCHAR(50)
AS
BEGIN
    IF dbo.ValidateNotNull(@NumeJob) = 0 OR dbo.ValidateNotNull(@Departament) = 0
    BEGIN
        THROW 50000, 'Validarea a esuat pentru UpdateJob.', 1;
    END;
	IF dbo.ValidateDepartament(@Departament) = 0
    BEGIN
        THROW 50002, 'Validarea a esuat pentru Departament in InsertJob.', 1;
    END;

    UPDATE Job
    SET NumeJob = @NumeJob, Departament = @Departament
    WHERE JobID = @JobID;
END;
GO

CREATE OR ALTER PROCEDURE GetJobById
    @JobID INT
AS
BEGIN
    SELECT * FROM Job WHERE JobID = @JobID;
END;
GO

CREATE OR ALTER PROCEDURE DeleteJob
    @JobID INT
AS
BEGIN
    DELETE FROM Job WHERE JobID = @JobID;
END;
GO


--CRUD Studii
CREATE OR ALTER PROCEDURE InsertStudii @seed INT
AS
BEGIN
    DECLARE @Nivel NVARCHAR(50) = CASE dbo.RandomInt2(1, 3)
        WHEN 1 THEN 'Licenta'
        WHEN 2 THEN 'Master'
        WHEN 3 THEN 'Doctorat'
    END;
    DECLARE @Institutie NVARCHAR(100) = 'Universitatea ' + CONVERT(VARCHAR(50), dbo.RandomInt2(1, 10));

    IF dbo.ValidateNotNull(@Nivel) = 0 OR dbo.ValidateNotNull(@Institutie) = 0
    BEGIN
        THROW 50000, 'Validarea a esuat pentru InsertStudii.', 1;
    END;
	 IF dbo.ValidateNivel(@Nivel) = 0
    BEGIN
        THROW 50003, 'Validarea a esuat pentru Nivel in InsertStudii.', 1;
    END;

    INSERT INTO Studii (Nivel, Institutie)
    VALUES (@Nivel, @Institutie);
END;
GO

CREATE OR ALTER PROCEDURE UpdateStudii
    @StudiiID INT,
    @Nivel VARCHAR(50),
    @Institutie VARCHAR(100)
AS
BEGIN
    IF dbo.ValidateNotNull(@Nivel) = 0 OR dbo.ValidateNotNull(@Institutie) = 0
    BEGIN
        THROW 50000, 'Validarea a esuat pentru UpdateStudii.', 1;
    END;
	 IF dbo.ValidateNivel(@Nivel) = 0
    BEGIN
        THROW 50003, 'Validarea a esuat pentru Nivel in InsertStudii.', 1;
    END;

    UPDATE Studii
    SET Nivel = @Nivel, Institutie = @Institutie
    WHERE StudiiID = @StudiiID;
END;
GO

CREATE OR ALTER PROCEDURE GetStudiiById
    @StudiiID INT
AS
BEGIN
    SELECT * FROM Studii WHERE StudiiID = @StudiiID;
END;
GO

CREATE OR ALTER PROCEDURE DeleteStudii
    @StudiiID INT
AS
BEGIN
    DELETE FROM Studii WHERE StudiiID = @StudiiID;
END;
GO


--CRUD Job_Telespectator
CREATE OR ALTER PROCEDURE InsertJobTelespectator
AS
BEGIN
    DECLARE @id_telespectator INT = (SELECT TOP 1 TelespectatorID FROM Telespectator ORDER BY NEWID());
    DECLARE @id_job INT = (SELECT TOP 1 JobID FROM Job ORDER BY NEWID());
    IF NOT EXISTS (SELECT * FROM Job_Telespectator WHERE TelespectatorID = @id_telespectator AND JobID = @id_job)
    BEGIN
        INSERT INTO Job_Telespectator (TelespectatorID, JobID)
        VALUES (@id_telespectator, @id_job);
    END
END;
GO

CREATE OR ALTER PROCEDURE UpdateJobTelespectator
    @JobID INT,
    @TelespectatorID INT,
    @NewJobID INT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM Job WHERE JobID = @NewJobID)
    BEGIN
        THROW 50000, 'JobID nou nu exista.', 1;
    END;

    UPDATE Job_Telespectator
    SET JobID = @NewJobID
    WHERE JobID = @JobID AND TelespectatorID = @TelespectatorID;
END;
GO

CREATE OR ALTER PROCEDURE GetJobsForTelespectator
    @TelespectatorID INT
AS
BEGIN
    SELECT j.JobID, j.NumeJob, j.Departament
    FROM Job_Telespectator jt
    INNER JOIN Job j ON jt.JobID = j.JobID
    WHERE jt.TelespectatorID = @TelespectatorID;
END;
GO

CREATE OR ALTER PROCEDURE DeleteJobTelespectator
    @TelespectatorID INT,
    @JobID INT
AS
BEGIN
    DELETE FROM Job_Telespectator 
    WHERE TelespectatorID = @TelespectatorID AND JobID = @JobID;
END;
GO



--CRUD Studii_Telespectator
CREATE OR ALTER PROCEDURE InsertStudiiTelespectator
AS
BEGIN
    DECLARE @id_telespectator INT = (SELECT TOP 1 TelespectatorID FROM Telespectator ORDER BY NEWID());
    DECLARE @id_studii INT = (SELECT TOP 1 StudiiID FROM Studii ORDER BY NEWID());
    IF NOT EXISTS (SELECT * FROM Studii_Telespectator WHERE TelespectatorID = @id_telespectator AND StudiiID = @id_studii)
    BEGIN
        INSERT INTO Studii_Telespectator (TelespectatorID, StudiiID)
        VALUES (@id_telespectator, @id_studii);
    END
END;
GO

CREATE OR ALTER PROCEDURE UpdateStudiiTelespectator
    @StudiiID INT,
    @TelespectatorID INT,
    @NewStudiiID INT
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM Studii WHERE StudiiID = @NewStudiiID)
    BEGIN
        THROW 50000, 'StudiiID nou nu exista.', 1;
    END;

    UPDATE Studii_Telespectator
    SET StudiiID = @NewStudiiID
    WHERE StudiiID = @StudiiID AND TelespectatorID = @TelespectatorID;
END;
GO

CREATE OR ALTER PROCEDURE GetStudiiForTelespectator
    @TelespectatorID INT
AS
BEGIN
    SELECT s.StudiiID, s.Nivel, s.Institutie
    FROM Studii_Telespectator st
    INNER JOIN Studii s ON st.StudiiID = s.StudiiID
    WHERE st.TelespectatorID = @TelespectatorID;
END;
GO

CREATE OR ALTER PROCEDURE DeleteStudiiTelespectator
    @TelespectatorID INT,
    @StudiiID INT
AS
BEGIN
    DELETE FROM Studii_Telespectator 
    WHERE TelespectatorID = @TelespectatorID AND StudiiID = @StudiiID;
END;
GO

-- View-uri
CREATE OR ALTER VIEW ViewTelespectatorJob
AS
SELECT t.TelespectatorID,t.Nume AS TelespectatorNume,t.Telefon,t.Varsta,t.Gen,j.JobID,j.NumeJob,j.Departament
FROM Telespectator t
INNER JOIN Job_Telespectator jt ON t.TelespectatorID = jt.TelespectatorID
INNER JOIN Job j ON jt.JobID = j.JobID;
GO

CREATE OR ALTER VIEW ViewTelespectatorStudii
AS
SELECT t.TelespectatorID,t.Nume AS TelespectatorNume,t.Telefon,t.Varsta,t.Gen,s.StudiiID,s.Nivel,s.Institutie
FROM Telespectator t
INNER JOIN Studii_Telespectator st ON t.TelespectatorID = st.TelespectatorID
INNER JOIN Studii s ON st.StudiiID = s.StudiiID;
GO

--index
CREATE NONCLUSTERED INDEX IDX_TelespectatorID ON Telespectator (TelespectatorID);
CREATE NONCLUSTERED INDEX IDX_Telespectator_Nume ON Telespectator (Nume);

CREATE NONCLUSTERED INDEX IDX_JobID ON Job (JobID);
CREATE NONCLUSTERED INDEX IDX_Job_NumeJob ON Job (NumeJob);

CREATE NONCLUSTERED INDEX IDX_JobTelespectator_TelespectatorID ON Job_Telespectator (TelespectatorID);
CREATE NONCLUSTERED INDEX IDX_JobTelespectator_JobID ON Job_Telespectator (JobID);

CREATE NONCLUSTERED INDEX IDX_StudiiID ON Studii (StudiiID);
CREATE NONCLUSTERED INDEX IDX_Studii_Nivel ON Studii (Nivel);

CREATE NONCLUSTERED INDEX IDX_StudiiTelespectator_TelespectatorID ON Studii_Telespectator (TelespectatorID);
CREATE NONCLUSTERED INDEX IDX_StudiiTelespectator_StudiiID ON Studii_Telespectator (StudiiID);

SELECT 
    t.name AS TableName,
    i.name AS IndexName,
    i.type_desc AS IndexType,
    i.is_primary_key AS IsPrimaryKey,
    i.is_unique AS IsUnique,
    i.is_unique_constraint AS IsUniqueConstraint
FROM sys.tables t
JOIN sys.indexes i ON t.object_id = i.object_id
WHERE i.type_desc = 'NONCLUSTERED'
ORDER BY t.name, i.name;





EXEC InsertTelespectator @seed = 1;
EXEC InsertTelespectator @seed = 2;
EXEC InsertTelespectator @seed = 3;
EXEC InsertTelespectator @seed = 4;
EXEC UpdateTelespectator @TelespectatorID = 1, @Nume = 'Actualizat Telespectator', @Telefon = 99999, @Varsta = 25, @Gen = 'Feminin';
EXEC GetTelespectatorById 2;
EXEC DeleteTelespectator 4;
DELETE FROM Telespectator;

DECLARE @InsertedID INT;
EXEC InsertTelespectator @seed = 1, @NewTelespectatorID = @InsertedID OUTPUT;
SELECT @InsertedID AS NewTelespectatorID;


EXEC InsertJob @seed = 1;
EXEC InsertJob @seed = 2;
EXEC InsertJob @seed = 3;
EXEC InsertJob @seed = 4;
EXEC UpdateJob @JobID = 1, @NumeJob = 'Actualizat Job', @Departament = 'Nou Departament';
EXEC DeleteJob @JobID = 3;
DELETE FROM Job;

EXEC InsertStudii @seed = 1;
EXEC InsertStudii @seed = 2;
EXEC InsertStudii @seed = 3;
EXEC InsertStudii @seed = 4;
EXEC UpdateStudii @StudiiID = 1, @Nivel = 'Licenta Actualizata', @Institutie = 'Noua Universitate';
EXEC DeleteStudii @StudiiID = 2;
DELETE FROM Studii;

EXEC InsertJobTelespectator;
EXEC UpdateJobTelespectator @JobID = 1, @TelespectatorID = 1, @NewJobID = 2;
DELETE FROM Job_Telespectator;

DECLARE @InsertedID INT;
EXEC InsertTelespectator @seed = 1, @NewTelespectatorID = @InsertedID OUTPUT;
SELECT @InsertedID AS NewTelespectatorID;

EXEC InsertStudiiTelespectator;
EXEC UpdateStudiiTelespectator @StudiiID = 1, @TelespectatorID = 1, @NewStudiiID = 3;
DELETE FROM Studii_Telespectator;

SELECT * FROM Telespectator
SELECT * FROM Job
SELECT * FROM Studii
SELECT * FROM Job_Telespectator
SELECT * FROM Studii_Telespectator

SELECT * FROM ViewTelespectatorJob;
SELECT * FROM ViewTelespectatorStudii;

DROP TABLE Telespectator
DROP TABLE Job
DROP TABLE Studii
DROP TABLE Job_Telespectator
DROP TABLE Studii_Telespectator

DROP INDEX IDX_TelespectatorID ON Telespectator;
DROP INDEX IDX_Telespectator_Nume ON Telespectator;
DROP INDEX IDX_JobID ON Job;
DROP INDEX IDX_Job_NumeJob ON Job;
DROP INDEX IDX_JobTelespectator_TelespectatorID ON Job_Telespectator;
DROP INDEX IDX_JobTelespectator_JobID ON Job_Telespectator;
DROP INDEX IDX_StudiiID ON Studii;
DROP INDEX IDX_Studii_Nivel ON Studii;
DROP INDEX IDX_StudiiTelespectator_TelespectatorID ON Studii_Telespectator;
DROP INDEX IDX_StudiiTelespectator_StudiiID ON Studii_Telespectator;
