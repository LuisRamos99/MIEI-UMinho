import java.io.*;
import java.util.*;
import static java.lang.System.out;

public class UmCarroJaApp {
    private static UmCarroJa ucj;
    private static Menu menuInicial, menuCliente, menuProprietario,menuEscolhaAlug,menuTipoEscolhido;

    private UmCarroJaApp() {}

    public static void main(String[] args) throws NifJaExisteException {
        fazMenus();
        ucj = new UmCarroJa();
        try{carregarDados();}
        catch(IOException | ClassNotFoundException e){ucj.setMap(ucj.arrayVendas(ucj.readWithFiles("logsPOO_carregamentoInicial.bak")));}
        do {
            menuInicial.executa();
            switch (menuInicial.getOpcao()) {
                case 1:
                    adicionaUtilizador();
                    break;
                case 2:
                    iniciarSessao();
                    break;
                case 3:
                    getFaturadoDatas();
                    break;
                case 4:
                    ucj.top10ClientesUtilizacoes();
                    break;
                case 5:
                    ucj.top10ClientesKms();
                    break;
            }
            try{gravaEstado();}
            catch(IOException e){}
        } while (menuInicial.getOpcao() != 0);
    }


    private static void fazMenus() {
        String[] inicial = {
                "+-------------------------------------------------+",
                "+               MENU DE UTILIZADOR                +",
                "+-------------------------------------------------+",
                "+      1.  Registar utilizador                    +",
                "+      2.  Iniciar sessão                         +",
                "+      3.  Total faturado por uma viatura         +",
                "+      4.  Top10 clientes (número de utilizações) +",
                "+      5.  Top10 clientes (número de kms feitos)  +",
                "+      0.  Sair                                   +",
                "+-------------------------------------------------+"};

        String[] cliente = {
                "+-------------------------------------------------+",
                "+               MENU DE ClIENTE                   +",
                "+-------------------------------------------------+",
                "+      1.  Efetuar um aluguer                     +",
                "+      2.  Visualizar histórico de alugueres      +",
                "+      3.  Classificar um aluguer                 +",
                "+      0.  Sair                                   +",
                "+-------------------------------------------------+"};
        String[] proprietario = {
                "+-------------------------------------------------+",
                "+               MENU DE PROPRIETÁRIO              +",
                "+-------------------------------------------------+",
                "+      1.  Adicionar viatura                      +",
                "+      2.  Alterar preço por quilómetro           +",
                "+      3.  Visualizar viaturas que possui         +",
                "+      4.  Visualizar histórico de alugueres      +",
                "+      5.  Visualizar pedidos pendentes           +",
                "+      6.  Visualizar carros para abastecer       +",
                "+      7.  Abastecer um carro                     +",
                "+      8.  Obter autonomia de um carro            +",
                "+      0.  Sair                                   +",
                "+-------------------------------------------------+"};
        String[] escolhaAlug = {
                "+--------------------------------------------------------------------+",
                "+      Indique a sua preferência:                                    +",
                "+      1.  Veículo mais perto                                        +",
                "+      2.  Veículo mais barato                                       +",
                "+      3.  Veículo específico (por matrícula em formato XX-YY-ZZ)    +",
                "+      4.  Veículo com autonomia desejada                            +",
                "+      5.  Veículo mais barato e distância máxima a caminhar         +",
                "+      0.  Sair                                                      +",
                "+--------------------------------------------------------------------+"};

        String[] tipoEscolhido = {
                "+-------------------------------------------------+",
                "+      Indique a sua preferência:                 +",
                "+      1.  Veículo a gasolina                     +",
                "+      2.  Veículo híbrido                        +",
                "+      3.  Veículo elétrico                       +",
                "+      4.  Sem preferência                        +",
                "+      0.  Sair                                   +",
                "+-------------------------------------------------+"};

        menuInicial = new Menu(inicial);
        menuCliente = new Menu(cliente);
        menuProprietario = new Menu(proprietario);
        menuEscolhaAlug = new Menu(escolhaAlug);
        menuTipoEscolhido = new Menu(tipoEscolhido);
    }


    //********************************************************************************************************************//

