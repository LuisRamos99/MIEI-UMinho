import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.lang.*;
import static java.lang.System.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.stream.Collectors;


public class UmCarroJa implements Serializable {

        private HashMap<Integer, Ator> map;
        private HashMap<String, Carro> carro;

        public UmCarroJa() {
            this.map = new HashMap<>();
            this.carro = new HashMap<>();
        }

        public UmCarroJa(UmCarroJa ucj) {
            this.map = ucj.getMap();
            this.carro = ucj.getCarro();
        }

        public UmCarroJa(HashMap<Integer, Ator> map) {
            for (Ator a : map.values()) {this.map.put(a.getNif(), a.clone());}
            for (Carro c : carro.values()) {this.carro.put(c.getMatricula(), c.clone());}
        }

        public void setNovaAutonomia(String mat){this.carro.get(mat).setAutonomiaAtual(carro.get(mat).getAutonomiaInicial());}

        public HashMap<Integer, Ator> getMap() {
            HashMap<Integer, Ator> copiaAtor = new HashMap<>();
            for (Ator a : this.map.values()) {copiaAtor.put(a.getNif(), a.clone());}
            return copiaAtor;
        }

        public Proprietario getProp(int nif){return((Proprietario) (map.get(nif)));}

        public Carro getHashCarro(String mat){return (carro.get(mat));}

        public HashMap<String, Carro> getCarro() {
            HashMap<String, Carro> copiaCarro = new HashMap<>();
            for (Carro c : this.carro.values()) {copiaCarro.put(c.getMatricula(), c.clone());}
            return copiaCarro;
        }

        public Ator getNif(int x) {return map.get(x);}

        public void setMap(HashMap<Integer, Ator> map) {
            for (Ator a : map.values()) {this.map.put(a.getNif(), a.clone());}
        }

        public void setCarro(HashMap<String, Ator> carro) {
            this.carro.clear();
            for (Ator a : map.values()) {this.map.put(a.getNif(), a.clone());}
        }

        public void setPrecoHashCarro(double preco, String mat){this.carro.get(mat).setPrecoKm(preco);}

    //**************************************************************************************************************************

        public Carro update(int nif, Aluguer alug){
            String matricula =alug.getCarro().getMatricula();
            Proprietario p = (Proprietario) map.get(nif);
            Carro c = p.getConj(matricula);
            c.setAutonomiaAtual(alug.getCarro().getAutonomiaAtual()-alug.getDistanciaAluguer());
            c.setCoordenadas(alug.getCoordChegada());
            c.setDisponivel(false);
            p.adicionaAluguer(alug,matricula);
            return c;
        }

        public HashMap<Integer, Ator> lePrimArg(String linha, HashMap<Integer, Ator> map) throws NifJaExisteException {
            String[] campos;
            String x;
            campos = linha.split(":");
            GregorianCalendar g = new GregorianCalendar();
            LocalDate j = LocalDate.now();
            x = campos[0];
            if (x.equals("NovoCliente")) {novoCli(campos[1], "", g);}
            else if (x.equals("NovoProp")) {novoProp(campos[1], "", g);}
            else if (x.equals("NovoCarro")) {novoCarro(campos[1]);}
            else if (x.equals("Aluguer")) {novoAluguer(campos[1],j);}
            else if (x.equals("Classificar")) {novaClass(campos[1]);}
            return map;
        }

        public void novoProp(String linha, String password, GregorianCalendar data) throws NifJaExisteException{
            String[] campos;
            campos = linha.split(",");
            String nome, email, morada;
            int nif;
            Ator at = new Proprietario();
            try {
                nome = campos[0];
                nif = Integer.parseInt(campos[1]);
                email = campos[2];
                morada = campos[3];
            } catch (InputMismatchException | ArrayIndexOutOfBoundsException | NumberFormatException exc) {return;}
            if (!map.containsKey(nif)) {
                at.setNome(nome);
                at.setNif(nif);
                at.setEmail(email);
                at.setMorada(morada);
                at.setPassword(password);
                at.setDataNascimento(data);
                map.put(nif, at);
            }
            else throw new NifJaExisteException("Nif já existente!!");
        }

