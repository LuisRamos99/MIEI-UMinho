import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public interface IFilial {
    InfVendaCli getInfoVendaCli(String cliente);
    int size();
    void insereProdutoPorCliente(Venda v);
    HashMap<Integer,Integer> cliCompMes();
    TreeMap<Double,String> maisFaturado();
    HashSet<String> numeroDistClientes(HashSet<String> list, String codProd);
}
