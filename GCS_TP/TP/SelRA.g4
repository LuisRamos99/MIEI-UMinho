grammar SelRA;


@header {
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
}



basedados returns[int ident, double pontuacao, String conc, int iterator, double fatorIdade, double fatorCaract]
@after{
       TreeMap<String,Integer> topOrdenadoC = new TreeMap<String,Integer>();
       TreeMap<String,Integer> topOrdenadoS = new TreeMap<String,Integer>();
       List<Integer> topOrdenado = new ArrayList<Integer>();
       top.forEach((id,pont)-> {if (ConceiMap.get(id).contains($conc)) topOrdenadoC.put(pont+","+id,id); else topOrdenadoS.put(pont+","+id,id); });
       topOrdenadoC.descendingKeySet().forEach(string -> {topOrdenado.add(Integer.parseInt(string.split(",")[1])); });
       topOrdenadoS.descendingKeySet().forEach(string -> {topOrdenado.add(Integer.parseInt(string.split(",")[1])); }); 
       $iterator=1;
       double min = 50 - 10 * $fatorIdade;
       double max = (50 + 5 * $fatorCaract) - min;
       if (idadeAluno!=0) System.out.println("\nOs "+ntop+" melhores recursos para o aluno " + numAluno + " aprender o conceito " + $conc + " são:\n");
       topOrdenado.forEach(id -> {
         double pont =(top.get(id) - min) * 100 / max ;
         System.out.println($iterator+":");
         System.out.println("Recurso com id "       + id);
         System.out.printf("Pontuação: %.2f", pont);
         System.out.println(" %");
         System.out.println("Tipo: "                + TipoMap.get(id));
         System.out.println("Titulo: "              + TituloMap.get(id));
         System.out.println("Público Alvo: dos "    + IdadeMap.get(id).split("/")[0] + " aos " + IdadeMap.get(id).split("/")[1] + " anos");
         System.out.println("Caracteristicas: "     + CaractMap.get(id));
         System.out.println("Conceitos: "           + ConceiMap.get(id));
         System.out.println("------------------------------------------------------------------------------");
         $iterator++;                        
       });
      }
