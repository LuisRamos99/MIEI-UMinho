#include <stdio.h> 
#include <stdlib.h> 
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "Arvores.h"




/* Estrutura da AVL */
struct avl {
    void* cod;
    int bal;
    struct avl *esq,*dir;
};




/* Funções envolvendo 'Gets' */

/* Função que retona o código da struct AVL */
void* getCod (AVL a) {
    return a->cod;
}

/* Função que retorna sub-árvore direita de uma dada AVL */
AVL getDir (AVL a) {
    return a->dir;
}

/* Função que retorna sub-árvore esquerda de uma dada AVL */
AVL getEsq (AVL a) {
    return a->esq;
}




/* Funções necessárias à AVL */

/* Rotação simples da árvore para a esquerda */
AVL rotateR (AVL a) {
    AVL aux;
    if (a&&a->esq) {
        aux      = a->esq;
        a->esq   = aux->dir;
        aux->dir = a;
        a        = aux;
    }
    else return NULL;
    return a;
}

/* Rotação simples da árvore para a direita */
AVL rotateL (AVL a) {
    AVL aux;
    if (a&&a->dir) {
        aux      = a->dir;
        a->dir   = aux->esq;
        aux->esq = a;
        a        = aux;
    }
    else return NULL;
    return a;
}

/* Função que balanceia a sub-árvore da direita */
AVL corrigeD (AVL a) {
    if (a->dir->bal==D) {
        a           = rotateL(a);
        a->bal      = B;
        a->esq->bal = B;
    }
    else {
        a->dir = rotateR(a->dir);
        a = rotateL(a);
        if (a->bal==B) {
            a->esq->bal = B;
            a->dir->bal = B;
        }
        else if (a->bal==E) {
                a->esq->bal = B;
                a->dir->bal = D;
             }
        else if (a->bal==D) {
                a->esq->bal = E;
                a->dir->bal = B;
             }
    }
    a->bal = B;
    return a;
}

/* Função que balanceia a sub-árvore da esquerda */
AVL corrigeE (AVL a) {
    if (a->esq->bal==E) {
        a           = rotateR(a);
        a->bal      = B;
        a->dir->bal = B;
    }
    else {
        a->esq = rotateL(a->esq);
        a      = rotateR(a);
        if (a->bal==B) {
            a->esq->bal = B;
            a->dir->bal = B;
        }
        else if (a->bal==D) {
                a->esq->bal = E;
                a->dir->bal = B;
             }
        else if (a->bal==E) {
                a->esq->bal = B;
                a->dir->bal = D;
             }
    }
    a->bal = B;
    return a;
}

/* Função que insere um código na árvore direita */
AVL insereAVLD (AVL a, void* c, int *cresceu, int comp(void *,void*), void adicionaFat(void*,int x, void*), int filial) {
    a->dir = insereAVL(a->dir,c,cresceu,comp,adicionaFat,filial);
    if (*cresceu){
        if(a->bal==E) {
            a->bal   = B;
            *cresceu = 0;
        }
        else if (a->bal==B) {
                a->bal   = D;
                *cresceu = 1;
             }
             else if(a->bal==D) {
                    a        = corrigeD(a);
                    *cresceu = 0;
                  }
    }
    return a;
}

/* Função que insere um código na árvore esquerda */
AVL insereAVLE (AVL a, void* c, int *cresceu, int comp(void *,void*), void adicionaFat(void*,int x, void*), int filial) {
    a->esq = insereAVL(a->esq,c,cresceu,comp,adicionaFat,filial);
    if (*cresceu){
        if (a->bal==D) {
            a->bal   = B;
            *cresceu = 0;
        }
        else if(a->bal==B) {
                a->bal   = E;
                *cresceu = 1;
             }
             else if(a->bal==E) {
                    a        = corrigeE(a);
                    *cresceu = 0;
                  }
    }
    return a;
}

/* Inicializa uma AVL */
AVL iniciaAVL(void* codigo, int *aumentou) {
    AVL a=malloc(sizeof(struct avl));
    a->cod    = codigo;
    a->bal    = B;
    a->dir    = a->esq=NULL;
    *aumentou = 1;
    return a;
}

/* Insere um determinado código numa dada árvore */
AVL insereAVL (AVL a, void* codigo, int *aumentou, int comparador(void* arg1,void* arg2), void adicionaFat(void* a,int x, void* b), int filial) {
    int cmp = 0;
    if (a==NULL) a = iniciaAVL(codigo,aumentou);
    else {
        cmp = comparador(codigo,(a->cod));
        if (cmp==0) adicionaFat(a->cod,filial,codigo);
        else if (cmp>0) a = insereAVLD(a,codigo,aumentou,comparador,adicionaFat,filial);  
             else       a = insereAVLE(a,codigo,aumentou,comparador,adicionaFat,filial);
    }
    return a; 
}

/* Percorre a AVL à procura de uma dada string pelo que, caso ela exista na árvore, então é retornado o valor 1 */
int existeString (AVL a, void* c, int comparador(void* arg1,void* arg2)) {
    int x = 0;
    AVL b = a;
    while (b!=NULL) {
        x = comparador(c,b->cod);
        if (x==0) return 1;
        else if (x<0) b = b->esq;
             else    b = b->dir;
    }
    return 0;
}

/* Percorre a AVL à procura de uma dada string pelo que, caso ela exista na árvore, então é retornado o valor 1 e os seus campos são atualizados */
int existeStringP (AVL a, void* c, int comparador(void* arg1,void* arg2), void atualizar(void* a, void *x)) {
    int x = 0;
    AVL b = a;
    while (b!=NULL) {
        x = comparador(c,b->cod);
        if (x==0) {
            atualizar(b->cod,c);
            return 1;
        }
        else if (x<0) b = b->esq;
             else     b = b->dir;
    }
    return 0;
}

/* Recebe um produto e verifica a sua existência, percorrendo a AVL de produtos. Se existir, então atualiza os campos e retorna a AVL resultante */
AVL existeProd (AVL a, void* c, int comparador(void* a,void* b), void atualizaProds(void* g, void* h)) {
    int cmp;
    if(a!=NULL) {
        cmp = comparador(c,a->cod);
        if (cmp==0) atualizaProds(a->cod,c);       
        else if (cmp<0) a = existeProd(a->esq,c,comparador,atualizaProds);
             else       a = existeProd(a->dir,c,comparador,atualizaProds);
        }
    return a;
}
