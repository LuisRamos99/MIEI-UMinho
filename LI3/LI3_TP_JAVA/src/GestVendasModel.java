import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.*;
import static java.lang.System.out;

/**
 * Classe com a informação do model
 */
public class GestVendasModel implements IGestVendasModel, Serializable {

    /**
     * Variáveis
     */
    private CatalogoClientes catClientes;
    private CatalogoProdutos catProdutos;
    private Filial filial1;
    private Filial filial2;
    private Filial filial3;
    private FaturacaoGlobal faturacaoGlobal;
    private UltimoFicheiro uf;

    /**
     * Construtor vazio
     */
    public GestVendasModel(){
      catClientes = new CatalogoClientes();
      catProdutos = new CatalogoProdutos();
      filial1 = new Filial();
      filial2 = new Filial();
      filial3 = new Filial();
      faturacaoGlobal = new FaturacaoGlobal();
      uf = new UltimoFicheiro();
    }

    /**
     * Preeche as variaveis catClientes e catProdutos
     */
    public void createData() {
        preencheCatCli("Clientes.txt");
        preencheCatProds("Produtos.txt");
        uf.setNumProds(catProdutos.size());
        uf.setNumClis(catClientes.size());
    }

    /**
     * Retorna true, se existe o produto, false caso contrário
     * @param codProd Código produto
     * @return Se existe produto, em Boolean
     */
    public boolean existeProd(String codProd) {
        return catProdutos.existe(codProd);
    }

    /**
     * Retorna true, se existe o cliente, false caso contrário
     * @param codCli Código cliente
     * @return Se existe cliente, em Boolean
     */
    public boolean existeCli(String codCli) {
        return catClientes.existe(codCli);
    }

    /**
     * Coloca um determinado nome na variável uf
     * @param nome Nome ficheiro
     */
    public void setNomeFicheiro(String nome) {this.uf.setnomeFicheiro(nome);}

    /**
     * Retorna o ultimo ficheiro lido
     * @return Ultimo ficheiro lido, em UltimoFicheiro
     */
    public UltimoFicheiro getUF() {
        return uf;
    }

    /**
     * Preenche a faturação e filial com um determinado ficheiro
     * @param vendas Nome do ficheiro
     */
    public void preencheFatFil(String vendas) {
        List<String> lvendas1 = readLinesWithBR(vendas);
        validaArrayVendas(arrayVendas(lvendas1));
    }

    /**
     * Separa uma linha, em varios campos de uma venda. Retorna essa venda
     * @param linha String
     * @return Venda, em Venda
     */
    public Venda linhaToVenda(String linha){
      String[] campos;
      String codProd, codCli, tipo;
      int mes,quant,filial;
      double preco;
        
      campos=linha.split(" ");
      codProd=campos[0];
      try{preco=Double.parseDouble(campos[1]);}
      catch(InputMismatchException | NumberFormatException exc){return null;}
      try{quant=Integer.parseInt(campos[2]);}
      catch(InputMismatchException | NumberFormatException exc){return null;}
      tipo=campos[3];
      codCli=campos[4];
      try{mes=Integer.parseInt(campos[5]);}
      catch(InputMismatchException | NumberFormatException exc){return null;}
      try{filial=Integer.parseInt(campos[6]);}
      catch(InputMismatchException | NumberFormatException exc){return null;}
      return new Venda(codProd,preco,quant,tipo,codCli,mes,filial);   
    }


    /**
     * Lê e coloca todas as linhas de um ficheiro numa lista
     * @param fichtxt Nome do ficheiro
     * @return Lista de linhas, em List
     */
    public List<String> readLinesWithBR(String fichtxt){
       List<String> linhas = new ArrayList<>();
       BufferedReader inFile;
       String linha;
       try{
         inFile = new BufferedReader(new FileReader(fichtxt));
         while((linha = inFile.readLine()) != null) {
           linhas.add(linha);
         }
       } catch (IOException e) {out.println(e);}
       return linhas;
    }

    /**
     * Lê e coloca todas as linhas de um ficheiro numa lista
     * @param fichtxt Nome do ficheiro
     * @return Lista de linhas, em List
     */
    public List<String> readWithFiles(String fichtxt){
      List<String> linhas = new ArrayList<>();
      try{
        linhas = Files.readAllLines(Paths.get(fichtxt), StandardCharsets.UTF_8);
      } catch (IOException exc) {out.println(exc);}
      return linhas;
    }

