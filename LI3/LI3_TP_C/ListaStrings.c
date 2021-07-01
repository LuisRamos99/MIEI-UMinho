#include <stdio.h> 
#include <stdlib.h> 
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "Arvores.h"
#include "CatProdutos.h"
#include "CatClientes.h"
#include "Faturacao.h"
#include "Filial.h"
#include "ListaStrings.h"

/* Estruturas */

/* Estutura de uma ListP */
typedef struct ListaProds{
    char** listP;   /* list de strings */
    int contador;  /* número de elementos */
    int index;    /* índice da 'página' */
}*ListP;

/* Estrutura que contém duas ListP */
struct duasList{
    ListP ln;   /* ListP do tipo 'N' */
    ListP lp;  /* ListP do tipo 'P' */  
};




/* Funções que removem */

/* Remove a estrutura da ListP, desalocando a memória correspondente */
void removeListP(ListP lp){
    int i;
    for(i=0;i<lp->contador;i++)
        free(lp->listP[i]);
    free(lp->listP);
    free(lp);
}




/* Funções envolvendo 'Gets' e 'Sets' */

/* Coloca no index o valor dado */
ListP setIndex(ListP p, int x) {
    p->index = x;
return p;
}

/* Obtém o número de elementos da lista */
int getContador (ListP lista){
    return (lista->contador);
}

/* Obtém o código de uma determinada posição da lista de strings */
char* getCodigo (ListP cod, int n){
	return (cod->listP[n]);
}

/* Obtém a Listp lp */
ListP getListaP(DListP lista) {
    return lista->lp;
}

/* Obtém a Listp ln */
ListP getListaN(DListP lista) {
    return lista->ln;
}




/* Funções necessárias às estrutura listP */

/* Inicializa a ListP */
ListP inicializa_ListP (int tam) {
    ListP novo     = malloc(sizeof(struct ListaProds));
    novo->listP    = malloc(sizeof(char*)*tam);
    novo->contador = 0;
    novo->index    = 0;
    return novo;
}

/* Insere na ListP um determinado produto */
ListP insereListP (ListP lista, Produto p) {
    lista->listP[lista->contador] = malloc(strlen(getCodProd(p))+1);
    strcpy(lista->listP[lista->contador], getCodProd(p));
    (lista->contador)++;
    return lista;
}

/* Efetua uma travessia In Order de uma ávore, inserindo numa ListP */
ListP percorreCatalogoP (ListP lista, AVL a){
    if (a!=NULL) {
        lista = percorreCatalogoP(lista,getEsq(a));
        lista = insereListP(lista,(Produto) getCod(a));
        lista = percorreCatalogoP(lista,getDir(a));
    }
    return lista;
}

/* Inicia a lista, aplicando a função cima referida */
ListP leituraProdsLetra (Cat_Produtos cp, char letra){
    ListP lista = inicializa_ListP(getNumProd(cp,letra));
    lista       = percorreCatalogoP(lista, getAVLProd(cp,letra));
    return lista;
}


/* Insere na ListP um determinado ProdVenda */
ListP insereListPV (ListP lista, ProdVenda p) {
    lista->listP[lista->contador] = malloc(strlen(getCodProdVenda(p))+1);
    strcpy(lista->listP[lista->contador], getCodProdVenda(p));
    (lista->contador)++;
    return lista;
}
/* Insere na ListP uma estrutura Clis */
ListP insereListC (ListP lista, Clis p) {
    if (comprouTodasFiliais(p)==1) {
        if (lista->contador == lista->index) {
            lista->index = lista->index*2;
            lista->listP = realloc(lista->listP,sizeof(char*)*lista->index);
        }
        lista->listP[lista->contador] = malloc(strlen(getCodCli(getCli(p)))+1);
        strcpy(lista->listP[lista->contador], getCodCli(getCli(p)));
        (lista->contador)++;
    }
    return lista;
}

/* Efetua uma travessia In Order de uma ávore, inserindo numa ListP */
ListP percorreFilial(ListP lista, AVL a){
    if (a!=NULL) {
        lista = percorreFilial(lista, getEsq(a));
        lista = insereListC(lista,(Clis) getCod(a));
        lista = percorreFilial(lista, getDir(a));
    }
    return lista;
}

/* Inicializa a ListP, percorrendo toda a filial */
ListP leituraCliFilial (Filial f) {
    int x=50;
    ListP lista  = inicializa_ListP(x);
    lista->index = x;
    for (int i=0; i<26; i++) {
        lista = percorreFilial(lista, getFilialAVL(f,i));
    }
    lista->index = 0;
    return lista;
}

