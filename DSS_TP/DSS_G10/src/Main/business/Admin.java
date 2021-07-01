package Main.business;

import java.util.Objects;

public class Admin extends Ator{

    public Admin(String password, String nome, String email){
        super(password,nome,email);
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString() + '}';
    }
}



