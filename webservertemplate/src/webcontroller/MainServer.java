package webcontroller;

import webcontroller.Controllers.Controller;
import webcontroller.Controllers.ApiRootController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import webcontroller.Controllers.RegistrationController;
import webcontroller.websocket.WSServer;
import static webtemplate.Config.WebConfig.GLOBAL_WEB_PATH;
import static webtemplate.Config.WebConfig.GLOBAL_WEB_API_PATH;
import static webtemplate.Config.WebConfig.GLOBAL_WEB_INDEX_NAME;
import static webtemplate.Config.WebConfig.GLOBAL_WEB_PORT_NUMBER;
import static webtemplate.Config.WebConfig.GLOBAL_WEB_NAME;
import static webtemplate.Config.WebConfig.GLOBAL_WEB_FOLDER_PATH;

/**
 * MainServer is the main service running and listening socket communication. 
 * Object includes array of controllers, port, HttpServer, WSServer etc.
 * @author Radek
 */
public class MainServer {
    private HttpServer server;
    private WSServer wsServer;
    private final String APIPath;
    private final String WebPath;
    private final String WebName;
    private final int portNumber;
    private final String webFolderName;
    private final String indexName;
    
    private final List<Controller> controllers;
    private WebFaceController mainController;
    private ApiRootController apiController;
    
            
    /**
     * Create webserver and controllers.
     */
    public MainServer(){
        
        this.WebPath = GLOBAL_WEB_PATH;
        this.WebName = GLOBAL_WEB_NAME;
        this.APIPath = (this.WebPath + GLOBAL_WEB_API_PATH).replace("//", "/");
        this.portNumber = GLOBAL_WEB_PORT_NUMBER;
        this.webFolderName = GLOBAL_WEB_FOLDER_PATH;
        this.indexName = GLOBAL_WEB_INDEX_NAME;
        
        this.controllers = new ArrayList<>();
        this.initWebServer();
        
        this.wsServer = new WSServer();
        wsServer.run();
    }
    
    /**
     * Info
     */
    private void optionsInfo(){
        System.out.printf("Server X běží:\nWeb: localhost:%d%s\nAPI: localhost:%d%s\n", portNumber, WebPath, portNumber, APIPath);
    }
    
    /**
     * 
     */
    private void controllersInfo(){
        System.out.println("Controllers:");
        if(this.mainController != null){
            System.out.println(mainController.path);
        } else {
            System.out.println("MainController is not defined.");
        }
        
        if(this.apiController != null){
            System.out.println(apiController.path);
        } else {
            System.out.println("ApiController is not defined.");
        }
        
        System.out.println("Specific controllers count: " + controllers.size());
        for (Controller controller : controllers) {
            System.out.println(controller.path);
        }
    }
    
    private void initControllers(){
        
        this.mainController = new WebFaceController(WebPath, webFolderName, indexName, WebName);
        
        this.apiController = new ApiRootController(this.APIPath);
        
        Controller exampleCtrl = new Controller("/example/id") {
            @Override
            public void handle(HttpExchange t) throws IOException {

                String response = "This is exampleCtrl\n" + t.getRequestURI().toString();
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        };
        
        this.controllers.add(exampleCtrl);
        this.controllers.add(new RegistrationController("/register"));
        //this.controllers.add(apiBaseCtrl);
    }
    
    private void initWebServer(){
        
        try {
            server = HttpServer.create(new InetSocketAddress(this.portNumber), 0);
            this.optionsInfo();
            
            this.initControllers();
            controllersInfo();
            
            for (Controller controller : controllers) {
                System.out.println("Init ctrl: " + this.APIPath + controller.path);
                server.createContext(this.APIPath + controller.path, controller);
            }
            
            if(apiController != null){
                System.out.println("init ctrl: " + apiController.path);
                server.createContext(apiController.path, apiController);
            }
            
            
            
            if(mainController != null){
                server.createContext(mainController.path, mainController);
            }
            
            
            
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Server started!\n\n");
        } catch (IOException ex) {
            if(ex.getMessage().equals("Address already in use: bind")){
                System.out.println("Server is not running, already bind.");
            }else {
                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stop() {
        server.stop(1);
    }
}
