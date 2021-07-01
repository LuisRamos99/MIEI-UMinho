import java.io.Serializable;

public class TriploCodCliQuantReceita implements Serializable,ITriploCodCliQuantReceita,Comparable<TriploCodCliQuantReceita> {

    private String codCli;
    private double gasto;
    private int quantidadeComp;


    public TriploCodCliQuantReceita(String s, double i, int qt) {
        codCli = s;
        gasto = i;
        quantidadeComp = qt;
    }

    public TriploCodCliQuantReceita(TriploCodCliQuantReceita pg) {
        codCli = pg.getCodCli();
        gasto = pg.getGasto();
        quantidadeComp = pg.getQuantidadeComp();
    }

    public double getGasto() {return gasto;}

    public String getCodCli() {return codCli;}

    public int getQuantidadeComp() {return quantidadeComp;}

    public int compareTo(TriploCodCliQuantReceita p) {
        if (quantidadeComp > p.getQuantidadeComp()) return -1;
        if (quantidadeComp < p.getQuantidadeComp()) return 1;
        else return codCli.compareTo(p.getCodCli());
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(codCli).append(" | ");
        sb.append("Quantidade comprada: ").append(quantidadeComp).append(" | ");
        sb.append("Total gasto: ").append(gasto);
        return sb.toString();
    }

    public TriploCodCliQuantReceita clone() {
        return new TriploCodCliQuantReceita(this);
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        TriploCodCliQuantReceita p = (TriploCodCliQuantReceita) o;
        return codCli.equals(p.getCodCli()) && gasto == p.getGasto() && quantidadeComp == p.getQuantidadeComp();
    }
}