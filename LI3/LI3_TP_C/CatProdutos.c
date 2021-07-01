#include <stdio.h> 
#include <stdlib.h> 
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "Arvores.h"
#include "Faturacao.h"
#include "CatClientes.h"
#include "CatProdutos.h"

/* Estrutuas */

/* Estrutura do produto */
struct prod {
    char* produto;    /* código do produto */
    int filial1;     /* filial associada a cada produto */
    int filial2;
    int filial3;
};

/* Estrutura do catálogo de produtos */
struct cat_prod {
    AVL ArrayLetra[N]; /* uma AVL correspondente a cada letra do alfabeto */
    int TamAVL[N];    /* tamanho das AVL's */
    int TotalProd;   /* total de produtos */
};



/* Funções que removem */

/* Remove um dado produto, desalocando a memória correspondente */
void removeProd(Produto p) {
    free(p->produto);
    free(p);
}

/* Remove uma árvore de produtos, desalocando a memória correspondente */
void removeAVLProd(AVL a) {
    if (a!=NULL) {
        Produto p = (Produto) getCod(a);
        if (p!=NULL) removeProd(p);
        removeAVLProd(getDir(a));
        removeAVLProd(getEsq(a));
    }
}

/* Remove um catálogo de produtos, desalocando a memória correspondente */
void removeCatProd(Cat_Produtos p) {
    int i;
    for(i=0; i<N; i++) {
        removeAVLProd(p->ArrayLetra[i]);
    }
    free(p);
}




/* Funções envolvendo 'Gets' */

/* Obtém o inteiro correspondente à filial dada */
int getFilial1 (Produto p) {
    return (p->filial1);
} 

/* Obtém o inteiro correspondente à filial dada */
int getFilial2 (Produto p) {
    return (p->filial2);
} 

/* Obtém o inteiro correspondente à filial dada */
int getFilial3 (Produto p) {
    return (p->filial3);
}

/* Obtém o produto corresponente ao inteiro dado */
AVL getAVLProds(Cat_Produtos p, int i){
    return (p->ArrayLetra[i]);
}

/* Obtém a AVL do catálogo de produtos correspondente a uma dada letra */
AVL getAVLProd(Cat_Produtos p, char letra){
    return (p->ArrayLetra[letra-T]);
}

/* Recebe uma estrutura produto e devolve o respetivo código de produto */
char* getCodProd(Produto p) {
    return (p->produto);
}

/* Obtém o número total de produtos */
int getTotalProd(Cat_Produtos p) {
    return (p->TotalProd);
}

/* Dada uma letra, devolve a quantidade de produtos começados pela mesma */
int getNumProd(Cat_Produtos p, char letra) {
    return (p->TamAVL[letra-T]);
}

/* Obtém, em cada AVL, o número de produtos não vendidos */
int getNumTotalAVL (AVL a) {
    if (a==NULL) return 0;
    else {
        Produto p = (Produto) getCod(a);
        if ((p->filial1==0 && p->filial2==0 && p->filial3==0)) {
            return (1 + getNumTotalAVL(getDir(a)) + getNumTotalAVL(getEsq(a)));
        }
        else return (getNumTotalAVL(getDir(a)) + getNumTotalAVL(getEsq(a)));
    }
}

/* Obtém o número total de produtos não vendidos */
int getNumTotalNVendido(Cat_Produtos p) {
    int x=0,i;
    for(i=0; i<26; i++) {
        x += getNumTotalAVL(p->ArrayLetra[i]);
    }
    return x;
}




/* Funções necessárias ao catálogo de produtos */

/* Dado um código de produto, verifica se este é, ou não válido */
int valProduto(char *prod){
    int val = myAtoi(prod,2); 
    return (isupper(prod[0]) && isupper(prod[1]) && val >999 && val<10000); 
}

/* Cria um novo produto */
Produto criaProd(char* codProd) {
    Produto p  = malloc(sizeof(struct prod));
    p->produto = strdup(codProd);
    p->filial1 = 0;
    p->filial2 = 0;
    p->filial3 = 0;
    return p;
}
/* Recebe um código de produto e uma filial, e cria um novo produto, já com a sua filial atualizada */
Produto criaProdDois(char* codProd, int fil) {
    Produto p = malloc(sizeof(struct prod));
    p->produto = strdup(codProd);
    if (fil==1) {p->filial1=1; p->filial2=0; p->filial3=0;}
    if (fil==2) {p->filial1=0; p->filial2=1; p->filial3=0;}
    if (fil==3) {p->filial1=0; p->filial2=0; p->filial3=1;}
    return p;
}

/* Inicializa a estrutura do catálogo de produtos */
Cat_Produtos inicializa_CatProd() {
    int i;
    Cat_Produtos p = malloc(sizeof(struct cat_prod));
    p->TotalProd = 0;
    for (i=0; i<N; i++) {
        p->ArrayLetra[i] = NULL;
        p->TamAVL[i]     = 0;
    } 
    return p;
}

/* Efetua a comparação entre dois produtos */
int comparaProd (void* a,void* b ) {
    Produto p1 = (Produto) a;
    Produto p2 = (Produto) b;
    return (strcmp(p1->produto,p2->produto));
}

/* Necessária à 'insereAVL', apesar de não fazer nada */
void nada2 (void* a, int x,void* b ) {
}

/* Insere um dado produto no catálogo de produtos */
Cat_Produtos insereProd(Cat_Produtos cat, Produto p){
    int x=0, y=0, posicao = p->produto[0]-T;
    cat->ArrayLetra[posicao] = insereAVL(cat->ArrayLetra[posicao],p,&x,comparaProd,nada2,y);
    (cat->TotalProd)++;
    (cat->TamAVL[posicao])++;
    return cat;
}

/* Lê o ficheiro produtos e, de seguida, cria o catálogo de produtos */
Cat_Produtos criaTreeProd(Cat_Produtos prod, char* a, int* x) {
    Produto p;
    char* s  = (char*) malloc(BUFSIZ);
    FILE *fp = fopen(a,"r");
    if(fp==NULL) {
        printf("O ficheiro %s nao existe!\n",a);
        exit(1);
    }
    while(fgets(s,BUFSIZ,fp)) {
        (*x)++;
        s = strtok(s,"\r\n");
        if (valProduto(s)) {
            p    = criaProd(s);
            prod = insereProd(prod,p);
        }
    }
    fclose(fp);
    return prod;
}

/* Atualiza a filial de um dado produto */
void atualizaFilial (void* a, void* x) {
    Produto p1 = (Produto) a;
    Produto p2 = (Produto) x;
    p1->filial1 = (p2->filial1 || p1->filial1);
    p1->filial2 = (p2->filial2 || p1->filial2);
    p1->filial3 = (p2->filial3 || p1->filial3);
}

/* Verifica se um dado produto existe no catálogo de produtos e, caso exista, atualiza as suas filiais */
int existePro (Cat_Produtos p, char* prod, int filial) {
  int j=prod[0]-T;
  Produto pr = criaProdDois(prod,filial);
  return (existeStringP(p->ArrayLetra[j],pr,comparaProd, atualizaFilial));
}

