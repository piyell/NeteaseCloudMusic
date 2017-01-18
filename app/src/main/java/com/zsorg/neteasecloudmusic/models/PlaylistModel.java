package com.zsorg.neteasecloudmusic.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/18.
 * E-mail:piyell@qq.com
 */

public class PlaylistModel {

    private final DiskMusicHelper mDBHelper;
    private final String songsILove;

    public PlaylistModel(Context context) {
        mDBHelper = new DiskMusicHelper(context, "diskMusic", 1);
        songsILove = context.getString(R.string.songs_i_love);
    }

    public List<MusicBean> loadPlaylist() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select playlist.[list_id],playlist.[name],playlist.[path],count(playlist_detail.[path]) from playlist left join playlist_detail on playlist_detail.[list_id]=playlist.[list_id] group by playlist.[list_id] order by playlist.[list_id] asc;",null);

        ArrayList<MusicBean> list = new ArrayList<>();

        while (null != cursor && cursor.moveToNext()) {
            int listID = cursor.getInt(0);
            String name = cursor.getString(1);
            String path = cursor.getString(2);
            String count = cursor.getString(3);
            list.add(new MusicBean((listID!=0?name:songsILove), null, count, listID, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return list;
    }
}
