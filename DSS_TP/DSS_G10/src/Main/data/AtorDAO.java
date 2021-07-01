package Main.data;

import Main.business.Admin;
import Main.business.Ator;
import Main.business.Utilizador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AtorDAO implements Map<String, Ator> {

    private Connection conexao;
    private String idUser;

    public AtorDAO() { };

    public boolean containsKey(Object key) {
        boolean result = false;
        try { if(key instanceof Utilizador){
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Utilizador WHERE Email = '" + (String) key + "';");
            ResultSet rs = ps.executeQuery();
            result = rs.next();}
            else{
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Admins WHERE Email = '" + (String) key + "';");
            ResultSet rs = ps.executeQuery();
            result = rs.next();}

        } catch (SQLException | ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean containsValue(Object value) {
        Ator at = (Ator) value;
        return containsKey(at.getEmail());
    }

    public Ator get(Object key) {
        Ator at = null;
        try {
            conexao = Connect.connect();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Admins WHERE Email = '"+(String)key+"';");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                at = new Admin(rs.getString(1),rs.getString(2),rs.getString(3));}
            else {
                ps = conexao.prepareStatement("SELECT * FROM Utilizador WHERE Email = '" + (String) key + "';");
                ResultSet aux = ps.executeQuery();
                if (aux.next()) {
                    at = new Utilizador(aux.getString(1), aux.getString(2), aux.getString(3));

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
        return at;
    }

    public int size() {
        int size = 0;
        try {
            conexao = Connect.connect();
            PreparedStatement ps = null;

            if (!idUser.equals("Antonio@")) {
                ps = conexao.prepareStatement("SELECT COUNT(*) FROM Utilizador;");
            } else {
                ps = conexao.prepareStatement("SELECT COUNT(*) FROM Admins; ");
            }
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

    public boolean isEmpty() {
        return size() == 0;
    }

    public Ator put(String key, Ator at) {
        Ator ator = null;
        try {
            String tipo = null;
            StringBuilder sb = new StringBuilder();
            if (containsKey(key) && (at instanceof Utilizador)) {
                conexao = Connect.connect();
                sb.append("UPDATE Utilizador\nSET Pwd = ").append(((Utilizador) at).getPassword());
                sb.append(", Email  = ").append(((Utilizador) at).getEmail());
                sb.append(", Nome = ").append(((Utilizador) at).getNome());
                sb.append("\nWHERE Email = ").append(at.getEmail()).append(";");
                PreparedStatement ps = conexao.prepareStatement(sb.toString());
                int update = ps.executeUpdate();
                if (update > 0) {
                    ator = at;
                }
            } else if (containsKey(key) && (at instanceof Admin)) {
                conexao = Connect.connect();
                sb.append("UPDATE Admins\nSET Pwd = ").append(((Admin) at).getPassword());
                sb.append(", Email  = ").append(((Admin) at).getEmail());
                sb.append(", Nome = ").append(((Admin) at).getNome());
                sb.append("\nWHERE Email = ").append(at.getEmail()).append(";");
                PreparedStatement ps = conexao.prepareStatement(sb.toString());
                int update = ps.executeUpdate();
                if (update > 0) {
                    ator = at;
                }
            }

            else {
                conexao = Connect.connect();
                sb.append("INSERT INTO Utilizador (Pwd,Email,Nome)\n");
                sb.append("VALUES('").append(at.getPassword()).append("',");
                sb.append("'").append(at.getEmail()).append("',");
                sb.append("'").append(at.getNome()).append("');");

                PreparedStatement ps = conexao.prepareStatement(sb.toString());
                int update = ps.executeUpdate();
                if (update > 0) {
                    ator = at;
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
        return ator;
    }

    public Ator remove(Object key) {
        Ator at = get(key);
        try {
            conexao = Connect.connect();
            PreparedStatement ps = null;

            if (at instanceof Utilizador) {
                ps = conexao.prepareStatement("DELETE FROM Utilizador WHERE Email = '" + (String) key + "';");
                ps.executeUpdate();
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
        return at;
    }


    public void putAll(Map<? extends String, ? extends Ator> map) {
        for (Ator at : map.values()) {
            put(at.getEmail(), at);
        }
    }

    public void clear() {
        throw new NullPointerException();
    }

    public Set<String> keySet() {
        throw new NullPointerException();
    }

    public Collection<Ator> values() {
        Collection<Ator> c = new HashSet<>();
        try {
            conexao = Connect.connect();
            PreparedStatement ps = null;
            if (idUser == null) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                        ps = conexao.prepareStatement("SELECT * FROM Utilizador WHERE Email = '" + rs.getString(1) + "';");
                        ResultSet aux = ps.executeQuery();
                        if (aux.next())
                            c.add(new Utilizador(aux.getString(1), aux.getString(2), aux.getString(3)));
                    }

            } else {
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


    public Set<Map.Entry<String, Ator>> entrySet() {
        throw new NullPointerException();
    }

    public int hashCode() {
        return this.conexao.hashCode();
    }

    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }


    public void setId(String st) {
        idUser = st;

    }
}



