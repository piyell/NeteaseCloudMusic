package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.views.viewholders.AlbumHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piyel_000 on 2017/1/6.
 */

public class MusicAlbumAdapter extends BaseAdapter<AlbumHolder> {

    private List<MusicBean> mList;

    public MusicAlbumAdapter(LayoutInflater layoutInflater) {
        super(layoutInflater);
    }

    @Override
    public void onBindHolder(AlbumHolder holder, int position) {

        MusicBean bean = mList.get(position);

        Context context = holder.tvSinger.getContext();

        String unknown = "未知";
        String subTitle = "首 ";

        if (null != context) {
            ImageCacheManager2.getInstance(context).displayImage(holder.ivAlbum,bean.getPath());
            unknown = context.getString(R.string.unknown);
            subTitle = context.getString(R.string.songs_count, String.valueOf(bean.getDuration()))+(bean.getSinger()==null?unknown:bean.getSinger());
        } else {
            subTitle = bean.getDuration() + subTitle+bean.getSinger();
        }

        String title = bean.getAlbum() == null ? unknown : bean.getAlbum();



        holder.setTitle(title);
        holder.tvSinger.setText(title);
        holder.tvCount.setText(subTitle);
    }

    @Override
    public void setDatas(List<MusicBean> list) {
        mList = list;
    }

    @Override
    public MusicBean getDataAtPosition(int position) {
        return mList.get(position);
    }

    @Override
    public AlbumHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new AlbumHolder(mInflater.inflate(R.layout.album_list_item, parent, false));
    }

    @Override
    public int getDataCount() {
        return mList!=null?mList.size():0;
    }
}
