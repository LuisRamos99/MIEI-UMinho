import java.io.Serializable;
import java.time.LocalDate;

public class Aluguer implements Serializable{
    private Carro carro;
    private Ponto2D coordPartida;
    private Ponto2D coordChegada;
    private boolean autonomia;
    private double distanciaInicial;
    private double distanciaAluguer;  
    private boolean aceitaAlug;       
    private double precoEstimado;     
    private double precoReal;         
    private double tempoCliente;      
    private double tempoReal;         
    private int avaliacao;
    private LocalDate dataAlug;
    private int nifCliente;

    public Aluguer() {
        this.carro = null;
        this.coordPartida = new Ponto2D();
        this.coordChegada = new Ponto2D();
        this.autonomia = true;
        this.distanciaInicial = 0.0;
        this.distanciaAluguer = 0.0;
        this.aceitaAlug = true;
        this.precoEstimado = 0.0;
        this.precoReal = 0.0;
        this.tempoCliente = 0;
        this.tempoReal = 0;
        this.avaliacao = 0;
        this.dataAlug=LocalDate.now();
        this.nifCliente=0;
    }

    public Aluguer(Aluguer a) {
        this.carro = a.getCarro();
        this.coordPartida = a.getCoordPartida();
        this.coordChegada = a.getCoordChegada();
        this.autonomia = a.getAutonomia();
        this.distanciaInicial = a.getDistanciaInicial();
        this.distanciaAluguer = a.getDistanciaAluguer();
        this.aceitaAlug = a.getAceitaAlug();
        this.precoEstimado = a.getPrecoEstimado();
        this.precoReal = a.getPrecoReal();
        this.tempoCliente = a.getTempoCliente();
        this.tempoReal = a.getTempoReal();
        this.avaliacao = a.getAvaliacao();
        this.dataAlug=a.getDataAlug();
        this.nifCliente=a.getNifCliente();
    }    
   
    public Aluguer(Carro carro, Ponto2D coordPartida,Ponto2D coordChegada, boolean autonomia, double distanciaInicial, double distanciaAluguer,
                   boolean aceitaAlug, double precoEstimado, double precoReal, double tempoCliente, double tempoReal, int avaliacao, LocalDate dataAlug, int nif) {
        this.carro = carro;
        this.coordPartida = coordPartida;
        this.coordChegada = coordChegada;
        this.autonomia = autonomia;
        if (autonomia) {
            this.distanciaInicial = distanciaInicial;
            this.distanciaAluguer = distanciaAluguer;
            this.aceitaAlug = aceitaAlug;
            this.precoEstimado = precoEstimado;
            this.precoReal = precoReal;
            this.tempoCliente = tempoCliente;
            this.tempoReal = tempoReal;
            if (0 <= avaliacao && avaliacao <= 100) this.avaliacao = avaliacao;
            else this.avaliacao = 0;
            this.dataAlug= dataAlug;
            this.nifCliente=nif;
        }

    }

    //****************************************GETS & SETS**********************************************************************


    public Carro getCarro() {
        return carro;
    }

    public Ponto2D getCoordPartida() {
        return this.coordPartida.clone();
    }

    public Ponto2D getCoordChegada() {
        return this.coordChegada.clone();
    }

    public LocalDate getDataAlug() {
        return dataAlug;
    }

    public int getNifCliente() {return this.nifCliente;}

    public void setDataAlug(LocalDate dataAlug) {
        this.dataAlug = dataAlug;
    }

    public boolean getAutonomia() {
        return this.autonomia;
    }

    public double getDistanciaInicial() {
        return this.distanciaInicial;
    }

    public double getDistanciaAluguer() {
        return this.distanciaAluguer;
    }

    public boolean getAceitaAlug() {
        return this.aceitaAlug;
    }

    public double getPrecoEstimado() {
        return this.precoEstimado;
    }

    public double getPrecoReal() {
        return this.precoReal;
    }

    public double getTempoCliente() {
        return this.tempoCliente;
    }

    public double getTempoReal() {
        return this.tempoReal;
    }

    public int getAvaliacao() {
        return this.avaliacao;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public void setNifCliente(int nif) {this.nifCliente=nif;}

    public void setCoordPartida(Ponto2D coordPartida) {
        this.coordPartida = coordPartida;
    }

    public void setCoordChegada(Ponto2D coordChegada) {
        this.coordChegada = coordChegada;
    }

    public void setAutonomia(boolean autonomia) {
        this.autonomia = autonomia;
    }

    public void setDistanciaInicial(double distanciaInicial) {
        this.distanciaInicial = distanciaInicial;
    }

    public void setDistanciaAluguer(double distanciaAluguer) {
        this.distanciaAluguer = distanciaAluguer;
    }

    public void setAceitaAlug(boolean aceitaAlug) {
        this.aceitaAlug = aceitaAlug;
    }

    public void setPrecoEstimado(double precoEstimado) {
        this.precoEstimado = precoEstimado;
    }

    public void setPrecoReal(double precoReal) {
        this.precoReal = precoReal;
    }

    public void setTempoCliente(double tempoCliente) {
        this.tempoCliente = tempoCliente;
    }

    public void setTempoReal(double tempoReal) {
        this.tempoReal = tempoReal;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

//***************************************************************************************************************************************

    public boolean equals(Object o) {
        if (this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())) return false;
        Aluguer aux = (Aluguer) o;
        return (this.carro.equals(aux.getCarro())&& this.dataAlug.equals(aux.getDataAlug()) && this.coordPartida.equals(aux.getCoordPartida()) && this.coordChegada.equals(aux.getCoordChegada()) &&  this.autonomia == aux.getAutonomia() && this.distanciaInicial==(aux.getDistanciaInicial()) &&
                this.distanciaAluguer == aux.getDistanciaAluguer() &&  this.aceitaAlug == aux.getAceitaAlug() && this.precoEstimado == aux.getPrecoEstimado() &&
                this.precoReal == aux.getPrecoReal() && this.tempoCliente == aux.getTempoCliente() && this.tempoReal==(aux.getTempoReal()) && this.avaliacao==(aux.getAvaliacao()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n------------------------------------------------------------\n");
        sb.append("Aluguer: \n");
        sb.append("Matricula do carro: ").append(this.carro.getMatricula()).append("\n");
        sb.append("Coordenadas do carro: ").append(this.carro.getCoordenadas()).append("\n");
        sb.append("Autonomia do carro: ").append(this.carro.getAutonomiaAtual()).append("\n");
        sb.append("Coordenadas do cliente: ").append(this.coordPartida).append("\n");
        sb.append("Coordenadas do destino: ").append(this.coordChegada).append("\n");
        sb.append("Distancia cliente-carro: ").append(this.distanciaInicial).append("\n");
        sb.append("Distancia viagem: ").append(this.distanciaAluguer).append("\n");
        sb.append("Preço aluguer: ").append(this.precoEstimado).append("\n");
        sb.append("Tempo de viagem: ").append(this.tempoReal).append("\n");
        sb.append("Avaliação do aluguer: ").append(this.avaliacao).append("\n");
        sb.append("Data do aluguer: ").append(this.dataAlug).append("\n");
        return sb.toString();
    }

    public Aluguer clone() {
        return new Aluguer(this);
    }
}

