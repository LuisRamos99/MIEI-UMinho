import java.io.Serializable;

public class ParCodProdQuantComp implements Serializable,IParCodProdQuantComp,Comparable<ParCodProdQuantComp>{

    private String codProd;
    private int quantidadeComp;


    public ParCodProdQuantComp(String s, int i){
        codProd = s;
        quantidadeComp = i;
    }

    public ParCodProdQuantComp(ParCodProdQuantComp p){
        codProd = p.getcodProd();
        quantidadeComp = p.getquantidadeComp();
    }

    public int getquantidadeComp(){return quantidadeComp;}

    public String getcodProd(){return codProd;}

    public void add(int q){quantidadeComp += q;}

    public int compareTo(ParCodProdQuantComp p){
        if(quantidadeComp > p.getquantidadeComp()) return -1;
        if(quantidadeComp < p.getquantidadeComp()) return 1;
        else return codProd.compareTo(p.getcodProd());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Produto: ").append(codProd).append(" | ");
        sb.append("Quantos: ").append(quantidadeComp);
        return sb.toString();
    }

    public String toStringQuery8(){
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(codProd).append(" | ");
        sb.append("Quantos: ").append(quantidadeComp);
        return sb.toString();
    }

    public ParCodProdQuantComp clone(){
        return new ParCodProdQuantComp(this);
    }

    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        ParCodProdQuantComp p = (ParCodProdQuantComp) o;
        return codProd.equals(p.getcodProd()) && quantidadeComp == p.getquantidadeComp();
    }
}