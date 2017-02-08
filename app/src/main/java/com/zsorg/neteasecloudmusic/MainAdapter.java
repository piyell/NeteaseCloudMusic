package com.zsorg.neteasecloudmusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by piyel_000 on 2017/1/2.
 */

class MainAdapter extends FragmentPagerAdapter implements IFragmentAdapter {


    private final ArrayList<Fragment> mList;

    public MainAdapter(FragmentManager fm) {
        super(fm);
        mList = new ArrayList<>();
        mList.add(MusicFragment.newInstance());
        mList.add(PlayListFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public void getFragmentAtPosition(int position) {
        getItem(position);
    }
}
