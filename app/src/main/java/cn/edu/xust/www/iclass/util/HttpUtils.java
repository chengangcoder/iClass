package cn.edu.xust.www.iclass.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chengangcoder on 2016/10/19.
 */

public class HttpUtils {
    public static final int HTTP_POST = 1;
    public static final int HTTP_GET = 0;
    //远程"http://115.159.63.34:80/IClass/"
    public static final String GLOBAL_ADDR = "http://115.159.63.34:80/IClass/";

    public static HttpURLConnection send(int what, URL url, byte[] bytes) {

        HttpURLConnection urlConnection = null;
        switch (what) {
            case HTTP_GET:
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    //设置GET请求
                    urlConnection.setRequestMethod("GET");


                    //urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    //不适用缓存
                    urlConnection.setUseCaches(false);
                    //设置连接超时时间
                    urlConnection.setConnectTimeout(3000);

                    //设置从服务器读数据超时时间

                    urlConnection.setReadTimeout(3000);

                    //Unused by Android.
                    urlConnection.connect();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case HTTP_POST:
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length", bytes.length + "");

                    urlConnection.setReadTimeout(3000);
                    urlConnection.setConnectTimeout(3000);
                    //urlConnection.setUseCaches(false);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;


        }


        return urlConnection;
    }

    public static HttpURLConnection send(int what, URL url) {
        return send(HTTP_GET, url, null);
    }
}
