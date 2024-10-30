% Sa se scrie un predicat care intoarce intersectia a doua multimi
% Model matematic:
%
% intersectie(l1...ln, L)={[], daca prima lista este vida}
%                         {l1 U intersectie(l2...ln, L), daca l1 apartine multimii L}
%                         {intersectie(l2...ln, L), daca l1 nu apartine multimii L}

% Model matematic:
%
% element_in(X, L)={true, daca X e primul element in L}
%                  {fals, daca nu este deloc X in L]}
%                  {element_in(X,L), daca nu a fost gasit inca X}


% intersectie(A: lista, L:lista, R:lista)
% A : prima multime
% L : a doua multime
% R : intersectia multimilor
% model de flux (i,i,i)

% element_in(X: intreg, L: lista)
% X: nr pe care il caut in lista
% L : lista in care se cauta nr
% model de flux (i,i)


% Cazul de baza: intersectia unei liste vide cu orice lista este lista vida.
intersectie([], _, []).

% Daca H este membru in L, il adaugam la rezultat.
intersectie([H|T], L, [H|R]) :-
    element_in(H, L),
    !,
    intersectie(T, L, R).

% Daca H nu este membru in L, trecem mai departe fara sa-l adaugam.
intersectie([_|T], L, R) :-
    intersectie(T, L, R).

% Predicate auxiliar: verifica daca un element apartine unei liste.
element_in(X, [X|_]).

element_in(X, [_|T]) :-
    element_in(X, T).


%  ['C:/Users/ungur/OneDrive/Desktop/facultate_materiale/AN_2/SEM_1/programare logica si functionala/lab1/9a.pl'].
% intersectie([1, 2, 3, 4], [2, 4, 6], Result).
% intersectie([1, 2, 3, 4], [7, 9, 6], Result).
% intersectie([1, 2, 3, 4], [], Result).
