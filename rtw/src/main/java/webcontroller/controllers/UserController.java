package webcontroller.controllers;

import webcontroller.controllers.Controller;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Radek
 */
public class UserController extends Controller {
    
    public UserController(String path) {
        super(path, false);
    }
    
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "<h1>This is apiBaseCtrl</h1>" + t.getRequestURI().toString();
        response += Controller.GetRequestToHTML(t, true);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}
