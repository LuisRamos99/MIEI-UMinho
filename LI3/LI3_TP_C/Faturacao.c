#include <stdio.h> 
#include <stdlib.h> 
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "Arvores.h"
#include "CatProdutos.h"
#include "CatClientes.h"
#include "Faturacao.h"

/* Estrutuas */

/* Estrutura da venda de um produto */
struct vendaP{
    int numVendas,quantidadeN,quantidadeP; /* número de vendas, quantidade N, quantidade P*/
    double receitasN,receitasP;           /* receitas N, receitas P*/
};

/* Estrutura de um produto já vendido */
struct prodV{
    char* produto; /* código do produto */
};

/* Estrutura de uma fatura */
struct fatura{
    ProdVenda prod;       /* fatura correspondente a um produto já vendido */
    VendaProd filial[3]; /* filial associada à venda de um produto */
};

/* Estutura do catálogo de faturação global */
struct cat_fatglobal{
    AVL Mes[12]; /* mês correspondente */
};




/* Funções que removem */

/* Remove a estrutura da venda de um produto, desalocando a memória correspondente */
void removeVendaProd(VendaProd p) {
    free(p);
}

/* Remove a estrutura de um produto já vendido, desalocando a memória correspondente */
void removeProdVenda(ProdVenda p) {
    free(p->produto);
    free(p);
}

/* Remove a estrutura de uma fatura, desalocando a memória correspondente */
void removeFatura(Fatura f) {
    int i;
    removeProdVenda(f->prod);
    for(i=0; i<3; i++) removeVendaProd(f->filial[i]);
    free(f);
}

/* Auxiliar que emove a AVL correspondente à faturação, desalocando a memória correspondente */
void removeAVLFat (AVL a) {
    if (a!=NULL) {
        Fatura f = (Fatura) getCod(a);
        if (f!=NULL) removeFatura(f);
        removeAVLFat(getDir(a));
        removeAVLFat(getEsq(a));
        free(a);
    }
}

/* Remove o catálogo de faturação completo, desalocando a memória correspondente */
void removeCatFat(Cat_FatGlobal fat) {
    int i;
    for(i=0; i<12; i++) removeAVLFat(fat->Mes[i]);
}




/* Funções envolvendo 'Gets' */

/* Dado um índice, obtém a AVL do catálogo de faturação correspondente ao meso */
AVL getAVLFat(Cat_FatGlobal fat, int i) {
    return fat->Mes[i];
}

/* Obtém o total faturado em cada fatura */
double getTotalFil (Fatura f) {
    double x=0;
    if (f->filial[0]!=NULL) x += (f->filial[0]->receitasN) + (f->filial[0]->receitasP);
    if (f->filial[1]!=NULL) x += (f->filial[1]->receitasN) + (f->filial[1]->receitasP);
    if (f->filial[2]!=NULL) x += (f->filial[2]->receitasN) + (f->filial[2]->receitasP);
return x;
}

/* Obtém o total faturado em cada AVL, ou seja, em cada mês */
double getTotalMes (AVL a) {
    double x=0;
    if (a!=NULL) {
    Fatura f = (Fatura) getCod(a);
    if (f!=NULL) x += getTotalFil(f);
    x += getTotalMes(getDir(a)) + getTotalMes(getEsq(a));
    }
return x;
}

/* Obtém o total faturado entre dois dados meses */
double getTotalFaturado(Cat_FatGlobal fat, int mes1, int mes2) {
    int i;
    double x=0;
    for (i=mes1; i<=mes2; i++) {
        x += getTotalMes(fat->Mes[i-1]);
    }
return x;
}

/* Obtém o número total de vendas de uma fatura */
int getNumTotalFil (Fatura f) {
    int x=0;
    if (f->filial[0]!=NULL) x+= (f->filial[0]->numVendas);
    if (f->filial[1]!=NULL) x+= (f->filial[1]->numVendas);
    if (f->filial[2]!=NULL) x+= (f->filial[2]->numVendas);
return x;
}

/* Obtém o total de vendas de uma AVL correspondente a uma fatura */
int getNumTotalMes (AVL a) {
    int x=0;
    if (a!=NULL) {
    Fatura f = (Fatura) getCod(a);
    if (f!=NULL) x += getNumTotalFil(f);
    x += getNumTotalMes(getDir(a)) + getNumTotalMes(getEsq(a));
    }
return x;
}

/* Obtém o total de vendas, dados dois meses */
int getNumTotal(Cat_FatGlobal fat, int mes1, int mes2) {
    int i,x=0;
    for (i=mes1; i<=mes2; i++) {
        x+=getNumTotalMes(fat->Mes[i-1]);
    }
return x;
}

/* Obtém o número de vendas de uma fatura, numa determinada filial */
int getNumVendaProd(Fatura f, int filial) {
    if (f->filial[filial-1]!=NULL) {return (f->filial[filial-1]->numVendas);}
    return 0;
}

/* Obtém a Quantidade N de uma fatura, numa determinada filial */
int getQuantNVendaProd(Fatura f, int filial) {
    if (f->filial[filial-1]!=NULL) {return (f->filial[filial-1]->quantidadeN);}
    return 0;
}

