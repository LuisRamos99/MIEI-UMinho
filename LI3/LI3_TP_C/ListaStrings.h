#ifndef ListaStrings_h 
#define ListaStrings_h

#define PSIZE 20


/*ESTRUTURAS*/
typedef struct ListaProds *ListP;
typedef struct duasList *DListP;


/*FUNCOES REMOVE*/
void removeListP (ListP lp);


/*FUNCOES GETS E SETS*/
ListP setIndex (ListP p, int x);
int getContador (ListP lista);
char* getCodigo (ListP cod, int n);
ListP getListaP(DListP lista);
ListP getListaN(DListP lista);


/*FUNCOES LISTP*/
ListP inicializa_ListP (int tam);
ListP insereListP (ListP lista, Produto p);
ListP percorreCatalogoP (ListP lista, AVL a);
ListP leituraProdsLetra (Cat_Produtos cp, char letra);
ListP insereListPV (ListP lista, ProdVenda p);
ListP insereListC (ListP lista, Clis p);
ListP percorreFilial (ListP lista, AVL a);
ListP leituraCliFilial (Filial f);
ListP insereNaList (ListP lista, Prods p);
ListP percorreAVLC (ListP lista, AVL a);
ListP produtosMaisQuantidade (AVL a, int tam);


/* FUNCOES RELATIVAS QUERY 4 */
ListP naoCompradoAux (ListP lp, AVL a);
ListP listaNaoComprados(Cat_Produtos cp);
ListP naoCompFilialAux (ListP lp, AVL a, int filial);
ListP naoCompFilial (Cat_Produtos cp, int filial);


/* FUNCOES RELATIVAS QUERY 9 */
DListP inicializa_DListP(int tam);
ListP insereListCertaAux (ListP lista, Clis p);
DListP insereListCerta (DListP lista, Clis p, char* prod, int filial);
DListP preencheAsListas (Filial f, char* prod, int filial);
DListP percorreFilialToda(DListP lista, AVL a, char* prod, int filial);


/*NAVEGADOR*/
int firstPageP (ListP lp);
int nextPageP (ListP lp);
int prevPageP (ListP lp);
int lastPageP (ListP lp);
void imprimeLista (ListP lp);

#endif
