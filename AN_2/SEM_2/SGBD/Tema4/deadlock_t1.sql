use sgbd
go

CREATE OR ALTER PROCEDURE Deadlock1_OrasTara AS
BEGIN
    BEGIN TRAN;

    -- T1 blocheaza ORAS (ex: id_oras = 1)
    UPDATE ORAS SET nume = 'Monte Carlo - T1' WHERE id_oras = 1;

    -- Pauza pentru ca T2 sa porneasca intre timp
    WAITFOR DELAY '00:00:10';

    -- T1 incearca apoi sa blocheze TARA (ex: id_tara = 1)
    UPDATE TARA SET nume = 'Monaco - T1' WHERE id_tara = 1;

    COMMIT TRAN;
    SELECT 'Transaction committed' AS [Message];

    -- Log pentru T1
    INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
    VALUES ('T1_ORAS_TARA', 'Update ORAS then TARA', 'Committed');
END;
GO

EXEC Deadlock1_OrasTara

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;