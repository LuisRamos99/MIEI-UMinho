import java.io.Serializable;
import java.lang.*;
import static java.lang.System.out;

/**
 * Classe com a informação do view
 */
public class GestVendasView implements IGestVendasView,Serializable {

    /**
     * Variáveis
     */
    private Menu menu;

    /**
     * Construtor vazio
     */
    public GestVendasView() {
        menu = new Menu();

    }

    /**
     * Inicia o menu principal
     */
    public void iniMenuPrin() {
        menu = menuPrincipal();
        menu.executa();        
    }

    /**
     * Inicia o menu ler
     */
    public void iniMenuLer() {
        menu = menuLeituras();
        menu.executa();
    }

    /**
     * Inicia o menu estatisticas
     */
    public void iniMenuEstaticas() {
        menu = menuConsultas();
        menu.executa();
    }

    /**
     * Inicia o menu interativas
     */
    public void iniMenuInterativas() {
        menu = menuInterativas();
        menu.executa();
    }

    /**
     * Retorna a opçao escolhida pelo utilizador
     * @return Opção, em Integer
     */
    public int getOpcao() {
        return menu.getOpcao();
    }

    /**
     * Criação do menu principal
     * @return Menu principal, em Menu
     */
    private Menu menuPrincipal(){
        String[] opcoes = { "--------------------------------------------------------------------------",
                            "|                             GestãoDeVendas                             |",
                            "--------------------------------------------------------------------------",
                            "                                                                          ",
                            "* Bem vindo! Seleccione uma das opcoes seguintes:                         ",
                            "                                                                          ",
                            "**************************************************************************",
                            "*          1 - Leitura de Ficheiros                                      *",
                            "*          2 - Consultas Estatisticas                                    *",
                            "*          3 - Consultas Interactivas                                    *",
                            "*          4 - Guardar estado actual                                     *",
                            "*          5 - Carregar estado                                           *",
                            "*          0 - Sair da aplicacao                                         *",
                            "**************************************************************************"};
        
        menu = new Menu(opcoes);
        return menu;
    }

    /**
     * Criação do menu leituras
     * @return Menu leituras, em Menu
     */
    private Menu menuLeituras(){
        String[] opcoes = {"--------------------------------------------------------------------------",
                           "Leitura de ficheiros:                                                     ",
                           "                                                                          ",
                           "       1 - Ficheiro por omissão                                           ",
                           "       2 - Inserir novo ficheiro                                          ",
                           "       0 - Voltar ao menu                                                 ",
                           "                                                                          ",
                           "--------------------------------------------------------------------------"};
                        
        menu = new Menu(opcoes);
        return menu;            
    }

    /**
     * Criação do menu consultas
     * @return Menu consultas, em Menu
     */
    private Menu menuConsultas() {
        String[] opcoes = {"--------------------------------------------------------------------------",
                           "Consultas estatisticas:                                                ",
                           "                                                                          ",
                           "           1 - Obter estatisticas do ultimo ficheiro lido                 ",
                           "           2 - Obter estatisticas gerais                                  ",
                           "           0 - Voltar ao menu                                             ",
                           "                                                                          ",
                           "--------------------------------------------------------------------------"};

        menu = new Menu(opcoes);
        return menu;
    }

    /**
     * Criação do menu interativas
     * @return Menu interativas, em Menu
     */
    private Menu menuInterativas() {
        String[] opcoes = {"--------------------------------------------------------------------------",
                           "Consultas interativas:                                                    ",
                           "                                                                          ",
                           "           1 - Lista ordenada dos produtos não comprados e nº total       ",
                           "           2 - Dado um mês, obter nº total vendas e nº clientes diferentes",
                           "           3 - Dado um cliente, obter nº compras,nº produtos e total gasto",
                           "           4 - Dado um produto, obter nº vezes comprado,nº clientes       ",
                           "               distintos e o total facturado                              ",
                           "           5 - Dado um cliente, obter lista de produtos mais comprados    ",
                           "           6 - Obter X produtos mais comprados e nº de clientes distintos ",
                           "           7 - Para cada filial, obter lista dos 3 maiores compradores    ",
                           "           8 - Obter X clientes que compraram mais produtos diferentes    ",
                           "           9 - Dado um produto, obter X clientes que mais o compraram e   ",
                           "               para cada um o valor gasto                                 ",
                           "           10- Faturação total, mês a mês, filial a filial                ",
                           "           0 - Voltar ao menu                                             ",
                           "--------------------------------------------------------------------------"};

        menu = new Menu(opcoes);
        return menu;
    }

    /**
     * Imprime a lista com varias colunas
     * @param ls Lista de strings
     */
    public void imprimeLista(ListStrings ls){
        String s = "F";
        Pagina p = null;
        while(!(s.equals("M"))){
            if(s.equals("F")) p = ls.priPag();
            if(s.equals("L")) p = ls.ultPag();
            if(s.equals("N")) p = ls.proxPag();
            if(s.equals("A")) p = ls.antPag();
            int tam = p.tamanho(), i;
            if (tam>50) {
                for(i = 0; i < tam-50; i++) {
                    out.println(p.nextString(0,0) + " | " + p.nextString(10,0) + " | " + p.nextString(20,0)
                     + " | " + p.nextString(30,0)+ " | " + p.nextString(40,0)+ " | " + p.nextString(50,1));
                }
            }
            else if (tam>40) {
                for(i = 0; i < tam-40; i++) {
                    out.println(p.nextString(0,0) + " | " + p.nextString(10,0) + " | " + p.nextString(20,0)
                    + " | " + p.nextString(30,0)+ " | " + p.nextString(40,1));
                }
            }
            else if (tam>30) {
                for(i = 0; i < tam-30; i++) {
                    out.println(p.nextString(0,0) + " | " + p.nextString(10,0) + " | " + p.nextString(20,0) + " | " + p.nextString(30,1));
                }
            }
            else if (tam>20) {
                for(i = 0; i < 10; i++) {out.println(p.nextString(0,0) + " | " + p.nextString(10,0) + " | " + p.nextString(20,1));}
            }
            else if (tam>10) {
                for(i = 0; i < tam-10; i++) {out.println(p.nextString(0,0) + " | " + p.nextString(10,1));}
            }
            else {
                for(i = 0; i < tam; i++) {out.println(p.nextString(0,1));}
            }
            out.println("\nNº da pagina: " + ls.getPagina() + "/" + ls.getUltPag());
            out.println("\nProxima Pagina (N)|Pagina Anterior (A)|Primeira Pagina (F)|Ultima Pagina (L)\n");
            out.println("Regressar ao Menu (M)");
            s = Input.lerString();
            p.setIndice(0);
        }
    }

    /**
     * Imprime a lista só com uma coluna
     * @param ls Lista de strings
     */
    public void imprimeLista1Coluna(ListStrings ls){
        String s = "F";
        Pagina p = null;
        while(!(s.equals("M"))){
            if(s.equals("F")) p = ls.priPag();
            if(s.equals("L")) p = ls.ultPag();
            if(s.equals("N")) p = ls.proxPag();
            if(s.equals("A")) p = ls.antPag();
            int tam = p.tamanho(), i;
            for(i = 0; i < tam; i++) {out.println(p.nextString(0,1));}
            out.println("\nNº da pagina: " + ls.getPagina() + "/" + ls.getUltPag());
            out.println("\nProxima Pagina (N)|Pagina Anterior (A)|Primeira Pagina (F)|Ultima Pagina (L)\n");
            out.println("Regressar ao Menu (M)");
            s = Input.lerString();
            p.setIndice(0);
        }
    }
}
