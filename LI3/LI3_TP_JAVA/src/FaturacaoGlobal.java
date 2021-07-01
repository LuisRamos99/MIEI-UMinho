import java.io.Serializable;
import java.util.*;

/**
 * Classe com a informação dos meses.
 */
public class FaturacaoGlobal implements Serializable,IFaturacaoGlobal {

    /**
     * Variáveis
     */
    private HashMap<Integer, Faturacao> produtosPorMes;

    /**
     * Construtor vazio
     */
    public FaturacaoGlobal(){produtosPorMes = new HashMap<>(12,1);}

    /**
     * Construtor por cópia
     * @param f Faturacao de um mes
     */
    public FaturacaoGlobal(FaturacaoGlobal f){produtosPorMes = f.getFatGlob();}

    /**
     * Retorna a faturação global
     * @return Faturaçao Global, em HashMap
     */
    public HashMap<Integer, Faturacao> getFatGlob(){
        HashMap<Integer, Faturacao> aux = new HashMap<>(12,1);
        produtosPorMes.forEach((a,b)->aux.put(a,b.clone()));
        return aux;
    }

    /**
     * Retorna a faturação por mes e filial
     * @param filial Filial
     * @param mes Mes
     * @return Faturacao por mes e filial, em Double
     */
    public double faturacaoMesFilial(int mes, int filial){
        HashMap<String, Fatura> map =  produtosPorMes.get(mes).getFaturacao();
        return map.values().stream()
                .mapToDouble(fat -> fat.getReceitas(filial))
                .sum();
    }


    /**
     * Retorna o numero de vendas por mes
     * @param mes Mes
     * @return Numero de vendas por mes, em Integer
     */
    public int vendasMes(int mes) {
        HashMap<String, Fatura> map =  produtosPorMes.get(mes).getFaturacao();
        return map.values().stream()
                .mapToInt(f -> f.getNumVendasTotal())
                .sum();
    }

    /**
     * Insere produto por mes
     * @param v Venda
     */
    public void insereProdutoPorMes(Venda v){
        Integer mes = v.getMes();
        if(produtosPorMes.containsKey(mes)){
            Faturacao ivp2 = produtosPorMes.get(mes);
            ivp2.iniciarVendaProd(v);
            produtosPorMes.replace(mes,ivp2);
            }
        else {
            Faturacao ivp = new Faturacao();
            ivp.iniciarVendaProd(v);
            produtosPorMes.put(mes,ivp);
        }
    }

    /**
     * Retorna true, se o produto foi comprado, false caso contrário
     * @param prod Codigo de produto
     * @return Se foi comprado ou não, em Boolean
     */
     public boolean prodFoiComprado(String prod){
        int i=1;
        boolean b = false;
        while(i!=13 && !b){b = produtosPorMes.get(i).prodComprado(prod);i++;}
        return b;
    }

    /**
     * Retorna a fatura de um produto num determinado mes
     * @param prod Codigo de produto
     * @param m Mes
     * @return Fatura do produto, em Fatura
     */
    public Fatura getProdMes(String prod, int m){
        Fatura f = produtosPorMes.get(m).getFatProd(prod);
        if(f != null) return f.clone();
        else return null;
    }

    /**
     * Retorna a lista do valor das faturaçoes totais, por mes
     * @param filial Filial
     * @return Lista do valor das faturaçoes totais, por mes, em List
     */
    public List<Double> getFatTotalMes(int filial) {
        List <Double> j = new ArrayList<>(12);
        int k = 1;
        while(k!=13) {
            j.add(produtosPorMes.get(k++).faturacaoGlobal(filial));
        }
        return j;
    }

    /**
     * Retorna o par codigo produto/quantidade comprada
     * @param prod Codigo produto
     * @return Par Codigo produto,Quantidade comprada, em ParCodProdQuantComp
     */
    public ParCodProdQuantComp totalComprado(String prod){
        ParCodProdQuantComp pcq = new ParCodProdQuantComp(prod,0);
        int i,x=0;
        for(i=1;i<13;i++){
            Fatura f = produtosPorMes.get(i).getFatProd(prod);
            if (f!=null) {x+=f.getQuantidadeTotal();}
        }
        pcq.add(x);
        return pcq;
    }

    /**
     * Clone
     */
    public FaturacaoGlobal clone(){return new FaturacaoGlobal(this);}

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        produtosPorMes.forEach((a,b)->sb.append("Mês: ").append(a).append("\n").append(b.clone()));
        return sb.toString();
    }

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || this.getClass()!=o.getClass()) return false;
        FaturacaoGlobal f = (FaturacaoGlobal) o;
        return (produtosPorMes.equals(f.getFatGlob()));
    }

    /**
     * hashCode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{produtosPorMes});
    }
}