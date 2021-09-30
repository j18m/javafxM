package utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class MyBase64 {
    public static void main(String[] args) throws Exception{



        String weekpass = "1234567";
        String bpass="MTIzNDU2Nw==";
        System.out.println(b64Encode(weekpass));
        System.out.println(b64Decode(bpass));

    }

    public static String b64Decode(String str){
        return new String(Base64.getDecoder().decode(str));
    }

    public static String b64Encode(String str) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
}
