package DataObjects;

import ValueObjects.UserVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Radek
 */
public class UserDO extends BaseDO {
    
    private static void Execute(String SQL) throws SQLException {

        Connection con = GetConnection();

        if(con == null) {
            System.err.println("Connection failed!");
            return;
        }

        Statement st = con.createStatement();

        st.execute(SQL);

        st.close();
        con.close();
    }
    
    private static Connection GetConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:postgresql://192.168.56.101:5432/rts2_web", "postgres", "postgres");
            
            return con;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Insert statement includuing all propertis.
     * @param userVO 
     */
    public static void InsertUser(UserVO userVO) throws SQLException {
        String insertSQL = "INSERT INTO \"User\" (";
        
        insertSQL += "\"UserName\", ";
        insertSQL += "\"FirstName\", ";
        insertSQL += "\"LastName\", ";
        insertSQL += "\"Role\", ";
        insertSQL += "\"Email\", ";
        insertSQL += "\"UpdateDate\", ";
        insertSQL += "\"CreateDate\", ";
        insertSQL += "\"Token\", ";
        insertSQL += "\"TokenExpiration\", ";
        insertSQL += "\"Password\"";
        
        insertSQL +=") VALUES (";
        
        insertSQL += "'" + userVO.UserName + "', ";
        insertSQL += "'" + userVO.FirstName + "', ";
        insertSQL += "'" + userVO.LastName + "', ";
        insertSQL += "'" + userVO.Role + "', ";
        insertSQL += "'" + userVO.Email + "', ";
        insertSQL += "'" + userVO.UpdateDate + "', ";
        insertSQL += "'" + userVO.CreateDate + "', ";
        insertSQL += "'" + userVO.Token + "', ";
        insertSQL += "'" + userVO.TokenExpiration + "', ";
        insertSQL += "'" + userVO.Password + "'";
        
        insertSQL += ");";
        
        Execute(insertSQL);
        
    }
    
    public static UserVO GetUser(long Id) throws SQLException {
        UserVO result = new UserVO();
        
        String SQL = "SELECT * FROM \"User\" WHERE Id = " + Id;
        
        Connection conn = GetConnection();
        
        Statement st = conn.createStatement();
        
        ResultSet rs = st.executeQuery(SQL);
        
        while (rs.next())
        {
           result.Id = rs.getLong("Id");
           result.UserName = rs.getString("UserName");
           result.FirstName = rs.getString("FirstName");
           result.LastName = rs.getString("LastName");
           result.Email = rs.getString("Email");
           result.Role = rs.getString("Role");
           result.Token = rs.getObject("Token", UUID.class);
           result.TokenExpiration = rs.getTimestamp("TokenExpiration");
           result.CreateDate = rs.getTimestamp("CreateDate");
           result.UpdateDate = rs.getTimestamp("UpdateDate");
           result.Password = rs.getString("Password");
        } 
        
        rs.close();
        st.close(); 
        conn.close();
        
        return result;
    }
    
    public static List<UserVO> GetUsers() throws SQLException {
        List<UserVO> users = new ArrayList<>();
        
        String SQL = "SELECT * FROM \"User\"";
        
        Connection conn = GetConnection();
        
        Statement st = conn.createStatement();
        
        ResultSet rs = st.executeQuery(SQL);
        
        UserVO user;
        
        while (rs.next())
        {
            user = new UserVO();
            
            user.Id = rs.getLong("Id");
            user.UserName = rs.getString("UserName");
            user.FirstName = rs.getString("FirstName");
            user.LastName = rs.getString("LastName");
            user.Email = rs.getString("Email");
            user.Role = rs.getString("Role");
            user.Token = rs.getObject("Token", UUID.class);
            user.TokenExpiration = rs.getTimestamp("TokenExpiration");
            user.CreateDate = rs.getTimestamp("CreateDate");
            user.UpdateDate = rs.getTimestamp("UpdateDate");
            
            users.add(user);
        } 
        
        rs.close();
        st.close(); 
        conn.close();
        
        return users;
    }
    
    public static void UpdateUser(UserVO userVO) throws SQLException{
        
        String insertSQL = "UPDATE \"User\" SET " +
        "\"FirstName\"='" + userVO.FirstName + "', " +
        "\"LastName\"='" + userVO.LastName + "', " + 
        "\"Role\"='" + userVO.Role + "', " +
        "\"Email\"='" + userVO.Email + "', " + 
        "\"UpdateDate\"='" + new Timestamp(System.currentTimeMillis()) + "', " + 
        "\"Token\"='" + userVO.Token + "', " + 
        "\"TokenExpiration\"='" + userVO.TokenExpiration + "', " +
        "\"Password\"='" + userVO.Password + "'" +
        " WHERE Id = " + userVO.Id + ";";
        
        Execute(insertSQL);
    }
}
