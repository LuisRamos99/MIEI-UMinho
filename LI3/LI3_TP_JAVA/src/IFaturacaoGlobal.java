
public interface IFaturacaoGlobal {
    double faturacaoMesFilial(int mes, int filial);
    int vendasMes(int mes);
    void insereProdutoPorMes(Venda v);
    boolean prodFoiComprado(String prod);
    Fatura getProdMes(String prod, int m);
    ParCodProdQuantComp totalComprado(String prod);
}
