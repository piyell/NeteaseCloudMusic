package com.zsorg.neteasecloudmusic.presenters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.R;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class SearchAdapter extends BaseAdapter<SongListItemHolder> {

    public SearchAdapter(@NonNull LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public SongListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongListItemHolder(mInflater.inflate(R.layout.song_list_item,parent,false));
    }

    @Override
    public void onBindHolder(SongListItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
