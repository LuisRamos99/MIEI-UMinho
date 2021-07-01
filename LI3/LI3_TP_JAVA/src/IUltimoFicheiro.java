public interface IUltimoFicheiro {
    String getnomeFicheiro();
    int getVendasErradas();
    int getTotalProds();
    int getTotalClis();
    int getTotalComprasZero();
    double getFaturacaoTotal();
    void setnomeFicheiro(String fl);
    void setVendasErradas(int x);
    void setfaturacaoTotal(double ft);
    void inctotalComprasZero();
    void setNumProds(int x);
    void setNumClis(int x);
    void addProduto(String codProd);
    void addCliente(String codCli);
}
