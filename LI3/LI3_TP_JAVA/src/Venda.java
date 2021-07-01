import java.io.Serializable;
import java.util.Arrays;
import java.lang.String;

public class Venda implements IVenda,Serializable {

    /**
     * Variaveis
     */
    private String codCli;
    private String codProd;
    private int filial;
    private double preco;
    private int quantidade;
    private String tipo;
    private int mes;

    /**
     * Construtor vazio
     */
    public Venda() {
        this.codProd="";
        this.codCli="";
        this.tipo="";
        this.filial=0;
        this.quantidade=0;
        this.mes=0;
        this.preco=0.0;
    }

    /**
     * Construtor por copia
     * @param v Venda
     */
    public Venda(Venda v) {
        this.codProd=v.getCodProd();
        this.codCli=v.getCodCli();
        this.preco=v.getPreco();
        this.tipo=v.getTipo();
        this.mes=v.getMes();
        this.filial=v.getFilial();
        this.quantidade=v.getQuantidade();
        this.preco=getPreco();
    }

    /**
     * Construtor por parametros
     * @param prod Codigo produto
     * @param preco Preço
     * @param quant Quantidade
     * @param tipo Tipo
     * @param cli Codigo cliente
     * @param mes Mes
     * @param fil Filial
     */
    public Venda(String prod, double preco, int quant, String tipo, String cli, int mes, int fil){
        this.codProd=prod;
        this.codCli=cli;
        this.tipo=tipo;
        this.mes=mes;
        this.quantidade=quant;
        this.filial=fil;
        this.preco=preco;
    }

    /**
     * Retorna o codigo do produto
     * @return Codigo produto, em String
     */
    public String getCodProd(){
        return this.codProd;
    }

    /**
     * Retorna o codigo do cliente
     * @return Codigo cliente, em String
     */
    public String getCodCli(){
        return this.codCli;
    }

    /**
     * Retorna o tipo
     * @return Tipo, em String
     */
    public String getTipo(){
        return this.tipo;
    }

    /**
     * Retorna o mes
     * @return Mes, em Integer
     */
    public int getMes(){
        return this.mes;
    }

    /**
     * Retorna a quantidade
     * @return Quantidade, em Integer
     */
    public int getQuantidade(){
        return this.quantidade;
    }

    /**
     * Retorna a filial
     * @return Filial, em Integer
     */
    public int getFilial(){
        return this.filial;
    }

    /**
     * Retorna o preço
     * @return Preço, em Double
     */
    public double getPreco(){
        return this.preco;
    }

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("CodProd: ").append(this.codProd).append("; ");
        sb.append("CodCli: ").append(this.codCli).append("; ");
        sb.append("Tipo: ").append(this.tipo).append("; ");
        sb.append("Mes: ").append(this.mes).append("; ");
        sb.append("Preco: ").append(this.preco).append("; ");
        sb.append("Quantidade: ").append(this.quantidade).append("; ");
        sb.append("Filial: ").append(this.filial).append(".\n");
        return sb.toString();
    }

    /**
     * Clone
     */
    public Venda clone(){
        return new Venda(this);
    }

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || this.getClass()!=o.getClass()) return false;
        Venda v=(Venda) o;
        if(this.codProd!=v.getCodProd()) return false;
        if(this.codCli!=v.getCodCli()) return false;
        if(this.tipo!=v.getTipo()) return false;
        if(this.mes!=v.getMes()) return false;
        if(this.quantidade!=v.getQuantidade()) return false;
        if(this.filial!=v.getFilial()) return false;
        if(this.preco!=v.getPreco()) return false;
        
        return true;
    }

    /**
     * hashCode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{codProd,codCli,tipo,mes,quantidade,filial,preco});
    }
    
    
}
