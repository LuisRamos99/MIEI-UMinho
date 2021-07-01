#include <stdio.h> 
#include <stdlib.h> 
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "Arvores.h"
#include "CatClientes.h"

/* Estruturas */

/* Estrutura do cliente */
struct cli {
    char* cliente;     /* código do cliente */
};

/* Estrutura do catálogo de clientes */
struct cat_cli {
    AVL ArrayLetra[N];  /* uma AVL correspondente a cada letra do alfabeto */
    int TamAVL[N];     /* tamanho das AVL's */
    int TotalCli;     /* total de clientes */
};

/* Função de conversão de uma string, a partir da x posição. 
 * myAtoi("45678",2) = 678 */
int myAtoi(char *str, int x) { 
    int res = 0;
    int i=x;
    for (;isdigit(str[i]); ++i) 
        res = res*10 + str[i] - '0'; 
    return res; 
} 



/* Funções que removem */

/* Remove um dado cliente, desalocando a memória correspondente */
void removeCli(Cliente p) {
    free(p->cliente);
}

/* Remove uma árvore de clientes, desalocando a memória correspondente */
void removeAVLCli(AVL a) {
    if (a!=NULL) {
        Cliente p = (Cliente) getCod(a);
        if (p!=NULL) removeCli(p);
        removeAVLCli(getDir(a));
        removeAVLCli(getEsq(a));
    }
}

/* Remove um catálogo de clientes, desalocando a memória correspondente */
void removeCatCli(Cat_Clientes p) {
    int i;
    for(i=0; i<N; i++) {
        removeAVLCli(p->ArrayLetra[i]);
    }
    free(p);
}




/* Funções envolvendo 'Gets' */

/* Obtém um determinado código de Cliente */
char* getCodCli(Cliente c) {
    return (c->cliente);
}

/* Obtém o total de clientes no catálogo */
int getTotalCli(Cat_Clientes c) {
    return (c->TotalCli);
}

/* Dada uma letra, retorna o número de clientes começados por essa mesma letra */
int getNumCli(Cat_Clientes c, char letra) {
    return (c->TamAVL[letra-T]);
}




/* Funções necessárias ao catálogo de clientes */

/* Valida um dado código de cliente */
int valCliente(char *cli){
    int val = myAtoi(cli,1); 
    return (isupper(cli[0]) && val >999 && val<5001); 
}

/* Cria a estrutura de um cliente */
Cliente criaCli(char* codCli) {
    Cliente c  = malloc(sizeof(struct cli));
    c->cliente = strdup(codCli);
    return c;
}

/* Inicializa a estrutura do catálogo de clientes */
Cat_Clientes inicializa_CatCli() {
    int i;
    Cat_Clientes c = malloc(sizeof(struct cat_cli));
    c->TotalCli    = 0;
    for (i=0; i<N; i++) {
        c->ArrayLetra[i] = NULL;
        c->TamAVL[i]    = 0;
    } 
    return c;
}

/* Efetua a comparação entre dois clientes */
int comparaCli (void* a, void* b ) {
    Cliente c1,c2;
    c1 = (Cliente) a;
    c2 = (Cliente) b;
    return (strcmp(c1->cliente,c2->cliente));
}

/* Necessária à 'insereAVL', apesar de não fazer nada */
void nada (void* a,int x, void* b ) {
}

/* Insere um dado cliente no catálogo de clientes */
Cat_Clientes insereCli(Cat_Clientes cat, Cliente c) {
    int x=0,y=0;
    int posicao = c->cliente[0]-T;
    cat->ArrayLetra[posicao] = insereAVL(cat->ArrayLetra[posicao],c,&x,comparaCli,nada,y);
    (cat->TotalCli)++;
    (cat->TamAVL[posicao])++;
    return cat;
}

/* Lê o ficheiro clientes e, de seguida, cria o catálogo de clientes */
Cat_Clientes criaTreeCli(Cat_Clientes cat, char* a, int* x) {
    Cliente c;
    char* s  = (char*) malloc(BUFSIZ);
    FILE *fp = fopen(a,"r");
    if (fp==NULL) {
        printf("O ficheiro %s nao existe!\n",a);
        exit(1);
    }
    while (fgets(s,BUFSIZ,fp)) {
        (*x)++;
        s = strtok(s,"\r\n");
        if (valCliente(s)) {
            c   = criaCli(s);
            cat = insereCli(cat,c);
        }
    }
    fclose(fp);
    return cat;
}

/* Verifica se um dado cliente existe */
int existeCli (Cat_Clientes c, char* cli) {
    return (existeString(c->ArrayLetra[cli[0]-T],criaCli(cli),comparaCli));
}
