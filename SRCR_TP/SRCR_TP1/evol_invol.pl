%%%----------------------------------- Evolução do conhecimento ----------------------------------------------------%%%

% EVOLUCAO CONHECIMENTO POSITIVO
evolucao( Termo , p) :-
    solucoes( Invariante,+Termo::Invariante,Lista ),
    insercao( Termo ),
    teste( Lista ).

% EVOLUCAO CONHECIMENTO NEGATIVO
evolucao( Termo , n) :-
    solucoes( Invariante,+(-Termo)::Invariante,Lista ),
    teste( Lista ),
    insercao( -Termo ).
    

% EVOLUCAO CONHECIMENTO INCERTO

%ADJUDICANTE
%Evolucao de adjudicante com nome desconhecido
evolucao(adjudicante(Id,Nome,Nif,Morada),nome_desconhecido) :-
    evolucao(adjudicante(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicante(I,_,Ni,M)):-adjudicante(I,Nome,Ni,M))).

%Evolucao de adjudicante com nif desconhecido
evolucao(adjudicante(Id,Nome,Nif,Morada),nif_desconhecido) :-
    evolucao(adjudicante(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicante(I,N,_,M)) :- adjudicante(I,N,Nif,M))).

%Evolucao de adjudicante com morada desconhecida
evolucao(adjudicante(Id,Nome,Nif,Morada),morada_desconhecida) :-
    evolucao(adjudicante(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicante(I,N,Ni,_)) :- adjudicante(I,N,Ni,Morada))).



%ADJUDICATARIA
%Evolucao de adjudicataria com nome desconhecido
evolucao(adjudicataria(Id,Nome,Nif,Morada),nome_desconhecido) :-
    evolucao(adjudicataria(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicataria(I,_,Ni,M)) :- adjudicataria(I,Nome,Ni,M))).

%Evolucao de adjudicataria com nif desconhecido
evolucao(adjudicataria(Id,Nome,Nif,Morada),nif_desconhecido) :-
    evolucao(adjudicataria(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicataria(I,N,_,M)) :- adjudicataria(I,N,Nif,M))).

%Evolucao de adjudicataria com morada desconhecida
evolucao(adjudicataria(Id,Nome,Nif,Morada),morada_desconhecida) :-
    evolucao(adjudicataria(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicataria(I,N,Ni,_)) :- adjudicataria(I,N,Ni,Morada))).


%CONTRATO
%Evolucao de contrato com IdAd desconhecido
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),idad_desconhecido) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,_,Id,TC,TP,D,V,P,L,Da)) :- adjudicataria(I,IdAd,Id,TC,TP,D,V,P,L,Da))).


%Evolucao de contrato com IdAda desconhecido
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),idada_desconhecido) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,Id,_,TC,TP,D,V,P,L,Da)) :- adjudicataria(I,Id,IdAda,TC,TP,D,V,P,L,Da))).


%Evolucao de contrato com TipoContrato desconhecido
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),tipoc_desconhecido) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,Id1,Id2,_,TP,D,V,P,L,Da)) :- adjudicataria(I,Id1,Id2,TipoContrato,TP,D,V,P,L,Da))).

%Evolucao de contrato com TipoProcedimento desconhecido
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),tipop_desconhecido) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,Id1,Id2,TC,_,D,V,P,L,Da)) :- adjudicataria(I,Id1,Id2,TC,TipoProcedimento,D,V,P,L,Da))).

%Evolucao de contrato com Descricao desconhecida
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),descricao_desconhecida) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,Id1,Id2,TC,TP,_,V,P,L,Da)) :- adjudicataria(I,Id1,Id2,TC,TP,Descricao,V,P,L,Da))).


%Evolucao de contrato com Valor desconhecido
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),descricao_desconhecida) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,Id1,Id2,TC,TP,D,_,P,L,Da)) :- adjudicataria(I,Id1,Id2,TC,TP,D,Valor,P,L,Da))).


%Evolucao de contrato com Prazo desconhecido
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),prazo_desconhecida) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,Id1,Id2,TC,TP,D,V,_,L,Da)) :- adjudicataria(I,Id1,Id2,TC,TP,D,V,Prazo,L,Da))).

%Evolucao de contrato com Local desconhecido
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),descricao_desconhecida) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,Id1,Id2,TC,TP,D,V,P,_,Da)) :- adjudicataria(I,Id1,Id2,TC,TP,D,V,P,Local,Da))).

