package BO;

import DO.TelescopeDO;
import VO.TelescopeVO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Radek
 */
public class TelescopeBO {
    public static List<TelescopeVO> GetTelescopes() throws SQLException {
        return TelescopeDO.GetTelescopes();
    }
     
    public static TelescopeVO GetTelescope(long id) throws SQLException {
        return TelescopeDO.GetTelescope(id);
    }
    
    public static TelescopeVO GetTelescope(String name) throws SQLException {
        return TelescopeDO.GetTelescopeByName(name);
    }
    
    public static boolean IsTelescopeValidForInsert(TelescopeVO telescopeVO) {
        if(telescopeVO == null) {
            return false;
        }
        
        if(telescopeVO.Name == null || telescopeVO.Name.trim().length() < 3){
            return false;
        }
        
        return true;
    }
    
    public static void InsertTelescope(TelescopeVO telescopeVO) throws Exception {
        if(IsTelescopeValidForInsert(telescopeVO))
        {
            TelescopeDO.InsertTelescope(telescopeVO);
        }
        else
            throw new Exception("Telescope data are invalid.");
    }
}