    /**
     * Valida uma venda, retornando um boolean
     * @param v Venda
     * @return Se a venda é válida, em Boolean
     */
    public boolean validaVenda(Venda v){
      boolean sucesso = true;
      if((v.getFilial() < 1 || v.getFilial() > 3) || (v.getMes() < 1 || v.getMes() > 12) 
           || (v.getPreco() < 0 || v.getPreco() > 999.99) || (v.getQuantidade() < 1 
           || v.getQuantidade() > 200) || (v.getTipo().equals("N") && v.getTipo().equals("P"))
           || (!catClientes.existe(v.getCodCli())) || (!catProdutos.existe(v.getCodProd())))
            {
      sucesso = false;
      }
      return sucesso;
    }

    /**
     * Valida a lista de linhas
     * @param vendas Lista de linhas
     */
    public void validaArrayVendas(List<Venda> vendas){
        int i=0;
        for(Venda v : vendas) {
            if (validaVenda(v)) {
                i++;
                preencheFil(v);
                preencheFat(v);
                uf.setfaturacaoTotal(v.getPreco()*v.getQuantidade());
                if (v.getPreco()==0) uf.inctotalComprasZero();
                uf.addProduto(v.getCodProd());
                uf.addCliente(v.getCodCli());
            }
        }
        uf.setVendasErradas(vendas.size()-i);
    }

    /**
     * Converte cada linha numa venda e retorna uma lista de vendas
     * @param linhas Lista de linhas
     * @return Lista de vendas, em List
     */
    public List<Venda> arrayVendas(List<String> linhas){

      List<Venda> vendas = new ArrayList<>();
      for(String v : linhas) {vendas.add(linhaToVenda(v));}
      return vendas;
    }

    /**
     * Insere um cliente
     * @param cli Codigo cliente
     */
    public void insereCli(String cli){
      if(catClientes == null) catClientes= new CatalogoClientes();
      catClientes.addCatClientes(cli);
    }

    /**
     * Insere um produto
     * @param prod Codigo produto
     */
    public void insereProd(String prod){
      if(catProdutos == null) catProdutos= new CatalogoProdutos();
      catProdutos.addCatProdutos(prod);
    }

    /**
     * Preenche a filial
     * @param v Venda
     */
    public void preencheFil(Venda v) {
        if (v.getFilial()==1){
            if(filial1 == null) filial1 = new Filial();
            filial1.insereProdutoPorCliente(v.clone());
            }
        if(v.getFilial()==2){
            if(filial2 == null) filial2 = new Filial();
            filial2.insereProdutoPorCliente(v.clone());
            }
        if(v.getFilial()==3){
            if(filial3 == null) filial3 = new Filial();
            filial3.insereProdutoPorCliente(v.clone());
        }
    }

    /**
     * Preenche a faturação
     * @param v Venda
     */
    public void preencheFat(Venda v){
        if (faturacaoGlobal == null) faturacaoGlobal = new FaturacaoGlobal();
        faturacaoGlobal.insereProdutoPorMes(v.clone());
    }

    /**
     * Preenche catalogo clientes
     * @param nome Nome do ficheiro
     */
    public void preencheCatCli(String nome) {
      List<String> lclis = readWithFiles(nome);
      for(String cli : lclis) {
          insereCli(cli);
      }
    }

    /**
     * Preenche catalogo produtos
     * @param nome Nome do ficheiro
     */
    public void preencheCatProds(String nome) {
      List<String> lprods = readWithFiles(nome);
      for(String prod : lprods) {
          insereProd(prod);
      }
    }

    /**
     * Retorna o numero de vendas num determinado mes
     * @param mes Mes
     * @return Numero de vendas por mes, em Integer
     */
    public int numeroVendasMes(int mes){
        return faturacaoGlobal.vendasMes(mes);
    }

