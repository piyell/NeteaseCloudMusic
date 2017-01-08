package com.zsorg.neteasecloudmusic.presenters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;

/**
 * Created by piyel_000 on 2017/1/6.
 */

public class MusicSingerAdapter extends BaseAdapter {
    public MusicSingerAdapter(LayoutInflater layoutInflater) {
        super(layoutInflater);
    }

    @Override
    public void onBindHolder(BaseHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingerHolder(mInflater.inflate(R.layout.singer_list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
