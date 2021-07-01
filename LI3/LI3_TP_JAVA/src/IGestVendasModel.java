import java.util.HashMap;
import java.util.List;

public interface IGestVendasModel {
    void createData();
    void setNomeFicheiro(String nome);
    UltimoFicheiro getUF();
    boolean existeProd(String codProd);
    boolean existeCli(String codCli);
    Venda linhaToVenda(String linha);
    void preencheFatFil(String vendas);
    boolean validaVenda(Venda v);
    void insereCli(String cli);
    void insereProd(String prod);
    void preencheFil(Venda v);
    void preencheFat(Venda v);
    void preencheCatCli(String nome);
    void preencheCatProds(String nome);
    int numeroVendasMes(int mes);
    void estatisticaGeral();
    int produtosDistintos(String cliente, int mes);
    double gastoTotal(String cliente, int mes);
    ListStrings prodsNaoComprados();
    ListStrings maiorQuantidade(String cliente);
    ListStrings maisComprados(int n);
    int numeroDistClientes(String codProd);
    ListStrings cliMaisCompra(int n);
    ListStrings prodMaisComp(String prod, int n);
    HashMap<Integer,Integer> clientesDistintosGlobal();
    HashMap<Integer,Integer> comprasPorMes(String cliente);
    List<Fatura> prodMensal(String prod);
    List<String> tresMaisCompFil(int filial);
    List<Double> getFatTotalMes(int filial);
}
