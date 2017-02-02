package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.views.viewholders.SingerHolder;

import java.util.ArrayList;
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
        ImageCacheManager2.getInstance(context).displayImage(holder.iv,bean.getPath());
        String singer = bean.getSinger() == null ? context.getString(R.string.unknown) : bean.getSinger();
        holder.setTitle(singer);
        holder.tvTitle.setText(singer);
        holder.tvContent.setText(context.getString(R.string.songs_count, String.valueOf(bean.getDuration())));

    }

    @Override
    public SingerHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new SingerHolder(mInflater.inflate(R.layout.singer_list_item, parent, false));
    }


    @Override
    public void setDatas(List list) {
        mList = list;
    }

    @Override
    public MusicBean getDataAtPosition(int position) {
        return mList.get(position);
    }

    @Override
    public int getDataCount() {
        return mList!=null?mList.size():0;
    }
}
