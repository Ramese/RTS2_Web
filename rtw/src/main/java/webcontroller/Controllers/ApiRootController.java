package webcontroller.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Radek
 */
public class ApiRootController implements HttpHandler {
    public String path;
    
    public ApiRootController(String path) {
        this.path = path;
    }
    
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "<h1>This is apiBaseCtrl</h1>";
        
        response += Controller.GetRequestToHTML(t, true);
        
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}
