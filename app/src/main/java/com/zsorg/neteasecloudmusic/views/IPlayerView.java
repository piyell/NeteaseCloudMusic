package com.zsorg.neteasecloudmusic.views;

import android.content.Context;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/23.
 * E-mail:piyell@qq.com
 */

public interface IPlayerView {

    void displayMusicInfo(MusicBean bean);

    Context getContext();

    void updateTrackInfo(int position);
}
