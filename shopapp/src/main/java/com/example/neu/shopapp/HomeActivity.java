//package com.example.neu.shopapp;
//
//
//import android.content.Intent;
//import android.support.annotation.IdRes;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import android.support.v4.app.Fragment;
//
//import com.android.volley.Response;
//import com.example.neu.baseinfo.Connectinfo;
//import com.example.neu.util.MyApplication;
//import com.example.neu.util.MyVolleyRequest;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomeActivity extends AppCompatActivity implements Response.Listener<JSONObject>, RadioGroup.OnCheckedChangeListener {
////             ViewPager pager=null;
//////            new ArrayList<Fragment> fragments;
////            List<Fragment> fragments = new ArrayList<Fragment>();
////             PagerAdapter adapter;
//
//    private ViewPager viewPager;
//    private HomeFragment homeFragment;
//    private ShopCarFragment shopcarFragment;
//
//    private CateFragment cateFragment;
//    private MeFragment meFragment;
//    private RadioGroup mainRadioGroup;
//    private RadioButton radioHome,radioShopcar,radioCate,radioSearch,radioMe;
//    private ArrayList<Fragment> fragmentList=new ArrayList<Fragment>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//
//        //获取所有的控件
//        viewPager= (ViewPager) findViewById(R.id.viewpagers);
//        mainRadioGroup= (RadioGroup) findViewById(R.id.radioGroup);
//        radioHome= (RadioButton) findViewById(R.id.radioButton6);
//        radioShopcar= (RadioButton) findViewById(R.id.radioButton2);
//        radioCate= (RadioButton) findViewById(R.id.radioButton5);
//       // radioSearch= (RadioButton) findViewById(R.id.radio_search);
//        radioMe= (RadioButton) findViewById(R.id.radioButton);
//        //设置单选按钮的事件处理,注册监听器
//        mainRadioGroup.setOnCheckedChangeListener(this);
//
//        Fragment hf = new HomeFragment() ;
//        Fragment cf = new CateFragment();
//        Fragment sf = new ShopCarFragment();
//        Fragment mf = new MeFragment();
//
//        //将各Fragment加入数组中
//        fragmentList.add(hf); fragmentList.add(cf);
//        fragmentList.add(sf); fragmentList.add(mf);
//        //设置ViewPager的适配器
//        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
//        //当前为第一个页面
//        viewPager.setCurrentItem(0);
//        initViewpager();
//
//
////        //构造适配器  
////    List<Fragment> fragments=new ArrayList<Fragment>();
////        fragments.add(new HomeFragment());
////        fragments.add(new CateFragment());
////        fragments.add(new MeFragment());
////                fragments.add(new ShopCarFragment())
////        FragAdapter adapter=new FragAdapter(getSupportFragmentManager(),fragments);
////
////        //设定适配器  
////        ViewPager vp=(ViewPager)findViewById(R.id.viewpager);
////        vp.setAdapter(adapter);
//
////        pager=(ViewPager)findViewById(R.id.viewpagers);
////
////        initFragment();
////        initPager();
//
//
//        //构造适配器
////        List<Fragment> fragments=new ArrayList<Fragment>();
////        fragments.add(new HomeFragment());
////        fragments.add(new CateFragment());
////        fragments.add(new ShopCarFragment());
////        fragments.add(new MeFragment());
////        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
////
////        //设定适配器
////        ViewPager vp = (ViewPager)findViewById(R.id.viewpager);
////        vp.setAdapter(adapter);
//
//
//
//
//
//    }
//
//    private void initViewpager() {
//
//        fragmentList=new ArrayList<Fragment>();
//        homeFragment=new HomeFragment();
//        shopcarFragment=new ShopCarFragment();
//        //searchFragment=new SearchFragment();
//        cateFragment=new CateFragment();
//        meFragment=new MeFragment();
//
//        fragmentList.add(homeFragment);
//        fragmentList.add(shopcarFragment);
//        fragmentList.add(cateFragment);
//        //fragmentList.add(searchFragment);
//        fragmentList.add(meFragment);
//
//        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
//        //滑动页面时，单元按钮也跟着变化
//        viewPager.addOnPageChangeListener(new MyListener());
//    }
//
////    private void initFragment(){
////        fragments.add(new HomeFragment());
////        fragments.add(new CateFragment());
////        fragments.add(new ShopCarFragment());
////        fragments.add(new MeFragment());
////    }
////
////    private void initPager(){
////        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
////        pager.setAdapter(adapter);
////    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_home, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_logout:
//                Toast.makeText(this, "退出登录", Toast.LENGTH_SHORT).show();
//                MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.logouturl,
//                        HomeActivity.this);
//                MyApplication.getInstance().addToRequestQueue(mr,"reg");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public void onResponse(JSONObject jsonObject) {
//        if (jsonObject!=null)
//        {
//            if (jsonObject.optString("logoutFlag").equals("1"))
//            {
//                Intent intent=new Intent(this, MainActivity.class);
//                startActivity(intent);
//            }else
//            {
//                Toast.makeText(this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup radioGroup,int checkId) {
//        int current=0;
//        switch (checkId) {
//            case R.id.radioButton6:
//                current=0;
//                break;
//            case R.id.radioButton2:
//                current=1;
//                break;
//            case R.id.radioButton5:
//                current=2;
//                break;
////            case  R.id.radio_search:
////                current=3;
////                break;
//            case R.id.radioButton:
//                current=4;
//                break;
//        }
//        if (viewPager.getCurrentItem()!=current)
//            viewPager.setCurrentItem(current);
//
//    }
//
//    private class MyListener implements ViewPager.OnPageChangeListener {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            int current=viewPager.getCurrentItem();
//            switch(current){
//                case 0:
//                    mainRadioGroup.check(R.id.radioButton6);
//                    break;
//                case 1:
//                    mainRadioGroup.check(R.id.radioButton2);
//                    break;
//                case 2:
//                    mainRadioGroup.check(R.id.radioButton5);
//                    break;
////                case 3:
////                    mainRadioGroup.check(R.id.radio_search);
////                    break;
//                case 4:
//                    mainRadioGroup.check(R.id.radioButton);
//                    break;
//            }
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    }
//
//    private class MyAdapter extends FragmentPagerAdapter {
//        public MyAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//    }
//}
//

