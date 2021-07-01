public interface IInfVendaCli {
    InfVendaProd getInfVendaProd(String prod);
    boolean containsProd(String codProd);
    int size();
    double gastoTotal(int mes);
    double gastoGlobal();
    void iniciarVendaProd(Venda v);
}
