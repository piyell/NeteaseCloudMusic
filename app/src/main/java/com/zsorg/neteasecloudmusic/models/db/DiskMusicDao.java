package com.zsorg.neteasecloudmusic.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.utils.MusicUtil;

import java.io.File;
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

    private final DiskMusicHelper mDBHelper;

    public DiskMusicDao(Context context) {
        mDBHelper = new DiskMusicHelper(context, "diskMusic", 1);
    }

    @Override
    protected void finalize() throws Throwable {
        mDBHelper.close();
        super.finalize();
    }

    public void addMusic(MusicBean musicBean) {
        addMusic(musicBean.getName(), musicBean.getSinger(), musicBean.getAlbum(),musicBean.getDuration(), musicBean.getPath());
    }

    public void clearAll() {
        mDBHelper.getWritableDatabase().execSQL("delete from diskMusic;");
    }

    public void addMusic(String name, String singer, String album,long duration, String path) {
//        ContentValues values = new ContentValues();
//        values.put("name",name);
//        values.put("singer",singer);
//        values.put("album",album);
//        values.put("path",path);
//        values.put("parent",path.substring(0,path.lastIndexOf(File.separatorChar)));
//        values.put("duration",duration);
//        mDBHelper.getWritableDatabase().insert("diskMusic", null, values);
        if (null == name) {
            name = MusicUtil.getParentAndFileName(path)[1];
        }
        mDBHelper.getWritableDatabase().execSQL("insert into diskMusic(name,singer,album,path,parent,duration) values (?,?,?,?,?,?);", new String[]{
                name, singer, album, path, path.substring(0, path.lastIndexOf(File.separatorChar)), String.valueOf(duration)
        });
    }

    public List<MusicBean> queryAll() {
        Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select * from diskMusic order by name desc;", null);
        ArrayList<MusicBean> musicList = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String singer = cursor.getString(1);
            String album = cursor.getString(2);
            String path = cursor.getString(4);
            long duration = cursor.getLong(3);
            musicList.add(new MusicBean( name, singer, album,duration, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        return musicList;
    }

    public Map<String,List<MusicBean>> queryAllName() {
        Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select name,singer,album,path,duration,count(path) from diskMusic group by name order by name desc;", null);
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

            list.add(new MusicBean( name, singer, album,duration, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        return musicMap;
    }

    public List<MusicBean> querySingerList() {
        Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select singer,path,count(path) from diskMusic group by singer order by singer desc;", null);
        List<MusicBean> list = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String singer = cursor.getString(0);
            String path = cursor.getString(1);
            int count = cursor.getInt(2);
            list.add(new MusicBean( null, singer, null,count, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public List<MusicBean> querySingerMusicBeanList(String singer) {
        Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select * from diskMusic where singer"+(null==singer?" is null":"==?")+" order by singer desc;", null!=singer?new String[]{singer}:null);
        List<MusicBean> list = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String album = cursor.getString(2);
            int duration = cursor.getInt(3);
            String path = cursor.getString(4);

            list.add(new MusicBean( name, singer, album,duration, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public List<MusicBean> queryAlbumMusicBeanList(String album) {
        Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select * from diskMusic where album"+(null==album?" is null":"==?")+" order by album desc;", null!=album?new String[]{album}:null);
        List<MusicBean> list = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String singer = cursor.getString(1);
            int duration = cursor.getInt(3);
            String path = cursor.getString(4);

            list.add(new MusicBean( name, singer, album,duration, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public List<MusicBean> queryFolderMusicBeanList(String parent) {
        Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select * from diskMusic where parent"+(null==parent?" is null":"==?")+" order by parent desc;", null!=parent?new String[]{parent}:null);
        List<MusicBean> list = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String name = cursor.getString(0);
            String singer = cursor.getString(1);
            String album = cursor.getString(2);
            int duration = cursor.getInt(3);
            String path = cursor.getString(4);

            list.add(new MusicBean( name, singer, album,duration, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public void deleteSingleSong(String path) {
        mDBHelper.getWritableDatabase().execSQL("delete from diskMusic where path==\"" + path.replace("\"", "\\\"") + "\"");
        mDBHelper.getWritableDatabase().execSQL("delete from playlist_detail where path==\"" + path.replace("\"", "\\\"") + "\"");
    }

    public void deleteFolderMusicList(String parent) {
        mDBHelper.getWritableDatabase().execSQL("delete from diskMusic where parent"+(null==parent?" is null":"==\""+parent.replace("\"", "\\\"")+"\";"));
    }

    public void deleteAlbumMusicList(String album) {
        mDBHelper.getWritableDatabase().execSQL("delete from diskMusic where album"+(null==album?" is null":"==\""+album.replace("\"", "\\\"")+"\";"));
    }

    public void deleteSingerMusicList(String singer) {
        mDBHelper.getWritableDatabase().execSQL("delete from diskMusic where singer"+(null==singer?" is null":"==\""+singer.replace("\"", "\\\"")+"\";"));
    }

    public List<MusicBean> queryAlbumList() {
        Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select singer,album,path,count(path) from diskMusic group by album order by album desc;", null);
        List<MusicBean> list = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String singer = cursor.getString(0);
            String album = cursor.getString(1);
            String path = cursor.getString(2);
            int count = cursor.getInt(3);
            list.add(new MusicBean( null, singer, album,count, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public List<MusicBean> queryPathList() {
        Cursor cursor = mDBHelper.getReadableDatabase().rawQuery("select parent,path,count(parent) from diskMusic group by parent order by parent desc;", null);
        List<MusicBean> list = new ArrayList<>();
        while (null != cursor && cursor.moveToNext()) {
            String parent = cursor.getString(0);
            String path = cursor.getString(1);
            int count = cursor.getInt(2);
            String[] parentAndFileName = MusicUtil.getParentAndFileName(parent);
            list.add(new MusicBean(parentAndFileName[1],parent ,parentAndFileName[0],count, path));
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

}
