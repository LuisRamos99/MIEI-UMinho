#ifndef Filial_h
#define Filial_h


typedef struct Prods *Prods;
typedef struct Clis *Clis;
typedef struct Filial *Filial;
typedef struct maisCaros *MC;

ProdVenda getProduto(Prods p);
Prods criaProdsSoCod(char* p);
MC inicializa_MC();

ProdVenda getMaior1(MC a);

ProdVenda getMaior2(MC a);

ProdVenda getMaior3(MC a);


AVL getProdAVL(Clis p, int filial, int mes) ;

MC preencheMC (MC a, Clis c);

Clis percorreAVL(AVL a, char* letra);

AVL getAVLClis(Filial f, char *letra);

void removeProds(Prods p);
int existeStringEspecial (AVL a, void* c, int comparador(void* arg1,void* arg2));

void removeAVLProds (AVL);

void removeClis (Clis p);

void removeAVLFil(AVL a);

char getTipo (Prods p);

void removeFil(Filial p);

Cliente getClienteClis (Clis c);

AVL getFilialAVL(Filial f, int i);

Cliente getCli (Clis p);

int comprouTodasFiliais(Clis p);

int comparaP (void* a, void* b);

int comparaC (void* a, void* b);

void nada3 (void* a,int x, void* b);

Prods juntaProds (Prods a, Prods b);

void addProdsCliente (void* arg1, int x, void* arg2);

AVL concatTrees (AVL a, void* arg);

AVL juntaAVL (AVL a,AVL b);

Clis total (Clis a, Clis b);

void addClis (void* arg1, int x, void* arg2);

Prods criaProds(char* p, int qtd, char* tipo, double preco);

Clis criaClis(Cliente c, Prods a, int mes, int fil);

Filial iniciaFilial();

Filial preencheFilial(Filial f,char* codCli, char* codProd, int mes, int filial, char* tipo, int quantidade, int preco);

int contaQuantidades(AVL a);

void search (AVL cls, char* c);

void prodsMesFilial (Filial f, char* c);

int comparaQtds (void* arg1, void* arg2);

AVL concatTrees2 (AVL a, void* arg);

AVL junta2 (AVL a,AVL b);

void printInOrder (AVL a);

void maisCompradosDecAux(AVL cls,char* c, int mes,AVL prodsOrdenados);

void maisCompradosDec(Filial f,char* c, int mes);

int contaNodos(AVL a);

int clientesNcompra (Filial f);

typedef struct prodMQuant *PMQuant;

int getTam(PMQuant p);

AVL getAVL(PMQuant p);

void atualizaProds(void* g, void* h);

PMQuant inicializa_PMQ ();

void nada4(void* h,int g, void*j);

AVL preenchePMQ(AVL a, AVL new, int *tam);

PMQuant prodMaisQuant (PMQuant pmq, Filial f,char* cli, int mes);

#endif