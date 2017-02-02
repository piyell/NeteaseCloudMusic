package com.zsorg.neteasecloudmusic.models;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/31.
 * E-mail:piyell@qq.com
 */

public class SingleSongModel implements IMenuModel{

    private static SingleSongModel mModel;

    List<MenuBean> mList;


    @Override
    public List<MenuBean> getMenuList() {
        return mList;
    }

    public static SingleSongModel getInstance(Context context) {
        if (null == mModel) {
            synchronized (SingleSongModel.class) {
                mModel = new SingleSongModel(context);
            }
        }
        return mModel;
    }



    private SingleSongModel(@NonNull Context context) {
        mList = new ArrayList<>();
        mList.add(new MenuBean(context.getString(R.string.play_at_next), 0));
        mList.add(new MenuBean(context.getString(R.string.collect_to_playlist), 0));
        mList.add(new MenuBean(context.getString(R.string.share), 0));
        mList.add(new MenuBean(context.getString(R.string.delete), R.drawable.ic_delete));
        mList.add(new MenuBean(context.getString(R.string.watch_music_info), 0));
    }
}
