package Tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import sun.net.www.http.HttpClient;

/**
 *
 * @author Radek
 */
public class HttpClientTest {
    public static void main(String[] args) throws MalformedURLException, IOException {
        URL url = new URL("http://192.168.56.101:8889/api/devices");
        URL urlSeznam = new URL("https://seznam.cz");
        
        HttpClient hc = HttpClient.New(url);
        
        //hc.
        
        
    }
}
