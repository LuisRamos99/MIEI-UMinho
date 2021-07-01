import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.Serializable;
import static java.lang.System.out;

/**
 * Classe com a informação do menu
 */
public class Menu implements Serializable,IMenu {

    /**
     * Variáveis
     */
    private int numEscolhido;
    private List<String> escolhas;

    /**
     * Construtor vazio
     */
    public Menu(){
        this.escolhas = new ArrayList<>();
        this.numEscolhido = 0;
    }

    /**
     * Construtor por parametros
     * @param opcoes Array de Strings
     */
    public Menu(String[] opcoes) {
        this.escolhas = new ArrayList<>();
        for (String linha : opcoes) 
            this.escolhas.add(linha);
        this.numEscolhido = 0;
    }

    /**
     * Executa o menu
     */
    public void executa() {
        for(String opcoes: escolhas){
            out.println(opcoes);
        }
        this.numEscolhido = lerOpcao();
    }

    /**
     * Lê a opção, validando a opção dada e retornando o valor da mesma
     * @return Opção, em Integer
     */
    public int lerOpcao() {
        int opcao; 
        Scanner is = new Scanner(System.in);
        
        out.print("Opção: ");
        try{
            opcao = is.nextInt();
        }
        catch(InputMismatchException e) {opcao=-1;}
        if (opcao<0 || opcao>this.escolhas.size()) {
            System.out.println("Opção Inválida!");
            return lerOpcao();
        }
        return opcao;
    }

    /**
     * Retornando a opção
     * @return Opção, em Integer
     */
    public int getOpcao() {
        return this.numEscolhido;
    }
}