%Evolucao de contrato com Data desconhecido
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),descricao_desconhecida) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    insercao((excecao(adjudicataria(I,Id1,Id2,TC,TP,D,V,P,L,_)) :- adjudicataria(I,Id1,Id2,TC,TP,D,V,P,L,Data))).






% EVOLUCAO CONHECIMENTO IMPRECISO
%VALOR IMPRECISO
evolucao( Termo , i) :-
    solucoes( Invariante,+(excecao(Termo))::Invariante,Lista ),
    insercao( excecao(Termo) ),
    teste( Lista ).



%INTERVALO DE VALORES

%ADJUDICANTE (nif impreciso)
evolucao( adjudicante(Id,Nome,Nif,Morada) , i, Inf, Sup) :-
    insercao((excecao(adjudicante(Id,Nome,Nif,Morada)) :- Nif >= Inf,Nif =< Sup)).

%ADJUCATARIA (nif impreciso)
evolucao( adjudicataria(Id,Nome,Nif,Morada) , i, Inf, Sup) :-
    insercao((excecao(adjudicataria(Id,Nome,Nif,Morada)) :- Nif >= Inf,Nif =< Sup)).

%CONTRATO (IdAd impreciso)
evolucao( contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data) , i, Inf, Sup) :-
    insercao((excecao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data)) :- IdAd >= Inf,IdAd =< Sup)). 

%CONTRATO (IdAda impreciso)
evolucao( contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data) , i, Inf, Sup) :-
    insercao((excecao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data)) :- IdAda >= Inf,IdAda =< Sup)). 

%CONTRATO (valor impreciso)
evolucao( contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data) , i, Inf, Sup) :-
    insercao((excecao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data)) :- Valor >= Inf,Valor =< Sup)).

%CONTRATO (prazo impreciso)
evolucao( contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data) , i, Inf, Sup) :-
    insercao((excecao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data)) :- Prazo >= Inf,Prazo =< Sup)). 



% EVOLUCAO CONHECIMENTO INTERDITO

%ADJUDICANTE

%Evolucao de adjudicante com nome impossivel
evolucao(adjudicante(Id,Nome,Nif,Morada),nome_impossivel) :-
    evolucao(adjudicante(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicante(I,_,Ni,M)):-adjudicante(I,Nome,Ni,M))),
    insercao((nulo(Nome))),
    insercao(( +adjudicante(I,_,Ni,M) :: (solucoes((I, _, Ni, M ),
    (adjudicante(Id, Nome, Nif, Morada ),nao(nulo(Nome))),R),comprimento(R,0)) )).

%Evolucao de adjudicante com nif impossivel
evolucao(adjudicante(Id,Nome,Nif,Morada),nif_impossivel) :-
    evolucao(adjudicante(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicante(I,N,_,M)):-adjudicante(I,N,Nif,M))),
    insercao((nulo(Nif))),
    insercao(( +adjudicante(I,N,_,M) :: (solucoes((I, N, _, M ),(adjudicante(Id, Nome, Nif, Morada ),nao(nulo(Nif))),R),comprimento(R,0)) )).

%Evolucao de adjudicante com morada impossivel
evolucao(adjudicante(Id,Nome,Nif,Morada),morada_impossivel) :-
    evolucao(adjudicante(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicante(I,N,Ni,_)):-adjudicante(I,N,Ni,Morada))),
    insercao((nulo(Morada))),
    insercao(( +adjudicante(I,N,Ni,_) :: (solucoes((I, N, Ni, _ ),(adjudicante(Id, Nome, Nif, Morada ),nao(nulo(Morada))),R),comprimento(R,0)) )).




%ADJUDICATARIA

%Evolucao de adjudicataria com nome impossivel
evolucao(adjudicataria(Id,Nome,Nif,Morada),nome_impossivel) :-
    evolucao(adjudicataria(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicataria(I,_,Ni,M)):-adjudicataria(I,Nome,Ni,M))),
    insercao((nulo(Nome))),
    insercao(( +adjudicataria(I,_,Ni,M) :: (solucoes((I, _, Ni, M ),(adjudicataria(Id, Nome, Nif, Morada ),nao(nulo(Nome))),R),comprimento(R,0)) )).

