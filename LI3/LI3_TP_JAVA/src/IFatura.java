public interface IFatura {
    int getQuantidadeTotal();
    double getReceitasTotal();
    int getNumVendasTotal();
    double getReceitas(int filial);
    int getNumVendas(int filial);
    void addFatura(int quant, double preco, int filial);
}
