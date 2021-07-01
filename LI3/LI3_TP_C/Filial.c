#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "Arvores.h"
#include "CatClientes.h"
#include "CatProdutos.h"
#include "Faturacao.h"
#include "Filial.h"

/* Estruturas */

/*Estrutura Prods, que guarda a informação de um produto, a sua quantidade vendida em tipo N, 
a sua quantidade vendida em tipo P, a sua receita total, e o tipo como foi vendido (N ou P) */
struct Prods{
  ProdVenda produto;
  int qtdN;
  int qtdP;
  double receita;
  char* tipo;
};

/*Estrutura Clis, que associa a um Cliente várias AVL (divididas por mes e filial) com a sua
  informação de produtos que compraram (Prods)*/
struct Clis{
  Cliente cliente;
  AVL produtosCompraram[3][12]; //filial - mes
};

/*Estrutura Filial, que guarda as informações dos Clis em 26 AVL diferentes
  Uma Estrutura Clis é guardada numa das 26 AVL consoante a letra inicial do
  Cliente que lhe está associado*/
struct Filial{
    AVL Clis[26]; 
};

struct maisCaros{
  double gastos[3];
  ProdVenda pmaiores[3];
};


/*------------------------------------------GETS---------------------------------------*/
/*Função que recebendo uma struct Prods retorna o seu código de produto (ProdVenda)
  associado*/
AVL getProdAVL(Clis p, int filial, int mes) {
  return (p->produtosCompraram[filial-1][mes]);
}

ProdVenda getProduto(Prods p) {
  return (p->produto);
}

ProdVenda getMaior1(MC a) {
  return (a->pmaiores[0]);
}

ProdVenda getMaior2(MC a) {
  return (a->pmaiores[1]);
}

ProdVenda getMaior3(MC a) {
  return (a->pmaiores[2]);
}

/*Função que retorna o Cliente de uma estrutura Clis*/
Cliente getCli (Clis p) {
  return p->cliente;
}

/*Função que recebendo um código, retorna a AVL de Clis
  associada à primeira letra desse código*/
AVL getAVLClis(Filial f, char *letra) {
  int i= letra[0]-65;
  return f->Clis[i];
}

/*Função que recebendo uma estrutura Clis, retorna o seu
  Cliente associado*/
Cliente getClienteClis (Clis c){
    return c->cliente;
}

/*Função que recebendo uma estrutura Filial e um indice,
  retorna a AVL de Clis guardada nesse índice*/
AVL getFilialAVL(Filial f, int i) {
  return (f->Clis[i]);

}

/*Retorna o tipo (N ou P) de um Prods*/
char getTipo (Prods p) {
  return (p->tipo[0]);
}

/*Função que inicializa a estrutura MC*/
MC inicializa_MC() {
  MC a = malloc(sizeof(struct maisCaros));
  return a;
}

static MC percorreAVLProds(MC a, AVL p) {
  if (p!=NULL) {
    Prods c = (Prods) getCod(p);
    if (a->gastos[0]<(c->receita)) {
      a->gastos[2]=a->gastos[1];a->pmaiores[2]=a->pmaiores[1];
      a->gastos[1]=a->gastos[0];a->pmaiores[1]=a->pmaiores[0];
      a->gastos[0]=c->receita;a->pmaiores[0]=c->produto;
    }
    else if (a->gastos[1]<(c->receita)) {
        a->gastos[2]=a->gastos[1];a->pmaiores[2]=a->pmaiores[1];
        a->gastos[1]=c->receita;a->pmaiores[1]=c->produto;      
    }
          else if (a->gastos[2]<(c->receita)) {
            a->gastos[2]=c->receita;a->pmaiores[2]=c->produto;
          }
    a = percorreAVLProds(a,getEsq(p));
    a = percorreAVLProds(a,getDir(p));
  }
  return a;
}


