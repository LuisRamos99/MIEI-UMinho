import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStrings implements Serializable,IListStrings {

    /**
     * Variaveis
     */
    private int pagina, ultimaPag, tamanhoPag, tamanho;
    private List<String> lista;

    /**
     * Construtor por parametros
     * @param x Tamanho da pagina
     */
    public ListStrings(int x){
        tamanho = 0;
        pagina = 1;
        ultimaPag = 0;
        tamanhoPag = x;
        lista = new ArrayList<>();
    }

    /**
     * Construtor vazio
     */
    public ListStrings(){
        tamanho = 0;
        pagina = 1;
        ultimaPag = 0;
        tamanhoPag = 60;
        lista = new ArrayList<>();
    }

    /**
     * Construtor por copia
     * @param ls Lista de Strings
     */
    public ListStrings(ListStrings ls){
        tamanho = ls.tamanho();
        pagina = ls.getPagina();
        ultimaPag = ls.getUltPag();
        tamanhoPag = ls.getTamPag();
        lista = ls.getLista();
    }

    /**
     * Retorna o tamanho
     * @return Tamanho, em Integer
     */
    public int tamanho(){return tamanho;}

    /**
     * Retorna a pagina
     * @return Pagina, em Integer
     */
    public int getPagina(){return pagina;}

    /**
     * Retorna o tamanho da pagina
     * @return Tamanho da Pagina, em Integer
     */
    public int getTamPag(){return tamanhoPag;}

    /**
     * Retorna a ultima pagina
     * @return Ultima Pagina, em Integer
     */
    public int getUltPag(){return ultimaPag;}

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
    public void add(String s){
        lista.add(s);
        tamanho++;
    }

    /**
     * Atualiza os indices
     */
    public void actualizaIndices(){
        ultimaPag = (tamanho/tamanhoPag) + 1;
        if (tamanho - (ultimaPag-1)*tamanhoPag == 0) ultimaPag--;
    }

    /**
     * Retorna a proxima pagina
     * @return Proxima pagina, em Pagina
     */
    public Pagina proxPag(){
        if(pagina < ultimaPag) pagina++;
        int i, s = 0;
        if(pagina == ultimaPag) s = tamanho - (pagina-1)*tamanhoPag;
        else s = tamanhoPag;
        Pagina p = new Pagina(s);
        for(i = 0; i < s; i++) {p.add(lista.get((pagina-1)*tamanhoPag+i));}
        return p;
    }

    /**
     * Retorna a pagina anterior
     * @return Pagina anterior, em Pagina
     */
    public Pagina antPag(){
        if(pagina > 1) pagina--;
        int i, s = 0;
        if(tamanho < tamanhoPag) s = tamanho;
        else s = tamanhoPag;
        Pagina p = new Pagina(s);
        for(i = 0; i < s; i++){p.add(lista.get((pagina-1)*tamanhoPag+i));}
        return p;
    }

    /**
     * Retorna a primeira pagina
     * @return Primeira pagina, em Pagina
     */
    public Pagina priPag(){
        pagina = 1;
        int i, s = 0;
        if(tamanho < tamanhoPag) s = tamanho;
        else s = tamanhoPag;
        Pagina p = new Pagina(s);
        for(i = 0; i < s; i++) {p.add(lista.get((pagina-1)*tamanhoPag+i));}
        return p;
    }

    /**
     * Retorna a ultima pagina
     * @return Ultima pagina, em Pagina
     */
    public Pagina ultPag(){
        pagina = ultimaPag;
        int i, s = tamanho - (pagina-1)*tamanhoPag;
        Pagina p = new Pagina(s);
        for(i = 0; i < s; i++) {p.add(lista.get((pagina-1)*tamanhoPag+i));}
        return p;
    }

    /**
     * Clone
     */
    public ListStrings clone(){return new ListStrings(this);}

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(String s:lista) sb.append(s).append("\n");
        return sb.toString();
    }

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        ListStrings ls = (ListStrings) o;
        if(tamanho != ls.tamanho()) return false;
        if(pagina != ls.getPagina()) return false;
        if(tamanhoPag != ls.getTamPag()) return false;
        if(ultimaPag != ls.getUltPag()) return false;
        ArrayList<String> al = (ArrayList<String>) ls.getLista();
        boolean b = true;
        int i;
        for(i = 0; i < tamanho && b;i++){
            b = lista.get(i).equals(al.get(i));
        }
        return b;
    }

    /**
     * hashCode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{lista});
    }
}
