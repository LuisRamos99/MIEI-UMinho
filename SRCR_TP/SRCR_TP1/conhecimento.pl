%%%---------------------------------------------------- Conhecimento positivo ----------------------------------------------------%%%
adjudicante(1, 'Municipio de Basto', 702675112, 'Portugal, Cabeceiras de Basto').
adjudicante(2, 'Municipio de Braga', 712675112, 'Portugal, Braga').
adjudicante(3, 'Municipio de Guimaraes', 722675112, 'Portugal, Guimaraes').
adjudicante(4, 'Municipio de Paris', 732675112, 'França, Paris').
adjudicante(5, 'Municipio de Rio', 742675112, 'Brasil, Rio de Janeiro').
adjudicante(6, 'Municipio de Lisboa', 752675112, 'Portugal, Lisboa').
adjudicante(7, 'Municipio de Madrid', 762675112, 'Espanha, Madrid').
adjudicante(8, 'Municipio de Barcelona', 772675112, 'Espanha, Barcelona').


adjudicataria(2, 'Sociedad de los Abogados, SP,RL', 712675112, 'Espanha').
adjudicataria(3, 'Societa il Avvocati, SP,RL', 732675112, 'Italia').
adjudicataria(4, 'Sociedade advocacia, SP,RL', 742675112, 'França').
adjudicataria(5, 'Lawyer Society, SP,RL', 752675112, 'Inglaterra').
adjudicataria(6, 'Sociedade Outros Advogados, SP,RL', 762675112, 'Portugal').
adjudicataria(7, 'Sociedade de los otros abogados, SP,RL', 772675112, 'Espanha').
adjudicataria(8, 'Sociedade Advogados alemaes, SP,RL', 782675112, 'Alemanha').

contrato(1, 1, 1, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Cabeceiras de Basto', [11,02,2020]).
contrato(2, 2, 2, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Braga', [10,02,2020]).
contrato(3, 3, 3, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Guimaraes', [12,02,2020]).
contrato(4, 4, 4, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Paris',[11,02,2020]).
contrato(5, 5, 5, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Rio de Janeiro', [11,12,2020]).
contrato(6, 6, 6, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Lisboa', [11,05,2020]).
contrato(7, 7, 7, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Madrid', [24,08,2020]).
contrato(8, 8, 8, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Barcelona', [12,03,2020]).
contrato(9, 1, 6, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Cabeceiras de Basto', [07,09,2020]).


%%%---------------------------------------------------- Conhecimento negativo ----------------------------------------------------%%%


-adjudicante( 9, 'Municipio Alegre', 702675102, 'Portugal, Vinhais' ).
-adjudicante( 10, 'Municipio Triste', 702675113, 'Portugal, Beja' ).

-adjudicataria( 9, 'Sociedade society, SP,RL', 712685112, 'Bulgaria' ).
-adjudicataria( 10, 'Advocacia de advogados, SP,RL', 712675110, 'Espanha' ).

-contrato( 10, 1, 2, 'Aquisicao de serviços', 'Consulta prévia', 'Assessoria juridica', 10200, 500, 'Guimaraes', '07-07-2020' ).
-contrato( 11, 2, 3, 'Aquisicao de serviços', 'Consulta prévia', 'Assessoria juridica', 20100, 400, 'Vizela', '05-05-2020' ).



%%%---------------------------------------------------- Conhecimento incerto ----------------------------------------------------%%%

excecao( adjudicante( _, Nome, NIF, Morada ) ) :- adjudicante( incerto, Nome, NIF, Morada ).
excecao( adjudicante( IdAd, _, NIF, Morada ) ) :- adjudicante( IdAd, incerto, NIF, Morada ).
excecao( adjudicante( IdAd, Nome, _, Morada ) ) :- adjudicante( IdAd, Nome, incerto, Morada ).
excecao( adjudicante( IdAd, Nome, NIF, _ ) ) :- adjudicante( IdAd, Nome, NIF, incerto ).

excecao( adjudicataria( _, Nome, NIF, Morada ) ) :- adjudicataria( incerto, Nome, NIF, Morada ).
excecao( adjudicataria( IdAda, _, NIF, Morada ) ) :- adjudicataria( IdAda, incerto, NIF, Morada ).
excecao( adjudicataria( IdAda, Nome, _, Morada ) ) :- adjudicataria( IdAda, Nome, incerto, Morada ).
excecao( adjudicataria( IdAda, Nome, NIF, _ ) ) :- adjudicataria( IdAda, Nome, NIF, incerto ).

excecao( contrato( _, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data ) ) :- contrato( incerto, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data ).
excecao( contrato( IdC, _, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data ) ) :- contrato( IdC, incerto, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data ).
excecao( contrato( IdC, IdAd, _, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data ) ) :- contrato( IdC, IdAd, incerto, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, Data ).
excecao( contrato( IdC, IdAd, IdAda, _, TipoProcedimento, Descricao, Valor, Prazo, Local, Data ) ) :- contrato( IdC, IdAd, IdAda, incerto, TipoProcedimento, Descricao, Valor, Prazo, Local, Data ).
excecao( contrato( IdC, IdAd, IdAda, TipoContrato, _, Descricao, Valor, Prazo, Local, Data ) ) :- contrato( IdC, IdAd, IdAda, TipoContrato, incerto, Descricao, Valor, Prazo, Local, Data ).
excecao( contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, _, Valor, Prazo, Local, Data ) ) :- contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, incerto, Valor, Prazo, Local, Data ).
excecao( contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, _, Prazo, Local, Data ) ) :- contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, incerto, Prazo, Local, Data ).
excecao( contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, _, Local, Data ) ) :- contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, incerto, Local, Data ).
excecao( contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, _, Data ) ) :- contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, incerto, Data ).
excecao( contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, _ ) ) :- contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, Local, incerto ).


