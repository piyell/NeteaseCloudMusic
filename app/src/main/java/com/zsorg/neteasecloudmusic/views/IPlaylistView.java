package com.zsorg.neteasecloudmusic.views;

import android.content.Context;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/18.
 * E-mail:piyell@qq.com
 */

public interface IPlaylistView {
    void showItems(List<MusicBean> list);

    Context getContext();
}
