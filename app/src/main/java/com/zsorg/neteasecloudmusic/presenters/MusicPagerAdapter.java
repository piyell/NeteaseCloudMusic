package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.telephony.SmsMessage;
import android.view.View;

import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.SubMusicFragment;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by piyel_000 on 2017/1/3.
 */

public class MusicPagerAdapter extends FragmentPagerAdapter {
    @BindArray(R.array.music)
    String[] mTabName;
    private int[] mFragmentType = {SubMusicFragment.TYPE_SINGLE, SubMusicFragment.TYPE_SINGER, SubMusicFragment.TYPE_ALBUM, SubMusicFragment.TYPE_FOLDER};

    public MusicPagerAdapter(View view, FragmentManager fm) {
        super(fm);
        ButterKnife.bind(this, view);
    }

    @Override
    public Fragment getItem(int position) {
        return SubMusicFragment.newInstance(mFragmentType[position]);
    }

    @Override
    public int getCount() {
        return mTabName.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabName[position];
    }
}
