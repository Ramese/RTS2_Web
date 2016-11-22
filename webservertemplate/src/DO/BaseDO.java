package DO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Radek
 */
public class BaseDO {
    protected static Connection GetConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:postgresql://192.168.56.101:5432/rts2_web", "postgres", "postgres");
            
            return con;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
