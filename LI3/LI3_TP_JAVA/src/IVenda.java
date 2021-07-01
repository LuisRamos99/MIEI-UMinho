

public interface IVenda {
    
    String getCodProd();
    String getCodCli();
    String getTipo();
    int getQuantidade();
    int getFilial();
    int getMes(); 
    double getPreco();
        
    String toString();
    boolean equals(Object o);
}
