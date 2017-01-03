package com.zsorg.neteasecloudmusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by piyel_000 on 2017/1/2.
 */

class MainAdapter extends FragmentPagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MusicFragment.newInstance(" "," ");
    }

    @Override
    public int getCount() {
        return 2;
    }
}
