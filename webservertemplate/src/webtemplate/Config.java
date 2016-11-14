package webtemplate;

/**
 *
 * @author Radek
 */
public class Config {
    public static final boolean GLOBAL_DEBUG = true;
    
    public static final class WebConfig{
        public static final String GLOBAL_WEB_NAME = "RTS2";
        public static final String GLOBAL_WEB_PATH = "/rts2";
        public static final String GLOBAL_WEB_API_PATH = "/API";
        public static final String GLOBAL_WEB_FOLDER_PATH = "./Web";
        public static final String GLOBAL_WEB_INDEX_NAME = "index.html";
        public static final int GLOBAL_WEB_PORT_NUMBER = 81;
        public static int GLOBAL_WEB_SOCKET_PORT_NUMBER = 8080;
    }
    
}
