#include <stdio.h> 
#include <stdlib.h> 
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <time.h>
#include "Arvores.h"
#include "CatClientes.h"
#include "Faturacao.h"
#include "CatProdutos.h"
#include "Filial.h"
#include "ListaStrings.h"

#define X 64



/* Estruturas */

/* Estrutura SGV */
typedef struct sgv{
   int prodLidos;     /* número produtos lidos */
   int cliLidos;      /* número clientes lidos */
   int vendasLidas;   /* número vendas lidas   */
   int vendasValidas; /* número vendas válida  */
   Cat_Produtos prod; /* catálogo de produtos  */
   Cat_Clientes cli;  /* catálogo de clientes  */
   Cat_FatGlobal fat; /* catálogo da faturação global */
   Filial fili;       /* filial */ 
} *SGV; 

/* Estrutura dos files */
typedef struct files{
   char *fc;    /* ficheiro clientes */
   char *fp;    /* ficheiro produtos */
   char *fv;    /* ficheiro vendas   */
} *FILES;

/* Estrutura de uma venda */
typedef struct venda{
    char* codProd;  /* código do produto */
    char* codCli;   /* código do cliente */
    double preco;   /* preço */
    int quantidade; /* quantidade */
    char* tipo;     /* tipo */
    int mes;        /* mês */
    int filial;     /* filial */
    float total;    /* receita total */
}*Venda;




/* Funções que removem */

/* Remove a estrutura da Venda, desalocando a memória correspondente */
void removeVenda (Venda a) {
    free(a->codProd);
    free(a->codCli);
    free(a->tipo);
    free(a);
}

/* Remove a estrutura SGV, desalocando a memória correspondente */
void removeSVG (SGV sgv) {
    removeCatProd(sgv->prod);
    removeCatCli(sgv->cli);
    removeCatFat(sgv->fat);
    removeFil(sgv->fili);
    free(sgv);
}
/* Remove a estrutura dos ficheiros, desalocando a memória correspondente */
void removeFiles (FILES files) {
    free(files->fp);
    free(files->fc);
    free(files->fv);
    free(files);
}




/* Funções associadas às estruturas */

/* Recebe uma venda e divide os vários campos da mesma */
Venda mkVenda(char* linhaVendaOk)  {
    char* campos[7];
    Venda vendaAux;
     vendaAux = (Venda) malloc(sizeof(struct venda));
    int index = 0;
    char* token = strtok(linhaVendaOk," ");  
    while(!(token == NULL)) {
        campos[index] = strdup(token);
        token = strtok(NULL," ");
        index++;
    } 
    vendaAux -> codProd    = strdup(campos[0]);
    vendaAux -> codCli     = strdup(campos[4]);
    vendaAux -> preco      = atof(campos[1]);
    vendaAux -> quantidade = atoi(campos[2]);
    vendaAux -> tipo       = campos[3];
    vendaAux -> mes        = atoi(campos[5]);
    vendaAux -> filial     = atoi(campos[6]); 
    vendaAux -> total      = vendaAux->quantidade * vendaAux->preco; 
    return vendaAux;   
}

/* Inicializa a estrutura ficheiros */
FILES inicializa_F(){
  FILES fl = malloc(sizeof(struct files));
  fl->fc   = NULL;
  fl->fp   = NULL;
  fl->fv   = NULL;
  return fl;
}

/* Verifica se é 'N' ou 'P' */
int valNP (char* x){
return (*x=='N' || *x=='P');
}

/* Inicializa a estrutura SGV */
SGV inicializa_SGV () {
    SGV a   = malloc(sizeof(struct sgv));
    a->fili = iniciaFilial();
    a->cli  = inicializa_CatCli();
    a->prod = inicializa_CatProd();
    a->fat  = inicializa_CatFatGlobal();
    a->vendasLidas   = 0;
    a->vendasValidas = 0;
    a->prodLidos     = 0;
    a->cliLidos      = 0;
return a;
}

