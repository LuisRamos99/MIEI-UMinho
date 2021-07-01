package Main.business;

public class Conteudo {
    private String nomeCont;
    private String nomeFicheiro;
    private String flagCat;
    private int tipo;

    public Conteudo(String nomeCont) {
        this.nomeCont = nomeCont;
    }

    public Conteudo(String nomeCont, String nomeFicheiro, String flagCat) {
        this.nomeCont = nomeCont;
        this.nomeFicheiro = nomeFicheiro;
        this.flagCat = flagCat;
    }

    public Conteudo(String nomeCont, String nomeFicheiro, String flagCat, int tipo) {
        this.nomeCont = nomeCont;
        this.nomeFicheiro = nomeFicheiro;
        this.flagCat = flagCat;
        this.tipo = tipo;
    }


    public int getTipo() {
        return tipo;
    }

    public String getNomeCont() {
        return nomeCont;
    }

    public void setNomeCont(String nomeCont) {
        this.nomeCont = nomeCont;
    }

    public String getNomeFicheiro() {
        return nomeFicheiro;
    }

    public void setNomeFicheiro(String nomeFicheiro) {
        this.nomeFicheiro = nomeFicheiro;
    }

    public String getFlagCat() {
        return flagCat;
    }

    public void setFlagCat(String flagCat) {
        this.flagCat = flagCat;
    }

    public String toString() {
        return "Conteudo{" +
                "nomeCont='" + nomeCont + '\'' +
                ", nomeFicheiro='" + nomeFicheiro + '\'' +
                ", flagCat='" + flagCat + '\'' +
                ", tipo=" + tipo +
                '}';
    }


}

