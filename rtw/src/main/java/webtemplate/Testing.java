package webtemplate;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import webcontroller.websocket.WSProtocol;

public class Testing {
    
    private static void TestRegexp(){
        List<String> paths = new ArrayList<>();
        
        paths.add("/");
        paths.add("/asdsd");
        paths.add("/asdsd/adsd");
        paths.add("/asd11sd/adsd123");
        paths.add("/asdsd/{adsd}");
        paths.add("/asd11sd/{adsd123}");
        paths.add("/asd11sd/{adsd123}/12das");
        paths.add("/asd11sd/{adsd123}/125das/{ads}");
        
        paths.add("");
        paths.add("///");
        paths.add("{}");
        paths.add("/dasd//dasd");
        paths.add("/{jiji123}");
        paths.add("/{asdsd}");
        paths.add("/asd11sd/{adsd123}/{}");
        paths.add("/asd11sd/{}");
        paths.add("/{}");
        paths.add("/asd11sd/{adsd123}{}");
        
        String patern = "(/[a-zA-Z0-9]{0,20}?)((/[{]{1}[a-zA-Z0-9]{1,20}?[}]{1})*|(/[a-zA-Z0-9]{1,20}?)*)";
                //"/[a-zA-Z0-9]*((/[a-zA-Z0-9]*)|(/{[a-zA-Z0-9]*" + "})*)*";
        
        for (String path : paths) {
            if(path.matches(patern)){
                System.out.println("OK " + path);
            } else {
                System.out.println("NOT " + path);
            }
        }
    }
    
    private static boolean ServerClientKeyTest() throws NoSuchAlgorithmException{
        String clientKeys[] = new String[2];
        String serverKeys[] = new String[2];
        
        clientKeys[0] = "uRovscZjNol/umbTt5uKmw==";
        serverKeys[0] = "rLHCkw/SKsO9GAH/ZSFhBATDKrU=";
        
        clientKeys[1] = "dGhlIHNhbXBsZSBub25jZQ==";
        serverKeys[1] = "s3pPLMBiTxaQ9kYGzzhZRbK+xOo=";
        
        for (int i = 0; i < clientKeys.length; i++) {
            if(!serverKeys[i].equals(WSProtocol.getServerKey(clientKeys[i]))) {
                System.err.println("Test Keys failed. i = " + i);
                return false;
            }
        }
        System.out.println("Test Keys success.");
        return true;
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            ServerClientKeyTest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
