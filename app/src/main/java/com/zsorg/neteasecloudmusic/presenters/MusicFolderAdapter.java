package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.OnDeleteListener;
import com.zsorg.neteasecloudmusic.OnMenuItemClickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.GroupSongMenuModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.utils.AlertUtil;
import com.zsorg.neteasecloudmusic.utils.FileUtil;
import com.zsorg.neteasecloudmusic.views.viewholders.FolderHolder;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by piyel_000 on 2017/1/6.
 */

public class MusicFolderAdapter extends BaseAdapter<FolderHolder> {

    private List<MusicBean> mList;

    public MusicFolderAdapter(LayoutInflater layoutInflater) {
        super(layoutInflater);
    }

    @Override
    public void onBindHolder(FolderHolder holder, final int position) {

        final MusicBean bean = mList.get(position);

        final Context context = holder.tvFolderName.getContext();

        String unknown = "未知";
        String count = "首 ";
        if (null != context) {
            unknown = context.getString(R.string.unknown);
            count = context.getString(R.string.songs_count, String.valueOf(bean.getDuration()));

            holder.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int menuPosition) {
                    final DiskMusicDao diskMusicDao = new DiskMusicDao(context);
                    final ArrayList<MusicBean> list = (ArrayList<MusicBean>) diskMusicDao.queryFolderMusicBeanList(bean.getSinger());
                    if (menuPosition == 0) {
                        MusicPlayerService.startActionSet(context, list, 0);
                        MusicPlayerService.startActionPlay(context, true);
                    } else {
                        AlertUtil.showDeleteDialog(context, new OnDeleteListener() {
                            @Override
                            public void onDelete(boolean isDeleteOnDisk) {
                                diskMusicDao.deleteFolderMusicList(bean.getSinger());
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
            count = bean.getDuration() + count+bean.getSinger();
        }

        String path = bean.getAlbum() == null ? unknown : bean.getAlbum();
        String title = bean.getName() == null ? unknown : bean.getName();

        holder.setTitle(title);
        holder.tvFolderName.setText(title);
        holder.tvCount.setText(count);
        holder.tvFolderPath.setText(path);
    }


    @Override
    public void setDatas(List list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public MusicBean getDataAtPosition(int position) {
        return mList.get(position);
    }

    @Override
    public FolderHolder onCreateHolder(ViewGroup parent, int viewType) {
        FolderHolder holder = new FolderHolder(mInflater.inflate(R.layout.folder_list_item, parent, false));
        holder.setMenuList(GroupSongMenuModel.getInstance(parent.getContext()).getMenuList());
        return holder;
    }

    @Override
    public int getDataCount() {
        return mList!=null?mList.size():0;
    }
}
