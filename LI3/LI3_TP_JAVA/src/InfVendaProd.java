import java.util.Arrays;
import java.io.Serializable;

/**
 * Classe com a informação da compra.
 */
public class InfVendaProd implements Serializable,IInfVendaProd{

    /**
     * Variáveis
     */
    private int quantidade;
    private double preco;
    private int mes;

    /**
     * Construtor vazio
     */
    public InfVendaProd(){
        quantidade=0;
        preco=0;
        mes=0;
    }

    /**
     * Construtor por cópia
     * @param ivp InfVendaProd
     */
    public InfVendaProd(InfVendaProd ivp){
        quantidade=ivp.getQuantidade();
        preco=ivp.getPreco();
        mes=ivp.getMes();
    }

    /**
     * Retorna a quantidade
     * @return Quantidade, em Integer
     */
    public int getQuantidade(){return quantidade;}

    /**
     * Retorna o preco
     * @return Preco, em Double
     */
    public double getPreco(){return preco;}

    /**
     * Retorna o mes
     * @return Mes, em Integer
     */
    public int getMes(){return mes;}

    /**
     * Coloca uma determinada quantidade
     * @param qt Quantidade
     */
    public void setQuantidade(int qt){quantidade=qt;}

    /**
     * Coloca um determinado preço
     * @param pr Preco
     */
    public void setPreco(double pr){preco=pr;}

    /**
     * Coloca um determinado mes
     * @param mes Mes
     */
    public void setMes(int mes){this.mes=mes;}

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Mes: ").append(this.mes).append("; ");
        sb.append("Preco: ").append(this.preco).append("; ");
        sb.append("Quant: ").append(this.quantidade).append("\n");
        return sb.toString();
    }

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || this.getClass()!=o.getClass()) return false;
        InfVendaProd v=(InfVendaProd) o;
        if(this.mes!=v.getMes()) return false;
        if(this.quantidade!=v.getQuantidade()) return false;
        if(this.preco!=v.getPreco()) return false;
        return true;
    }

    /**
     * Clone
     */
    public InfVendaProd clone(){return new InfVendaProd(this);}

    /**
     * hashCode
     */
    public int hashCode(){return Arrays.hashCode(new Object[]{quantidade,preco,mes});}
}