#ifndef Faturacao_h 
#define Faturacao_h

/*ESTRUTURAS*/
typedef struct vendaP *VendaProd;
typedef struct prodV *ProdVenda;
typedef struct fatura *Fatura;
typedef struct cat_fatglobal *Cat_FatGlobal;


/*FUNCOES REMOVE*/
void removeVendaProd(VendaProd p);
void removeProdVenda(ProdVenda p);
void removeFatura(Fatura f);
void removeAVLFat (AVL a);
void removeCatFat(Cat_FatGlobal fat);


/*FUNCOES GETS*/
AVL getAVLFat(Cat_FatGlobal fat, int i);
double getTotalFil (Fatura f);
double getTotalMes (AVL a);
double getTotalFaturado(Cat_FatGlobal fat, int mes1, int mes2);
int getNumTotalFil (Fatura f);
int getNumTotalMes (AVL a);
int getNumTotal(Cat_FatGlobal fat, int mes1, int mes2);
int getNumVendaProd(Fatura f, int filial);
int getQuantNVendaProd(Fatura f, int filial);
int getQuantPVendaProd(Fatura f, int filial);
int getQuantTotalVendaProd(Fatura f);
double getReceitaNVendaProd(Fatura f, int filial);
double getReceitaPVendaProd(Fatura f, int filial);
Fatura getFaturaProdAux(AVL a, char* prod);
Fatura getFaturaProd(Cat_FatGlobal fg, char* prod, int mes);
char* getCodProdVenda (ProdVenda p);
ProdVenda getCodFat (Fatura p);


/*FUNCOES DA FATURACAO*/
ProdVenda criaProduto(char* prod);
Fatura iniciaFat(ProdVenda p, VendaProd v, int filial);
void adicionaFat(void* f1, int filial, void* v1);
int comparaFatura(void* arg1, void* arg2);
VendaProd criaVenda(char* tipo,double preco,int quantidade);
VendaProd adicionaVenda(VendaProd v,VendaProd b);
Fatura atualizaVenda (Fatura a, Fatura f);
Cat_FatGlobal inicializa_CatFatGlobal();
Cat_FatGlobal preencheCatFatGlobal(Cat_FatGlobal fg, int mes, char* prod, char* tipo, double preco, int quantidade, int filial);


#endif