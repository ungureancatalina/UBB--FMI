use sgbd
go

CREATE OR ALTER PROCEDURE NonRepeatableReads2_Oras AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
    BEGIN TRAN;
    BEGIN TRY
        -- T2 face prima citire: va vedea valorile initiale din ORAS
        SELECT * FROM ORAS;

        -- Pauza pentru a permite lui T1 sa faca UPDATE
        WAITFOR DELAY '00:00:15';

        -- T2 face a doua citire: valorile pot fi diferite daca T1 a facut UPDATE
        SELECT * FROM ORAS;

        -- Finalizare tranzactie
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];

        -- Log final
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T2_ORAS', 'Non-repeatable read on ORAS (READ COMMITTED)', 'Completed');
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;
GO


CREATE OR ALTER PROCEDURE NonRepeatableReads2_Oras_Correct AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
    BEGIN TRAN;
    BEGIN TRY
        -- T2 face prima citire si blocheaza modificarile asupra inregistrarilor citite
        SELECT * FROM ORAS;

        -- Pauza pentru a da timp lui T1 sa incerce UPDATE (care va fi blocat pana T2 termina)
        WAITFOR DELAY '00:00:15';

        -- T2 face a doua citire: va vedea aceleasi date ca la prima citire
        SELECT * FROM ORAS;

        -- Finalizam tranzactia
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];

        -- Log final
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T2_ORAS_CORRECT', 'Repeatable read on ORAS (REPEATABLE READ)', 'Completed');
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;
GO


EXEC NonRepeatableReads2_Oras
EXEC NonRepeatableReads2_Oras_Correct

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;