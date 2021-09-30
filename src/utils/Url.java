package utils;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Url {

    private static DefaultHttpClient defaultHttpClient = null;
    private String url;
//    private static void setHeader(HttpRequestBase httpRequestBase) {
//        httpRequestBase.setHeader("Accept", ReadConfig.ACCEPT);
//        httpRequestBase.setHeader("User-Agent", ReadConfig.USER_AGENT);
//        httpRequestBase.setHeader("Content-Type", ReadConfig.CONTENT_TYPE);
//        httpRequestBase.setHeader("Accept-Charset", ReadConfig.ACCEPT_CHARSET);
//        httpRequestBase.setHeader("Cookie", ReadConfig.COOKIE);
//    }


    public static void main(String[] args) throws Exception{
        System.out.println(Fofa.EMAIL);
        System.out.println(Fofa.KEY);
        System.out.println(doGet(Fofa.BASEURL));
    }

    public static String doPost(String url){
        String result = null;
        HttpPost post = new HttpPost(url);

        return "";
    }

    public static String doGet(String url) throws IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        HttpGet get = new HttpGet(url);
        try {
            response = httpClient.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//            response.close();
            httpClient.close();
        }
        return result;
    }




}
