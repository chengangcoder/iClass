package cn.edu.xust.www.iclass.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xust.www.iclass.R;
import cn.edu.xust.www.iclass.fragment.class_Fragment;
import cn.edu.xust.www.iclass.fragment.chat_Fragment;
import cn.edu.xust.www.iclass.fragment.user_Fragment;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragments;
    private ViewPager list_viewpager;
    private RadioButton radioButton_chat;
    private RadioButton radioButton_user;
    private RadioButton radioButton_class;

    // private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

        fragments = new ArrayList<>();

        fragments.add(new class_Fragment());
        fragments.add(new chat_Fragment());
        fragments.add(new user_Fragment());



        list_viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        list_viewpager.addOnPageChangeListener(new MyPageChangeListener());


      /*  tabs = (TabLayout) findViewById(R.id.sliding_tabs);


        // 省略了tab和viepager相互加监听，非常方便
        tabs.setupWithViewPager(list_viewpager);
        // 设置tab模式
        tabs.setTabMode(TabLayout.MODE_FIXED);*/


    }

    private void initViews() {
        radioButton_chat = (RadioButton) findViewById(R.id.radioButton_chat);
        radioButton_user = (RadioButton) findViewById(R.id.radioButton_user);
        radioButton_class = (RadioButton) findViewById(R.id.radioButton_class);
        list_viewpager = (ViewPager) findViewById(R.id.list_viewpager);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton_chat:
                list_viewpager.setCurrentItem(1);
                break;
            case R.id.radioButton_class:
                list_viewpager.setCurrentItem(0);
                break;

            case R.id.radioButton_user:
                list_viewpager.setCurrentItem(2);
                break;

        }

    }


    class MyPagerAdapter extends FragmentPagerAdapter {


        String[] titles = {"title_1", "title_2"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            //return super.getPageTitle(position);
            return titles[position];

        }
    }


    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            switch (position) {
                case 0:
                    radioButton_class.setChecked(true);
                    break;
                case 1:
                    radioButton_chat.setChecked(true);
                    break;
                case 2:
                    radioButton_user.setChecked(true);
                    break;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}

