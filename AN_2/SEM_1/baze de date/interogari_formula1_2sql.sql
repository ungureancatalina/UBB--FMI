use formula1_2
go

--1. Echipele a caror sponsori au un buget mai mare de 50000000
SELECT E.nume AS nume_echipa,E.an_aparitie,S.nume AS nume_sponsor,S.anul AS an_sponsorizare ,S.buget
FROM ECHIPE E
INNER JOIN ECHIPE_SPONSORI ES ON E.id_echipe = ES.id_echipe
INNER JOIN SPONSORI S ON S.id_sponsori = ES.id_sponsori
WHERE S.buget > 50000000;


--2. Soferii care sunt la o echipa care incepe cu M
SELECT S.nume AS nume_sofer,S.prenume, S.pozitie_podium, I.nume_echipa AS nume_echipa_vechi, I.puncte_aduse, E.nume AS nume_echipa
FROM SOFER S
INNER JOIN ISTORIC I ON S.id_sofer = I.id_sofer
INNER JOIN ECHIPE E ON S.id_echipe = E.id_echipe
WHERE I.nume_echipa like 'M%' AND E.nume like 'M%';


--3. Continentele cu mai mult de o cursa
SELECT T.continent, COUNT(c.id_cursa) AS nr_curse
FROM TARA T
INNER JOIN CURSA C ON T.id_cursa = C.id_cursa
GROUP BY T.continent
HAVING COUNT(c.id_cursa)>1;


--4. Tarile cu nr de participanti recurent mai mare de 60000 si orasele incep cu L
SELECT T.nume AS nume_tara, O.nume AS nume_oras, SUM(P.numar_recurent) AS nr_participanti_recurenti
FROM TARA T
INNER JOIN PARTICIPANTI P ON T.id_tara = P.id_tara
INNER JOIN ORAS O ON T.id_tara = O.id_tara
GROUP BY T.nume, O.nume
HAVING SUM(P.numar_recurent) > 60000 AND O.nume like 'L%';


--5. Echipele cu mai mult de 2 sponsori
SELECT E.nume AS nume_echipa, COUNT(DISTINCT S.id_sponsori) AS nr_sponsori
FROM ECHIPE E
INNER JOIN ECHIPE_SPONSORI ES ON E.id_echipe = ES.id_echipe
INNER JOIN SPONSORI S ON ES.id_sponsori = S.id_sponsori
GROUP BY E.nume
HAVING COUNT(S.id_sponsori) > 1;


--6. Orasele cu nr de locuitori mai mare de 1000000 si cu participanti peste 70000
SELECT T.nume AS nume_tara, O.nume AS nume_oras, O.nr_locuitori, P.numar
FROM TARA T
INNER JOIN ORAS O ON T.id_tara = O.id_tara
INNER JOIN PARTICIPANTI P ON T.id_tara = P.id_tara
WHERE O.nr_locuitori > 1000000 AND P.numar> 70000;


--7. Mecanicii cu experienta peste 17 ani
SELECT DISTINCT E.nume AS nume_echipa, M.nume AS nume_mecanic, M.experienta
FROM ECHIPE E
INNER JOIN MECANICI M ON E.id_echipe = M.id_echipe
WHERE M.experienta > 19;


--8. Cursele din continente care incep cu A sau sunt in Europa
SELECT T.nume AS nume_tara,T.continent AS nume_continent, C.numar AS nr_curse, P.numar AS nr_participanti
FROM TARA T
INNER JOIN CURSA C ON T.id_cursa = C.id_cursa
INNER JOIN PARTICIPANTI P ON T.id_tara = P.id_tara
WHERE T.continent = 'Europe' OR T.continent like 'A%';


--9. Numele echipelor si nr cursei la care participa echipa
SELECT DISTINCT E.nume AS nume_echipa,C.numar, SUM(DISTINCT P.numar) AS nr_puncte
FROM ECHIPE E
INNER JOIN PUNCTE P ON E.id_echipe = P.id_echipe
INNER JOIN CURSA C ON E.id_echipe = C.id_echipe
GROUP BY E.nume, C.numar;


--10. Sponsorii cu suma contribuita peste 50000000
SELECT S.nume AS nume_sponsor, SUM(ES.suma_contribuita) AS nr_contributii
FROM SPONSORI S
INNER JOIN ECHIPE_SPONSORI ES ON S.id_sponsori = ES.id_sponsori
GROUP BY S.nume
HAVING SUM(ES.suma_contribuita) >  50000000;