%Evolucao de adjudicataria com nif impossivel
evolucao(adjudicataria(Id,Nome,Nif,Morada),nif_impossivel) :-
    evolucao(adjudicataria(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicataria(I,N,_,M)):-adjudicataria(I,N,Nif,M))),
    insercao((nulo(Nif))),
    insercao(( +adjudicataria(I,N,_,M) :: (solucoes((I, N, _, M ),(adjudicataria(Id, Nome, Nif, Morada ),nao(nulo(Nif))),R),comprimento(R,0)) )).

%Evolucao de adjudicataria com morada impossivel
evolucao(adjudicataria(Id,Nome,Nif,Morada),morada_impossivel) :-
    evolucao(adjudicataria(Id,Nome,Nif,Morada),p),
    insercao((excecao(adjudicataria(I,N,Ni,_)):-adjudicataria(I,N,Ni,Morada))),
    insercao((nulo(Morada))),
    insercao(( +adjudicataria(I,N,Ni,_) :: (solucoes((I, N, Ni, _ ),(adjudicataria(Id, Nome, Nif, Morada ),nao(nulo(Morada))),R),comprimento(R,0)) )).




%CONTRATO

%Evolucao de contrato com tipo de contrato impossivel
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),tipoc_impossivel) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    insercao((excecao(contrato(I,I1,I2,_,TP,D,V,P,L,Da)):-contrato(I,I1,I2,TipoContrato,TP,D,V,P,L,Da))),
    insercao((nulo(TipoContrato))),
    insercao((+contrato(I,I1,I2,_,TP,D,V,P,L,Da) :: (solucoes((I,I1,I2,_,TP,D,V,P,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(TipoContrato))),R),comprimento(R,0)) )).

%Evolucao de contrato com tipo de procedimento impossivel
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),tipop_impossivel) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    insercao((excecao(contrato(I,I1,I2,TC,_,D,V,P,L,Da)):-contrato(I,I1,I2,TC,TipoProcedimento,D,V,P,L,Da))),
    insercao((nulo(TipoProcedimento))),
    insercao((+contrato(I,I1,I2,TC,_,D,V,P,L,Da) :: (solucoes((I,I1,I2,TC,_,D,V,P,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(TipoProcedimento))),R),comprimento(R,0)) )).

%Evolucao de contrato com descriçao impossivel
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),descricao_impossivel) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    insercao((excecao(contrato(I,I1,I2,TC,TP,_,V,P,L,Da)):-contrato(I,I1,I2,TC,TP,Descricao,V,P,L,Da))),
    insercao((nulo(Descricao))),
    insercao((+contrato(I,I1,I2,TC,TP,_,V,P,L,Da) :: (solucoes((I,I1,I2,TC,TP,_,V,P,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(Descricao))),R),comprimento(R,0)) )).


%Evolucao de contrato com valor impossivel
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),valor_impossivel) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    insercao((excecao(contrato(I,I1,I2,TC,TP,D,_,P,L,Da)):-contrato(I,I1,I2,TC,TP,D,Valor,P,L,Da))),
    insercao((nulo(Valor))),
    insercao((+contrato(I,I1,I2,TC,TP,D,_,P,L,Da) :: (solucoes((I,I1,I2,TC,TP,D,_,P,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(Valor))),R),comprimento(R,0)) )).

%Evolucao de contrato com prazo impossivel
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),valor_impossivel) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    insercao((excecao(contrato(I,I1,I2,TC,TP,D,V,_,L,Da)):-contrato(I,I1,I2,TC,TP,D,V,Prazo,L,Da))),
    insercao((nulo(Prazo))),
    insercao((+contrato(I,I1,I2,TC,TP,D,V,_,L,Da) :: (solucoes((I,I1,I2,TC,TP,D,V,_,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(Prazo))),R),comprimento(R,0)) )).


%Evolucao de contrato com local impossivel
evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),valor_impossivel) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    insercao((excecao(contrato(I,I1,I2,TC,TP,D,V,P,_,Da)):-contrato(I,I1,I2,TC,TP,D,V,P,Local,Da))),
    insercao((nulo(Local))),
    insercao((+contrato(I,I1,I2,TC,TP,D,V,P,_,Da) :: (solucoes((I,I1,I2,TC,TP,D,V,P,_,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(Local))),R),comprimento(R,0)) )).




%%%----------------------------------- Involução do conhecimento ----------------------------------------------------%%%

