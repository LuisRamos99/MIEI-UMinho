%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% DefiniÃ§Ã£o de invariante

:- op(900,xfy,'::').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
:- dynamic(adjudicante/4).
:- dynamic(adjudicataria/4).
:- dynamic(contrato/10).
:- dynamic(excecao/1).
:- dynamic(nulo/1).
:- dynamic(evolucao/1).
:- dynamic(evolucao/2).
:- dynamic(insercao/1).
:- dynamic(teste/1).
:- dynamic(involucao/1).
:- dynamic(remocao/1).
:- dynamic(demo/2).
:- dynamic(nao/1).
:- dynamic(solucoes/3).
:- dynamic(comprimento/2).
:- dynamic(pertence/2).
:- dynamic('-'/1).
:- dynamic('::'/2).


:- include('conhecimento.pl').
:- include('evol_invol.pl').
:- include('invariantes.pl').



%Pressupostos de mundo fechado _______________________________________________________________________
-adjudicante( IdAd, Nome, NIF, Morada ) :-
    nao( adjudicante( IdAd, Nome, NIF, Morada ) ),
    nao( excecao(adjudicante( IdAd, Nome, NIF, Morada ) )).

-adjudicataria( IdAda, Nome, NIF, Morada ) :-
    nao( adjudicataria( IdAda, Nome, NIF, Morada ) ),
    nao( excecao(adjudicataria( IdAda, Nome, NIF, Morada )) ).




% QUERIE -------------------------------------- - - - - - - - - - -  - -  -   -  

contratosAtivos(Data) :- solucoes((contrato(Id,Id1,Id2,TC,TP,D,V,Prazo,Local,Da)),(contrato(Id,Id1,Id2,TC,TP,D,V,Prazo,Local,Da),dataNaoExpirada(Data,Da,Prazo)),L),showLista(L).

dataNaoExpirada([Da,Ma,Aa],[D,M,A],Prazo) :- sub(Aa,A,R),mul(R,365,R1),
                                             sub(Ma,M,R2),mul(R2,30,R3),
                                             sub(Da,D,R4),
                                             sum(R1,R3,R5),sum(R5,R4,F),
                                             F < Prazo.

showLista([]).
showLista([X|Y]) :- write(X),nl,showLista(Y).



% FUNÇOES AUXILIARES--------------------------------- - - - - - - - - - -  -  -  -  -   -

evolucao( Termo ) :-
    solucoes( Invariante,+Termo::Invariante,Lista ),
    insercao( Termo ),
    teste( Lista ).

insercao( Termo ) :-
    assert( Termo ).
insercao( Termo ) :-
    retract( Termo ),!,fail.

teste( [] ).
teste( [R|LR] ) :-
    R,
    teste( LR ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a involucao do conhecimento

involucao( Termo ) :-
    solucoes( Invariante,-Termo::Invariante,Lista ),
    remocao( Termo ),
    teste( Lista ).

remocao( Termo ) :-
    retract( Termo ).
remocao( Termo ) :-
    assert( Termo ),!,fail.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado demo: Questao,Resposta -> {V,F}
%                            Resposta = { verdadeiro,falso,desconhecido }

demo( Questao,verdadeiro ) :-
    Questao.
demo( Questao,falso ) :-
    -Questao.
demo( Questao,desconhecido ) :-
    nao( Questao ),
    nao( -Questao ).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

solucoes( X,Y,Z ) :-
    findall( X,Y,Z ).

comprimento( S,N ) :-
    length( S,N ).



pertence( X,[X|L] ).
pertence( X,[Y|L] ) :-
    X \= Y,
    pertence( X,L ).


