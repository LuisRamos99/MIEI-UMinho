%%%---------------------------------------------------- Invariantes ----------------------------------------------------%%%

% INVARIANTES GERAIS

% Invariante para não existir conhecimento repetido
% conhecimento positivo 
+GERAL :: (solucoes(GERAL, GERAL, N),
          comprimento(N, 1)).

% Invariante para não existir conhecimento repetido
% conhecimento negativo
+(-GERAL) :: (solucoes(GERAL, -GERAL, N),
          comprimento(N, 1)).

% Invariante para não adicionar conhecimento contraditório
% conhecimento positivo que contradiz conhecimento negativo
+GERAL :: nao(-GERAL).

% Invariante para não adicionar conhecimento contraditório
% conhecimento negativo que contradiz conhecimento positivo
+(-GERAL) :: nao(GERAL).

% Invariante que garante que não existem excecoes repetidas
+(excecao(GERAL)) :: (solucoes(GERAL, excecao(GERAL), N),
                     comprimento(N, 1)).





% FUNCOES AUXILIARES PARA OS SEGUINTES INVARIANTES
nifValido(desconhecido).
nifValido(impossivel).
nifValido(NIF) :- NIF >= 100000000,NIF =< 999999999.
valPositivo(desconhecido).
valPositivo(impossivel).
valPositivo(N) :- N >= 0.
idsValidos(ID1,ID2,ID3) :- ID1 >= 0, ID2 >= 0, ID3 >= 0.
procedimentoValido(desconhecido).
procedimentoValido(impossivel).
procedimentoValido('Ajuste direto').
procedimentoValido('Consulta previa').
procedimentoValido('Concurso publico').
condicoesValidas(TipoC,TipoP,Valor,Prazo) :- TipoC='Aquisicao de servicos',TipoP='Ajuste direto',Valor =< 5000,Prazo=<365;
                                             TipoC='Contrato de aquisicao',TipoP='Ajuste direto',Valor =< 5000,Prazo=<365;
                                             TipoC='Locacao de bens moveis',TipoP='Ajuste direto',Valor =< 5000,Prazo=<365;
                                             TipoP\='Ajuste direto'.

somaLista([],0).
somaLista([X|H],R) :- somaLista(H,M), R is M+X.

% TODAS ESTAS FUNÇÕES SERVEM PARA VALIDAR A DATA
bissexto(Ano) :- R is mod(Ano,4),R==0. 

dataValida([D,02,A]) :- A>=0, bissexto(A), D>=1, D=<29; A>=0, D>=1, D=<28.  
dataValida([D,M,A]) :- A>=0, pertence(M,[01,03,05,07,08,10,12]), D>=1, D=<31.
dataValida([D,M,A]) :- A>=0, pertence(M,[04,06,09,11]), D>=1, D=<30.  

% MESES COM 31 -> 01,03,05,07,08,10,12
% MESES COM 30 -> 04,06,09,11
% MESES COM 28/29 -> 02



% INVARIANTES PARA ADJUDICANTE 


% Invariante para garantir que o id não é repetido
% conhecimento positivo
+adjudicante(IdAd,_,_,_) :: (solucoes(IdAd,adjudicante(IdAd,_,_,_),L),
                               comprimento(L,1)).

% Invariante para garantir que o id não é repetido
% conhecimento negativo
+(-adjudicante(IdAd,_,_,_)):: (solucoes(IdAd,-adjudicante(IdAd,_,_,_),L),
                               comprimento(L,1)).

% Invariante para garantir que o nif não é repetido
% conhecimento positivo
+adjudicante(_,_,Nif,_) :: (solucoes(Nif,adjudicante(_,_,Nif,_),L),
                               comprimento(L,1)).

% Invariante para garantir que o nif não é repetido
% conhecimento negativo
+(-adjudicante(_,_,Nif,_)):: (solucoes(Nif,-adjudicante(_,_,Nif,_),L),
                               comprimento(L,1)).

% Invariante para garantir que o NIF é válido
% conhecimento positivo
+adjudicante(_,_,NIF,_) :: nifValido(NIF).

% Invariante para garantir que o NIF é válido
% conhecimento negativo
+(-adjudicante(_,_,NIF,_)) :: nifValido(NIF).

% Invariante para garantir que o id é válido
% conhecimento positivo
+adjudicante(Id,_,_,_) :: valPositivo(Id).

% Invariante para garantir que o id é válido
% conhecimento negativo
+(-adjudicante(Id,_,_,_)) :: valPositivo(Id).







% INVARIANTES PARA ADJUDICATARIA 


% Invariante para garantir que o id não é repetido
% conhecimento positivo
+adjudicataria(IdAd,_,_,_) :: (solucoes(IdAd,adjudicataria(IdAd,_,_,_),L),
                               comprimento(L,1)).

% Invariante para garantir que o id não é repetido
% conhecimento negativo
+(-adjudicataria(IdAd,_,_,_)):: (solucoes(IdAd,-adjudicataria(IdAd,_,_,_),L),
                               comprimento(L,1)).

