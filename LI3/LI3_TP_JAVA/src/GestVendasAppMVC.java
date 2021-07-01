import java.io.*;
import static java.lang.System.out;

public class GestVendasAppMVC implements Serializable {
    
    public static void main(String[] args){

       IGestVendasModel model = new GestVendasModel();
       model.createData();
       if (model==null) { out.println("Erro"); System.exit(-1);}
       
       IGestVendasView view = new GestVendasView();

       IGestVendasController control = new GestVendasController();
       control.setModel(model);control.setView(view);
       control.start();
       System.exit(0);

    } 
}
