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
import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.utils.AlertUtil;
import com.zsorg.neteasecloudmusic.utils.FileUtil;
import com.zsorg.neteasecloudmusic.views.viewholders.SingerHolder;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        final MusicBean bean = mList.get(position);
        final Context context = holder.tvTitle.getContext();
        ImageCacheManager2.getInstance(context).displayImage(holder.iv,bean.getPath());
        String singer = bean.getSinger() == null ? context.getString(R.string.unknown) : bean.getSinger();
        holder.setTitle(singer);
        holder.tvTitle.setText(singer);
        holder.tvContent.setText(context.getString(R.string.songs_count, String.valueOf(bean.getDuration())));

        if (null != context) {
            holder.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int menuPosition) {
                    final DiskMusicDao diskMusicDao = new DiskMusicDao(context);
                    final ArrayList<MusicBean> list = (ArrayList<MusicBean>) diskMusicDao.querySingerMusicBeanList(bean.getSinger());
                    if (menuPosition == 0) {
                        MusicPlayerService.startActionSet(context, list, 0);
                        MusicPlayerService.startActionPlay(context, true);
                    } else {
                        AlertUtil.showDeleteDialog(context, new OnDeleteListener() {
                            @Override
                            public void onDelete(boolean isDeleteOnDisk) {
                                diskMusicDao.deleteSingerMusicList(bean.getSinger());
                                if (isDeleteOnDisk) {
                                    FileUtil.deleteFileOnDisk(list);
                                }
                            }
                        });
                    }
                }
            });
        }

    }

    @Override
    public SingerHolder onCreateHolder(ViewGroup parent, int viewType) {
        SingerHolder holder = new SingerHolder(mInflater.inflate(R.layout.singer_list_item, parent, false));
        holder.setMenuList(GroupSongMenuModel.getInstance(parent.getContext()).getMenuList());
        return holder;
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
