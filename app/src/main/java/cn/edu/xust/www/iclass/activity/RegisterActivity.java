package cn.edu.xust.www.iclass.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.xust.www.iclass.R;
import cn.edu.xust.www.iclass.util.HttpUtils;
import cn.edu.xust.www.iclass.util.StreamTools;

public class RegisterActivity extends FragmentActivity {
    private static final int REGISTER_SUCCESS = 0;
    private static final int REGISTER_FAIL = 1;
    private static final int CONNECT_ERROR = 2;
    private static final int USERNAME_ALREADY_EXIST = 3;
    private static final int EMPTY_USERNAME = 4;
    private static final int USERCODE_ALREADY_EXIST = 5;
    private static final int EMPTY_USERCODE = 6;
    private static final int RETURN_DATE = 7;
    private RadioButton roleName_checkedButton;
    private RadioButton sex_checkedButton;
    private EditText editText_name;
    private EditText editText_userCode;
    private EditText editText_password;
    private TextView editText_userBirth;
    private EditText editText_userFullName;
    private EditText editText_userEmail;
    private EditText editText_userPhoneNum;
    private ImageView register_verification_code;
    private EditText editText_register_verification_code;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case REGISTER_SUCCESS:
                    Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case REGISTER_FAIL:
                    Toast.makeText(RegisterActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
                    break;
                case CONNECT_ERROR:
                    Toast.makeText(RegisterActivity.this, "服务器无响应!", Toast.LENGTH_SHORT).show();
                    break;
                case USERCODE_ALREADY_EXIST:
                    Toast.makeText(RegisterActivity.this, "用户码已存在!", Toast.LENGTH_SHORT).show();
                    break;
                case USERNAME_ALREADY_EXIST:
                    Toast.makeText(RegisterActivity.this, "用户名已存在！", Toast.LENGTH_SHORT).show();
                    break;
                case EMPTY_USERCODE:
                    Toast.makeText(RegisterActivity.this, "用户码为空！", Toast.LENGTH_SHORT).show();
                    break;
                case EMPTY_USERNAME:
                    Toast.makeText(RegisterActivity.this, "用户名为空！", Toast.LENGTH_SHORT).show();
                    break;

                case RETURN_DATE:
                    if (!TextUtils.isEmpty((String) msg.obj)) {
                        editText_userBirth.setText((String) msg.obj);
                    }

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();


    }

