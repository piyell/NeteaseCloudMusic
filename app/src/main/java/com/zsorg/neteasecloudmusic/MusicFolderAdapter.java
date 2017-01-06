package com.zsorg.neteasecloudmusic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by piyel_000 on 2017/1/6.
 */

class MusicFolderAdapter extends BaseAdapter {
    public MusicFolderAdapter(LayoutInflater layoutInflater) {
        super(layoutInflater);
    }

    @Override
    public void onBindHolder(BaseHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FolderHolder(mInflater.inflate(R.layout.folder_list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
