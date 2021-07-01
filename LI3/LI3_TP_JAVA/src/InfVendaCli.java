import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Classe com a informação do produto comprado.
 */
public class InfVendaCli implements Serializable,IInfVendaCli {

    /**
     * Variáveis
     */
    private HashMap<String,InfVendaProd> produtosComprados;

    /**
     * Construtor vazio
     */
    public InfVendaCli(){produtosComprados = new HashMap<>();}

    /**
     * Construtor por parametros
     * @param prods HashMap
     */
    public InfVendaCli(HashMap<String,InfVendaProd> prods){
        produtosComprados = new HashMap<>();
        prods.forEach((a,b)->produtosComprados.put(a,b.clone()));
    }

    /**
     * Retorna a informação da venda do produto
     * @param prod Codigo produto
     * @return Informação da venda, em InfVendaProd
     */
    public InfVendaProd getInfVendaProd(String prod){return produtosComprados.get(prod).clone();}

    /**
     * Retorna true, se o map contem o produto
     * @param codProd Codigo produto
     * @return Se contem o produto, em Boolean
     */
    public boolean containsProd(String codProd) {return produtosComprados.containsKey(codProd);}

    /**
     * Retorna o mapa com a informação da venda de cada produto
     * @return Informação de cada produto, em HashMap
     */
    public HashMap<String,InfVendaProd> getProdutosComprados(){
        HashMap<String,InfVendaProd> copia = new HashMap<>();
        produtosComprados.forEach((a,b)->copia.put(a,b.clone()));
        return copia;
    }

    /**
     * Retorna a quantidade de produtos comprado
     * @return Quantidade de produtos, em Integer
     */
    public int size() {return produtosComprados.size();}

    /**
     * Retorna a o valor total gasto em cada mes
     * @param mes Mes
     * @return Gasto total, em Double
     */
    public double gastoTotal(int mes){
        return produtosComprados.values().stream()
                .filter(fat -> fat.getMes()==mes)
                .mapToDouble(fat -> fat.getPreco()*fat.getQuantidade())
                .sum();
    }

    /**
     * Retorna o valor total gasto global
     * @return Gasto global, em Double
     */
    public double gastoGlobal(){
        return produtosComprados.values().stream()
                .mapToDouble(fat -> fat.getPreco()*fat.getQuantidade())
                .sum();
    }

    /**
     * Inicia uma venda de um produto
     * @param v Venda
     */
    public void iniciarVendaProd(Venda v){
        if(produtosComprados==null) produtosComprados = new HashMap<>();
        InfVendaProd ivp=new InfVendaProd();
        ivp.setPreco(v.getPreco());
        ivp.setQuantidade(v.getQuantidade());
        ivp.setMes(v.getMes());
        produtosComprados.put(v.getCodProd(),ivp.clone());
    }

    /**
     * Retorna a lista dos meses em que foi feito compras
     * @return Meses em que comprou, em HashSet
     */
    public HashSet<Integer> mesesComprou(){
        HashSet<Integer> hs = new HashSet<>();
        produtosComprados.forEach((a,b)->hs.add(b.getMes()));
        return hs;
    }

    /**
     * Clone
     */
    public InfVendaCli clone(){return new InfVendaCli(produtosComprados);}

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        produtosComprados.forEach((a,b)->sb.append("Produto: ").append(a).append(" | InfVendaProd: ").append(b.clone()));
        return sb.toString();
    }

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || this.getClass()!=o.getClass()) return false;
        InfVendaCli v=(InfVendaCli) o;
        return (produtosComprados.equals(v.getProdutosComprados()));
    }

}
