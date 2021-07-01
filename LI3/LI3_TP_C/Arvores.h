#ifndef Arvores_h 
#define Arvores_h

#define E -1
#define B 0
#define D 1

/*ESTRUTURAS*/
typedef struct avl *AVL;


/*FUNCOES GETS*/
void* getCod (AVL a);
AVL getDir (AVL a);
AVL getEsq (AVL a);


/*FUNCOES AVL*/
AVL rotateL (AVL a);
AVL rotateR (AVL a);
AVL corrigeD (AVL a);
AVL corrigeE (AVL a);
AVL insereAVLE (AVL a, void* codigo, int *aumentou, int comparador(void* arg1,void* arg2), void adicionaFat(void* a, int x, void* b), int filial);
AVL insereAVLD (AVL a, void* codigo, int *aumentou, int comparador(void* arg1,void* arg2), void adicionaFat(void* a, int x, void* b), int filial);
AVL iniciaAVL (void* codigo, int *aumentou);
AVL insereAVL (AVL a, void* codigo, int *aumentou, int comparador(void* arg1,void* arg2), void adiconaFat(void* a, int x, void* b), int filial);
int existeString (AVL a, void* c,int comparador(void* arg1,void* arg2));
int existeStringP (AVL a, void* c, int comparador(void* arg1,void* arg2), void atualizar(void* a, void* x));
AVL existeProd (AVL a, void* c, int compparador(void* a,void* b), void atualizaProds(void* g, void* h));

#endif