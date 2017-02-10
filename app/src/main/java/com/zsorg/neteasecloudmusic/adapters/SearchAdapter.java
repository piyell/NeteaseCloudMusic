package com.zsorg.neteasecloudmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.callbacks.OnDeleteListener;
import com.zsorg.neteasecloudmusic.callbacks.OnMenuItemClickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.SingleSongMenuModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.utils.AlertUtil;
import com.zsorg.neteasecloudmusic.views.viewholders.SongListItemHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class SearchAdapter extends BaseAdapter<SongListItemHolder> {

    private final String unknown;
    private final PlayerManager mPlayer;
    private List<MusicBean> mList;

    public SearchAdapter(@NonNull LayoutInflater inflater) {
        super(inflater);
        unknown = inflater.getContext().getString(R.string.unknown);
        mPlayer = PlayerManager.getInstance(inflater.getContext());
    }

    @Override
    public SongListItemHolder onCreateHolder(ViewGroup parent, int viewType) {
        SongListItemHolder holder = new SongListItemHolder(mInflater.inflate(R.layout.song_list_item, parent, false));
        holder.setMenuList(SingleSongMenuModel.getInstance(parent.getContext()).getMenuList());
        return holder;
    }

    @Override
    public void onBindHolder(SongListItemHolder holder, final int position) {
        final MusicBean bean = mList.get(position);
        String name = bean.getName()==null?unknown:bean.getName();
        String singer = bean.getSinger()==null?unknown:bean.getSinger();
        String album = bean.getAlbum()==null?unknown:bean.getAlbum();
        holder.tvTitle.setText(name);
        holder.tvContent.setText(singer+" - "+album);

        holder.setTitle(name);

        final Context context = holder.tvTitle.getContext();
        if (null == context) {
            return;
        }
        holder.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int menuPosition) {
                switch (menuPosition) {
                    case 0:
                        List<MusicBean> playlist = mPlayer.getPlaylist();
                        if (playlist != null && playlist.size()>0) {
                            int index = playlist.indexOf(bean);
                            if (index >= 0) {
                                playlist.remove(index);
                            }
                            int currentPosition = mPlayer.getCurrentPosition();
                            currentPosition = (currentPosition >= playlist.size() - 1 ? playlist.size() : currentPosition+1);
                            playlist.add(currentPosition, bean);
                        } else {
                            ArrayList<MusicBean> beanList = new ArrayList<>();
                            beanList.add(bean);
                            MusicPlayerService.startActionSet(context, beanList, 0);
//                                mPlayer.setCurrentPlaylist(beanList);
//                                mPlayer.setCurrentPosition(0);
//                                mPlayer.playMusic(itemHolder.tvTitle.getContext());
                            MusicPlayerService.startActionPlay(context,true);
                        }
                        break;
                    case 1:
                        //收藏

                        AlertUtil.showCollectionDialog(context,bean);
                        break;
                    case 2:
                        //分享
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("audio/x-mpeg");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(bean.getPath())));
                        context.startActivity(Intent.createChooser(intent,context.getString(R.string.share_to)));
                        break;
                    case 3:
//                            删除
                        AlertUtil.showDeleteDialog(context, new OnDeleteListener() {
                            @Override
                            public void onDelete(boolean isDeleteOnDisk) {
                                new DiskMusicDao(context).deleteSingleSong(bean.getPath());
                                if (isDeleteOnDisk) {
                                    new File(bean.getPath()).delete();
                                }
                                mList.remove(position);
                                notifyItemRemoved(position);
                            }
                        });

                        break;
                }
            }
        });
    }

    @Override
    public void setDatas(List list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getDataCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public MusicBean getDataAtPosition(int position) {
        return mList!=null?mList.get(position):null;
    }
}
