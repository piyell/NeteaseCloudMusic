package com.zsorg.neteasecloudmusic.presenters;

import android.content.Intent;

import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.OnTrackListener;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.PlaylistModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.views.IPlayerView;
import com.zsorg.neteasecloudmusic.views.IPlaylistView;

import java.util.List;


/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/23.
 * E-mail:piyell@qq.com
 */

public class PlayerPresenter implements OnTrackListener {

    private final IPlayerView iPlayerView;

    public PlayerPresenter(IPlayerView view) {
        iPlayerView = view;
    }

    public void startPlay(boolean isRestart){
        MusicPlayerService.startActionPlay(iPlayerView.getContext(),isRestart);
    }

    public void Pause(){
        MusicPlayerService.startActionPause(iPlayerView.getContext());
    }

    public void requestMusicInfo(int id, int position) {
        List<MusicBean> list = new PlaylistModel(iPlayerView.getContext()).loadPlaylist(id);
        if (position < list.size()) {
            iPlayerView.displayMusicInfo(list.get(position));
        }
    }

    public void setPlaylist(int id, int position) {
        MusicPlayerService.startActionSet(iPlayerView.getContext(),id,position);
        PlayerManager.getInstance(iPlayerView.getContext()).setOnTrackListener(this);
    }

    @Override
    public void onTrack(int position) {
        if (null != iPlayerView) {
            iPlayerView.updateTrackInfo(position);
        }
    }
}
