import java.util.Arrays;
import java.util.HashSet;
import java.io.Serializable;

/**
 * Classe com a informação dos produtos.
 */
public class CatalogoProdutos implements Serializable,ICatalogoProdutos {

	/**
	 * Catálogo de produtos
	 */
	private HashSet<String> catProdutos;

	/**
	 * Construtor vazio
	 */
	public CatalogoProdutos(){
		catProdutos = new HashSet<>();
	}

	/**
	 * Construtor por cópia
	 * @param c Catalogo produtos
	 */
	public CatalogoProdutos(CatalogoProdutos c){
		catProdutos = c.getCatProd();
	}

	/**
	 * Retorna o catalogo de produtos
	 * @return Catalogo de produtos, em HashSet
	 */
	public HashSet<String> getCatProd(){
             HashSet<String> copia = new HashSet<>();
             catProdutos.stream().forEach(x->copia.add(x));
             return copia;
	}

	/**
	 * Adiciona um produto ao catalogo
	 * @param prod Codigo produto
	 */
	public void addCatProdutos(String prod){
            if(catProdutos==null) catProdutos = new HashSet<>();
            catProdutos.add(prod);
	}

	/**
	 * Verifica se determinado produto existe no catalogo
	 * @param s Codigo produto
	 */
	public boolean existe(String s){
	   return catProdutos.contains(s);
	}

	/**
	 * Retorna o tamanho do catalogo de produtos
	 * @return Tamanho do catalogo de produto, em Integer
	 */
	public int size(){
	    return catProdutos.size();
	}

	/**
	 * Equals
	 */
	public boolean equals(Object o){
		if(this == o) return true;
		if(this == null || this.getClass() != o.getClass()) return false;
		CatalogoProdutos c = (CatalogoProdutos) o;
		return catProdutos.retainAll(c.getCatProd());
	}

	/**
	 * Clone
	 */
	public CatalogoProdutos clone(){
		return new CatalogoProdutos(this);
	}

	/**
	 * toString
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		catProdutos.forEach(a-> sb.append(a).append("\n"));
		return sb.toString();
	}

	/**
	 * hashCode
	 */
	public int hashCode(){
		return Arrays.hashCode(new Object[]{catProdutos});
	}
	
}