    private void initViews() {

        RadioGroup radiogroup_register_roleName = (RadioGroup) findViewById(R.id.radiogroup_register_roleName);
        RadioGroup radiogroup_register_userSex = (RadioGroup) findViewById(R.id.radiogroup_register_userSex);

        int roleName_checkedRadioButtonId = radiogroup_register_roleName.getCheckedRadioButtonId();
        int sex_checkedRadioButtonId = radiogroup_register_userSex.getCheckedRadioButtonId();


        roleName_checkedButton = (RadioButton) findViewById(roleName_checkedRadioButtonId);
        sex_checkedButton = (RadioButton) findViewById(sex_checkedRadioButtonId);

        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_userCode = (EditText) findViewById(R.id.editText_userCode);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_userBirth = (TextView) findViewById(R.id.editText_userBirth);
        editText_userFullName = (EditText) findViewById(R.id.editText_userFullName);
        editText_userEmail = (EditText) findViewById(R.id.editText_userEmail);
        editText_userPhoneNum = (EditText) findViewById(R.id.editText_userPhoneNum);
        register_verification_code = (ImageView) findViewById(R.id.register_Verification_Code);
        editText_register_verification_code = (EditText) findViewById(R.id.editText_register_Verification_Code);


        //验证用户码是否存在
        editText_userCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(editText_userCode.getText().toString())) {
                        handler.sendEmptyMessage(EMPTY_USERCODE);
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                // super.run();
                                try {
                                    URL url = new URL(HttpUtils.GLOBAL_ADDR + "ValidateUser/validateUsercode");

                                    String param = new String();


                                    param = "usercode=" + editText_userCode.getText().toString();

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

                                        JSONObject jsonObject = new JSONObject(result);

                                        String code = jsonObject.getString("code");

                                        String msg = jsonObject.getString("msg");

                                        String data = jsonObject.getString("data");

                                        Log.i("result=", result + "");

                                        if (!code.equals("1004"))

                                            handler.sendEmptyMessage(USERCODE_ALREADY_EXIST);
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }.start();
                    }
                }

            }
        });


        //验证用户名是否存在


        editText_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                {
                    if (TextUtils.isEmpty(editText_name.getText().toString())) {
                        handler.sendEmptyMessage(EMPTY_USERNAME);
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                // super.run();
                                try {
                                    URL url = new URL(HttpUtils.GLOBAL_ADDR + "ValidateUser/validateUsername");

                                    String param = new String();


                                    param = "username=" + editText_name.getText().toString();

                                    byte[] paramBytes = param.getBytes();

                                    HttpURLConnection urlConnection = HttpUtils.send(HttpUtils.HTTP_POST, url, paramBytes);

                                    OutputStream os = urlConnection.getOutputStream();

                                    os.write(paramBytes);

                                    int responseCode = urlConnection.getResponseCode();

                                    Log.i("response_code", "" + responseCode);
                                    //200成功 302重定向 404没找到 5xx内部错误
                                    if (responseCode == 200) {

                                        InputStream inputStream = urlConnection.getInputStream();


                                        String result = StreamTools.readInputStream(inputStream);

                                        JSONObject jsonObject = new JSONObject(result);

                                        String code = jsonObject.getString("code");

                                        String msg = jsonObject.getString("msg");

                                        String data = jsonObject.getString("data");

                                        Log.i("response_result=", result + "");

                                        if (!code.equals("1003"))

                                            handler.sendEmptyMessage(USERNAME_ALREADY_EXIST);
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }.start();
                    }
                }
            }
        });


    }

    public void onClick(View V) {
        switch (V.getId()) {
            case R.id.editText_userBirth:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH) + 1;
                int day = calendar.get(calendar.DAY_OF_MONTH);
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Message m = new Message();
                        m.what = RETURN_DATE;
                        m.obj = "" + year + "-" + monthOfYear + "-" + dayOfMonth;
                        handler.sendMessage(m);
                    }
                }, year, month, day).show();
                break;
            case R.id.register_button:
                new Thread() {
                    @Override
                    public void run() {

                        try {
                            URL url = new URL(HttpUtils.GLOBAL_ADDR + "signup");
                            String param = new String();
                            param = " userrole=" + URLEncoder.encode(roleName_checkedButton.getText().toString()) +
                                    "&usercode=" + editText_userCode.getText().toString() +
                                    "&username=" + editText_name.getText().toString() +
                                    "&usersex=" + URLEncoder.encode(sex_checkedButton.getText().toString()) +
                                    "&userpassword=" + editText_password.getText().toString() +
                                    "&userbirth=" + editText_userBirth.getText().toString() +
                                    "&userfullname=" + editText_userFullName.getText().toString() +
                                    "&useremail=" + editText_userEmail.getText().toString() +
                                    "&userphonenum=" + editText_userPhoneNum.getText().toString()+
                                    "&userregisterdate="+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());


                            byte[] bytes = param.getBytes();

                            HttpURLConnection urlConnection = HttpUtils.send(HttpUtils.HTTP_POST, url, bytes);

                            OutputStream outputStream = urlConnection.getOutputStream();

                            outputStream.write(bytes);

                            int responseCode = urlConnection.getResponseCode();
                            Log.i("result", "" + param);
                            Log.i("Code", "" + responseCode);
                            if (responseCode == 200) {


                                InputStream inputStream = urlConnection.getInputStream();
                                String result = StreamTools.readInputStream(inputStream);


                                Log.i("result", "" + result);

                                JSONObject jsonObject = new JSONObject(result);

                                String code = jsonObject.getString("code");

                                String msg = jsonObject.getString("msg");

                                String data = jsonObject.getString("data");

                                if (code.equals("2003")) {
                                    handler.sendEmptyMessage(REGISTER_SUCCESS);
                                } else {
                                    handler.sendEmptyMessage(REGISTER_FAIL);
                                }


                            } else {
                                handler.sendEmptyMessage(CONNECT_ERROR);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
                break;
        }

    }
}
