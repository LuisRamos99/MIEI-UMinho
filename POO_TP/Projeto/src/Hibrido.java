import java.util.ArrayList;

public class Hibrido extends Carro {

    public Hibrido(){
        super();
    }

    public Hibrido(Hibrido hibrido){
        super(hibrido);
    }


    public Hibrido(double velMedia, double precoKm, double consumo,String matricula, String modelo, double autonomia,Ponto2D coordernadas){
        this.setVelMedia(velMedia);
        this.setPrecoKm(precoKm);
        this.setConsumo(consumo);
        this.setMatricula(matricula);
        this.setModelo(modelo);
        this.setAutonomiaInicial(autonomia);
        this.setAutonomiaAtual(autonomia);
        this.setCoordenadas(coordernadas);
    }

    public Hibrido(double velMedia, double precoKm, double consumo, ArrayList<Aluguer> historico, double classificacaoCarro, double fatorFiabilidade, String matricula, String modelo, Ponto2D coordenadas, double autonomia, Proprietario proprietario, boolean disponivel){
        super(velMedia,precoKm,consumo,historico,classificacaoCarro,fatorFiabilidade,matricula,modelo,coordenadas,autonomia,disponivel);
    }

    //**************************************************************************************************************************


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n------------------------------------------------------------\n");
        sb.append("Carro híbrido: ").append("\n").append(super.toString());
        return sb.toString();
    }

    public boolean equals(Object exCarro){
        if(this==exCarro) return true;
        if ((exCarro == null || (this.getClass() != exCarro.getClass())))
            return false;

        Hibrido hib = (Hibrido) exCarro;
        return(super.equals(hib));
    }

    public Hibrido clone(){
        return new Hibrido(this);
    }
}