/* Insere um código de um produto numa ListP */
ListP insereNaList(ListP lista, Prods p){
    lista->listP[lista->contador] = malloc(strlen(getCodProdVenda(getProduto(p)))+1);
    strcpy(lista->listP[lista->contador], getCodProdVenda(getProduto(p)));
    (lista->contador)++;
    return lista;
}

/* Percorre a AVL, insere na ListP dada*/
ListP percorreAVLC(ListP lista, AVL a){
    if (a!=NULL) {
        lista = insereNaList(lista,(Prods) getCod(a));
        lista = percorreAVLC(lista, getDir(a));
        lista = percorreAVLC(lista, getEsq(a));
    } 
    return lista;
}

/* Inicializa a ListP, efetuando de seguida uma travessia In Order de uma AVL dada, recorrendo à função anterior */
ListP produtosMaisQuantidade(AVL a, int tam) {
    ListP lista = inicializa_ListP(tam);
    if (a==NULL) printf("\nO Cliente não efetuou compras neste mês!\n");
    else lista = percorreAVLC(lista,a);
    return lista;
}

/* Funções relativas à QUERY 4 */

/* Verifica numa AVL se o produto foi comprado e, caso não tenha sido, insere  numa ListP */
ListP naoCompradoAux (ListP lp,AVL a) {
    if(a!=NULL){   
    Produto p = (Produto) getCod(a); 
        lp=naoCompradoAux(lp,getEsq(a));
        if(getFilial1(p)==0 && getFilial2(p)==0 && getFilial3(p)==0) lp=insereListP(lp,p);
        lp=naoCompradoAux(lp,getDir(a));
    }
    return lp;
}

/* Aplica a função anterior para as todas as AVL's, correspondentes às letras do alfabeto */
ListP listaNaoComprados(Cat_Produtos cp){
    int i;
    int x=0;
    ListP lp=inicializa_ListP(getTotalProd(cp));
    for(i=0;i<26;i++){
        lp=naoCompradoAux(lp,getAVLProds(cp,i));
        x++;
    }
    return lp;

}

/* Verifica numa AVL se o produto foi ou não comprado, inserindo numa ListP se não tiver sido. Notar que serão criadas três listas, uma correspondente
a cada filial */
ListP naoCompFilialAux(ListP lp,AVL a,int filial){
    Produto p = (Produto) getCod(a); 
    if(a!=NULL){
        if(filial==1){  
            lp= naoCompradoAux(lp,getEsq(a));
            p= (Produto) getCod(a); 
            if(getFilial1(p)==0) lp=insereListP(lp,p); 
            lp= naoCompradoAux(lp,getDir(a));
        }
        if(filial==2){  
            lp= naoCompradoAux(lp,getEsq(a));
            p= (Produto) getCod(a); 
            if(getFilial2(p)==0) lp=insereListP(lp,p); 
            lp= naoCompradoAux(lp,getDir(a));
        }
        if(filial==3){  
            lp= naoCompradoAux(lp,getEsq(a));
            p= (Produto) getCod(a); 
            if(getFilial3(p)==0) lp=insereListP(lp,p); 
            lp= naoCompradoAux(lp,getDir(a));
        }
    }
    return lp;
}

/* Aplica a função anterior para as todas as AVL's, correspondentes às letras do alfabeto */
ListP naoCompFilial(Cat_Produtos cp,int filial){
    int i;
    ListP lp=inicializa_ListP(getTotalProd(cp));
    for( i=0;i<26;i++)
        lp=naoCompFilialAux(lp,getAVLProds(cp,i),filial);
    return lp;
}


/* Funções relativas à QUERY 9 */

/* Inicializa a DListP */
DListP inicializa_DListP(int tam) {
    DListP lista = malloc(sizeof(struct duasList));
    lista->ln=inicializa_ListP(tam);
    lista->ln->index=tam;
    lista->lp=inicializa_ListP(tam);
    lista->lp->index=tam;
return lista;
}

/* Insere um código de um dado cliente numa ListP */
ListP insereListCertaAux (ListP lista, Clis p) {
        if (lista->contador == lista->index) {
            lista->index = lista->index*2;
            lista->listP = realloc(lista->listP,sizeof(char*)*lista->index);
        }
        lista->listP[lista->contador] = malloc(strlen(getCodCli(getCli(p)))+1);
        strcpy(lista->listP[lista->contador], getCodCli(getCli(p)));
        (lista->contador)++;
    return lista;
 }

