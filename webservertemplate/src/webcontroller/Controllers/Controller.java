package webcontroller.Controllers;

import VO.ResponseMessageVO;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import webcontroller.HTTPProtocol;
import static webtemplate.Config.GLOBAL_DEBUG;

/**
 *
 * @author Radek
 */
public class Controller implements HttpHandler {
    public final String path;
    public String regex;
    
    public static final String RESPONSE_TOKEN_EXPIRED		= "TOKEN_EXPIRED";
    public static final String RESPONSE_TOKEN_WRONG		= "TOKEN_WRONG";
    public static final String RESPONSE_EXCEPTION               = "EXCEPTION_OCCURRED";
    public static final String RESPONSE_TOKEN_UNAUTHORIZED      = "TOKEN_UNAUTHORIZED";
    
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
    
    public static void TokenUnparsable(String message, HttpExchange request) throws IOException {
        // log message
        System.out.println("TokenUnparsable: " + message);
        
        SendBadResponse(RESPONSE_TOKEN_WRONG, request, HttpURLConnection.HTTP_NOT_ACCEPTABLE);
    }
    
    public static void NotLoggedIn(String message, HttpExchange request) throws IOException {
        // log message
        System.out.println("NotLoggedIn: " + message);
        
        SendBadResponse(RESPONSE_TOKEN_EXPIRED, request, HttpURLConnection.HTTP_NOT_ACCEPTABLE);
    }
    
    public static void Unauthorized(String message, HttpExchange request) throws IOException {
        // log message 
        System.out.println("Unauthorized: " + message);
        
        SendBadResponse(RESPONSE_TOKEN_UNAUTHORIZED, request, HttpURLConnection.HTTP_UNAUTHORIZED);
    }
    
    public static void Exception(String message, HttpExchange request, String messageForUser) throws IOException {
        // log message
        System.out.println("Exception: " + message);
        
        SendBadResponse(messageForUser, request, HttpURLConnection.HTTP_INTERNAL_ERROR, false);
    }
    
    public static void SaveException(String message, HttpExchange request, String messageForUser) throws IOException {
        // log message
        System.out.println("SaveException: " + message);
        
        SendBadResponse(messageForUser, request, HttpURLConnection.HTTP_INTERNAL_ERROR, false);
    }
    
    public static void ExpectationFailed(String message, HttpExchange request, String messageForUser) throws IOException {
        // log message
        System.out.println("ExpectationFailed: " + message);
        
        SendBadResponse(messageForUser, request, HttpURLConnection.HTTP_PRECON_FAILED, false);
    }
    
    private static void SendBadResponse(String message, HttpExchange request, int code) throws IOException {
        SendBadResponse(message, request, code, true);
    }
    
    private static void SendBadResponse(String message, HttpExchange request, int code, boolean isTranslatable) throws IOException {
        ResponseMessageVO response = new ResponseMessageVO();
        
        response.message = message;
        response.isTranslatable = isTranslatable;
        
        Gson gson = new Gson();
        
        String outputJSON = gson.toJson(response);
        
        request.getResponseHeaders().add(HTTPProtocol.HEADER_CONTENT_TYPE, HTTPProtocol.HEADER_CONTENT_TYPE_JSON);
        
        request.sendResponseHeaders(code, outputJSON.length());
        OutputStream os = request.getResponseBody();
        os.write(outputJSON.getBytes());
        os.close();
    }
    
    public static void SendGoodResponse(Object data, HttpExchange request) throws IOException {
        Gson gson = new Gson();
        
        String outputJSON = gson.toJson(data);
        
        if(GLOBAL_DEBUG) {
            System.out.println("output  " + outputJSON);
        }
        
        request.getResponseHeaders().add(HTTPProtocol.HEADER_CONTENT_TYPE, HTTPProtocol.HEADER_CONTENT_TYPE_JSON);
        
        request.sendResponseHeaders(HttpURLConnection.HTTP_OK, outputJSON.length());
        OutputStream os = request.getResponseBody();
        os.write(outputJSON.getBytes());
        os.close();
    }
    
    public static Object GetObjectFromBody(InputStream body, Class _class) {
        // get JSON
        Scanner s = new Scanner(body).useDelimiter("\\A");
        String inputJSON = s.hasNext() ? s.next() : "";
        
        if(GLOBAL_DEBUG){
            System.out.println("json: " + inputJSON);
        }

        // transfer JSON to Value Object
        Gson gson = new Gson();
        return gson.fromJson(inputJSON, _class);
    }
}
