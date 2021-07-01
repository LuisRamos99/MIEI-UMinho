package Main.data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    
// ESTABELECE A LIGAÇÃO À BD
    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/MC?serverTimezone=UTC","root","soubot123");
        return conn;   
    }
    

// TERMINA LIGAÇÃO 
    public static void close(Connection c) {
        try {
            if(c!=null && !c.isClosed()) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
