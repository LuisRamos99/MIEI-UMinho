package Main.business;

import java.util.Objects;


public abstract class Ator {
    private String password,email,nome,tipo;

    public Ator() {};

    public Ator(Ator ator){
        this.password=ator.getPassword();
        this.email=ator.getEmail();
        this.nome=ator.getNome();
        this.tipo=ator.getTipo();
    }

    public Ator(String password, String email, String nome) {
        this.password = password;
        this.email = email;
        this.nome = nome;
    }




    public Ator(String password, String email, String nome,String tipo) {
        this.password = password;
        this.email = email;
        this.nome = nome;
        this.tipo=tipo;

    }

    public String getTipo() {
        return tipo;
    }
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ator other = (Ator) obj;

        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }

        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
    public String toString() {
        return  "email=" + email + ", password=" + password + ", nome=" + nome  + '}';
    }



}