    private static void iniciarSessao() {
        Scanner input = new Scanner(System.in);
        out.print('\u000C');
        out.println("");
        out.println("-> Iniciar sessão: ");
        out.print("NIF do Utilizador: ");
        String nifS = input.nextLine();
        out.print("Password: ");
        String pass = input.nextLine();
        int nif = Integer.parseInt(nifS);
        boolean entrou;
        try {entrou = ucj.iniciaSessao(nif, pass);}
        catch (NumberFormatException | SemAutorizacaoException e ) {out.println(e.getMessage());return;}
        if (!entrou) {out.println("Password errada!");return;}
        out.println("Sessão iniciada com sucesso!!");

        if ((ucj.getMap().get(nif)) instanceof Cliente) {
            do {
                Cliente c = (Cliente) (ucj.getMap().get(nif));
                menuCliente.executa();
                switch (menuCliente.getOpcao()) {

                    case 1:
                        adicionaAluguer(nif);
                        break;
                    case 2:
                        getHistAlugDatas(c,nif);
                        break;
                    case 3:
                        classificarAluguer(nif);
                        break;
                    case 0: break;
                    default: out.println("Opção inválida!");
                }
            } while (menuCliente.getOpcao() != 0);

        }
        if ((ucj.getMap().get(nif)) instanceof Proprietario) {
            do {
                Proprietario p = (Proprietario) (ucj.getMap().get(nif));
                menuProprietario.executa();
                switch (menuProprietario.getOpcao()) {
                    case 1: adicionaCarro(nif);
                         break;
                    case 2:
                        alteraPreco(nif);
                         break;
                    case 3:
                        out.println(ucj.getListaViaturas(nif).values());
                        break;
                    case 4:
                        getHistAlugDatas(p,nif);
                        break;
                    case 5:
                        aceitaAluguer(p.getPendentes(),nif);
                        break;
                    case 6:
                        out.println(ucj.listaCarrosSemAutonomia(nif));
                        break;
                    case 7:
                        abastece(nif);
                        break;
                    case 8:
                        obtemAutonomia(nif);
                        break;
                    case 0:
                        break;
                    default: out.println("Opção inválida!");
                }
            } while (menuProprietario.getOpcao() != 0) ;
        }
    }

    public static void classificarAluguer(int nif) {
        String resposta, classifica;
        StringBuilder st = new StringBuilder();
        Scanner input = new Scanner(System.in);
        Cliente c = (Cliente) ucj.getMap().get(nif);
        for (Aluguer a : c.getHistorico()) {
            if (a.getAvaliacao() == 0) {
                out.println(a);
                out.println("Deseja atribuir uma classificação a este aluguer? (S/N)");
                resposta = input.nextLine();
                String matricula = a.getCarro().getMatricula();
                if (resposta.equals("S")) {
                    ucj.getHashCarro(matricula).setDisponivel(true);
                    out.println("Indique o valor (0-100) da classificação. ");
                    classifica = input.nextLine();
                    st.append(matricula).append(",").append(classifica);
                    ucj.novaClass(st.toString());
                    a.setNifCliente(nif);
                    ucj.darUpdateAvaliacao(a,Integer.parseInt(classifica));
                    out.println("Classificação atribuída com sucesso. ");
                }
            }
        }
    }

    public static void obtemAutonomia(int nif) {
        Scanner input = new Scanner(System.in);
        String mat;
        out.println("Indique a matricula do veículo no formato XX-YY-ZZ: ");
        mat = input.nextLine();
        Proprietario p = (Proprietario) ucj.getMap().get(nif);
        out.println("\nAutonomia atual do veiculo: "+p.getConjunto().get(mat).getAutonomiaAtual()+"\n");
    }