/* Valida a venda */
int validarVenda (Cat_Clientes c, Cat_Produtos p, Venda v) {
  if ((v->preco)<0 || (v->preco)>999.99 || (v->quantidade)<=0 || 
    (v->quantidade)>200 || valNP(v->tipo)==0 || 
    (v->mes)<1 || (v->mes)>12 || (v->filial)<1 || (v->filial)>3) return 0;
    if (existeCli(c,v->codCli)) return (existePro(p,v->codProd,v->filial));
    else return 0;
}

/* Lê o ficheiro vendas */
SGV lerVendas (SGV sgv, char* a) {
    char s[128];
    FILE *fp = fopen(a,"r");
    if(fp==NULL) {printf("O ficheiro %s nao existe!\n",a);exit(1);}
    while(fgets(s,128,fp)) {
        Venda v = mkVenda(s);
        (sgv->vendasLidas)++;
        if (validarVenda(sgv->cli,sgv->prod,v)) {
                (sgv->vendasValidas)++;
                sgv->fat  = preencheCatFatGlobal(sgv->fat,v->mes,v->codProd,v->tipo,v->preco,v->quantidade,v->filial);
                sgv->fili = preencheFilial(sgv->fili,v->codCli,v->codProd,v->mes,v->filial,v->tipo,v->quantidade,v->preco);
                removeVenda(v);
        }
    }
fclose(fp);
return sgv; 
}

/* Atualiza a estrutura SGV */
SGV atualiza_SGV (SGV sgv, FILES z) {
    sgv->cli  = criaTreeCli(sgv->cli,z->fc,&(sgv->cliLidos));
    sgv->prod = criaTreeProd(sgv->prod,z->fp,&(sgv->prodLidos));
    sgv       = lerVendas(sgv,z->fv);
return sgv;
}

/* Menu inicial e obtenção dos nomes dos fichieros */
FILES ficheiros(FILES files){
    char cmd[5];
    printf("---------------------------------------------------------\n");
    printf("|\t  *** SISTEMA DE GESTÃO DE VENDAS ***\t\t|\n");
    printf("---------------------------------------------------------\n");
    printf("Opção pretendida: \n\n");
    printf("1 - Inserir manualmente nomes dos ficheiros \n");
    printf("2 - Assumir por omissão \n");
    printf("------------------------------------------------------------------------------\n");

    files->fc=malloc(sizeof(char)*X);
    files->fp=malloc(sizeof(char)*X);
    files->fv=malloc(sizeof(char)*X);
    if(fgets(cmd,5,stdin)!=NULL){
      if(cmd[0]=='1') {
        printf("Insira o nome do ficheiro que contém os clientes \n");
          if(fgets(files->fc,X,stdin)!=NULL)  files->fc[strlen(files->fc)-1]='\0';
        printf("Insira o nome do ficheiro que contém os produtos \n");
          if(fgets(files->fp,X,stdin)!=NULL)  files->fp[strlen(files->fp)-1]='\0';
        printf("Insira o nome do ficheiro que contém as vendas \n");
          if(fgets(files->fv,X,stdin)!=NULL)  files->fv[strlen(files->fv)-1]='\0';
        }
       else if (cmd[0]=='2'){
            strcpy(files->fc,"Clientes");
            strcpy(files->fp,"Produtos");
            strcpy(files->fv,"Vendas_1M");
            }
            else {
                printf("\nOpção inválida!\n");
                exit(1);
            }
    }
    return files;
}