    /**
     * Apresenta ao utilizador as estaticas gerais
     */
    public void estatisticaGeral(){
        int mes;
        double f1,f2,f3;
        HashMap<Integer,Integer> compMesf1 = filial1.cliCompMes();
        HashMap<Integer,Integer> compMesf2 = filial2.cliCompMes();
        HashMap<Integer,Integer> compMesf3 = filial3.cliCompMes();
        for (mes = 1; mes < 13; mes++) {
            f1 = faturacaoGlobal.faturacaoMesFilial(mes,1);
            f2 = faturacaoGlobal.faturacaoMesFilial(mes,2);
            f3 = faturacaoGlobal.faturacaoMesFilial(mes,3);
            StringBuilder sb = new StringBuilder();
            sb.append("\nMes: ").append(mes).append("\n");
            sb.append("Nº de compras: ").append(faturacaoGlobal.vendasMes(mes)).append("\n");
            sb.append("Nº de clientes diferentes -> Filial 1: ").append(compMesf1.get(mes)).append("; ");
            sb.append("Filial 2: ").append(compMesf2.get(mes)).append("; ");
            sb.append("Filial 3: ").append(compMesf3.get(mes)).append("\n");
            sb.append("Faturação -> Filial 1: ").append(f1).append("; ");
            sb.append("Filial 2: ").append(f2).append("; ");
            sb.append("Filial 3: ").append(f3).append("\n");
            sb.append("Faturação total: ").append(f1+f2+f3);
            out.println(sb.toString());
        }
    }


    /**
     * Retorna o map com o numero total de clientes distintos em cada mes
     * @return Numero de clientes distintos por mes, em HashMap
     */
    public HashMap<Integer,Integer> clientesDistintosGlobal(){
        HashMap<Integer,Integer> cDG;
        int x;
        cDG = filial1.cliCompMes();
        for (Map.Entry<Integer, Integer> k : cDG.entrySet()) {
             x = cDG.get(k.getKey());
             cDG.put(k.getKey(),x+1);
        }
        cDG = filial2.cliCompMes();
        for (Map.Entry<Integer, Integer> k : cDG.entrySet()) {
            x = cDG.get(k.getKey());
            cDG.put(k.getKey(), x+1);
        }
        cDG = filial3.cliCompMes();
        for (Map.Entry<Integer, Integer> k : cDG.entrySet()) {
            x = cDG.get(k.getKey());
            cDG.put(k.getKey(), x+1);
        }
        return cDG;
    }

    /**
     * Retorna o map com o numero de compras de um determinado cliente
     * @param cliente Codigo cliente
     * @return Numero de compras, em HashMap
     */
    public HashMap<Integer,Integer> comprasPorMes(String cliente){
        HashMap<Integer,Integer> compMes = new HashMap<>(12,1);
        int i=1;
        while(i!=13)
            compMes.put(i++,0);
        filial1.getFilial().get(cliente).getProdutosComprados().entrySet().forEach((me) -> {
            int j = compMes.get(me.getValue().getMes());
            compMes.put(me.getValue().getMes(),j+1);
        });
        filial2.getFilial().get(cliente).getProdutosComprados().entrySet().forEach((me) -> {
            int j = compMes.get(me.getValue().getMes());
            compMes.put(me.getValue().getMes(),j+1);
        });
        filial3.getFilial().get(cliente).getProdutosComprados().entrySet().forEach((me) -> {
            int j = compMes.get(me.getValue().getMes());
            compMes.put(me.getValue().getMes(),j+1);
        });
        return compMes;
    }

    /**
     * Retorna o numero de produtos distintos  num determinado mes e cliente
     * @param mes Mes
     * @param cliente Codigo cliente
     * @return Numero de produtos distintos, em Integer
     */
    public int produtosDistintos(String cliente, int mes){
        HashSet<String> hs = new HashSet<>();
        filial1.getFilial().get(cliente).getProdutosComprados().entrySet().forEach((me) -> {
            if (me.getValue().getMes()==mes) hs.add(me.getKey());
        });
        filial2.getFilial().get(cliente).getProdutosComprados().entrySet().forEach((me) -> {
            if (me.getValue().getMes()==mes) hs.add(me.getKey());
        });
        filial3.getFilial().get(cliente).getProdutosComprados().entrySet().forEach((me) -> {
            if (me.getValue().getMes()==mes) hs.add(me.getKey());
        });
        return hs.size();
    }

    /**
     * Retorna o gasto total de num determinado cliente, num determinado mes
     * @param mes Mes
     * @param cliente Codigo cliente
     * @return Gasto total, em Double
     */
    public double gastoTotal(String cliente, int mes){
       double p=0;
       p+=filial1.getFilial().get(cliente).gastoTotal(mes);
       p+=filial2.getFilial().get(cliente).gastoTotal(mes);
       p+=filial3.getFilial().get(cliente).gastoTotal(mes);
       return p;
    }

    /**
     * Retorna a lista de faturas de um determinado produto
     * @param prod Codigo produto
     * @return Lista de Faturas, em List
     */
    public List<Fatura> prodMensal(String prod){
        List<Fatura>  lista = new ArrayList<>(12);
        int i;
        for(i = 1; i < 13; i++){
            lista.add(faturacaoGlobal.getProdMes(prod,i));
        }
        return lista;
    }


