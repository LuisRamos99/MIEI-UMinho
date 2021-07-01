
public interface IGestVendasController {
    void setView(IGestVendasView view);
    void setModel(IGestVendasModel model);
    void lerFicheiros();
    void start();
    void consultasEstat();
    void consultasInter();
}
