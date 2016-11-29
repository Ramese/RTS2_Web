package BO;

import DO.UserDO;
import VO.RoleVO;
import VO.UserVO;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 *
 * @author Radek
 */
public class UserBO {
    
    public static UserVO Login(String userName, String password) throws SQLException {
        UserVO user = UserDO.GetUserByUserName(userName);
        
        if(user == null) {
            return null;
        }
        
        if(user.Password.equals(password)) {
            //if token is expired - generate new token
            if(!user.TokenExpiration.after(new Timestamp(System.currentTimeMillis()))) {
                user.Token = UUID.randomUUID();
            }
            //extend token time
            long eightHours = 8*60*60*1000;
            user.TokenExpiration = new Timestamp(System.currentTimeMillis() + eightHours);
            
            //save
            UserDO.UpdateUser(user);
            
            //erase pass for client site
            user.Password = null;
            
            //return token
            return user;
        } else {
            return null;
        }
    }
    
    public static boolean IsPasswordStrong(String pass){
        if(pass == null || pass.equals(""))
            return false;
        if(pass.trim().length() < 8)
            return false;
        
        return true;
    }
    
    /**
     * 
     * @param userVO
     * @return 
     */
    public static boolean IsUserValidForInsert(UserVO userVO) {
        if(userVO == null) {
            return false;
        }
        
        if(userVO.UserName == null || userVO.UserName.trim().length() < 3){
            return false;
        }
        
        if(userVO.Email == null)
            return false;
        
        if(!IsPasswordStrong(userVO.Password))
            return false;
        
        if(userVO.Role == null)
            return false;
        
        if(!(userVO.Role.equals(RoleVO.ADMIN) || userVO.Role.equals(RoleVO.PUBLIC) || userVO.Role.equals(RoleVO.SCIENTIST)))
            return false;
        
        return true;
    }
    
    public static UserVO GetUser(long id) throws SQLException {
        return UserDO.GetUser(id);
    }
    
    public static UserVO GetUserByUserName(String userName) throws SQLException {
        return UserDO.GetUserByUserName(userName);
    }
    
    public static UserVO GetUserByEmail(String userName) throws SQLException {
        return UserDO.GetUserByEmail(userName);
    }
    
    public static UserVO GetUserByToken(String token) throws SQLException {
        return UserDO.GetUserByToken(token);
    }
    
    public static void InsertUser(UserVO userVO) throws Exception {
        if(IsUserValidForInsert(userVO))
        {
            UserDO.InsertUser(userVO);
        }
        else
            throw new Exception("User data are invalid.");
    }
}
