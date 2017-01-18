package com.zsorg.neteasecloudmusic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.views.viewholders.PlaylistHolder;

import java.util.List;

public class PlaylistAdapter extends BaseAdapter<PlaylistHolder> {

    private List<MusicBean> mValues;

    public PlaylistAdapter(@NonNull LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public PlaylistHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new PlaylistHolder(mInflater.inflate(R.layout.play_list_item, parent, false));
    }

    @Override
    public void onBindHolder(PlaylistHolder holder, int position) {

        MusicBean bean = mValues.get(position);

        Context context = holder.tvTitle.getContext();

        String unknown = "未知";
        String count = "首 ";
        if (null != context) {
            unknown = context.getString(R.string.unknown);
            count = context.getString(R.string.songs_count,bean.getAlbum());
        } else {
            count = bean.getAlbum()+count;
        }

        String title = bean.getName() == null ? unknown : bean.getName();

        holder.tvTitle.setText(title);
        holder.tvContent.setText(count);
    }

    @Override
    public void setDatas(List<MusicBean> list) {

        mValues = list;
    }

    @Override
    public int getDataCount() {
        return mValues!=null?mValues.size():0;
    }

}
