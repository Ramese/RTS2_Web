/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcontroller;

import com.sun.net.httpserver.Headers;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Radek
 */
public class HTTPProtocol {
    public static final String PATTERN_WEB_FACE = "";
    public static final String PATTERN_API = "";
    public static final String PATTERN_WEB_CONTENT_FOLDER = "";
    public static final String PATTERN_WEB_INDEX_NAME = "";
    
    public static final int RESPONSE_CODE_INTERNAL_ERROR = 500;
    public static final int RESPONSE_CODE_OK = 200;
    public static final int RESPONSE_CODE_PAGE_NOT_FOUND = 404;
    
    public static final String HEADER_CONTENT_TYPE = "Content-type";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-disposition";
    
    public static final String HEADER_CONTENT_TYPE_MP3 = "audio/mpeg";
    public static final String HEADER_CONTENT_TYPE_CSS = "text/css";
    public static final String HEADER_CONTENT_TYPE_JS = "text/js";
    public static final String HEADER_CONTENT_TYPE_PNG = "image/png";
    public static final String HEADER_CONTENT_TYPE_JPG = "image/jpeg";
    public static final String HEADER_CONTENT_TYPE_WOFF2 = "application/font-woff2";
    
    private HTTPProtocol() {
        // no constructor
    }
    
    public static void headerInfo(Headers h){
        Set<String> keys = h.keySet();
        for (String key : keys) {
            System.out.print(key + ":\t");
            List<String> values = h.get(key);
            
            for (String value : values) {
                if(values.get(0).equals(value)){
                    System.out.print(value);
                } else {
                    System.out.print(", " + value);
                }
            }
            
            System.out.println("");
        }
        System.out.println("\n=============");
    }
    
}
