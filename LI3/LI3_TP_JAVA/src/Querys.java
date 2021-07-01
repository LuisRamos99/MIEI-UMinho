import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.System.out;

public abstract class Querys implements IQuerys {

    public Querys() {}

    /**
     * Query 1
     * @param model IGestVendasModel
     * @param view IGestVendasView
     */
    public void query1(IGestVendasModel model, IGestVendasView view) {
        Crono.start();
        ListStrings ls = model.prodsNaoComprados();
        Crono.stop();
        out.println("Tempo decorrido: "+ Crono.print() + " segundos");
        out.println("Total: " + ls.tamanho() + "\n");
        view.imprimeLista(ls);
    }

    /**
     * Query 2
     * @param model IGestVendasModel
     */
    public void query2(IGestVendasModel model) {
        out.println("Insira um mes: ");
        int mes = Input.lerInt();
        if(mes<1||mes>12) {out.println("Mês inválido!");return;}
        Crono.start();
        HashMap<Integer,Integer> cDG = model.clientesDistintosGlobal();
        StringBuilder sb = new StringBuilder();
        sb.append("\nMes: ").append(mes).append("\n");
        sb.append("Nº de clientes diferentes que efetuaram compras: ").append(cDG.get(mes)).append("\n");
        sb.append("Nº total de vendas realizadas: ").append(model.numeroVendasMes(mes)).append("\n");
        out.println(sb.toString());
        out.println("Tempo decorrido: " + Crono.stop() + " segundos");
    }

    /**
     * Query 3
     * @param model IGestVendasModel
     */
    public void query3(IGestVendasModel model) {
        out.println("Indique um cliente: ");
        String cliente = Input.lerString();
        if (!model.existeCli(cliente)) {out.println("O Cliente não existe!");return;}
        Crono.start();
        HashMap<Integer,Integer> comprasMes = model.comprasPorMes(cliente);
        double x = Crono.stop();
        for(Map.Entry<Integer,Integer> m : comprasMes.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Mes: ").append(m.getKey()).append("\n");
            sb.append("Nº de compras: ").append(m.getValue()).append("\n");
            sb.append("Nº de produtos distintos: ").append(model.produtosDistintos(cliente,m.getKey())).append("\n");
            sb.append("Total gasto ").append(model.gastoTotal(cliente,m.getKey())).append("\n");
            out.println(sb.toString());
        }
        out.println("Tempo decorrido: " + x + " segundos");
    }

    /**
     * Query 4
     * @param model IGestVendasModel
     */
    public void query4(IGestVendasModel model) {
        out.println("Indique um produto: ");
        String produto = Input.lerString();
        if (!model.existeProd(produto)) {out.println("O Produto não existe!");return;}
        Crono.start();
        List<Fatura> lista = model.prodMensal(produto);
        int i=1;
        for(Fatura f : lista) {
            if (f!=null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Mes: ").append(i++).append("\n");
                sb.append("Nº de vezes que foi comprado: ").append(f.getNumVendasTotal()).append("\n");
                sb.append("Nº de clientes diferentes: ").append(f.getNumVendasTotal()).append("\n");
                sb.append("Total faturado ").append(f.getReceitasTotal()).append("\n");
                out.println(sb.toString());
            }
            else i++;
        }
        out.println("Tempo decorrido: " + Crono.stop() + " segundos");
    }

    /**
     * Query 5
     * @param model IGestVendasModel
     * @param view IGestVendasView
     */
    public void query5(IGestVendasModel model,IGestVendasView view) {
        out.println("Indique um cliente: ");
        String cliente = Input.lerString();
        if (!model.existeCli(cliente)) {out.println("O Cliente não existe!");return;}
        Crono.start();
        ListStrings ls = model.maiorQuantidade(cliente);
        out.println("Tempo decorrido: " + Crono.stop() + " segundos");
        view.imprimeLista1Coluna(ls);
    }


    /**
     * Query 6
     * @param model IGestVendasModel
     * @param view IGestVendasView
     */
    public void query6(IGestVendasModel model,IGestVendasView view) {
        out.println("Coloque o número de produtos que deseja visualizar: ");
        int num = Input.lerInt();
        Crono.start();
        ListStrings ls = model.maisComprados(num);
        Crono.stop();
        out.println("Tempo decorrido: " + Crono.stop() + " segundos\n");
        view.imprimeLista1Coluna(ls);
    }

    /**
     * Query 7
     * @param model IGestVendasModel
     */
    public void query7(IGestVendasModel model) {
        Crono.start();
        List<String> f1 = model.tresMaisCompFil(1);
        List<String> f2 = model.tresMaisCompFil(2);
        List<String> f3 = model.tresMaisCompFil(3);
        Crono.stop();
        StringBuilder sb = new StringBuilder();
        sb.append("\nFilial 1: ");
        for (String prod : f1) {sb.append(prod).append("; ");}
        sb.append("\nFilial 2: ");
        for (String prod : f2) {sb.append(prod).append("; ");}
        sb.append("\nFilial 3: ");
        for (String prod : f3) {sb.append(prod).append("; ");}
        out.println(sb.toString());
        out.println("\nTempo decorrido: "+ Crono.print() + " segundos");
    }

    /**
     * Query 8
     * @param model IGestVendasModel
     * @param view IGestVendasView
     */
    public void query8(IGestVendasModel model, IGestVendasView view) {
        out.println("Coloque o número de clientes que deseja visualizar: ");
        int num = Input.lerInt();
        Crono.start();
        ListStrings ls = model.cliMaisCompra(num);
        Crono.stop();
        out.println("Tempo decorrido: " + Crono.stop() + " segundos\n");
        view.imprimeLista1Coluna(ls);
    }

    /**
     * Query 9
     * @param model IGestVendasModel
     * @param view IGestVendasView
     */
    public void query9(IGestVendasModel model, IGestVendasView view) {
        out.println("Indique o produto ");
        String prod = Input.lerString();
        if (!model.existeProd(prod)) {out.println("O Produto não existe!");return ;}
        out.println("Indique o numero de clientes ");
        int n = Input.lerInt();
        Crono.start();
        ListStrings ls = model.prodMaisComp(prod,n);
        Crono.stop();
        out.println("Tempo decorrido: " + Crono.stop() + " segundos\n");
        view.imprimeLista1Coluna(ls);

    }

    /**
     * Query 10
     * @param model IGestVendasModel
     */
    public void query10(IGestVendasModel model) {
        Crono.start();
        List<Double> f1 = model.getFatTotalMes(1);
        List<Double> f2 = model.getFatTotalMes(2);
        List<Double> f3 = model.getFatTotalMes(3);
        Crono.stop();
        int i=0;
        out.println("\nMês\t\tFilial 1\t\t\tFilial 2\t\t\tFilial 3");
        while (i!=12) {
            StringBuilder sb = new StringBuilder();
            sb.append(i+1).append(" ");
            sb.append("\t\t").append(f1.get(i));
            sb.append("\t\t").append(f2.get(i));
            sb.append("\t\t").append(f3.get(i));
            out.println(sb.toString());
            i++;
        }
        out.println("\nTempo decorrido: "+ Crono.print() + " segundos");
    }


}
