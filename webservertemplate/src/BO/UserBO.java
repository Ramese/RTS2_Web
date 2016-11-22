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
        return UserDO.GetUserByUserName(userName);
    }
    
    public static void InsertUser(UserVO userVO) throws Exception {
        if(IsUserValidForInsert(userVO))
        {
            userVO.CreateDate = new Timestamp(System.currentTimeMillis());
            userVO.TokenExpiration = userVO.CreateDate;
            userVO.UpdateDate = userVO.CreateDate;
            userVO.Token = UUID.randomUUID();
            UserDO.InsertUser(userVO);
        }
        else
            throw new Exception("User data are invalid.");
    }
}
