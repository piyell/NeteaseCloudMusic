package com.zsorg.neteasecloudmusic.models.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/16.
 * E-mail:piyell@qq.com
 */

public class DiskMusicHelper extends SQLiteOpenHelper {

    public DiskMusicHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists diskMusic(name varchar not null,singer varchar,album varchar,duration integer ,path varchar not null,parent varchar); ");
        db.execSQL("create table if not playlist(list_id integer not null  default 1 primary key autoincrement,name text,path text);");
        db.execSQL("insert into playlist(list_id,name) values(0,\"我喜欢的音乐\");");
        db.execSQL("create table if not playlist_detail(list_id integer not null,path varchar not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