% Invariante para garantir que o nif não é repetido
% conhecimento positivo
+adjudicataria(_,_,Nif,_) :: (solucoes(Nif,adjudicataria(_,_,Nif,_),L),
                               comprimento(L,1)).

% Invariante para garantir que o nif não é repetido
% conhecimento negativo
+(-adjudicataria(_,_,Nif,_)):: (solucoes(Nif,-adjudicataria(_,_,Nif,_),L),
                               comprimento(L,1)).

% Invariante para garantir que o NIF é válido
% conhecimento positivo
+adjudicataria(_,_,NIF,_) :: nifValido(NIF).

% Invariante para garantir que o NIF é válido
% conhecimento negativo
+(-adjudicataria(_,_,NIF,_)) :: nifValido(NIF).

% Invariante para garantir que o id é válido
% conhecimento positivo
+adjudicataria(Id,_,_,_) :: valPositivo(Id).

% Invariante para garantir que o id é válido
% conhecimento negativo
+(-adjudicataria(Id,_,_,_)) :: valPositivo(Id).





% INVARIANTES PARA CONTRATO 


% Invariante para garantir que o id não é repetido
% conhecimento positivo
+contrato(IdC,_,_,_,_,_,_,_,_,_) :: (solucoes(IdC,contrato(IdC,_,_,_,_,_,_,_,_,_),L),
                                    comprimento(L,1)).

% Invariante para garantir que o id não é repetido
% conhecimento negativo
+(-contrato(IdC,_,_,_,_,_,_,_,_,_)) :: (solucoes(IdC,-contrato(IdC,_,_,_,_,_,_,_,_,_),L),
                                       comprimento(L,1)).

% Invariante para garantir que os ids sao validos
% conhecimento positivo
+contrato(IdC,IdAd,IdAda,_,_,_,_,_,_,_) :: idsValidos(IdC,IdAd,IdAda).

% Invariante para garantir que os ids sao validos
% conhecimento negativo
+(-contrato(IdC,IdAd,IdAda,_,_,_,_,_,_,_)) :: idsValidos(IdC,IdAd,IdAda).

% Invariante para garantir que o tipo de procedimento é valido
% conhecimento positivo
+contrato(_,_,_,_,Tipo,_,_,_,_,_) :: procedimentoValido(Tipo).

% Invariante para garantir que o tipo de procedimento é valido
% conhecimento negativo
+(-contrato(_,_,_,_,Tipo,_,_,_,_,_)) :: procedimentoValido(Tipo).

% Invariante para garantir que o prazo é valido
% conhecimento positivo
+contrato(_,_,_,_,_,_,_,Prazo,_,_) :: valPositivo(Prazo).

% Invariante para garantir que o prazo é valido
% conhecimento negativo
+(-contrato(_,_,_,_,_,_,_,Prazo,_,_)) :: valPositivo(Prazo).

% Invariante para garantir que o valor é valido
% conhecimento positivo
+contrato(_,_,_,_,_,_,Valor,_,_,_) :: valPositivo(Valor).

% Invariante para garantir que o valor é valido
% conhecimento negativo
+(-contrato(_,_,_,_,_,_,Valor,_,_,_)) :: valPositivo(Valor).

% Invariante para garantir que a data é valida
% conhecimento positivo
+contrato(_,_,_,_,_,_,_,_,_,Data) :: dataValida(Data).

% Invariante para garantir que a data é valida
% conhecimento negativo
+(-contrato(_,_,_,_,_,_,_,_,_,Data)) :: dataValida(Data).

% Invariante para garantir que quando o tipo é "Ajuste direto" as condições obrigatórias sao cumpridas
% conhecimento positivo
+contrato(_,_,_,TipoC,TipoP,_,Valor,Prazo,_,_) :: condicoesValidas(TipoC,TipoP,Valor,Prazo).

% Invariante para garantir que quando o tipo é "Ajuste direto" as condições obrigatórias sao cumpridas
% conhecimento negativo
+(-contrato(_,_,_,TipoC,TipoP,_,Valor,Prazo,_,_)) :: condicoesValidas(TipoC,TipoP,Valor,Prazo).

%-------------------------------------------------------

+contrato(_,IdAd,IdAda,_,TipoProcedimento,_,V,_,_,Data) :: (solucoes((Valor),(contrato(_,IdAd,IdAda,_,TipoProcedimento,_,Valor,_,_,Da),dataDentro(Data,Da)), L),
                                                                 somaLista(L,R),
                                                                 sub(R,V,F),
                                                                 F<75000).


dataDentro([Da,Ma,Aa],[D,M,A]):- Aa=A; sum(A,1,R),Aa=R; sum(A,2,R),Aa=R,Ma=<M,Da=<D.
sum(X,Y,R) :- R is X+Y.
sub(X,Y,R) :- R is X-Y.
mul(X,Y,R) :- R is X*Y.



% Invariante para garantir que não é possível remover um adjudicante que tem mais de 2 contratos
% Conhecimento positivo
-adjudicante(Id,_,_,_) :: (solucoes(Id, contrato(_,Id,_,_,_,_,_,_,_,_), R),comprimento(R, N),N=<2).
