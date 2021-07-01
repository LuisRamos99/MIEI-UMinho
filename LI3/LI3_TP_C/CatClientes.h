#ifndef CatClientes_h 
#define CatClientes_h

#define N 26 // nº de letras 
#define T 65 // nº a retirar ao valor de cada letra(ASCII)

/*ESTRUTURAS*/
typedef struct cli *Cliente;
typedef struct cat_cli *Cat_Clientes;


/*FUNCOES REMOVE*/
void removeCli(Cliente p);
void removeAVLCli(AVL a);
void removeCatCli(Cat_Clientes p);


/*FUNCOES GETS*/
char* getCodCli(Cliente c);
int getTotalCli(Cat_Clientes c);
int getNumCli(Cat_Clientes c, char letra);


/*FUNCOES DO CATALOGO DE CLIENTES*/
int myAtoi(char *str, int x);
int valCliente(char *cli);
Cliente criaCli(char* codCli);
Cat_Clientes inicializa_CatCli();
int comparaCli (void* a, void* b);
Cat_Clientes insereCli(Cat_Clientes cat, Cliente c);
Cat_Clientes criaTreeCli(Cat_Clientes c, char* a, int* x);
int existeCli (Cat_Clientes c, char* cli);

#endif