% INVOLUCAO CONHECIMENTO POSITIVO 
involucao( Termo , p) :-
    solucoes( Invariante,-Termo::Invariante,Lista ),
    teste( Lista ),
    remocao( Termo ).
  

% INVOLUCAO CONHECIMENTO NEGATIVO 
involucao( Termo , n) :-
    solucoes( Invariante,-(-Termo)::Invariante,Lista ),
    remocao( -Termo ),
    teste( Lista ).




% INVOLUCAO CONHECIMENTO INCERTO 

%ADJUDICANTE

%Involucao de adjudicante com nome desconhecido
involucao(adjudicante(Id,Nome,Nif,Morada),nome_desconhecido) :-
    involucao(adjudicante(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicante(I,_,Ni,M)):-adjudicante(I,Nome,Ni,M))).

%Involucao de adjudicante com nif desconhecido
involucao(adjudicante(Id,Nome,Nif,Morada),nif_desconhecido) :-
    involucao(adjudicante(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicante(I,N,_,M)) :- adjudicante(I,N,Nif,M))).

%Involucao de adjudicante com morada desconhecida
involucao(adjudicante(Id,Nome,Nif,Morada),morada_desconhecida) :-
    involucao(adjudicante(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicante(I,N,Ni,_)) :- adjudicante(I,N,Ni,Morada))).




%ADJUDICATARIA

%Involucao de adjudicataria com nome desconhecido
involucao(adjudicataria(Id,Nome,Nif,Morada),nome_desconhecido) :-
    involucao(adjudicataria(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicataria(I,_,Ni,M)) :- adjudicataria(I,Nome,Ni,M))).


%Involucao de adjudicataria com nif desconhecido
involucao(adjudicataria(Id,Nome,Nif,Morada),nif_desconhecido) :-
    involucao(adjudicataria(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicataria(I,N,_,M)) :- adjudicataria(I,N,Nif,M))).

%Involucao de adjudicataria com morada desconhecida
involucao(adjudicataria(Id,Nome,Nif,Morada),morada_desconhecida) :-
    involucao(adjudicataria(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicataria(I,N,Ni,_)) :- adjudicataria(I,N,Ni,Morada))).





%CONTRATO


%Involucao de contrato com IdAd desconhecido
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),idad_desconhecido) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,_,Id,TC,TP,D,V,P,L,Da)) :- adjudicataria(I,IdAd,Id,TC,TP,D,V,P,L,Da))).


%Involucao de contrato com IdAda desconhecido
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),idada_desconhecido) :-
    evolucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,Id,_,TC,TP,D,V,P,L,Da)) :- adjudicataria(I,Id,IdAda,TC,TP,D,V,P,L,Da))).


%Involucao de contrato com TipoContrato desconhecido
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),tipoc_desconhecido) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,Id1,Id2,_,TP,D,V,P,L,Da)) :- adjudicataria(I,Id1,Id2,TipoContrato,TP,D,V,P,L,Da))).

%Involucao de contrato com TipoProcedimento desconhecido
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),tipop_desconhecido) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,Id1,Id2,TC,_,D,V,P,L,Da)) :- adjudicataria(I,Id1,Id2,TC,TipoProcedimento,D,V,P,L,Da))).

%Involucao de contrato com Descricao desconhecida
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),descricao_desconhecida) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,Id1,Id2,TC,TP,_,V,P,L,Da)) :- adjudicataria(I,Id1,Id2,TC,TP,Descricao,V,P,L,Da))).


%Involucao de contrato com Valor desconhecido
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),descricao_desconhecida) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,Id1,Id2,TC,TP,D,_,P,L,Da)) :- adjudicataria(I,Id1,Id2,TC,TP,D,Valor,P,L,Da))).


%Involucao de contrato com Prazo desconhecido
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),prazo_desconhecida) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,Id1,Id2,TC,TP,D,V,_,L,Da)) :- adjudicataria(I,Id1,Id2,TC,TP,D,V,Prazo,L,Da))).

%Involucao de contrato com Local desconhecido
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),descricao_desconhecida) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,Id1,Id2,TC,TP,D,V,P,_,Da)) :- adjudicataria(I,Id1,Id2,TC,TP,D,V,P,Local,Da))).