        public void novoCli(String linha, String password, GregorianCalendar data) throws NifJaExisteException {
            String[] campos;
            campos = linha.split(",");
            String nome, email, morada;
            int nif;
            Ponto2D posicao;
            Ator at = new Cliente();
            try {
                nome = campos[0];
                nif = Integer.parseInt(campos[1]);
                email = campos[2];
                morada = campos[3];
                posicao = new Ponto2D(Double.parseDouble(campos[4]), Double.parseDouble(campos[5]));

            } catch (InputMismatchException | ArrayIndexOutOfBoundsException | NumberFormatException exc) {return;}
            if (!map.containsKey(nif)) {
                at.setNome(nome);
                at.setNif(nif);
                at.setEmail(email);
                at.setMorada(morada);
                ((Cliente) at).setCoordenadas(posicao);
                at.setPassword(password);
                at.setDataNascimento(data);
                map.put(nif, at);
            } else throw new NifJaExisteException("Nif já existente!!");

        }


        public void novoCarro(String linha) {
            String[] campos;
            campos = linha.split(",");
            String matricula, modelo;
            int nif;
            double velMedia, precoKm, consumo, autonomia;
            Ponto2D posicao;
            String tipo = campos[0];
            Carro ca = null;
            try {
                modelo = campos[1];
                matricula = campos[2];
                nif = Integer.parseInt(campos[3]);
                velMedia = Double.parseDouble(campos[4]);
                precoKm = Double.parseDouble(campos[5]);
                consumo = Double.parseDouble(campos[6]);
                autonomia = Double.parseDouble(campos[7]);
                posicao = new Ponto2D(Double.parseDouble(campos[8]), Double.parseDouble(campos[9]));
            } catch (InputMismatchException | ArrayIndexOutOfBoundsException | NumberFormatException exc) {return;}
            if (tipo.equals("Electrico")) {ca = new Eletrico(velMedia, precoKm, consumo, matricula, modelo, autonomia, posicao);}
            else if (tipo.equals("Hibrido")) {ca = new Hibrido(velMedia, precoKm, consumo, matricula, modelo, autonomia, posicao);}
            else if (tipo.equals("Gasolina")) {ca = new Gasolina(velMedia, precoKm, consumo, matricula, modelo, autonomia, posicao);}
            final Carro ca2 = ca;
            carro.put(matricula, ca2);
            map.forEach((nome, ator) -> {
                if ((ator.getNif() == (nif)) && ator instanceof Proprietario) {((Proprietario) ator).addCarro(ca2);}
            });
        }


        public void novoAluguer(String linha, LocalDate data) {
        String[] campos = null;
        campos = linha.split(",");
        String preferencia, tipo;
        int nif;
        Ponto2D posDestino;
        Aluguer alug = new Aluguer();
        try {
            nif = Integer.parseInt(campos[0]);
            posDestino = new Ponto2D(Double.parseDouble(campos[1]), Double.parseDouble(campos[2]));
            tipo = campos[3];
            preferencia = campos[4];
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException | NumberFormatException exc) {return ; }
        Cliente c = (Cliente) map.get(nif);
        alug.setCoordChegada(posDestino);
        alug.setDataAlug(data);
        String mat="";
        alug.setCoordPartida(c.getCoordenadas());
        if (preferencia.equals("MaisBarato")) {
            if (tipo.equals("Electrico")) mat = procuraMaisBaratoE(alug);
            if (tipo.equals("Gasolina")) mat = procuraMaisBaratoG(alug);
            if (tipo.equals("Hibrido")) mat = procuraMaisBaratoH(alug);
        }
        else if (preferencia.equals("MaisPerto")) {
            if (tipo.equals("Electrico")) mat = procuraMaisPertoE(alug);
            if (tipo.equals("Gasolina")) mat = procuraMaisPertoG(alug);
            if (tipo.equals("Hibrido")) mat = procuraMaisPertoH(alug);
        }
        final String mat2 = mat;
        Carro car = carro.get(mat2).clone();
        alug.setCarro(car);
        alug.setPrecoEstimado(calculaCustoEstimadoViagem(car.getCoordenadas(), posDestino,car));
        alug.setTempoReal(calculaTempoViagem(car.getCoordenadas(), posDestino,car));
        alug.setDistanciaAluguer(calculaDistanciaViagem(car.getCoordenadas(),posDestino));
        alug.setDistanciaInicial(calculaDistanciaViagem(c.getCoordenadas(),car.getCoordenadas()));
        carro.get(mat2).adicionaAluguer(alug);
        c.addAluguer(alug);
        carro.get(mat2).setCoordenadas(alug.getCoordChegada());
        carro.get(mat2).setAutonomiaAtual(car.getAutonomiaAtual()-alug.getDistanciaAluguer());
        c.setCoordenadas(alug.getCoordChegada());
        map.values().forEach(a-> {
            if (a instanceof Proprietario && ((Proprietario) a).getConjunto().containsKey(mat2)) {
                ((Proprietario) a).adicionaAluguer(alug,mat2);
            }
        });
    }

