import java.io.*;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.lang.Object;

public class Cliente extends Ator implements Serializable {
    private Ponto2D coordenadas;
    private ArrayList<Aluguer> historico;

    public Cliente() {
        super();
        this.coordenadas = new Ponto2D();
        this.historico = new ArrayList<>();
    }

    public Cliente(Cliente c) {
        super(c);
        this.coordenadas = c.getCoordenadas();
        this.historico = c.getHistorico();
    }

    public Cliente(String email, String nome, String password, String morada, GregorianCalendar dataDeNascimento,int nif,double classificacao, Ponto2D coordenadas, ArrayList<Aluguer> historico) {
        super(email,nome,password,morada,dataDeNascimento,nif,classificacao);
        this.coordenadas=coordenadas;
        this.historico = historico;
    }

    //****************************************GETS & SETS**********************************************************************

    public Ponto2D getCoordenadas() {return this.coordenadas;}

    public ArrayList<Aluguer> getHistorico() {
        ArrayList<Aluguer> novo = new ArrayList<>();
        for(Aluguer v : historico) {novo.add(v.clone());}
        return novo;
    }

    public void setCoordenadas(Ponto2D coordenadas) {this.coordenadas=coordenadas.clone();}

    public void setHistorico(ArrayList<Aluguer> historico) {
        this.historico = new ArrayList<>();
        for(Aluguer v: historico) {this.historico.add(v.clone());}
    }

    public void setAvaliacao(String mat, int valor) {
        for (Aluguer a: this.historico) {if (a.getCarro().getMatricula().equals(mat)) a.setAvaliacao(valor);}
    }

    public int getNumAluguers() {
        if (historico==null) return 0;
        return historico.size();
    }

    public void addAluguer(Aluguer aluguer){
       historico.add(aluguer);
    }

    public Double getKmsTotal() {
        return historico.stream().mapToDouble(a->a.getDistanciaAluguer()).sum();
    }



//**************************************************************************************************************************

    public boolean equals(Object umCliente) {
        if (this == umCliente)
            return true;

        if ((umCliente == null || (this.getClass() != umCliente.getClass())))
            return false;

        Cliente c = (Cliente) umCliente;
        return(super.equals(c));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCliente: ").append("\n").append("\n");
        sb.append(super.toString());
        sb.append("Coordenadas do cliente: ").append(this.coordenadas).append("\n");
        sb.append("Lista de alugueres do cliente: ").append(this.historico).append("\n");
        return sb.toString();
    }

    public Cliente clone() {
        return new Cliente(this);
    }
}
