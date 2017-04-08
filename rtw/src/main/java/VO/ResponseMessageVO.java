package VO;

/**
 *
 * @author Radek
 */
public class ResponseMessageVO {
    public ResponseMessageVO() {
        this.isTranslatable = false;
    }
    
    public String message;
    public boolean isTranslatable;
    
    public boolean isInternal;
    public HttpResult messageObject;
    
    public class HttpResult {
        public String message;
        public String statusLine;
        public int statusCode;
    }
}


