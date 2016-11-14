package webcontroller.Controllers;

import webcontroller.Controllers.Controller;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

/**
 *
 * @author Radek
 */
public class RegistrationController extends Controller {
    
    public RegistrationController(String path) {
        super(path);
    }
    
    @Override
    public void handle(HttpExchange t) throws IOException {
        
        Scanner s = new Scanner(t.getRequestBody()).useDelimiter("\\A");
        String json = s.hasNext() ? s.next() : "";
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(stringToParse);
        
        System.out.println("json: " + json);
        
        String response = "This is registerCtrl\n" + t.getRequestURI().toString();
        t.sendResponseHeaders(200, response.length());
        //OutputStream os = t.getResponseBody();
        //os.write(response.getBytes());
        //os.close();
    }
}
