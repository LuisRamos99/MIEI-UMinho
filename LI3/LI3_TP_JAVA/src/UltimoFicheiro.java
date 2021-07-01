import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;


public class UltimoFicheiro implements Serializable,IUltimoFicheiro{

    /**
     * Variaveis
     */
    private String nomeFicheiro;
    private int vendasErradas;
    private int totalProds;
    private Set<String> numProdsDif;
    private int totalClis;
    private Set<String> numClisDif;
    private int totalComprasZero;
    private double faturacaoTotal;


    /**
     * Construtor vazio
     */
    public UltimoFicheiro(){
        this.nomeFicheiro = " ";
        this.vendasErradas = 0;
        this.totalProds = 0;
        this.numProdsDif = new HashSet<>();
        this.totalClis = 0;
        this.numClisDif = new HashSet<>();
        this.totalComprasZero = 0;
        this.faturacaoTotal = 0;
    }

    /**
     * Construtor por copia
     * @param u Ultimo ficheiro
     */
    public UltimoFicheiro(UltimoFicheiro u){
        nomeFicheiro = u.getnomeFicheiro();
        vendasErradas = u.getVendasErradas();
        totalProds = u.getTotalProds();
        numProdsDif = u.getnumProdsDif();
        totalClis = u.getTotalClis();
        numClisDif = u.getnumClisDif();
        totalComprasZero = u.getTotalComprasZero();
        faturacaoTotal = u.getFaturacaoTotal();
    }

    /**
     * Retorna o nome
     * @return Nome, em String
     */
    public String getnomeFicheiro(){return nomeFicheiro;}

    /**
     * Retorna o numero de vendas erradas
     * @return Numero de vendas erradas, em Integer
     */
    public int getVendasErradas(){return vendasErradas;}

    /**
     * Retorna o numero total de produtos
     * @return Total de produtos, em Integer
     */
    public int getTotalProds() {return totalProds;}

    /**
     * Retorna o set com os produtos diferentes
     * @return Produtos diferentes, em Set
     */
    public Set<String> getnumProdsDif(){
        Set <String> copia = new HashSet<>();
        for(String str : numProdsDif) {copia.add(str);}
        return copia;
    }

    /**
     * Retorna o set com os clientes diferentes
     * @return Clientes diferentes, em Set
     */
    public Set<String> getnumClisDif() {
        Set <String> copia = new HashSet<>();
        for(String str : numClisDif) {copia.add(str);}
        return copia;
    }

    /**
     * Retorna o numero total de clientes
     * @return Total de clientes, em Integer
     */
    public int getTotalClis(){return totalClis;}

    /**
     * Retorna o numero total de compras a zero
     * @return Total de compras a zero, em Integer
     */
    public int getTotalComprasZero(){return totalComprasZero;}

    /**
     * Retorna faturação total
     * @return Faturação total, em Double
     */
    public double getFaturacaoTotal(){return faturacaoTotal;}

    public void setnomeFicheiro(String fl){nomeFicheiro = fl;}

    public void setVendasErradas(int x) {vendasErradas=x;}

    public void setfaturacaoTotal(double ft){faturacaoTotal+=ft;}

    public void inctotalComprasZero(){totalComprasZero++;}

    public void setNumProds(int x){totalProds=x;}

    public void setNumClis(int x){totalClis=x;}

    public void addProduto(String codProd) {numProdsDif.add(codProd);}

    public void addCliente(String codCli) {numClisDif.add(codCli);}

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(this == o) return true;
        if(o==null || this.getClass()!=o.getClass()) return false;
        UltimoFicheiro u = (UltimoFicheiro) o;
        return(nomeFicheiro.equals(u) && vendasErradas == u.getVendasErradas() && totalProds == u.getTotalProds()
                && numProdsDif.retainAll(u.getnumProdsDif()) && totalClis == u.getTotalClis() && totalComprasZero == u.getTotalComprasZero()
                && numClisDif.retainAll(u.getnumClisDif()) && faturacaoTotal == u.getFaturacaoTotal());
    }

    /**
     * Clone
     */
    public UltimoFicheiro clone(){
        return new UltimoFicheiro(this);
    }

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome do ficheiro: ").append(nomeFicheiro).append("\n");
        sb.append("Número total de vendas erradas: ").append(vendasErradas).append("\n");
        sb.append("Número total de produtos: ").append(totalProds).append("\n");
        sb.append("Número total de diferentes produtos comprados: ").append(numProdsDif.size()).append("\n");
        sb.append("Número total de produtos não comprados: ").append(totalProds - numProdsDif.size()).append("\n");
        sb.append("Número total de clientes: ").append(totalClis).append("\n");
        sb.append("Número total de diferentes clientes que compraram: ").append(numClisDif.size()).append("\n");
        sb.append("Número total de clientes que não compraram: ").append(totalClis - numClisDif.size()).append("\n");
        sb.append("Número de compras de valor total igual a 0.0: ").append(totalComprasZero).append("\n");
        sb.append("Faturação total: ").append(faturacaoTotal).append("\n");
        return sb.toString();
    }

    /**
     * hashCode
     */
    public int hashCode(){return nomeFicheiro.hashCode();}
}