MC preencheMC (MC a, Clis c) {
    for(int i=0;i<3;i++) {
      for(int j=0;j<12;j++) {
          a = percorreAVLProds(a,c->produtosCompraram[i][j]);
      }
    } 
  return a;
}

/*Função que recebendo uma letra e uma AVL de Clis, procura na árvore a estrutura Clis
  que corresponde ao código de cliente dado*/
Clis percorreAVL(AVL a, char* letra) {
  while (a!=NULL) {
    Clis c = (Clis) getCod(a);
    int x = strcmp(getCodCli(c->cliente),letra);
    if (x==0) return c;
    else if (x>0) a=getEsq(a);
         else a=getDir(a);
  }
  return NULL;
}




/*-------------------------------------------------REMOVES-----------------------------------*/

/*Função que remove uma estrutura Prods, desalocando a
  memória correspondente*/
void removeProds(Prods p) {
  removeProdVenda(p->produto);
  free(p->tipo);
  free(p);
}

/*Função que remove uma AVL de Prods, desalocando a
  memória correspondente*/
void removeAVLProds (AVL a) {
      if (a!=NULL) {
      Prods p = (Prods) getCod(a);
      if (p!=NULL) removeProds(p);
      removeAVLProds(getDir(a));
      removeAVLProds(getEsq(a));
    }
}

/*Função que remove uma estrutura Clis, desalocando a
  memória correspondente*/
void removeClis (Clis p) {
  int i,j;
    removeCli(p->cliente);
    for (i=0; i<3; i++) {
      for (j=0; j<12; j++) {
        removeAVLProds(p->produtosCompraram[i][j]);
      }
    }
  free(p);
}

/*Função que remove uma AVL da estrutura Filial, desalocando
  a memória correspondente*/
void removeAVLFil(AVL a) {
    if (a!=NULL) {
        Clis p = (Clis) getCod(a);
        if (p!=NULL) removeClis(p);
        removeAVLFil(getDir(a));
        removeAVLFil(getEsq(a));
    }
}

/*Função que remove uma estrutura Filial, desalocando a memória
  correspondente*/
void removeFil(Filial p) {
    int i;
    for(i=0; i<N; i++) {
        removeAVLFil(p->Clis[i]);
    }
    free(p);
}


/*Função que compara dois Prods (compara os códigos de ProdVenda)*/
int comparaP (void* a, void* b){
  Prods p1 = (Prods) a;
  Prods p2 = (Prods) b;
  return strcmp(getCodProdVenda(p1->produto),getCodProdVenda(p2->produto));
}

/*Função que compara dois Clis (compara os códigos de Cliente)*/
int comparaC (void* a, void* b){
  Clis c1 = (Clis) a;
  Clis c2 = (Clis) b;
  return strcmp(getCodCli(c1->cliente),getCodCli(c2->cliente));
}

/*Necessária na insereAVL, apesar de não fazer nada*/
void nada3 (void* a,int x, void* b) {}


/*Função que atualiza um Prods
  Caso numa AVL de Prods se queira adicionar um Prods que
  já existe, este tem que ser atualizado*/
void addProdsCliente (void* arg1, int x, void* arg2){
  Prods a=(Prods) arg1;
  Prods b=(Prods) arg2;
  a->qtdN+=b->qtdN;
  a->qtdP+=b->qtdP;
  a->receita+=b->receita;
}

/*Funcão auxiliar à juntaAVL
  Adiciona um Prods numa Avl*/
AVL concatTrees (AVL a, void* arg){
  Prods p=(Prods) arg;
  int x=0, z=0;
  a=insereAVL(a,p,&x,comparaP,addProdsCliente,z);
  return a;
}

/*Função que junta 2 AVL de Prods numa só
  Adiciona Prods um a um*/
AVL juntaAVL (AVL a,AVL b){
       if(!a) return b;
       if(a && b){
          a=concatTrees(a,getCod(b));
          a=juntaAVL(a,getEsq(b));
          a=juntaAVL(a,getDir(b));
        }
       return a;
}