    public static void adicionaUtilizador(){
        Scanner input = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        String nome, email, pass, morada, tipoUtilizador, nif;

        out.print('\u000C');
        out.println("------> Novo Utilizador <------");
        out.print("Nome: ");
        nome = input.nextLine();
        out.print("Nif: ");
        nif = input.nextLine();
        out.print("Email: ");
        email = input.nextLine();
        out.print("Password: ");
        pass = input.nextLine();
        out.print("Morada: ");
        morada = input.nextLine();
        out.println("Data de Nascimento: ");
        out.print("-> Dia: ");
        String dia = input.nextLine();
        out.print("-> Mes: ");
        String mes = input.nextLine();
        out.print("-> Ano: ");
        String ano = input.nextLine();
        int d,m,a;
        try {
            d = Integer.parseInt(dia);
            m = Integer.parseInt(mes);
            a = Integer.parseInt(ano);
        } catch (NumberFormatException erro) {out.println("Data de nascimento inválida!"); return;}
        GregorianCalendar cal = new GregorianCalendar(a, m, d);
        str.append(nome).append(",").append(nif).append(",");
        str.append(email).append(",").append(morada).append(",");
        str.append(0).append(",").append(0);

        out.println("Tipo de Utilizador (Cliente ou Motorista)");
        out.println("1 - Cliente");
        out.println("2 - Proprietário");
        out.print("Opção: ");
        tipoUtilizador = input.nextLine();

        try {Integer.parseInt(tipoUtilizador);}
        catch (NumberFormatException erro) { out.println(erro.toString());return;}
        if (tipoUtilizador.equals("1")) {
            try {ucj.novoCli(str.toString(), pass, cal);}
            catch (NifJaExisteException e) {out.println(e.getMessage());return;}
        }
        else if (tipoUtilizador.equals("2")) {
            try {ucj.novoProp(str.toString(), pass, cal);}
            catch (NifJaExisteException e) {out.println(e.getMessage());return;}
        }
        else {out.println("Sem sucesso!");return;}

        out.println("Utilizador criado com sucesso!");
    }

    public static void adicionaCarro(int nif) {
        Scanner input = new Scanner(System.in);
        String velMedia, precoKm, consumo, modelo, matricula, autonomia,cordX, cordY;
        StringBuilder st = new StringBuilder();
        out.print('\u000C');
        out.println("------> Novo Veículo <------");
        out.println("Tipo de Viatura:");
        out.println("1 - Carro a gasolina");
        out.println("2 - Carro elétrico");
        out.println("3 - Carro híbrido");
        out.print("Opcao: ");
        String tipo = input.nextLine();
        String tipoCarro = "";
        if (tipo.equals("1")) {tipoCarro = "Gasolina";}
        if (tipo.equals("2")) {tipoCarro = "Electrico";}
        if (tipo.equals("3")) {tipoCarro = "Hibrido";}
        int op;
        try {op = Integer.parseInt(tipo);}
        catch (NumberFormatException erro) {out.println("Opcao Invalida!");return;}
        if (op == 1 || op == 2 || op == 3) {
            out.println("Indique o modelo da viatura:");
            modelo = input.nextLine();
            out.println("Indique a matrícula da viatura:");
            matricula = input.nextLine();
            out.println("Velocidade média por Km : ");
            velMedia = input.next();
            out.println("Preço por Km: ");
            precoKm = input.next();
            out.println("Consumo da Viatura: ");
            consumo = input.next();
            out.println("Autonomia da Viatura: ");
            autonomia = input.next();
            out.println("Indique a coordenada X em que a viatura se encontra: ");
            cordX = input.next();
            out.println("Indique a coordenada Y em que a viatura se encontra: ");
            cordY = input.next();
            st.append(tipoCarro).append(",").append(modelo).append(",").append(matricula).append(",").append(nif).append(",").append(velMedia).append(",");
            st.append(precoKm).append(",").append(consumo).append(",").append(autonomia).append(",").append(cordX).append(",").append(cordY);
            ucj.novoCarro(st.toString());

        } else {out.println("Opção inválida!");}

        out.println("Viatura criada com sucesso!");
    }

    public static void adicionaAluguer(int nif){
        Scanner input = new Scanner(System.in);
        String cordXCliente, cordYCliente, cordXDestino,cordYDestino;
        out.print('\u000C');
        out.println("------> Novo Aluguer <------");
        out.println("Indique a coordenada X em que se encontra: ");
        cordXCliente = input.next();
        out.println("Indique a coordenada Y em que se encontra: ");
        cordYCliente = input.next();
        out.println("Indique a coordenada X do seu destino: ");
        cordXDestino = input.next();
        out.println("Indique a coordenada Y do seu destino: ");
        cordYDestino = input.next();
        Aluguer alug = new Aluguer();
        Ponto2D posDestino= new Ponto2D(Double.parseDouble(cordXDestino),Double.parseDouble(cordYDestino));
        alug.setCoordPartida(new Ponto2D(Double.parseDouble(cordXCliente),Double.parseDouble(cordYCliente)));
        alug.setCoordChegada(posDestino);
        alug.setNifCliente(nif);
        do {
            menuEscolhaAlug.executa();
            switch (menuEscolhaAlug.getOpcao()) {
                case 1:
                    alug = printaTiposPerto(alug);
                    adicionaAluguerQueue(alug);
                    return;
                case 2:
                       alug = printaTiposBarato(alug);
                    adicionaAluguerQueue(alug);
                    return;
                case 3:
                    alug = aluguerPorMatricula(alug);
                    adicionaAluguerQueue(alug);
                    return;
                case 4:
                    alug = autonomiaDesejada(alug);
                    adicionaAluguerQueue(alug);
                    return;
                case 5:
                    alug = veiculoMBDM(alug);
                    adicionaAluguerQueue(alug);
                    return;
                case 0:
                    break;
                default: out.println("Opção inválida!");
            }

        } while (menuEscolhaAlug.getOpcao() != 0) ;


    }

