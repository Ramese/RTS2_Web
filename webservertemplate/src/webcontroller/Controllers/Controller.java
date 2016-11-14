package webcontroller.Controllers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 *
 * @author Radek
 */
public class Controller implements HttpHandler {
    public final String path;
    public String regex;
    
    public static String GetRequestToHTML(HttpExchange t, boolean html) {
        String newLine = "\n";
        
        if(html) {
            newLine = "<br />";
        }
        
        String result = newLine;
        result += "Method: " + t.getRequestMethod() + newLine;
        result += "Protocol: " + t.getProtocol() + newLine;
        result += "URI: " + t.getRequestURI().toString() + newLine;
        
        
        Headers hs = t.getRequestHeaders();
        
        for(Entry<String, List<String>> key : hs.entrySet()) {
            result += key.getKey() + ":";
            
            for(String value : key.getValue()){
                result += " " + value;
            }
            
            result += newLine;
        }
        
        result += newLine;
        
        Scanner s = new Scanner(t.getRequestBody()).useDelimiter("\\A");
        result += s.hasNext() ? s.next() : "";
        
        return result;
    }

    public Controller(String path){
        
        if(path.startsWith("/")){
            this.path = path;
        } else {
            this.path = "/" + path;
        }
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("Not imp.");
    }
}
