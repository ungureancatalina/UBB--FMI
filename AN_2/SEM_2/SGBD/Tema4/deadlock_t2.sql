use sgbd
go

CREATE OR ALTER PROCEDURE Deadlock2_OrasTara AS
BEGIN
    BEGIN TRAN;

    -- T2 blocheaza TARA (ex: id_tara = 1)
    UPDATE TARA SET nume = 'Monaco - T2' WHERE id_tara = 1;

    -- Pauza pentru ca T1 sa ruleze si sa blocheze ORAS
    WAITFOR DELAY '00:00:10';

    -- T2 incearca sa blocheze ORAS (ex: id_oras = 1)
    UPDATE ORAS SET nume = 'Monte Carlo - T2' WHERE id_oras = 1;

    COMMIT TRAN;
    SELECT 'Transaction committed' AS [Message];

    -- Log pentru T2
    INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
    VALUES ('T2_ORAS_TARA', 'Update TARA then ORAS', 'Committed');
END;
GO

CREATE OR ALTER PROCEDURE Deadlock2_OrasTara_Correct AS
BEGIN
    BEGIN TRAN;

    -- T2 blocheaza ORAS (ex: id_oras = 1)
    UPDATE ORAS SET nume = 'Monte Carlo - T2 fixed' WHERE id_oras = 1;

    -- Pauza pentru sincronizare
    WAITFOR DELAY '00:00:10';

    -- T2 actualizeaza TARA (ex: id_tara = 1)
    UPDATE TARA SET nume = 'Monaco - T2 fixed' WHERE id_tara = 1;

    COMMIT TRAN;
    SELECT 'Transaction committed' AS [Message];

    -- Log pentru varianta corecta
    INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
    VALUES ('T2_ORAS_TARA_CORRECT', 'Correct update order', 'Committed');
END;
GO

EXEC Deadlock2_OrasTara
EXEC Deadlock2_OrasTara_Correct

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;
