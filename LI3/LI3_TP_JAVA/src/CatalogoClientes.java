import java.util.Arrays;
import java.util.HashSet;
import java.io.Serializable;

/**
 * Classe com a informação dos clientes.
 */
public class CatalogoClientes implements Serializable,ICatalogoClientes {

    /**
     * Catálogo de clientes
     */
    private HashSet<String> catClientes;

    /**
     * Construtor vazio
     */
    public CatalogoClientes(){
        catClientes = new HashSet<>();
    }

    /**
     * Construtor por cópia
     * @param c Catalogo clientes
     */
    public CatalogoClientes(CatalogoClientes c){
        catClientes = c.getCatCli();
    }


    /**
     * Retorna o catalogo de clientes
     * @return Catalogo de clientes, em HashSet
     */
    public HashSet<String> getCatCli(){
       HashSet<String> copia = new HashSet<>();
                catClientes.stream().forEach(x->copia.add(x));
                return copia;
    }

    /**
     * Adiciona um cliente ao catalogo
     * @param cliente Codigo cliente
     */
    public void addCatClientes(String cliente){
            if(catClientes==null) catClientes = new HashSet<>();
            catClientes.add(cliente);
    }

    /**
     * Verifica se determinado cliente existe no catalogo
     * @param s Codigo cliente
     */
    public boolean existe(String s){
       return catClientes.contains(s);
    }

    /**
     * Retorna o tamanho do catalogo de clientes
     * @return Tamanho do catalogo de clientes, em Integer
     */
    public int size(){
        return catClientes.size();
    }

    /**
     * Equals
     */
    public boolean equals(Object o){
        if(this == o) return true;
        if(this == null || this.getClass() != o.getClass()) return false;
        CatalogoClientes c = (CatalogoClientes) o;
        return catClientes.retainAll(c.getCatCli());
    }

    /**
     * Clone
     */
    public CatalogoClientes clone(){
        return new CatalogoClientes(this);
    }

    /**
     * toString
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        catClientes.forEach(a-> sb.append(a).append("\n"));
        return sb.toString();
    }

    /**
     * hashCode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{catClientes});
    }
 }
