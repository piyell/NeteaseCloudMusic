package com.zsorg.neteasecloudmusic.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;

import com.zsorg.neteasecloudmusic.CONST;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

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

    public void addToPlaylist(int playlistID,String path) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("list_id", playlistID);
        values.put("path", path);
        db.insert("playlist_detail", null, values);
        db.close();
    }

    public int findPositionInPlaylist(int playlistID, String path) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from playlist_detail where list_id==?  order by playlist_detail.[path] desc;", new String[]{String.valueOf(playlistID)});
        for (int position = 0; cursor != null && cursor.moveToNext(); position++) {
            String tmp = cursor.getString(1);
            if (path.equals(tmp)) {
                return position;
            }
        }
        return 0;
    }

    public int addMusicToTempPlaylist(Context context, final String path) {
        int position = 0;
        final List<MusicBean> list = new DiskMusicDao(context).queryAll();
        for (; position < list.size() && !path.equals(list.get(position).getPath()); position++);
        return position;
//        Flowable.fromIterable(list).filter(new Predicate<MusicBean>() {
//            @Override
//            public boolean test(MusicBean bean) throws Exception {
//                return path.equals(bean.getPath());
//            }
//        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<MusicBean>() {
//            @Override
//            public void accept(MusicBean bean) throws Exception {
//                position= list.indexOf(bean);
//            }
//        });
    }

    public void deleteFromPlaylist(int playlistID,String path) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete("playlist_detail", "(list_id==?) and (path==?)", new String[]{String.valueOf(playlistID), path});
        db.close();
    }

    public List<MusicBean> loadPlaylistList() {
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

    public List<MusicBean> loadPlaylist(int playlistID) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name,singer,album,duration,diskMusic.[path] from diskMusic left join playlist_detail on playlist_detail.[path]=diskMusic.[path] where playlist_detail.[list_id]=? order by playlist_detail.[path] desc; "
                ,new String[]{String.valueOf(playlistID)});

        ArrayList<MusicBean> list = new ArrayList<>();

        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String singer = cursor.getString(1);
            String album = cursor.getString(2);
            long duration = cursor.getLong(3);
            String path = cursor.getString(4);
            list.add(new MusicBean(name,singer,album,duration, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return list;
    }
}
