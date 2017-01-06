package com.zsorg.neteasecloudmusic;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by piyel_000 on 2017/1/5.
 */

class SearchAdapter extends BaseAdapter<SongListItemHolder> {

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
