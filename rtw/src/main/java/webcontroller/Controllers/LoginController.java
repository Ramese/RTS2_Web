package webcontroller.Controllers;

import BO.UserBO;
import VO.UserVO;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import webcontroller.HTTPProtocol;
import static rtw.Config.GLOBAL_DEBUG;

/**
 *
 * @author Radek
 */
public class LoginController {
    public List<Controller> ctrls = new ArrayList<>();
    
    public LoginController() {
        ctrls.add(GetLoginCtrl());
    }
    
    private Controller GetLoginCtrl() {
        return new Controller("/login") {
            @Override
            public void handle(HttpExchange t) {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("LoginController:");
                    }
                    
                    UserVO userCredentials = (UserVO)Controller.GetObjectFromBody(t.getRequestBody(), UserVO.class);

                    // save object
                    UserVO user = UserBO.Login(userCredentials.UserName, userCredentials.Password);

                    if(user == null) {
                        Controller.ExpectationFailed("Incorrect credentials.", t, "Incorrect user name or password.");
                        return;
                    }
                    
                    Controller.SendGoodResponse(user, t);
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
}