/* Obtém a Quantidade P de uma fatura, numa determinada filial */
int getQuantPVendaProd(Fatura f, int filial) {
    if (f->filial[filial-1]!=NULL) {return (f->filial[filial-1]->quantidadeP);}
    return 0;
}

/* Obtém a quantidade (N+P) de uma fatura */
int getQuantTotalVendaProd(Fatura f) {
    return (getQuantNVendaProd(f,1)+getQuantNVendaProd(f,2)+getQuantNVendaProd(f,3)+
            getQuantPVendaProd(f,1)+getQuantPVendaProd(f,2)+getQuantPVendaProd(f,3));
}

/* Obtém a receita N de uma fatura, numa determinada filial */
double getReceitaNVendaProd(Fatura f, int filial) {
    if (f->filial[filial-1]!=NULL) {return (f->filial[filial-1]->receitasN);}
    return 0;
}

/* Obtém a receita P de uma fatura, numa determinada filial */
double getReceitaPVendaProd(Fatura f, int filial) {
    if (f->filial[filial-1]!=NULL) {return (f->filial[filial-1]->receitasP);}
    return 0;
}

/* Procura pela fatura associada a um determinado código de produto */
Fatura getFaturaProdAux(AVL a, char* prod) {
    if (a!=NULL) {
    Fatura f = (Fatura) getCod(a);
    int comp = strcmp(f->prod->produto,prod);
    if(comp<0) f = getFaturaProdAux(getDir(a),prod);
    if(comp>0) f = getFaturaProdAux(getEsq(a),prod);
    return f;
    }
    else {
        return NULL;
    }
}

/* Obtém a fatura associada a um determinado código de produto num determinado mês */
Fatura getFaturaProd(Cat_FatGlobal fg, char* prod, int mes) {
    return (getFaturaProdAux(fg->Mes[mes-1],prod));
}

/* Retorna o código de um produto de uma ProdVenda */
char* getCodProdVenda (ProdVenda p){
    return (p->produto);
}

/* Obtém o código  associado à fatura */
ProdVenda getCodFat (Fatura p) {
    return (p->prod);
}




/* Funções necessárias à Faturação */

/* Cria um ProdVenda */
ProdVenda criaProduto(char* prod) {
    ProdVenda p = malloc(sizeof(struct prodV));
    p->produto  = strdup(prod);
    return p;
}

/* Cria uma VendaProd */
VendaProd criaVenda (char* tipo, double preco,int quantidade) {
    VendaProd v = malloc(sizeof(struct vendaP));
    if (*tipo=='N') {
        v->quantidadeN = quantidade;
        v->quantidadeP = 0;
        v->receitasP   = 0;
        v->receitasN   = preco*quantidade;
    }
    else {
        v->quantidadeN = 0;
        v->quantidadeP = quantidade;
        v->receitasP   = preco*quantidade;
        v->receitasN   = 0;
    }
    v->numVendas = 1;
    return v;
}


/* Adiciona duas 'VendaProd' */
VendaProd adicionaVenda(VendaProd v,VendaProd b){
    (v->numVendas)++;
    v->quantidadeN += b->quantidadeN;
    v->quantidadeP += b->quantidadeP;
    v->receitasP   += b->receitasP;
    v->receitasN   += b->receitasN;
    return v;
}

/* Cria uma fatura */
Fatura iniciaFat(ProdVenda p, VendaProd v, int filial) {
    int i;
    Fatura fat = malloc(sizeof(struct fatura));
    for(i=0;i<3;i++) fat->filial[i]=NULL;
    fat->prod             = p;
    fat->filial[filial-1] = v;
    return fat;
}

/* Dadas duas faturas, compara o código de produto correspondente a cada uma */ 
int comparaFatura(void* arg1, void* arg2) {
    Fatura f1 = (Fatura) arg1;
    Fatura f2 = (Fatura) arg2;
    return strcmp(f1->prod->produto,f2->prod->produto);
}

/* Adiciona duas faturas com o mesmo código de produto */
void adicionaFat(void* f1, int filial, void* v1) {
    Fatura f = (Fatura) f1;
    Fatura v = (Fatura) v1;
    if (f->filial[filial-1]==NULL) f->filial[filial-1]=v->filial[filial-1];
    else {
        f->filial[filial-1] = adicionaVenda(f->filial[filial-1],v->filial[filial-1]);
    }
}

/* Preenche o catálogo de faturação */
Cat_FatGlobal preencheCatFatGlobal(Cat_FatGlobal fg, int mes, char* prod, char* tipo, double preco, int quantidade, int filial){
    int x=0;
    ProdVenda p    = criaProduto(prod);
    VendaProd v    = criaVenda(tipo,preco,quantidade);
    Fatura f       = iniciaFat(p,v,filial);
    fg->Mes[mes-1] = insereAVL(fg->Mes[mes-1],f,&x,comparaFatura,adicionaFat,filial);
return fg;
}

/* Inicializa o catálogo de faturação */
Cat_FatGlobal inicializa_CatFatGlobal() {
    int i;
    Cat_FatGlobal f = malloc(sizeof(struct cat_fatglobal));
    for (i=0; i<12; i++) f->Mes[i] = NULL;
    return f;
}







 
