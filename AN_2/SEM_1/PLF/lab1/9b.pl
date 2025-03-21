% Sa se construiasca lista (m, ..., n), adica multimea numerelor intregi din intervalul m, n
% Model matematic:
%
% generare(M, N, lista) = { [], daca M > N }
%                 { [N], daca M = N }
%                 { M ∪ generare(M+1, N), daca M < N }


% generare(M: intreg, N: intreg, R: lista)
% M : nr de unde sa inceapa lista
% N : nr unde sa se termine lista
% R : multimea nr din intervalul M,N
% model de flux (i,i,i)

% Cazul de baza: cand M este egal cu N, lista rezultata va fi [N].
generare(N, N, [N]).

% Cazul cand M > N: nu exista numere intre M si N, deci lista rezultata este goala.
generare(M, N, []) :- 
    M > N.

% Cazul recursiv: cand M < N, adaugam M la lista si continuam cu restul intervalului [M+1, N].
generare(M, N, [M|Rez]) :- 
    M < N,             
    M1 is M + 1,       
    generare(M1, N, Rez). 


% ['C:/Users/ungur/OneDrive/Desktop/facultate_materiale/AN_2/SEM_1/programare logica si functionala/lab1/9b.pl'].
% generare(1, 5, Lista).
% generare(5, 5, Lista).
% generare(7, 5, Lista).