%Involucao de contrato com Data desconhecido
involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),descricao_desconhecida) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data),p),
    remocao((excecao(adjudicataria(I,Id1,Id2,TC,TP,D,V,P,L,_)) :- adjudicataria(I,Id1,Id2,TC,TP,D,V,P,L,Data))).







% INVOLUCAO CONHECIMENTO IMPRECISO 

%VALOR IMPRECISO
involucao( Termo , i) :-
    solucoes( Invariante,-(excecao(Termo))::Invariante,Lista ),
    remocao( excecao(Termo) ),
    teste( Lista ).



%INTERVALO DE VALORES

%ADJUDICANTE (nif impreciso)
involucao( adjudicante(Id,Nome,Nif,Morada) , i, Inf, Sup) :-
    remocao((excecao(adjudicante(Id,Nome,Nif,Morada)) :- Nif >= Inf,Nif =< Sup)).

%ADJUCATARIA (nif impreciso)
involucao( adjudicataria(Id,Nome,Nif,Morada) , i, Inf, Sup) :-
    remocao((excecao(adjudicataria(Id,Nome,Nif,Morada)) :- Nif >= Inf,Nif =< Sup)).

%CONTRATO (IdAd impreciso)
involucao( contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data) , i, Inf, Sup) :-
    remocao((excecao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data)) :- IdAd >= Inf,IdAd =< Sup)). 

%CONTRATO (IdAda impreciso)
involucao( contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data) , i, Inf, Sup) :-
    remocao((excecao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data)) :- IdAda >= Inf,IdAda =< Sup)). 

%CONTRATO (valor impreciso)
involucao( contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data) , i, Inf, Sup) :-
    remocao((excecao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data)) :- Valor >= Inf,Valor =< Sup)).

%CONTRATO (prazo impreciso)
involucao( contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data) , i, Inf, Sup) :-
    remocao((excecao(contrato(IdC,IdAd,IdAda,TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data)) :- Prazo >= Inf,Prazo =< Sup)). 






% INVOLUCAO CONHECIMENTO INTERDITO

%ADJUDICANTE

%Involucao de adjudicante com nome impossivel
involucao(adjudicante(Id,Nome,Nif,Morada),nome_impossivel) :-
    involucao(adjudicante(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicante(I,_,Ni,M)):-adjudicante(I,Nome,Ni,M))),
    remocao((nulo(Nome))),
    remocao(( +adjudicante(I,_,Ni,M) :: (solucoes((I, _, Ni, M ),(adjudicante(Id, Nome, Nif, Morada ),nao(nulo(Nome))),R),comprimento(R,0)) )).

%Involucao de adjudicante com nif impossivel
involucao(adjudicante(Id,Nome,Nif,Morada),nif_impossivel) :-
    involucao(adjudicante(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicante(I,N,_,M)):-adjudicante(I,N,Nif,M))),
    remocao((nulo(Nif))),
    remocao(( +adjudicante(I,N,_,M) :: (solucoes((I, N, _, M ),(adjudicante(Id, Nome, Nif, Morada ),nao(nulo(Nif))),R),comprimento(R,0)) )).

%Involucao de adjudicante com morada impossivel
involucao(adjudicante(Id,Nome,Nif,Morada),morada_impossivel) :-
    involucao(adjudicante(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicante(I,N,Ni,_)):-adjudicante(I,N,Ni,Morada))),
    remocao((nulo(Morada))),
    remocao(( +adjudicante(I,N,Ni,_) :: (solucoes((I, N, Ni, _ ),(adjudicante(Id, Nome, Nif, Morada ),nao(nulo(Morada))),R),comprimento(R,0)) )).




%ADJUDICATARIA

%Involucao de adjudicataria com nome impossivel
involucao(adjudicataria(Id,Nome,Nif,Morada),nome_impossivel) :-
    involucao(adjudicataria(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicataria(I,_,Ni,M)):-adjudicataria(I,Nome,Ni,M))),
    remocao((nulo(Nome))),
    remocao(( +adjudicataria(I,_,Ni,M) :: (solucoes((I, _, Ni, M ),(adjudicataria(Id, Nome, Nif, Morada ),nao(nulo(Nome))),R),comprimento(R,0)) )).

