use formula1_2

INSERT INTO Tables(Name) VALUES
('Sofer'), ('Echipe'), ('Echipe_Sponsori'); 

SELECT * FROM Tables




INSERT INTO Views VALUES ('VSofer'), ('VEchipe'), ('VEchipe_Sponsori');
SELECT * FROM Views


INSERT INTO Tests(Name) 
VALUES ('insert_table_10'),
       ('insert_table_100'),
	   ('insert_table_1000'),
	   ('delete_table_10'),
	   ('delete_table_100'),
	   ('delete_table_1000'),
	   ('select_view')
GO
SELECT * FROM Tests



INSERT INTO TestViews(TestID, ViewID) 
VALUES (7, 1), (7, 2), (7, 3)
GO
SELECT * FROM TestViews


INSERT INTO TestTables VALUES
(1, 1, 10, 1),
(2, 1, 100, 1),
(3, 1, 1000, 1),
(1, 2, 10, 2),
(2, 2, 100, 2),
(3, 2, 1000, 2),
(1, 3, 10, 3),
(2, 3, 100, 3),
(3, 3, 1000, 3),

(4, 1, 10, 3),
(5, 1, 100, 3),
(6, 1, 1000, 3),
(4, 2, 10, 2),
(5, 2, 100, 2),
(6, 2, 1000, 2),
(4, 3, 10, 1),
(5, 3, 100, 1),
(6, 3, 1000, 1)
GO
SELECT * FROM TestTables


