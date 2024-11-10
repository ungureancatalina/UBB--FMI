use formula1_2
go

CREATE PROCEDURE modifica_coloana AS
ALTER TABLE MECANICI
ALTER COLUMN nume VARCHAR(50)
PRINT 'Coloana nume din tabelul MECANICI a fost modificata'
GO

CREATE PROCEDURE undo_modifica_coloana AS
ALTER TABLE MECANICI
ALTER COLUMN nume VARCHAR(30)
PRINT 'Coloana nume din tabelul MECANICI a fost modificata, adusa la starea initiala'
GO

CREATE PROCEDURE adauga_constrangere_valoare_implicita AS
ALTER TABLE PUNCTE
ADD CONSTRAINT numar_minim DEFAULT 0 FOR numar
PRINT 'Pentru coloana numar din tabelul PUNCTE s-a adaugat o constrangere'
GO

CREATE PROCEDURE undo_adauga_constrangere_valoare_implicita AS
ALTER TABLE PUNCTE
DROP CONSTRAINT numar_minim 
PRINT 'Pentru coloana numar din tabelul PUNCTE s-a eliminat constrangerea'
GO

CREATE PROCEDURE creare_tabel AS
DROP TABLE IF EXISTS TIP_CURSA
CREATE TABLE TIP_CURSA
(
id_cursa_tip INT NOT NULL PRIMARY KEY,
descriere VARCHAR(30)
)
PRINT 'S-a creat un nou tabel [TIP_CURSA]'
GO

CREATE PROCEDURE undo_creare_tabel AS
DROP TABLE TIP_CURSA
PRINT 'S-a sters tabelul [TIP_CURSA]'
GO

CREATE PROCEDURE adauga_camp AS
ALTER TABLE CURSA
ADD vreme VARCHAR(30)
PRINT 'S-a adaugat un camp nou in tabela [CURSA]'
GO

CREATE PROCEDURE undo_adauga_camp AS
ALTER TABLE CURSA
DROP COLUMN vreme 
PRINT 'S-a eliminat campul [vreme] din tabela [CURSA]'
GO

CREATE PROCEDURE creare_constrangere_cheie_straina AS
ALTER TABLE TIP_CURSA
ADD CONSTRAINT id_tip FOREIGN KEY(id_cursa_tip) REFERENCES CURSA(id_cursa)
PRINT 'S-a creat o constrangere de cheie straina'
GO

CREATE PROCEDURE undo_creare_constrangere_cheie_straina AS
ALTER TABLE TIP_CURSA
DROP CONSTRAINT id_tip
PRINT 'S-a sters constrangerea de cheie straina'
GO

DROP PROCEDURE modifica_coloana
DROP PROCEDURE adauga_constrangere_valoare_implicita
DROP PROCEDURE creare_tabel
DROP PROCEDURE undo_creare_tabel
DROP PROCEDURE adauga_camp
DROP PROCEDURE creare_constrangere_cheie_straina
DROP PROCEDURE undo_creare_constrangere_cheie_straina
DROP PROCEDURE undo_modifica_coloana
DROP PROCEDURE undo_adauga_constrangere_valoare_implicita
DROP PROCEDURE undo_adauga_camp


EXEC modifica_coloana
EXEC undo_modifica_coloana
EXEC adauga_constrangere_valoare_implicita
EXEC undo_adauga_constrangere_valoare_implicita
EXEC creare_tabel
EXEC adauga_camp
EXEC undo_adauga_camp
EXEC creare_constrangere_cheie_straina
EXEC undo_creare_constrangere_cheie_straina
EXEC undo_creare_tabel


DROP TABLE IF EXISTS VERSIUNE
CREATE TABLE VERSIUNE 
(
	nr_versiune INT PRIMARY KEY
);
INSERT INTO VERSIUNE VALUES (0)


GO
CREATE OR ALTER PROCEDURE main @versiune INT AS
		
		IF @versiune < 0 OR @versiune > 5
		BEGIN
			PRINT 'Versiunea este invalida!'
			RETURN
		END

		DECLARE @versiune_curenta AS INT
		SET @versiune_curenta = (SELECT nr_versiune FROM VERSIUNE)

		IF @versiune = @versiune_curenta
		BEGIN
			PRINT 'Versiunea actuala este deja cea curenta!'
			RETURN
		END

		DELETE FROM VERSIUNE
		INSERT INTO VERSIUNE(nr_versiune) VALUES (@versiune)

		WHILE(@versiune_curenta < @versiune)
			BEGIN
				SET @versiune_curenta = @versiune_curenta + 1

				IF @versiune_curenta = 1
					EXEC modifica_coloana
				ELSE IF @versiune_curenta = 2
					EXEC adauga_constrangere_valoare_implicita
				ELSE IF @versiune_curenta = 3
					EXEC creare_tabel
				ELSE IF @versiune_curenta = 4
					EXEC adauga_camp
				ELSE IF @versiune_curenta = 5
					EXEC creare_constrangere_cheie_straina
			END

		WHILE(@versiune_curenta > @versiune)
			BEGIN
				IF @versiune_curenta = 5
					EXEC undo_creare_constrangere_cheie_straina
				ELSE IF @versiune_curenta = 4
					EXEC undo_adauga_camp
				ELSE IF @versiune_curenta = 3
					EXEC undo_creare_tabel
				ELSE IF @versiune_curenta = 2
					EXEC undo_adauga_constrangere_valoare_implicita
				ELSE IF @versiune_curenta = 1
					EXEC undo_modifica_coloana
     
				SET @versiune_curenta = @versiune_curenta - 1
			END
GO

EXEC main 0
EXEC main 1
EXEC main 2
EXEC main 3
EXEC main 4
EXEC main 5