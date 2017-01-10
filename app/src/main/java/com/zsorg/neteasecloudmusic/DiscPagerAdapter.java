package com.zsorg.neteasecloudmusic;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by piyel_000 on 2017/1/8.
 */

class DiscPagerAdapter extends PagerAdapter {

    private final LayoutInflater mInflater;

    public DiscPagerAdapter(LayoutInflater inflater) {
        super();
        mInflater = inflater;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.disc_pager_item, container,false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
