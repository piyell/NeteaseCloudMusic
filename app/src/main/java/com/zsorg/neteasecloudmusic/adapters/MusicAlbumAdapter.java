package com.zsorg.neteasecloudmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.callbacks.OnDeleteListener;
import com.zsorg.neteasecloudmusic.callbacks.OnMenuItemClickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.GroupSongMenuModel;
import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.utils.AlertUtil;
import com.zsorg.neteasecloudmusic.utils.FileUtil;
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
    public void onBindHolder(AlbumHolder holder, final int position) {

        final MusicBean bean = mList.get(position);

        final Context context = holder.tvSinger.getContext();

        String unknown = "未知";
        String subTitle = "首 ";

        if (null != context) {
            ImageCacheManager2.getInstance(context).displayImage(holder.ivAlbum, bean.getPath());
            unknown = context.getString(R.string.unknown);
            subTitle = context.getString(R.string.songs_count, String.valueOf(bean.getDuration())) + (bean.getSinger() == null ? unknown : bean.getSinger());

            holder.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int menuPosition) {
                    final DiskMusicDao diskMusicDao = new DiskMusicDao(context);
                    final ArrayList<MusicBean> list = (ArrayList<MusicBean>) diskMusicDao.queryAlbumMusicBeanList(bean.getAlbum());
                    if (menuPosition == 0) {
                        MusicPlayerService.startActionSet(context, list, 0);
                        MusicPlayerService.startActionPlay(context, true);
                    } else {
                        AlertUtil.showDeleteDialog(context, new OnDeleteListener() {
                            @Override
                            public void onDelete(boolean isDeleteOnDisk) {
                                diskMusicDao.deleteAlbumMusicList(bean.getAlbum());
                                if (isDeleteOnDisk) {
                                    FileUtil.deleteFileOnDisk(list);
                                }
                                mList.remove(position);
                                notifyItemRemoved(position);
                            }
                        });
                    }
                }
            });
        } else {
            subTitle = bean.getDuration() + subTitle + bean.getSinger();
        }


        String title = bean.getAlbum() == null ? unknown : bean.getAlbum();


        holder.setTitle(title);
        holder.tvSinger.setText(title);
        holder.tvCount.setText(subTitle);
    }

    @Override
    public void setDatas(List<MusicBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public MusicBean getDataAtPosition(int position) {
        return mList.get(position);
    }

    @Override
    public AlbumHolder onCreateHolder(ViewGroup parent, int viewType) {
        AlbumHolder holder = new AlbumHolder(mInflater.inflate(R.layout.album_list_item, parent, false));
        holder.setMenuList(GroupSongMenuModel.getInstance(parent.getContext()).getMenuList());
        return holder;
    }

    @Override
    public int getDataCount() {
        return mList != null ? mList.size() : 0;
    }
}
