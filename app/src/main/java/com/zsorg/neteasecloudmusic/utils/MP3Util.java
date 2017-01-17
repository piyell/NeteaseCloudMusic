package com.zsorg.neteasecloudmusic.utils;

import android.annotation.TargetApi;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/16.
 * E-mail:piyell@qq.com
 */

public class MP3Util {
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    public static MusicBean parseMP3File(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String singer = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        long duration = Long.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        mmr.release();
        return new MusicBean(name, singer, album,duration, path);
    }
}
