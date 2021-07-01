import java.util.GregorianCalendar;
import java.io.Serializable;

public abstract class Ator implements Serializable {
    private String email;
    private String nome;
    private String password;
    private String morada;
    private GregorianCalendar dataNascimento;
    private int nif;
    private double classificacao;

    public Ator() {
    this.email="";
    this.nome="";
    this.password="";
    this.morada="";
    this.dataNascimento= new GregorianCalendar();
    this.nif=0;
    this.classificacao=0.0;
    }

    public Ator(Ator ator) {
        this.email = ator.getEmail();
        this.nome = ator.getNome();
        this.password = ator.getPassword();
        this.morada = ator.getMorada();
        this.dataNascimento = ator.getDataNascimento();
        this.nif=ator.getNif();
        this.classificacao=ator.getClassificacao();
    }

    public Ator(String email, String nome, String password, String morada, GregorianCalendar dataNascimento, int nif,double classificacao) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.morada = morada;
        this.dataNascimento = dataNascimento;
        this.nif=nif;
        this.classificacao=classificacao;
    }

    //****************************************GETS & SETS**********************************************************************

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getPassword() {
        return password;
    }

    public String getMorada() {
        return morada;
    }

    public GregorianCalendar getDataNascimento() {
        return dataNascimento;
    }

    public int getNif(){
        return nif;
    }

    public double getClassificacao(){return classificacao;}

    public void setClassificacao(double classificacao) {this.classificacao=classificacao;}

    public void setNif(int nif){
        this.nif=nif;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setDataNascimento(GregorianCalendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

//***************************************************************************************************************

    public void fazmedia(double cla) {
        if (classificacao > 0)  classificacao = ( classificacao + cla) / 2;
        else  classificacao = cla;
    }

    //***************************************************************************************************************


    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Ator aux = (Ator) o;
        return (this.email.equals(aux.getEmail()) && this.nome.equals(aux.getNome()) &&
                this.password.equals(aux.getPassword()) && this.morada.equals(aux.getMorada()) &&
                this.dataNascimento.equals(aux.getDataNascimento()) && this.nif==aux.getNif() && this.classificacao==aux.getClassificacao());
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Nif: ").append(this.nif).append("\n");
        sb.append("Password: ").append(this.password).append("\n");
        sb.append("Email: ").append(this.email).append("\n");
        sb.append("Morada: ").append(this.morada).append("\n");
        sb.append("Classificação: ").append(this.classificacao).append("\n");
        sb.append("Data de nascimento: ").append(this.dataNascimento.get(GregorianCalendar.DAY_OF_MONTH)).append("/");
        sb.append(this.dataNascimento.get(GregorianCalendar.MONTH)).append("/");
        sb.append(this.dataNascimento.get(GregorianCalendar.YEAR)).append("\n");
        return sb.toString();
    }

    public abstract Ator clone();
}
