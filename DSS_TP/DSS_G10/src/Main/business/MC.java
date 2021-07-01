package Main.business;

import Main.data.AtorDAO;
import Main.data.CategoriaDAO;
import Main.data.ConteudoDAO;
import Main.ui.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class MC {
    private Ator at;
    private AtorDAO atores;
    private CategoriaDAO categoria;
    private ConteudoDAO conteudo;

    public MC(){
        at=null;
        atores = new AtorDAO();
        categoria = new CategoriaDAO();
        conteudo = new ConteudoDAO();
    }

    public boolean autenticar(String email, String password) throws NullPointerException {
            Ator teste = atores.get(email);
            if (at == null) {
                String user = teste.getEmail();
                String passe = teste.getPassword();
                if (email.equals(user) && password.equals(passe)) { at = teste; return true;
                } else return false;
            }
        return false;
    }


    public boolean alterarCategoria(String nCont, String email,String categoriaVelha, String novaCategoria,int tipo){
        if(categoriaVelha.equals(novaCategoria)) {return false;}
        if(checkaCateg(novaCategoria)==1){categoria.put(novaCategoria, new Categoria(novaCategoria, tipo));}
        if(conteudo.getConsoanteUser(nCont,email)==null){conteudo.putM(email,nCont,novaCategoria);}
        else conteudo.updateCat(email,nCont,novaCategoria);
        return true;
    }


    public int checkaNome(String nome) {
        int flagNome=1;
        if (conteudo.containsKey(nome) )  {flagNome = 0; } return flagNome;}


    public int checkaCateg(String cat){
        int flagCateg=1;
        if(categoria.containsKey(cat)) {flagCateg=0; } return flagCateg;}


    public boolean existeFile(String fl) {
        boolean b = false;
        for (Conteudo c : conteudo.values()) {if(c.getNomeFicheiro().equals(fl)){b=true;} }
        return b;
    }


    public boolean adicionarConteudo(String nCont, String nFich, String nCat, int tipo){
        if(!(existeFile(nFich)) && checkaNome(nCont)==1) {
            if (checkaCateg(nCat) == 1) {categoria.put(nCat, new Categoria(nCat, tipo));}
            conteudo.put(nCont, new Conteudo(nCont, nFich, nCat, tipo));
            conteudo.putM(at.getEmail(), nCont,nCat);
            return true;
        }
        else if((checkaNome(nCont) == 0) && conteudo.get(nCont).getNomeFicheiro().equals(nFich)){
            conteudo.putM(at.getEmail(), nCont,nCat);
            return true;
        } else return false;
    }


    public void terminarSessao(){ at = null; }



    public ArrayList<String> getNomesTeste(int tipo){
        return ( categoria.printMenu(tipo));
    }

    public Conteudo[] getConteudoUser(String email,String categoria){return ((Conteudo[])conteudo.toArrayFiltrada(email,categoria));}

    public Conteudo[] getConteudoConv(String categoria) {return ((Conteudo[]) conteudo.toArray(categoria));}

    public Ator getAut() {
        return at;
    }

    public boolean isAdmin(){
        return (at instanceof Admin);
    }

    public boolean isUser(){
        return (at instanceof Utilizador);
    }

    public boolean isLogged(){
        return (at != null);
    }

    public ConteudoDAO getConteudo() { return conteudo; }

    public void reproduzirCont(String filename){Player pla = new Player(filename);}
    }





