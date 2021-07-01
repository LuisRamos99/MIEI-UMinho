#ifndef CatProdutos_h 
#define CatProdutos_h

#define K 3 // nº de filiais
#define N 26 // nº de letras 
#define T 65 // nº a retirar ao valor de cada letra(ASCII)

/*ESTRUTURAS*/
typedef struct prod *Produto;
typedef struct cat_prod *Cat_Produtos;


/*FUNCOES REMOVE*/
void removeProd(Produto p);
void removeAVLProd(AVL a);
void removeCatProd(Cat_Produtos p);


/*FUNCOES GETS*/
int getFilial1 (Produto p);
int getFilial2 (Produto p);
int getFilial3 (Produto p);
AVL getAVLProds(Cat_Produtos p, int i);
AVL getAVLProd(Cat_Produtos p, char letra);
char* getCodProd(Produto p);
int getTotalProd(Cat_Produtos p);
int getNumProd(Cat_Produtos p, char letra);
int getNumTotalAVL (AVL a);
int getNumTotalNVendido(Cat_Produtos p);


/*FUNCOES DO CATALOGO DE PRODUTOS*/
int valProduto(char *prod);
Produto criaProd(char* codProd);
Produto criaProdDois(char* codProd, int fil);
Cat_Produtos inicializa_CatProd();
int comparaProd (void* a,void* b );
void nada2 (void* a, int x,void* b );
Cat_Produtos insereProd(Cat_Produtos cat, Produto p);
Cat_Produtos criaTreeProd(Cat_Produtos p, char* a, int* x);
void atualizaFilial (void* a, void* x);
int existePro (Cat_Produtos p, char* prod, int filial);

#endif