% 12A

% inlocuire(E1: intreg, E2: intreg, L: lista, Rez: lista)
% Model de flux: (i,i,i,i) 
% E1 : numarul care se inlocuieste
% E2 : numarul cu care se inlocuieste
% L  : lista initiala, inainte de inlocuire
% Rez: lista dupa inlocuire

% MODEL MATEMATIC
% inlocuire(l1...ln, E1, E2) = [], daca lista este vida
%                            = [E2] U inlocuire(l2...ln, E1, E2), daca l1 = E1
%                            = [l1] U inlocuire(l2...ln, E1, E2), daca l1 != E1

% Cazul de baza: daca lista este goala, returneaza o lista goala.
inlocuire(_, _, [], []).

% Daca elementul curent din lista este egal cu E1, il inlocuieste cu E2.
inlocuire(E1, E2, [E1 | T], [E2 | T1]) :- 
                inlocuire(E1, E2, T, T1).

% Daca elementul curent din lista nu este egal cu E1, il pastreaza si continua recursiv.
inlocuire(E1, E2, [H | T], [H | T1]) :-
                H \= E1,
                inlocuire(E1, E2, T, T1).



% 12B

% maxim(L: lista, Max: intreg)
% Model de flux: (i, o)
% L : lista in care cautam maximul
% Max : raspunsul final, maximul sirului

% MODEL MATEMATIC
% maxim(l1...ln) = 0, daca lista este vida
%                = maxim(l2...ln), daca l1 este o lista
%                = max(l1, maxim(l2...ln)), altfel

% Cazul de baza: daca lista este goala, returneaza 0 ca maxim implicit.
maxim([], 0).  

% Daca elementul curent este o lista, o ignora si continua cu restul listei.
maxim([H | T], R) :- 
                is_list(H), 
                !,
                maxim(T, R).  

% Recursiv, gaseste maximul din coada si compara elementul curent cu maximul gasit in coada.
maxim([H | T], R) :- 
                maxim(T, R1),  
                R is max(H, R1). 


% maxim_sublista(L: lista, MaxSub: intreg, Rez: lista)
% Model de flux: (i,i,i)
% L : lista initiala (poate contine subliste)
% MaxSub : maximul elementelor care nu sunt liste din lista principala
% Rez : lista modificata, unde fiecare sublista are toate aparitiile lui MaxSub inlocuite cu maximul sublistei

% MODEL MATEMATIC
% maxim_sublista(l1...ln, MaxGlob) = [], daca lista este vida
%                                  = [l1] U maxim_sublista(l2...ln, MaxGlob), daca l1 nu este lista
%                                  = [H1] U maxim_sublista(l2...ln, MaxGlob), daca l1 este lista si H1 = inlocuire(MaxGlob, maxim(l1), l1)


% Cazul de baza: daca lista este goala, returneaza o lista goala.
maxim_sublista([], _,[]) :- 
                !.

% Daca elementul curent nu este o lista, il pastreaza si continua recursiv.
maxim_sublista([H | T], Max, [H | T1]) :- 
                not(is_list(H)),
                maxim_sublista(T, Max, T1).

% Daca elementul curent este o lista, gaseste maximul sublistei, inlocuieste toate aparitiile lui MaxSub 
% cu acest maxim in sublista respectiva si continua recursiv.
maxim_sublista([H | T], Max, [H1 | T1]) :- 
                is_list(H), 
                maxim(H, Rez),       % Gasesc maximul sublistei H
                inlocuire(Max, Rez, H, H1),  % Inlocuiesc MaxSub cu maximul sublistei
                maxim_sublista(T, Max, T1).  % Continui pentru restul listei


% main(L: lista, Rez: lista)
% Model de flux: (i, o)
% L : lista initiala (poate contine subliste)
% Rez : lista finala, dupa aplicarea operatiilor

% MODEL MATEMATIC
% main(l1...ln) = maxim_sublista(l1...ln, maxim(l1...ln))

% Gaseste maximul din lista principala si modifica sublistele pe baza acestui maxim.
main(L, Rez) :- 
                maxim(L, Max),  
                maxim_sublista(L, Max, Rez).



% inlocuire(2, 55, [1, 2, 4, 5, 2, 7, 2, 3, 4], Rez).
% main([7, 3, [5, 9, 7], [7, 4, 9]], Rez).