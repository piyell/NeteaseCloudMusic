package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.utils.MusicUtil;

import java.util.List;

/**
 * Created by piyel_000 on 2017/1/6.
 */

public class MusicFolderAdapter extends BaseAdapter<FolderHolder> {

    private List<MusicBean> mList;

    public MusicFolderAdapter(LayoutInflater layoutInflater) {
        super(layoutInflater);
    }

    @Override
    public void onBindHolder(FolderHolder holder, int position) {

        MusicBean bean = mList.get(position);

        Context context = holder.tvFolderName.getContext();

        String unknown = "未知";
        String count = "首 ";
        if (null != context) {
            unknown = context.getString(R.string.unknown);
            count = context.getString(R.string.songs_count, String.valueOf(bean.getDuration()));
        } else {
            count = bean.getDuration() + count+bean.getSinger();
        }

        String path = bean.getAlbum() == null ? unknown : bean.getAlbum();
        String title = bean.getName() == null ? unknown : bean.getName();

        holder.tvFolderName.setText(title);
        holder.tvCount.setText(count);
        holder.tvFolderPath.setText(path);
    }


    @Override
    public void setDatas(List list) {
        mList = list;
    }

    @Override
    public FolderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FolderHolder(mInflater.inflate(R.layout.folder_list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }
}
