import java.io.Serializable;
import java.util.*;

/**
 * Classe com a informação de clientes.
 */
public class Filial implements Serializable,IFilial{

    /**
     * Variáveis
     */
    private HashMap<String,InfVendaCli> filial;

    /**
     * Construtor vazio
     */
    public Filial() {filial = new HashMap<>();}

    /**
     * Construtor por cópia
     * @param f Filial
     */
    public Filial(Filial f) {filial = f.getFilial();}

    /**
     * Retorna a filial
     * @return Filial, em HashMap
     */
    public HashMap<String,InfVendaCli> getFilial(){
        HashMap<String,InfVendaCli> copia = new HashMap<>();
        filial.forEach((a,b)->copia.put(a,b.clone()));
        return copia;
    }

    /**
     * Retorna a informaçao de um determinado cliente
     * @return Infromação das compras do cliente, em InfVendaCli
     */
    public InfVendaCli getInfoVendaCli(String cliente) {return filial.get(cliente);}

    /**
     * Retorna a quantidade de clientes que efetuaram compras
     * @return Quantidade de clientes que efetuaram compras, em Integer
     */
    public int size() {return filial.size();}

    /**
     * Insere produto por cliente
     * @param v Venda
     */
    public void insereProdutoPorCliente(Venda v){
        String cliente = v.getCodCli();
        InfVendaCli tm = filial.get(cliente);
        if(tm!=null){
            tm.iniciarVendaProd(v.clone());
            filial.put(cliente, tm.clone());
        }
        else {
            InfVendaCli g = new InfVendaCli();
            g.iniciarVendaProd(v.clone());
            filial.put(cliente,g.clone());
        }
    }

    /**
     * Retorna a quantidade compras por mes
     * @return Quantidade decompras por mes, em HashMap
     */
    public HashMap<Integer,Integer> cliCompMes(){
        HashMap<Integer,Integer> hm= new HashMap<>(12);
        int i=1;
        while(i!=13){hm.put(i++,0);}
        filial.entrySet().stream().forEach((me) -> {
            me.getValue().mesesComprou().stream().forEach((k) -> {
                int x=hm.get(k);
                hm.put(k,x+1);
            });
        });
        return hm;
    }

    /**
     * Retorna o cliente e a sua faturaçao
     * @return Cliente e sua faturacao, em TreeMap
     */
    public TreeMap<Double,String> maisFaturado(){
        TreeMap<Double,String> hm = new TreeMap<>();
        filial.entrySet().forEach((me) -> hm.put(me.getValue().gastoGlobal(),me.getKey()));
        return hm;
    }

    /**
     * Retorna a lista dos clientes distintos
     * @param list Lista de clientes
     * @param codProd Codigo produto
     * @return Clientes distintos, em HashSet
     */
    public HashSet<String> numeroDistClientes(HashSet<String> list, String codProd) {
        filial.forEach((a,b)-> {if (b.containsProd(codProd)) list.add(a);});
        return list;
    }

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || this.getClass()!=o.getClass()) return false;
        Filial v=(Filial) o;
        return (filial.equals(v.getFilial()));
    }

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        filial.forEach((a,b)->sb.append("CodCliente: ").append(a).append(b.clone()));
        return sb.toString();
    }


    /**
     * Clone
     */
    public Filial clone(){
        return new Filial(this);
    }

    /**
     * hashCode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{filial});
    }
}
