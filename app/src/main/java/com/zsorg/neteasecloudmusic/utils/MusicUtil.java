package com.zsorg.neteasecloudmusic.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/15.
 * E-mail:piyell@qq.com
 */

public class MusicUtil {

    public static boolean isMusic(File file) {
        return null!=file && file.getName().toLowerCase().endsWith(".mp3");
    }

    public static String getAlbumArt(Context mContext, String album_id) {
        String[] projection = new String[]{MediaStore.Audio.Albums.ALBUM_ART};
        Cursor cur = mContext.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projection, MediaStore.Audio.Albums._ID + "=?", new String[]{album_id}, null);
        String album_art = null;
        if (null!=cur && cur.moveToNext()) {
            album_art = cur.getString(0);
            cur.close();
        }

        return album_art;
    }

    public static String[] getParentAndFileName(String path) {
        if (null!=path) {
            int lastIndex = path.lastIndexOf(File.separatorChar);
            if (lastIndex == path.length() - 1 && lastIndex > 0) {
                String tmp = path.substring(0, lastIndex);
                lastIndex = tmp.lastIndexOf(File.separatorChar);
            }
            String parent = path.substring(0, lastIndex+1);
            String name = path.substring(lastIndex+1, path.length());
            return new String[]{parent,name};
        }
        return new String[]{path,path};
    }
}
