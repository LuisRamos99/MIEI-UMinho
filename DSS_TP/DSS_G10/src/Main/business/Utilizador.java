package Main.business;

import java.util.Objects;

public class Utilizador extends Ator{

    public Utilizador(){
        super();
    }

    public Utilizador(Utilizador u){
        super(u);
    }
    public Utilizador(String password, String nome, String email){
        super(password,nome,email);
    }

    @Override
    public String toString() {
        return "Utilizador{" + super.toString() + '}';
    }

}