        public List<Carro> listaCarrosSemAutonomia (int nif) {
            Proprietario p = (Proprietario) map.get(nif);
            return p.getConjunto().values().stream().filter(x-> autonomiaAbaixo10(x.getAutonomiaInicial(),x.getAutonomiaAtual())).collect(Collectors.toList());
        }

        public boolean autonomiaAbaixo10 (double autonomiaInicial, double autonomiaAtual) {
            double autonomiaMinima = autonomiaInicial - autonomiaInicial*0.1;
            if (autonomiaMinima > autonomiaAtual) return true;
            else return false;
        }

        public double calculaDistanciaViagem (Ponto2D inicio, Ponto2D fim) {
            return (Math.sqrt(Math.pow(fim.getX() - inicio.getX(), 2) + Math.pow(fim.getY() - inicio.getY(), 2)));
        }

        public double calculaTempoViagem (Ponto2D inicio, Ponto2D fim, Carro t) {
            double distViagem = calculaDistanciaViagem(inicio, fim);
            return ((distViagem)/(t.getVelMedia()));

        }

        public double calculaCustoEstimadoViagem (Ponto2D inicio, Ponto2D fim, Carro t) {
            double distViagem = calculaDistanciaViagem(inicio, fim);
            return (distViagem * (t.getPrecoKm()));
          }

