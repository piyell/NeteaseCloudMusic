package com.zsorg.neteasecloudmusic;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.List;

/**
 * Created by piyel_000 on 2017/1/8.
 */

class DiscPagerAdapter extends PagerAdapter {

    private final LayoutInflater mInflater;
    private final List<MusicBean> playlist;

    public DiscPagerAdapter(LayoutInflater inflater) {
        super();
        mInflater = inflater;
        playlist = PlayerManager.getInstance(mInflater.getContext()).getPlaylist();
    }

    @Override
    public int getCount() {
        return playlist==null?0:playlist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.disc_pager_item, container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_album);
        if (position>=0 && position<playlist.size()) {
            ImageCacheManager2.getInstance(container.getContext()).displayImage(imageView,playlist.get(position).getPath());
        }
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