@init{ 
      $fatorIdade     = 0.5;
      $fatorCaract    = 1.5;
      String numAluno = null;
      int idadeAluno  = 0;
      int ntop        = 3;
      int diferenca   = 0;
      List<String>                  caracteristicasAluno = new ArrayList<String>();
      HashMap<Integer,String>       IdadeMap             = new HashMap<Integer,String>(); 
      HashMap<Integer,List<String>> TituloMap            = new HashMap<Integer,List<String>>(); 
      HashMap<Integer,List<String>> ConceiMap            = new HashMap<Integer,List<String>>(); 
      HashMap<Integer,List<String>> CaractMap            = new HashMap<Integer,List<String>>(); 
      HashMap<Integer,String>       TipoMap              = new HashMap<Integer,String>(); 
      HashMap<Integer,Double>       top                  = new HashMap<Integer,Double>();
      }
          :  
          (fatores
           {
            $fatorCaract = $fatores.fatC;
            $fatorIdade  = $fatores.fatI;
           }
          )?
          query
           {
            $conc = $query.conceit;
            if ($query.nTop!=0) ntop  = $query.nTop;
           }
          (aluno
           {
            if($aluno.nAluno.equals($query.nAluno)) {
               numAluno   = $aluno.nAluno;
               idadeAluno = $aluno.idad;
               $aluno.caracteristics.forEach(elem -> { caracteristicasAluno.add(elem); });
               }
            }
           )+
           {
            if (idadeAluno==0) System.out.println("\nO aluno " + $query.nAluno + " não existe na base de dados!");
            else System.out.println("\nAluno " + numAluno + " com " + idadeAluno + " anos e com as seguintes caracteristicas: " + caracteristicasAluno);
           }
           (recursoAprendizagem
            {
            if(idadeAluno!=0){
              $pontuacao=50;
              $ident = 0;
              $recursoAprendizagem.caracteristics.forEach(c -> {
                caracteristicasAluno.forEach(elem -> { if (elem.equals(c)) $pontuacao+=$fatorCaract; });                                              
              });
              if ($recursoAprendizagem.idadMin>idadeAluno || idadeAluno>$recursoAprendizagem.idadMax) {                                                                 
                 if (idadeAluno<$recursoAprendizagem.idadMax) diferenca = $recursoAprendizagem.idadMin-idadeAluno;
                 else diferenca = idadeAluno-$recursoAprendizagem.idadMax; 
                 if (idadeAluno<=13)                   {$pontuacao -= diferenca * $fatorIdade * 1.2;}   
                 if (14<=idadeAluno && idadeAluno<=20) {$pontuacao -= diferenca * $fatorIdade      ;}
                 if (21<=idadeAluno && idadeAluno<=50) {$pontuacao -= diferenca * $fatorIdade * 0.2;}
                 if (51<=idadeAluno)                   {$pontuacao -= diferenca * $fatorIdade * 0.4;}
              }
              if(top.size()>=ntop){                 
                TreeMap<String,Integer> topwithoutc = new TreeMap<String,Integer>();
                TreeMap<String,Integer> topwithc    = new TreeMap<String,Integer>();
                top.forEach((k,v)-> { if (ConceiMap.get(k).contains($query.conceit)) topwithc.put(v+","+k,k); else topwithoutc.put(v+","+k,k); });
                if ($recursoAprendizagem.conceits.contains($query.conceit)) {
                   if (topwithoutc.size()>0) $ident = Integer.parseInt(topwithoutc.firstKey().split(",")[1]);
                }   
                else {
                   if (topwithoutc.size()>0) {
                      double pont = Double.parseDouble(topwithoutc.firstKey().split(",")[0]);
                      if (pont<$pontuacao) $ident = Integer.parseInt(topwithoutc.firstKey().split(",")[1]);
                   }
                }
                if ($ident!=0) {
                  IdadeMap.remove( $ident);
                  TituloMap.remove($ident);
                  ConceiMap.remove($ident);
                  CaractMap.remove($ident);
                  TipoMap.remove(  $ident);
                  top.remove(      $ident);
                }
              }
              if (top.size()<ntop) {
                IdadeMap.put($recursoAprendizagem.ident  , $recursoAprendizagem.idadMin+"/"+$recursoAprendizagem.idadMax);
                TituloMap.put($recursoAprendizagem.ident , $recursoAprendizagem.title);
                ConceiMap.put($recursoAprendizagem.ident , $recursoAprendizagem.conceits);
                CaractMap.put($recursoAprendizagem.ident , $recursoAprendizagem.caracteristics);   
                TipoMap.put($recursoAprendizagem.ident   , $recursoAprendizagem.tipoR);
                top.put($recursoAprendizagem.ident       , $pontuacao);
              }
             }
            }
           )+
         ;


fatores returns[double fatC, double fatI]
      : FATORES fatorC fatorI
        {
         $fatC = $fatorC.Fc;
         $fatI = $fatorI.Fi;
        }
    ;


query returns[String nAluno, String conceit, int nTop]
      : QUERY numAluno conceito 
        {
         $nAluno  = $numAluno.nAluno;
         $conceit = $conceito.conc;
        }
        (numTop
        {
         $nTop    = $numTop.nTop;
        }
        )?
      ;


aluno returns[String nAluno, int idad, List<String> caracteristics]
      : AL numAluno VIRGULA idade VIRGULA caracteristicas
        {
         $nAluno         = $numAluno.nAluno;
         $idad           = $idade.idad;
         $caracteristics = $caracteristicas.caracteristics;
        }
      ;


recursoAprendizagem returns[int ident, String tipoR, List<String> title, int idadMin, int idadMax, List<String> conceits, List<String> caracteristics]
                    : RA id  VIRGULA tipo VIRGULA titulo VIRGULA idadeIntervalo VIRGULA conceitos VIRGULA caracteristicas
                      {
                       $ident          = $id.ident;
                       $tipoR          = $tipo.tip;
                       $title          = $titulo.title;
                       $idadMin        = $idadeIntervalo.idadMin;
                       $idadMax        = $idadeIntervalo.idadMax;
                       $conceits       = $conceitos.conceits;
                       $caracteristics = $caracteristicas.caracteristics;
                      }
                    ;