    /**
     * Retorna a lista de strings dos tres clientes que mais compraram por filial
     * @param filial Filial
     * @return Lista de strings, em List
     */
    public List<String> tresMaisCompFil(int filial){
        List<String> tmc = new ArrayList<>();
        NavigableMap<Double,String> fil;
        if (filial==1) fil = filial1.maisFaturado().descendingMap();
        else if (filial==2) fil = filial2.maisFaturado().descendingMap();
        else fil = filial3.maisFaturado().descendingMap();
        Iterator<Map.Entry<Double,String>> it = fil.entrySet().iterator();
        int i=0;
        while(it.hasNext() && i!=3){
            Map.Entry<Double,String> m2=it.next();
            String str = m2.getValue();
            tmc.add(str);
            i++;
        }
        return tmc;
    }

    /**
     * Retorna a lista com a faturação total em cada mes numa determinada por filial
     * @param filial Filial
     * @return Lista de valores, em List
     */
    public List<Double> getFatTotalMes(int filial) {
       return faturacaoGlobal.getFatTotalMes(filial);
    }

    /**
     * Retorna a lista dos produtos não comprados
     * @return Produtos nao comprados, em ListStrings
     */
    public ListStrings prodsNaoComprados(){
        TreeSet<String> ts = new TreeSet<>();
        HashSet<String> hs = catProdutos.getCatProd();
        ListStrings ls = new ListStrings();
        hs.stream().filter((s) -> (!faturacaoGlobal.prodFoiComprado(s))).forEach((s) -> ts.add(s));
        ts.forEach((s) -> ls.add(s));
        ls.actualizaIndices();
        return ls;
    }

    /**
     * Retorna a lista dos produtos que um cliente mais comprou
     * @param cliente Codigo cliente
     * @return Maior quantidade, em ListStrings
     */
    public ListStrings maiorQuantidade(String cliente){
        HashMap<String,Integer> quantComp = new HashMap<>();
        TreeSet<ParCodProdQuantComp> ts = new TreeSet<>();
        ListStrings ls = new ListStrings(10);

        quantComp= maiorQuant(quantComp,cliente,filial1);
        quantComp= maiorQuant(quantComp,cliente,filial2);
        quantComp= maiorQuant(quantComp,cliente,filial3);

        for(Map.Entry<String,Integer> si : quantComp.entrySet()){
            ParCodProdQuantComp pcq = new ParCodProdQuantComp(si.getKey(),si.getValue());
            ts.add(pcq.clone());
        }
        for(ParCodProdQuantComp p : ts){ls.add(p.toString());}
        ls.actualizaIndices();
        return ls;
    }

    /**
     * Retorna a lista de strings dos tres clientes que mais compraram por filial
     * @param quantComp HashMap
     * @param cliente Codigo cliente
     * @param f Filial
     * @return Maior quantidade, em HashMap
     */
    private HashMap<String,Integer> maiorQuant(HashMap<String,Integer> quantComp, String cliente,Filial f){
        f.getInfoVendaCli(cliente).getProdutosComprados().entrySet().forEach((me) -> {
            if(quantComp.containsKey(me.getKey())){
                Integer i = quantComp.get(me.getKey());
                quantComp.put(me.getKey(),i+me.getValue().getQuantidade());
            }
            else quantComp.put(me.getKey(),me.getValue().getQuantidade());
        });
        return quantComp;
    }


    /**
     * Retorna a lista do numero de clientes distintos
     * @param n Numero pedido pelo utilizador
     * @return Lista dos mais comprados, em ListStrings
     */
    public ListStrings maisComprados(int n){
        TreeSet<ParCodProdQuantComp> ts = new TreeSet<>();
        HashSet<String> hs = catProdutos.getCatProd();
        int i=0,x;
        ListStrings ls = new ListStrings(10);
        hs.forEach((s) -> ts.add(faturacaoGlobal.totalComprado(s).clone()));
        for(ParCodProdQuantComp pcq : ts) {
            if (i<n) {
                x = numeroDistClientes(pcq.getcodProd());
                StringBuilder sb = new StringBuilder();
                sb.append(pcq.toString()).append(" | ").append("Clientes distintos: ").append(x);
                ls.add(sb.toString());
                i++;
            }
            else break;
        }
        ls.actualizaIndices();
        return ls;
    }


