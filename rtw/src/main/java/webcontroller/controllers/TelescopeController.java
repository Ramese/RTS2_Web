package webcontroller.controllers;

import BO.TelescopeBO;
import VO.InputObjectVO;
import VO.PaginationVO;
import VO.TelescopeVO;
import VO.UserVO;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static rtw.Config.GLOBAL_DEBUG;
import static webcontroller.controllers.Controller.GetObjectFromBody;

/**
 *
 * @author Radek
 */
public class TelescopeController {
    public List<Controller> ctrls = new ArrayList<>();
    
    public TelescopeController() {
        ctrls.add(GetTelescopesCtrl());
        ctrls.add(GetTelescopeCtrl());
        ctrls.add(GetSaveTelescopeCtrl());
    }
    
    private Controller GetTelescopesCtrl() {
        return new Controller("/telescopes", true) {
            @Override
            public void inerHandle(HttpExchange t) {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("TelescopesController:");
                    }
                    
                    PaginationVO pagination = (PaginationVO)GetObjectFromBody(t.getRequestBody(), PaginationVO.class);
                    
                    UserVO user = (UserVO)t.getAttribute("user");
                    
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
    
    private Controller GetTelescopeCtrl() {
        return new Controller("/telescope", true) {
            @Override
            public void inerHandle(HttpExchange t) {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("TelescopeController:");
                    }
                    
                    InputObjectVO id = (InputObjectVO)GetObjectFromBody(t.getRequestBody(), InputObjectVO.class);
                    
                    UserVO user = (UserVO)t.getAttribute("user");
                    
                    Controller.SendGoodResponse(TelescopeBO.GetTelescope(id.Id), t);
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
    
    private Controller GetSaveTelescopeCtrl() {
        return new Controller("/savetelescope", true) {
            @Override
            public void inerHandle(HttpExchange t) {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("TelescopeController:");
                    }
                    
                    TelescopeVO telescope = (TelescopeVO)GetObjectFromBody(t.getRequestBody(), TelescopeVO.class);
                    
                    UserVO user = (UserVO)t.getAttribute("user");
                    
                    if(telescope.Id == 0) { // insert 
                        TelescopeBO.InsertTelescope(telescope);
                    } else { // update
                        TelescopeBO.UpdateTelescope(telescope);
                    }
                    
                    Controller.SendGoodResponse(TelescopeBO.GetTelescope(telescope.Name), t);
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
    
    private Controller GetCurrentImageCtrl() {
        return new Controller("/currentimage", true) {
            @Override
            public void inerHandle(HttpExchange t) {
                try {
                    if(GLOBAL_DEBUG) {
                        System.out.println("CurrentImageController:");
                    }
                    
                    PaginationVO pagination = (PaginationVO)GetObjectFromBody(t.getRequestBody(), PaginationVO.class);
                    
                    UserVO user = (UserVO)t.getAttribute("user");
                    
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
