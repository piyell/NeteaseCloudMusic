package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

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
        String count = "首 ";
        if (null != context) {
            unknown = context.getString(R.string.unknown);
            count = context.getString(R.string.songs_count, String.valueOf(bean.getDuration()))+bean.getSinger();
        } else {
            count = bean.getDuration() + count+bean.getSinger();
        }

        String title = bean.getAlbum() == null ? unknown : bean.getAlbum();



        holder.tvSinger.setText(title);
        holder.tvCount.setText(count);
    }

    @Override
    public void setDatas(List list) {
        mList = list;
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumHolder(mInflater.inflate(R.layout.album_list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }
}
