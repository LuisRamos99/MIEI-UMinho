import java.util.HashSet;

public interface ICatalogoClientes {
    void addCatClientes(String cliente);
    boolean existe(String s);
    HashSet<String> getCatCli();
    int size();
}
