package webcontroller.Controllers;

import BO.TelescopeBO;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static webtemplate.Config.GLOBAL_DEBUG;

/**
 *
 * @author Radek
 */
public class TelescopeController {
    public List<Controller> ctrls = new ArrayList<>();
    
    public TelescopeController() {
        ctrls.add(GetTelescopesCtrl());
    }
    
    private Controller GetTelescopesCtrl() {
        return new Controller("/telescopes") {
            @Override
            public void handle(HttpExchange t) {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("TelescopesController:");
                    }
                    
                    Controller.SendGoodResponse(TelescopeBO.GetTelescopes(), t);
                } catch (Exception ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("message: " + ex.getMessage());
                    System.out.println("loc mes:" + ex.getLocalizedMessage());
                    
                    try {
                        ex.printStackTrace();
                        Controller.Exception(ex.getMessage(), t, "Server error.");
                    } catch (Exception ex1) {
                        Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        };
    }
}