package com.example.neu.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.shopapp.HomeFragment;
import com.example.neu.shopapp.MeFragment;
import com.example.neu.shopapp.ShopCarFragment;
import com.example.neu.shopapp.CateFragment;
import com.example.neu.shopapp.MainActivity;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyVolleyRequest;
import com.example.neu.util.PrefStore;

import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements Response.Listener<JSONObject>, RadioGroup.OnCheckedChangeListener {
    private ViewPager viewPager;
    private HomeFragment homeFragment;
    private ShopCarFragment shopcarFragment;
    private CateFragment cateFragment;
    private MeFragment meFragment;
    private RadioGroup radioGroup;
    private RadioButton radioHome,radioShopcar,radioSort,radioMe;
    private ArrayList<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //获取所有的控件
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        radioHome= (RadioButton) findViewById(R.id.radioButton6);
        radioShopcar= (RadioButton) findViewById(R.id.radioButton2);
        radioSort= (RadioButton) findViewById(R.id.radioButton5);
        radioMe= (RadioButton) findViewById(R.id.radioButton);
        //设置单选按钮的事件处理,注册监听器
        radioGroup.setOnCheckedChangeListener(this);
        //初始化Viewpager中的页面及事件处理
        initViewpager();
        //init(this);
        //initDB();

    }
    public void initViewpager(){
        fragmentList=new ArrayList<Fragment>();
        homeFragment=new HomeFragment();
        shopcarFragment=new ShopCarFragment();
        cateFragment=new CateFragment();
        meFragment=new MeFragment();

        fragmentList.add(homeFragment);
        fragmentList.add(cateFragment);
        fragmentList.add(shopcarFragment);
        fragmentList.add(meFragment);

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        //滑动页面时，单元按钮也跟着变化
        viewPager.addOnPageChangeListener(new MyListener());

    }
    class  MyListener implements  ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int current=viewPager.getCurrentItem();
            switch(current){
                case 0:
                    radioGroup.check(R.id.radioButton6);
                    break;
                case 1:
                    radioGroup.check(R.id.radioButton5);
                    break;
                case 2:
                    radioGroup.check(R.id.radioButton2);
                    break;
                case 3:
                    radioGroup.check(R.id.radioButton);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //定义适配器类
    class MyAdapter extends FragmentPagerAdapter {
        MyAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
    //当单选按钮变化时切换的相应页面
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int current=0;
        switch (checkedId) {
            case R.id.radioButton6:
                current=0;
                break;
            case R.id.radioButton5:
                current=1;
                break;
            case R.id.radioButton2:
                current=2;
                break;
            case R.id.radioButton:
                current=3;
                break;
        }
        if (viewPager.getCurrentItem()!=current)
            viewPager.setCurrentItem(current);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(HomeActivity.this,SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                this.startActivity(intent);

                break;

            case R.id.menu_logout:
                Toast.makeText(this, "退出登录", Toast.LENGTH_SHORT).show();
                MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.logouturl,
                        HomeActivity.this);
                MyApplication.getInstance().addToRequestQueue(mr,"reg");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject!=null)
        {
            if (jsonObject.optString("logoutFlag").equals("1"))
            {
                PrefStore pref=PrefStore.getInstance();
                pref.clearPref();
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
            }else
            {
                Toast.makeText(this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        }

    }
}

