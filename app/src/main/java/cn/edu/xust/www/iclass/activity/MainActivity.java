package cn.edu.xust.www.iclass.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import cn.edu.xust.www.iclass.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();
        downVerificationCode();

    }

    // 下载验证码图片
    private void downVerificationCode() {
        new Thread() {


        }.start();

    }


    // 初始化控件
    private void initViews() {
        EditText name = (EditText) findViewById(R.id.editText_name);
        EditText password = (EditText) findViewById(R.id.editText_password);
        EditText editText_verification_Code = (EditText) findViewById(R.id.editText_Verification_Code);
        ImageView image_verification_Code = (ImageView) findViewById(R.id.Verification_Code);


    }

    // 点击按钮，提交登陆请求
    public void onClick(View v) {

    }

    // 点击验证码图片，实现刷新
    public void onFresh(View v) {

    }
}
