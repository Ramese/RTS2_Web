package client;

import VO.ResponseMessageVO;
import VO.TelescopeImageVO;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class RTS2Client {
    
    /**
     * Universal method for http request. Just to reduce code.
     * @param serverUrl
     * @param query
     * @param userName
     * @param password
     * @return
     * @throws IOException 
     */
    private static CloseableHttpResponse GetResponse(String serverUrl, String query, String userName, String password) throws IOException {
        if(serverUrl.startsWith("http://"))
            serverUrl = serverUrl.substring(7);
        
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, password);
        
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        
        credsProvider.setCredentials(new AuthScope(serverUrl, AuthScope.ANY_PORT), creds);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        HttpGet httpget = new HttpGet("http://" + serverUrl + query);
        return httpclient.execute(httpget, context);
    }
    
    /**
     * Get String array of devices.
     * @param serverUrl
     * @param userName
     * @param password
     * @return
     * @throws IOException 
     */
    public static String GetDevices(String serverUrl, String userName, String password) throws IOException {
        CloseableHttpResponse response = GetResponse(serverUrl, "/api/devices", userName, password);
        
        
        return "";
    }
    
    public static void GetCurrentImage(String serverUrl, String userName, String password) throws MalformedURLException, IOException {
        
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, password);
        
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        
        credsProvider.setCredentials(
            new AuthScope("192.168.56.101", AuthScope.ANY_PORT), creds);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        HttpGet httpget = new HttpGet("http://" + serverUrl + "/api/currentimage?ccd=C0");
        CloseableHttpResponse response = httpclient.execute(httpget, context);
        
        try {

            System.out.println(response.getProtocolVersion());
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine().getReasonPhrase());
            System.out.println(response.getStatusLine().toString());

            InputStream is = response.getEntity().getContent();
            
            DataInputStream dis = new DataInputStream(is);
            
            TelescopeImageVO timg = TelescopeImageVO.Fill(dis);
            
            
            
            
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String inputJSON = s.hasNext() ? s.next() : "";
            
            System.out.println("json "+inputJSON);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            response.close();
        }
    }
}
