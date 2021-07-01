package Main.data;

import Main.business.Categoria;
import Main.business.Conteudo;
import Main.business.Utilizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CategoriaDAO implements Map<String, Categoria> {
    private Connection conexao;
    private String nomeCat;

    public CategoriaDAO() {
    }

    public void clear() {
        try {
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM Categoria;");
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean containsKey(Object key) {
        boolean b = false;
        try {
            conexao = Connect.connect();

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Categoria WHERE Nome = '" + (String) key + "';");
            ResultSet rs = ps.executeQuery();
            b = rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    public boolean containsValue(Object value) {
        Categoria a = (Categoria) value;
        return containsKey(a.getNome());
    }


    public Set<Entry<String, Categoria>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Categoria>> entrySet() not implemented!");
    }


    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }

    public Categoria get(Object key) {
        Categoria cat = null;
        try {
            conexao = Connect.connect();

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Categoria WHERE Nome = '" + (String) key + "';");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cat = new Categoria(rs.getString(1), rs.getInt(2));
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
        return cat;
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


    public Categoria put(String key, Categoria value) {
        Categoria cat = null;
        try {
                conexao = Connect.connect();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO Categoria (Nome,Tipo)\n");
            sb.append("VALUES('").append(value.getNome()).append("',");
            sb.append("'").append(value.getTipo()).append("');");
             PreparedStatement ps = conexao.prepareStatement(sb.toString());
            int upd = ps.executeUpdate();
            if (upd > 0) {
                cat = value;
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
        return cat;
    }


    public void putAll(Map<? extends String, ? extends Categoria> map) {
        for (Categoria cat : map.values()) {
            put(cat.getNome(), cat);
        }
    }


    public Categoria remove(Object key) {
        Categoria cat = get(key);
        try {
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM Categoria WHERE Nome = '" + (String) key + "';");
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cat;
    }

    public int size() {
        int size = 0;
        try {
            conexao = Connect.connect();
            PreparedStatement ps = null;
            if (nomeCat == null)
                ps = conexao.prepareStatement("SELECT COUNT(*) FROM Categoria;");
            else
                ps = conexao.prepareStatement("SELECT COUNT(*) FROM Categoria WHERE Email = " + nomeCat + ";");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                size = rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return size;
    }


    public Collection<Categoria> values() {
        Collection<Categoria> c = new HashSet<>();
        try {
            conexao = Connect.connect();
            PreparedStatement ps = null;
            if (nomeCat == null)
                ps = conexao.prepareStatement("SELECT * FROM Categoria;");
            else
                ps = conexao.prepareStatement("SELECT * FROM Categoria WHERE Nome = '" + nomeCat + "';");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                c.add(new Categoria(rs.getString(1), rs.getInt(2)));
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
        return c;
    }

    public void setNomeCat(String str) {
        nomeCat = str;
    }

    public ArrayList<String> printMenu(int tipo) {
        ArrayList<String> mp = new ArrayList<>();
        int i = 0;
        try {
            if (nomeCat == null) {
                conexao = Connect.connect();
                PreparedStatement ps = conexao.prepareStatement("SELECT Nome FROM Categoria WHERE Tipo ='" + tipo + "';");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Categoria cafe = new Categoria(rs.getString(1));
                    mp.add(cafe.getNome());
                }
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


}