/* Usando a função de cima, verifica se o código do produto foi comprado e, caso seja, guarda na ListP correspondente ao seu tipo */
DListP insereListCerta (DListP lista, Clis p, char* prod, int filial) {
    for (int i=0; i<12; i++ ) {
        if ((existeStringEspecial(getProdAVL(p,filial,i),criaProdsSoCod(prod),comparaP))==1) 
            lista->ln=insereListCertaAux(lista->ln,p);
        else if ((existeStringEspecial(getProdAVL(p,filial,i),criaProdsSoCod(prod),comparaP))==2) 
            lista->lp=insereListCertaAux(lista->lp,p);
    }
return lista;
}

/* Inicializa a estutura DListP e preenche os seus campos */
DListP preencheAsListas (Filial f, char* prod, int filial) {
    int x=50;
    DListP lista  = inicializa_DListP(x);
    for (int i=0; i<26; i++) {
        lista = percorreFilialToda(lista, getFilialAVL(f,i), prod, filial);
    }
    lista->ln->index = 0;
    lista->lp->index = 0;
    return lista;
}

/* Perocorre uma AVL, inserindo o código do cliente na DListP */
DListP percorreFilialToda(DListP lista, AVL a, char* prod, int filial){
    if (a!=NULL) {
        lista = percorreFilialToda(lista, getEsq(a),prod,filial);
        lista = insereListCerta(lista,(Clis) getCod(a), prod, filial);
        lista = percorreFilialToda(lista, getDir(a),prod,filial);
    }
    return lista;
}


/* Funções necessárias ao Navegador */

/* Atualiza o índice para a primeira página */
int firstPageP (ListP lp) {
    int i;
    if ((lp->contador) < PSIZE) {
      for(i=0; i<lp->contador; i++)
      printf("%s\n",lp->listP[i]);
      return (lp->index);
    }
    else{
      for(i=0; i<PSIZE; i++)
      printf("%s\n", lp->listP[i]);
      return (lp->index = PSIZE);
    }
}

/* Atualiza o índice para a próxima página */
int nextPageP (ListP lp) {
    int i;
    if ((lp->index+PSIZE) < lp->contador){
        for (i=lp->index; i<lp->index+PSIZE; i++)
           printf("%s\n" ,lp->listP[i]);
           return ((lp->index)+PSIZE);
    }
    else {
        for(i=lp->index; i<lp->contador; i++)
           printf("%s\n" ,lp->listP[i]);
           return (lp->index);
    }
}

/* Atualiza o índice para a página anterior */
int prevPageP (ListP lp) {
    int i;
    if (lp->index-2*PSIZE >= 0) {
        for(i=lp->index-2*PSIZE; i<(lp->index)-PSIZE; i++)
        printf("%s\n" ,lp->listP[i]);
        return((lp->index) - PSIZE);
    }
    else return firstPageP(lp);
}

/* Atualiza o índice para a última página */
int lastPageP (ListP lp) {
    int resto,i;
    if ((lp->contador) < PSIZE) return firstPageP(lp);
    else {
         resto=((lp->contador)%PSIZE);
         if (resto==0) lp->index = (lp->contador)-PSIZE;
         else {
             lp->index = ((lp->contador)-resto);
         }
         for(i=lp->index; i<(lp->contador); i++)
         printf("%s\n" ,(lp->listP[i]));
    return ((lp->index)+PSIZE);
    }
}

/* Dada uma ListP, imprime essa mesma lista, tendo em consideração as opções pretendidas pelo utilizador */
void imprimeLista (ListP lp) {
  char* key = malloc(sizeof(char)*10);
  strcpy(key,"F");
  while (strcmp(key,"M")!=0) {
        if (strcmp(key,"N")==0)  lp = setIndex(lp, nextPageP(lp));
        if (strcmp(key,"P")==0)  lp = setIndex(lp, prevPageP(lp));
        if (strcmp(key,"L")==0)  lp = setIndex(lp, lastPageP(lp));
        if (strcmp(key,"F")==0)  lp = setIndex(lp, firstPageP(lp));
        printf("---------------------------------------------------------------------\n");
        puts("Próxima Página  (N)   ||   Página Anterior (P)\nPrimeira Página (F)   ||   Última Página   (L)");
        printf("---------------------------------------------------------------------\n");
        puts("Regressar ao menu (M)");
        if (fgets(key,10,stdin)!=NULL) key=strtok(key,"\n");
        if ((strcmp(key,"N") && strcmp(key,"P") && strcmp(key,"F") && strcmp(key,"M") && strcmp(key,"L"))!=0) printf("\nOpção inválida!\n");
      }
}
