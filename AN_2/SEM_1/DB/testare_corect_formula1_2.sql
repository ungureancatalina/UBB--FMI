use formula1_2
GO

CREATE OR ALTER VIEW RandomView
AS
SELECT RAND() AS Value
GO

CREATE OR ALTER FUNCTION RandomInt(@lower INT, @upper INT)
    RETURNS INT
AS
BEGIN
    RETURN FLOOR((SELECT Value FROM RandomView) * (@upper - @lower) + @lower)
END
GO

CREATE OR ALTER PROCEDURE InsertEchipa @seed INT
AS
BEGIN
    INSERT INTO ECHIPE(nume, an_aparitie)
    VALUES ('Echipa-' + CONVERT(VARCHAR(50), @seed), dbo.RandomInt(1950, 2023))
END
GO

CREATE OR ALTER PROCEDURE InsertSofer @seed INT
AS
BEGIN
    INSERT INTO SOFER(id_echipe, nume, prenume, varsta, pozitie_podium)
    VALUES (
        (SELECT TOP 1 id_echipe FROM ECHIPE ORDER BY NEWID()),
        'Sofer-' + CONVERT(VARCHAR(50), @seed),
        'Prenume-' + CONVERT(VARCHAR(50), @seed),
        dbo.RandomInt(20, 40),
        dbo.RandomInt(1, 10)
    )
END
GO

CREATE OR ALTER PROCEDURE InsertEchipeSponsori @seed INT
AS
BEGIN
    DECLARE @id_echipe INT = (SELECT TOP 1 id_echipe FROM ECHIPE ORDER BY NEWID())
    DECLARE @id_sponsori INT = (SELECT TOP 1 id_sponsori FROM SPONSORI ORDER BY NEWID())
    IF NOT EXISTS (SELECT * FROM ECHIPE_SPONSORI WHERE id_echipe = @id_echipe AND id_sponsori = @id_sponsori)
    BEGIN
        INSERT INTO ECHIPE_SPONSORI(id_echipe, id_sponsori, suma_contribuita)
        VALUES (@id_echipe, @id_sponsori, dbo.RandomInt(5000, 50000))
    END
END
GO

CREATE OR ALTER PROCEDURE PopulateTable @tableName VARCHAR(50), @noRows INT
AS
BEGIN
    DECLARE @currentRow INT = 0
    WHILE @currentRow < @noRows
    BEGIN
        DECLARE @command NVARCHAR(MAX);
		SET @command = 'EXEC Insert' + @tableName + ' ' + CAST(@currentRow AS NVARCHAR(10)); 
		EXEC sp_executesql @command;
        SET @currentRow = @currentRow + 1
    END
END
GO

CREATE OR ALTER PROCEDURE ClearTable @tableName VARCHAR(50)
AS
    EXEC ('DELETE FROM ' + @tableName)
GO
CREATE OR ALTER VIEW VSofer AS
	SELECT I.nume_echipa AS nume_echipa_istoric, S.nume AS nume_sofer, S.prenume,S.pozitie_podium, S.varsta, E.nume AS nume_echipa
	FROM SOFER S
	JOIN ISTORIC I ON I.id_sofer = S.id_sofer
	JOIN ECHIPE E ON E.id_echipe = S.id_echipe
GO


CREATE OR ALTER VIEW VEchipe AS
	SELECT nume, an_aparitie
	FROM ECHIPE
GO

CREATE OR ALTER VIEW VEchipe_Sponsori AS
    SELECT ES.id_echipe, ES.id_sponsori, E.nume AS nume_echipa, S.nume AS nume_sponsori
    FROM ECHIPE_SPONSORI ES
    JOIN ECHIPE E ON E.id_echipe = ES.id_echipe
	JOIN SPONSORI S ON S.id_sponsori = ES.id_sponsori
	GROUP BY ES.id_echipe, ES.id_sponsori, E.nume, S.nume;
GO


EXEC PopulateTable 'Echipa', 10
EXEC PopulateTable 'Sofer', 20
EXEC PopulateTable 'EchipeSponsori', 15

EXEC ClearTable 'ECHIPE_SPONSORI';
EXEC ClearTable 'SOFER';
EXEC ClearTable 'ECHIPE';