/*Função que atualiza um Clis
  Caso numa AVL de Clis se queira adicionar um Clis que
  já existe, este tem que ser atualizado*/
void addClis (void* arg1, int x, void* arg2){
  Clis a=(Clis) arg1;
  Clis b=(Clis) arg2;
  int i,j;
  for(i=0;i<3;i++){
    for(j=0;j<12;j++){
        a->produtosCompraram[i][j]=juntaAVL(a->produtosCompraram[i][j],b->produtosCompraram[i][j]);
      }
    }
}


int existeStringEspecial (AVL a, void* c, int comparador(void* arg1,void* arg2)) {
    int x = 0;
    AVL b = a;
    while (b!=NULL) {
        x = comparador(c,(Prods) getCod(b));
        if (x==0) {
            if (getTipo((Prods) getCod(b))=='N') return 1;
            else if (getTipo((Prods) getCod(b))=='P') return 2;
        }
        else if (x<0) b = getEsq(b);
             else    b = getDir(b);
    }
    return 0;
}

Prods criaProdsSoCod(char* p) {
    Prods r=malloc(sizeof(struct Prods));
    r->produto=criaProduto(p);
    return r;
}

/*Função que recebendo um código de produto, uma quantidade, um
  tipo e um preco, cria um Prods de acordo com os estes valores*/
Prods criaProds(char* p, int qtd, char* tipo, double preco){
    Prods r=malloc(sizeof(struct Prods));
    r->produto=criaProduto(p);
    if (*tipo=='N') {
      r->qtdN=qtd;
      r->qtdP=0;
    }
    else {
      r->qtdP=qtd; 
      r->qtdN=0;
    }
    r->receita=qtd*preco;
    r->tipo=strdup(tipo);
    return r;
}

/*Função que recebendo um cliente, um Prods, um mes e uma filial, cria
  um Clis de acordo com estes valores*/
Clis criaClis(Cliente c, Prods a, int mes, int fil){
    Clis r = malloc(sizeof(struct Clis));
    int i,j, x=0; //z=0;
    for(i=0;i<3;i++){
        for(j=0;j<12;j++){
            r->produtosCompraram[i][j]=NULL;
    }
  }
  r->cliente=c;
  r->produtosCompraram[fil-1][mes-1]=iniciaAVL(a,&x);

    return r;
}

/*Função que inicia uma estrutura Filial vazia*/
Filial iniciaFilial (){
    Filial f=malloc(sizeof(struct Filial));
    for(int i=0; i<26; i++) f->Clis[i]=NULL;
    return f;
}

/*Função que preenche uma Filial*/
Filial preencheFilial(Filial f,char* codCli, char* codProd, int mes, int filial, char* tipo, int quantidade, int preco){
  int x=0,z=0;
  Cliente c=criaCli(codCli);
  Prods p=criaProds(codProd,quantidade,tipo,preco);
  Clis cs = criaClis(c,p,mes,filial);
  f->Clis[codCli[0]-65]=insereAVL(f->Clis[codCli[0]-65],cs,&x,comparaC,addClis,z);
  return f;
}

/*Dada uma AVL de Prods, soma as quantidades (N e P) de
  todos os Prods da árvore*/
int contaQuantidades (AVL a){
  int r=0;
  if(a){
     Prods p = (Prods) getCod(a);
     r=(p->qtdN)+(p->qtdP) +contaQuantidades(getEsq(a))+contaQuantidades(getDir(a));
   }
  return r;
}

/*Função auxiliar à prodsMesFilial*/
void search (AVL a, char* c){
  int j;
  if (a!=NULL) {
      Clis aux = (Clis) getCod(a);
      if(strcmp(getCodCli(aux->cliente),c)==0) {
        printf("\n\t\tCliente '%s'\n\n",c);
        printf("Mes\tFilial 1\tFilial 2\tFilial 3\n\n");
        for(j=0; j<12; j++) {
            printf("%d\t   %d\t\t   %d\t\t   %d\n",j+1,contaQuantidades(aux->produtosCompraram[0][j]),
            contaQuantidades(aux->produtosCompraram[1][j]),contaQuantidades(aux->produtosCompraram[2][j]));
            }
    }
    search(getDir(a),c);
    search(getEsq(a),c);
    }

}

