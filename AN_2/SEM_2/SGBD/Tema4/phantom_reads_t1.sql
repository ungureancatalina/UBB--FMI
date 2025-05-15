use sgbd 
go

CREATE OR ALTER PROCEDURE PhantomReads1_Oras AS
BEGIN
    BEGIN TRAN;
    BEGIN TRY
        -- Log: T1 incepe si asteapta pentru a permite T2 sa faca prima citire
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T1_ORAS', 'Insert new ORAS', 'Started');

        -- Pauza pentru ca T2 sa ruleze in paralel
        WAITFOR DELAY '00:00:10';

        -- Inseram un nou oras care va cauza un phantom read
        INSERT INTO ORAS (id_tara, nume, sofer_provenienta_nume, nr_locuitori)
        VALUES (1, 'Phantomville', 'Ghost Driver', 123123);

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

EXEC PhantomReads1_Oras

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;