        public String procuraMaisPertoE(Aluguer alug) {
            double x = 1000;
            String f = null;
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                double o = c.getAutonomiaAtual();
                if (calculaDistanciaViagem(alug.getCoordPartida(), c.getCoordenadas()) < x && c.getDisponivel() && c instanceof Eletrico && k<o) {
                    x = calculaDistanciaViagem(alug.getCoordPartida(), c.getCoordenadas());
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public String[] lista3MaisPerto (Aluguer alug, String tipo) {
            int i;
            String x="";
            String array[] = new String[3];
            for(i=0;i<3;i++) {
                if (tipo.equals("E")) x = procuraMaisPertoE(alug);
                if (tipo.equals("G")) x = procuraMaisPertoG(alug);
                if (tipo.equals("H")) x = procuraMaisPertoH(alug);
                if (tipo.equals("T")) x = procuraMaisPertoTotal(alug);
                final String x2 = x;
                array[i]=x2;
                carro.get(x2).setDisponivel(false);
            }
            for(i=0;i<3;i++) carro.get(array[i]).setDisponivel(true);
            return array;
        }

        public String[] lista3MaisBarato (Aluguer alug, String tipo) {
            int i;
            String x="";
            String array[] = new String[3];
            for(i=0;i<3;i++) {
                if (tipo.equals("E")) x = procuraMaisBaratoE(alug);
                if (tipo.equals("G")) x = procuraMaisBaratoG(alug);
                if (tipo.equals("H")) x = procuraMaisBaratoH(alug);
                if (tipo.equals("T")) x = procuraMaisBaratoTotal(alug);
                final String x2 = x;
                array[i]=x2;
                carro.get(x2).setDisponivel(false);
            }
            for(i=0;i<3;i++) carro.get(array[i]).setDisponivel(true);
            return array;
        }

        public String[] lista3AutonomiaDesejada (double autonomiaDesejada) {
            int i;
            String x="";
            String array[] = new String[3];
            for(i=0;i<3;i++) {
                x = procuraAutonomiaDesejada(autonomiaDesejada);
                array[i]=x;
                carro.get(x).setDisponivel(false);
            }
            for(i=0;i<3;i++) carro.get(array[i]).setDisponivel(true);
            return array;
        }


        public String procuraAutonomiaDesejada(double autonomidaDesejada) {
            String f = null;
            for (Carro c : carro.values()) {
                if (c.getDisponivel() && autonomidaDesejada < c.getAutonomiaAtual()) {
                   f = c.getMatricula();
                }
            }
            return f;
        }

        public String[] lista3MaisBaratoDistancia (Aluguer alug, double distancia) {
            int i;
            String x="";
            String array[] = new String[3];
            for(i=0;i<3;i++) {
                x = procuraMaisBaratoDistancia(alug,distancia);
                array[i]=x;
                carro.get(x).setDisponivel(false);
            }
            for(i=0;i<3;i++) carro.get(array[i]).setDisponivel(true);
            return array;
        }

        public String procuraMaisBaratoDistancia(Aluguer alug, double distancia) {
            double x = 10;
            String f = "";
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                double u = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordPartida());
                if (c.getPrecoKm() < x && c.getDisponivel() && k<c.getAutonomiaAtual() && u<distancia) {
                    x = c.getPrecoKm();
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public String procuraMaisPertoTotal(Aluguer alug) {
            double x = 1000;
            String f = null;
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                if (calculaDistanciaViagem(alug.getCoordPartida(), c.getCoordenadas()) < x && c.getDisponivel() && k<c.getAutonomiaAtual()) {
                    x = calculaDistanciaViagem(alug.getCoordPartida(), c.getCoordenadas());
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public String procuraMaisPertoH(Aluguer alug) {
            double x = 1000;
            String f = null;
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                if (calculaDistanciaViagem(alug.getCoordPartida(), c.getCoordenadas()) < x && c.getDisponivel() && c instanceof Hibrido && k<c.getAutonomiaAtual()) {
                    x = calculaDistanciaViagem(alug.getCoordPartida(), c.getCoordenadas());
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public String procuraMaisPertoG(Aluguer alug) {
            double x = 1000;
            String f = null;
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                if (calculaDistanciaViagem(alug.getCoordPartida(), c.getCoordenadas()) < x && c.getDisponivel() && c instanceof Gasolina && k<c.getAutonomiaAtual()) {
                    x = calculaDistanciaViagem(alug.getCoordPartida(), c.getCoordenadas());
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public String procuraMaisBaratoTotal(Aluguer alug) {
            double x = 10;
            String f = "";
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                if (c.getPrecoKm() < x && c.getDisponivel() && k<c.getAutonomiaAtual()) {
                    x = c.getPrecoKm();
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public String procuraMaisBaratoE(Aluguer alug) {
            double x = 10;
            String f = "";
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                if (c.getPrecoKm() < x && c.getDisponivel() && c instanceof Eletrico && k<c.getAutonomiaAtual()) {
                    x = c.getPrecoKm();
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public String procuraMaisBaratoG(Aluguer alug) {
            double x = 10;
            String f = "";
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                if (c.getPrecoKm() < x && c.getDisponivel() && c instanceof Gasolina && k<c.getAutonomiaAtual()) {
                    x = c.getPrecoKm();
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public String procuraMaisBaratoH(Aluguer alug) {
            double x = 10;
            String f = "";
            for (Carro c : carro.values()) {
                double k = calculaDistanciaViagem(c.getCoordenadas(),alug.getCoordChegada());
                if (c.getPrecoKm() < x && c.getDisponivel() && c instanceof Hibrido && k<c.getAutonomiaAtual()) {
                    x = c.getPrecoKm();
                    f = c.getMatricula();
                }
            }
            return f;
        }

        public HashMap<String,Carro> getListaViaturas(int nif){
            HashMap<String,Carro> t = new HashMap<>();
            if(map.containsKey(nif) && (map.get(nif)) instanceof Proprietario) {
                Proprietario p = (Proprietario) (map.get(nif));
                t =p.getConjunto();
            }
            return t;
        }

        public ArrayList<Aluguer> getHistoricoGeral(int nif){
            ArrayList<Aluguer> a = new ArrayList<>();
            if(map.containsKey(nif) ){
                if(map.get(nif) instanceof Proprietario){
                    Proprietario p = (Proprietario) (map.get(nif));
                    a=p.getHistorico();
                }
                else {
                    Cliente c = (Cliente) (map.get(nif));
                    a=c.getHistorico();
                }
            }
            return a;
        }

        public ArrayList<Aluguer> getTotalFaturado(String mat){
            ArrayList<Aluguer> a = new ArrayList<>();
            if(carro.containsKey(mat) ) {
                Carro c = carro.get(mat);
                a = c.getHistorico();
            }
            return a;
        }


        public void novaClass(String linha) {
            String[] campos;
            campos = linha.split(",");
            String matricula, geral;
            int nif = 0;
            Double classificacao;
            matricula = null;
            try {
                geral = campos[0];
                if (geral.length() != 8) {nif = Integer.parseInt(geral);}
                else {matricula = geral;}
                classificacao = Double.parseDouble(campos[1]);
            } catch (InputMismatchException | ArrayIndexOutOfBoundsException | NumberFormatException exc) {return ;}
            final String mat = matricula;
            final Integer nif2 = nif;
            if (mat != null) {
                map.forEach((nome, ator) -> {
                    if (ator instanceof Proprietario) {
                        if (((Proprietario) ator).getConjunto().containsKey(mat)) {
                            ((Proprietario) ator).getConj(mat).fazmedia(classificacao);
                        }
                    }
                });
                carro.get(mat).fazmedia(classificacao);
            } else {map.get(nif2).fazmedia(classificacao);}
        }


        public static List<String> readWithFiles(String fichtxt) {
            List<String> linhas = new ArrayList<>();
            try {linhas = Files.readAllLines(Paths.get(fichtxt), StandardCharsets.UTF_8);}
            catch (IOException exc) {out.println(exc);}
            return linhas;
        }


        public HashMap<Integer, Ator> arrayVendas(List<String> linhas) throws NifJaExisteException {
            HashMap<Integer, Ator> map = new HashMap<>();
            for (String v : linhas) {map = lePrimArg(v, map);}
            return map;
        }


        public boolean iniciaSessao(int nif, String password) throws SemAutorizacaoException{
            if (nif == 0 || password == null || map == null) return false;
            if (map.containsKey(nif)) {
                if (map.get(nif).getPassword().equals(password)) {return true;}
            }
            else throw new SemAutorizacaoException("Sessão não iniciada!");
            return false;
        }

        public Aluguer preencheAluguer(Aluguer alug, String matricula) {
            Carro car = carro.get(matricula).clone();
            alug.setDataAlug(LocalDate.now());
            alug.setCarro(car);
            alug.setPrecoEstimado(calculaCustoEstimadoViagem(car.getCoordenadas(), alug.getCoordChegada(),car));
            alug.setTempoReal(calculaTempoViagem(car.getCoordenadas(), alug.getCoordChegada(),car));
            alug.setDistanciaAluguer(calculaDistanciaViagem(car.getCoordenadas(),alug.getCoordChegada()));
            alug.setDistanciaInicial(calculaDistanciaViagem(alug.getCoordPartida(),car.getCoordenadas()));
            return alug;
        }

        public void adicionaAluguerQueue(int nif,Aluguer alug){
            Proprietario p = (Proprietario) map.get(nif);
            p.addPedido(alug);
        }

        public void darUpdateAvaliacao(Aluguer a, int valor) {
            String mat = a.getCarro().getMatricula();
            int nifCliente = a.getNifCliente();
            Cliente c = (Cliente) map.get(nifCliente);
            c.setAvaliacao(mat,valor);
            map.values().forEach(p->{
                if (p instanceof Proprietario && ((Proprietario) p).getConjunto().containsKey(mat)) {
                    Proprietario k = (Proprietario) map.get(p.getNif());
                    k.setAvaliacao(valor, mat);
                }
            });
            carro.get(mat).setClassificacaoHistorico(valor,mat);
        }

        public void top10ClientesKms() {
            int i=1;
            int cont=10;
            Map<Double,Cliente> lista10 = new HashMap<>();
            List<Ator> lista = map.values().stream().filter(a->a instanceof Cliente).collect(Collectors.toList());

            for(Ator a : lista) {
                Cliente c = (Cliente) a;
                lista10.put(c.getKmsTotal(),c.clone());
            }

            List<Double> l = new ArrayList<>(lista10.keySet());
            Collections.sort(l);

            int tamanho = l.size()-10;

            for(Double a : l) {
                if (i>tamanho) {
                    out.println(cont+":");
                    out.println(lista10.get(a));
                    out.println("Total de kms deste cliente: " + a);
                    out.println("\n************************************************************\n");
                    cont--;
                }
                i++;
            }
        }

        public void top10ClientesUtilizacoes() {
            int i=1;
            int cont=10;
            Map<Integer,Cliente> lista10 = new HashMap<>();
            List<Ator> lista = map.values().stream().filter(a->a instanceof Cliente).collect(Collectors.toList());
            for(Ator a : lista) {
                Cliente c = (Cliente) a;
                lista10.put(c.getNumAluguers(),c.clone());
            }
            List<Integer> l = new ArrayList<>(lista10.keySet());
            Collections.sort(l);
            int tamanho = l.size()-10;
            if (tamanho<0) cont=cont+tamanho;
            for(Integer a : l) {
                if (i>tamanho) {
                   out.println(cont+":");
                   out.println(lista10.get(a));
                   out.println("Total de utilizações deste cliente: " + a);
                  out.println("\n************************************************************\n");
                  cont--;
                }
             i++;
             }
         }
    }