SELECT * FROM ECHIPE
SELECT * FROM SOFER
SELECT * FROM ECHIPE_SPONSORI
SELECT * FROM VSofer;
SELECT * FROM VEchipe;
SELECT * FROM VEchipe_Sponsori;

DELETE FROM SOFER WHERE id_sofer>1000
DELETE FROM Echipe_Sponsori WHERE id_echipe>1000
DELETE FROM Echipe WHERE id_echipe>1000



CREATE OR ALTER PROCEDURE CreateTest @name VARCHAR(50)
AS
BEGIN
    IF EXISTS(SELECT * FROM Tests T WHERE Name = @name)
        BEGIN
            PRINT 'Exista deja'
            RETURN
        END
    INSERT INTO Tests(Name) VALUES (@name)
END
EXEC CreateTest 'CreareTest1'


CREATE OR ALTER PROCEDURE AddTableToTestTable @tableName VARCHAR(50)
AS
    BEGIN
        IF NOT EXISTS(SELECT * FROM sys.tables WHERE name = @tableName)
            BEGIN
                PRINT 'Tabelul' + @tableName + ' nu exista'
                RETURN
            END
        IF EXISTS(SELECT * FROM Tables T WHERE T.Name = @tableName)
            BEGIN
                PRINT 'Tabelul ' + @tableName + ' este adaugat deja'
                RETURN
            END
        INSERT INTO Tables(Name) VALUES (@tableName)
    END

EXEC AddTableToTestTable 'ECHIPE'
EXEC AddTableToTestTable 'SOFER'
EXEC AddTableToTestTable 'ECHIPE_SPONSORI'


CREATE OR ALTER PROCEDURE RelateTestsAndTables @tableName VARCHAR(50), @testName VARCHAR(50), @noRows INT, @position INT
AS
    BEGIN
        IF @position < 0
        BEGIN
            PRINT 'Pozitia >0'
            RETURN
        END
        IF @noRows < 0
        BEGIN
            PRINT 'Nr de randuri >0'
            RETURN
        END

        DECLARE @testID INT, @tableID INT
        SET @testID = (SELECT T.TestID FROM Tests T WHERE T.Name = @testName)
        SET @tableID = (SELECT T.TableID FROM Tables T WHERE T.Name = @tableName)
        INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES
        (@testID, @tableID, @noRows, @position)
    END
EXEC RelateTestsAndTables 'ECHIPE_SPONSORI', 'CreareTest1', 20, 0


CREATE OR ALTER PROCEDURE AddViewToTestTable @viewName VARCHAR(50)
AS
BEGIN
    IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = @viewName)
    BEGIN
        PRINT 'View-ul nu existat'
        RETURN
    end
    IF EXISTS(SELECT * FROM Views WHERE Name = @viewName)
    BEGIN
        PRINT 'View-ul e deja adaugat'
        RETURN
    end
    INSERT INTO Views(Name) VALUES (@viewName)
end

CREATE OR ALTER PROCEDURE RelateTestsAndViews @viewName VARCHAR(50), @testName VARCHAR(50)
AS
    BEGIN
        DECLARE @testID INT, @viewID INT
        SET @testID = (SELECT TestID FROM Tests WHERE Name = @testName)
        SET @viewID = (SELECT ViewID FROM Views WHERE Name = @viewName)
        INSERT INTO TestViews(testid, viewid) VALUES (@testID, @viewID)
    end