%Involucao de adjudicataria com nif impossivel
involucao(adjudicataria(Id,Nome,Nif,Morada),nif_impossivel) :-
    involucao(adjudicataria(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicataria(I,N,_,M)):-adjudicataria(I,N,Nif,M))),
    remocao((nulo(Nif))),
    remocao(( +adjudicataria(I,N,_,M) :: (solucoes((I, N, _, M ),(adjudicataria(Id, Nome, Nif, Morada ),nao(nulo(Nif))),R),comprimento(R,0)) )).

%Involucao de adjudicataria com morada impossivel
involucao(adjudicataria(Id,Nome,Nif,Morada),morada_impossivel) :-
    involucao(adjudicataria(Id,Nome,Nif,Morada),p),
    remocao((excecao(adjudicataria(I,N,Ni,_)):-adjudicataria(I,N,Ni,Morada))),
    remocao((nulo(Morada))),
    remocao(( +adjudicataria(I,N,Ni,_) :: (solucoes((I, N, Ni, _ ),(adjudicataria(Id, Nome, Nif, Morada ),nao(nulo(Morada))),R),comprimento(R,0)) )).




%CONTRATO

%Involucao de contrato com tipo de contrato impossivel
involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),tipoc_impossivel) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    remocao((excecao(contrato(I,I1,I2,_,TP,D,V,P,L,Da)):-contrato(I,I1,I2,TipoContrato,TP,D,V,P,L,Da))),
    remocao((nulo(TipoContrato))),
    remocao((+contrato(I,I1,I2,_,TP,D,V,P,L,Da) :: (solucoes((I,I1,I2,_,TP,D,V,P,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(TipoContrato))),R),comprimento(R,0)) )).

%Involucao de contrato com tipo de procedimento impossivel
involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),tipop_impossivel) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    remocao((excecao(contrato(I,I1,I2,TC,_,D,V,P,L,Da)):-contrato(I,I1,I2,TC,TipoProcedimento,D,V,P,L,Da))),
    remocao((nulo(TipoProcedimento))),
    remocao((+contrato(I,I1,I2,TC,_,D,V,P,L,Da) :: (solucoes((I,I1,I2,TC,_,D,V,P,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(TipoProcedimento))),R),comprimento(R,0)) )).

%Involucao de contrato com descriçao impossivel
involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),descricao_impossivel) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    remocao((excecao(contrato(I,I1,I2,TC,TP,_,V,P,L,Da)):-contrato(I,I1,I2,TC,TP,Descricao,V,P,L,Da))),
    remocao((nulo(Descricao))),
    remocao((+contrato(I,I1,I2,TC,TP,_,V,P,L,Da) :: (solucoes((I,I1,I2,TC,TP,_,V,P,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(Descricao))),R),comprimento(R,0)) )).


%Involucao de contrato com valor impossivel
involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),valor_impossivel) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    remocao((excecao(contrato(I,I1,I2,TC,TP,D,_,P,L,Da)):-contrato(I,I1,I2,TC,TP,D,Valor,P,L,Da))),
    remocao((nulo(Valor))),
    remocao((+contrato(I,I1,I2,TC,TP,D,_,P,L,Da) :: (solucoes((I,I1,I2,TC,TP,D,_,P,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(Valor))),R),comprimento(R,0)) )).

%Involucao de contrato com prazo impossivel
involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),valor_impossivel) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    remocao((excecao(contrato(I,I1,I2,TC,TP,D,V,_,L,Da)):-contrato(I,I1,I2,TC,TP,D,V,Prazo,L,Da))),
    remocao((nulo(Prazo))),
    remocao((+contrato(I,I1,I2,TC,TP,D,V,_,L,Da) :: (solucoes((I,I1,I2,TC,TP,D,V,_,L,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(Prazo))),R),comprimento(R,0)) )).


%Involucao de contrato com local impossivel
involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),valor_impossivel) :-
    involucao(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),p),
    remocao((excecao(contrato(I,I1,I2,TC,TP,D,V,P,_,Da)):-contrato(I,I1,I2,TC,TP,D,V,P,Local,Da))),
    remocao((nulo(Local))),
    remocao((+contrato(I,I1,I2,TC,TP,D,V,P,_,Da) :: (solucoes((I,I1,I2,TC,TP,D,V,P,_,Da),(contrato(IdC,IdAd,IdAda,TipoContrato,TipoProcedimento,Descricao,Valor,Prazo,Local,Data),nao(nulo(Local))),R),comprimento(R,0)) )).

