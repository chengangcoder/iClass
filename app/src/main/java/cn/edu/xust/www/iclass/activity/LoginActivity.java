package cn.edu.xust.www.iclass.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cn.edu.xust.www.iclass.R;
import cn.edu.xust.www.iclass.util.HttpUtils;
import cn.edu.xust.www.iclass.util.StreamTools;

public class LoginActivity extends FragmentActivity {

    private static final int BITMAP_RETURN_SUCCESS = 0x01;
    private static final int BITMAP_RETURN_FAIL = 0x02;
    private static final int LOGIN_SUCCESS = 0x03;
    private static final int LOGIN_FAIL = 0x04;
    private static final int USERNAME_ALREADY_EXIST = 0x05;
    private static final int EMPTY_USERNAME = 0x06;
    private EditText name;
    private EditText password;
    private EditText editText_verification_code;
    private ImageView image_verification_code;
    private Bitmap bitmap;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BITMAP_RETURN_SUCCESS:
                    if (bitmap != null)
                        image_verification_code.setImageBitmap(bitmap);
                    break;
                case BITMAP_RETURN_FAIL:
                    image_verification_code.setImageResource(R.drawable.fail);
                    break;

                case USERNAME_ALREADY_EXIST:
                    name.setError("用户名已存在!");
                    break;
                case EMPTY_USERNAME:
                    name.setError("用户名不能为空!");
                    break;
                case LOGIN_SUCCESS:
                    Toast.makeText(LoginActivity.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(LoginActivity.this, MainActivity.class_idle);

                    //startActivity(intent);
                    //finish();
                    break;
                case LOGIN_FAIL:
                    Toast.makeText(LoginActivity.this, "登陆失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initViews();
        downVerificationCode();


    }


    // 下载验证码图片
    private void downVerificationCode() {


        new Thread() {

            @Override
            public void run() {
                URL url = null;
                try {

                    url = new URL(HttpUtils.GLOBAL_ADDR + "servlet/ImageServlet");

                    HttpURLConnection urlConnection = HttpUtils.send(HttpUtils.HTTP_GET, url);

                    InputStream inputStream = urlConnection.getInputStream();


                    bitmap = BitmapFactory.decodeStream(inputStream);

                    inputStream.close();

                    handler.sendEmptyMessage(BITMAP_RETURN_SUCCESS);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    // 初始化控件
    private void initViews() {
        name = (EditText) findViewById(R.id.editText_name);
        password = (EditText) findViewById(R.id.editText_password);
        editText_verification_code = (EditText) findViewById(R.id.editText_Verification_Code);
        image_verification_code = (ImageView) findViewById(R.id.Verification_Code);


        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
              /*  if (!hasFocus) {
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        handler.sendEmptyMessage(EMPTY_USERNAME);
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                // super.run();
                                try {
                                    URL url = new URL(HttpUtils.GLOBAL_ADDR + "servlet/ValidateUserCode");

                                    String param = new String();


                                    param = "usercode=" + name.getText().toString();

                                    byte[] paramBytes = param.getBytes();

                                    HttpURLConnection urlConnection = HttpUtils.send(HttpUtils.HTTP_POST, url, paramBytes);

                                    OutputStream os = urlConnection.getOutputStream();

                                    os.write(paramBytes);

                                    int responseCode = urlConnection.getResponseCode();

                                    Log.i("code", "" + responseCode);
                                    //200成功 302重定向 404没找到 5xx内部错误
                                    if (responseCode == 200) {

                                        InputStream inputStream = urlConnection.getInputStream();


                                        String result = StreamTools.readInputStream(inputStream);

                                        Log.i("result=", result + "");

                                        if (!result.equals("no"))

                                            handler.sendEmptyMessage(USERNAME_ALREADY_EXIST);
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }.start();
                    }
                }*/
            }
        });

    }

    // 点击按钮，提交登陆请求
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                Intent intent_temp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent_temp);
                finish();
                //login();
                break;
            case R.id.register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void login() {
        new Thread() {
            @Override
            public void run() {
                //super.run();
                try {
                    URL url = new URL(HttpUtils.GLOBAL_ADDR + "/servlet/LoginServlet");
                    String param = new String();
                    param = "rolename=" + URLEncoder.encode("学生") +
                            "&username=" + URLEncoder.encode(name.getText().toString()) +
                            "&password=" + URLEncoder.encode(password.getText().toString()) +
                            "&checkCode=" + URLEncoder.encode(editText_verification_code.getText().toString());

                    //String encode = URLEncoder.encode(param);
                    Log.i("encode", param);
                    byte[] bytes = param.getBytes();

                    HttpURLConnection urlConnection = HttpUtils.send(HttpUtils.HTTP_POST, url, bytes);

                    OutputStream outputStream = urlConnection.getOutputStream();

                    outputStream.write(bytes);


                    int responseCode = urlConnection.getResponseCode();

                    if (responseCode == 200) {
                        //Log.i("test", "test");
                        InputStream inputStream = urlConnection.getInputStream();

                        String result = StreamTools.readInputStream(inputStream);

                        Log.i("result=", result);

                        if (result.equals("success")) {
                            handler.sendEmptyMessage(LOGIN_SUCCESS);
                        } else {
                            handler.sendEmptyMessage(LOGIN_FAIL);
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    // 点击验证码图片，实现刷新
    public void onFresh(View v) {
        downVerificationCode();
    }
}