/*Função que imprime os valores mês a mês, distinguindo por Filial,
  as quantidades compradas por um determinado cliente*/
void prodsMesFilial (Filial f, char* c)  {
  int i=c[0]-65;    
  search(f->Clis[i],c);
}

/*Função que confere se um determinado Cliente de uma estrutura
  Clis comprou em todas as Filiais*/
int comprouTodasFiliais(Clis p) {
  int j,c=0,k=0,o=0;
  for(j=0;j<12;j++) {
      if (p->produtosCompraram[0][j]!=NULL) c=1;
      }
  for(j=0;j<12;j++) {
      if (p->produtosCompraram[1][j]!=NULL) k=1;
      }
  for(j=0;j<12;j++) {
      if (p->produtosCompraram[2][j]!=NULL) o=1;
      }
  return (c*o*k);
}

/*Compara as quantidades entre dois Prods*/
int comparaQtds (void* arg1, void* arg2){
    Prods a = (Prods) arg1;
    Prods b = (Prods) arg2;
    if(a->qtdN + a->qtdP > b->qtdN + b->qtdP) return 1;
    return -1;
}

/*Conta nodos de uma AVL*/
int contaNodos(AVL a){
  int x=0;
  if(a){
    x=1+contaNodos(getDir(a))+contaNodos(getEsq(a));
  }
  return x;
}

/*Conta quantos clientes fazem compras
  Para a Q6, o resultado será a diferença entre o numero
  total de clientes e o resultado desta função*/
int clientesNcompra (Filial f){
  int x=0;
  for(int i=0; i<26;i++){
    x+=contaNodos(f->Clis[i]);
  }
  return x;
}

/*Função que atualiza um Prods
  Caso numa AVL de Prods se queira adicionar um Prods que
  já existe, este tem que ser atualizado*/
void atualizaProds(void* g, void* h) {
    Prods p1,p2;
    p1 = (Prods) g;
    p2 = (Prods) h;
    p1->qtdN+=p2->qtdN;
    p1->qtdP+=p2->qtdP;
}

struct prodMQuant {
    int tam;
    AVL avl;
};

int getTam(PMQuant p) {
  return (p->tam);
}

/*Retorna a AVL de uma estrutura PMQuant*/
AVL getAVL(PMQuant p) {
  return (p->avl);
}

/*Inicializa uma estrutura PMQuant*/
PMQuant inicializa_PMQ () {
    PMQuant pmq = malloc(sizeof(struct prodMQuant));
    pmq->avl = NULL;
    pmq->tam = 0;
    return pmq;
}



void nada4(void* h,int g, void*j) {

}

AVL preenchePMQ(AVL a, AVL new, int *tam){
    int cr=0,x=0;
    if(a!=NULL){
        new = preenchePMQ(getEsq(a),new,tam);
        new = preenchePMQ(getDir(a),new,tam);
        (*tam)++;
        if (existeString(new,getCod(a),comparaP)) {
            new = existeProd(new,getCod(a),comparaP,atualizaProds);
            (*tam)--;
        }
        new=insereAVL(new,getCod(a),&cr,comparaQtds,nada4,x);
    }
    return new;
}

PMQuant prodMaisQuant (PMQuant pmq, Filial f,char* cliente, int mes) {
    int tam = 0, i;
    Clis c = percorreAVL(getAVLClis(f,cliente),cliente);
    if (c==NULL) {printf("\nO Cliente '%s' não existe!\n",cliente);}
    else {
        for (i=0; i<3; i++) {
            pmq->avl=preenchePMQ(c->produtosCompraram[i][mes-1],pmq->avl,&tam);
            pmq->tam+=tam;
        }
    }
    return pmq;
}   
