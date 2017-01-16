package com.zsorg.neteasecloudmusic.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/16.
 * E-mail:piyell@qq.com
 */

public class DiskMusicDao {

    private final SQLiteDatabase mDB;

    public DiskMusicDao(Context context) {
        mDB = new DiskMusicHelper(context, "diskMusic", 1).getWritableDatabase();
    }

    public void addMusic(MusicBean musicBean) {
        addMusic(musicBean.getName(), musicBean.getSinger(), musicBean.getAlbum(),musicBean.getDuration(), musicBean.getPath());
    }

    public void addMusic(String name, String singer, String album,long duration, String path) {
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("singer",singer);
        values.put("album",album);
        values.put("path",path);
        values.put("duration",duration);
        mDB.insert("diskMusic", null, values);
    }

    public List<MusicBean> querryAll() {
        Cursor cursor = mDB.rawQuery("select * from diskMusic;", null);
        ArrayList<MusicBean> musicList = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String singer = cursor.getString(1);
            String album = cursor.getString(2);
            String path = cursor.getString(4);
            long duration = cursor.getLong(3);
            musicList.add(new MusicBean(name, singer, album,duration, path));
        }
        return musicList;
    }

    public Map<String,List<MusicBean>> querryAllName() {
        Cursor cursor = mDB.rawQuery("select name,singer,album,path,duration,count(name) from diskMusic group by name;", null);
        HashMap<String, List<MusicBean>> musicMap = new HashMap<>();
        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String singer = cursor.getString(1);
            String album = cursor.getString(2);
            String path = cursor.getString(3);
            long duration = cursor.getLong(4);
            int count = cursor.getInt(5);
            List<MusicBean> list = musicMap.get(name);
            if (null == list) {
                list = new ArrayList<>();
                musicMap.put(name, list);
            }

            list.add(new MusicBean(name, singer, album,duration, path));
        }
        return musicMap;
    }

    public Map<String,List<MusicBean>> querryAllSinger() {
        Cursor cursor = mDB.rawQuery("select name,singer,album,path,duration,count(singer) from diskMusic group by singer;", null);
        HashMap<String, List<MusicBean>> musicMap = new HashMap<>();
        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String singer = cursor.getString(1);
            String album = cursor.getString(2);
            String path = cursor.getString(3);
            long duration = cursor.getLong(4);
            int count = cursor.getInt(5);
            List<MusicBean> list = musicMap.get(name);
            if (null == list) {
                list = new ArrayList<>();
                musicMap.put(name, list);
            }

            list.add(new MusicBean(name, singer, album,duration, path));
        }
        return musicMap;
    }

    public Map<String,List<MusicBean>> querryAllAlbum() {
        Cursor cursor = mDB.rawQuery("select name,singer,album,path,duration,count(album) from diskMusic group by album;", null);
        HashMap<String, List<MusicBean>> musicMap = new HashMap<>();
        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String singer = cursor.getString(1);
            String album = cursor.getString(2);
            String path = cursor.getString(3);
            long duration = cursor.getLong(4);
            int count = cursor.getInt(5);
            List<MusicBean> list = musicMap.get(name);
            if (null == list) {
                list = new ArrayList<>();
                musicMap.put(name, list);
            }

            list.add(new MusicBean(name, singer, album,duration, path));
        }
        return musicMap;
    }
}
