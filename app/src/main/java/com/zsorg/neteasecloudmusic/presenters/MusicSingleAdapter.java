package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.OnMenuItemClickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.SingleSongModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.views.viewholders.PlayAllHolder;
import com.zsorg.neteasecloudmusic.views.viewholders.SongListItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class MusicSingleAdapter extends BaseAdapter {
    private final int TYPE_HEAD = 1;
    private final int TYPE_CONTENT = 0;
    private final PlayerManager mPlayer;
    private ArrayList<MusicBean> mList;
    private String title;

    public MusicSingleAdapter(@NonNull LayoutInflater inflater) {
        super(inflater);
        mPlayer = PlayerManager.getInstance(inflater.getContext());
    }

    @Override
    public int getItemType(int position) {
        return position==0?TYPE_HEAD:TYPE_CONTENT;
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            SongListItemHolder holder = new SongListItemHolder(mInflater.inflate(R.layout.song_list_item, parent, false));
            holder.setMenuList(SingleSongModel.getInstance(parent.getContext()).getMenuList());
            return holder;
        } else {
            return new PlayAllHolder(mInflater.inflate(R.layout.play_all_layout, parent, false));
        }
    }

    @Override
    public void onBindHolder(final BaseHolder holder, final int position) {

        if (position != 0) {
            final MusicBean bean = mList.get(position - 1);

            final SongListItemHolder itemHolder = (SongListItemHolder) holder;
            Context context = itemHolder.tvTitle.getContext();
            String unknown = context.getString(R.string.unknown);
            title = bean.getName() == null ? unknown : bean.getName();
            itemHolder.setTitle(title);
            itemHolder.setOnMenuItemClickListener(new OnMenuItemClickListener() {
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
                                MusicPlayerService.startActionSet(itemHolder.tvTitle.getContext(), beanList, 0);
//                                mPlayer.setCurrentPlaylist(beanList);
//                                mPlayer.setCurrentPosition(0);
//                                mPlayer.playMusic(itemHolder.tvTitle.getContext());
                                MusicPlayerService.startActionPlay(itemHolder.tvTitle.getContext(),true);
                            }
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                    }
                }
            });
            itemHolder.tvTitle.setText(title);
            String singer = bean.getSinger() == null ? unknown : bean.getSinger();
            String album = bean.getAlbum() == null ? unknown : bean.getAlbum();
            itemHolder.tvContent.setText(singer + " - " + album);


        } else {
            PlayAllHolder itemHolder = (PlayAllHolder) holder;
            Context context = itemHolder.tvCount.getContext();
            itemHolder.tvCount.setText(context.getString(R.string.songs_in_total, String.valueOf(mList.size())));
        }
    }

    @Override
    public void setDatas(List list) {
        this.mList = (ArrayList<MusicBean>) list;
        notifyDataSetChanged();
    }

    public MusicBean getDataAtPosition(int position) {
        return mList.get(position);
    }

    @Override
    public ArrayList<MusicBean> getDataList() {
        return mList;
    }

    @Override
    public int getDataCount() {
        return mList!=null && mList.size()>0?mList.size()+1:0;
    }

}
