package webcontroller.Controllers;

import BO.UserBO;
import VO.UserVO;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static webtemplate.Config.GLOBAL_DEBUG;

/**
 *
 * @author Radek
 */
public class RegistrationController {
    
    public List<Controller> ctrls = new ArrayList<>();
    
    public RegistrationController() {
        ctrls.add(GetRegistrationCtrl());   
        ctrls.add(GetCheckUserNameCtrl());
        ctrls.add(GetCheckUserEmailCtrl());
    }
    
    private Controller GetRegistrationCtrl() {
        return new Controller("/register") {
            @Override
            public void handle(HttpExchange t) {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("RegistrationController:");
                    }
                    
                    UserVO regUser = (UserVO)Controller.GetObjectFromBody(t.getRequestBody(), UserVO.class);

                    // check if the object is ok
                    if(!UserBO.IsUserValidForInsert(regUser)) {
                        Controller.ExpectationFailed("Wrong user data. inputJSON: ", t, "Registration of new user is not successful. User data are incorrect.");
                        return;
                    }

                    // save object
                    UserBO.InsertUser(regUser);

                    // return new ID, token and 200
                    regUser = UserBO.GetUserByUserName(regUser.UserName);
                    
                    Controller.SendGoodResponse(regUser, t);
                } catch (Exception ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("message: " + ex.getMessage());
                    System.out.println("loc mes:" + ex.getLocalizedMessage());
                    
                    ex.printStackTrace();
                    
                    try {
                        Controller.Exception(ex.getMessage(), t, "Server error.");
                    } catch (Exception ex1) {
                        Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        };
    }
    
    private Controller GetCheckUserNameCtrl() {
        return new Controller("/register/isUsernameFree") {
            @Override
            public void handle(HttpExchange t) throws IOException {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("CheckUserNameController:");
                    }
                    
                    UserVO regUser = (UserVO)Controller.GetObjectFromBody(t.getRequestBody(), UserVO.class);

                    // return new ID, token and 200
                    try {
                        regUser = UserBO.GetUserByUserName(regUser.UserName);
                    } catch (SQLException ex) {
                        Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Controller.SendGoodResponse(regUser == null, t);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
    
    private Controller GetCheckUserEmailCtrl() {
        return new Controller("/register/isUserEmailFree") {
            @Override
            public void handle(HttpExchange t) throws IOException {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("CheckUserEmailController:");
                    }
                    
                    UserVO regUser = (UserVO)Controller.GetObjectFromBody(t.getRequestBody(), UserVO.class);

                    // return new ID, token and 200
                    regUser = UserBO.GetUserByEmail(regUser.Email);

                    Controller.SendGoodResponse(regUser == null, t);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
}
