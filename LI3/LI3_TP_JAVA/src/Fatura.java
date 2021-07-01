import java.io.Serializable;

/**
 * Classe com a informação da compra.
 */
public class Fatura implements Serializable,IFatura {

    /**
     * Variáveis
     */
    private int[] quantidade, nVendas;
    private double[] receita;

    /**
     * Construtor por parametros
     * @param quant Quantidade
     * @param preco Preço
     * @param fil Filial
     */
    public Fatura(int quant, double preco, int fil) {
        nVendas = new int[3];
        quantidade = new int[3];
        receita = new double[3];
        if (quant>0) nVendas[fil-1] = 1;
        quantidade[fil-1] = quant;
        receita[fil-1] = preco * quant;
    }

    /**
     * Construtor por cópia
     * @param f Fatura
     */
    public Fatura(Fatura f) {
        nVendas = new int[3];
        quantidade = new int[3];
        receita = new double[3];
        quantidade[0] = f.getQuantidade(1);
        nVendas[0] = f.getNumVendas(1);
        receita[0] = f.getReceitas(1);
        quantidade[1] = f.getQuantidade(2);
        nVendas[1] = f.getNumVendas(2);
        receita[1] = f.getReceitas(2);
        quantidade[2] = f.getQuantidade(3);
        nVendas[2] = f.getNumVendas(3);
        receita[2] = f.getReceitas(3);
    }

    /**
     * Retorna a quantidade total
     * @return Quantidade total, em Integer
     */
    public int getQuantidadeTotal() {return quantidade[0] + quantidade[1] + quantidade[2];}

    /**
     * Retorna a receita total
     * @return Receita total, em Double
     */
    public double getReceitasTotal() {return receita[0] + receita[1] + receita[2];}

    /**
     * Retorna o numero de vendas total
     * @return Numero de vendas total, em Integer
     */
    public int getNumVendasTotal() {return nVendas[0] + nVendas[1] + nVendas[2];}

    /**
     * Retorna a quantidade por filial
     * @return Quantidade por filial, em Integer
     */
    public int getQuantidade(int filial) {return quantidade[filial-1];}

    /**
     * Retorna a receita por filial
     * @return Receita por filial, em Double
     */
    public double getReceitas(int filial) {return receita[filial-1];}

    /**
     * Retorna o numero de vendas por filial
     * @return Numero de vendas por filial, em Integer
     */
    public int getNumVendas(int filial) {return nVendas[filial-1];}

    /**
     * Adiciona fatura
     * @param quant Quantidade
     * @param preco Preço
     * @param filial Filial
     */
    public void addFatura(int quant, double preco, int filial) {
        quantidade[filial-1] += quant;
        receita[filial-1] += preco*quant;
        nVendas[filial-1] += 1;
    }

    /**
     * Equals
     */
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Fatura f = (Fatura) o;
        if (receita[0] != f.getReceitas(1)) return false;
        if (receita[1] != f.getReceitas(2)) return false;
        if (receita[2] != f.getReceitas(3)) return false;
        if (quantidade[0] != f.getQuantidade(1)) return false;
        if (quantidade[1] != f.getQuantidade(2)) return false;
        if (quantidade[2] != f.getQuantidade(3)) return false;
        if (nVendas[0] != f.getNumVendas(1)) return false;
        if (nVendas[1] != f.getNumVendas(2)) return false;
        if (nVendas[2] != f.getNumVendas(3)) return false;
        return true;
    }

    /**
     * Clone
     */
    public Fatura clone() {return new Fatura(this);}

    /**
     * toString
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Quantidade Vendida: ").append(this.getQuantidadeTotal()).append("; ");
        sb.append("Total Faturado: ").append(this.getReceitasTotal()).append("; ");
        sb.append("Numero de Vendas: ").append(this.getNumVendasTotal()).append("\n");
        return sb.toString();
    }
}

