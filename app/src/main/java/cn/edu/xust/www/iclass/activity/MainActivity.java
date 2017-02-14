package cn.edu.xust.www.iclass.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xust.www.iclass.R;
import cn.edu.xust.www.iclass.fragment.chat_Fragment;
import cn.edu.xust.www.iclass.fragment.class_Fragment;
import cn.edu.xust.www.iclass.fragment.user_Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager list_viewpager;
    private RadioButton chat_radiobtn;
    private RadioButton class_radiobtn;
    private RadioButton user_radiobtn;
    private List<Fragment> fragments;
    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏actionbar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.external_layout);

        fragments = new ArrayList<>();

        fragments.add(new class_Fragment());
        fragments.add(new chat_Fragment());
        fragments.add(new user_Fragment());


        initView();

        //必须放在syncstate之前
        setSupportActionBar(toolbar);
        // 增加抽屉样式按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        drawerlayout.addDrawerListener(toggle);

        toggle.syncState();


        nav_view.setNavigationItemSelectedListener(this);


        list_viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));


        list_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        class_radiobtn.setChecked(true);
                        break;
                    case 1:
                        chat_radiobtn.setChecked(true);
                        break;
                    case 2:
                        user_radiobtn.setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initView() {
        list_viewpager = (ViewPager) findViewById(R.id.list_viewpager);
        chat_radiobtn = (RadioButton) findViewById(R.id.radioButton_chat);
        class_radiobtn = (RadioButton) findViewById(R.id.radioButton_class);
        user_radiobtn = (RadioButton) findViewById(R.id.radioButton_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        nav_view = (NavigationView) findViewById(R.id.nav_view);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_gallery:
                break;
            case R.id.nav_import:
                break;
            case R.id.nav_send:
                break;
            case R.id.nav_share:

                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_tool:
                break;
            case R.id.nav_view:
                break;
        }

        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {


        public MyFragmentPagerAdapter(FragmentManager fm) {
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
    }
}
