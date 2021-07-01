%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- use_module(library(statistics)).
:- set_prolog_flag( unknown,fail ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% DefiniÃ§Ã£o de invariante

:- op(900,xfy,'::').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

:- dynamic(solucoes/3).
:- dynamic(comprimento/2).
:- dynamic(pertence/2).
:- dynamic('::'/2).


:- include('conhecimento.pl').


%--------------------------------- - - - - - - - - - -  -  -  -  -   -



%% ALGORITMOS NAO INFORMADOS

percurso1(InicioPercurso,FimPercurso):- 
    caminho1(InicioPercurso,FimPercurso,[InicioPercurso], Percurso),
    write(Percurso),length(Percurso,R),nl,
    write(R),write(" paragens").
caminho1(Gid, Gid, _, [Gid]).                   
caminho1(InicioPercurso, FimPercurso, ParagensVisitadas, [InicioPercurso | Percurso]) :-  
     adjacente((_,_,InicioPercurso),(_,_,Seguinte),_),
     \+memberchk(Seguinte, ParagensVisitadas),
     caminho1(Seguinte, FimPercurso, [Seguinte|ParagensVisitadas], Percurso).


percurso2(InicioPercurso,FimPercurso):- 
    caminho2(InicioPercurso,[FimPercurso],[FimPercurso],Percurso),
    write(Percurso),length(Percurso,S),nl,
    write(S),write(" paragens").
caminho2(Gid, [Gid|Percurso], _, [Gid|Percurso]).        
caminho2(InicioPercurso, [FimPercurso|Percurso], ParagensVisitadas, PercursoFinal) :- 
    adjacente((_,_,Anterior),(_,_,FimPercurso),_),
    \+memberchk(Anterior, ParagensVisitadas),
    caminho2(InicioPercurso, [Anterior,FimPercurso|Percurso], [Anterior|ParagensVisitadas], PercursoFinal).
caminho2(InicioPercurso, [T,FimPercurso|Percurso], ParagensVisitadas,PercursoFinal) :-
    caminho2(InicioPercurso,[FimPercurso|Percurso],ParagensVisitadas,PercursoFinal).


percurso3( InicioPercurso, FimPercurso):- 
    caminho3( FimPercurso, [[InicioPercurso]], Percurso),
    write(Percurso),length(Percurso,S),nl,
    write(S),write(" paragens").
caminho3(FimPercurso, [[FimPercurso|ParagensVisitadas]|_], Percurso):- 
    inverte([FimPercurso|ParagensVisitadas], Percurso).
caminho3( FimPercurso, [ParagensVisitadas|Resto], Percurso) :-  
    caminho3(FimPercurso, Resto, Percurso).
caminho3( FimPercurso, [ParagensVisitadas|Resto], Percurso) :-
    ParagensVisitadas = [InicioPercurso|_],            
    InicioPercurso \== FimPercurso,
    findall(Seguinte,(adjacente((_,_,InicioPercurso),(_,_,Seguinte),_),\+ memberchk(Seguinte, ParagensVisitadas)),[T|Extend]),
    maplist2( consed(ParagensVisitadas), [T|Extend], VisitadosExtended),     
    append2( Resto, VisitadosExtended, UpdatedLista),   
    caminho3( FimPercurso, UpdatedLista, Percurso ).








%% AS SEGUINTES QUERYS FORAM RESOLVIDAS COM ALGORITMOS INFORMADOS

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Calcular um trajeto entre dois pontos;
%callquery1(183,499).


%% PRIMEIRO ALGORITMO

callquery1(InicioPercurso,FimPercurso):- 
    paragem(_,_,FimPercurso,_,_,_,_,_,_,_,_,_),  % se a paragem final nao existir acaba logo
    query1(InicioPercurso,FimPercurso,[InicioPercurso], Percurso),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query1(FimPercurso, FimPercurso, _, [FimPercurso]).      
query1(InicioPercurso,  FimPercurso, [InicioPercurso], []) :-
    paragem(_,C, FimPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,InicioPercurso), % ve se a paragem final é isolada 
    \+paragem(_,C, InicioPercurso,_,_,_,_,_,_,_,_,_). % se nao pertence a mesma carreira acaba
query1(InicioPercurso,  FimPercurso, ParagensVisitadas, [InicioPercurso | Percurso]) :-  
    verificaCarreira(InicioPercurso, FimPercurso,R), % se tem carreira comum
    (length(R,H), H>0, buscaValor(R,CarreiraComum),  % pega na carreira
    buscaId(CarreiraComum,InicioPercurso, FimPercurso,Id), % calcula o id
    adjacente((_,CarreiraComum,InicioPercurso),(Id,CarreiraComum,X),_); % busca o adjacente com respetivo id
    adjacente((_,_,InicioPercurso),(_,_,X),_)),    
    \+memberchk(X, ParagensVisitadas),
    query1(X,  FimPercurso, [X|ParagensVisitadas], Percurso).



%% SEGUNDO ALGORITMO

callquery11(InicioPercurso,FimPercurso):- 
    paragem(_,_,InicioPercurso,_,_,_,_,_,_,_,_,_),  % se a paragem inicial nao existir acaba logo
    query11(InicioPercurso,[FimPercurso],[FimPercurso], Percurso),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query11(Gid, [Gid|P], _, [Gid|P]). 
query11(InicioPercurso, [FimPercurso], [FimPercurso], []) :-
    paragem(_,C,InicioPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,FimPercurso).             
query11(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso) :- 
    verificaCarreira(InicioPercurso, Ultimo,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId2(V,InicioPercurso, Ultimo,Id) ,adjacente((Id,V,Penultimo),(_,V,Ultimo),_);
    adjacente((_,_,Penultimo),(_,_,Ultimo),_)),
    \+memberchk(Penultimo, ParagensVisitadas),
    query11(InicioPercurso, [Penultimo,Ultimo|P], [Penultimo|ParagensVisitadas], Percurso).
query11(InicioPercurso, [Penultimo,Ultimo|P], ParagensVisitadas, Percurso) :-
    query11(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Selecionar apenas algumas das operadoras de transporte para um determinado percurso;
%callquery2(183,499,['SCoTTURB']).
%callquery2(183,499,['SCoTTURB','Vimeca']).
%callquery22(183,499,['SCoTTURB','Vimeca']).
%time(callquery22(183,80,['Vimeca'])).

%% PRIMEIRO ALGORITMO
callquery2(InicioPercurso,FimPercurso, ListaOperadoras):- 
    paragem(_,_,FimPercurso,_,_,_,_,_,Operadora,_,_,_),  % se a paragem final nao existir acaba logo
    memberchk(Operadora, ListaOperadoras),
    query2(InicioPercurso,FimPercurso,[InicioPercurso], Percurso,  ListaOperadoras),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query2(FimPercurso, FimPercurso, _, [FimPercurso],_).      
query2(InicioPercurso,  FimPercurso, [InicioPercurso], [],_) :-
    paragem(_,C, FimPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,InicioPercurso), % ve se a paragem final é isolada 
    \+paragem(_,C, InicioPercurso,_,_,_,_,_,_,_,_,_). % se nao pertence a mesma carreira acaba    
query2(InicioPercurso,  FimPercurso, ParagensVisitadas, [InicioPercurso | Percurso], ListaOperadoras) :-  
    verificaCarreira(InicioPercurso, FimPercurso,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId(V,InicioPercurso, FimPercurso,T) ,adjacente((_,V,InicioPercurso),(T,V,X),_);
    adjacente((_,_,InicioPercurso),(_,_,X),_)),
    paragem(_,_,X,_,_,_,_,_,Operadora,_,_,_),
    memberchk(Operadora, ListaOperadoras), %verifica se operadora pertence à lista
    \+memberchk(X, ParagensVisitadas),
    query2(X,  FimPercurso, [X|ParagensVisitadas], Percurso,  ListaOperadoras).

%% SEGUNDO ALGORITMO

callquery22(InicioPercurso,FimPercurso, ListaOperadoras):- 
    paragem(_,_,InicioPercurso,_,_,_,_,_,Operadora,_,_,_),  % se a paragem inicial nao existir acaba logo
    memberchk(Operadora, ListaOperadoras),
    query22(InicioPercurso,[FimPercurso],[FimPercurso], Percurso,  ListaOperadoras),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query22(Gid, [Gid|P], _, [Gid|P],_). 
query22(InicioPercurso, [FimPercurso], [FimPercurso], [], _) :-
    paragem(_,C,InicioPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,FimPercurso).                  
query22(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso, ListaOperadoras) :- 
    verificaCarreira(InicioPercurso, Ultimo,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId2(V,InicioPercurso, Ultimo,Id) ,adjacente((Id,V,Penultimo),(_,V,Ultimo),_);
    adjacente((_,_,Penultimo),(_,_,Ultimo),_)),
    paragem(_,_,Penultimo,_,_,_,_,_,Operadora,_,_,_),
    memberchk(Operadora, ListaOperadoras),
    \+memberchk(Penultimo, ParagensVisitadas),
    query22(InicioPercurso, [Penultimo,Ultimo|P], [Penultimo|ParagensVisitadas], Percurso, ListaOperadoras).
query22(InicioPercurso, [Penultimo,Ultimo|P], ParagensVisitadas, Percurso, ListaOperadoras) :-
    query22(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso, ListaOperadoras).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Excluir um ou mais operadores de transporte para o percurso;
%callquery3(183,499,[]).
%time(callquery3(183,499,['SCoTTURB'])).

%% PRIMEIRO ALGORITMO

callquery3(InicioPercurso,FimPercurso, ListaOperadoras):- 
    paragem(_,_,FimPercurso,_,_,_,_,_,Operadora,_,_,_),  % se a paragem final nao existir acaba logo
    \+memberchk(Operadora, ListaOperadoras),
    query3(InicioPercurso,FimPercurso,[InicioPercurso], Percurso,  ListaOperadoras),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query3(FimPercurso, FimPercurso, _, [FimPercurso],_).      
query3(InicioPercurso,  FimPercurso, [InicioPercurso], [],_) :-
    paragem(_,C, FimPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,InicioPercurso), % ve se a paragem final é isolada 
    \+paragem(_,C, InicioPercurso,_,_,_,_,_,_,_,_,_). % se nao pertence a mesma carreira acaba     
query3(InicioPercurso,  FimPercurso, ParagensVisitadas, [InicioPercurso | Percurso], ListaOperadoras) :-  
    verificaCarreira(InicioPercurso, FimPercurso,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId(V,InicioPercurso, FimPercurso,T) ,adjacente((_,V,InicioPercurso),(T,V,X),_);
    adjacente((_,_,InicioPercurso),(_,_,X),_)),
    paragem(_,_,X,_,_,_,_,_,Operadora,_,_,_),
    \+memberchk(Operadora, ListaOperadoras), % verifica que operadora nao pertence à lista  
    \+memberchk(X, ParagensVisitadas),
    query3(X,  FimPercurso, [X|ParagensVisitadas], Percurso,  ListaOperadoras).



%% SEGUNDO ALGORITMO

callquery33(InicioPercurso,FimPercurso, ListaOperadoras):- 
    paragem(_,_,InicioPercurso,_,_,_,_,_,Operadora,_,_,_),  % se a paragem inicial nao existir acaba logo
    \+memberchk(Operadora, ListaOperadoras),
    query33(InicioPercurso,[FimPercurso],[FimPercurso], Percurso,  ListaOperadoras),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query33(Gid, [Gid|P], _, [Gid|P],_). 
query33(InicioPercurso, [FimPercurso], [FimPercurso], [], _) :-
    paragem(_,C,InicioPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,FimPercurso).                  
query33(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso, ListaOperadoras) :- 
    verificaCarreira(InicioPercurso, Ultimo,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId2(V,InicioPercurso, Ultimo,Id) ,adjacente((Id,V,Penultimo),(_,V,Ultimo),_);
    adjacente((_,_,Penultimo),(_,_,Ultimo),_)),
    paragem(_,_,Penultimo,_,_,_,_,_,Operadora,_,_,_),
    \+memberchk(Operadora, ListaOperadoras),
    \+memberchk(Penultimo, ParagensVisitadas),
    query33(InicioPercurso, [Penultimo,Ultimo|P], [Penultimo|ParagensVisitadas], Percurso, ListaOperadoras).
query33(InicioPercurso, [Penultimo,Ultimo|P], ParagensVisitadas, Percurso, ListaOperadoras) :-
    query33(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso, ListaOperadoras).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Identificar quais as paragens com o maior número de carreiras num determinado percurso;
% callquery4([183,791,595,594,180,181,593,499],3).

callquery4(Percurso, NumeroParagens) :- 
    numerocarreiras(Percurso,PFinal),
    sort(PFinal,Top),
    inverte(Top,FinalTop),
    showTop(FinalTop,NumeroParagens).

numerocarreiras([H],[(R,H)]):- ncarreiras(H,R).
numerocarreiras([H|T],[(R,H)|P]):- 
    ncarreiras(H,R),
    numerocarreiras(T,P).

ncarreiras(Gid,R) :- 
    findall(Carreira,paragem(_,Carreira,Gid,_,_,_,_,_,_,_,_,_),L),
    sort(L,T),
    length(T,R).

showTop(_,0).
showTop([],_).
showTop([(R,T)|Y],N) :- 
    N>0,
    write("A paragem "),
    write(T), 
    write(" contem "),
    write(R), 
    write(" carreiras!\n"),
    decrementador(N,NAnteiror),
    showTop(Y,NAnteiror).




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher o menor percurso (usando critério menor número de paragens);

callquery5(InicioPercurso, FimPercurso) :-
    findall((NumeroParagens,Percurso), (query1(InicioPercurso,FimPercurso,[InicioPercurso], Percurso),length(Percurso,NumeroParagens)), L),
    sort(L,R),
    buscaValor2(R,J),
    write(J).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher o percurso mais rápido (usando critério da distância);

callquery6(InicioPercurso, FimPercurso) :- 
    findall((Distancia,Percurso), (query1(InicioPercurso,FimPercurso,[InicioPercurso], Percurso),calculaDistancia(Percurso,Distancia)), L),
    sort(L,R),
    buscaValor2(R,J),
    write(J).

calculaDistancia([X],0).
calculaDistancia([X,Y|T],Distancia+Dist):-
    adjacente((_,_,X),(_,_,Y),Dist),
    calculaDistancia([Y|T],Distancia).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher o percurso que passe apenas por abrigos com publicidade;
% callquery7(183,499,'Yes'). -> com publicidade
% time(callquery7(183,499,'No')). -> sem publicidade
%time(callquery77(583,609,'No')).

%% PRIMEIRO ALGORITMO

callquery7(InicioPercurso,FimPercurso, Flag):- 
    paragem(_,_,FimPercurso,_,_,_,_,Publicidade,_,_,_,_),  % se a paragem final nao existir acaba logo
    memberchk(Publicidade, [Flag]),
    query7(InicioPercurso,FimPercurso,[InicioPercurso], Percurso,  [Flag]),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query7(FimPercurso, FimPercurso, _, [FimPercurso],_).      
query7(InicioPercurso,  FimPercurso, [InicioPercurso], [],_) :-
    paragem(_,C, FimPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,InicioPercurso), % ve se a paragem final é isolada 
    \+paragem(_,C, InicioPercurso,_,_,_,_,_,_,_,_,_). % se nao pertence a mesma carreira acaba     
query7(InicioPercurso,  FimPercurso, ParagensVisitadas, [InicioPercurso | Percurso], Flag) :-  
    verificaCarreira(InicioPercurso, FimPercurso,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId(V,InicioPercurso, FimPercurso,Id) ,adjacente((_,V,InicioPercurso),(Id,V,Proximo),_);
    adjacente((_,_,InicioPercurso),(_,_,Proximo),_)),
    paragem(_,_,Proximo,_,_,_,_,Publicidade,_,_,_,_),
    memberchk(Publicidade, Flag),   %verifica se tem publicidade
    \+memberchk(Proximo, ParagensVisitadas),
    query7(Proximo,  FimPercurso, [Proximo|ParagensVisitadas], Percurso,  Flag).



%% SEGUNDO ALGORITMO

callquery77(InicioPercurso,FimPercurso, Flag):- 
    paragem(_,_,InicioPercurso,_,_,_,_,Publicidade,_,_,_,_),  % se a paragem inicial nao existir acaba logo
    memberchk(Publicidade, [Flag]),
    query77(InicioPercurso,[FimPercurso],[FimPercurso], Percurso,  [Flag]),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query77(Gid, [Gid|P], _, [Gid|P],_). 
query77(InicioPercurso, [FimPercurso], [FimPercurso], [], _) :-
    paragem(_,C,InicioPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,FimPercurso).                  
query77(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso, Flag) :- 
    verificaCarreira(InicioPercurso, Ultimo,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId2(V,InicioPercurso, Ultimo,Id) ,adjacente((Id,V,Penultimo),(_,V,Ultimo),_);
    adjacente((_,_,Penultimo),(_,_,Ultimo),_)),
    paragem(_,_,Penultimo,_,_,_,_,Publicidade,_,_,_,_),
    memberchk(Publicidade, Flag),
    \+memberchk(Penultimo, ParagensVisitadas),
    query77(InicioPercurso, [Penultimo,Ultimo|P], [Penultimo|ParagensVisitadas], Percurso, Flag).
query77(InicioPercurso, [Penultimo,Ultimo|P], ParagensVisitadas, Percurso, Flag) :-
    query77(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso, Flag).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher o percurso que passe apenas por paragens abrigadas;
% callquery8(183,499,'Yes'). -> com abrigo
% time(callquery8(183,499,'No')).  -> sem abrigo

%% PRIMEIRO ALGORITMO

callquery8(InicioPercurso,FimPercurso, 'Yes'):- 
    paragem(_,_,FimPercurso,_,_,_,Abrigo,_,_,_,_,_),  % se a paragem final nao existir acaba logo
    memberchk(Abrigo, ['Aberto dos Lados','Fechado dos Lados']),
    query8(InicioPercurso,FimPercurso,[InicioPercurso], Percurso,  ['Aberto dos Lados','Fechado dos Lados']),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

callquery8(InicioPercurso,FimPercurso, 'No'):- 
    paragem(_,_,FimPercurso,_,_,_,Abrigo,_,_,_,_,_),  % se a paragem final nao existir acaba logo
    memberchk(Abrigo, ['Sem Abrigo']),
    query8(InicioPercurso,FimPercurso,[InicioPercurso], Percurso,['Sem Abrigo']),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

query8(FimPercurso, FimPercurso, _, [FimPercurso],_).      
query8(InicioPercurso,  FimPercurso, [InicioPercurso], [],_) :-
    paragem(_,C, FimPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,InicioPercurso), % ve se a paragem final é isolada 
    \+paragem(_,C, InicioPercurso,_,_,_,_,_,_,_,_,_). % se nao pertence a mesma carreira acaba    
query8(InicioPercurso,  FimPercurso, ParagensVisitadas, [InicioPercurso | Percurso], Flag) :-  
    verificaCarreira(InicioPercurso, FimPercurso,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId(V,InicioPercurso, FimPercurso,Id) ,adjacente((_,V,InicioPercurso),(Id,V,Proximo),_);
    adjacente((_,_,InicioPercurso),(_,_,Proximo),_)),
    paragem(_,_,Proximo,_,_,_,Abrigo,_,_,_,_,_),
    memberchk(Abrigo, Flag),   % verifica se tem abrigo
    \+memberchk(Proximo, ParagensVisitadas),
    query8(Proximo,  FimPercurso, [Proximo|ParagensVisitadas], Percurso,  Flag).



%% SEGUNDO ALGORITMO

callquery88(InicioPercurso,FimPercurso, 'Yes'):- 
    paragem(_,_,InicioPercurso,_,_,_,Abrigo,_,_,_,_,_),  % se a paragem inicial nao existir acaba logo
    memberchk(Abrigo, ['Aberto dos Lados','Fechado dos Lados']),
    query88(InicioPercurso,[FimPercurso],[InicioPercurso], Percurso,  ['Aberto dos Lados','Fechado dos Lados']),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

callquery88(InicioPercurso,FimPercurso, 'No'):- 
    paragem(_,_,InicioPercurso,_,_,_,Abrigo,_,_,_,_,_),  % se a paragem inicial nao existir acaba logo
    memberchk(Abrigo, ['Sem Abrigo']),
    query88(InicioPercurso,[FimPercurso],[InicioPercurso], Percurso,['Sem Abrigo']),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").


query88(Gid, [Gid|P], _, [Gid|P],_). 
query88(InicioPercurso, [FimPercurso], [FimPercurso], [], _) :-
    paragem(_,C,InicioPercurso,_,_,_,_,_,_,_,_,_),
    verificaIsolada(C,FimPercurso).                  
query88(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso, Flag) :- 
    verificaCarreira(InicioPercurso, Ultimo,R),
    (length(R,H), H>0, buscaValor(R,V), buscaId2(V,InicioPercurso, Ultimo,Id) ,adjacente((Id,V,Penultimo),(_,V,Ultimo),_);
    adjacente((_,_,Penultimo),(_,_,Ultimo),_)),
    paragem(_,_,Penultimo,_,_,_,Abrigo,_,_,_,_,_),
    memberchk(Abrigo, Flag),
    \+memberchk(Penultimo, ParagensVisitadas),
    query88(InicioPercurso, [Penultimo,Ultimo|P], [Penultimo|ParagensVisitadas], Percurso, Flag).
query88(InicioPercurso, [Penultimo,Ultimo|P], ParagensVisitadas, Percurso, Flag) :-
    query88(InicioPercurso, [Ultimo|P], ParagensVisitadas, Percurso, Flag).




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher um ou mais pontos intermédios por onde o percurso deverá passar;
% time(callquery9(183,499,[595])).



%% PRIMEIRO ALGORITMO

callquery9(InicioPercurso,FimPercurso, Lista):- 
    paragem(_,_,FimPercurso,_,_,_,_,_,_,_,_,_),  % se a paragem final nao existir acaba logo
    rquery9(InicioPercurso,FimPercurso,[InicioPercurso], _, Lista, Percurso),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

rquery9(InicioPercurso, FimPercurso, _ , [InicioPercurso | Percurso], [], P):-
    query1(InicioPercurso, FimPercurso, [InicioPercurso] , P).

rquery9(InicioPercurso, FimPercurso, ParagensVisitadas, [InicioPercurso | Percurso], [H|T], PFinal):-
    % se a paragem ainda nao pertence ao percurso final
    (\+(memberchk(H, ParagensVisitadas)),\+(memberchk(H,[InicioPercurso])),\+(memberchk(H,[FimPercurso])) ->
    query1(InicioPercurso, H, ParagensVisitadas, P),
    removehead(PF,PFaux),
    junta(P,PFaux,PFinal),
    rquery9(H, FimPercurso, ParagensVisitadas, [H | Percurso], T, PF);
     % se a paragem ja existe no percurso não percisa de voltar a passar lá
    rquery9(InicioPercurso, FimPercurso, ParagensVisitadas, [InicioPercurso | Percurso], T, PFinal)).
   



%% SEGUNDO ALGORITMO

callquery99(InicioPercurso,FimPercurso, Lista):- 
    paragem(_,_,InicioPercurso,_,_,_,_,_,_,_,_,_),  % se a paragem inicial nao existir acaba logo
    rquery99(InicioPercurso,[FimPercurso],[FimPercurso], _, Lista, Percurso),
    write("\n"),
    write(Percurso),
    length(Percurso,R),
    write("\n\nO percurso contem "),
    write(R),
    write(" paragens!").

rquery99(InicioPercurso, [FimPercurso],ParagensVisitadas , [FimPercurso | Percurso], [], P):-
    query11(InicioPercurso, [FimPercurso], [FimPercurso] , P).

rquery99(InicioPercurso, [FimPercurso], ParagensVisitadas, [FimPercurso | Percurso], [H|T], PFinal):-
    (\+(memberchk(H, ParagensVisitadas)),\+(memberchk(H,[InicioPercurso])),\+(memberchk(H,[FimPercurso])) ->
    query11(InicioPercurso, [H], ParagensVisitadas, P),
    removehead(PF,PFaux),
    junta(P,PFaux,PFinal),
    rquery99(H, [FimPercurso], ParagensVisitadas, [FimPercurso | Percurso], T, PF);
    rquery99(InicioPercurso, [FimPercurso], ParagensVisitadas, [FimPercurso | Percurso], T, PFinal)).


%time(callquery99(219,712,[595,791,1009,25,580,45])).
%time(callquery99(183,499,[])). 




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% FUNCOES AUXILIARES

solucoes( X,Y,Z ) :-
    findall( X,Y,Z ).

comprimento( S,N ) :-
    length( S,N ).

pertence( X,[X|_] ).
pertence( X,[Y|L] ) :-
    X \= Y,
    pertence( X,L ).

inverte(Xs, Ys):-
	inverte(Xs, [], Ys).

inverte([], Xs, Xs).
inverte([X|Xs],Ys, Zs):-
	inverte(Xs, [X|Ys], Zs).


showLista([]).
showLista([X|Y]) :- write(X),nl,showLista(Y).


buscaValor2([(L,X)|T],X).

menor([],P,P).
menor(H,L,L) :- length(H,I),I=0; length(H,I),length(L,J),I>=J.
menor(H,L,H) :- length(H,I),length(L,J),I<J.


numNodosDiferentes(R) :- findall(Gid,paragem(_,_,Gid,_,_,_,_,_,_,_,_,_),L),
                         sort(L,G),
                         length(G,R).

numNodosCada(Gid,R) :- findall(Gid,paragem(_,_,Gid,_,_,_,_,_,_,_,_,_),L),
                       length(L,R).
 
verifica([]).
verifica([X|T]):- X=1,verifica(T).

verificaIsolada(C,G):-
    findall(Gid,paragem(_,C,Gid,_,_,_,_,_,_,_,_,_),Lista),
    vefIsolada(Lista),
    \+paragem(_,C,G,_,_,_,_,_,_,_,_,_).

vefIsolada([]).
vefIsolada([H|T]) :-
    findall(H,paragem(_,_,H,_,_,_,_,_,_,_,_,_),Lista),
    length(Lista,R),
    R=1,
    vefIsolada(T).

verificaCarreira(Ni,Nf,R):-
    findall(C,paragem(_,C,Ni,_,_,_,_,_,_,_,_,_),L),
    findall(P,paragem(_,P,Nf,_,_,_,_,_,_,_,_,_),T),
    inter(L,T,R).
    
buscaValor([X|T],X).

buscaId(V,Start,Finish,T):-
    paragem(Id1,V,Start,_,_,_,_,_,_,_,_,_),
    paragem(Id2,V,Finish,_,_,_,_,_,_,_,_,_),
    (Id1>Id2, decrementador(Id1,T);
     Id1<Id2, incrementador(Id1,T)).

inter([], _, []).
inter([H1|T1], L2, [H1|Res]) :-
    memberchk(H1, L2),
    inter(T1, L2, Res).
inter([_|T1], L2, Res) :-
    inter(T1, L2, Res).

incrementador(A,B) :- B is A+1.

decrementador(A,B) :- B is A-1.

buscaId2(V,Start,Finish,T):-
    paragem(Id1,V,Start,_,_,_,_,_,_,_,_,_),
    paragem(Id2,V,Finish,_,_,_,_,_,_,_,_,_),
    (Id1>Id2, incrementador(Id2,T);
     Id1<Id2, decrementador(Id2,T)).

removehead([_|Tail], Tail).

junta([],X,X).                                 
junta([X|Y],Z,[X|W]) :- junta(Y,Z,W).


consed( A, B, [B|A]).


append2([X|Y],Z,[X|W]) :- append2(Y,Z,W).  
append2([],X,X).

maplist2(_C_2, [], []).
maplist2( C_2, [X|Xs], [Y|Ys]) :-
   call(C_2, X, Y),
   maplist2( C_2, Xs, Ys).