caracteristicas returns[List<String> caracteristics]
@init {
      $caracteristics = new ArrayList<String>();
      }
                : LPARENTRETO 
                  caracteristica
                  {
                  $caracteristics.add($caracteristica.caracterist);
                  }
                  (VIRGULA caracteristica
                  {
                  $caracteristics.add($caracteristica.caracterist);
                  }
                  )*
                  RPARENTRETO
                ;

caracteristica returns[String caracterist]
               : TEXTO
                 {
                 $caracterist=$TEXTO.text;
                 }
                 (TEXTO
                 {
                 $caracterist+=" "+$TEXTO.text;
                 })*
               ;


conceitos returns[List<String> conceits]
@init {
      $conceits = new ArrayList<String>();
      }
          : LPARENTRETO 
            conceito
            {
            $conceits.add($conceito.conc);
            }
            (VIRGULA conceito
            {
            $conceits.add($conceito.conc);
            }
            )*
            RPARENTRETO
          ;


conceito returns[String conc]
         : TEXTO
           {
           $conc=$TEXTO.text;
           }
           (TEXTO
           {
           $conc+=" "+$TEXTO.text;
           })*
         ;

titulo returns[List<String> title]
@init {
      $title = new ArrayList<String>();
      }
           : ASPA 
             titulo_aux
             {
             $title.add($titulo_aux.title_aux);
             }
             (VIRGULA titulo_aux
             {
             $title.add($titulo_aux.title_aux);
             }
             )*
             ASPA
           ;

titulo_aux returns[String title_aux]
          : TEXTO
            {
            $title_aux=$TEXTO.text;
            }
            (TEXTO 
            {
            $title_aux+=" "+$TEXTO.text;
            }
            | DOUBLE
            {
            $title_aux+=" "+$DOUBLE.text;
            }
            )*
          ;



idadeIntervalo returns[int idadMin, int idadMax]
      : LPARENT NUM
        {
        $idadMin=$NUM.int;
        }
        BARRA NUM RPARENT
        {
        $idadMax=$NUM.int;
        }
      ;



idade returns[int idad]
      : NUM
        {
        $idad=$NUM.int;
        }
      ;



id returns[int ident]
   : NUM
     {
     $ident=$NUM.int;
     }
   ;


numTop returns[int nTop]
   : NUM
     {
     $nTop=$NUM.int;
     }
   ;


tipo returns[String tip]
        : TEXTO 
          {
          $tip=$TEXTO.text;
          }
          (TEXTO
          {
          $tip+=" "+$TEXTO.text;
          })*
        ;


numAluno returns[String nAluno]
        : NUMALUNO 
          {
          $nAluno=$NUMALUNO.text;
          }
        ;


fatorC returns[double Fc]
        : DOUBLE 
          {
          $Fc=Double.parseDouble($DOUBLE.text);
          }
        ;

fatorI returns[double Fi]
        : DOUBLE 
          {
          $Fi=Double.parseDouble($DOUBLE.text);
          }
        ;








//LEXER

RA: [rR][aA]
  ;

FATORES: [fF][aA][tT][oO][rR][eE][sS]
  ;

QUERY: [qQ][uU][eE][rR][yY]
  ;

AL: [aA][lL]
  ;

NUM: [1-9][0-9]*
     ;

DOUBLE: [0-9]+([\.][0-9]+)?
     ;

NUMALUNO:([aA]|[pP][gG])[1-9][0-9][0-9][0-9][0-9]
        ;

TEXTO: [a-zA-Z0-9\-\&\#\.\:]+
     ;

VIRGULA:      ',';
PONTOVIRGULA: ';';
LPARENT:      '(';
RPARENT:      ')';
LPARENTRETO:  '[';
RPARENTRETO:  ']';
ASPA:         '"';
BARRA:        '/';

WS: ('\r'? '\n' | ' ' | '\t')+ ->skip;
