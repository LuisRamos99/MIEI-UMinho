import static java.lang.System.out;
import java.io.*;

/**
 * Classe com a informação do controller
 */
public class GestVendasController implements IGestVendasController, Serializable {

    /**
     * Variáveis
     */
    private IGestVendasView view;
    private IGestVendasModel model;
    private boolean ficheiroLido;
    private boolean dadosLidos;

    /**
     * Construtor vazio
     */
    public GestVendasController() {
        view = new GestVendasView();
        model = new GestVendasModel();
    }

    /**
     * Set do view
     * @param vi View
     */
    public void setView(IGestVendasView vi) {view = vi;}

    /**
     * Set do model
     * @param mod Model
     */
    public void setModel(IGestVendasModel mod) {model = mod;}

    /**
     * Dá start à aplicação
     */
    public void start() {
        boolean acabou = false;
        int opcao;
        ficheiroLido = false;
        dadosLidos = false;
        while (!acabou) {
             view.iniMenuPrin();
            opcao = view.getOpcao();
            switch (opcao) {
                case 0:
                    acabou = true;
                    break;
                case 1:
                    lerFicheiros();
                    break;
                case 2:
                    consultasEstat();
                    break;
                case 3:
                    consultasInter();
                    break;
                case 4:
                    try{gravaEstado();}
                    catch(IOException e){out.println("Nao foi possivel guardar o estado!");}
                    break;
                case 5:
                    try {carregarDados();dadosLidos = true;}
                    catch(IOException | ClassNotFoundException e){out.println("Nao foi possivel guardar o estado!");}
                    break;
                default:
                    out.println("Opcao inválida!");
            }
        }
    }

    /**
     * Leitura dos ficheiros
     */
    public void lerFicheiros() {
        int opcao;
        String ficheiroVendas = "";
        view.iniMenuLer();
        opcao = view.getOpcao();
        switch (opcao) {
            case 0:
                break;
            case 1:
                ficheiroVendas = "Vendas_1M.txt";
                break;
            case 2:
                out.println("Indique o ficheiro de vendas a ler: ");
                ficheiroVendas = Input.lerString();
                break;
            default:
                out.println("Opcao Invalida!");
        }
        if (opcao != 0) {
            Crono.start();
            model.preencheFatFil(ficheiroVendas);
            out.println("Tempo total de preencher decorrido: " + Crono.stop() + " segundos");
            ficheiroLido = true;
            model.setNomeFicheiro(ficheiroVendas);
        }
    }

    /**
     * Consultas estatisticas
     */
    public void consultasEstat() {
        int opcao;
        if (ficheiroLido || dadosLidos) {
            view.iniMenuEstaticas();
            opcao = view.getOpcao();
            switch (opcao) {
                case 0:
                    break;
                case 1:
                    if (!ficheiroLido) {out.println("Ainda não leu nenhum ficheiro!");}
                    else out.println(model.getUF());
                    break;
                case 2:
                    model.estatisticaGeral();
                    break;
                default:
                    out.println("Opcao Invalida!");
            }
        } else out.println("Ainda não foram carregados dados!");
    }

    /**
     * Consultas interativas
     */
    public void consultasInter() {
        int opcao;
        Querys q = new Querys() {};
        if (ficheiroLido || dadosLidos) {
            view.iniMenuInterativas();
            opcao = view.getOpcao();
            switch (opcao) {
                case 0:
                    break;
                case 1:
                    q.query1(model,view);
                    break;
                case 2:
                    q.query2(model);
                    break;
                case 3:
                    q.query3(model);
                    break;
                case 4:
                    q.query4(model);
                    break;
                case 5:
                    q.query5(model,view);
                    break;
                case 6:
                    q.query6(model,view);
                    break;
                case 7:
                    q.query7(model);
                    break;
                case 8:
                    q.query8(model,view);
                    break;
                case 9:
                    q.query9(model,view);
                    break;
                case 10:
                    q.query10(model);
                    break;
                default:
                    out.println("Opcao Invalida!");
            }
        } else out.println("Ainda não foram carregados dados!");
    }

    /**
     * Gravar o estado
     */
    private void gravaEstado() throws IOException {
            Crono.start();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gest.dat"));
            oos.writeObject(model);
            oos.flush();
            oos.close();
            out.println("Estado gravado com sucesso!");
            out.println("Tempo decorrido: " + Crono.stop()+ " segundos");
    }

    /**
     * Carregar um estado
     */
    private void carregarDados() throws IOException,ClassNotFoundException {
         Crono.start();
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream("gest.dat"));
         model = (GestVendasModel) ois.readObject();
         ois.close();
         out.println("Estado lido com sucesso!");
         out.println("Tempo decorrido: " + Crono.stop()+ " segundos");
    }

}