CREATE OR ALTER PROCEDURE InsertEchipe (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @nume VARCHAR(30)
    DECLARE @an_aparitie INT
	DECLARE @i int
	DECLARE @lastId int

	SET @nume = 'Nume_Echipa'
	SET @id = 1000
	SET @i = 1

	SET IDENTITY_INSERT ECHIPE ON;

	WHILE @i <= @rows
	BEGIN
		SET @id = 1000+@i
		SELECT TOP 1 @lastId = ECHIPE.id_echipe FROM dbo.ECHIPE ORDER BY ECHIPE.id_echipe DESC;
		IF @lastId > 1000
			SET @id = @lastId + 1
	
		SET @nume = CONCAT('Echipa', CAST(@i AS VARCHAR));
        SET @an_aparitie = 2000 + @i;
		INSERT INTO ECHIPE(id_echipe, nume, an_aparitie) VALUES (@id, @nume, @an_aparitie)

		SET @i = @i + 1
	END

	SET IDENTITY_INSERT ECHIPE OFF;

END
GO
EXEC InsertEchipe 10;
SELECT * FROM ECHIPE;


CREATE OR ALTER PROCEDURE DeleteEchipe (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @i int
	DECLARE @lastId int

	SET @id = 1000
	SET @i = @rows

	WHILE @i > 0
	BEGIN
		SET @id = 1000 + @i
		SELECT TOP 1 @lastId = ECHIPE.id_echipe FROM dbo.ECHIPE ORDER BY ECHIPE.id_echipe DESC;
		IF @lastId > @id
			SET @id = @lastId
		DELETE FROM ECHIPE WHERE ECHIPE.id_echipe = @id;
		SET @i = @i - 1
	END
END
GO
 EXEC DeleteEchipe 10;
SELECT * FROM ECHIPE;


CREATE OR ALTER PROCEDURE InsertSofer (@rows int)
AS
BEGIN
	DECLARE @id INT
    DECLARE @nume VARCHAR(30)
    DECLARE @prenume VARCHAR(30)
    DECLARE @varsta INT
    DECLARE @pozitie_podium INT
    DECLARE @fkEchipa INT
    DECLARE @i INT
    DECLARE @lastId INT


    SET @nume = 'Nume_Sofer'
    SET @prenume = 'Prenume_Sofer'
    SET @varsta = 20
    SET @pozitie_podium = 1
    SET @i = 1
    SET @id = 1000
	
	SET IDENTITY_INSERT SOFER ON;
	WHILE @i <= @rows
	BEGIN
		SET @id = 1000 + @i;
		SELECT TOP 1 @lastId = id_sofer FROM dbo.SOFER ORDER BY id_sofer DESC
        IF @lastId > 1000
            SET @id = @lastId + 1;
		SELECT TOP 1 @fkEchipa = id_echipe FROM dbo.ECHIPE ORDER BY NEWID();
		INSERT INTO SOFER (id_sofer, id_echipe, nume, prenume, varsta, pozitie_podium)
        VALUES (@id, @fkEchipa, CONCAT(@nume, CAST(@i AS VARCHAR)), CONCAT(@prenume, CAST(@i AS VARCHAR)), @varsta + @i, @pozitie_podium);

		SET @pozitie_podium = @pozitie_podium + 1
        SET @id = @id + 1
        IF @pozitie_podium > 3 
        BEGIN
            SET @pozitie_podium = 1;
        END

		SET @i = @i + 1
	END
	SET IDENTITY_INSERT SOFER OFF;
END
GO
EXEC InsertSofer 10;
SELECT * FROM SOFER


CREATE OR ALTER PROCEDURE DeleteSofer (@rows INT)
AS
BEGIN
    DECLARE @id INT
    DECLARE @i INT
	DECLARE @lastId INT

    SET @i = @rows
	SET @id = 1000

    WHILE @i > 0
    BEGIN
		SET @id = 1000 + @i
        SELECT TOP 1 @lastId = Sofer.id_sofer FROM dbo.SOFER ORDER BY id_sofer DESC

        IF @lastId > @id
			SET @id = @lastId
        DELETE FROM SOFER WHERE id_sofer = @id
        SET @i = @i - 1
    END
END
GO
EXEC DeleteSofer 10;
SELECT * FROM SOFER;

CREATE OR ALTER PROCEDURE InsertEchipe_Sponsori (@rows INT)
AS
BEGIN
    DECLARE @i INT
    SET @i = @rows

    EXEC InsertEchipe @rows;
    DECLARE @idEchipe INT, @idSponsori INT
    DECLARE cursorEchipe CURSOR SCROLL FOR
    SELECT id_echipe FROM ECHIPE;
    OPEN cursorEchipe;
    FETCH LAST FROM cursorEchipe INTO @idEchipe;

    WHILE @i > 0 AND @@FETCH_STATUS = 0
    BEGIN
        SET @idSponsori = (SELECT TOP 1 id_sponsori FROM SPONSORI ORDER BY NEWID());

        INSERT INTO ECHIPE_SPONSORI(id_echipe, id_sponsori, suma_contribuita) 
        VALUES (@idEchipe, @idSponsori, 2000000 + (@i * 50000));
        FETCH PRIOR FROM cursorEchipe INTO @idEchipe;

        SET @i = @i - 1;
    END
    CLOSE cursorEchipe;
    DEALLOCATE cursorEchipe;
END;
GO
EXEC InsertEchipe_Sponsori 10;
SELECT * FROM ECHIPE_SPONSORI;


CREATE OR ALTER PROCEDURE DeleteEchipe_Sponsori (@rows INT)
AS
BEGIN
    DECLARE @idEchipe INT, @idSponsori INT
    DECLARE @i INT

    SET @i = @rows

    WHILE @i > 0
    BEGIN
        SELECT TOP 1 @idEchipe = id_echipe FROM dbo.ECHIPE ORDER BY id_echipe DESC;

        IF @idEchipe > 1000
        BEGIN
            DELETE FROM ECHIPE_SPONSORI WHERE id_echipe = @idEchipe;
			EXEC DeleteEchipe 1;
        END
        SET @i = @i - 1;
    END
END;
GO
EXEC DeleteEchipe_Sponsori 10;
SELECT * FROM ECHIPE_SPONSORI;


CREATE OR ALTER PROCEDURE insert_table_10 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Echipe'
	exec InsertEchipe 10
	IF @Tabela='Sofer'
	exec InsertSofer 10
	IF @Tabela='Echipe_Sponsori'
	exec InsertEchipe_Sponsori 10
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE insert_table_100 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Echipe'
	exec InsertEchipe 100
	IF @Tabela='Sofer'
	exec InsertSofer 100
	IF @Tabela='Echipe_Sponsori'
	exec InsertEchipe_Sponsori 100
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE insert_table_1000 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Echipe'
	exec InsertEchipe 1000
	IF @Tabela='Sofer'
	exec InsertSofer 1000
	IF @Tabela='Echipe_Sponsori'
	exec InsertEchipe_Sponsori 1000
	else PRINT 'Nume invalid'
END
GO


CREATE OR ALTER PROCEDURE delete_table_10 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Echipe'
	exec DeleteEchipe 10
	IF @Tabela='Sofer'
	exec DeleteSofer 10
	IF @Tabela='Echipe_Sponsori'
	exec DeleteEchipe_Sponsori 10
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE delete_table_100 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Echipe'
	exec DeleteEchipe 100
	IF @Tabela='Sofer'
	exec DeleteSofer 100
IF @Tabela='Echipe_Sponsori'
	exec DeleteEchipe_Sponsori 100
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE delete_table_1000 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Echipe'
	exec DeleteEchipe 1000
	IF @Tabela='Sofer'
	exec DeleteSofer 1000
IF @Tabela='Echipe_Sponsori'
	exec DeleteEchipe_Sponsori 1000
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE Evaluare (@View VARCHAR(20))
AS
BEGIN
	IF @View='VSofer'
	select * from VSofer
	IF @View='VEchipe'
	select * from VEchipe
	IF @View='VEchipe_Sponsori'
	select * from VEchipe_Sponsori
	else PRINT 'Nume invalid'
END
GO


CREATE OR ALTER PROCEDURE main (@Tabela VARCHAR(20), @rows int)
AS
BEGIN
	DECLARE @t1 datetime, @t2 datetime, @t3 datetime
	DECLARE @desc VARCHAR(2000)

	DECLARE @testInserare VARCHAR(20)
	DECLARE @testSterge VARCHAR(20)
	DECLARE @nrRows int
	DECLARE @idTest int

	DECLARE @id_table int
	SELECT @id_table = Tables.TableID FROM Tables WHERE Tables.Name = @Tabela

	DECLARE @id_view int
	SELECT @id_view = Views.ViewID FROM Views WHERE Views.Name = 'V'+@Tabela

	SET @testInserare = 'insert_table_' + CONVERT(VARCHAR(10),@rows)
	SET @testSterge = 'delete_table_' + CONVERT(VARCHAR(10),@rows)

		SET @t1 = GETDATE()
		exec @testInserare @Tabela
		exec @testSterge @Tabela
		SET @t2 = GETDATE()
		exec Evaluare @Tabela
		SET @t3 = GETDATE()
		SET @desc = @testInserare+' - '+@testSterge+' - '+@Tabela
		INSERT INTO TestRuns VALUES (@desc, @t1, @t2)
		SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC
		INSERT INTO TestRunTables VALUES (@idTest,@id_table,@t1,@t2)
		INSERT INTO TestRunViews VALUES (@idTest,@id_view,@t2,@t3)
END

EXEC main @Tabela = 'ECHIPE', @rows = 10;
EXEC main @Tabela = 'SOFER', @rows = 10;
EXEC main @Tabela = 'ECHIPE_SPONSORI', @rows = 10;

EXEC main @Tabela = 'ECHIPE', @rows = 100;
EXEC main @Tabela = 'SOFER', @rows = 100;
EXEC main @Tabela = 'ECHIPE_SPONSORI', @rows = 100;

EXEC main @Tabela = 'ECHIPE', @rows = 1000;
EXEC main @Tabela = 'SOFER', @rows = 1000;
EXEC main @Tabela = 'ECHIPE_SPONSORI', @rows = 1000;




select * from TestRuns
select * from TestRunTables
select * from TestRunViews

SELECT * FROM VEchipe;
SELECT * FROM VSofer;
SELECT * FROM VEchipe_Sponsori;


DROP TABLE TestRunViews
DROP TABLE TestRunTables
DROP TABLE TestRuns 
DROP TABLE TestTables
DROP TABLE TestViews
DROP TABLE Tests
DROP TABLE Tables
DROP TABLE Views




use formula1_2
GO

CREATE VIEW RandomView
AS
SELECT RAND() AS Value
GO

CREATE FUNCTION RandomInt(@lower INT, @upper INT)
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

SELECT *
FROM CometVisibleFromPlanet
    CREATE OR
    ALTER PROCEDURE PopulateTable @tableName VARCHAR(50),
                                  @noRows INT
    AS
    BEGIN
        DECLARE @currentRow INT, @command VARCHAR(256)
        SET @currentRow = 0
        WHILE @currentRow < @noRows
            BEGIN
                SELECT @command = 'Insert' + @tableName + ' ' + CONVERT(VARCHAR(10), @currentRow)
                EXEC (@command)
                SET @currentRow = @currentRow + 1
            END
    END
GO

CREATE PROCEDURE ClearTable @tableName VARCHAR(50)
AS
    EXEC ('DELETE FROM ' + @tableName)
GO

CREATE VIEW LargePlanets AS
SELECT P.Name, P.Age, P.Size, P.OrbitalTime
FROM BackupPlanet P
WHERE P.Size > 50

-- Each satellite with its planet
CREATE VIEW PlanetSatellite AS
SELECT S.Name as Satellite, P.Name as Planet
FROM BackupPlanet P
         INNER JOIN BackupSatellite S on P.ID = S.PlanetID


-- Show every star that is in a constellation together with their constellation, alphabetical by stars then constellation
CREATE VIEW ViewStarConstellation AS
SELECT C.Name as Constellation, S.Name as Star
FROM Star S
         RIGHT JOIN StarConstellation SC on S.ID = SC.StarID
         RIGHT JOIN Constellation C on C.ID = SC.ConstellationID
GROUP BY S.Name, C.Name

CREATE OR
ALTER PROCEDURE CreateTest @name VARCHAR(50)
AS
BEGIN
    IF EXISTS(SELECT * FROM Tests T WHERE Name = @name)
        BEGIN
            PRINT 'Exista deja'
            RETURN
        END
    INSERT INTO Tests(Name) VALUES (@name)
END
    EXEC CreateTest 'Creare Test'
SELECT *
FROM Tests


CREATE OR ALTER PROCEDURE AddTableToTestTable @tableName VARCHAR(50)
AS
    BEGIN
        IF NOT EXISTS(SELECT * FROM sys.tables WHERE name = @tableName)
            BEGIN
                PRINT 'Table' + @tableName + ' does not exist'
                RETURN
            END
        IF EXISTS(SELECT * FROM Tables T WHERE T.Name = @tableName)
            BEGIN
                PRINT 'Table ' + @tableName + ' already added to test'
                RETURN
            END
        INSERT INTO Tables(Name) VALUES (@tableName)
    END

EXEC AddTableToTestTable 'Planet'
EXEC AddTableToTestTable 'Comet'
EXEC AddTableToTestTable 'CometVisibleFromPlanet'



CREATE OR ALTER PROCEDURE RelateTestsAndTables @tableName VARCHAR(50), @testName VARCHAR(50), @noRows INT, @position INT
AS
    BEGIN
        IF @position < 0
        BEGIN
            PRINT 'Position must be >0'
            RETURN
        END
        IF @noRows < 0
        BEGIN
            PRINT 'Number of rows must be >0'
            RETURN
        END

        DECLARE @testID INT, @tableID INT
        SET @testID = (SELECT T.TestID FROM Tests T WHERE T.Name = @testName)
        SET @tableID = (SELECT T.TableID FROM Tables T WHERE T.Name = @tableName)
        INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES
        (@testID, @tableID, @noRows, @position)
    END

EXEC RelateTestsAndTables 'CometVisibleFromPlanet', 'FirstTestSoWeCanNameItHelloWorld', 2000, 0

CREATE OR ALTER PROCEDURE AddViewToTestTable @viewName VARCHAR(50)
AS
BEGIN
    IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = @viewName)
    BEGIN
        PRINT 'View does not exist'
        RETURN
    end
    IF EXISTS(SELECT * FROM Views WHERE Name = @viewName)
    BEGIN
        PRINT 'View already added'
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

SELECT * FROM CometVisibleFromPlanet
SELECT * FROM Comet
SELECT * FROM Planet
DELETE TestRunTables
DELETE TestTables

EXEC RelateTestsAndTables 'Planet', 'FirstTestSoWeCanNameItHelloWorld', 2530, 1
EXEC RelateTestsAndTables 'Comet', 'FirstTestSoWeCanNameItHelloWorld', 3000, 2
EXEC RelateTestsAndTables 'CometVisibleFromPlanet', 'FirstTestSoWeCanNameItHelloWorld', 2000, 3

EXEC CreateTest 'FastTestShouldBeShort'
EXEC RelateTestsAndTables 'Planet', 'FastTestShouldBeShort', 100, 2
EXEC RelateTestsAndTables 'Comet', 'FastTestShouldBeShort', 150, 1
EXEC RelateTestsAndTables 'CometVisibleFromPlanet', 'FastTestShouldBeShort', 33, 3

EXEC AddViewToTestTable 'LargePlanets'
EXEC AddViewToTestTable 'PlanetSatellite'
EXEC AddViewToTestTable 'ViewStarConstellation'

EXEC RelateTestsAndViews 'LargePlanets', 'FastTestShouldBeShort'
EXEC RelateTestsAndViews 'PlanetSatellite', 'FastTestShouldBeShort'
EXEC RelateTestsAndViews 'ViewStarConstellation', 'FastTestShouldBeShort'

SELECT * FROM Views
SELECT * FROM TestViews

SELECT * FROM TestTables
SELECT * FROM TestRunTables
SELECT * FROM TestRuns
SELECT * FROM TestRunViews

PRINT SYSDATETIME()
EXEC RunTest 'FastTestShouldBeShort'
EXEC RunTest 'FirstTestSoWeCanNameItHelloWorld'