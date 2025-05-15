use sgbd
go

CREATE OR ALTER PROCEDURE NonRepeatableReads1_Oras AS
BEGIN
    BEGIN TRAN;
    BEGIN TRY
        -- Log: T1 incepe si asteapta ca T2 sa faca prima citire
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T1_ORAS', 'Start and wait before update', 'Started');

        -- Asteptam ca T2 sa faca prima citire
        WAITFOR DELAY '00:00:10';

        -- T1 face UPDATE asupra unei valori din ORAS (ex: oras cu id = 1)
        UPDATE ORAS SET nr_locuitori = 444444 WHERE id_oras = 1;

        -- Confirmam tranzactia
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;
GO

EXEC NonRepeatableReads1_Oras

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;