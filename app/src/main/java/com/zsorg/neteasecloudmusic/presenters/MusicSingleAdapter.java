package com.zsorg.neteasecloudmusic.presenters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class MusicSingleAdapter extends BaseAdapter {
    private final int TYPE_HEAD = 1;
    private final int TYPE_CONTENT = 0;

    public MusicSingleAdapter(@NonNull LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?TYPE_HEAD:TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new SongListItemHolder(mInflater.inflate(R.layout.song_list_item, parent, false));
        } else {
            return new PlayAllHolder(mInflater.inflate(R.layout.play_all_layout, parent, false));
        }
    }

    @Override
    public void onBindHolder(BaseHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
