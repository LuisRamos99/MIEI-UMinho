package Main.business;

public class Categoria {
    private String nome;
    private int tipo;

    public Categoria(String nome) {
        this.nome = nome;
    }


    public Categoria(String nome, int tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return nome;
    }

}