    /**
     * Retorna o numero de clientes distintos num determinado produto
     * @param codProd Codigo produto
     * @return Numero de clientes, em Integer
     */
    public int numeroDistClientes(String codProd) {
        HashSet<String> listaClis = new HashSet<>();
        listaClis=filial1.numeroDistClientes(listaClis,codProd);
        listaClis=filial2.numeroDistClientes(listaClis,codProd);
        listaClis=filial3.numeroDistClientes(listaClis,codProd);
        return listaClis.size();
    }



    /**
     * Retorna a lista de strings dos clientes que mais compraram
     * @param n Numero pedido pelo utilizador
     * @return Clientes que mais compraram, em ListStrings
     */
    public ListStrings cliMaisCompra(int n){
        TreeMap<String,Integer> hm = new TreeMap<>();
        HashMap<String,HashSet<String>> aux = new HashMap<>();
        TreeSet<ParCodProdQuantComp> ts = new TreeSet<>();
        int i=0;
        ListStrings ls = new ListStrings(10);
        aux = cliMais(aux,filial1);
        aux = cliMais(aux,filial2);
        aux = cliMais(aux,filial3);
        for(Map.Entry<String,HashSet<String>> m: aux.entrySet()){hm.put(m.getKey(),m.getValue().size());}
        hm.entrySet().forEach((e) -> ts.add(new ParCodProdQuantComp(e.getKey(),e.getValue())));
        for(ParCodProdQuantComp pcq : ts) {
            if (i<n) {ls.add(pcq.toStringQuery8());i++;}
            else break;
        }
        ls.actualizaIndices();
        return ls;
    }


    /**
     * Auxiliar da "cliMaisCompra" .Retorna o map com os produtos, e a lista de clientes em cada produto
     * @param aux HashMap
     * @param f Filial
     * @return Clientes que mais compraram, em HashMap
     */
    public HashMap<String,HashSet<String>> cliMais(HashMap<String,HashSet<String>> aux, Filial f){
        HashMap<String,InfVendaCli> ig = f.getFilial();
        ig.forEach((a,b)-> {
            if (aux.containsKey(a)) {b.getProdutosComprados().forEach((c,g)-> aux.get(a).add(c));}
            else {
                HashSet <String> nova = new HashSet<>();
                b.getProdutosComprados().forEach((c,g)-> nova.add(c));
                aux.put(a,nova);
            }
        });
        return aux;
    }


    /**
     * Retorna a lista de produtos mais comprados
     * @param n Numero pedido pelo utilizador
     * @param prod Codigo produto
     * @return Produtos mais comprados, em ListStrings
     */
    public ListStrings prodMaisComp(String prod, int n){
        int i=0;
        double gasto;
        HashMap<String,InfVendaProd> aux = new HashMap<>();
        ListStrings ls = new ListStrings(10);
        TreeSet<TriploCodCliQuantReceita> ts = new TreeSet<>();
        aux = prodMais(prod,aux,filial1);
        aux = prodMais(prod,aux,filial2);
        aux = prodMais(prod,aux,filial3);
        for(Map.Entry<String,InfVendaProd> si : aux.entrySet()){
            InfVendaProd ivp = si.getValue();
            gasto=ivp.getQuantidade()*ivp.getPreco();
            TriploCodCliQuantReceita tcqr = new TriploCodCliQuantReceita(si.getKey(),gasto,ivp.getQuantidade());
            ts.add(tcqr.clone());
        }
        for(TriploCodCliQuantReceita tcqr : ts) {
            if (i<n) {
                ls.add(tcqr.toString());
                i++;
            }
            else break;
        }
        ls.actualizaIndices();
        return ls;
    }

    /**
     * Auxiliar da "prodMaisComp". Retorna a lista de strings dos clientes que mais compraram
     * @param aux HashMap
     * @param prod Codigo produto
     * @param f Filial
     * @return Produtos mais comprados, em HashMap
     */
    public HashMap<String,InfVendaProd> prodMais(String prod, HashMap<String,InfVendaProd> aux, Filial f) {
        f.getFilial().forEach((a,b)->{
            if (b.containsProd(prod)) {
                InfVendaProd ifp = b.getInfVendaProd(prod);
                if (aux.containsKey(a)) {
                    InfVendaProd i = aux.get(a);
                    if (i.getQuantidade()<ifp.getQuantidade()) aux.put(a,ifp);
                }
                else aux.put(a,ifp);
            }
        });
        return aux;
    }

}
