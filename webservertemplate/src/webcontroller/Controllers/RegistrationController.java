package webcontroller.Controllers;

import BO.UserBO;
import VO.ResponseMessageVO;
import VO.UserVO;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.net.HttpURLConnection;
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
    }
    
    private Controller GetRegistrationCtrl() {
        return new Controller("/register") {
            @Override
            public void handle(HttpExchange t) throws IOException {

                if(GLOBAL_DEBUG) {
                    System.out.println("RegistrationController:");
                }
                // get JSON
                Scanner s = new Scanner(t.getRequestBody()).useDelimiter("\\A");
                String inputJSON = s.hasNext() ? s.next() : "";

                // transfer JSON to Value Object
                Gson gson = new Gson();
                UserVO regUser = (UserVO)gson.fromJson(inputJSON, UserVO.class);

                // check if the object is ok
                if(!UserBO.IsUserValidForInsert(regUser)) {

                    ResponseMessageVO response = new ResponseMessageVO();

                    response.message = "Registration of new user is not successful. User data are incorrect.";

                    String responseString = gson.toJson(response);

                    t.sendResponseHeaders(HttpURLConnection.HTTP_NOT_ACCEPTABLE, responseString.length());
                    OutputStream os = t.getResponseBody();
                    os.write(responseString.getBytes());
                    os.close();

                    return;
                }


                // save object
                try {
                    UserBO.InsertUser(regUser);
                } catch (Exception ex) {
                    // need to resolve if it is exception of DB connection or something else (UQ etc.)
                    ex.printStackTrace();

                    // ad-hoc solution
                    String response = "";
                    t.sendResponseHeaders(HttpURLConnection.HTTP_NOT_ACCEPTABLE, response.length());
                    return;
                }

                // return new ID, token and 200
                try {
                    regUser = UserBO.GetUserByUserName(regUser.UserName);
                } catch (SQLException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }

                String outputJSON = gson.toJson(regUser);
                System.out.println("json: " + inputJSON);
                System.out.println("user" + outputJSON);

                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, outputJSON.length());
                OutputStream os = t.getResponseBody();
                os.write(outputJSON.getBytes());
                os.close();
            }
        };
    }
    
    private Controller GetCheckUserNameCtrl() {
        return new Controller("/register/isUsernameFree") {
            @Override
            public void handle(HttpExchange t) throws IOException {

                if(GLOBAL_DEBUG) {
                    System.out.println("CheckUserNameController:");
                }
                
                // get JSON
                Scanner s = new Scanner(t.getRequestBody()).useDelimiter("\\A");
                String inputJSON = s.hasNext() ? s.next() : "";

                // transfer JSON to Value Object
                Gson gson = new Gson();
                UserVO regUser = (UserVO)gson.fromJson(inputJSON, UserVO.class);

                // return new ID, token and 200
                try {
                    regUser = UserBO.GetUserByUserName(regUser.UserName);
                } catch (SQLException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                boolean result = regUser == null;
                
                String outputJSON = gson.toJson(result);
                System.out.println("json: " + inputJSON);
                System.out.println("user" + outputJSON);

                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, outputJSON.length());
                OutputStream os = t.getResponseBody();
                os.write(outputJSON.getBytes());
                os.close();
            }
        };
    }
}
