package com.zsorg.neteasecloudmusic.models;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import io.reactivex.Flowable;


/**
 * Created by piyel_000 on 2017/1/13.
 */

public interface IMusicModel {
    Flowable<MusicBean> scanMusicFile();

    Flowable<MusicBean> loadMusicFile();

}
