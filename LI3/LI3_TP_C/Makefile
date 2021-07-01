CC = gcc
CFLAGS = -Wall -O2 -pedantic 

all: Projeto

Projeto : Arvores.o CatClientes.o CatProdutos.o Faturacao.o Filial.o ListaStrings.o main.o
	$(CC) -Wall -O2 -pedantic Arvores.o CatClientes.o CatProdutos.o Faturacao.o Filial.o ListaStrings.o main.o -o Projeto

Arvores.o: Arvores.c Arvores.h
	$(CC) -c $(CFLAGS) Arvores.c

CatClientes.o: Arvores.h CatClientes.c CatClientes.h
	$(CC) -c $(CFLAGS) CatClientes.c

CatProdutos.o: Arvores.h CatProdutos.c CatProdutos.h
	$(CC) -c $(CFLAGS) CatProdutos.c

Faturacao.o: Arvores.h Faturacao.c Faturacao.h
	$(CC) -c $(CFLAGS) Faturacao.c

Filial.o: Arvores.h Filial.c Filial.h
	$(CC) -c $(CFLAGS) Filial.c

ListaStrings.o: Arvores.h ListaStrings.c ListaStrings.h
	$(CC) -c $(CFLAGS) ListaStrings.c

main.o: Arvores.h CatClientes.h CatProdutos.h  main.c
	$(CC) -c $(CFLAGS) main.c

clean:
	rm -rf *.o