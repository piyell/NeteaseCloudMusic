package com.zsorg.neteasecloudmusic.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import static android.R.attr.duration;

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
        return new MusicBean( name, singer, album,duration, path);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD_MR1)
    public static Bitmap getAlbumArtFromMP3File(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        byte[] bytes = mmr.getEmbeddedPicture();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
