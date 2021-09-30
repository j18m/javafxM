package TongDaOA;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.HttpUtils;

public class tongdaVuln {

    /**
     * 返回前端数据
     * @param url
     * @return
     */
    public static String GetCookie(String url){
        String Cookie = null;
        if( "".equals(url) || url ==null ){return "url输入为空";}




        return Cookie;
    }


    public static String tongdaGetVersionInfo(String url){
        try {
            String html = HttpUtils.GetBody(url+"/inc/expired.php");
            Document document = Jsoup.parse(html);
            Elements version = document.select("body > table > tbody > tr:nth-child(1) > td > span");
            System.out.println(version);
            return version.text();
        }catch (Exception e){
            return e.toString();
        }
    }

    public static String tongdaComputerName(String url){
        try {
            String html = HttpUtils.GetBody(url+"/resque/worker.php");
            return Jsoup.parse(html).body().text();
        }catch (Exception e){
            return e.toString();
        }


    }


}
