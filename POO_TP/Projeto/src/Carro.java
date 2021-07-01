import java.io.Serializable;
import java.util.ArrayList;

public abstract class  Carro implements Serializable {
    private double velMedia;
    private double precoKm;
    private double consumo;
    private ArrayList<Aluguer> historico;
    private double classificacaoCarro;
    private double fatorFiabilidade;
    private String matricula;
    private String modelo;
    private Ponto2D coordenadas;
    private double autonomiaInicial;
    private double autonomiaAtual;
    private boolean disponivel;


    public Carro() {
        this.velMedia = 0.0;
        this.precoKm = 0.0;
        this.consumo = 0.0;
        this.historico = new ArrayList<>();
        this.classificacaoCarro = 0.0;
        this.fatorFiabilidade = 0.0;
        this.matricula = "";
        this.modelo = "";
        this.coordenadas = new Ponto2D();
        this.autonomiaInicial = 0.0;
        this.autonomiaAtual = 0.0;
        this.disponivel = true;
    }

    public Carro(Carro c) {
        this.velMedia = c.getVelMedia();
        this.precoKm = c.getPrecoKm();
        this.consumo = c.getConsumo();
        this.historico = c.getHistorico();
        this.classificacaoCarro = c.getClassificacaoCarro();
        this.fatorFiabilidade = c.getFatorFiabilidade();
        this.matricula = c.getMatricula();
        this.modelo = c.getModelo();
        this.coordenadas = c.getCoordenadas();
        this.autonomiaInicial = c.getAutonomiaInicial();
        this.autonomiaAtual = c.getAutonomiaAtual();
        this.disponivel = c.getDisponivel();
    }

    public Carro(double velMedia, double precoKm, double consumo, ArrayList<Aluguer> historico, double classificacaoCarro, double fatorFiabilidade, String matricula, String modelo, Ponto2D coordenadas, double autonomiaInicial, boolean disponivel) {
        this.velMedia = velMedia;
        this.precoKm = precoKm;
        this.consumo = consumo;
        for (Aluguer a : historico) {this.historico.add(a.clone());}
        this.classificacaoCarro = classificacaoCarro;
        this.fatorFiabilidade = fatorFiabilidade;
        this.matricula = matricula;
        this.modelo = modelo;
        this.coordenadas = coordenadas;
        this.autonomiaInicial = autonomiaInicial;
        this.autonomiaAtual = autonomiaInicial;
        this.disponivel = disponivel;
    }
    //****************************************GETS & SETS**********************************************************************

    public double getVelMedia() {return velMedia;}

    public double getPrecoKm() {return precoKm;}

    public double getConsumo() {return consumo;}

    public ArrayList<Aluguer> getHistorico() {
        ArrayList<Aluguer> a = new ArrayList<>();
        if (this.historico == null) return a;
        for (Aluguer v : this.historico) {a.add(v.clone());}
        return a;
    }

    public double getClassificacaoCarro() {return classificacaoCarro;}

    public double getFatorFiabilidade() {return fatorFiabilidade;}

    public String getMatricula() {return matricula;}

    public String getModelo() {return modelo;}

    public Ponto2D getCoordenadas() {return this.coordenadas.clone();}

    public double getAutonomiaInicial() {return autonomiaInicial;}

    public double getAutonomiaAtual() {return autonomiaAtual;}

    public boolean getDisponivel() {return this.disponivel;}

    public void setVelMedia(double velMedia) {this.velMedia = velMedia;}

    public void setPrecoKm(double precoKm) {this.precoKm = precoKm;}

    public void setConsumo(double consumo) {this.consumo = consumo;}

    public void setHistorico(ArrayList<Aluguer> historico) {
        for (Aluguer a : historico) {this.historico.add(a.clone());}
    }

    public void setClassificacaoCarro(double classificacaoCarro) {this.classificacaoCarro = classificacaoCarro;}

    public void setFatorFiabilidade(double fatorFiabilidade) {this.fatorFiabilidade = fatorFiabilidade;}

    public void setMatricula(String matricula) {this.matricula = matricula;}

    public void setModelo(String modelo) {this.modelo = modelo;}

    public void setCoordenadas(Ponto2D coordenadas) {this.coordenadas = coordenadas.clone();}

    public void setAutonomiaInicial(double autonomiaInicial) {this.autonomiaInicial = autonomiaInicial;}

    public void setAutonomiaAtual(double autonomiaAtual) {this.autonomiaAtual = autonomiaAtual;}

    public void setDisponivel(boolean disponivel) {this.disponivel = disponivel;}

    public void setClassificacaoHistorico(int valor, String mat) {
        for (Aluguer a: historico) {if (a.getCarro().getMatricula().equals(mat)) a.setAvaliacao(valor);}
    }

    public void adicionaAluguer(Aluguer a) {historico.add(a);}


    //************************************************************************************************************************

    public void fazmedia(double cla) {
        if (classificacaoCarro > 0)  classificacaoCarro = ( classificacaoCarro + cla) / 2;
        else  classificacaoCarro = cla;
    }

    //************************************************************************************************************************

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Carro aux = (Carro) o;
        return (this.velMedia == aux.getVelMedia() && this.precoKm == aux.getPrecoKm() && this.consumo == aux.getConsumo()
                && this.historico.equals(aux.getHistorico()) && this.classificacaoCarro == aux.getClassificacaoCarro()
                && this.fatorFiabilidade == aux.getFatorFiabilidade() && this.matricula.equals(aux.getMatricula())
                && this.modelo.equals(aux.getModelo()) && this.coordenadas.equals(aux.getCoordenadas())
                && this.autonomiaInicial == aux.getAutonomiaInicial()&& this.disponivel == aux.getDisponivel())
                && this.autonomiaAtual == aux.getAutonomiaAtual();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matricula: ").append(this.matricula).append("\n");
        sb.append("Modelo: ").append(this.modelo).append("\n");
        sb.append("Classificação: ").append(this.classificacaoCarro).append("\n");
        sb.append("Autonomia atual: ").append(this.autonomiaAtual).append("\n");
        sb.append("Consumo: ").append(this.consumo).append("\n");
        sb.append("Velocidade média: ").append(this.velMedia).append("\n");
        sb.append("Preço por Km: ").append(this.precoKm).append("\n");
        sb.append("Coordenadas: ").append(this.coordenadas).append("\n");
        sb.append("Historico de alugueres: ").append(this.historico).append("\n");
        return sb.toString();
    }

    public abstract Carro clone();
}




    

    
