package com.zsorg.neteasecloudmusic.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.callbacks.OnDeleteListener;
import com.zsorg.neteasecloudmusic.callbacks.OnMenuItemClickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.GroupSongMenuModel;
import com.zsorg.neteasecloudmusic.models.PlaylistModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.utils.AlertUtil;
import com.zsorg.neteasecloudmusic.views.viewholders.PlaylistHolder;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends BaseAdapter<PlaylistHolder> {

    private ArrayList<MusicBean> mValues;
    private boolean isHideRightIcon;

    public PlaylistAdapter(@NonNull LayoutInflater inflater) {
        super(inflater);
    }

    public PlaylistAdapter(@NonNull LayoutInflater inflater,boolean hideRightIcon) {
        super(inflater);
        isHideRightIcon = hideRightIcon;
    }

    @Override
    public PlaylistHolder onCreateHolder(ViewGroup parent, int viewType) {
        PlaylistHolder holder = new PlaylistHolder(mInflater.inflate(R.layout.play_list_item, parent, false));
        holder.ivRight.setVisibility(isHideRightIcon? View.GONE:View.VISIBLE);
        holder.setMenuList(GroupSongMenuModel.getInstance(parent.getContext()).getMenuList());
        return holder;
    }

    @Override
    public void onBindHolder(final PlaylistHolder holder, final int position) {

        final MusicBean bean = mValues.get(position);

        final Context context = holder.tvTitle.getContext();

        String unknown = "未知";
        String count = "首 ";
        if (null != context) {
            unknown = context.getString(R.string.unknown);
            count = context.getString(R.string.songs_count,bean.getAlbum());

            holder.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int menuPosition) {
                    final PlaylistModel model = new PlaylistModel(context);

                    final ArrayList<MusicBean> list = (ArrayList<MusicBean>) model.loadPlaylist((int) bean.getDuration());
                    if (menuPosition == 0) {
                        MusicPlayerService.startActionSet(context, list, 0);
                        MusicPlayerService.startActionPlay(context, true);
                    } else {
                        AlertUtil.showDeletePlaylistDialog(context, new OnDeleteListener() {
                            @Override
                            public void onDelete(boolean isDeleteOnDisk) {
                                int playlistID = (int) bean.getDuration();
                                if (playlistID > 0) {
                                    model.deletePlaylist(playlistID);
                                    mValues.remove(position);
                                    notifyItemRemoved(position);
                                } else {
                                    Snackbar.make(holder.iv,R.string.cannot_delete_favorite,Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            count = bean.getAlbum()+count;
        }

        String title = bean.getName() == null ? unknown : bean.getName();

        holder.setTitle(title);

        holder.tvTitle.setText(title);
        holder.tvContent.setText(count);
    }

    @Override
    public void setDatas(List<MusicBean> list) {

        mValues = (ArrayList<MusicBean>) list;
    }

    @Override
    public MusicBean getDataAtPosition(int position) {
        return mValues!=null?mValues.get(position-1):null;
    }

    @Override
    public ArrayList<MusicBean> getDataList() {
        return mValues;
    }

    @Override
    public int getDataCount() {
        return mValues!=null?mValues.size():0;
    }

}
