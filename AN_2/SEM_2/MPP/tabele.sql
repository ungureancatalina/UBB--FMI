use lab1mpp

CREATE TABLE utilizator (   --organizatorii
    id_utilizator INT PRIMARY KEY IDENTITY(1,1),     
    nume VARCHAR(50),                   
    parola VARCHAR(255)                  
);

CREATE TABLE echipa (
	id_echipa INT PRIMARY KEY IDENTITY(1,1),     
	nume VARCHAR(50)
);

CREATE TABLE participant (
	 id_participant INT PRIMARY KEY IDENTITY(1,1),     
	 nume VARCHAR(50),      
	 CNP VARCHAR(50),
	 capacitate_motor INT,
	 id_echipa INT FOREIGN KEY REFERENCES echipa(id_echipa)
);

CREATE TABLE cursa (
	id_cursa INT PRIMARY KEY IDENTITY(1,1),     
	nume VARCHAR(50),     
	capacitate_minima INT,
	capacitate_maxima INT
);


CREATE TABLE participant_cursa (
	id_participant INT FOREIGN KEY REFERENCES participant(id_participant),
	id_cursa INT FOREIGN KEY REFERENCES cursa(id_cursa),
	CONSTRAINT id_part_cursa PRIMARY KEY (id_participant, id_cursa)
);

	     
