package com.example.neu.shopapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by hasee on 2017/9/20.
 */

class MyPagerAdapter extends FragmentPagerAdapter  {
    private List<Fragment> fragments = new ArrayList<Fragment>();
//    public MyPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
    public MyPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments){
        super(fragmentManager);
        this.fragments = fragments;
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
