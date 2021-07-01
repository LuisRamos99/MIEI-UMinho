import java.io.Serializable;
import java.util.HashMap;
import java.util.Arrays;

/**
 * Classe com a informação do produto comprado.
 */
public class Faturacao implements Serializable,IFaturacao {

    /**
     * Variáveis
     */
    private  HashMap<String, Fatura> produtosVendidos;

    /**
     * Construtor vazio
     */
    public Faturacao(){produtosVendidos = new HashMap<>();}

    /**
     * Construtor por parametros
     * @param prods HashMap
     */
    public Faturacao(HashMap<String,Fatura> prods){
        produtosVendidos = new HashMap<>();
        prods.forEach((a,b)->produtosVendidos.put(a,b.clone()));
    }

    /**
     * Retorna a quantidade de produtos comprado
     * @return Quantidade de produtos, em Integer
     */
    public int size() {return produtosVendidos.size();}

    /**
     * Retorna a faturação
     * @return Faturaçao, em HashMap
     */
    public HashMap<String, Fatura> getFaturacao(){
        HashMap<String, Fatura> aux = new HashMap<>();
        produtosVendidos.forEach((a,b)->aux.put(a,b.clone()));
        return aux;
    }

    /**
     * Retorna a faturação global numa filial
     * @param filial Filial
     * @return Faturaçao global, em Double
     */
    public double faturacaoGlobal(int filial){
        return produtosVendidos.values().stream()
                .mapToDouble(fat -> fat.getReceitas(filial))
                .sum();
    }

    /**
     * Inicia uma venda de um produto
     * @param v Venda
     */
    public void iniciarVendaProd(Venda v){
        String prod = v.getCodProd();
        if (produtosVendidos.containsKey(prod)) {
            Fatura ivp2 = produtosVendidos.get(prod);
            ivp2.addFatura(v.getQuantidade(),v.getPreco(),v.getFilial());
            produtosVendidos.replace(prod,ivp2);
        }
        else produtosVendidos.put(v.getCodProd(), new Fatura(v.getQuantidade(), v.getPreco(), v.getFilial()));
    }

    /**
     * Retorna a fatura de determinado produto
     * @param prod Codigo de produto
     * @return Fatura do produto, em Fatura
     */
    public Fatura getFatProd(String prod){
        if (produtosVendidos.get(prod)==null) return null;
        else return (new Fatura(produtosVendidos.get(prod)));
    }

    /**
     * Retorna true, se o produto foi comprado, false caso contrário
     * @param prod Codigo de produto
     * @return Se foi comprado ou não, em Boolean
     */
    public boolean prodComprado(String prod){return produtosVendidos.containsKey(prod);}

    /**
     * Clone
     */
    public Faturacao clone(){return new Faturacao(produtosVendidos);}

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        produtosVendidos.forEach((a,b)->sb.append("Produto: ").append(a).append(" -> Fatura: ").append(b.clone()));
        return sb.toString();
    }

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || this.getClass()!=o.getClass()) return false;
        Faturacao f = (Faturacao) o;
        return (produtosVendidos.equals(f.getFaturacao()));
    }

    /**
     * hashCode
     */
    public int hashCode(){return Arrays.hashCode(new Object[]{produtosVendidos});}
}
