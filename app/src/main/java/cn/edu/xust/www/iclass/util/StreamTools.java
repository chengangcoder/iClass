package cn.edu.xust.www.iclass.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chengangcoder on 2016/10/19.
 */

public class StreamTools {

    public static String readInputStream(InputStream is) {

      //  InputStreamReader isr = new InputStreamReader(is);

        byte[] response = new byte[1024];

        int length = 0;

       // StringBuilder sb = new StringBuilder();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();


        String result = null;

        try {
            while ((length = is.read(response)) != -1) {

                 byteArray.write(response, 0, length);
            }

            is.close();

            byteArray.close();

            result = byteArray.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}