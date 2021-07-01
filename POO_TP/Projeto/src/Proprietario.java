import java.io.Serializable;
import java.util.*;


public class Proprietario extends Ator implements Serializable {
    private ArrayList<Aluguer> historico;
    private HashMap<String,Carro> conjunto;
    private Queue<Aluguer> pendentes;

    public Proprietario() {
        super();
        this.historico = new ArrayList<>();
        this.conjunto= new HashMap<>();
        this.pendentes = new LinkedList<>();
    }

    public Proprietario(Proprietario p) {
        super(p);
        this.historico = p.getHistorico();
        this.conjunto= p.getConjunto();
        this.pendentes = p.getPendentes();

    }

    public Proprietario(String email, String nome, String password, String morada, GregorianCalendar dataDeNascimento, int nif, double classificacao, ArrayList<Aluguer> historico, ArrayList<Carro>conjunto) {
        super(email,nome,password,morada,dataDeNascimento,nif,classificacao);
        this.historico = new ArrayList<>();
        this.conjunto= new HashMap();
    }

    //**************************************************************************************************************************


    public ArrayList<Aluguer> getHistorico() {
        ArrayList<Aluguer> a = new ArrayList<>();
        if (this.historico == null) return a;
        for (Aluguer v : this.historico) {a.add(v.clone());}
        return a;
    }

    public Queue<Aluguer> getPendentes(){
        Queue<Aluguer> q = new LinkedList<>();
        if(this.pendentes==null) return q;
        for(Aluguer v : this.pendentes){q.add(v.clone());}
        return q;
    }

    public HashMap<String,Carro> getConjunto() {
        HashMap<String,Carro> c = new HashMap<>();
        conjunto.forEach((a,b)->c.put(a,b.clone()));
        return c;
    }

    public Carro getConj(String mat){return (conjunto.get(mat));}

    public void setPrecoMenu(double preco, String mat){this.conjunto.get(mat).setPrecoKm(preco);}

    public void setNovaAutonomia(String mat){this.conjunto.get(mat).setAutonomiaAtual(conjunto.get(mat).getAutonomiaInicial());}

    public void setAvaliacao (int valor, String mat) {
        for(Aluguer a: historico) {if (a.getCarro().getMatricula().equals(mat)) a.setAvaliacao(valor);}
        conjunto.get(mat).setClassificacaoHistorico(valor,mat);
    }

    public void setHistorico(ArrayList<Aluguer> historico) {
        for (Aluguer a : historico) {this.historico.add(a.clone());}
    }

    public void setPendentes(Queue<Aluguer> pendentes){
        for(Aluguer a : pendentes){this.pendentes.add(a.clone());}
    }

    public void setConjunto(HashMap<String,Carro> conjunto){this.conjunto= conjunto;}

    public void adicionaAluguer(Aluguer a, String mat) {
        this.historico.add(a);
        this.conjunto.get(mat).adicionaAluguer(a);
    }

    public void addPedido(Aluguer alug){pendentes.add(alug);}

    public void addCarro(Carro carro){conjunto.put(carro.getMatricula(),carro.clone());}


//**************************************************************************************************************************


    public boolean equals(Object umProprietario) {
        if (this == umProprietario) return true;
        if ((umProprietario == null || (this.getClass() != umProprietario.getClass()))) return false;
        Proprietario p = (Proprietario) umProprietario;
        return(super.equals(p) && ( this.historico.equals(p.getHistorico())) &&
                this.conjunto.equals(p.getConjunto()));
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nProprietário: ").append("\n").append("\n");
        sb.append(super.toString());
        sb.append("Carros do propriétário: ").append(this.conjunto.values().toString()).append("\n");
        sb.append("Histório de alugueres do propriétário: ").append(this.historico).append("\n");
        return sb.toString();
    }

    public Proprietario clone() {
        return new Proprietario(this);
    }
}
