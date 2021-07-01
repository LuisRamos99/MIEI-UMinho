import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.Serializable;

public class Menu implements Serializable {
    private List<String> escolhas;
    private int nEscolhas;


    public Menu(String[] opcoes) {
        this.escolhas = new ArrayList<String>();
        for (String op : opcoes) this.escolhas.add(op);
        this.nEscolhas = 0;
    }

    public Menu(){
        this.escolhas = new ArrayList<String>();
        this.nEscolhas = 0;
    }

    public void executa() {
        showMenu();
        this.nEscolhas = lerOpcao();
    }

    public void showMenu() {
        for(String opcoes: escolhas){
            System.out.println(opcoes);
        }
    }

    private int lerOpcao() {
        int op;
        Scanner is = new Scanner(System.in);

        System.out.print("Opção: ");
        try{
            op = is.nextInt();
        }
        catch(InputMismatchException e){
            op=-1;
        }
        if (op<0 || op>this.escolhas.size()) {
            System.out.println("Opção Inválida!!!");
            return lerOpcao();
        }
        return op;
    }


    public int getOpcao() {
        return this.nEscolhas;
    }
}
