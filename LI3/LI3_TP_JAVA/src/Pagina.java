import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Pagina implements Serializable,IPagina {

    /**
     * Variaveis
     */
    private int tamanho, indice;
    private List<String> lista;

    /**
     * Construtor por parametros
     * @param s Tamanho da pagina
     */
    public Pagina(int s){
        tamanho = s;
        indice = 0;
        lista = new ArrayList<>();
    }

    /**
     * Construtor por copia
     * @param p Pagina
     */
    public Pagina(Pagina p){
        tamanho = p.tamanho();
        indice = p.indiceAtual();
        lista = p.getLista();
    }

    /**
     * Coloca um determinado indice
     * @param i Indice
     */
    public void setIndice(int i){if(i >= 0 && i < tamanho) indice = i;}

    /**
     * Retorna o tamanho
     * @return Tamanho, em Integer
     */
    public int tamanho(){return tamanho;}

    /**
     * Retorna o indice atual
     * @return Indice, em Integer
     */
    public int indiceAtual(){return indice;}

    /**
     * Retorna a lista de strings
     * @return Lista, em List
     */
    public List<String> getLista(){
        List<String> aux = new ArrayList<>();
        for (String s : lista) {aux.add(s);}
        return aux;
    }

    /**
     * Adiciona uma string à lista
     * @param s String
     */
    public void add(String s) {lista.add(s);}

    /**
     * Retorna a proxima string
     * @param i Indice
     * @param a Variavel que dá ordem de troca
     * @return String, em String
     */
    public String nextString(int i, int a){
        if(indice < tamanho){
            if (indice>=lista.size()-i) {indice++; return " ";}
            String s = lista.get(indice+i);
            if (a==1) indice++;
            return s;
        }
        return " ";
    }

    /**
     * Clone
     */
    public Pagina clone(){return new Pagina(this);}


    /**
     * Equals
     */
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Pagina p = (Pagina) o;
        if(tamanho != p.tamanho()) return false;
        if(indice != p.indiceAtual()) return false;
        boolean b = true;
        int i;
        List<String> al = p.getLista();
        for(i = 0; i < tamanho && b; i++){lista.get(i).equals(al.get(i));}
        return b;
    }

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(String s:lista) sb.append(s).append("\n");
        return sb.toString();
    }

    /**
     * hashCode
     */
    public int hashCode(){return Arrays.hashCode(new Object[]{lista});}
}