public interface IFaturacao {
    int size();
    boolean prodComprado(String prod);
    double faturacaoGlobal(int filial);
    void iniciarVendaProd(Venda v);
    Fatura getFatProd(String prod);
}