int main() {
    static int mes=0,mes2=0;
    static char *key=NULL,*letra=NULL;
    static clock_t start, end;
    static Fatura  ft;
    static SGV     sgv;
    static ListP   lp;
    static MC      caros;
    static PMQuant pmq;

    /* Comandos e respetiva interpretação */
    letra = malloc(sizeof(char)*10);
    key   = malloc(sizeof(char)*10);
    strcpy(key,"Q1");
    strcpy(letra,"a");
    while(strcmp(key,"S")!=0) {
     
    /* Query 1 */
    if (strcmp(key,"Q1")==0)  {
    if (sgv!=NULL) removeSVG(sgv);
    FILES z = inicializa_F();
    ficheiros(z);
    start = clock();
    sgv = inicializa_SGV();
    sgv = atualiza_SGV(sgv,z);
    end = clock();
    printf("\nFicheiro clientes : '%s' com %i lidos e %i válidos.\n",z->fc,sgv->cliLidos,getTotalCli(sgv->cli));
    printf("Ficheiro produtos : '%s' com %i lidos e %i válidos.\n",z->fp,sgv->prodLidos,getTotalProd(sgv->prod));
    printf("Ficheiro vendas   : '%s' com %i lidas e %i válidas.\n",z->fv,sgv->vendasLidas,sgv->vendasValidas);
    printf("CPU Time: %.3f segundos\n",((double)(end - start))/CLOCKS_PER_SEC);
    removeFiles(z);
    }


    /* Query 2 */
    if (strcmp(key,"Q2")==0)  {
        strcpy(letra,"a");
        while (letra[0]<65 || letra[0]>92) {
        printf("\nEscreva a letra que deseja : \n");
        if (fgets(letra,64,stdin)!=NULL) letra=strtok(letra,"\n");
        if (letra[0]<65 || letra[0]>90) {printf("\nA letra '%s' é inválida!\n",letra);}
        else {
            lp=leituraProdsLetra(sgv->prod,letra[0]);
            imprimeLista(lp);
            removeListP(lp);
            }
        }
    }


    /* Query 3 */
    if (strcmp(key,"Q3")==0)  {
        printf("\nEscreva o produto que deseja : \n");
        if (fgets(letra,X,stdin)!=NULL) letra=strtok(letra,"\n");
        printf("\nEscreva o mês que deseja : \n");
        if (fgets(key,X,stdin)!=NULL) mes=atoi(strtok(key,"\n"));
        ft = getFaturaProd(sgv->fat,letra,mes);
        if (ft!=NULL) {
            printf("\nPretende valores totais (t) ou por filial (f) :\n");
            if (fgets(letra,X,stdin)!=NULL) letra=strtok(letra,"\n");
            if (letra[0]=='f') {
                printf("\nFilial 1 -> Número de vendas : %d | Faturação (N) : %.4f | Faturação (P) : %.4f\n",
                getNumVendaProd(ft,1),getReceitaNVendaProd(ft,1),getReceitaPVendaProd(ft,1));
                printf("Filial 2 -> Número de vendas : %d | Faturação (N) : %.4f | Faturação (P) : %.4f\n",
                getNumVendaProd(ft,2),getReceitaNVendaProd(ft,2),getReceitaPVendaProd(ft,2));
                printf("Filial 3 -> Número de vendas : %d | Faturação (N) : %.4f | Faturação (P) : %.4f\n",
                getNumVendaProd(ft,3),getReceitaNVendaProd(ft,3),getReceitaPVendaProd(ft,3));
            }
            else if (letra[0]=='t') {
                    printf("\nNúmero de Vendas : %d | Total Faturado (N) : %.4f | Total Faturado (P) : %.4f\n",
                        getNumVendaProd(ft,1)     +getNumVendaProd(ft,2)     +getNumVendaProd(ft,3),
                        getReceitaNVendaProd(ft,1)+getReceitaNVendaProd(ft,2)+getReceitaNVendaProd(ft,3),
                        getReceitaPVendaProd(ft,1)+getReceitaPVendaProd(ft,2)+getReceitaPVendaProd(ft,3));
                 }
                 else {printf("Opção inválida!\n");}
            }
        else printf("\nO produto '%s' não tem faturação!\n",letra);
    }


    /* Query 4 */
    if (strcmp(key,"Q4")==0)  {
        while (strcmp(key,"M")!=0) {
            if (strcmp(key,"A")!=0) {
                printf("---------------------------------------------------------------------\n");
                printf("Valores Totais(T) ou Divididos por Filiais(F)\n"); 
                if(fgets(letra,X,stdin)!=NULL) key=strtok(letra,"\n"); 
            }
            if (strcmp(key,"T")==0) {
                ListP lp1=listaNaoComprados(sgv->prod);
                if(getContador(lp1)>0) {printf("%d produtos não foram comprados\n",getContador(lp1));imprimeLista(lp1);}
                else printf("Todos os produtos foram comprados\n");
                strcpy(key,"M");
            removeListP(lp1);
            }
            else if (strcmp(key,"F")==0){
                strcpy(key,"A");
                while (strcmp(key,"A")==0) {
                strcpy(key,"F");
                if(strcmp(key,"F")==0) {
                    printf("---------------------------------------------------------------------\n");
                    puts("Deseja Filial1(F1), Filial2(F2) ou Filial(F3)?");
                    if (fgets(key,X,stdin)!=NULL) key=(strtok(key,"\n"));

                    ListP lp1=naoCompFilial(sgv->prod,1);
                    ListP lp2=naoCompFilial(sgv->prod,2);
                    ListP lp3=naoCompFilial(sgv->prod,3);

                if (strcmp(key,"F1")==0){
                    if(getContador(lp1)>0) {printf("\n\t\t***Filial 1***\n");printf("%d produtos não foram comprados\n",getContador(lp1));imprimeLista(lp1);}
                    else {printf("\n\t\t*** Filial 1 ***\n");printf("Todos os produtos foram comprados\n");}
                    puts("\nAlterar filial (A)    ||   Regressar ao menu (M)");
                    if (fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");
                removeListP(lp1);
                }

                if (strcmp(key,"F2")==0){
                    if(getContador(lp2)>0 ) {printf("\n\t\t***Filial 2***\n");printf("%d produtos não foram comprados\n",getContador(lp2));imprimeLista(lp2);}
                    else {printf("\n\t\t***Filial 2***\n");printf("Todos os produtos foram comprados\n");}
                    puts("\nAlterar filial (A)    ||   Regressar ao menu (M)");
                    if (fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");
                removeListP(lp2);
                }

                if (strcmp(key,"F3")==0){
                    if(getContador(lp3)>0) {printf("\n\t\t***Filial 3***\n");printf("%d produtos não foram comprados\n",getContador(lp3));imprimeLista(lp3);}
                    else {printf("\n\t\t***Filial 3***\n");printf("Todos os produtos foram comprados\n");}
                    printf("---------------------------------------------------------------------\n");
                    puts("\nAlterar filial (A)    ||   Regressar ao menu (M)");
                    if (fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");
                removeListP(lp3);
                }
                } 
                }
            }
            if (strcmp(key,"M")!=0) printf("\nOpção inválida!\n");
        }
    }

    
    /* Query 5 */
    if (strcmp(key,"Q5")==0)  {
        lp=leituraCliFilial(sgv->fili);
        imprimeLista(lp);
        removeListP(lp);
    }


    /* Query 6 */
    if (strcmp(key,"Q6")==0)  {
        start = clock();
        printf("\nNúmero de clientes que não efetuaram compras : %i\n",(getTotalCli(sgv->cli) - clientesNcompra(sgv->fili)));
        printf("Número de produtos não comprados : %i\n", getNumTotalNVendido(sgv->prod));
        end = clock();
        printf("\nCPU Time: %.3f milissegundos\n",((double)(end - start))/CLOCKS_PER_SEC*1000);
    }


    /* Query 7 */
    if (strcmp(key,"Q7")==0)  {
        strcpy(key,"C");
        while (strcmp(key,"M")!=0) {
            if (strcmp(key,"C")==0) {
                printf("\nEscreva o cliente que deseja : \n");
                if (fgets(letra,X,stdin)!=NULL) {
                    letra=strtok(letra,"\n");
                    printf("---------------------------------------------------------------------\n");
                    start = clock();
                    prodsMesFilial(sgv->fili,letra);
                    end = clock();
                    printf("---------------------------------------------------------------------\n");
                    puts("Escolher outro cliente (C)    ||   Regressar ao menu (M)");
                    if (fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");
                }    
            } 
            else {
                printf("Opção inválida!\n");
                if (fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");              
            }
        }
        printf("\nCPU Time: %.3f milissegundos\n",((double)(end - start))/CLOCKS_PER_SEC*1000);   
    }


    /* Query 8 */
    if (strcmp(key,"Q8")==0)  {

        printf("\nIndique o primeiro mês: \n");
        if(fgets(key,X,stdin)!=NULL) mes=atoi(strtok(key,"\n"));
        printf("Indique outro mês: \n");
        if(fgets(key,X,stdin)!=NULL) mes2=atoi(strtok(key,"\n"));
        if (mes2>12 || mes>12 || mes<1 || mes2<1 || mes2<mes) printf("\nOpção inválida!\n");
        else {
            start=clock();
            printf("\nEntre o mês %d e o mês %d : \n\n",mes,mes2);
            printf("Número de vendas: %i\n", getNumTotal(sgv->fat,mes,mes2));
            printf("Total faturado: %f\n",getTotalFaturado(sgv->fat,mes,mes2));
            end=clock();
            printf("\nCPU Time: %.3f milissegundos\n",((double)(end - start))/CLOCKS_PER_SEC*1000);            
        }
    }


    /* Query 9 */
    if (strcmp(key,"Q9")==0)  {

        printf("\nEscreva o produto que deseja : \n");
        if (fgets(letra,X,stdin)!=NULL) letra=strtok(letra,"\n");
        printf("\nIndique a filial : \n");
        if(fgets(key,X,stdin)!=NULL) mes=atoi(strtok(key,"\n"));
        if (((mes<1 || mes>3) && valProduto(letra)==0)==1) printf("\nInválido!\n");
        else {
        start=clock();
        DListP lista=preencheAsListas(sgv->fili,letra,mes);
        end=clock();
        strcpy(key,"F");
        while (strcmp(key,"M")!=0) {
            if (strcmp(key,"F")==0) {
                printf("\nDeseja os clientes que compraram (N) ou (P) : \n");
                if(fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");
            }
            else {
            if (strcmp(key,"P")==0) {
                imprimeLista(getListaP(lista));
                puts("\nVer Clientes (N)    ||   Regressar ao menu (M)");
                if (fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");
            }
            else if (strcmp(key,"N")==0) {
                imprimeLista(getListaN(lista));
                puts("\nVer Clientes (P)    ||   Regressar ao menu (M)");
                if (fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");
            }
            else {
                if (strcmp(key,"M")) printf("Opção inválida!\n");
            }
        }
        }
        printf("\nCPU Time: %.3f milissegundos\n",((double)(end - start))/CLOCKS_PER_SEC*1000);
        }  
    }


    /* Query 10 */
    if (strcmp(key,"Q10")==0)  {

        printf("\nEscreva o cliente que deseja : \n");
        if (fgets(letra,X,stdin)!=NULL) letra=strtok(letra,"\n");
        printf("\nIndique o mês : \n");
        if(fgets(key,X,stdin)!=NULL) mes=atoi(strtok(key,"\n"));
        if (mes>12 || mes<1) printf("\nMês inválido!\n");
        else if (valCliente(letra)==0) printf("\nCliente inválido!\n");
             else {
                 start=clock();
                 pmq = inicializa_PMQ();
                 pmq = prodMaisQuant(pmq,sgv->fili,letra,mes);
                 lp = produtosMaisQuantidade(getAVL(pmq),getTam(pmq));
                 end = clock();
                 imprimeLista(lp);
                 removeListP(lp);
                 printf("\nCPU Time: %.3f milissegundos\n",((double)(end - start))/CLOCKS_PER_SEC*1000);  
             }
    }


    /* Query 11 */
    if (strcmp(key,"Q11")==0)  {
        printf("Quantos produtos deseja visualizar:\n");
        if(fgets(letra,128,stdin)!=NULL) mes = atoi(strtok(letra,"\n"));
        /*
        lp=NMVend(sgv->fat,mes);
        imprimeLista(lp);
        */
    }


    /* Query 12 */
    if (strcmp(key,"Q12")==0)  {
        printf("\nEscreva o cliente que deseja : \n");
        if (fgets(letra,X,stdin)!=NULL) letra=strtok(letra,"\n");
        caros = inicializa_MC(); 
        if (valCliente(letra)==0) printf("\nO Cliente '%s' não existe!\n",letra);
        else {
            start=clock();
            AVL a = getAVLClis(sgv->fili,letra); 
            Clis c = percorreAVL(a,letra); 
            if (c==NULL) {printf("\nO Cliente '%s' não efetuou compras!\n",letra);end=clock();}
            else {
                caros = preencheMC(caros,c);
                printf("\nOs 3 produtos em que o cliente '%s' mais gastou dinheiro: \n\n",letra);
                printf("%s \n",getCodProdVenda(getMaior1(caros)));
                printf("%s \n",getCodProdVenda(getMaior2(caros)));
                printf("%s \n",getCodProdVenda(getMaior3(caros)));
                end=clock();
            }
        printf("\nCPU Time: %.3f milissegundos\n",((double)(end - start))/CLOCKS_PER_SEC*1000);        
        }
    }



      /* Menu das querys */
      printf("------------------------------------------------------------------------------\n");
      printf("Indique o pretendido:\n\n");
      printf("(Q1) - Recarregar os ficheiros\n");
      printf("(Q2) - Apresentar a lista de Produtos começados pela mesma letra\n");
      printf("(Q3) - Apresentar a faturação de um Produto num determinado mês\n");
      printf("(Q4) - Apresentar a lista de Produtos que não foram comprados \n");
      printf("(Q5) - Apresentar a lista de Clientes que compraram em todas as filiais\n");
      printf("(Q6) - Apresentar o número de Produtos não comprados e de Clientes que não compraram\n");
      printf("(Q7) - Apresentar tabela do número de Produtos comprados por um determinado Cliente\n");
      printf("(Q8) - Apresentar o número de Vendas e total faturado dado um intervalo de meses\n");
      printf("(Q9) - Apresentar os Clientes que adquiriram um dado Produto numa dada filial\n");
      printf("(Q10) - Apresentar a lista de Produtos mais comprados por um Cliente por mês\n");
      printf("(Q11) - Apresentar os N Produtos mais vendidos\n");
      printf("(Q12) - Apresentar os 3 Produtos em que um dado Cliente gastou mais dinheiro\n");
      printf("\n(S) - Sair\n");
      printf("------------------------------------------------------------------------------\n");
    if (fgets(key,X,stdin)!=NULL) key=strtok(key,"\n");



    /* Mensagem de erro caso a opção introduzida seja inválida */
    if ((strcmp(key,"Q1") && strcmp(key,"Q2") && strcmp(key,"Q3") && strcmp(key,"Q4") && 
         strcmp(key,"Q5") && strcmp(key,"Q6") && strcmp(key,"Q7") && strcmp(key,"Q8") && 
         strcmp(key,"Q9") && strcmp(key,"Q10") && strcmp(key,"Q11") && strcmp(key,"Q12") && 
         strcmp(key,"S"))!=0) printf("\nOpção inválida!\n");
    }
    
    return 0;
}