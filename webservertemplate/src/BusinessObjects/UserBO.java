package BusinessObjects;

import DataObjects.UserDO;
import ValueObjects.RoleVO;
import ValueObjects.UserVO;
import java.sql.SQLException;

/**
 *
 * @author Radek
 */
public class UserBO {
    public static boolean IsUserValidForInsert(UserVO userVO) {
        if(userVO == null) {
            return false;
        }
        
        if(userVO.UserName == null || userVO.UserName.trim().length() < 3){
            return false;
        }
        
        if(userVO.Email == null)
            return false;
        
        if(userVO.Password == null)
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
    
    public static void InsertUser(UserVO userVO) throws Exception {
        if(IsUserValidForInsert(userVO))
            UserDO.InsertUser(userVO);
        else
            throw new Exception("User data are invalid.");
    }
}
