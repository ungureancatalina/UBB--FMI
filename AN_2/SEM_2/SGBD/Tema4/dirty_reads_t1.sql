use sgbd
go

CREATE OR ALTER PROCEDURE DirtyReads1_Oras AS
BEGIN
	BEGIN TRAN;
	BEGIN TRY
		-- Log pentru inceputul tranzactiei ORAS_T1
		INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
		VALUES ('ORAS_T1', 'Update oras cu ID 1', 'Started');

		-- UPDATE pe oras deja existent 
		UPDATE ORAS SET nr_locuitori = 123456 WHERE id_oras = 1;

		-- Pauza pentru simulare dirty read
		WAITFOR DELAY '00:00:10';

		-- Log pentru rollback
		INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
		VALUES ('ORAS_T1', 'Rollback update oras ID 1', 'Rolled Back');

		-- Anulam modificarile
		ROLLBACK TRAN;
		SELECT 'Transaction successfully rolled back!' AS [Message];
	END TRY
	BEGIN CATCH
		IF @@TRANCOUNT > 0
		BEGIN
			ROLLBACK TRANSACTION;
		END
		SELECT 'Transaction unsuccessfully rolled back!' AS [Message];
	END CATCH;
END;
GO

EXEC DirtyReads1_Oras;


SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;
