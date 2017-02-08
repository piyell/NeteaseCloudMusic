package com.zsorg.neteasecloudmusic.presenters;

import android.content.Intent;

import com.zsorg.neteasecloudmusic.CONST;
import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.OnTrackListener;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.PlaylistModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.views.IPlayerView;
import com.zsorg.neteasecloudmusic.views.IPlaylistView;

import java.util.ArrayList;
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

    public void requestMusicInfo() {
//        List<MusicBean> list = new PlaylistModel(iPlayerView.getContext()).loadPlaylist(id);

        MusicBean musicBean = PlayerManager.getInstance(iPlayerView.getContext()).getMusicBean();
        iPlayerView.displayMusicInfo(musicBean);
    }

    public void setPlaylist(ArrayList<MusicBean> list, int position) {
        MusicPlayerService.startActionSet(iPlayerView.getContext(),list,position);
    }

    public void addToPlaylist(MusicBean bean) {
        PlayerManager manager = PlayerManager.getInstance(iPlayerView.getContext());
        List<MusicBean> playlist = manager.getPlaylist();
        if (null == playlist) {
            playlist = new ArrayList<>();
            manager.setCurrentPlaylist(playlist);
        }
        if (null!=bean) {
            int index = playlist.indexOf(bean);

            if (index<0) {
                playlist.add(bean);
                index = playlist.size() - 1;
            }
            manager.setCurrentPosition(index);
        }
    }

    public void syncPlayerInfo() {
        PlayerManager.getInstance(iPlayerView.getContext()).setOnTrackListener(this);
    }

    public void unSyncPlayerInfo() {
        PlayerManager.getInstance(iPlayerView.getContext()).setOnTrackListener(null);
    }

    @Override
    public void onTrack(int position) {
        if (null != iPlayerView) {
            iPlayerView.updateTrackInfo(position);
        }
    }

    @Override
    public void onNext(MusicBean bean) {
        if (null != iPlayerView) {
            iPlayerView.displayMusicInfo(bean);
        }
    }

    @Override
    public void onPlayStateChange(boolean isPlay) {
        if (null != iPlayerView) {
            iPlayerView.updatePlayButton(isPlay);
        }
    }

    public void nextMusic() {
        PlayerManager.getInstance(iPlayerView.getContext()).nextMusic();
    }

    public void preMusic() {
        PlayerManager.getInstance(iPlayerView.getContext()).preMusic();
    }

    public boolean isAddedToFavorite() {
        return new PlaylistModel(iPlayerView.getContext()).isInPlaylist(CONST.PLAYLIST_FAVORITE,PlayerManager.getInstance(iPlayerView.getContext()).getMusicBean().getPath());
    }

    public void addToFavorite() {
        new PlaylistModel(iPlayerView.getContext()).addToPlaylist(CONST.PLAYLIST_FAVORITE,PlayerManager.getInstance(iPlayerView.getContext()).getMusicBean().getPath());
    }

    public void cancelFavorite() {
        new PlaylistModel(iPlayerView.getContext()).deleteFromPlaylist(CONST.PLAYLIST_FAVORITE,PlayerManager.getInstance(iPlayerView.getContext()).getMusicBean().getPath());
    }
}
