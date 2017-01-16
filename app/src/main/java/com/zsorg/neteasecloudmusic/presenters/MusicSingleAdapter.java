package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.List;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class MusicSingleAdapter extends BaseAdapter {
    private final int TYPE_HEAD = 1;
    private final int TYPE_CONTENT = 0;
    private List<MusicBean> mList;

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

        if (position != 0) {
            MusicBean bean = mList.get(position - 1);

            SongListItemHolder itemHolder = (SongListItemHolder) holder;
            Context context = itemHolder.tvTitle.getContext();
            String unknown = context.getString(R.string.unknown);
            itemHolder.tvTitle.setText(bean.getName() == null ? unknown : bean.getName());
            String singer = bean.getSinger() == null ? unknown : bean.getSinger();
            String album = bean.getAlbum() == null ? unknown : bean.getAlbum();
            itemHolder.tvContent.setText(singer + " - " + album);

        } else {
            PlayAllHolder itemHolder = (PlayAllHolder) holder;
            Context context = itemHolder.tvCount.getContext();
            itemHolder.tvCount.setText(context.getString(R.string.songs_in_total, String.valueOf(mList.size())));
        }
    }

    @Override
    public void setDatas(List list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public MusicBean getDataAtPosition(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size()+1:0;
    }
}