    public static Aluguer printaTiposBarato(Aluguer alug) {
        String matricula;
        String array[];
        do {
            menuTipoEscolhido.executa();
            switch (menuTipoEscolhido.getOpcao()) {
                case 1:
                    array = ucj.lista3MaisBarato(alug,"G");
                    matricula = aceitaCarros(array,alug);
                    if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
                    return alug;
                case 2:
                    array = ucj.lista3MaisBarato(alug,"H");
                    matricula = aceitaCarros(array,alug);
                    if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
                    return alug;
                case 3:
                    array = ucj.lista3MaisBarato(alug,"E");
                    matricula = aceitaCarros(array,alug);
                    if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
                    return alug;
                case 4:
                    array = ucj.lista3MaisBarato(alug,"T");
                    matricula = aceitaCarros(array,alug);
                    if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
                    return alug;
                case 0:
                    break;
                default: out.println("Opção inválida!");
            }
        } while (menuTipoEscolhido.getOpcao() != 0) ;
        return alug;
    }

    public static Aluguer printaTiposPerto(Aluguer alug) {
        String matricula;
        String array[];
        do {
            menuTipoEscolhido.executa();
            switch (menuTipoEscolhido.getOpcao()) {
                case 1:
                    array = ucj.lista3MaisPerto(alug,"G");
                    matricula = aceitaCarros(array,alug);
                    if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
                    return alug;
                case 2:
                    array = ucj.lista3MaisPerto(alug,"H");
                    matricula = aceitaCarros(array,alug);
                    if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
                    return alug;
                case 3:
                    array = ucj.lista3MaisPerto(alug,"E");
                    matricula = aceitaCarros(array,alug);
                    if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
                    return alug;
                case 4:
                    array = ucj.lista3MaisPerto(alug,"T");
                    matricula = aceitaCarros(array,alug);
                    if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
                    return alug;
                case 0:
                    break;
                default: out.println("Opção inválida!");
            }
        } while (menuTipoEscolhido.getOpcao() != 0) ;
        return alug;
    }

    private static void getHistAlugDatas(Ator at, int nif) {
        Scanner input = new Scanner(System.in);
        String diaI, mesI, anoI, diaF, mesF, anoF;
        out.print('\u000C');
        out.println("Indique as datas em que pretende verificar: ");
        out.println("Data Inicial: ");
        out.print("-> Dia: ");
        diaI = input.nextLine();
        out.print("-> Mês: ");
        mesI = input.nextLine();
        out.print("-> Ano: ");
        anoI = input.nextLine();
        out.println("Data Final: ");
        out.print("-> Dia: ");
        diaF = input.nextLine();
        out.print("-> Mês: ");
        mesF = input.nextLine();
        out.print("-> Ano: ");
        anoF = input.nextLine();
        int dia1,mes1,ano1,dia2,mes2,ano2;
        try {
            dia1 = Integer.parseInt(diaI);
            mes1 = (Integer.parseInt(mesI)-1);
            ano1 = Integer.parseInt(anoI);
            dia2 = Integer.parseInt(diaF);
            mes2 = (Integer.parseInt(mesF)-1);
            ano2 = Integer.parseInt(anoF);
        }
        catch (NumberFormatException e) {out.println("Opção Inválida!");return;}

        GregorianCalendar dataInicial = new GregorianCalendar(ano1, mes1, dia1);
        GregorianCalendar dataFinal = new GregorianCalendar(ano2, mes2, dia2);

        ArrayList<Aluguer> auxDatas ;
        ArrayList<Aluguer> auxInfo = new ArrayList<>();
        auxDatas= ucj.getHistoricoGeral(nif);

        for (Aluguer al : auxDatas) {
            int d = al.getDataAlug().getDayOfMonth();
            int m =  (al.getDataAlug().getMonthValue()-1);
            int a =  al.getDataAlug().getYear();
            GregorianCalendar dataAux = new GregorianCalendar(a, m, d);
            if ((dataAux.equals(dataInicial) || dataAux.equals(dataFinal)) ||
                    (dataAux).before(dataFinal) && (dataAux).after(dataInicial)) {
                auxInfo.add(al.clone());
            }
        }

       out.println(auxInfo.toString());
    }