%%%---------------------------------------------------- Conhecimento impreciso ----------------------------------------------------%%%


excecao(adjudicante(IdAd,a,b,c)) :- IdAd>=1, IdAd=<5.               
excecao(adjudicante(10, 'Municipio de AAAA', 772675117, 'Espanha, Boas')).
excecao(adjudicante(11, 'Municipio de AAAA', 772675117, 'Espanha, Boas')).


excecao(adjudicataria(id, a, B, c)) :- B>=100, B=<2500.
excecao(adjudicataria(3, 'Societa il Avvocati, SP,RL', 984524475, 'Italia')).
excecao(adjudicataria(3, 'Societa il Avvocati, SP,RL', 732675112, 'Italia')).


excecao(contrato(1,IdAd, 8, a, b, c, 15000, 600, d, e)) :- IdAd>=2, IdAd=<4. 
excecao(contrato(1, 8, 8, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Madrid', [12,03,2020])).
excecao(contrato(1, 8, 8, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, 'Barcelona', [12,03,2020])).


%%%---------------------------------------------------- Conhecimento interdito ----------------------------------------------------%%%


adjudicante( 13, coisa, 123456789, morada_imp ).
excecao( adjudicante( IdAd, Nome, NIF, _ ) ) :- adjudicante( IdAd, Nome, NIF, morada_imp ).
nulo( morada_imp ).
+adjudicante(IdAd, Nome, NIF, _ ) :: (solucoes((IdAd, Nome, NIF, _ ),(adjudicante(13, coisa, 123456789, morada_imp ),nao(nulo(morada_imp))),R),comprimento(R,0)).


adjudicataria( 14, coisa, nif_imp, 'portugal' ).
excecao( adjudicataria( IdAd, Nome, _ , Morada ) ) :- adjudicataria( IdAd, Nome, nif_imp, Morada ).
nulo( nif_imp ).
+adjudicataria(IdAd, Nome, _, Morada ) :: (solucoes((IdAd, Nome, _, Morada ),(adjudicataria(13, coisa, nif_imp, 'portugal' ),nao(nulo(nif_imp))),R),comprimento(R,0)).



contrato(12, 1, 6, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, local_imp, [07,09,2020]).
excecao( contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, _, Data ) ) :- contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, local_imp, Data ).
nulo( local_imp ).
+contrato( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, _, Data ) :: (solucoes(( IdC, IdAd, IdAda, TipoContrato, TipoProcedimento, Descricao, Valor, Prazo, _, Data ),(contrato(12, 1, 6, 'Aquisicao de servicos', 'Consulta previa', 'Assessoria juridica', 15000, 600, local_imp, [07,09,2020]),nao(nulo(local_imp))),R),comprimento(R,0)).

