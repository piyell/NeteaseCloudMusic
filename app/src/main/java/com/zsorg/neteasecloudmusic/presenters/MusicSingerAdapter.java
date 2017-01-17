package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by piyel_000 on 2017/1/6.
 */

public class MusicSingerAdapter extends BaseAdapter<SingerHolder> {

    private List<MusicBean> mList;

    public MusicSingerAdapter(LayoutInflater layoutInflater) {
        super(layoutInflater);
    }

    @Override
    public void onBindHolder(SingerHolder holder, int position) {
        MusicBean bean = mList.get(position);
        Context context = holder.tvTitle.getContext();
        String singer = bean.getSinger() == null ? context.getString(R.string.unknown) : bean.getSinger();
        holder.tvTitle.setText(singer);
        holder.tvContent.setText(context.getString(R.string.songs_count, String.valueOf(bean.getDuration())));

    }

    @Override
    public SingerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingerHolder(mInflater.inflate(R.layout.singer_list_item, parent, false));
    }


    @Override
    public void setDatas(List list) {
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }
}
