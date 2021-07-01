package Main.data;

import Main.business.Categoria;
import Main.business.Conteudo;
import Main.business.Utilizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ConteudoDAO implements Map<String, Conteudo> {
    private Connection conexao;
    private String nomeConteudo;

    public ConteudoDAO() {}

    public void clear(){
        try{
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM Conteudo;");
            ps.executeUpdate();
        }
        catch(SQLException | ClassNotFoundException e){
            throw new NullPointerException(e.getMessage());
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean containsKey(Object key){
        boolean b = false;
        try{
            conexao = Connect.connect();

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Conteudo WHERE Nome = '"+(String)key+"';");
            ResultSet rs = ps.executeQuery();
            b = rs.next();
        }
        catch(SQLException | ClassNotFoundException e){
            throw new NullPointerException(e.getMessage());
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    public boolean containsValue(Object value){
        Conteudo c = (Conteudo) value;
        return containsKey(c.getNomeCont());
    }


    public Set<Map.Entry<String,Conteudo>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Conteudo>> entrySet() not implemented!");
    }


    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }

    public Conteudo get(Object key){
        Conteudo co= null;
        try{
            conexao = Connect.connect();

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Conteudo WHERE Nome = '"+(String)key+"';");

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                co = new Conteudo(rs.getString(2),rs.getString(1),rs.getString(3),rs.getInt(4));
            }
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return co;
    }

    public Conteudo getConsoanteUser(Object key,String email){
        Conteudo co= null;
        try{
            conexao = Connect.connect();

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM User_Conteudo WHERE EmailUtilizador = '"+email+"' and Nome_Conteudo='"+key+"';");

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                co = new Conteudo(rs.getString(2),rs.getString(1),rs.getString(3),rs.getInt(4));
            }
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return co;
    }


    public ArrayList<String> printM(int tipo,String categoria) {
        ArrayList<String> mp = new ArrayList<>();
        int i = 0;
        try {
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("SELECT Nome_Conteudo FROM User_Conteudo WHERE Nome_Categoria ='" + categoria + "';");
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Conteudo cafe = new Conteudo(rs.getString(1));
                mp.add(cafe.getNomeCont());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mp;
    }


    public int hashCode() {
        return this.conexao.hashCode();
    }


    public boolean isEmpty() {
        return size() == 0;
    }


    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }


    public Conteudo put(String key, Conteudo value){
        Conteudo co= null;
        try{
            conexao = Connect.connect();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO Conteudo (Filename,Nome,Nome_Categoria,Tipo)\n");
            sb.append("VALUES('");
            sb.append(value.getNomeFicheiro()).append("',");
            sb.append("'").append(value.getNomeCont()).append("',");
            sb.append("'").append(value.getFlagCat()).append("',");
            sb.append("'").append(value.getTipo()).append("');");
            PreparedStatement ps = conexao.prepareStatement(sb.toString());
            int upd = ps.executeUpdate();
            if(upd > 0){
                co = value;
            }
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return co;
    }



    public void putM( String emailU,String nCont,String nCatg){
        try{
            conexao = Connect.connect();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO User_Conteudo (EmailUtilizador,Nome_Conteudo,Nome_Categoria)\n");
            sb.append("VALUES('").append(emailU).append("','").append(nCont).append("','").append(nCatg).append("');");
            PreparedStatement ps = conexao.prepareStatement(sb.toString());
            int upd = ps.executeUpdate();
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCat(String emailU,String nCont,String novaCategoria){
        try{
            conexao = Connect.connect();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE User_Conteudo\n SET Nome_Categoria= '"+novaCategoria +"'\n");
            sb.append("WHERE (User_Conteudo.Nome_Conteudo='"+nCont+"' and User_Conteudo.EmailUtilizador ='"+emailU +"');");
            PreparedStatement ps = conexao.prepareStatement(sb.toString());
            int upd = ps.executeUpdate();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void putAll(Map<? extends String, ? extends Conteudo> map){
        for(Conteudo co : map.values()){
            put(co.getNomeCont(),co);
        }
    }


    public Conteudo remove(Object key){
        Conteudo co = get(key);
        try{
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM Conteudo WHERE Nome = '"+(String)key+"';");
            ps.executeUpdate();
        }
        catch(SQLException | ClassNotFoundException e){
            throw new NullPointerException(e.getMessage());
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return co;
    }

    public int sizeEspecial(String email, String cat, Integer flag){
        int size = 0;
        try{
            conexao = Connect.connect();
            PreparedStatement ps;
            if (flag==1) {
                ps = conexao.prepareStatement("SELECT * FROM Conteudo\n LEFT JOIN User_Conteudo\nON(User_Conteudo.Nome_Conteudo=Conteudo.Nome and User_Conteudo.EmailUtilizador='" + email + "');");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getString(8)!=null && rs.getString(8).equals(cat)) size++;
                    else if (rs.getString(8)==null && rs.getString(3).equals(cat)) size++;
                }
            }
            else if (flag==2) {
                ps = conexao.prepareStatement("SELECT COUNT(*) FROM Conteudo WHERE Nome_Categoria ='" + cat + "';");
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {size = rs.getInt(1);}
            }
        }
        catch(SQLException | ClassNotFoundException e){
            throw new NullPointerException(e.getMessage());
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return size;
    }


    public int size(){
        int size = 0;
        try{
            conexao = Connect.connect();
            PreparedStatement ps = null;

            ps = conexao.prepareStatement("SELECT COUNT(*) FROM Conteudo;");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                size = rs.getInt(1);
            }
        }
        catch(SQLException | ClassNotFoundException e){
            throw new NullPointerException(e.getMessage());
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return size;
    }



    public Collection<Conteudo> values(){
        Collection<Conteudo> c = new HashSet<>();
        try{
            conexao = Connect.connect();
            PreparedStatement ps = null;
            if(nomeConteudo== null)
                ps = conexao.prepareStatement("SELECT * FROM Conteudo;");
            else
                ps = conexao.prepareStatement("SELECT * FROM Conteudo WHERE Nome = '"+nomeConteudo+"';");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                c.add(new Conteudo(rs.getString(2),rs.getString(1),rs.getString(3),rs.getInt(4)));
            }
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    public Collection<Conteudo> valuesCateg(String nomeCategoria){
        Collection<Conteudo> c = new HashSet<>();
        try{
            conexao = Connect.connect();
            PreparedStatement ps = null;
            if(nomeConteudo== null)
                ps = conexao.prepareStatement("SELECT * FROM Conteudo;");
            else
                ps = conexao.prepareStatement("SELECT * FROM Conteudo WHERE Nome_Categoria = '"+nomeCategoria+"';");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                c.add(new Conteudo(rs.getString(2),rs.getString(1),rs.getString(3),rs.getInt(4)));
            }
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                conexao.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return c;
    }


    public void setNomeCat(String str){
        nomeConteudo = str;
    }

    //O método seguinte é o que vai buscar os conteúdos default para mostrar ao CONVIDADO!
    public Object[] toArray(String cat) {
        Conteudo[] mp= new Conteudo[sizeEspecial("",cat,2)];
        int i = 0;
        try {
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Conteudo WHERE Nome_Categoria='"+cat+"';");
            ResultSet rs = ps.executeQuery();
            while (rs.next() ) {
                mp[i] = new Conteudo(rs.getString(2),rs.getString(1),rs.getString(3),rs.getInt(4));
                i++;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mp;
    }


    //O método seguinte é o que vai buscar os conteúdos (alterados se for o caso) para mostrar ao USER!!
    public Object[] toArrayFiltrada(String email, String categoria) {
        Conteudo[] mp= new Conteudo[sizeEspecial(email,categoria,1)]; //1
        int i = 0;
        try {
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Conteudo\n LEFT JOIN User_Conteudo\nON(User_Conteudo.Nome_Conteudo=Conteudo.Nome and User_Conteudo.EmailUtilizador='" + email + "');");
            ResultSet rs = ps.executeQuery();
            while (rs.next() ) {
                if(rs.getString(8)!=null && (rs.getString(8).equals(categoria))){
                    mp[i] = new Conteudo(rs.getString(2),rs.getString(1),rs.getString(8),rs.getInt(4));
                    i++;
                }
                else if (rs.getString(8)==null && (rs.getString(3).equals(categoria))) {
                    mp[i] = new Conteudo(rs.getString(2),rs.getString(1),rs.getString(3),rs.getInt(4));
                    i++;

                }
            }} catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mp;
    }


}

