import java.util.ArrayList;

public class Eletrico extends Carro {

    public Eletrico(){
        super();
    }

    public Eletrico(Eletrico eletrico){
        super(eletrico);
    }

    public Eletrico(double velMedia, double precoKm, double consumo,String matricula, String modelo, double autonomia,Ponto2D coordernadas){
        this.setVelMedia(velMedia);
        this.setPrecoKm(precoKm);
        this.setConsumo(consumo);
        this.setMatricula(matricula);
        this.setModelo(modelo);
        this.setAutonomiaInicial(autonomia);
        this.setAutonomiaAtual(autonomia);
        this.setCoordenadas(coordernadas);
    }

    public Eletrico(double velMedia, double precoKm, double consumo, ArrayList<Aluguer> historico, double classificacaoCarro, double fatorFiabilidade, String matricula, String modelo, Ponto2D coordenadas, double autonomia, Proprietario proprietario, boolean disponivel){
        super(velMedia,precoKm,consumo,historico,classificacaoCarro,fatorFiabilidade,matricula,modelo,coordenadas,autonomia,disponivel);
    }

    //**************************************************************************************************************************

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n------------------------------------------------------------\n");
        sb.append("Carro elétrico: ").append("\n").append(super.toString());
        return sb.toString();
    }
    public boolean equals(Object exCarro){
        if(this==exCarro) return true;
        if ((exCarro == null || (this.getClass() != exCarro.getClass())))
            return false;

       Eletrico ele = (Eletrico) exCarro;
        return(super.equals(ele));
    }

    public Eletrico clone(){
        return new Eletrico(this);
    }
}

