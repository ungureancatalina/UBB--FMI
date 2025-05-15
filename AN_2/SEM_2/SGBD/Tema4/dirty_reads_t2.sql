use sgbd
go

CREATE OR ALTER PROCEDURE DirtyReads2_Oras AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
	BEGIN TRAN;
	BEGIN TRY
		-- T2: citeste toate inregistrarile din ORAS, inclusiv modificarile neconfirmate facute de T1
		SELECT * FROM ORAS;

		-- Pauza pentru a permite rularea simultana cu T1
		WAITFOR DELAY '00:00:15';

		-- T2 citeste din nou: daca T1 nu a dat rollback inca, modificarile sunt inca vizibile
		SELECT * FROM ORAS;

		-- Finalizare tranzactie T2
		COMMIT TRAN;
		SELECT 'Transaction committed!' AS [Message];

		-- Log inregistrare finala pentru T2
		INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
		VALUES ('T2_ORAS', 'SELECT on ORAS (READ UNCOMMITTED)', 'Completed');
	END TRY
	BEGIN CATCH
		-- In caz de eroare, anulam tranzactia
		ROLLBACK TRAN;
		SELECT 'Transaction rolled back!' AS [Message];
	END CATCH;
END;
GO

CREATE OR ALTER PROCEDURE DirtyReads2_Oras_Correct AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
	BEGIN TRAN;
	BEGIN TRY
		-- T2: citeste doar datele care au fost deja confirmate (COMMIT)
		SELECT * FROM ORAS;

		-- Pauza pentru a permite rularea paralela cu T1
		WAITFOR DELAY '00:00:15';

		-- Citire repetata: daca T1 a facut rollback intre timp, modificarile nu vor fi vizibile
		SELECT * FROM ORAS;

		-- Finalizare tranzactie T2
		COMMIT TRAN;
		SELECT 'Transaction committed!' AS [Message];

		-- Log pentru comportamentul corect (nu vede datele neconfirmate)
		INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
		VALUES ('T2_ORAS_CORRECT', 'SELECT on ORAS (READ COMMITTED)', 'Completed');
	END TRY
	BEGIN CATCH
		-- In caz de eroare, se face rollback
		ROLLBACK TRAN;
		SELECT 'Transaction rolled back!' AS [Message];
	END CATCH;
END;
GO


EXEC DirtyReads2_Oras;
EXEC DirtyReads2_Oras_Correct;

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;
