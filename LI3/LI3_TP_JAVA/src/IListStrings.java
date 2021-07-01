public interface IListStrings {
    int tamanho();
    int getPagina();
    int getTamPag();
    int getUltPag();
    void add(String s);
    void actualizaIndices();
    Pagina proxPag();
    Pagina antPag();
    Pagina priPag();
    Pagina ultPag();
}