    private static void getFaturadoDatas() {
        Scanner input = new Scanner(System.in);
        String mat,diaI, mesI, anoI, diaF, mesF, anoF;
        out.print('\u000C');
        out.println("Indique a matricula do veículo no formato XX-YY-ZZ: ");
        mat=input.nextLine();
        out.println("Indique as datas em que pretende verificar: ");
        out.println("Data Inicial: ");
        out.print("-> Dia: ");
        diaI = input.nextLine();
        out.print("-> Mês: ");
        mesI = input.nextLine();
        out.print("-> Ano: ");
        anoI = input.nextLine();
        out.println("Data Final: ");
        out.print("-> Dia: ");
        diaF = input.nextLine();
        out.print("-> Mês: ");
        mesF = input.nextLine();
        out.print("-> Ano: ");
        anoF = input.nextLine();
        int dia1,mes1,ano1,dia2,mes2,ano2;
        double total =0;
        try {
            dia1 = Integer.parseInt(diaI);
            mes1 = (Integer.parseInt(mesI)-1);
            ano1 = Integer.parseInt(anoI);
            dia2 = Integer.parseInt(diaF);
            mes2 = (Integer.parseInt(mesF)-1);
            ano2 = Integer.parseInt(anoF);
        }
        catch (NumberFormatException e) {out.println("Opção Inválida!");return;}
        GregorianCalendar dataInicial = new GregorianCalendar(ano1, mes1, dia1);
        GregorianCalendar dataFinal = new GregorianCalendar(ano2, mes2, dia2);
        ArrayList<Aluguer> auxDatas ;
        auxDatas= ucj.getTotalFaturado(mat);
        for (Aluguer al : auxDatas) {
            int d = al.getDataAlug().getDayOfMonth();
            int m =  (al.getDataAlug().getMonthValue()-1);
            int a =  al.getDataAlug().getYear();
            GregorianCalendar dataAux = new GregorianCalendar(a, m, d);
            if ((dataAux.equals(dataInicial) || dataAux.equals(dataFinal)) ||
                    (dataAux).before(dataFinal) && (dataAux).after(dataInicial)) {
                total += al.getPrecoEstimado();
            }
        }
        out.println(total);
    }

    public static void alteraPreco(int nif){
        Scanner input = new Scanner(System.in);
        String mat,novoPre;
        double novoPreco;
        out.println("Indique a matricula do veículo no formato XX-YY-ZZ: ");
        mat = input.nextLine();
        out.println("Indique o novo preço por km pretendido: ");
        novoPre = input.nextLine();
        novoPreco = Double.parseDouble(novoPre);
        out.println(novoPreco);
            if((ucj.getProp(nif).getConjunto().containsKey(mat))&&(ucj.getCarro().containsKey(mat))){
                ucj.getProp(nif).setPrecoMenu(novoPreco,mat);
                ucj.setPrecoHashCarro(novoPreco,mat);
            }
            else {out.println("VEÍCULO NÃO EXISTENTE!");return;}

        out.println("Dados alterados com sucesso!");
    }


        public static void abastece(int nif){
            Scanner input = new Scanner(System.in);
            String mat;
            System.out.println("Indique a matricula do veículo no formato XX-YY-ZZ: ");
            mat = input.nextLine();
            if (ucj.getProp(nif).getConjunto().containsKey(mat)) {
                ucj.getProp(nif).setNovaAutonomia(mat);
                ucj.setNovaAutonomia(mat);
            }
            else {out.println("Proprietário não contem nenhum carro com esta matricula!");return;}

            out.println("Carro abastecido com sucesso!");
        }


