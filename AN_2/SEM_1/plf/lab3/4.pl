% Se dau doua numere naturale n si m. Se cere sa se afiseze in toate modurile posibile toate numerele de la 1 la n, astfel incat intre orice doua numere afisate pe pozitii consecutive, diferenta in modul sa fie >=m.


% MODEL MATEMATIC
% element_posibil(N) = {N}, dacă N = 1
%                    = {N} U element_posibil(N - 1), dacă N > 1

% element_posibil(N: intreg, Rez: intreg)
% Model de flux: (i, o)
% N : numărul maxim din interval
% Rez : număr generat în intervalul [1..N] în ordine descrescătoare

% caz de baza: daca N este egal cu 1 atunci  se returneaza tot N
element_posibil(N, N).

% daca N este mai mare decat 1 atunci se scade cate o unitate din N și se continuă recursiv cu N-1 până ajungem la 1
element_posibil(N, Rez) :-
    N > 1,
    N1 is N - 1,
    element_posibil(N1, Rez).


% MODEL MATEMATIC
% solutii_aux(N, M, C) = C, dacă lungimea listei C = N (secvența completă)
%                      = solutii_aux(N, M, {Elem} ∪ C), dacă lungimea listei < N și abs(Elem - H) >= M, unde H este primul element din C și Elem este un candidat din intervalul [1..N] cu Elem < H

% solutii_aux(N: intreg, M: intreg, C: lista, Rez: lista)
% Model de flux: (i, i, i, o)
% N : lungimea dorită a secvenței
% M : diferența minimă dorita între două numere consecutive
% C: lista curentă 
% Rez : lista finală, când secvența are exact N elemente

% Cazul de oprire daca secvența a ajuns la lungimea dorită si lista curentă C conține exact N elemente
solutii_aux(_,M,[X,Y|Rez],[X,Y|Rez]):-
    T is X-Y,
    abs(T)>=M.

% Cazul recursiv dacă lista curentă C nu are încă N elemente, atunci alegem un nou element care respectă diferența minimă M față de elementul curent H
solutii_aux(N,M,[H|C],Rez):-
    element_posibil(N,Elem),
    Elem<H,
    T is Elem - H,
    abs(T)>=M,
    solutii_aux(N,M,[Elem,H|C],Rez).



% MODEL MATEMATIC
% solutii(N, M) = solutii_aux(N, M, {Elem})

% solutii(N: intreg, M: intreg, Rez: lista)
% Model de flux: (i, i, o)
% N : numărul maxim din interval [1..N]
% M : diferența minimă dintre două numere consecutive din secvență
% Rez : lista rezultat, conținând o secvență validă de N elemente

% returneaza solutiile problemei, unde Elem va fi pe parcurs cate un intreg din intervalul [1,..,N]
solutii(N, M, Rez) :-
    element_posibil(N, Elem),
    solutii_aux(N, M, [Elem], Rez).