CREATE OR ALTER PROCEDURE RunTest @name VARCHAR(50)
AS
    DECLARE @test INT
    SET @test = (SELECT T.TestID FROM Tests T WHERE T.Name = @name)

    DECLARE @tableName VARCHAR(50), @noRows INT, @tableID INT,
        @allTestsStartTime DATETIME2, @allTestsEndTime DATETIME2,
        @currentTestStartTime DATETIME2, @currentTestEndTime DATETIME2,
        @testRunID INT, @command VARCHAR(256),
        @viewName VARCHAR(50), @viewID INT

    INSERT INTO TestRuns(Description) VALUES (@name)
    SET @testRunID = CONVERT(INT, (SELECT last_value FROM sys.identity_columns WHERE name = 'TestRunID'))

    DECLARE TablesCursor CURSOR SCROLL FOR
    SELECT T2.TableID, T2.Name, TT.NoOfRows FROM TestTables TT INNER JOIN Tables T2 on T2.TableID = TT.TableID
    WHERE TT.TestID = @test
    ORDER BY TT.Position

    DECLARE ViewsCursor CURSOR FOR
    SELECT V.ViewID, V.Name FROM Views V INNER JOIN TestViews TV on V.ViewID = TV.ViewID
    WHERE TV.TestID = @test

    SET @allTestsStartTime = SYSDATETIME();
    OPEN TablesCursor

    FETCH FIRST FROM TablesCursor INTO @tableID, @tableName, @noRows

    WHILE @@FETCH_STATUS = 0
    BEGIN
        SET @currentTestStartTime = SYSDATETIME();
        SET @command = 'PopulateTable ' + char(39) + @tableName + char(39) + ', ' + CONVERT(VARCHAR(10), @noRows)
        PRINT @noRows
        EXEC(@command)
        SET @currentTestEndTime = SYSDATETIME();
        INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@testRunID, @tableID, @currentTestStartTime, @currentTestEndTime)
        FETCH NEXT FROM TablesCursor INTO @tableID, @tableName, @noRows
    end

    CLOSE TablesCursor
    OPEN TablesCursor
    FETCH LAST FROM TablesCursor INTO @tableID, @tableName, @noRows

    WHILE @@FETCH_STATUS = 0
    BEGIN
        EXEC ClearTable @tableName
        FETCH PRIOR FROM TablesCursor INTO @tableID, @tableName, @noRows
    end
    CLOSE TablesCursor
    DEALLOCATE TablesCursor

    OPEN ViewsCursor
    FETCH FROM ViewsCursor INTO @viewID, @viewName
    WHILE @@FETCH_STATUS = 0
        BEGIN
            SET @currentTestStartTime = SYSDATETIME()
            DECLARE @statement VARCHAR(256)
            SET @statement = 'SELECT * FROM ' + @viewName
            PRINT @statement
            EXEC (@statement)
            SET @currentTestEndTime = SYSDATETIME()
            INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES (@testRunID, @viewID, @currentTestStartTime, @currentTestEndTime)
            FETCH NEXT FROM ViewsCursor INTO @viewID, @viewName
        end
    SET @allTestsEndTime = SYSDATETIME();
    CLOSE ViewsCursor
    DEALLOCATE ViewsCursor
    UPDATE TestRuns
	SET StartAt = @allTestsStartTime, EndAt = @allTestsEndTime
	WHERE TestRunID = @testRunID
go

SELECT * FROM ECHIPE
SELECT * FROM SOFER
SELECT * FROM ECHIPE_SPONSORI

SELECT * FROM VSofer;
SELECT * FROM VEchipe;
SELECT * FROM VEchipe_Sponsori;

EXEC CreateTest 'CreareTest2'
EXEC RelateTestsAndTables 'ECHIPE', 'CreareTest2', 100, 2
EXEC RelateTestsAndTables 'SOFER', 'CreareTest2', 150, 1
EXEC RelateTestsAndTables 'ECHIPE_SPONSORI', 'CreareTest2', 33, 3

EXEC AddViewToTestTable 'VSofer'
EXEC AddViewToTestTable 'VEchipe'
EXEC AddViewToTestTable 'VEchipe_Sponsori'

EXEC RelateTestsAndViews 'VSofer', 'CreareTest2'
EXEC RelateTestsAndViews 'VEchipe', 'CreareTest2'
EXEC RelateTestsAndViews 'VEchipe_Sponsori', 'CreareTest2'

SELECT * FROM Views
SELECT * FROM TestViews
SELECT * FROM TestTables
SELECT * FROM TestRunTables
SELECT * FROM TestRuns
SELECT * FROM TestRunViews

PRINT SYSDATETIME()
EXEC RunTest 'CreareTest2'
EXEC RunTest 'CreareTest1'

DELETE FROM SOFER WHERE id_sofer>1000
DELETE FROM Echipe_Sponsori WHERE id_echipe>1000
DELETE FROM Echipe WHERE id_echipe>1000