    public static Aluguer veiculoMBDM(Aluguer alug) {
        Scanner input = new Scanner(System.in);
        Double dist;
        out.println("Indique a distância que está dispoto a percorrer a pé: ");
        dist = Double.parseDouble(input.nextLine());
        String array[] = ucj.lista3MaisBaratoDistancia(alug, dist);
        String matricula = aceitaCarros(array,alug);
        if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
        return alug;
    }

    public static Aluguer aluguerPorMatricula(Aluguer alug) {
        Scanner input = new Scanner(System.in);
        String matricula,resposta;
        out.println("Indique a matricula: ");
        matricula = input.nextLine();
        alug=ucj.preencheAluguer(alug,matricula);
        out.println(alug);
        out.println("Aceita o aluguer? (S/N)");
        resposta = input.nextLine();
        if (resposta.equals("S")) return alug;
        else return null;
    }

    public static String aceitaCarros(String array[],Aluguer alug) {
        Scanner input = new Scanner(System.in);
        String resposta;
        out.println(ucj.getCarro().get(array[0]));
        out.println(ucj.preencheAluguer(alug,array[0]));
        out.println("Aceita este carro? (S/N)");
        resposta = input.nextLine();
        if (resposta.equals("S")) return array[0];
        else {
            out.println(ucj.getCarro().get(array[1]));
            out.println(ucj.preencheAluguer(alug,array[1]));
            out.println("Aceita este carro? (S/N)");
            resposta = input.nextLine();
            if (resposta.equals("S")) return array[1];
            else {
                out.println(ucj.getCarro().get(array[2]));
                out.println(ucj.preencheAluguer(alug,array[2]));
                out.println("Aceita este carro? (S/N)");
                resposta = input.nextLine();
                if (resposta.equals("S")) return array[2];
                else return null;
            }
        }
    }

    public static Aluguer autonomiaDesejada(Aluguer alug) {
        Scanner input = new Scanner(System.in);
        Double dist;
        out.println("Indique a autonomia desejada: ");
        dist = Double.parseDouble(input.nextLine());
        String array[]=ucj.lista3AutonomiaDesejada(dist);
        String matricula = aceitaCarros(array,alug);
        if (matricula!=null) alug=ucj.preencheAluguer(alug,matricula);
        return alug;
    }

    public static void adicionaAluguerQueue(Aluguer alug) {
        String mat = alug.getCarro().getMatricula();
        ucj.getMap().values().forEach(a-> {
            if(a instanceof Proprietario && ((Proprietario) a).getConjunto().containsKey(mat)) {
                ucj.adicionaAluguerQueue(a.getNif(),alug);
            }
        });
    }

    public static void aceitaAluguer(Queue<Aluguer> ll, int nif) {
        Scanner input = new Scanner(System.in);
        String resposta,deseja,classifica;
        StringBuilder st = new StringBuilder();
        Proprietario p = (Proprietario) ucj.getMap().get(nif);
        for(Aluguer a: ll){
        out.println(a);
        out.println("Aceita este Aluguer? (S/N)");
        resposta = input.nextLine();
        String matricula=a.getCarro().getMatricula();
        if (resposta.equals("S") && (ucj.getCarro().get(matricula).getDisponivel())){
            ucj.getHashCarro(matricula).setAutonomiaAtual(a.getCarro().getAutonomiaAtual()-a.getDistanciaAluguer());
            ucj.getHashCarro(matricula).setCoordenadas(a.getCoordChegada());
            ucj.getHashCarro(matricula).adicionaAluguer(a);
            ucj.getHashCarro(matricula).setDisponivel(false);
            Cliente c = (Cliente) ucj.getNif(a.getNifCliente());c.addAluguer(a);
            if (p.getConjunto().containsKey(matricula)) {ucj.update(nif,a);}
            out.println("Deseja atribuir uma classificação ao cliente? (S/N) ");
            deseja= input.nextLine();
                if(deseja.equals("S")){
                    out.println("Indique o valor (0-100) da classificação. ");
                    classifica=input.nextLine();
                    st.append(a.getNifCliente()).append(",").append(classifica);
                    ucj.novaClass(st.toString());
                    out.println("Classificação atribuída com sucesso. ");
                }
            out.println("Aluguer aceite!");
            }
            ll.remove();
        }
    }

    private static void gravaEstado() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Estado.dat"));
        oos.writeObject(ucj);
        oos.flush();
        oos.close();
    }

    private static void carregarDados() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Estado.dat"));
        ucj = (UmCarroJa) ois.readObject();
        ois.close();
    }

}


